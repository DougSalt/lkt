/**
 * 
 */
package uk.ac.hutton.lt;

import org.nlogo.api.*;

import java.io.PrintStream;
import java.util.*;

/**
 * LookupTable
 * 
 * A lookup table is a recursive tree-like datastructure in which the nodes of
 * the tree act as an index on the data stored at the leaves. In general, it can
 * be conceived as a map with multi-dimensional keys, each of which can be
 * thought of as defining a situation, and scalar values, each defining the
 * outcome of the situation it is keyed by. Since it is a tree-like structure,
 * however, keys need not all have the same number of dimensions, but the order
 * of the dimensions is constant and shared across all keys. However, the same
 * specific set of keys must always either point to an outcome or to further
 * sets of keys. Thus, the following is not permitted, because it leads to a
 * non-functional relationship between keys and outcomes (i.e. there is
 * ambiguity over what the key sequence <code>item1, item2, item3</code>
 * corresponds to:
 * 
 * <p>
 * <code>item1, item2, item3 --&gt; outcome1
 * <br>item1, item2, item3, item4 --&gt; outcome2</code>
 * </p>
 * 
 * @author Gary Polhill
 */
public class LookupTable<I, O> {
	  /**
	   * Map from this to the next dimension of keys
	   */
	  Map<I, LookupTable<I, O>> next;

	  /**
	   * Map from key to value for those keys in this dimension that do not map to
	   * further keys
	   */
	  Map<I, O> last;

	  /**
	   * Label for this dimension
	   */
	  String label;

	  /**
	   * Labels for other dimensions
	   */
	  String[] labels;

	  Iterator<O> statesIterator;

	  /**
	   * Default constructor, with no labels for the dimensions
	   */
	  public LookupTable() {
	    next = null;
	    last = null;
	    label = null;
	    labels = null;
	  }

	  public LookupTable(String... labels) {
	    this(null, labels);
	  }

	  /**
	   * Constructor providing a series of labels, one for each dimension of input
	   * 
	   * @param labels Labels to be applied to each key dimension
	   */
	  public LookupTable(String prefix, String... labels) {
	    if(labels == null) return;
	    String[] plabels = new String[labels.length];
	    for(int i = 0; i < labels.length; i++) {
	      plabels[i] = prefix == null ? labels[i] : prefix + labels[i];
	    }
	    label = plabels[0];
	    this.labels = new String[plabels.length - 1];
	    for(int i = 1; i < plabels.length; i++) {
	      this.labels[i - 1] = plabels[i];
	    }
	  }

	  /**
	   * Constructor called recursively when storing values in the lookup table.
	   * This then goes on to recursively add the remaining keys.
	   * 
	   * @param labels Labels, if any, for key dimensions
	   * @param outcome The outcome being added to the tree
	   * @param input The iterator looping through the key dimensions
	   */
	  protected LookupTable(String[] labels, final O outcome, Iterator<I> input) {
	    this(labels);
	    this.add(outcome, input);
	  }
	  
	  /**
	   * <!-- getInputLabels -->
	   *
	   * @return An array containing all the labels in this (sub)lookup table.
	   */
	  public String[] getInputLabels() {
	    String[] inputLabels = new String[labels.length + 1];
	    inputLabels[0] = label;
	    for(int i = 0; i < labels.length; i++) {
	      inputLabels[i + 1] = labels[i];
	    }
	    return inputLabels;
	  }

	  /**
	   * <!-- getInputs -->
	   * 
	   * Return the inputs in the lookup table for a particular labelled dimension,
	   * as a set. The set will be empty if none can be found.
	   * 
	   * @param label The label for the dimension to find the input set for
	   * @return The input set
	   */
	  public Set<I> getInputs(String label) {
	    Set<I> inputs = new HashSet<I>();

	    if(next != null) inputs.addAll(next.keySet());

	    if(label.equals(this.label)) {
	      if(last != null) inputs.addAll(last.keySet());
	      return inputs;
	    }
	    else {
	      if(next == null || next.size() == 0) return new HashSet<I>();
	      Iterator<I> ix = inputs.iterator();
	      I anInput = ix.next();
	      return next.get(anInput).getInputs(label);
	    }

	  }

	  /**
	   * <!-- add -->
	   * 
	   * Add an outcome with a series of keys. A variable arguments list is used to
	   * negate the need to construct key arrays. This method builds a linked list
	   * from the arguments list, and calls the add method with that.
	   * 
	   * @param outcome The outcome to add
	   * @param input The keys for that outcome
	   */
	  public void add(final O outcome, final I... input) {
	    LinkedList<I> inputList = new LinkedList<I>();

	    for(int i = 0; i < input.length; i++) {
	      inputList.addLast(input[i]);
	    }
	    add(outcome, inputList);
	  }

	  /**
	   * <!-- add -->
	   * 
	   * Add an outcome with a series of keys stored in an Iterable collection. This
	   * method gets an iterator for the input, and calls the add method with that.
	   * 
	   * @param outcome The outcome to add
	   * @param input The keys for that outcome
	   */
	  public void add(final O outcome, final Iterable<I> input) {
	    add(outcome, input.iterator());
	  }

	  /**
	   * <!-- add -->
	   * 
	   * Add an outcome with using an iterator over a set of keys. This is the main
	   * add method, which all the other add methods will end up calling. A
	   * NoSuchElementException will be thrown by this method if
	   * <code>input.hasNext() == false</code>. A RuntimeException will be thrown if
	   * adding this key would lead to a non-functional relationship between key
	   * sequence and outcome. This does not prevent new values being stored for
	   * specific existing key sequences, however.
	   * 
	   * @param outcome The outcome to add
	   * @param input Iterator over the keys for that outcome
	   */
	  public void add(final O outcome, Iterator<I> input) {
	    // If the iterator is already at the end of the iterable, the following
	    // statement will throw a NoSuchElementException
	    I this_input = input.next();
	    if(!input.hasNext()) {
	      if(next != null && next.containsKey(this_input)) {
	        throw new RuntimeException("Ambiguous entry in a lookup table: key \"" + this_input
	          + "\" is being used to point to outcome \"" + outcome
	          + "\" when this key has previously been used to store entries " + "with further key dimensions.");
	      }
	      if(last == null) {
	        last = new HashMap<I, O>();
	      }
	      last.put(this_input, outcome);
	    }
	    else {
	      if(last != null && last.containsKey(this_input)) {
	        throw new RuntimeException("Ambiguous entry in a lookup table: key \"" + this_input
	          + "\" is being used to store outcome \"" + outcome
	          + "\" with further key dimensions, when this key has previously " + "\" been used to store outcome \""
	          + last.get(this_input) + "\".");
	      }
	      if(next == null) {
	        next = new HashMap<I, LookupTable<I, O>>();
	      }
	      if(next.containsKey(this_input)) {
	        next.get(this_input).add(outcome, input);
	      }
	      else {
	        next.put(this_input, new LookupTable<I, O>(Arrays.copyOfRange(labels, 1, labels.length), outcome, input));
	      }
	    }
	  }

	  /**
	   * <!-- add -->
	   * 
	   * Add an entry to the lookup table where the keys are stored in a map from
	   * label to input.
	   * 
	   * @param outcome The outcome to add
	   * @param input A map of labels to keys at which to add the outcome
	   */
	  public void add(final O outcome, final Map<String, I> input) {
	    add(outcome, buildInputList(new LinkedList<I>(), input));
	  }

	  /**
	   * <!-- buildInputList -->
	   * 
	   * A private method used by the add and lookup methods that take a map, which
	   * recursively calls sub-lookup-tables to build a list of keys.
	   * 
	   * @param inputList The list of keys being recursively built
	   * @param input The map of labels of key dimensions to keys
	   * @return The list of keys
	   */
	  private LinkedList<I> buildInputList(LinkedList<I> inputList, final Map<String, I> input) {
	    I this_input = input.get(label);
	    inputList.addLast(this_input);
	    if(next != null && next.containsKey(this_input)) {
	      return next.get(this_input).buildInputList(inputList, input);
	    }
	    else {
	      return inputList;
	    }
	  }

	  /**
	   * <!-- lookup -->
	   * 
	   * Lookup an outcome, specified by a map of key dimension labels to key
	   * values.
	   * 
	   * @param input The map.
	   * @return The outcome.
	   */
	  public O lookup(final Map<String, I> input) {
	    return lookup(buildInputList(new LinkedList<I>(), input));
	  }

	  /**
	   * <!-- lookup -->
	   * 
	   * Provide access to a sub-lookup-table, accessed by a single key
	   * 
	   * @param input The key of the sub-lookup-table
	   * @return The sub-lookup-table
	   */
	  public LookupTable<I, O> lookup(I input) {
	    return next.get(input);
	  }

	  /**
	   * <!-- lookup -->
	   * 
	   * Lookup the outcome for a specified set of inputs, given as an array or a
	   * variable argument list. This method builds an iterable linked list and
	   * delegates responsibility for looking up to other polymorphisms.
	   * 
	   * @param input The inputs to look up
	   * @return The outcome
	   */
	  public O lookup(I... input) {
	    LinkedList<I> inputList = new LinkedList<I>();
	    for(int i = 0; i < input.length; i++) {
	      inputList.addLast(input[i]);
	    }
	    return lookup(inputList);
	  }

	  /**
	   * <!-- lookup -->
	   * 
	   * Lookup the outcome for a set of inputs given as an iterable collection.
	   * This method gets an iterator, and hands over to the main lookup method.
	   * 
	   * @param input
	   * @return
	   */
	  public O lookup(Iterable<I> input) {
	    return lookup(input.iterator());
	  }

	  /**
	   * <!-- lookup -->
	   * 
	   * Main lookup method, which takes an iterator, and recurses through the
	   * sub-lookup-tables until the keys lead to a specific outcome.
	   * 
	   * @param input An iterator on a collection of keys
	   * @return The outcome
	   */
	  public O lookup(Iterator<I> input) {
	    // If the iterator is already at the end of the iterable, the following
	    // statement will throw a NoSuchElementException
	    I this_input = input.next();
	    if(!input.hasNext()) {
	      if(last == null) return (O)Default.defaultOutcome;
	      if(!last.containsKey(this_input)) return (O)Default.defaultOutcome;
	      return last.get(this_input);
	    }
	    else {
	      if(next == null) return (O)Default.defaultOutcome;
	      if(!next.containsKey(this_input)) return (O)Default.defaultOutcome;
	      return next.get(this_input).lookup(input);
	    }
	  }

	/**
	 * <!-- setState -->
	 *
	 * Sets the outcome for a set of inputs given as an iterable collection.
	 * This method gets an iterator, and hands over to the main set method.
	 *
	 * @param input
	 * @return
	 */
	public void setState(Iterable<I> input, O state) {
		setState(input.iterator(), state);
	}

	/**
	 * <!-- setState -->
	 *
	 * Main set method, which takes an iterator, and recurses through the
	 * sub-lookup-tables until the keys lead to a specific outcome.
	 *
	 * @param input An iterator on a collection of keys
	 * @return The outcome
	 */
	public void setState(Iterator<I> input, O state) {
		// If the iterator is already at the end of the iterable, the following
		// statement will throw a NoSuchElementException
		I this_input = input.next();
		if(!input.hasNext()) {
			if(last == null) return;
			if(!last.containsKey(this_input)) return;
			last.replace(this_input, state);
		}
		else {
			if(next == null) return;
			if(!next.containsKey(this_input)) return;
			next.get(this_input).setState(input, state);
		}
	}

	  public List<O> states() {
	  	return states(null, new ArrayList<>());
	  }

	  private List<O> states(I before, List<O> states) {
		  if(last != null)
		  {
			  for (I key : last.keySet())
			  {
				  states.add(last.get(key));
			  }
			  return states;
		  }

	  	if(next != null) {
			  for(I key: next.keySet()) {
				  next.get(key).states(key, states);
			  }
		  }

	  	return states;
	  }

	  /**
	   * <!-- print -->
	   * 
	   * Print the table to standard out
	   */
	  public void print() {
	    print("", System.out);
	  }

	  /**
	   * <!-- print -->
	   * 
	   * Print the table to a specified output stream
	   * 
	   * @param stream The stream to output to
	   */
	  public void print(PrintStream stream) {
	    print("", stream);
	  }

	  /**
	   * <!-- print -->
	   * 
	   * Recursive private method implementing the table print. We print all the
	   * leaf nodes, then recurse to the subnodes.
	   * 
	   * @param before Text containing the previous nodes
	   * @param stream The stream to output to
	   */
	  private void print(String before, PrintStream stream) {
	    if(last != null) {
	      for(I key: last.keySet()) {
	        stream.println(before + key + " = " + last.get(key));
	      }
	    }
	    if(next != null) {
	      for(I key: next.keySet()) {
	        next.get(key).print(before + key + ", ", stream);
	      }
	    }
	  }

	  public String getString(String before, StringBuilder builder)
	  {
		  if(last != null) {
			  for(I key: last.keySet()) {
				  builder.append(before + key + " = " + last.get(key)).append(System.getProperties().getProperty("line.separator"));
			  }
		  }
		  if(next != null) {
			  for(I key: next.keySet()) {
				  next.get(key).getString(before + key + ", ", builder);
			  }
		  }

		  return builder.toString();
	  }

	  public O get(String dimension, List<I> inputs) {
	  	if (dimension.equalsIgnoreCase(label))
	  		return lookup(inputs);

		  if(next != null) {
			  for(I key: next.keySet()) {
				  return next.get(key).get(dimension, inputs);
			  }
		  }

		  return null;
	  }

	  public void setState(String dimension, List<I> inputs, O state) {
		  if (dimension.equalsIgnoreCase(label))
			  setState(inputs, state);

		  if(next != null) {
			  for(I key: next.keySet()) {
				  next.get(key).setState(dimension, inputs, state);
			  }
		  }
	  }

	  public Iterator<O> statesIterator() {
	  	if (statesIterator == null)
	  		statesIterator = states().iterator();

	  	return statesIterator;
	  }

	  public void resetStatesIterator() {
	  	statesIterator = states().iterator();
	  }

	  public O getState() throws ExtensionException {
	  	if (statesIterator().hasNext())
	  		return statesIterator().next();

	  	throw new ExtensionException("No more states");
	  }

	  public boolean moreStates() {
	  	return statesIterator().hasNext();
	  }

	  public String toString()
	  {
		  return getString("", new StringBuilder());
	  }
}
