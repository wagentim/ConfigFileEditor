package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class ConfigFileComposite extends AbstractComposite
{
	
	public ConfigFileComposite(Composite composite, int style, MainController controller)
	{
		super(composite, style, controller);
	}
	
	protected void initComposite()
	{
		super.initComposite();
		new SelectComposite(this, SWT.NONE, controller);
		new ConfigFileListComposite(this, SWT.BORDER, controller);
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub
		
	}
}
