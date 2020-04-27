package de.etas.tef.config.listener;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.MenuItem;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.IMessage;
import de.etas.tef.editor.message.MessageManager;

public class GitTreeMouseListener implements MouseListener
{

	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		Object src = event.getSource();
		
		if( src instanceof MenuItem )
		{
			MenuItem item = (MenuItem)src;
			
			int type = (int)(item).getData(IConstants.DATA_TYPE);
			
			switch(type)
			{
			case IConstants.DATA_TYPE_DIR:
				item.setEnabled(true);
				break;
			case IConstants.DATA_TYPE_FILE:
				MessageManager.INSTANCE.sendMessage(IMessage.MSG_SET_FILE, item.getData(IConstants.DATA_PATH));
				
			}
		}
	}

	@Override
	public void mouseDown(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
