package de.etas.tef.config.listener;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IMessage;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.editor.message.MessageManager;

public class GitTreeMouseListener implements MouseListener
{

	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		Object src = event.getSource();
		
		if( src instanceof Tree )
		{
			TreeItem item = ((Tree)src).getSelection()[0];
			
			if( null == item )
			{
				return;
			}
			
			int type = (int)(item).getData(IConstants.DATA_TYPE);
			
			switch(type)
			{
			case IConstants.DATA_TYPE_DIR:
				item.setExpanded(true);
				break;
			case IConstants.DATA_TYPE_FILE:
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_FILE, item.getData(IConstants.DATA_PATH));
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
