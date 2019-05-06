package de.etas.tef.config.controller;

import javax.management.RuntimeErrorException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;

public class InfoBlockWriter implements IActionListener
{
	private final Text infoBlock;
	private final Text commentBlock;
	private final Color error;
	private final Color info;
	private final IController controller;

	public InfoBlockWriter(final Text infoBlock, final Text commentBlock, IController controller)
	{
		if (null == infoBlock)
		{
			throw new RuntimeErrorException(null, "InfoBlock is null");
		}
		this.infoBlock = infoBlock;
		this.error = infoBlock.getDisplay().getSystemColor(SWT.COLOR_RED);
		this.info = infoBlock.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.controller = controller;
		this.commentBlock = commentBlock;
		ActionManager.INSTANCE.addActionListener(this);
	}

	private void logError(String text)
	{
		infoBlock.setForeground(error);
		infoBlock.append("[ERRO] " + text + "\n");
	}

	private void logInfo(String text)
	{
		infoBlock.setForeground(info);
		infoBlock.append("[INFO] " + text + "\n");
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if ((type == Constants.ACTION_LOG_WRITE_INFO))
		{
			logInfo(content.toString());
		} 
		else if (type == Constants.ACTION_LOG_WRITE_ERROR)
		{
			logError(content.toString());
		}
		else if(type == Constants.ACTION_SOURCE_NEW_FILE_SELECTED)
		{
			logInfo("Set Source File: " + content.toString());
			logInfo("New Source Parameter Blocks: " + controller.getAllBlocks(CompositeID.COMPOSITE_LEFT).length);
		}
		else if (type == Constants.ACTION_TARGET_NEW_FILE_SELECTED)
		{
			logInfo("Set Target File: " + content.toString());
			logInfo("New Target Parameter Blocks: " + controller.getAllBlocks(CompositeID.COMPOSITE_RIGHT).length);
		}
		else if (type == Constants.ACTION_SOURCE_PARAMETER_UPDATE)
		{
			ConfigBlock cb = controller.getCurrSourceConfigBlock();
			if(null == cb)
			{
				return;
			}
			logInfo("Select Source Block: " + cb.getBlockName() + " : Parameter Number: " + cb.getAllParameters().size());
		}
		else if (type == Constants.ACTION_TARGET_PARAMETER_UPDATE)
		{
			ConfigBlock cb = controller.getCurrTargetConfigBlock();
			logInfo("Select Target Block: " + cb.getBlockName() + " : Parameter Number: " + cb.getAllParameters().size());
		}
		else if (type == Constants.ACTION_CONNECT_SELECTED)
		{
			if ((boolean)content)
			{
				logInfo("Source File is Connected to Target File");
			}
			else
			{
				logInfo("Source File is Disconnected to Target File");
			}
		}
		else if (type == Constants.ACTION_BLOCK_SELECTED)
		{
			commentBlock.setText(((ConfigBlock)content).getComments());
		}
	}
}
