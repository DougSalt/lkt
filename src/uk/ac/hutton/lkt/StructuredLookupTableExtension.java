package uk.ac.hutton.lkt;

import java.util.List;

import org.nlogo.api.*;
import org.nlogo.api.ExtensionManager;
import org.nlogo.core.*;

public class StructuredLookupTableExtension extends DefaultClassManager
{
	@Override
	public void load(PrimitiveManager manager)
		throws ExtensionException {
		manager.addPrimitive("new", new New());
		manager.addPrimitive("dimensions", new Dimensions());
		manager.addPrimitive("first-dimension", new FirstDimension());
		manager.addPrimitive("get-dimension", new GetDimension());
		manager.addPrimitive("more-dimensions?", new MoreDimensions());
		manager.addPrimitive("symbols", new Symbols());
		manager.addPrimitive("first-symbol", new FirstSymbol());
		manager.addPrimitive("get-symbol", new GetSymbol());
		manager.addPrimitive("more-symbols?", new MoreSymbols());
		manager.addPrimitive("get", new Get());
		manager.addPrimitive("states", new States());
		manager.addPrimitive("first-state", new FirstState());
		manager.addPrimitive("get-state", new GetState());
		manager.addPrimitive("more-states?", new MoreStates());
		manager.addPrimitive("group", new Group());
		manager.addPrimitive("set", new SetState());
		manager.addPrimitive("default", new Default());
	}

	@Override
	public StringBuilder exportWorld() {
		return super.exportWorld();
	}

	@Override
	public void importWorld(List<String[]> lines, ExtensionManager reader, ImportErrorHandler handler)
		throws ExtensionException {
		super.importWorld(lines, reader, handler);
	}

	@Override
	public void clearAll() {
		super.clearAll();
	}

	@Override
	public ExtensionObject readExtensionObject(ExtensionManager em, String typeName, String value)
		throws ExtensionException, CompilerException {
		return super.readExtensionObject(em, typeName, value);
	}
}
