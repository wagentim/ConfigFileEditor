package de.etas.tef.config.ui.composites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.listener.ConfigFileTreeMouseListener;
import de.etas.tef.config.listener.FileTreeRightMenuSelectionListener;
import de.etas.tef.config.listener.TreeSelectionListener;

public class ConfigFileTree extends CustomTree
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigFileTree.class);
	
	private TreeSelectionListener selectionListener = null;
	private ConfigFileTreeMouseListener mouseListener = null;
	
	public ConfigFileTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComponent();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}
	
	@Override
	protected String getRootNodeName()
	{
		return IConstants.TXT_CONFIG_FILES;
	}

	@Override
	protected SelectionListener getTreeRightMenuSelectionListener()
	{
		if( null == selectionListener )
		{
			selectionListener = new FileTreeRightMenuSelectionListener(tree, controller);
		}
		
		return selectionListener;
	}

	@Override
	protected void createCustomRightMenu(Menu rightClickMenu)
	{
		new MenuItem(rightClickMenu, SWT.SEPARATOR);
		
		TreeItem selItem = getSelectedItem();
		
		int type = (int)selItem.getData(IConstants.DATA_TYPE);
		
		if( type == IConstants.DATA_TYPE_DIR)
		{
			MenuItem newFileItem = new MenuItem(rightClickMenu, SWT.NONE);
			newFileItem.setText(IConstants.TXT_MENU_ADD_FILE);
			newFileItem.addSelectionListener(getTreeRightMenuSelectionListener());
		
			MenuItem newDirItem = new MenuItem(rightClickMenu, SWT.NONE);
			newDirItem.setText(IConstants.TXT_MENU_ADD_DIR);
			newDirItem.addSelectionListener(getTreeRightMenuSelectionListener());
		}
		else if( type == IConstants.DATA_TYPE_FILE )
		{
			MenuItem newFileItem = new MenuItem(rightClickMenu, SWT.NONE);
			newFileItem.setText(IConstants.TXT_TOOLBAR_FILE_HISTORY);
			newFileItem.addSelectionListener(getTreeRightMenuSelectionListener());
		}
	}

	@Override
	protected void loadData()
	{
		
	}
	
	private void delayLoadData()
	{
		String path = controller.getSetting().getRepositoryLocal();
		root.setData(IConstants.DATA_PATH, path);
		root.setData(IConstants.DATA_TYPE, IConstants.DATA_TYPE_DIR);
		createFileTree(root, path);
		root.setExpanded(true);
	}
	
	private void createFileTree(TreeItem root, String path)
	{
		Path p = Paths.get(path);
		try
		{
			Files.list(p).forEach(new Consumer<Path>()
			{

				@Override
				public void accept(Path pth)
				{
					if(pth == null || pth.getFileName().toString().equalsIgnoreCase(".git"))
					{
						return;
					}
					else
					{
						TreeItem ti = new TreeItem(root, SWT.NONE);
						ti.setText(pth.getFileName().toString());
						ti.setData(IConstants.DATA_PATH, pth.toString());
						ti.setData(IConstants.DATA_TYPE, IConstants.DATA_TYPE_FILE);

						if(Files.isDirectory(pth))
						{
							createFileTree(ti, pth.toString());
							ti.setData(IConstants.DATA_TYPE, IConstants.DATA_TYPE_DIR);
						}
					}
				}
			});
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected MouseListener getMouseListener()
	{
		if( mouseListener == null )
		{
			mouseListener = new ConfigFileTreeMouseListener();
		}
		
		return mouseListener;
	}
}
