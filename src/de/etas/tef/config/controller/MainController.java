package de.etas.tef.config.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.helper.IIniFileWorker;
import de.etas.tef.config.helper.InitFileWorker;

public class MainController implements IController
{
	private IIniFileWorker worker = null;

	private String leftFilePath = Constants.EMPTY_STRING;
	private String rightFilePath = Constants.EMPTY_STRING;

	private ConfigBlock currLeftConfigBlock = null;
	private ConfigBlock currRightConfigBlock = null;


	private ConfigFile sourceConfigFile = null;
	private ConfigFile targetConfigFile = null;

	private boolean isConnected = false;
	
	public MainController()
	{
		worker = new InitFileWorker();
	}

	private void selectFile(boolean isSource)
	{
		String fp = isSource ? getSourceFilePath() : getTargetFilePath();

		ConfigFile result = new ConfigFile();
		
		try
		{
			worker.readFile(fp, result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (isSource)
		{
			sourceConfigFile = result;
		} 
		else
		{
			targetConfigFile = result;
		}
	}

	@Override
	public String[] getBlockNames(boolean isSource)
	{
		if (isSource)
			return getBlockNames(sourceConfigFile.getConfigBlocks());
		else
			return getBlockNames(targetConfigFile.getConfigBlocks());

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

	public void setSelectedBlock(String blockName, boolean isSource)
	{
		if (isSource)
		{
			currLeftConfigBlock = getConfigBlock(sourceConfigFile, blockName);
		} else
		{
			currRightConfigBlock = getConfigBlock(targetConfigFile, blockName);
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
	public void updateParameter(CellIndex cell, String newValue, boolean isSource)
	{
		ConfigBlock cb;

		if (isSource)
		{
			cb = getCurrSourceConfigBlock();
		} else
		{
			cb = getCurrTargetConfigBlock();
		}

		int row = cell.getRow();
		
		if (row < 0)
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

	public String getSourceFilePath()
	{
		return leftFilePath;
	}

	public void setSourceFilePath(String sourceFilePaht)
	{
		this.leftFilePath = sourceFilePaht;
		selectFile(true);
	}

	public String getTargetFilePath()
	{
		return rightFilePath;
	}

	public void setTargetFilePath(String targetFilePath)
	{
		this.rightFilePath = targetFilePath;
		selectFile(false);
	}

	@Override
	public void saveFile(String filePath, boolean isSource)
	{
		try
		{
			worker.writeFile(filePath, isSource ? sourceConfigFile : rightFilePath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteParameters(int[] selectedItems, String text, boolean isSource)
	{
		if( null == text || text.isEmpty() || null == selectedItems|| selectedItems.length < 1)
		{
			return;
		}
		
		ConfigBlock currBlock = isSource ? currLeftConfigBlock : currRightConfigBlock;
		
		List<KeyValuePair> paras = currBlock.getAllParameters();
		
		for(int i = selectedItems.length - 1; i >= 0 ; i--)
		{
			paras.remove(selectedItems[i]);
		}
	}
}
