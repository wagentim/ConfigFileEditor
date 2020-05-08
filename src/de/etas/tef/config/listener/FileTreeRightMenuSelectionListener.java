package de.etas.tef.config.listener;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.IMessage;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.editor.message.MessageManager;

public class FileTreeRightMenuSelectionListener extends TreeSelectionListener
{
	
	protected boolean result = false;

	public FileTreeRightMenuSelectionListener(Tree tree, MainController controller)
	{
		super(tree, controller);
	}

	@Override
	protected void handleRightMenu(MenuItem item)
	{
		String text = item.getText();
		
		if(text.equals(IConstants.TXT_MENU_ADD_DIR))
		{
			handleAddDir();
		}
		else if(text.equals(IConstants.TXT_MENU_ADD_FILE))
		{
			handelAddFile();
		}
		else if(text.equals(IConstants.TXT_TOOLBAR_FILE_HISTORY))
		{
			handleShowFileHistory();
		}
	}
	
	private void handleShowFileHistory()
	{
		TreeItem selected = getSelectedTreeItem();
		Path filePath = Paths.get((String) selected.getData(IConstants.DATA_PATH));
		MessageManager.INSTANCE.sendMessage(IMessage.MSG_GET_FILE_HISTORY, filePath);
	}

	private void handleAddDir()
	{
		TreeItem newItem = addNewTreeItem();
		
		if( null != newItem)
		{
			newItem.setText(IConstants.TXT_DEFAULT_DIR);
			handleTreeItemEdit(newItem);
		}
	}

	/**
	 * Add {@link TreeItem} under selected Item
	 */
	private void handelAddFile()
	{
		TreeItem newItem = addNewTreeItem();
		
		if( null != newItem)
		{
			newItem.setText(IConstants.TXT_DEFAULT_INI);
			handleTreeItemEdit(newItem);
		}
	}

	private TreeItem addNewTreeItem()
	{
		TreeItem selected = getSelectedTreeItem();
		
		if( null == selected )
		{
			logger.error("The selected tree node is null");
			return null;
		}
		
		TreeItem ti = new TreeItem(selected, SWT.NONE);
		selected.setExpanded(true);
		
		return ti;
	}
	
	@Override
	protected boolean updateTreeItemValue(TreeItem item)
	{
		String fileName = item.getText();
		String parentPath = (String)item.getParentItem().getData(IConstants.DATA_PATH);
		String text = parentPath + File.separator + fileName;
		item.setData(IConstants.DATA_PATH, text);
		
		TreeItem parent = item.getParentItem();
		
		Path newFile = Paths.get(text);
		
		if(isNameDuplicated(parent, text))
		{
			return false;
		}
		else
		{
			if( text.toLowerCase().endsWith(".ini") || text.toLowerCase().endsWith(".txt") )
			{
				try
				{
					Files.createFile(newFile);
					item.setData(IConstants.DATA_TYPE, IConstants.DATA_TYPE_FILE);
					controller.getGitController().addFileToRepository(text);
				} 
				catch (IOException e)
				{
					logger.error("Create new File Failed: {}", text);
					item.dispose();
				}
			}
			else
			{
				try
				{
					Files.createDirectories(newFile);
					item.setData(IConstants.DATA_TYPE, IConstants.DATA_TYPE_DIR);
				} 
				catch (IOException e)
				{
					logger.error("Create new Directory Failed: {}", text);
					item.dispose();
				}
			}
		}
		
		return true;
	}

	@Override
	protected boolean isNameDuplicated(TreeItem parent, String name)
	{
		Path p = Paths.get((String)parent.getData(IConstants.DATA_PATH));
		String checkName = Paths.get(name).getFileName().toString();
		
		result = false;
		
		try
		{
			Files.list(p).forEach(new Consumer<Path>()
			{
				@Override
				public void accept(Path path)
				{
					if(path == null || path.getFileName().toString().equalsIgnoreCase(".git"))
					{
						return;
					}
					else
					{
						String n = path.getFileName().toString();
						
						if(n.equals(checkName))
						{
							result  = true;
						}
					}
				}
			});
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}
