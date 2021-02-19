package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.constant.IImageConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.listener.IMessageListener;
import de.etas.tef.editor.message.MessageManager;

public class MainScreen implements IMessageListener
{
	private final MainController controller;
	
	private StatusbarComponent statusBar;

	public MainScreen(final Display display, final MainController controller)
	{
		this.controller = controller;
		MessageManager.INSTANCE.addMessageListener(this);
		
		Shell shell = new Shell(display);
		shell.setText(IConstants.TXT_APP_TITLE);
		shell.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_TITLE));
		
		shell.addShellListener(new ShellListener()
		{
			
			@Override
			public void shellIconified(ShellEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeiconified(ShellEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellDeactivated(ShellEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void shellClosed(ShellEvent arg0)
			{
				System.exit(0);
			}
			
			@Override
			public void shellActivated(ShellEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		initialShell(shell);
		new MenuComponent(shell, controller);
		new MainDisplayComponent(shell, controller);
		
		statusBar = new StatusbarComponent(shell, SWT.BORDER, controller);
		
		Runnable timer = new Runnable()
		{
			@Override
			public void run()
			{
				statusBar.updateTime();
				display.timerExec(1000, this);
			}
		};
		display.timerExec(1000, timer);
		
		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		display.dispose();
	}

	/** Define the main window location */
	private void initialShell(Composite shell)
	{
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.pack();
		shell.setBounds((Math.abs(area.width - IConstants.MAIN_SCREEN_WIDTH)) / 2,
				Math.abs((area.height - IConstants.MAIN_SCREEN_HEIGHT)) / 2, IConstants.MAIN_SCREEN_WIDTH,
				IConstants.MAIN_SCREEN_HEIGHT);
		shell.setBackground(controller.getColorFactory().getColorBackground());
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
	}
}
