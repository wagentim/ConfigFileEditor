package de.etas.tef.config.worker.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.DuplicatedSectionProblem;

public class DuplicatedSectionValidator extends AbstractChecker 
{

	public DuplicatedSectionValidator(MainController controller) 
	{
		super(controller);
	}

	@Override
	public void check(ConfigFilePath file) 
	{
		checkDuplicatedSection(file);
	}

	private void checkDuplicatedSection(ConfigFilePath cf) 
	{
		List<ConfigBlock> blocks = cf.getConfigFile().getConfigBlocks();
		
		if(blocks != null && !blocks.isEmpty())
		{
			checkDuplicated(blocks, cf);
		}
	}

	private void checkDuplicated(List<ConfigBlock> blocks, ConfigFilePath file) 
	{
		Set<String> s = new HashSet<String>();
		List<String> addedProblem = new ArrayList<String>();
		
		for(ConfigBlock cb : blocks)
		{
			String str = cb.getBlockName();
			
			if(s.add(str) == false && !addedProblem.contains(str))
			{
				file.getConfigFile().addProblem(new DuplicatedSectionProblem(str));
			}
		}
	}
	

}
