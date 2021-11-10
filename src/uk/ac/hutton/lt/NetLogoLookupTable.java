package uk.ac.hutton.lt;

import org.nlogo.api.*;
import org.nlogo.core.*;

import java.io.*;
import java.util.*;

public class NetLogoLookupTable extends StructuredLookupTable<Object> implements ExtensionObject
{
	private static long next = 0;

	private static Map<Long, NetLogoLookupTable> lookupTables = new HashMap<Long, NetLogoLookupTable>();

	private final long id;

	protected static void reset() {
		next = 0;
		lookupTables = new HashMap<Long, NetLogoLookupTable>();
	}

	protected static Collection<NetLogoLookupTable> lookupTables() {
		return lookupTables.values();
	}

	protected static NetLogoLookupTable manifest(String idValue) throws ExtensionException, IOException {
		String s[] = idValue.split(":");
		long id = Long.parseLong(s[0]);

		if(lookupTables.containsKey(id)) {
			return lookupTables.get(id);
		}
		else if(s.length > 1) {
			if(s[1].startsWith(" ")) {
				s[1] = s[1].substring(1);
			}
			return new NetLogoLookupTable(s[1]);
		}
		else {
			throw new ExtensionException("Cannot construct a lookup table from string " + idValue);
		}
	}

	public NetLogoLookupTable(StructuredLookupTable<Object> top) {
		super(top);
		this.id = next;
		lookupTables.put(this.id, this);
		next++;
	}

	public NetLogoLookupTable(StructuredLookupTable<Object> top, String[] labels) {
		super(top, labels);
		this.id = next;
		lookupTables.put(this.id, this);
		next++;
	}

	public NetLogoLookupTable(Map<String, Map<String, List<String>>> structure) {
		super(structure);
		this.id = next;
		lookupTables.put(this.id, this);
		next++;
	}

	public NetLogoLookupTable(String filename)
		throws IOException {
		super(filename);
		this.id = next;
		lookupTables.put(this.id, this);
		next++;
	}

	public NetLogoLookupTable(BufferedReader fp)
		throws IOException {
		super(fp);
		this.id = next;
		lookupTables.put(this.id, this);
		next++;
	}

	@Override
	public String dump(boolean readable, boolean exporting, boolean reference) {
		StringBuilder buff = new StringBuilder();
		if(exporting) {
			buff.append(id);
			if(!reference) {
				buff.append(": ");
			}
		}
		if(!(reference && exporting)) {
			buff.append(this.toString());
		}

		return buff.toString();
	}

	@Override
	public String getExtensionName() {
		return "lt";
	}

	@Override
	public String getNLTypeName() {
		return "";
	}

	@Override
	public boolean recursivelyEqual(Object obj) {
		return super.equals(obj);
	}
}
