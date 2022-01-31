package uk.ac.hutton.lkt;

import org.nlogo.api.Command;
import org.nlogo.api.*;
import org.nlogo.core.*;

import java.util.*;
import java.util.stream.Collectors;

public class SetState implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException {
		if (args[0].get() instanceof NetLogoLookupTable) {
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();
			//String dimensionName = args[1].getString();
			List<String> dimensionLabels = lt.getDimensions().stream()
					.map(Dimension::getName)
					.collect(Collectors.toList());
			
			String dimensionName = dimensionLabels.get(0);
			List<String> symbols = new ArrayList<>();

			LogoList list = args[1].getList();
			for (Object o : list.javaIterable())
				if (o instanceof String)
					symbols.add((String)o);

			Object state = args[2].get();

			lt.setForDimension(dimensionName, symbols, state);
		} else {
			throw new ExtensionException("Invalid lookup-table");
		}
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.ListType(), Syntax.StringType()});
	}
}
