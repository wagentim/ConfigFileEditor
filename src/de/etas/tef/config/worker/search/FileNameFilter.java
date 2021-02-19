package de.etas.tef.config.worker.search;

import java.nio.file.Path;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigFilePath;

public class FileNameFilter extends AbstractFilter
{
	
	public FileNameFilter(MainController controller)
	{
		super(controller);
	}

	@Override
	public boolean check(ConfigFilePath file, SearchInfo si)
	{
		// check if it is an INI file
		if(!isINIFile(file.getPath(), IConstants.PATTERN))
		{
			return false;
		}
		
		// check if the file name defined
		String txtFileName = si.getFileName();
		
		// case 1: no search key word defined -> allow
		if(txtFileName == null || txtFileName.isEmpty())
		{
			return true;
		}
		else
		{
			String fileName = file.getPath().getFileName().toString().trim();
			
			// case 2: contain key word -> allow
			if(fileName.toLowerCase().contains(txtFileName.toLowerCase()))
			{
				return true;
			}
			else
			{	// case 3: do not contain key word -> refuse
				return false;
			}
		}
	}
	
	private boolean isINIFile(Path path, String[] pattern)
	{
		String fileName = path.getFileName().toString();
		
		for(String pa : pattern)
		{
			if(fileName.endsWith(pa))
			{
				return true;
			}
		}
		
		return false;
	}
}
