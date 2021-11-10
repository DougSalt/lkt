package uk.ac.hutton.lt;

import java.io.*;

import org.nlogo.api.*;
import org.nlogo.api.Reporter;
import org.nlogo.core.*;

public class New implements Reporter
{
	@Override
	public Object report(Argument[] args, Context context)
		throws ExtensionException
	{
		String structureFile = args[0].getString();
		String dataFile = args[1].getString();

		try
		{
			NetLogoLookupTable lookupTable = new NetLogoLookupTable(structureFile);
			lookupTable.readDataFile(dataFile);

			return lookupTable;
		}
		catch(IOException e)
		{
			throw new ExtensionException(e);
		}
	}

	@Override
	public Syntax getSyntax()
	{
		return SyntaxJ.reporterSyntax(new int[] { Syntax.StringType(), Syntax.StringType()}, Syntax.WildcardType());
	}
}
