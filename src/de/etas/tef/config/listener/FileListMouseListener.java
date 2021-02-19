package de.etas.tef.config.listener;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.Problem;
import de.etas.tef.editor.message.MessageManager;

public class FileListMouseListener implements MouseListener
{

	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
	}

	@Override
	public void mouseDown(MouseEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent event)
	{
		Object source = event.getSource();
		
		if(source instanceof Table)
		{
			Table table = (Table)source;
			
			TableItem item = table.getItem(new Point(event.x, event.y));
			
			if(null != item)
			{
				ConfigFilePath filePath = (ConfigFilePath) item.getData();
				
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_OPEN_INI_FILE, filePath);
				
				ConfigFile cf = filePath.getConfigFile();
				Map<Integer, List<Problem>> problems = cf.getProblemList();
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SHOW_PROBLEM, problems);
			}
		}

	}
	
}
