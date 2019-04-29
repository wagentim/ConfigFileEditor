package de.etas.tef.device.ui.core;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.DefaultController;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.listener.IActionListener;

public abstract class AbstractComposite extends Composite implements IActionListener
{
	
	private IController controller = new DefaultController();

	public AbstractComposite(Composite parent, int style, IController controller)
	{
		super(parent, style);
		
		if(null != controller)
		{
			this.controller = controller;
		}
		
		ActionManager.INSTANCE.addActionListener(this);
	}
	
	protected IController getController()
	{
		return controller;
	}
}
