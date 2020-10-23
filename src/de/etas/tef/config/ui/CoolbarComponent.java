package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.MainController;

public class CoolbarComponent
{
	private final Shell shell;
	private final MainController controller;
	
	public CoolbarComponent(final Shell shell, final MainController controller)
	{
		this.shell = shell;
		this.controller = controller;
		createToolbar();
	}
	
	private void createToolbar()
	{
		CoolBar coolbar = new CoolBar(shell, SWT.NONE);
	}
}
