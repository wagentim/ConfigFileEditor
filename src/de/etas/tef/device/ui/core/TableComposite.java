package de.etas.tef.device.ui.core;


import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Constants;

public class TableComposite extends AbstractComposite
{
	private Tree blockList;
	private Table table;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnSave;
	private final int HEIGHT_HINT = 150;
	private TreeItem root;
	

	public TableComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		initTable(this);
		initTableButtons(this);
	}
	

	public Table getTable()
	{
		return table;
	}

	public Button getBtnAdd()
	{
		return btnAdd;
	}

	public Button getBtnDelete()
	{
		return btnDelete;
	}

	public Button getBtnSave()
	{
		return btnSave;
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

	protected void initTable(Composite comp)
	{
		
		SashForm sf = new SashForm(comp, SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		sf.setLayoutData(gd);
		
		blockList = new Tree(sf, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = HEIGHT_HINT;
		blockList.setLayoutData(gd);
		
		root = new TreeItem(blockList, SWT.NONE);
		root.setText(Constants.TXT_CONFIG_FILE);
		
		blockList.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				TreeItem ti = blockList.getSelection()[0];
				String s = ti.getText();
				treeItemSelected(s.trim());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		table = new Table(sf, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = HEIGHT_HINT;
		table.setLayoutData(gd);
	    
		for (int i = 0; i < Constants.TABLE_TITLES.length; i++) 
		{
			TableColumn column = new TableColumn(table, SWT.LEFT);
			column.setText(Constants.TABLE_TITLES[i]);
			column.setResizable(true);
			column.setWidth(150);
		}
		
		addTableMouseListener();
		addTableSelectedListener();
		sf.setWeights(new int[]{1, 3});
	}
	
	protected void addTableSelectedListener()
	{
		
	}


	protected void treeItemSelected(String blockName)
	{
		
	}


	protected void addTableMouseListener() {}
	
	protected void initTableButtons(Composite comp)
	{
		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new GridLayout(1, false));
		
		GridData gd = new GridData();
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		
		btnAdd = new Button(c, SWT.PUSH);
		btnAdd.setText(Constants.TXT_BTN_ADD);
		btnAdd.setLayoutData(gd);
		
		btnDelete = new Button(c, SWT.PUSH);
		btnDelete.setText(Constants.TXT_BTN_DELETE);
		btnDelete.setLayoutData(gd);
		
		btnSave = new Button(c, SWT.PUSH);
		btnSave.setText(Constants.TXT_BTN_SAVE);
		btnSave.setLayoutData(gd);
		btnSave.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				toSave();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		btnSave.setVisible(false);
	}

	public void updateParameters(List<KeyValuePair> values)
	{
		if( null == table || null == values || values.size() < 1 )
		{
			return;
		}
		
		clearTable();
		
		for(int i = 0; i < values.size(); i++)
		{
			KeyValuePair kvp = values.get(i);
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setText(0, kvp.getKey());
			ti.setText(1, kvp.getValue());
			ti.setText(2, kvp.getOther());
			ti.setText(3, kvp.getForthValue());
		}
	}
	
	protected void clearTable()
	{
		if( null == table )
		{
			return;
		}
			
		TableItem[] items = table.getItems();
		
		if( items.length <= 0 )
		{
			return;
		}
		
		for(int i = items.length - 1; i >= 0; i--)
		{
			table.remove(i);
		}
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}

	protected void setAllComponentsEnable(boolean isEnable)
	{
		table.setEnabled(isEnable);
		btnAdd.setEnabled(isEnable);
		btnDelete.setEnabled(isEnable);
		btnSave.setEnabled(isEnable);
	}
	
	protected String fileSave(Shell shell)
	{
		FileDialog fd = new FileDialog(shell, SWT.APPLICATION_MODAL | SWT.SAVE);
		fd.setFilterExtensions(Constants.CONFIG_FILE_EXTENSION);
		fd.setFilterNames(Constants.CONFIG_FILE_NAME);
		return fd.open();
	}
	
	private void toSave() 
	{
		String fileSavePath = fileSave(this.getShell());

		if (null == fileSavePath)
		{
			fileSavePath = Constants.EMPTY_STRING;
		}

		saveAction(fileSavePath);
	}
	
	protected void saveAction(String targetFilePath)
	{}
	
	protected void setTreeSelectedBlock(String blockName)
	{
		TreeItem[] items = root.getItems();
		
		for( int i = 0 ; i < items.length; i++)
		{
			if( blockName.equals(items[i].getText()))
			{
				blockList.select(items[i]);
			}
		}
	}
}
