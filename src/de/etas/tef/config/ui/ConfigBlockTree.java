package de.etas.tef.config.ui;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.listener.ConfigBlockTreeMouseListener;

public class ConfigBlockTree extends CustomTree
{

	private ConfigBlockTreeMouseListener mouseListener = null;

	public ConfigBlockTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComponent();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_OPEN_INI_FILE)
		{
			if(content == null)
			{
				return;
			}
			
			ConfigFile cf = ((ConfigFilePath)content).getConfigFile();
			updateRootNode(cf.getFilePath().getFileName().toString(), cf);
			createTree(cf);
			root.setExpanded(true);
		}
		else if(type == IConstants.ACTION_UPDATE_FILE_LIST)
		{
			clearTree();
		}
	}
	
	protected void clearTree() 
	{
		TreeItem[] items = root.getItems();
		
		if(items != null)
		{
			int size = items.length;
			
			for(int i = size -1; i >=0 ; i--)
			{
				(items[i]).dispose();
			}
		}
	}

	public void createTree(ConfigFile configFile)
	{
		clearTree();
		List<ConfigBlock> blocks = configFile.getConfigBlocks();
		blocks = controller.getSorter().sortConfigBlock(blocks);
		Iterator<ConfigBlock> it = blocks.iterator();

		while (it.hasNext())
		{
			ConfigBlock cb = it.next();
			TreeItem ti = new TreeItem(root, SWT.NONE);
			ti.setText(cb.getBlockName());
			ti.setData(cb);
		}
	}

	@Override
	protected String getRootNodeName()
	{
		return IConstants.TXT_INI_FILE_DEFAULT;
	}

	@Override
	protected SelectionListener getTreeRightMenuSelectionListener()
	{
		// TODO Auto-generated method stub
		return new SelectionAdapter()
		{
		};
	}

	@Override
	protected void createCustomRightMenu(Menu rightClickMenu)
	{

	}

	@Override
	protected MouseListener getMouseListener()
	{
		if( mouseListener  == null )
		{
			mouseListener = new ConfigBlockTreeMouseListener();
		}
		
		return mouseListener;
	}

}
