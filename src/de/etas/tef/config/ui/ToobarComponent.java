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
		
	}
	
}
