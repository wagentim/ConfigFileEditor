package de.etas.tef.device.ui.source;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.BlockComposite;

public class SourceBlockComposite extends BlockComposite
{

	public SourceBlockComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	protected void initLabel(Composite comp)
	{
		super.initLabel(comp);
		
		getLabel().setForeground(this.getDisplay().getSystemColor(SWT.COLOR_BLUE));
	}
	
	protected void comboSelectionListener()
	{
		getController().setSelectedBlock(getCombo().getText(), true);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_SOURCE_PARAMETER_UPDATE, getController().getCurrSourceConfigBlock());
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_SOURCE_NEW_FILE_SELECTED )
		{
			String[] allBlocks = getController().getBlockNames(true);
			getCombo().setItems(allBlocks);
		}
		else if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_START)
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
			
			if(false == (boolean)content)
			{
				comboSelectionListener();
			}
		}
		
	}
	
}
