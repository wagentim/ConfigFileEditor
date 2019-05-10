package de.etas.tef.config.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.helper.IConfigFileWorker;
import de.etas.tef.config.helper.InitFileWorker;
import de.etas.tef.config.helper.Validator;

public class ConfigFileController extends AbstractController
{
	// worker is used to parser the file and also save the content to the file
	private IConfigFileWorker worker = null;
	
	// input file
	private ConfigFile configFile = null;
	private ConfigBlock selectedConfigBlock = null;
	private List<ConfigBlock> showConfigBlocks = null;

	public ConfigFileController()
	{
		worker = new InitFileWorker();
		showConfigBlocks = new ArrayList<ConfigBlock>();
	}
	
	@Override
	public void setInputConfigFile(String filePath)
	{
		if( !Validator.INSTANCE().validFile(filePath, false) )
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, CompositeID.COMPOSITE_ALONE,  "Input File is Wrong!!");
			return;
		}
		
		configFile = parserConfigFile(filePath);
	}

	private ConfigFile parserConfigFile(String inputFile)
	{
		ConfigFile result = new ConfigFile();
		
		try
		{
			worker.readFile(inputFile, result);
		}
		catch (IOException e)
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, CompositeID.COMPOSITE_ALONE, "I/O Error by parsing inputFile: " + inputFile);
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String[] getAllBlocks()
	{
		return getBlockNames(configFile.getConfigBlocks());
	}

	@Override
	public void setSelectedBlock(String blockName)
	{
		
		if( null == configFile )
		{
			return;
		}

		List<ConfigBlock> blocks = configFile.getConfigBlocks();

		for (int i = 0; i < blocks.size(); i++)
		{
			if (blockName.equals(blocks.get(i).getBlockName()))
			{
				selectedConfigBlock = blocks.get(i);
				break;
			}
		}
	}

	@Override
	public void parameterChanged(CellIndex cell, String newValue)
	{
		ConfigBlock cb = getSelectedConfigBlock();
		
		int row = cell.getRow();
		
		if (row < 0 || null == cb)
		{
			return;
		}

		KeyValuePair pair = cb.getParameter(cell.getRow());

		int column = cell.getColumn();

		switch (column)
		{
		case 0:
			pair.setKey(newValue);
			break;
		case 1:
			pair.setValue(newValue);
			break;
		case 2:
			pair.setOther(newValue);
			break;
		}
	}

	public ConfigBlock getSelectedConfigBlock()
	{
		return selectedConfigBlock;
	}

	@Override
	public void saveFile(String filePath)
	{
		try
		{
			worker.writeFile(filePath, configFile);
		}
		catch (IOException e)
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, CompositeID.COMPOSITE_ALONE, "Error by writing data to file: " + filePath);
			e.printStackTrace();
		}
	}

	@Override
	public void deleteParameters(int[] selectedItems, String text)
	{
		if( null == text || text.isEmpty() || null == selectedItems|| selectedItems.length < 1)
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, CompositeID.COMPOSITE_ALONE, "ERROR by deleting the parameters");
			return;
		}
		
		ConfigBlock currBlock = selectedConfigBlock;
		
		List<KeyValuePair> paras = currBlock.getAllParameters();
		
		for(int i = selectedItems.length - 1; i >= 0 ; i--)
		{
			paras.remove(selectedItems[i]);
		}
	}

	@Override
	public void setShowConfigBlocks(String text)
	{
		if( null == text )
		{
			return;
		}

		List<ConfigBlock> blocks = configFile.getConfigBlocks();
		showConfigBlocks.clear();

		for (int i = 0; i < blocks.size(); i++)
		{
			if (blocks.get(i).getBlockName().toLowerCase().contains(text.toLowerCase()))
			{
				showConfigBlocks.add(blocks.get(i));
			}
		}
	}

	@Override
	public String[] getShowConfigBlocks()
	{
		return getBlockNames(showConfigBlocks);
	}

	private String[] getBlockNames(List<ConfigBlock> blocks)
	{
		if (null == blocks || blocks.isEmpty())
		{
			return Constants.EMPTY_STRING_ARRAY;
		}

		String[] result = new String[blocks.size()];

		for (int i = 0; i < blocks.size(); i++)
		{
			result[i] = blocks.get(i).getBlockName();
		}

		Arrays.sort(result);

		return result;
	}

	
}
