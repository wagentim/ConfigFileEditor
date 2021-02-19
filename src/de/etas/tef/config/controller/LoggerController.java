package de.etas.tef.config.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ChangeAction;
import de.etas.tef.config.helper.Utils;
import de.etas.tef.editor.message.MessageManager;

public class LoggerController
{
	private final MainController controller;
	private Path logfile = null;
	
	public LoggerController(final MainController controller)
	{
		this.controller = controller;
	}
	
	public void log(List<ChangeAction> changes)
	{
		if(logfile == null)
		{
			logfile = Paths.get(Utils.getCurrentPath().toFile().getAbsoluteFile() + File.separator + Utils.getTimeStample() + ".log");

			if(!Utils.validFile(logfile, true))
			{
				return;
			}
		}
		
		
		StringBuilder sb = new StringBuilder();
		
		for(ChangeAction ca : changes)
		{
			sb.append(Utils.getTime());
			sb.append("-- Edit File: ");
			sb.append(ca.getFile().toFile().getAbsolutePath());
			sb.append(IConstants.SYMBOL_NEW_LINE);
			
			int type = ca.getType();
			switch(type)
			{
			case IConstants.ADD_SECTION:
				sb.append(Utils.getTime());
				sb.append("Add Section: ");
				sb.append(ca.getNewSection());
				sb.append(IConstants.SYMBOL_NEW_LINE);
				break;
			case IConstants.ADD_KEY_VALUE:
				sb.append(Utils.getTime());
				sb.append(IConstants.SYMBOL_LEFT_BRACKET);
				sb.append(ca.getNewSection());
				sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
				sb.append("Add Key-Value: ");
				sb.append(ca.getNewKey());
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(IConstants.SYMBOL_EQUAL);
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(ca.getNewValue());
				sb.append(IConstants.SYMBOL_NEW_LINE);
				break;
			case IConstants.UPDATE_VALUE:
				sb.append(Utils.getTime());
				sb.append(IConstants.SYMBOL_LEFT_BRACKET);
				sb.append(ca.getOldSection());
				sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
				sb.append("Update Key-Value: ");
				sb.append(ca.getOldKey());
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(IConstants.SYMBOL_EQUAL);
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(ca.getOldValue());
				sb.append(" --> ");
				sb.append(ca.getOldKey());
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(IConstants.SYMBOL_EQUAL);
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(ca.getNewValue());
				sb.append(IConstants.SYMBOL_NEW_LINE);
				break;
			case IConstants.DELET_KEY_VALUE:
				sb.append(Utils.getTime());
				sb.append(IConstants.SYMBOL_LEFT_BRACKET);
				sb.append(ca.getOldSection());
				sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
				sb.append("Delete Key-Value: ");
				sb.append(ca.getOldKey());
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(IConstants.SYMBOL_EQUAL);
				sb.append(IConstants.SYMBOL_SPACE);
				sb.append(ca.getOldValue());
				sb.append(IConstants.SYMBOL_NEW_LINE);
				break;
			case IConstants.UNKNOWN:
				sb.append(Utils.getTime());
				sb.append("Unknown");
				sb.append(IConstants.SYMBOL_NEW_LINE);
			}
			sb.append(IConstants.SYMBOL_NEW_LINE);
		}
		
		MessageManager.INSTANCE.sendMessage(IConstants.ACTION_ADD_LOG, sb.toString());
		
		try
		{
			controller.getIOController().writeStringToFile(logfile, sb.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
