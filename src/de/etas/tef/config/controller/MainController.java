package de.etas.tef.config.controller;

import de.etas.tef.config.helper.CompositeID;

public class MainController extends AbstractController
{
	private final IController left;
	private final IController right;
	
	private boolean connected = false;
	
	public MainController()
	{
		this.left = new ConfigFileController();
		this.left.setParent(this);
		this.right = new ConfigFileController();
		this.right.setParent(this);
	}
	
	public IController getController(int compositeID)
	{
		switch (compositeID)
		{
			case CompositeID.COMPOSITE_LEFT:
				return left;
			case CompositeID.COMPOSITE_RIGHT:
				return right;
			case CompositeID.COMPOSITE_ALONE:
			case CompositeID.COMPOSITE_ROOT:
				return this;
		}
		
		return null;
	}
	
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	public boolean isConnected()
	{
		return connected;
	}

}
