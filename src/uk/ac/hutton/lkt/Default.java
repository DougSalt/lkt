package uk.ac.hutton.lkt;

import org.nlogo.api.*;
import org.nlogo.api.Command;
import org.nlogo.core.*;

public class Default implements Command
{
	public static Object defaultOutcome = null;

	@Override
	public void perform(Argument[] args, Context context)
		throws ExtensionException
	{
		defaultOutcome = args[0].get();
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.commandSyntax(new int[] { Syntax.WildcardType()});
	}
}
