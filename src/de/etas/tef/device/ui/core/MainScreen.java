package de.etas.tef.device.ui.core;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.controller.InfoBlockWriter;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.device.ui.source.SourceConfigComposite;
import de.etas.tef.device.ui.target.TargetConfigComposite;

public class MainScreen
{
	public MainScreen()
	{
		IController controller = new MainController();
		
		Display display = new Display();
        Shell shell = new Shell(display);

        initMainScreen(shell);
        initMainComponents(shell, controller);
        new OptionComposite(shell, SWT.BORDER, controller);
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
	}
	
	private void initMainComponents(Shell shell, IController controller)
	{
		SashForm main = new SashForm(shell, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);
		
		SashForm sf = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);
		
		new SourceConfigComposite(sf, SWT.BORDER, controller);
        new TargetConfigComposite(sf, SWT.BORDER, controller);
        
        Text txtInfoBlock = new Text(main, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		new InfoBlockWriter(txtInfoBlock, controller);
		
		main.setWeights(new int[]{4, 1});
	}
	
	private void initMainScreen(Shell shell)
	{
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.pack();
		shell.setBounds((area.width - Constants.MAIN_SCREEN_WIDTH) / 2, (area.height - Constants.MAIN_SCREEN_HEIGHT)/2, Constants.MAIN_SCREEN_WIDTH, Constants.MAIN_SCREEN_HEIGHT);
		shell.setText(Constants.TXT_APP_TITLE);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
	}
	
}
