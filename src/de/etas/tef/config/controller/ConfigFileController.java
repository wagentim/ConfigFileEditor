package de.etas.tef.config.controller;

import java.io.IOException;
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

public class ConfigFileController implements IController
{
	private IConfigFileWorker worker = null;

	private String leftFilePath = Constants.EMPTY_STRING;
	private String rightFilePath = Constants.EMPTY_STRING;

	private ConfigBlock currLeftConfigBlock = null;
	private ConfigBlock currRightConfigBlock = null;

	private ConfigFile leftConfigFile = null;
	private ConfigFile rightConfigFile = null;

	// define is left composite is connected to right composite
	private boolean isConnected = false;
	
	public ConfigFileController()
	{
		worker = new InitFileWorker();
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
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String[] getAllBlocks(int compositeID)
	{
		switch(compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				return getBlockNames(leftConfigFile.getConfigBlocks());
			case CompositeID.COMPOSITE_RIGHT:
				return getBlockNames(rightConfigFile.getConfigBlocks());
		}
		return Constants.EMPTY_STRING_ARRAY;
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

	@Override
	public void setSelectedBlock(String blockName, int compositeID)
	{
		
		switch(compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				currLeftConfigBlock = getConfigBlock(leftConfigFile, blockName);
				break;
			case CompositeID.COMPOSITE_RIGHT:
				currRightConfigBlock = getConfigBlock(rightConfigFile, blockName);
				break;
		}
	}

	private ConfigBlock getConfigBlock(ConfigFile cf, String blockName)
	{
		ConfigBlock cb = null;
		
		if( null == cf )
		{
			return null;
		}

		List<ConfigBlock> blocks = cf.getConfigBlocks();

		for (int i = 0; i < blocks.size(); i++)
		{
			if (blockName.equals(blocks.get(i).getBlockName()))
			{
				cb = blocks.get(i);
				break;
			}
		}

		return cb;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}

	@Override
	public void parameterChanged(CellIndex cell, String newValue, int compositeID)
	{
		ConfigBlock cb = null;

		switch(compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				cb = getCurrSourceConfigBlock();
				break;
			case CompositeID.COMPOSITE_RIGHT:
				cb = getCurrTargetConfigBlock();
				break;
		}
		
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

	@Override
	public void setCurrSourceConfigBlock(ConfigBlock cb)
	{
		this.currLeftConfigBlock = cb;
	}

	@Override
	public void setCurrTargetConfigBlock(ConfigBlock cb)
	{
		this.currRightConfigBlock = cb;
	}

	public ConfigBlock getCurrSourceConfigBlock()
	{
		return currLeftConfigBlock;
	}

	public ConfigBlock getCurrTargetConfigBlock()
	{
		return currRightConfigBlock;
	}

	@Override
	public void saveFile(String filePath, int compositeID)
	{
		try
		{
			switch(compositeID)
			{
				case CompositeID.COMPOSITE_LEFT:
					worker.writeFile(filePath, leftFilePath);
					break;
				case CompositeID.COMPOSITE_RIGHT:
					worker.writeFile(filePath, rightFilePath);
					break;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteParameters(int[] selectedItems, String text, int compositeID)
	{
		if( null == text || text.isEmpty() || null == selectedItems|| selectedItems.length < 1)
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR by deleting the parameters");
			return;
		}
		
		ConfigBlock currBlock = null;
		
		switch(compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				currBlock = currLeftConfigBlock;
				break;
			case CompositeID.COMPOSITE_RIGHT:
				currBlock = currRightConfigBlock;
				break;
		}
		
		List<KeyValuePair> paras = currBlock.getAllParameters();
		
		for(int i = selectedItems.length - 1; i >= 0 ; i--)
		{
			paras.remove(selectedItems[i]);
		}
	}

	@Override
	public void setInputConfigFile(String filePath, int compositeID)
	{
		if( !Validator.INSTANCE().validFile(filePath, false) )
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "Input File is Wrong!!");
			return;
		}
		
		ConfigFile configFile = parserConfigFile(filePath);
		
		switch(compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				this.leftFilePath = filePath;
				this.leftConfigFile = configFile;
				break;
			case CompositeID.COMPOSITE_RIGHT:
				this.rightFilePath = filePath;
				this.rightConfigFile = configFile;
				break;
		}
	}
}
