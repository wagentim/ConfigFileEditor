package de.etas.tef.device.ui.source;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.SelectComposite;

public class SourceFileSelectComposite extends SelectComposite
{

	public SourceFileSelectComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_START)
		{
			setAllComponentsEnable(false);
		}
		else if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_FINISHED)
		{
			setAllComponentsEnable(true);
		}
		else if (type == Constants.ACTION_GPIB_SELECTED)
		{
			setAllComponentsEnable(!(boolean)content);
		}
	}
	
	protected void initLabel(Composite comp)
	{
		super.initLabel(comp);
		getLabel().setForeground(this.getDisplay().getSystemColor(SWT.COLOR_BLUE));
	}
	
	protected void fileSelected() 
	{
		String filePath = getCurrentFilePath();
		
		if( null == filePath || filePath.isEmpty() )
		{
			return;
		}
		
		getController().setSourceFilePath(filePath);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_SOURCE_NEW_FILE_SELECTED, getCurrentFilePath());
	}
}
