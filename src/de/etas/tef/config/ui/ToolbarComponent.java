package de.etas.tef.config.ui;

import java.util.List;

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

import de.etas.tef.config.controller.IMessage;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.editor.message.MessageManager;

public class ToolbarComponent extends AbstractComposite
{
	
	private ToolBar toolbar;

	public ToolbarComponent(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		toolbar = new ToolBar(this, SWT.NONE);
		toolbar.setBackground(controller.getColorFactory().getColorBackground());
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}
	
	public void addSeperator()
	{
		new ToolItem(toolbar, SWT.SEPARATOR | SWT.VERTICAL);
	}
	
	public void addItem(String name, List<Image> images, SelectionListener listener)
	{
		ToolItem ti = new ToolItem(toolbar, SWT.PUSH);
		if(name != null && !name.isEmpty())
		{
			ti.setText(name);
		}
		
		if(images != null && images.size() > 0)
		{
			int counter = 0;
			
			for(Image i : images)
			{
				if(counter == 0 && i != null)
				{
					ti.setImage(i);
				}
				else if(counter == 1 && i != null)
				{
					ti.setHotImage(i);
				}
				else if(counter == 2 && i != null)
				{
					ti.setDisabledImage(i);
				}
				
				counter++;
			}
		}
		
		ti.addSelectionListener(listener);
	}
	
	private ToolItem initToolItem(String name, Image image, int type, String tooltip)
	{
		ToolItem ti = new ToolItem(toolbar, type);

		ti.setImage(image);
		ti.setToolTipText(tooltip);
		
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
					String text = selected.getText();
					MessageManager.INSTANCE.sendMessage(IMessage.MSG_TOOLBAR_ITEM, text);
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
