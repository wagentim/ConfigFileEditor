package de.etas.tef.config.worker.checker;

import de.etas.tef.config.controller.MainController;

public abstract class AbstractChecker implements IChecker 
{
	protected final MainController controller;
	
	public AbstractChecker(final MainController controller) 
	{
		this.controller = controller;
	}
}