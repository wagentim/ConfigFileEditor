package de.etas.tef.config.listener;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.editor.message.MessageManager;

public class ConfigBlockTreeMouseListener implements MouseListener
{

	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		
	}

	@Override
	public void mouseDown(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent event)
	{
		Object src = event.getSource();
		
		if( src instanceof Tree )
		{
			TreeItem[] selections = ((Tree)src).getSelection();
			
			if(selections == null || selections.length < 1)
			{
				return;
			}
			
			TreeItem item = selections[0];
			
			if( null == item )
			{
				return;
			}
			
			Object value = item.getData();
			if(value instanceof ConfigBlock)
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_CONFIG_BLOCK, value);
			}
			else if(value instanceof ConfigFile)
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SET_CONFIG_FILE, value);
			}
		}

	}

}
