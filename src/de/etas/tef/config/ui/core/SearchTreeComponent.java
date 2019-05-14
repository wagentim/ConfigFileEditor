package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class SearchTreeComponent extends AbstractComposite
{

	private Tree blockList;
	private TreeItem root;
	private TableComposite tableComposite;
	private static Image IMAGE_BLOCK;
	private static Image IMAGE_ROOT;
	
	public SearchTreeComponent(Composite parent, int style, MainController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		IMAGE_BLOCK = new Image(parent.getDisplay(), "icons/block.png");
		IMAGE_ROOT = new Image(parent.getDisplay(), "icons/root.png");

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
		
		blockList.addMouseListener(new TreeListener(blockList, controller, getCompositeID()));
	}
	
	public void setBlockList(String[] blockList)
	{
		root.removeAll();
		
		for(int i = 0; i < blockList.length; i++)
		{
			TreeItem it = new TreeItem(root, SWT.NONE);
			it.setText(blockList[i]);
			it.setImage(IMAGE_BLOCK);
		}
		
		root.setExpanded(true);
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
	}
	
}
