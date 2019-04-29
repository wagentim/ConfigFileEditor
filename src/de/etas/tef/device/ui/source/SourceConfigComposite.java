package de.etas.tef.device.ui.source;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.IController;
import de.etas.tef.device.ui.core.ConfigComposite;

public class SourceConfigComposite extends ConfigComposite
{
	
	public SourceConfigComposite(Composite composite, int style, IController controller)
	{
		super(composite, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

	@Override
	protected void initComponent()
	{
		new SourceFileSelectComposite(this, SWT.NONE, getController());
		new SourceBlockComposite(this, SWT.NONE, getController());
		new SourceTableComposite(this, SWT.NONE, getController());
		
	}
}
