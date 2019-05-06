package de.etas.tef.config.ui.target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.ui.core.ConfigComposite;

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

	@Override
	protected void setFilePath(String file)
	{
		getController().setTargetFilePath(file);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_DROP_TARGET_NEW_FILE_SELECTED, file);
	}

}
