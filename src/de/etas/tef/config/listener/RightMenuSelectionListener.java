package de.etas.tef.config.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RightMenuSelectionListener implements SelectionListener
{
	private static final Logger logger = LoggerFactory.getLogger(RightMenuSelectionListener.class);
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		
	}

	@Override
	public void widgetSelected(SelectionEvent event)
	{
		
		Object scr = event.getSource();
		if(scr instanceof MenuItem)
		{
			MenuItem mi = (MenuItem)scr;
			logger.info("Selected Menu: {}", mi.getText());
		}
	}
	
	protected abstract void handleMenu(String text);

}
