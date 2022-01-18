package uk.ac.hutton.lkt;

import org.nlogo.api.*;

import java.util.Iterator;
import java.util.List;

public class Dimension {
	private final String name;
	private final String group;
	private final List<String> symbols;
	private Iterator<String> symbolIterator;

	public Dimension(String name, String group, List<String> symbols) {
		this.name = name;
		this.group = group;
		this.symbols = symbols;
	}

	boolean hasSymbol(String symbol) {
		return symbols.contains(symbol);
	}

	public String getName() {
		return name;
	}

	public List<String> getSymbols() {
		return symbols;
	}

	public Iterator<String> symbolIterator() {
		if (symbolIterator == null)
			symbolIterator = symbols.iterator();

		return symbolIterator;
	}

	public void resetIterator() {
		symbolIterator = symbols.iterator();
	}

	public String getSymbol() throws ExtensionException {
		if (symbolIterator().hasNext())
			return symbolIterator().next();

		throw new ExtensionException("No more symbols");
	}

	public String getGroup() {
		return group;
	}

	public boolean moreSymbols() {
		return symbolIterator().hasNext();
	}
}
