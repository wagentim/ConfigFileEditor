package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;

public class TreeListener extends CellEditingListener
{

	private TreeEditor editor = null;
	private String newValue = Constants.EMPTY_STRING;
	private String oldValue = Constants.EMPTY_STRING;
	protected boolean isLocked = true;
	
	public TreeListener(Tree tree, IController controller, int compositeID)
	{
		super(tree, controller, compositeID);
		
		editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;
	}
	
	protected TreeEditor getTreeEditor()
	{
		return editor;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		disposeOldEditor();

		final TreeItem item = getTree().getSelection()[0];
        
        if (item == null)
        {
          return;
        }
        
        Text newEditor = new Text(getTree(), SWT.NONE);
        oldValue = item.getText();
        
        newEditor.setText(oldValue);
        
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
				editor.getItem().setText(newValue);
			}

		});
        
        newEditor.selectAll();
        newEditor.setFocus();
        editor.setEditor(newEditor, item);
	}

	@Override
	public void mouseDown(MouseEvent arg0)
	{
		disposeOldEditor();
	}

	protected void disposeOldEditor()
	{
		disposeEditor(newValue);
	}

	@Override
	public void mouseUp(MouseEvent arg0)
	{
		
	}
	
	protected void disposeEditor(String newValue)
	{
		Text oldEditor = (Text)getTreeEditor().getEditor();

		if (oldEditor != null)
		{
			if(newValue.isEmpty())
			{
				oldEditor.setText(oldValue);
			}
			
			oldEditor.dispose();
			if ( !newValue.equalsIgnoreCase(oldValue))
			{
				
			}
		}
	}
	
	protected String getInfoText() {return Constants.EMPTY_STRING;}
	
	protected ConfigBlock getConfigBlock() {return null;}
	
	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent key)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent key)
	{
		// TODO Auto-generated method stub
		
	}
	
	private Tree getTree()
	{
		return (Tree)getComposite();
	}
}
