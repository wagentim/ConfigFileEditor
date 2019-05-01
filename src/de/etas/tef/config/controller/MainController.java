package de.etas.tef.config.controller;

import java.util.Iterator;
import java.util.List;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.helper.IWorker;
import de.etas.tef.config.helper.InitFileWorker;

public class MainController implements IController
{
	private String sourceFilePath = Constants.EMPTY_STRING;
	private String targetFilePath = Constants.EMPTY_STRING;

	private ConfigBlock currSourceConfigBlock = null;
	private ConfigBlock currTargetConfigBlock = null;

	private IWorker worker = null;

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

		ConfigFile result = worker.read(fp);

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

	@Override
	public int findConfigBlockIndexInTarget(String blockName)
	{
		int result = -1;

//		if( !targetConfigBlocks.containsKey(blockName) )
//			return result;
//		else
//		{
//			for (String entry : targetConfigBlocks.keySet()) 
//			{
//				result++;
//			     if (blockName.equals(entry)) 
//			     {
//			    	 break;
//			     }
//			}
//		}
		return result;
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

	@Override
	public void startAcceptSourceParameter()
	{
//		try
//		{
//			ConfigBlock cb = currSourceConfigBlock.clone();
//			targetConfigBlocks.put(cb.getBlockName(), cb);
//			currTargetConfigBlock = cb;
//			ActionManager.INSTANCE.sendAction(Constants.ACTION_TAKE_SOURCE_PARAMETERS_FINISHED, null);
//		} catch (CloneNotSupportedException e)
//		{
//			e.printStackTrace();
//		}
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
	public void saveFile(String targetFilePath, boolean isSource)
	{
//		Map<String, ConfigBlock> saveConfigBlocks = isSource ? sourceConfigBlocks : targetConfigBlocks;

//		worker.write(targetFilePath,saveConfigBlocks);

	}

	@Override
	public void gpibSelected(boolean isSelected)
	{
		if (!isSelected)
		{
			return;
		}

//		if (null == sourceConfigBlocks || sourceConfigBlocks.isEmpty())
//		{
//			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "[GPIB] Source File is Empty!");
//			return;
//		} else
//		{
//			ConfigBlock deviceName = sourceConfigBlocks.get(Constants.TXT_GPIB_DEVICE_NAME);
//
//			if (null == deviceName)
//			{
//				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "[GPIB] Cannot find DEVICE_NAME!");
//				return;
//			}
//
//			ConfigBlock pmNumber = sourceConfigBlocks.get(Constants.TXT_GPIB_PM_NUMBER);
//
//			if (null == pmNumber)
//			{
//				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "[GPIB] Cannot find PM_NUMBER!");
//				return;
//			}
//
//			ConfigBlock gpibAdress = sourceConfigBlocks.get(Constants.TXT_GPIB_ADRESS);
//
//			if (null == gpibAdress)
//			{
//				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "[GPIB] Cannot find GPIB ADRESS!");
//				return;
//			}
//
//			List<KeyValuePair> gpibs = new ArrayList<KeyValuePair>();
//
//			Iterator<KeyValuePair> it = deviceName.getAllParameters().iterator();
//
//			while (it.hasNext())
//			{
//				KeyValuePair gpib = new KeyValuePair();
//
//				KeyValuePair p = it.next();
//				String device = p.getKey();
//				gpib.setKey(device);
//				gpib.setValue(p.getValue());
//				String tmp = getValue(device, pmNumber.getAllParameters());
//
//				if (null == tmp || tmp.isEmpty())
//				{
//					ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR,
//							"[GPIB] Cannot find PM Number for: " + device);
//					continue;
//				}
//
//				gpib.setOther(tmp);
//
//				tmp = getValue(device, gpibAdress.getAllParameters());
//
//				if (null == tmp || tmp.isEmpty())
//				{
//					ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR,
//							"[GPIB] Cannot find GPIB ADRESS for: " + device);
//					continue;
//				}
//
//				gpib.setForthValue(tmp);
//
//				gpibs.add(gpib);
//			}
//
//			ActionManager.INSTANCE.sendAction(Constants.ACTION_GPIB_SOURCE_FINISHED, gpibs);
//		}
	}

	private String getValue(String device, List<KeyValuePair> allParameters)
	{
		Iterator<KeyValuePair> it = allParameters.iterator();

		while (it.hasNext())
		{
			KeyValuePair p = it.next();

			if (device.equals(p.getKey()))
			{
				return p.getValue();
			}
		}
		return null;
	}

}
