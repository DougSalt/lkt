package uk.ac.hutton.lt;

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class GetSymbol implements Reporter {
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException {
		if (args[0].get() instanceof NetLogoLookupTable) {
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();
			String dimensionName = args[1].getString();

			return lt.getSymbolFor(dimensionName);
		}

		throw new ExtensionException("Invalid lookup-table");
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType(), Syntax.StringType() }, Syntax.StringType());
	}
}
