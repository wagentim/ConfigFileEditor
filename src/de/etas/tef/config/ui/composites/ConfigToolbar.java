package de.etas.tef.config.ui.composites;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class ConfigToolbar extends CustomToolbar
{

	public ConfigToolbar(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComponent();
	}
	
	@Override
	protected void initComponent()
	{
		super.initComponent();
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}

}
