package de.etas.tef.config.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;

public abstract class TreeSelectionListener extends SelectionAdapter
{
	protected static final Logger logger = LoggerFactory.getLogger(TreeSelectionListener.class);
	
	protected final Tree tree;
	protected final MainController controller;
	protected TreeEditor editor = null;
	
	protected String oldValue = IConstants.EMPTY_STRING;
	protected String newValue = IConstants.EMPTY_STRING;
	
	public TreeSelectionListener(final Tree tree, final MainController controller)
	{
		this.tree = tree;
		this.controller = controller;
	}
	
	@Override
	public void widgetSelected(SelectionEvent event)
	{
		
		Object scr = event.getSource();
		
		if(scr instanceof MenuItem)
		{
			MenuItem mi = (MenuItem)scr;
			logger.info("Selected Menu: {}", mi.getText());
			handleRightMenu(mi);
		}
	}
	
	protected TreeItem getSelectedTreeItem()
	{
		TreeItem ti = tree.getSelection()[0];
		return ti;
	}
	
	protected TreeEditor getNewTreeEditor()
	{
		TreeEditor editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;
		return editor;
	}
	
	protected void disposeEditor()
	{
		if( null == editor )
		{
			return;
		}
		
		Text oldEditor = (Text)editor.getEditor();
		
		if( null == oldEditor )
		{
			return;
		}

		if (newValue.isEmpty())
		{
			oldEditor.setText(oldValue);
		}

		oldEditor.dispose();

		editor.setEditor(null);
	}
	
	protected void handleTreeItemEdit(TreeItem item)
	{
		disposeEditor();

        if (item == null)
        {
          return;
        }
        
        if( editor == null )
        {
        	editor = getNewTreeEditor();
        }
        
        Text newEditor = new Text(tree, SWT.BORDER);
        newValue = oldValue = item.getText();
        
        newEditor.setText(oldValue);
        
        newEditor.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyReleased(KeyEvent event)
			{
				if(event.keyCode == SWT.CR)
				{
					
					if (!newValue.equalsIgnoreCase(oldValue))
					{
						if(updateTreeItemValue(item))
						{
							disposeEditor();
						}
					}
				}
				else if(event.keyCode == SWT.ESC)
				{
					newValue = IConstants.EMPTY_STRING;
					disposeEditor();
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
			public void modifyText(ModifyEvent event)
			{
				Text text = (Text) editor.getEditor();
				
				if(null == text)
				{
					return;
				}
				
				newValue = text.getText();
				item.setText(newValue);
			}

		});
        
        newEditor.selectAll();
        newEditor.setFocus();
        editor.setEditor(newEditor, item);
	}
	
	protected abstract boolean updateTreeItemValue(TreeItem item);
	protected abstract void handleRightMenu(MenuItem item);
	protected abstract boolean isNameDuplicated(TreeItem parent, String name);
}
