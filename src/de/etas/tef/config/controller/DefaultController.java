package de.etas.tef.config.controller;

import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;

public class DefaultController implements IController
{

	@Override
	public String[] getBlockNames(boolean isSource)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findConfigBlockIndexInTarget(String blockName)
	{
		// TODO Auto-generated method stub
		return -1;
	}

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
	public void updateParameter(CellIndex cell, String newValue, boolean isSource)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startAcceptSourceParameter()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSourceFilePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSourceFilePath(String sourceFilePaht)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTargetFilePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTargetFilePath(String targetFilePath)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSelectedBlock(String blockName, boolean isSource)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveFile(String targetFilePath, boolean b)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gpibSelected(boolean isSelected) {
		// TODO Auto-generated method stub
		
	}
}
