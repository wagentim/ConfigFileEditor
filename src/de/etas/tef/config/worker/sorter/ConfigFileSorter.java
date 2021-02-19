package de.etas.tef.config.worker.sorter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;

public class ConfigFileSorter
{
	private final MainController controller;

	public ConfigFileSorter(final MainController controller)
	{
		this.controller = controller;
	}

	/**
	 * Sort the {@code ConfigBlock} same as Template
	 * 
	 * @param blocks
	 * @return
	 */
	public List<ConfigBlock> sortConfigBlock(List<ConfigBlock> blocks)
	{
		ConfigFilePath template = controller.getCurrentTemplate();

		if (blocks == null || blocks.isEmpty() || template == null)
		{
			return blocks;
		}

		ConfigFile tempConfigFile = template.getConfigFile();
		List<ConfigBlock> tempBlocks = tempConfigFile.getConfigBlocks();

		if (tempBlocks == null || tempBlocks.isEmpty())
		{
			return blocks;
		}

		List<ConfigBlock> result = new ArrayList<ConfigBlock>();
		List<String> tempBlockName = getTemplateBlockNames(tempConfigFile);
		Iterator<String> itTempBlockName = tempBlockName.iterator();

		while (itTempBlockName.hasNext())
		{
			String bk = itTempBlockName.next();

			List<ConfigBlock> find = findConfigBlock(bk, blocks);
			if (find == null || find.isEmpty())
			{
				continue;
			} else
			{
				for (ConfigBlock cb : find)
				{
					result.add(cb);
				}
			}
		}

		handleExcpteTempBlocks(tempBlockName, blocks, result);

		return result;
	}

	public List<KeyValuePair> sortParameters(String blockName, List<KeyValuePair> paras)
	{
		ConfigFilePath template = controller.getCurrentTemplate();

		if (paras == null || paras.isEmpty() || template == null)
		{
			return paras;
		}

		ConfigFile tmpFile = template.getConfigFile();

		List<ConfigBlock> findBlock = tmpFile.findConfigBlock(blockName);

		if (!Utils.validList(findBlock))
		{
			return paras;
		}

		List<KeyValuePair> para = findBlock.get(0).getAllParameters();

		if (!Utils.validList(para))
		{
			return paras;
		}
		
		return sortParas(para, paras);

	}

	private List<KeyValuePair> sortParas(List<KeyValuePair> tempPara, List<KeyValuePair> inputParas)
	{
		List<KeyValuePair> result = new ArrayList<KeyValuePair>();
		
		Iterator<KeyValuePair> it = tempPara.iterator();
		List<String> keys = new ArrayList<String>();
		
		while(it.hasNext())
		{
			KeyValuePair tempkvp = it.next();
			keys.add(tempkvp.getKey());
			List<KeyValuePair> findValues = findKeyValuePair(tempkvp.getKey(), inputParas);
			
			if(Utils.validList(findValues))
			{
				for(KeyValuePair k : findValues)
				{
					result.add(k);
				}
			}
		}
		
		handleRestParas(keys, inputParas, result);
		
		return result;
	}
	
	private void handleRestParas(List<String> tempKeys, List<KeyValuePair> inputParas, List<KeyValuePair> result)
	{
		Iterator<KeyValuePair> it = inputParas.iterator();
		
		while(it.hasNext())
		{
			KeyValuePair para = it.next();
			
			if(!tempKeys.contains(para.getKey()))
			{
				result.add(para);
			}
		}
	}
	
	private List<KeyValuePair> findKeyValuePair(String keyName, List<KeyValuePair> inputParas)
	{
		if(!Utils.validList(inputParas))
		{
			return null;
		}
		
		List<KeyValuePair> result = new ArrayList<KeyValuePair>();
		
		Iterator<KeyValuePair> it = inputParas.iterator();
		
		while(it.hasNext())
		{
			KeyValuePair kvp = it.next();
			
			if(kvp.getKey().equalsIgnoreCase(keyName))
			{
				result.add(kvp);
			}
		}
		
		return result;
	}

	private void handleExcpteTempBlocks(List<String> tempBlockName, List<ConfigBlock> blocks, List<ConfigBlock> result)
	{
		Iterator<ConfigBlock> origBlocks = blocks.iterator();
		while (origBlocks.hasNext())
		{
			ConfigBlock cb = origBlocks.next();

			if (!tempBlockName.contains(cb.getBlockName()))
			{
				result.add(cb);
			}
		}
	}

	private List<String> getTemplateBlockNames(ConfigFile tempConfigFile)
	{
		List<String> tempBlocks = new ArrayList<String>();
		Iterator<ConfigBlock> itTempBlock = tempConfigFile.getConfigBlocks().iterator();

		while (itTempBlock.hasNext())
		{
			tempBlocks.add(itTempBlock.next().getBlockName());
		}

		return tempBlocks;

	}

	private List<ConfigBlock> findConfigBlock(String name, List<ConfigBlock> blocks)
	{
		Iterator<ConfigBlock> it = blocks.iterator();
		List<ConfigBlock> result = new ArrayList<ConfigBlock>();
		while (it.hasNext())
		{
			ConfigBlock cb = it.next();
			if (name.equals(cb.getBlockName()))
			{
				result.add(cb);
			}
		}

		return result;
	}
}
