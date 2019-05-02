package de.etas.tef.config.controller;

import java.io.IOException;
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
	private String sourceFilePath = Constants.EMPTY_STRING;
	private String targetFilePath = Constants.EMPTY_STRING;

	private ConfigBlock currSourceConfigBlock = null;
	private ConfigBlock currTargetConfigBlock = null;

	private IIniFileWorker worker = null;

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
			currSourceConfigBlock = getConfigBlock(sourceConfigFile, blockName);
		} else
		{
			currTargetConfigBlock = getConfigBlock(targetConfigFile, blockName);
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
		this.currSourceConfigBlock = cb;
	}

	@Override
	public void setCurrTargetConfigBlock(ConfigBlock cb)
	{
		this.currTargetConfigBlock = cb;
	}

	public ConfigBlock getCurrSourceConfigBlock()
	{
		return currSourceConfigBlock;
	}

	public ConfigBlock getCurrTargetConfigBlock()
	{
		return currTargetConfigBlock;
	}

	public String getSourceFilePath()
	{
		return sourceFilePath;
	}

	public void setSourceFilePath(String sourceFilePaht)
	{
		this.sourceFilePath = sourceFilePaht;
		selectFile(true);
	}

	public String getTargetFilePath()
	{
		return targetFilePath;
	}

	public void setTargetFilePath(String targetFilePath)
	{
		this.targetFilePath = targetFilePath;
		selectFile(false);
	}

	@Override
	public void saveFile(String filePath, boolean isSource)
	{
		try
		{
			worker.writeFile(filePath, isSource ? sourceConfigFile : targetFilePath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
