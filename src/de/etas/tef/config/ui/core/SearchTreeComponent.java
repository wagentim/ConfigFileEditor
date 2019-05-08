package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class SearchTreeComponent extends AbstractComposite
{

	private Tree blockList;
//	private Text searchText;
	private TreeItem root;
	private TableComposite tableComposite;
	
	public SearchTreeComponent(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		initComponent();
	}

	protected void initComponent()
	{
		
		new SearchComposite(this, SWT.BORDER);
//		searchText = new Text(this, SWT.ICON_SEARCH | SWT.ICON_CANCEL);
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.heightHint = Constants.SEARCH_TEXT_HEIGHT;
//		searchText.setLayoutData(gd);
		
		blockList = new Tree(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = Constants.HEIGHT_HINT;
		blockList.setLayoutData(gd);
		
		root = new TreeItem(blockList, SWT.NONE);
		root.setText(Constants.TXT_CONFIG_FILE);
		
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
	}
	
	public void setBlockList(String[] blockList)
	{
		root.removeAll();
		root.setText(root.getText()); 
		
		for(int i = 0; i < blockList.length; i++)
		{
			TreeItem it = new TreeItem(root, SWT.NONE);
			it.setText(blockList[i]);
		}
		
		root.setExpanded(true);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getCompositeID()
	{
		return CompositeID.COMPOSITE_ALONE;
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
	
}
