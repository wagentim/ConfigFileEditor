package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;

public class TableMouseListener implements MouseListener, IActionListener
{

	private TableEditor editor = null;
	private final Table table;
	private final IController controller;
	private CellIndex cell = null;
	private String newValue = Constants.EMPTY_STRING;
	protected boolean isTextChanged = false;
	protected boolean isLocked = true;
	
	public TableMouseListener(Table table, IController controller)
	{
		this.table = table;
		this.controller = controller;
		
		editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;

	    ActionManager.INSTANCE.addActionListener(this);
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
        
        final TableItem item = table.getItem(cell.getRow());
        
        if (item == null || (isLocked && cell.getColumn() == 0))
        {
          return;
        }
        
        Text newEditor = new Text(table, SWT.NONE);
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
		int columnCount = table.getColumnCount();
		int rowCount = table.getItemCount();

		for( int j = 0; j < columnCount; j++)
		{
			for (int i = 0; i < rowCount; i++)
			{
				Rectangle rect = table.getItem(i).getBounds(j);
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
	}
	
	protected String getInfoText() {return Constants.EMPTY_STRING;}
	
	protected ConfigBlock getConfigBlock() {return null;}
	
	protected IController getController()
	{
		return controller;
	}

	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( Constants.ACTION_LOCK_SELECTION_CHANGED == type)
		{
			isLocked = (boolean)content;
			disposeOldEditor();
		}
	}

}
