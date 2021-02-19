package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import de.etas.tef.config.controller.MainController;

public class TableEditor extends AbstractComposite 
{
	
	private Table table;

	public TableEditor(Composite parent, int style, MainController controller) 
	{
		super(parent, style, controller);
	}
	
	@Override
	protected void initComposite() 
	{
		table = new Table(this, SWT.NONE);
	}

	@Override
	public void receivedAction(int type, Object content) {
		// TODO Auto-generated method stub

	}

}
