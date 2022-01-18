package uk.ac.hutton.lkt;

import org.nlogo.api.Command;
import org.nlogo.api.*;
import org.nlogo.core.*;

public class FirstState implements Command {
	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoLookupTable) {
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();
			lt.resetStatesIterator();
		} else {
			throw new ExtensionException("Invalid lookup-table");
		}
	}

	@Override
	public Syntax getSyntax() {
		return SyntaxJ.commandSyntax(new int[] {Syntax.WildcardType()});
	}
}
