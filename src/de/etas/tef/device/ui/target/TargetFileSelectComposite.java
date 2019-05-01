package de.etas.tef.device.ui.target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.SelectComposite;

public class TargetFileSelectComposite extends SelectComposite
{

	protected TargetFileSelectComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_CONNECT_SELECTED)
		{
//			setAllComponentsEnable(!(boolean)content);
		}
	}
	
	protected void initLabel(Composite comp)
	{
		super.initLabel(comp);
		
		getLabel().setForeground(this.getDisplay().getSystemColor(SWT.COLOR_MAGENTA));
	}

	protected void fileSelected() 
	{
		getController().setTargetFilePath(getCurrentFilePath());
		ActionManager.INSTANCE.sendAction(Constants.ACTION_TARGET_NEW_FILE_SELECTED, getCurrentFilePath());
	}
}
