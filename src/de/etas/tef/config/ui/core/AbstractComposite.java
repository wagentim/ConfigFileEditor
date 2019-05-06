package de.etas.tef.config.ui.core;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.DefaultController;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.listener.IActionListener;

public abstract class AbstractComposite extends Composite implements IActionListener
{
	
	private IController controller = null;

	public AbstractComposite(Composite parent, int style, IController controller)
	{
		super(parent, style);
		
		if(null != controller)
		{
			this.controller = controller;
		}
		else
		{
			this.controller = new DefaultController();
		}
		
		ActionManager.INSTANCE.addActionListener(this);
	}
	
	protected IController getController()
	{
		return controller;
	}
	
	protected abstract int getCompositeID();
}
