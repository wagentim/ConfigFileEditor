package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;

public class TableListener extends CellEditingListener
{

	private TableEditor editor = null;
	private CellIndex cell = null;
	private String newValue = Constants.EMPTY_STRING;
	protected boolean isTextChanged = false;
	protected boolean isLocked = true;
	
	public TableListener(Table table, IController controller, int compositeID)
	{
		super(table, controller, compositeID);
		
		editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;
	}
	
	protected TableEditor getTableEditor()
	{
		return editor;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		
		disposeOldEditor();

        Point pt = new Point(event.x, event.y);
        cell = getDoubleClickPosIndex(pt);
        
        if( null == cell )
        {
        	return;
        }
        
        final TableItem item = getTable().getItem(cell.getRow());
        
        if (item == null || (isLocked && cell.getColumn() == 0))
        {
          return;
        }
        
        Text newEditor = new Text(getTable(), SWT.NONE);
        String text = item.getText(cell.getColumn());
        
        newEditor.setText(text);
        
        newEditor.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyReleased(KeyEvent event)
			{
				if(event.keyCode == SWT.CR)
				{
					disposeOldEditor();
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				
			}
		});
        
        newEditor.addModifyListener(new ModifyListener()
		{
			
			@Override
			public void modifyText(ModifyEvent arg0)
			{
				Text text = (Text) editor.getEditor();
				newValue = text.getText();
				editor.getItem().setText(cell.getColumn(), newValue);
				isTextChanged = true;
			}

		});
        
        newEditor.selectAll();
        newEditor.setFocus();
        editor.setEditor(newEditor, item, cell.getColumn());
	}

	@Override
	public void mouseDown(MouseEvent arg0)
	{
		disposeOldEditor();
	}

	protected void disposeOldEditor()
	{
		disposeEditor(cell, newValue);
	}

	@Override
	public void mouseUp(MouseEvent arg0)
	{
		
	}
	
	private CellIndex getDoubleClickPosIndex(Point pt)
	{
		int columnCount = getTable().getColumnCount();
		int rowCount = getTable().getItemCount();

		for( int j = 0; j < columnCount; j++)
		{
			for (int i = 0; i < rowCount; i++)
			{
				Rectangle rect = getTable().getItem(i).getBounds(j);
				if (rect.contains(pt))
				{
					return new CellIndex(i, j);
				}
			}
		}
		return null;
	}
	
	protected void disposeEditor(CellIndex cell, String newValue)
	{
		Control oldEditor = getTableEditor().getEditor();

		if (oldEditor != null)
		{
			oldEditor.dispose();
			if (isTextChanged)
			{
				getController().parameterChanged(cell, newValue);
				isTextChanged = false;
				ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, getCompositeID(), "Config Block: "
						+ getConfigBlock().getBlockName() + " with new value: " + newValue + cell.toString());
			}
		}
	}
	
	protected String getInfoText() {return Constants.EMPTY_STRING;}
	
	protected ConfigBlock getConfigBlock() {return null;}
	
	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( Constants.ACTION_LOCK_SELECTION_CHANGED == type)
		{
			isLocked = (boolean)content;
			disposeOldEditor();
		}
	}
	
	private Table getTable()
	{
		return (Table)getComposite();
	}
}
