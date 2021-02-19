package de.etas.tef.config.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;

public class CheckEnableSelectionListener implements SelectionListener
{
	private final Control control;
	
	public CheckEnableSelectionListener(final Control control)
	{
		this.control = control;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent event)
	{
		Button b = (Button)event.getSource();
		control.setEnabled(b.getSelection());
	}

}
