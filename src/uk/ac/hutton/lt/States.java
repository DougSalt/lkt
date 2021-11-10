package uk.ac.hutton.lt;

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class States implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		if (args[0].get() instanceof NetLogoLookupTable)
		{
			NetLogoLookupTable lt = (NetLogoLookupTable) args[0].get();

			return LogoList.fromJava(lt.states());
		}

		throw new ExtensionException("Invalid lookup-table");
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.WildcardType() }, Syntax.ListType());
	}
}
