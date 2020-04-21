package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.IConstants;

public abstract class CustomTree extends AbstractComposite
{

	private final MainController controller;
	
	private Tree tree;
	private TreeItem root;
	private TreeListener tl;
	private Menu rightClickMenu;
	
	public CustomTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		this.controller = controller;
		
		initComponent();
	}

	protected void initComponent()
	{
		super.initComposite();
		
		tree = new Tree(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = IConstants.HEIGHT_HINT;
		tree.setLayoutData(gd);
		
		root = new TreeItem(tree, SWT.NONE);
		root.setText(getRootNodeName());
		
		tree.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				String s = getSelectedTreeItem().getText();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
	}
	
	protected abstract String getRootNodeName();
	
	private void createRightMenu(Control control, SelectionListener listener)
	{
		rightClickMenu = new Menu(control);
		control.setMenu(rightClickMenu);
		
		rightClickMenu.addMenuListener(new MenuAdapter()
	    {
	        public void menuShown(MenuEvent e)
	        {
	        	
	            MenuItem[] items = rightClickMenu.getItems();
	            
	            for (int i = 0; i < items.length; i++)
	            {
	                items[i].dispose();
	            }
	            
	            if(tree.getSelectionCount() <= 0)
	            {
	            	return;
	            }
	            
	            MenuItem copyItem = new MenuItem(rightClickMenu, SWT.NONE);
	            copyItem.setText(IConstants.TXT_COPY);
	            copyItem.addSelectionListener(listener);
	            
	            MenuItem pasteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            pasteItem.setText(IConstants.TXT_PASTE);
	            pasteItem.addSelectionListener(listener);
	            
	            new MenuItem(rightClickMenu, SWT.SEPARATOR);
	            
	            MenuItem newItem = new MenuItem(rightClickMenu, SWT.NONE);
	            newItem.setText(IConstants.TXT_BTN_ADD);
	            newItem.addSelectionListener(listener);

	            MenuItem deleteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            deleteItem.setText(IConstants.TXT_BTN_DELETE);
	            deleteItem.addSelectionListener(listener);
	            
	            
	        }
	    });
	}
	
	public void setBlockList(String[] blockList)
	{
		root.removeAll();
		
		for(int i = 0; i < blockList.length; i++)
		{
			String blockName = blockList[i];
			
			addTreeItem(blockName, root, -1);
		}
		
		root.setExpanded(true);
	}
	
	private void addTreeItem(String blockName, TreeItem parent, int index)
	{
		TreeItem it;
		
		if( index < 0 )
		{
			it = new TreeItem(parent, SWT.NONE);
		}
		else
		{
			it = new TreeItem(parent, SWT.NONE, index);
		}
		it.setText(blockName);
	}

	public TreeItem getSelectedTreeItem()
	{
		TreeItem ti = tree.getSelection()[0];
		return ti;
	}
	
	public void setTreeSelectedBlock(String blockName)
	{
		TreeItem[] items = root.getItems();
		
		for( int i = 0 ; i < items.length; i++)
		{
			if( blockName.trim().equals(items[i].getText().trim()))
			{
				tree.select(items[i]);
			}
		}
	}

	private TreeItem getTreeItem(String name)
	{
		TreeItem[] items = root.getItems();
		
		for(int i = 0; i < items.length; i++)
		{
			TreeItem ti = items[i];
			
			if(name.equals(ti.getText()))
			{
				return ti;
			}
		}
		
		return null;
	}
	
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( IConstants.ACTION_SET_SHOW_CONFIG_BLOCKS == type)
		{
			
		}
		
		if( IConstants.ACTION_LOCK_SELECTION_CHANGED == type)
		{
			boolean locked = (boolean)content;
			
			if( locked )
			{
				tree.setMenu(null);
				
				if(null != rightClickMenu)
				{
					rightClickMenu.dispose();
				}
			}
			else
			{
				createRightMenu(tree, tl);
			}
		}
		
		if( IConstants.ACTION_ADD_NEW_BLOCK == type )
		{
			
			addNewBlock((ConfigBlock)content);
		}
		
		if( IConstants.ACTION_DELETE_BLOCK == type )
		{
			String blockName = (String)content;
			
			if( null == blockName || blockName.isEmpty() )
			{
				return;
			}
			
			TreeItem selected = getSelectedTreeItem();
			
			if(null == selected.getParentItem())
			{
				return;
			}
			
			TreeItem root = selected.getParentItem();
			selected.dispose();
			tree.setSelection(root.getItem(0));
		}
		
		if( IConstants.ACTION_COPY_BLOCK == type )
		{
			TreeItem cb = tree.getSelection()[0];
			
			if( null == cb || null == cb.getParentItem() )
			{
				return;
			}
			
			String blockName = cb.getText();
		}
		
		if( IConstants.ACTION_PASTE_BLOCK == type )
		{
			ConfigBlock newBlock = controller.getCopyBlock();
			
			if( null == newBlock )
			{
				return;
			}
			addNewBlock(newBlock);
		}
		
	}
	
	private void addNewBlock(ConfigBlock content)
	{
		ConfigBlock newBlock = content;
		
		TreeItem selectedItem = getSelectedTreeItem();
		TreeItem root = selectedItem.getParentItem();
		
		TreeItem parent;
		int index;
		
		if( null == root )
		{
			parent = selectedItem;
			index = 0;
		}
		else
		{
			parent = root;
			index = parent.indexOf(selectedItem);
		}
		
		addTreeItem(newBlock.getBlockName(), parent, index);
	}
}
