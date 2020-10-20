package de.etas.tef.config.ui.composites;

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
import de.etas.tef.config.helper.IConstants;

public class TreeListener extends CellEditingListener
{
	private final MainController controller;
	private final Tree tree;

	public TreeListener(Tree tree, MainController controller)
	{
		super(tree, controller);
		this.controller = controller;
		this.tree = tree;
	}
	
	protected Tree getTree()
	{
		return tree;
	}
	
	
	@Override
	protected void updateWithNewValue()
	{
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
			handleRightMenu(event);
		}
	}
	
	protected void handleRightMenu(SelectionEvent event)
	{
		String text = ((MenuItem)event.getSource()).getText();
		
		if( text.contentEquals(IConstants.TXT_BTN_DELETE) )
		{
		}
		else if( text.contentEquals(IConstants.TXT_COPY) )
		{
			sendCopyMessage();
		}
		else if( text.contentEquals(IConstants.TXT_PASTE) )
		{
			sendPasteMessage();
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

	@Override
	protected ControlEditor getNewEditor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Item getSelectedItem(TypedEvent event)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
