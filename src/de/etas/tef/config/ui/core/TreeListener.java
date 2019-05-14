package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.entity.CellIndex;

public class TreeListener extends CellEditingListener
{

	public TreeListener(Tree tree, IController controller, int compositeID)
	{
		super(tree, controller, compositeID);
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
		getController().updateBlockName(oldValue, newValue);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent event)
	{
		
	}

	@Override
	protected CellIndex getCell()
	{
		return null;
	}
}
