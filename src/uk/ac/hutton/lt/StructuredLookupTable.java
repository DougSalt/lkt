/**
 * 
 */
package uk.ac.hutton.lt;

import org.nlogo.api.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 * @author GP40285
 *
 */
public class StructuredLookupTable<T> extends LookupTable<String, T> {
	final StructuredLookupTable<T> top;
	Map<String, Map<String, List<String>>> structure;

	List<String> structureDimensions;
	List<Dimension> dimensions = new ArrayList<>();
	Iterator<Dimension> dimensionIterator;


	public StructuredLookupTable(StructuredLookupTable<T> top) {
		super();
		if(top == null) {
			throw new IllegalArgumentException("top cannot be null");
		}
		this.top = top;
		this.structure = null;
	}
	
	public StructuredLookupTable(StructuredLookupTable<T> top, String[] labels) {
		super(labels);
		if(top == null) {
			throw new IllegalArgumentException("top cannot be null");
		}
		this.top = top;
		this.structure = null;
	}
	
	public StructuredLookupTable(Map<String, Map<String, List<String>>> structure) {
		super(getLabelsFromStructure(structure).toArray(new String[0]));
		this.top = null;
		this.structure = structure;

		// Use Java stream methods to pull out the list of dimensions from the structure
		structureDimensions = structure.keySet().stream()
			.flatMap(s -> structure.get(s).keySet().stream())
			.collect(Collectors.toList());

		for (String group : structure.keySet())
		{
			Map<String, List<String>> subGroups = structure.get(group);
			for (String subGroup : subGroups.keySet())
			{
				dimensions.add(new Dimension(subGroup, group, subGroups.get(subGroup)));
			}
		}
	}

	public static List<String> getLabelsFromStructure(Map<String, Map<String, List<String>>> structure) {
		return structure.keySet().stream()
			.flatMap(s -> structure.get(s).keySet().stream())
			.collect(Collectors.toList());
	}

	public StructuredLookupTable(String filename) throws IOException {
		this(new BufferedReader(new FileReader(filename)));
	}
	
	public StructuredLookupTable(BufferedReader fp) throws IOException {
		this(readStructure(fp));
		fp.close();
	}
	
	public static Map<String, Map<String, List<String>>> readStructure(BufferedReader fp) throws IOException {
		Map<String, Map<String, List<String>>> structure = new LinkedHashMap<String, Map<String, List<String>>>();
		String line = fp.readLine();
		String currentGroup = null;
		String currentSubGroup = null;
		Map<String, List<String>> currentGroupStructure = null;
		while(line != null) {
			if(line.startsWith("\t\t")) {
				// Symbols for current subgroup
				if(currentSubGroup == null) {
					// Exception
					throw new IOException("Invalid file format: no subgroup name specified for symbols");
				}
				List<String> list = currentGroupStructure.get(currentSubGroup);
				String symbolStr = line.substring(2);
				for(String symbol: symbolStr.split("\\s+")) {
					list.add(symbol);
				}
			}
			else if(line.startsWith("\t")) {
				// New subgroup
				if(currentGroupStructure == null) {
					// Exception
					throw new IOException("Invalid file format: no group name specified before subgroup");
				}
				currentSubGroup = line.substring(1);
				currentGroupStructure.put(currentSubGroup, new ArrayList<String>());
			}
			else {
				// New group
				currentGroupStructure = new LinkedHashMap<String, List<String>>();
				currentGroup = line;
				structure.put(currentGroup, currentGroupStructure);
				currentSubGroup = null;
			}
			line = fp.readLine();
		}
		return structure;
	}

	public void readDataFile(String dataFile)
		throws ExtensionException, IOException
	{
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(dataFile)))) {
			// The first like of the data file should be the dimensions from the structure file
			String line = reader.readLine();
			// Data file is whitespace separated, hence the regex
			String[] labels = line.split("\\s+");
			// If the dimensions of the data file don't match those of the structure file, throw an exception

			while ((line = reader.readLine()) != null) {
				String[] input = line.split("\\s+");
				String[] arr = Arrays.copyOf(input, input.length-1);
				Iterator<String> iter = Arrays.asList(arr).iterator();
				add((T)(input[input.length-1]), iter);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected StructuredLookupTable(StructuredLookupTable<T> top, String[] labels, List<Dimension> dimensions, final T outcome,
			Iterator<String> input) {
		super(labels);
		this.dimensions = dimensions;
		this.add(outcome, input);
		if(top == null) {
			throw new IllegalArgumentException("top cannot be null");
		}
		this.top = top;
		this.structure = null;
	}

	@Override
	public void add(final T outcome, Iterator<String> input) {
		// If the iterator is already at the end of the iterable, the following
		// statement will throw a NoSuchElementException
		String this_input = input.next();
		if (!input.hasNext()) {
			if (next != null && next.containsKey(this_input)) {
				throw new RuntimeException("Ambiguous entry in a lookup table: key \"" + this_input
						+ "\" is being used to point to outcome \"" + outcome
						+ "\" when this key has previously been used to store entries "
						+ "with further key dimensions.");
			}
			if (last == null) {
				last = new HashMap<String, T>();
			}
			last.put(this_input, outcome);
		} else {
			if (last != null && last.containsKey(this_input)) {
				throw new RuntimeException("Ambiguous entry in a lookup table: key \"" + this_input
						+ "\" is being used to store outcome \"" + outcome
						+ "\" with further key dimensions, when this key has previously "
						+ "\" been used to store outcome \"" + last.get(this_input) + "\".");
			}
			if (next == null) {
				next = new HashMap<String, LookupTable<String, T>>();
			}
			if (next.containsKey(this_input)) {
				next.get(this_input).add(outcome, input);
			} else {
				// We don't need to take the head off labels here as this is done in the call to add which happens
				// as part of the StructuredLookupTable constructor that is called
				next.put(this_input, new StructuredLookupTable<T>(this, labels, dimensions, outcome, input));
			}
		}
	}

	/**
	 * <!-- lookup -->
	 *
	 * Overrides the lookup in LookupTable to allow for matching against wildcard inputs.
	 *
	 * @param input An iterator on a collection of keys
	 * @return The outcome
	 */
	public T lookup(Iterator<String> input) {
		Dimension dimension = getDimension(label);
		// If the iterator is already at the end of the iterable, the following
		// statement will throw a NoSuchElementException
		String this_input = input.next();
		if(!input.hasNext()) {
			if(last == null) return (T)Default.defaultOutcome;
			if(!last.containsKey(this_input))
			{
				if (last.containsKey("*"))
				{
					if (dimension.hasSymbol(this_input))
						return last.get("*");
					else
						return (T)Default.defaultOutcome;
				}
				return (T)Default.defaultOutcome;
			}
			return last.get(this_input);
		}
		else {
			if(next == null) return (T)Default.defaultOutcome;
			if(!next.containsKey(this_input))
			{
				if (next.containsKey("*"))
				{
					if (dimension.hasSymbol(this_input))
						return next.get("*").lookup(input);
					else
						return (T)Default.defaultOutcome;
				}
				return (T)Default.defaultOutcome;
			}
			return next.get(this_input).lookup(input);
		}
	}

	public Map<String, Map<String, List<String>>> getStructure() {
		if(top != null) {
			return top.getStructure();
		}
		else {
			return structure;
		}
	}

	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public Iterator<Dimension> dimensionIterator() {
		if (dimensionIterator == null)
			dimensionIterator = dimensions.iterator();

		return dimensionIterator;
	}

	public void resetDimensionIterator() {
		dimensionIterator = dimensions.iterator();
	}

	public String getDimension() throws ExtensionException {
		if (dimensionIterator().hasNext())
			return dimensionIterator().next().getName();

		throw new ExtensionException("No more dimensions");
	}

	private Dimension getDimension(String dimension) {
		for (Dimension d : dimensions) {
			if (d.getName().equalsIgnoreCase(dimension))
				return d;
		}

		return null;
	}

	public boolean moreDimensions() {
		return dimensionIterator().hasNext();
	}

	boolean hasDimension(String dimension) {
		return dimensions.stream()
			.anyMatch(d -> d.getName().equalsIgnoreCase(dimension));
	}

	List<String> getSymbols(String dimensionName) throws ExtensionException {
		Dimension dimension = getDimension(dimensionName);

		return dimension.getSymbols();
	}

	void resetIteratorFor(String dimensionName) throws ExtensionException {
		Dimension dimension = getDimension(dimensionName);
		if (dimension == null)
			throw new ExtensionException("Invalid dimension");

		dimension.resetIterator();
	}

	String getSymbolFor(String dimensionName) throws ExtensionException {
		Dimension dimension = getDimension(dimensionName);

		return dimension.getSymbol();
	}

	boolean hasMoreSymbolsFor(String dimensionName) throws ExtensionException {
		Dimension dimension = getDimension(dimensionName);
		if (dimension == null)
			throw new ExtensionException("Invalid dimension");

		return dimension.moreSymbols();
	}

	public T getForDimension(String dimensionName, List<String> symbols) throws ExtensionException {
		if (!hasDimension(dimensionName))
			throw new ExtensionException("Incorrect dimension");

		return get(dimensionName, symbols);
	}

	public void setForDimension(String dimensionName, List<String> symbols, T state) throws ExtensionException {
		if (!hasDimension(dimensionName))
			throw new ExtensionException("Incorrect dimension");

		setState(dimensionName, symbols, state);
	}

	public String getGroupFor(String dimension) throws ExtensionException {
		if (!hasDimension(dimension))
			throw new ExtensionException("Invalid dimension");

		return getDimension(dimension).getGroup();
	}
}
