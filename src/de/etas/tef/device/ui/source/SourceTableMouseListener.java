package de.etas.tef.device.ui.source;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.TableMouseListener;

public class SourceTableMouseListener extends TableMouseListener
{

	public SourceTableMouseListener(Table table, IController controller)
	{
		super(table, controller);
	}
	
	protected ConfigBlock getConfigBlock()
	{
		return getController().getCurrSourceConfigBlock();
	}
	
	protected boolean isSource()
	{
		return true;
	}
	
	@Override
	protected void disposeEditor(CellIndex cell, String newValue)
	{
		Control oldEditor = getTableEditor().getEditor();
		
        if (oldEditor != null)
        {
			oldEditor.dispose();
			if( isTextChanged )
			{
				getController().updateParameter(cell, newValue, true);
				isTextChanged = false;
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, "Config Block: " + getConfigBlock().getBlockName() + " with new value: " + newValue + cell.toString());
			}
        }
	}
}
