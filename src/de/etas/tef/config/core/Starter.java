package de.etas.tef.config.core;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.controller.MainController;

/**
 * Handle start process
 * 
 * @author UIH9FE
 *
 */
public final class Starter
{
	
	public static void main(String[] args)
	{
		Display display = new Display();
		new MainController(display);
	}
}
