package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.MainController;

public class MainDisplayComponent
{
	private final Shell shell;
	private final MainController controller;
	
	public MainDisplayComponent(final Shell shell, final MainController controller)
	{
		this.shell = shell;
		this.controller = controller;
		
		createDisplayComponent();
	}
	
	private void createDisplayComponent()
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		shell.setLayoutData(gd);
		
		SashForm mainArea = new SashForm(shell, SWT.HORIZONTAL);
		mainArea.setBackground(controller.getColorFactory().getColorBackground());
		gd = new GridData(GridData.FILL_BOTH);
		mainArea.setLayoutData(gd);
		new FileManageComposite(mainArea, SWT.NONE, controller);
		new ConfigMainComposite(mainArea, SWT.BORDER, controller);
		
		mainArea.setWeights(new int[] {1, 0});
	}
}
