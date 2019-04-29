package de.etas.tef.device.ui.core;

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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;

public class TableMouseListener implements MouseListener
{

	private final TableEditor editor;
	private final Table table;
	private final IController controller;
//	private final boolean isSource;
	private CellIndex cell = null;
	private String newValue = Constants.EMPTY_STRING;
	
	public TableMouseListener(Table table, IController controller)
	{
		this.table = table;
		this.controller = controller;
		
		editor = new TableEditor(table);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;
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
        
        if (item == null)
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
				// TODO Auto-generated method stub
				
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

	@Override
	public void mouseUp(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
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
	
	private void disposeOldEditor()
	{
		Control oldEditor = editor.getEditor();
		
        if (oldEditor != null)
        {
			oldEditor.dispose();
			getController().updateParameter(cell, newValue, isSource());
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_INFO, getInfoText() + " File Block: " + getConfigBlock().getBlockName() + " with new value: " + newValue + cell.toString());
        }
	}
	
	protected boolean isSource() 
	{
		return false;
	};
	
	protected String getInfoText() {return Constants.EMPTY_STRING;}
	
	protected ConfigBlock getConfigBlock() {return null;}
	
	protected IController getController()
	{
		return controller;
	}

}
