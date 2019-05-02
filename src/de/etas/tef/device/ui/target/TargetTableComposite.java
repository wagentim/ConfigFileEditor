package de.etas.tef.device.ui.target;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.TableComposite;

public class TargetTableComposite extends TableComposite
{

	public TargetTableComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}
	
	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_TARGET_PARAMETER_UPDATE )
		{
			ConfigBlock cb = getController().getCurrTargetConfigBlock();
			updateParameters(cb.getAllParameters());
			
		}
		else if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_FINISHED )
		{
			ConfigBlock cb = getController().getCurrTargetConfigBlock();
			updateParameters(cb.getAllParameters());
			getController().setCurrTargetConfigBlock(cb);
		}
		else if( type == Constants.ACTION_TARGET_NEW_FILE_SELECTED )
		{
			clearTable();
			String[] allBlocks = getController().getBlockNames(false);
			setBlockList(allBlocks);
		}
		else if( type == Constants.ACTION_SOURCE_PARAMETER_SELECTED )
		{
			String blockName = (String)content;
			getController().setSelectedBlock(blockName, false);
			setTreeSelectedBlock(blockName);
			treeItemSelected(blockName);
		}
		
		
	}
	
	protected void treeItemSelected(String blockName)
	{
		
		getController().setSelectedBlock(blockName, false);
		ConfigBlock cb = getController().getCurrTargetConfigBlock();
		
		if( null != cb)
		{
			updateParameters(getController().getCurrTargetConfigBlock().getAllParameters());
		}
	}
	
	protected void addTableMouseListener() 
	{
		getTable().addMouseListener(new TargetTableMouseListener(getTable(), getController()));
	}
	
	protected void saveAction(String targetFilePath) 
	{
		getController().saveFile(targetFilePath, false);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, "Target Write to: " + targetFilePath + "finished!");
	}

	@Override
	protected void addTableSelectedListener()
	{
		
	}
}
