package de.etas.tef.config.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;

public class ToobarComponent extends AbstractComposite
{
	
	private ToolBar toolbar;

	public ToobarComponent(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite() 
	{
		super.initComposite();
		toolbar = new ToolBar(this, SWT.PUSH);
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}
	
	public void addSeperator()
	{
		new ToolItem(toolbar, SWT.SEPARATOR);
	}
	
	public void addButton(String name, List<Image> images, SelectionListener listener)
	{
		ToolItem ti = new ToolItem(toolbar, SWT.FLAT);
		
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
	}
	
}
