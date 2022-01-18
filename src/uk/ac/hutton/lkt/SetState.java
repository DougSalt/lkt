package uk.ac.hutton.lkt;

import org.nlogo.api.Command;
import org.nlogo.api.Reporter;
import org.nlogo.api.*;
import org.nlogo.core.*;

import java.util.*;

public class SetState implements Command
{
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException {
		if (args[0].get() instanceof NetLogoLookupTable) {
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();
			String dimensionName = args[1].getString();

			List<String> symbols = new ArrayList<>();

			LogoList list = args[2].getList();
			for (Object o : list.javaIterable())
				if (o instanceof String)
					symbols.add((String)o);

			Object state = args[3].get();

			lt.setForDimension(dimensionName, symbols, state);
		} else {
			throw new ExtensionException("Invalid lookup-table");
		}
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType(), Syntax.StringType(), Syntax.ListType(), Syntax.WildcardType()});
	}
}
