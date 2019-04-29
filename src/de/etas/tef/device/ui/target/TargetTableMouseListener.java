package de.etas.tef.device.ui.target;

import org.eclipse.swt.widgets.Table;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.IDHelper;
import de.etas.tef.config.helper.Mapper;
import de.etas.tef.device.ui.core.TableMouseListener;

public class TargetTableMouseListener extends TableMouseListener
{

	public TargetTableMouseListener(Table table, IController controller)
	{
		super(table, controller);
	}
	
	protected String getInfoText() 
	{
		return Mapper.INSTANCE.getText(IDHelper.ID_TARGET_CONFIG_COMPOSITE);
	}
	
	protected ConfigBlock getConfigBlock()
	{
		return getController().getCurrTargetConfigBlock();
	}
	
	protected boolean isSource()
	{
		return false;
	}
}
