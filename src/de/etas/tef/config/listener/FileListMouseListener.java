package de.etas.tef.config.listener;

import java.nio.file.Path;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.helper.IConstants;
import de.etas.tef.editor.message.MessageManager;

public class FileListMouseListener implements MouseListener
{

	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		Object source = event.getSource();
		
		if(source instanceof Table)
		{
			Table table = (Table)source;
			
			TableItem item = table.getItem(new Point(event.x, event.y));
			
			if(null != item)
			{
				Path filePath = (Path) item.getData(IConstants.DATA_PATH);
				
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_OPEN_INI_FILE, filePath);
			}
		}
	}

	@Override
	public void mouseDown(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}
	
}
