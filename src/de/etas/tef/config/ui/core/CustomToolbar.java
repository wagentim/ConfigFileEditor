package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
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
		super.initComposite();
		toolbar = new ToolBar(this, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
	}
	
	protected ToolItem addNewItem(String name, int style, Image image)
	{
		ToolItem ti = new ToolItem(toolbar, style);

		if(showText)
		{
			ti.setText(name);
		}
		
		ti.setImage(image);
		
		return ti;
	}
	
}
