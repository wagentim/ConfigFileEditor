package de.etas.tef.config.ui.target;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.ui.core.TableMouseListener;

public class TargetTableMouseListener extends TableMouseListener
{

	public TargetTableMouseListener(Table table, IController controller)
	{
		super(table, controller);
	}
	
	protected ConfigBlock getConfigBlock()
	{
		return getController().getCurrTargetConfigBlock();
	}
	
	protected boolean isSource()
	{
		return false;
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
				getController().parameterChanged(cell, newValue, CompositeID.COMPOSITE_RIGHT);
				isTextChanged = false;
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, getInfoText() + " File Block: " + getConfigBlock().getBlockName() + " with new value: " + newValue + cell.toString());
			}
        }
	}
	
}
