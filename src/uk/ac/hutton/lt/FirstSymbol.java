package uk.ac.hutton.lt;

import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class FirstSymbol implements Command {
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoLookupTable) {
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();
			String dimensionName = args[1].getString();

			lt.resetIteratorFor(dimensionName);
		} else {
			throw new ExtensionException("Invalid lookup-table");
		}
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.commandSyntax(new int[] {Syntax.WildcardType(), Syntax.StringType()});
	}
}
