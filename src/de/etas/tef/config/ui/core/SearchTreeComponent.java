package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class SearchTreeComponent extends AbstractComposite
{

	private Tree blockList;
	private TreeItem root;
	private TableComposite tableComposite;
	private static Image IMAGE_BLOCK;
	private static Image IMAGE_ROOT;
	private static Image IMAGE_ADD;
	private static Image IMAGE_REMOVE;
	private TreeListener tl;
	private Menu rightClickMenu;
	
	public SearchTreeComponent(Composite parent, int style, MainController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		IMAGE_BLOCK = new Image(parent.getDisplay(), "icons/block.png");
		IMAGE_ROOT = new Image(parent.getDisplay(), "icons/root.png");
		IMAGE_ADD = new Image(parent.getDisplay(), "icons/add.png");
		IMAGE_REMOVE = new Image(parent.getDisplay(), "icons/remove.png");
		
		initComponent(controller);
	}

	protected void initComponent(MainController controller)
	{
		new SearchComposite(this, SWT.BORDER, controller, getCompositeID());
		
		blockList = new Tree(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = Constants.HEIGHT_HINT;
		blockList.setLayoutData(gd);
		
		root = new TreeItem(blockList, SWT.NONE);
		root.setText(Constants.TXT_CONFIG_FILE);
		root.setImage(IMAGE_ROOT);
		
		blockList.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				String s = getSelectedTreeItem().getText();
				getTableComposite().treeItemSelected(s.trim());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
		
		tl = new TreeListener(blockList, getController(), getCompositeID());
		blockList.addMouseListener(tl);
		blockList.addKeyListener(tl);
		
	}
	
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
	            
	            MenuItem newItem = new MenuItem(rightClickMenu, SWT.NONE);
	            newItem.setText(Constants.TXT_BTN_ADD);
	            newItem.setImage(IMAGE_ADD);
	            newItem.addSelectionListener(listener);

	            MenuItem deleteItem = new MenuItem(rightClickMenu, SWT.NONE);
	            deleteItem.setText(Constants.TXT_BTN_DELETE);
	            deleteItem.setImage(IMAGE_REMOVE);
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
		it.setImage(IMAGE_BLOCK);
	}

	public TreeItem getSelectedTreeItem()
	{
		TreeItem ti = blockList.getSelection()[0];
		return ti;
	}
	
	public void setTreeSelectedBlock(String blockName)
	{
		TreeItem[] items = root.getItems();
		
		for( int i = 0 ; i < items.length; i++)
		{
			if( blockName.trim().equals(items[i].getText().trim()))
			{
				blockList.select(items[i]);
			}
		}
	}

	public TableComposite getTableComposite()
	{
		return tableComposite;
	}

	public void setTableComposite(TableComposite tableComposite)
	{
		this.tableComposite = tableComposite;
	}
	
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( compositeID != getCompositeID() && compositeID != CompositeID.COMPOSITE_ALONE)
		{
			return;
		}
		
		if( Constants.ACTION_SET_SHOW_CONFIG_BLOCKS == type)
		{
			String[] blocks = getController().getShowConfigBlocks();
			setBlockList(blocks);
			
			if(blocks.length > 0)
			{
				String s = blocks[0];
				setTreeSelectedBlock(s);
				getTableComposite().treeItemSelected(s);
			}
			else
			{
				getTableComposite().treeItemSelected(Constants.EMPTY_STRING);
			}
			
		}
		
		if( Constants.ACTION_LOCK_SELECTION_CHANGED == type)
		{
			boolean locked = (boolean)content;
			
			if( locked )
			{
				blockList.setMenu(null);
				
				if(null != rightClickMenu)
				{
					rightClickMenu.dispose();
				}
			}
			else
			{
				createRightMenu(blockList, tl);
			}
		}
		
		if( Constants.ACTION_ADD_NEW_BLOCK == type )
		{
			ConfigBlock newBlock = (ConfigBlock)content;
			
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
		
		if( Constants.ACTION_DELETE_BLOCK == type )
		{
			String blockName = (String)content;
			
			if( null == blockName || blockName.isEmpty() )
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, CompositeID.COMPOSITE_ALONE, "Delete node name is empty");
				return;
			}
			
			TreeItem selected = getSelectedTreeItem();
			
			if(null == selected.getParentItem())
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_WARNING, CompositeID.COMPOSITE_ALONE, "Cannot remove the root node");
				return;
			}
			getController().removeBlock(blockName);
			
			TreeItem root = selected.getParentItem();
			selected.dispose();
			blockList.setSelection(root.getItem(0));
		}
	}
}
