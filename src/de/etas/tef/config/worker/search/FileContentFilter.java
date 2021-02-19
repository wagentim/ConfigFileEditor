package de.etas.tef.config.worker.search;

import java.util.Iterator;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;

public class FileContentFilter extends AbstractFilter
{
	public FileContentFilter(MainController controller)
	{
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check(ConfigFilePath file, SearchInfo si)
	{
		String section = si.getSectionName();
		String key = si.getKeyName();
		String value = si.getValueName();
		
		boolean isSectionOK = false;
		boolean isKeyOK = false;
		boolean isValueOK = false;
		
		// case 1: no filter defined
		if((section == null || section.isEmpty())
				&& (key == null || key.isEmpty())
				&& (value == null || value.isEmpty())
				)
		{
			return true;
		}
		
		ConfigFile cf = file.getConfigFile();
		
		if(cf == null)
		{
			return true;
		}
		
		Iterator<ConfigBlock> it = cf.getConfigBlocks().iterator();
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			
			if(section == null || section.isEmpty())
			{
				isSectionOK = true;
			}
			else
			{
				isSectionOK = Utils.isContentSame(section, cb.getBlockName());
			}
			
			if(!isSectionOK)
			{
				continue;
			}
			
			Iterator<KeyValuePair> itp = cb.getAllParameters().iterator();
			
			while(itp.hasNext())
			{
				KeyValuePair kvp = itp.next();
				
				if(key == null || key.isEmpty())
				{
					isKeyOK = true;
				}
				else
				{
					isKeyOK = Utils.isContentSame(key, kvp.getKey());
				}
				
				if(value == null || value.isEmpty())
				{
					isValueOK = true;
				}
				else
				{
					isValueOK = Utils.isContentSame(value, kvp.getValue());
				}

				if(isSectionOK && isKeyOK && isValueOK)
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
