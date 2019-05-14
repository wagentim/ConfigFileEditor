package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;

public class TreeMouseListener implements MouseListener, IActionListener
{

	private TreeEditor editor = null;
	private final Tree tree;
	private final IController controller;
	private String newValue = Constants.EMPTY_STRING;
	protected boolean isTextChanged = false;
	protected boolean isLocked = true;
	private final int compositeID;
	
	public TreeMouseListener(Tree tree, IController controller, int compositeID)
	{
		this.tree = tree;
		this.controller = controller;
		this.compositeID = compositeID;
		
		editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;

	    ActionManager.INSTANCE.addActionListener(this);
	}
	
	protected TreeEditor getTreeEditor()
	{
		return editor;
	}
	
	@Override
	public void mouseDoubleClick(MouseEvent event)
	{
		
		disposeOldEditor();
        final TreeItem item = tree.getSelection()[0];
        
        if (item == null)
        {
          return;
        }
        
        Text newEditor = new Text(tree, SWT.NONE);
        String text = item.getText();
        
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
				editor.getItem().setText(newValue);
				isTextChanged = true;
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
		Control oldEditor = getTreeEditor().getEditor();

		if (oldEditor != null)
		{
			oldEditor.dispose();
			if (isTextChanged)
			{
			}
		}
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
	}
	
	protected int getCompositeID()
	{
		return this.compositeID;
	}

}
