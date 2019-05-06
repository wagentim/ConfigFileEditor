package de.etas.tef.config.controller;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;

public class DefaultController implements IController
{

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConnected(boolean isConnected)
	{
		
	}

	@Override
	public void setCurrSourceConfigBlock(ConfigBlock cb)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrTargetConfigBlock(ConfigBlock cb)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfigBlock getCurrTargetConfigBlock()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigBlock getCurrSourceConfigBlock()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInputConfigFile(String filePath, int compositeID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteParameters(int[] selectedItems, String text, int compositeID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFile(String targetFilePath, int compositeID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parameterChanged(CellIndex cell, String newValue, int compositeID)
	{
		
	}

	@Override
	public void setSelectedBlock(String blockName, int compositeID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAllBlocks(int compositeID)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
