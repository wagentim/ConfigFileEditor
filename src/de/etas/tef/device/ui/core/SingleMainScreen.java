package de.etas.tef.device.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.Constants;

public class SingleMainScreen
{
	public SingleMainScreen()
	{
		IController controller = new MainController();
		
		Display display = new Display();
        Shell shell = new Shell(display);

        initMainScreen(shell);
        
        new SelectComposite(shell, SWT.NONE, controller);
        
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
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
