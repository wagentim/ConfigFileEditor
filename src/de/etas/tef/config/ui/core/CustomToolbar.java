package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;

public abstract class CustomToolbar extends AbstractComposite
{
	protected ToolBar toolbar;
	protected boolean showText = false;

	public CustomToolbar(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected CustomToolbar setShowText(boolean value)
	{
		showText = value;
		return this;
	}

	protected void initComponent()
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		this.setLayout(layout);
		this.setLayoutData(gd);
		toolbar = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	}
	
	protected ToolItem addNewSTDItem(String name, Image image, SelectionListener listener, String tooltip)
	{
		ToolItem ti = initToolItem(name, image, SWT.NONE, tooltip);
		ti.addSelectionListener(listener);
		return ti;
	}
	
	protected ToolItem addDropdownItem(String name, Image image, String[] items, String tooltip)
	{
		ToolItem ti = initToolItem(name, image, SWT.DROP_DOWN, tooltip);
		DropdownToolItem itm = new DropdownToolItem(ti);
		for(String s : items)
		{
			itm.add(s);
		}
		ti.addSelectionListener(itm);
		return ti;
	}
	
	private ToolItem initToolItem(String name, Image image, int type, String tooltip)
	{
		ToolItem ti = new ToolItem(toolbar, type);

		if(showText)
		{
			ti.setText(name);
		}
		
		ti.setImage(image);
		ti.setToolTipText(tooltip);
		
		return ti;
	}
	
	class DropdownToolItem extends SelectionAdapter
	{
		private ToolItem dropdown;

		private Menu menu;

		public DropdownToolItem(ToolItem dropdown)
		{
			this.dropdown = dropdown;
			menu = new Menu(dropdown.getParent().getShell());
		}

		public void add(String item)
		{
			MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText(item);
			menuItem.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent event)
				{
					MenuItem selected = (MenuItem) event.widget;
					dropdown.setText(selected.getText());
				}
			});
		}

		public void widgetSelected(SelectionEvent event)
		{
			if (event.detail == SWT.ARROW)
			{
				ToolItem item = (ToolItem) event.widget;
				Rectangle rect = item.getBounds();
				Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
				menu.setLocation(pt.x, pt.y + rect.height);
				menu.setVisible(true);
			} else
			{
				System.out.println(dropdown.getText() + " Pressed");
			}
		}
	}
	
}
