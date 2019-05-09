package de.etas.tef.config.controller;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;

public abstract class AbstractController implements IController
{
	private IController parent = null;
	
	@Override
	public IController getParent()
	{
		return parent;
	}

	@Override
	public void setParent(IController parent)
	{

	}
	
	@Override
	public void setInputConfigFile(String filePath)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteParameters(int[] selectedItems, String text)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFile(String targetFilePath)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parameterChanged(CellIndex cell, String newValue)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ConfigBlock getSelectedConfigBlock()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAllBlocks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedBlock(String blockName)
	{
		// TODO Auto-generated method stub
		
	}

}
