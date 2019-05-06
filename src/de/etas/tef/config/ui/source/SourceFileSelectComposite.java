package de.etas.tef.config.ui.source;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.ui.core.SelectComposite;

public class SourceFileSelectComposite extends SelectComposite
{

	public SourceFileSelectComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if( Constants.ACTION_DROP_SOURCE_NEW_FILE_SELECTED == type )
		{
			getText().setText(content.toString());
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
		getText().setText(filePath);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_SOURCE_NEW_FILE_SELECTED, getCurrentFilePath());
	}

}
