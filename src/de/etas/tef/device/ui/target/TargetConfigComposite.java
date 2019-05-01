package de.etas.tef.device.ui.target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.IController;
import de.etas.tef.device.ui.core.ConfigComposite;

public class TargetConfigComposite extends ConfigComposite
{

	public TargetConfigComposite(Composite composite, int style, IController controller)
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
		new TargetFileSelectComposite(this, SWT.NONE, getController());
//		new TargetBlockComposite(this, SWT.NONE, getController());
		new TargetTableComposite(this, SWT.NONE, getController());
	}

}
