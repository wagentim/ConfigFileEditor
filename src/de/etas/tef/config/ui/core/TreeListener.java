package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.helper.Constants;

public class TreeListener extends CellEditingListener
{
	private final MainController controller;

	public TreeListener(Tree tree, MainController controller)
	{
		super(tree, controller);
		this.controller = controller;
	}
	
	protected ControlEditor getNewEditor()
	{
		TreeEditor editor = new TreeEditor(getTree());
		editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    editor.minimumWidth = 50;
		return editor;
	}
	
	private Tree getTree()
	{
		return (Tree)getComposite();
	}

	@Override
	protected void updateWithNewValue()
	{
	}

	@Override
	protected Item getSelectedItem(TypedEvent event)
	{
		return getTree().getSelection()[0];
	}

	@Override
	protected void setNewEditor(Text newEditor, Item item)
	{
		((TreeEditor)editor).setEditor(newEditor, (TreeItem)item);
	}

	@Override
	protected void setNewValueByModify()
	{
		getTree().getSelection()[0].setText(newValue);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}

	@Override
	public void widgetSelected(SelectionEvent event)
	{
		if(event.getSource() instanceof MenuItem)
		{
			String text = ((MenuItem)event.getSource()).getText();
			
			if( text.contentEquals(Constants.TXT_BTN_ADD) )
			{
				ConfigBlock cb = new ConfigBlock();
				cb.setBlockName(Constants.TXT_TEMP);
			}
			else if( text.contentEquals(Constants.TXT_BTN_DELETE) )
			{
			}
			else if( text.contentEquals(Constants.TXT_COPY) )
			{
				sendCopyMessage();
			}
			else if( text.contentEquals(Constants.TXT_PASTE) )
			{
				sendPasteMessage();
			}
		}
	}
	
	private void sendCopyMessage()
	{
	}
	
	private void sendPasteMessage()
	{
	}

	@Override
	protected CellIndex getCell()
	{
		return null;
	}

	@Override
	protected void keyCopyPressed()
	{
		sendCopyMessage();
	}

	@Override
	protected void keyPastePressed()
	{
		sendPasteMessage();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub
		
	}
}
