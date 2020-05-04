package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.MainController;

public abstract class CustomTree extends AbstractComposite
{

	protected final MainController controller;
	
	protected Tree tree;
	protected TreeItem root;
	protected TreeListener tl;
	
	public CustomTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		this.controller = controller;
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
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
		
		tree.addMouseListener(getMouseListener());
		
		createRightMenu(getTreeRightMenuSelectionListener());
		
		this.getDisplay().asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				loadData();
			}
		});
	}
	
	protected void updateRootNode(String name, String key, Object value)
	{
		root.setText(name);
		root.setData(key, value);
	}
	
	protected abstract MouseListener getMouseListener();

	protected abstract void loadData();

	protected abstract SelectionListener getTreeRightMenuSelectionListener();
	
	protected abstract String getRootNodeName();
	
	protected Menu createRightMenu(SelectionListener listener)
	{
		Menu rightClickMenu = new Menu(tree);
		tree.setMenu(rightClickMenu);
		
		rightClickMenu.addMenuListener(new MenuAdapter()
	    {
	        public void menuShown(MenuEvent e)
	        {
	            MenuItem[] items = rightClickMenu.getItems();
	            
	            for (int i = 0; i < items.length; i++)
	            {
	                items[i].dispose();
	            }
	            
	            TreeItem selItem = getSelectedItem();
	            
	            if(null == selItem)
	            {
	            	return;
	            }
	            
	            MenuItem copyItem = new MenuItem(rightClickMenu, SWT.NONE);
	            copyItem.setText(IConstants.TXT_MENU_COPY);
	            copyItem.addSelectionListener(listener);
	            
	            MenuItem pasteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            pasteItem.setText(IConstants.TXT_MENU_PASTE);
	            pasteItem.addSelectionListener(listener);
	            
	            createCustomRightMenu(rightClickMenu);
	        }
	    });
		
		return rightClickMenu;
	}
	
	protected TreeItem getSelectedItem()
	{
		return tree.getSelection()[0];
	}
	
	protected abstract void createCustomRightMenu(Menu rightClickMenu);

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
}
