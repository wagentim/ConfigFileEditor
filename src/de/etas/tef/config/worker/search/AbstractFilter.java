package de.etas.tef.config.worker.search;

import de.etas.tef.config.controller.MainController;

public abstract class AbstractFilter implements IFilter
{
	protected final MainController controller;
	
	public AbstractFilter(final MainController controller)
	{
		this.controller = controller;
	}
}
