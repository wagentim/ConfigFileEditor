package de.etas.tef.config.worker.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.DuplicatedKeyProblem;
import de.etas.tef.config.entity.KeyValuePair;

public class DuplicatedKeyValidator extends AbstractChecker 
{

	public DuplicatedKeyValidator(MainController controller) 
	{
		super(controller);
	}

	@Override
	public void check(ConfigFilePath file) 
	{
		List<ConfigBlock> blocks = file.getConfigFile().getConfigBlocks();
		
		if(blocks == null || blocks.size() < 0 )
		{
			return;
		}
		
		Iterator<ConfigBlock> it = blocks.iterator();
		
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			
			Set<String> checker = new HashSet<String>();
			
			Iterator<KeyValuePair> paras = cb.getAllParameters().iterator();
			List<String> addedKey = new ArrayList<String>();
			
			while(paras.hasNext())
			{
				KeyValuePair kvp = paras.next();
				
				int type = kvp.getType();
				
				if(type == KeyValuePair.TYPE_COMMENT || type == KeyValuePair.TYPE_UNKNOWN)
				{
					continue;
				}
				
				String key = kvp.getKey();
				
				if(checker.add(key) == false && !addedKey.contains(key))
				{
					file.getConfigFile().addProblem(new DuplicatedKeyProblem(cb, key));
				}
			}
		}
	}
}
