package de.etas.tef.config.ui.composites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.listener.FileTreeRightMenuSelectionListener;
import de.etas.tef.config.listener.TreeSelectionListener;
import de.etas.tef.config.ui.core.CustomTree;

public class FileListTree extends CustomTree
{
	private static final Logger logger = LoggerFactory.getLogger(FileListTree.class);
	
	private TreeSelectionListener selectionListener = null;
	
	public FileListTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}
	
	@Override
	protected String getRootNodeName()
	{
		return IConstants.TXT_FILES;
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
		
		MenuItem newFileItem = new MenuItem(rightClickMenu, SWT.NONE);
		newFileItem.setText(IConstants.TXT_MENU_ADD_FILE);
		newFileItem.addSelectionListener(getTreeRightMenuSelectionListener());
	
		MenuItem newDirItem = new MenuItem(rightClickMenu, SWT.NONE);
		newDirItem.setText(IConstants.TXT_MENU_ADD_DIR);
		newDirItem.addSelectionListener(getTreeRightMenuSelectionListener());
        
        MenuItem deleteItem = new MenuItem(rightClickMenu, SWT.NONE);
        deleteItem.setText(IConstants.TXT_MENU_DELETE);
        deleteItem.addSelectionListener(getTreeRightMenuSelectionListener());
	}

	@Override
	protected void loadData()
	{
		String path = controller.getSetting().getRepositoryLocal();
		root.setData(IConstants.DATA_PATH, path);
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
}