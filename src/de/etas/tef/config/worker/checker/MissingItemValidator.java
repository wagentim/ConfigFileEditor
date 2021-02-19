package de.etas.tef.config.worker.checker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.entity.MissingBlockProblem;
import de.etas.tef.config.entity.MissingKeyProblem;

public class MissingItemValidator extends AbstractChecker
{

	public MissingItemValidator(MainController controller)
	{
		super(controller);
	}

	@Override
	public void check(ConfigFilePath file)
	{
		ConfigFilePath template = controller.getCurrentTemplate();
		
		if(template == null)
		{
			return;
		}
		
		ConfigFile cf = file.getConfigFile();
		
		if( cf == null )
		{
			return;
		}
		
		checkBlock(template.getConfigFile(), cf);
	}
	
	private void checkBlock(ConfigFile template, ConfigFile cf)
	{
		Map<String, List<String>> mustHave = template.getMustHaveElement();
		
		if(mustHave == null || mustHave.isEmpty())
		{
			return;
		}
		
		Iterator<String> sets = mustHave.keySet().iterator();
		while(sets.hasNext())
		{
			String block = sets.next();
			List<String> keys = mustHave.get(block);
			check(block, keys, cf);
		}
	}

	private void check(String block, List<String> keys, ConfigFile cf)
	{
		Iterator<ConfigBlock> it = cf.getConfigBlocks().iterator();
		
		boolean findBlock = false;
		
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			
			if(cb.getBlockName().trim().equals(block.trim()))
			{
				findBlock = true;
				
				for(String key : keys)
				{
					if(!checkKey(key, cb.getAllParameters()))
					{
						cf.addProblem(new MissingKeyProblem(cb.getBlockName(), key));
					}
				}
			}
		}
		
		if(!findBlock)
		{
			cf.addProblem(new MissingBlockProblem(block));
		}
	}

	private boolean checkKey(String key, List<KeyValuePair> allParameters)
	{
		Iterator<KeyValuePair> it = allParameters.iterator();
		while(it.hasNext())
		{
			KeyValuePair kvp = it.next();
			if(key.trim().equals(kvp.getKey().trim()))
			{
				return true;
			}
		}
		return false;
	}

}
