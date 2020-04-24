package de.etas.tef.config.listener;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.IMessage;
import de.etas.tef.editor.message.MessageManager;

public class FileTreeRightMenuSelectionListener extends DefaultRightMenuSelectionListener
{
	@Override
	protected void handleMenu(String text)
	{
		super.handleMenu(text);
		
		if(text.equals(IConstants.TXT_MENU_ADD_FILE))
		{
			MessageManager.INSTANCE.sendMessage(IMessage.MSG_ADD_DIR, null);
		}
		else if(text.equals(IConstants.txt_menu))
	}
}
