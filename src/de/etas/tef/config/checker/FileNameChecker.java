package de.etas.tef.config.checker;

import de.etas.tef.config.helper.IConstants;

public class FileNameChecker extends StringChecker
{

	@Override
	public boolean check(String input)
	{
		if(input == null || input.isEmpty())
		{
			return false;
		}
		
		for(int i = 0; i < IConstants.CHECKER_FILE_NAME.length(); i++)
		{
			if(input.toLowerCase().endsWith(IConstants.SUPPORT_FILE_TYPE[i]))
			{
				return true;
			}
		}
		
		return false;
	}

}
