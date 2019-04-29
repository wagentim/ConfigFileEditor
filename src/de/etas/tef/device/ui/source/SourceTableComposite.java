package de.etas.tef.device.ui.source;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.core.TableComposite;

public class SourceTableComposite extends TableComposite
{

	public SourceTableComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void receivedAction(int type, Object content)
	{
		if( type == Constants.ACTION_SOURCE_PARAMETER_UPDATE )
		{
			ConfigBlock cb = getController().getCurrSourceConfigBlock();
			if( null != cb )
			{
				updateParameters(cb.getAllParameters());
			}
			else
			{
				updateParameters(Collections.emptyList());
			}
		}
		else if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_START)
		{
			setAllComponentsEnable(false);
		}
		else if( type == Constants.ACTION_TAKE_SOURCE_PARAMETERS_FINISHED)
		{
			setAllComponentsEnable(true);
		}
		else if( type == Constants.ACTION_SOURCE_NEW_FILE_SELECTED )
		{
			clearTable();
		}
		else if (type == Constants.ACTION_GPIB_SELECTED)
		{
			setAllComponentsEnable(!(boolean)content);
			getController().gpibSelected((boolean)content);
		}
		
		else if (type == Constants.ACTION_GPIB_SOURCE_FINISHED)
		{
			updateParameters((List<KeyValuePair>) content);
		}
		 
	}
	
	protected void addTableMouseListener() 
	{
		getTable().addMouseListener(new SourceTableMouseListener(getTable(), getController()));
	}
	
	protected void saveAction(String targetFilePath) 
	{
		getController().saveFile(targetFilePath, true);
		
		ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, "Source Write to: " + targetFilePath + "finished!");
	}
}
