package de.etas.tef.config.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IImageConstants;

public class DropdownMenuSelectionListener implements SelectionListener
{
	
	private final Composite parent;
	private final MainController controller;
	
	public DropdownMenuSelectionListener(final Composite parent, final MainController controller)
	{
		this.parent = parent;
		this.controller = controller;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0)
	{
		Menu menu = new Menu(parent.getShell(), SWT.POP_UP);

        MenuItem item1 = new MenuItem(menu, SWT.PUSH);
        item1.setText("Search File List");
        item1.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH));
        MenuItem item2 = new MenuItem(menu, SWT.PUSH);
        item2.setText("Search File Content");

        Point loc = parent.getLocation();
        Rectangle rect = parent.getBounds();

        Point mLoc = new Point(loc.x-1, loc.y+rect.height);
        menu.setLocation(parent.getDisplay().map(parent.getParent(), null, mLoc));
        menu.setVisible(true);
		
	}

}