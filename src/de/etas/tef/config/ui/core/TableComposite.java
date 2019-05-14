package de.etas.tef.config.ui.core;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class TableComposite extends AbstractComposite
{
	private Table table;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnSave;
	private Button btnLock;
	protected Color tableBackgroudColor;
	private Composite buttonComposite;
	
	private SearchTreeComponent searchTree;
	
	public TableComposite(Composite parent, int style, MainController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);

		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		initMainComposite(this, controller);
		initTableButtons(this);
		
		tableBackgroudColor = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
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
	
	protected void initMainComposite(Composite comp, MainController controller)
	{
		SashForm sf = new SashForm(comp, SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		sf.setLayoutData(gd);
		
		searchTree = new SearchTreeComponent(sf, SWT.NONE, controller, getCompositeID());
		searchTree.setTableComposite(this);
		
		table = new Table(sf, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = Constants.HEIGHT_HINT;
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
		sf.setWeights(new int[]{1, 2});
	}
	
	protected void addTableSelectedListener()
	{
		getTable().addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				String text = getTable().getItem(getTable().getSelectionIndex()).getText(1);
				
				if( !((MainController)(getController().getParent())).isConnected() )
				{
					return;
				}
					
				ActionManager.INSTANCE.sendAction(Constants.ACTION_SOURCE_PARAMETER_SELECTED, getCompositeID(), text);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});
	}

	protected void treeItemSelected(String blockName)
	{
		if( null == blockName || blockName.isEmpty() )
		{
			updateParameters(Collections.emptyList());
			return;
		}
		
		getController().setSelectedBlock(blockName);
		
		ConfigBlock cb = getController().getSelectedConfigBlock();
		
		if( null != cb)
		{
			updateParameters(getController().getSelectedConfigBlock().getAllParameters());
			ActionManager.INSTANCE.sendAction(Constants.ACTION_BLOCK_SELECTED, getCompositeID(), cb);
		}
	}

	protected void addTableMouseListener()
	{
		getTable().addMouseListener(new TableListener(getTable(), getController(), getCompositeID()));
	}
	
	protected void setTreeSelectedBlock(String blockName)
	{
		searchTree.setTreeSelectedBlock(blockName);
	}
	
	protected void setBlockList(String[] blockList)
	{
		searchTree.setBlockList(blockList);
	}
	
	protected void initTableButtons(Composite comp)
	{
		buttonComposite = new Composite(comp, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(1, false));
		
		GridData gd = new GridData();
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		
		btnAdd = new Button(buttonComposite, SWT.PUSH);
		btnAdd.setText(Constants.TXT_BTN_ADD);
		btnAdd.setLayoutData(gd);
		btnAdd.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				addTableItem(null);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		btnDelete = new Button(buttonComposite, SWT.PUSH);
		btnDelete.setText(Constants.TXT_BTN_DELETE);
		btnDelete.setLayoutData(gd);
		btnDelete.addSelectionListener(new SelectionListener() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent event) 
			{
				deleteSelectedItems();
				treeItemSelected(searchTree.getSelectedTreeItem().getText().trim());
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) 
			{
				
			}
		});
		
		
		btnSave = new Button(buttonComposite, SWT.PUSH);
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
		
		btnLock = new Button(buttonComposite, SWT.CHECK);
		btnLock.setText("Edit Lock");
		gd = new GridData();
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		gd.heightHint = 80;
		btnLock.setLayoutData(gd);
		btnLock.setSelection(true);
		
		btnLock.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOCK_SELECTION_CHANGED, getCompositeID(), btnLock.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		});
	}

	protected void deleteSelectedItems()
	{
		int[] selectedItems = table.getSelectionIndices();
		
		if( null != selectedItems && selectedItems.length > 0 )
		{
			
			MessageBox mb = new MessageBox(this.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

			mb.setMessage("Do you really want to delete?");

			boolean done = mb.open() == SWT.YES;
			
			if( done )
			{
				getController().deleteParameters(selectedItems, searchTree.getSelectedTreeItem().getText());
		
			}
		}
	}


	public void updateParameters(List<KeyValuePair> values)
	{
		clearTable();

		if( null == table || null == values || values.size() < 1 )
		{
			return;
		}
		
		
		for(int i = 0; i < values.size(); i++)
		{
			addTableItem(values.get(i));
			
		}
	}
	
	protected void addTableItem(KeyValuePair kvp)
	{
		TableItem ti = new TableItem(table, SWT.NONE);
		ti.setText(0, kvp.getKey());
		ti.setText(1, kvp.getValue());
		ti.setText(2, kvp.getOther());
		ti.setText(3, kvp.getForthValue());
		ti.setBackground(tableBackgroudColor);
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

	protected String fileSave(Shell shell)
	{
		FileDialog fd = new FileDialog(shell, SWT.APPLICATION_MODAL | SWT.SAVE);
		fd.setFilterExtensions(Constants.CONFIG_FILE_EXTENSION);
		fd.setFilterNames(Constants.CONFIG_FILE_NAME);
		String result = fd.open();
		if( null != result )
		{
			Path file = Paths.get(result);
	        if (Files.exists(file))
	        {
	          MessageBox mb = new MessageBox(fd.getParent(), SWT.ICON_WARNING | SWT.YES | SWT.NO);

	          mb.setMessage(result + " already exists. Do you want to replace it?");

	          boolean done = mb.open() == SWT.YES;
	          
	          if( !done )
	          {
	        	return null;  
	          }
	        }
		}
		
		return result;
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
	{
		getController().saveFile(targetFilePath);
		ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, getCompositeID(), "Source Write to: " + targetFilePath + " finished!");
	}
	
	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( compositeID != getCompositeID() && compositeID != CompositeID.COMPOSITE_ALONE )
		{
			return;
		}
		
		if( type == Constants.ACTION_PARAMETER_UPDATE )
		{
			ConfigBlock cb = getController().getSelectedConfigBlock();
			if( null != cb )
			{
				updateParameters(cb.getAllParameters());
			}
			else
			{
				updateParameters(Collections.emptyList());
			}
		}
		else if( type == Constants.ACTION_NEW_FILE_SELECTED || type == Constants.ACTION_DROP_NEW_FILE_SELECTED)
		{
			clearTable();
			String[] allBlocks = getController().getAllBlocks();
			setBlockList(allBlocks);
		}
		 
	}
}
