package uk.ac.hutton.lkt;

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.LogoList;
import org.nlogo.core.Syntax;
import org.nlogo.core.SyntaxJ;

import java.util.*;
import java.util.stream.Collectors;

public class Get implements Reporter {
	@Override
	public Object report(Argument[] args, Context context)
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

			return lt.getForDimension(dimensionName, symbols);
		}

		throw new ExtensionException("Invalid lookup-table");
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.ListType()}, Syntax.WildcardType());
	}
}
