package de.etas.tef.config.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;

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
		new ToolItem(toolbar, SWT.SEPARATOR);
	}
	
	public void addButton(String name, List<Image> images, SelectionListener listener)
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
	}
}
