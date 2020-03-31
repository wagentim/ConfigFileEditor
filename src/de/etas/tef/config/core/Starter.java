package de.etas.tef.config.core;

import de.etas.tef.config.controller.GitController;
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
		MainController mc = new MainController();
		
		GitController gc = new GitController();
		mc.setGitController(gc);
	}
}
