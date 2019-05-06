package de.etas.tef.config.ui.target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.ui.core.SelectComposite;

public class TargetFileSelectComposite extends SelectComposite
{

	protected TargetFileSelectComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	public void receivedAction(int type, Object content)
	{
		if( Constants.ACTION_DROP_TARGET_NEW_FILE_SELECTED == type )
		{
			getText().setText(content.toString());
		}
	}
	
	protected void initLabel(Composite comp)
	{
		super.initLabel(comp);
		
		getLabel().setForeground(this.getDisplay().getSystemColor(SWT.COLOR_MAGENTA));
	}

	protected void fileSelected() 
	{
		String filePath = getCurrentFilePath();
		getController().setTargetFilePath(filePath);
		getText().setText(filePath);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_TARGET_NEW_FILE_SELECTED, getCurrentFilePath());
	}

}