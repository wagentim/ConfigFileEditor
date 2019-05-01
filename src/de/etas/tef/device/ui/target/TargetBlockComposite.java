package de.etas.tef.device.ui.target;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.BlockComposite;

public class TargetBlockComposite extends BlockComposite
{

	public TargetBlockComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initLabel(Composite comp)
	{
		super.initLabel(comp);
		
		getLabel().setForeground(this.getDisplay().getSystemColor(SWT.COLOR_MAGENTA));
	}
	
	protected void comboSelectionListener()
	{
		getController().setSelectedBlock(getCombo().getText(), false);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_TARGET_PARAMETER_UPDATE, null);
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_TARGET_NEW_FILE_SELECTED )
		{
			String[] allBlocks = getController().getBlockNames(false);
			getCombo().setItems(allBlocks);
		}
		else if( (type == Constants.ACTION_CONNECT_SELECTED) )
		{
//			boolean value = (boolean)content;
//			getCombo().setEnabled(!value);
		}
		else if( getController().isConnected() && type == Constants.ACTION_SOURCE_PARAMETER_UPDATE )
		{
			ConfigBlock block = getController().getCurrSourceConfigBlock();
			int index = getController().findConfigBlockIndexInTarget(block.getBlockName());
			if( index < 0 )
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "In Target File CANNOT find: " + block.getBlockName());
			}
			else
			{
				getCombo().select(index);
				getController().setSelectedBlock(block.getBlockName(), false);
				ActionManager.INSTANCE.sendAction(Constants.ACTION_TARGET_PARAMETER_UPDATE, null);
			}
		}
	}

}
