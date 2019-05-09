package de.etas.tef.config.controller;

import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.CompositeID;

public class MainController
{
	private final IController left;
	private final IController right;
	
	public MainController()
	{
		this.left = new ConfigFileController();
		this.right = new ConfigFileController();
	}
	
	public String[] getAllBlocks(int compositeID)
	{
		IController controller = getController(compositeID);
		return controller.getAllBlocks();
	}
	
	public ConfigBlock getSelectedConfigBlock(int compositeID)
	{
		IController controller = getController(compositeID);
		return controller.getSelectedConfigBlock();
	}
	
	private IController getController(int compositeID)
	{
		switch (compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				return left;
			case CompositeID.COMPOSITE_RIGHT:
				return right;
		}
		
		return null;
	}
}
