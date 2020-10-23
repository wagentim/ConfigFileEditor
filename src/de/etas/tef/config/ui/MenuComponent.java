package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IImageConstants;

public class MenuComponent
{
	private final Shell shell;
	private final MainController controller;

	public MenuComponent(final Shell shell, final MainController controller)
	{
		this.shell = shell;
		this.controller = controller;
		createMenu();
	}

	private void createMenu()
	{
		Menu menuBar = new Menu(shell, SWT.BAR);
		
		createFileMenu(menuBar);
		createFunctionMenu(menuBar);
		createWindowMenu(menuBar);
		createAboutMenu(menuBar);
		shell.setMenuBar(menuBar);
	}
	
	private void createFileMenu(final Menu menuBar)
	{
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("E&xit");
		fileExitItem.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_EXIT));
		fileExitItem.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
				mb.setText("Exit Confirmation");
				mb.setMessage("Do you really want to Exit?");

				boolean done = mb.open() == SWT.YES;

				if (done)
				{
					System.exit(0);
				}
			}
		});
	}
	
	private void createFunctionMenu(final Menu menuBar)
	{
		MenuItem functionMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		functionMenuHeader.setText("F&unction");

		Menu functionMenu = new Menu(shell, SWT.DROP_DOWN);
		functionMenuHeader.setMenu(functionMenu);

		MenuItem connectItem = new MenuItem(functionMenu, SWT.PUSH);
		connectItem.setText("&Settings");
		connectItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void createWindowMenu(final Menu menuBar)
	{
		MenuItem windowMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		windowMenuHeader.setText("Window");

		Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
		windowMenuHeader.setMenu(windowMenu);

		MenuItem leftPaneItem = new MenuItem(windowMenu, SWT.PUSH);
		leftPaneItem.setText("Show/Hide Left Pane");
		leftPaneItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		MenuItem rightPaneItem = new MenuItem(windowMenu, SWT.PUSH);
		rightPaneItem.setText("Show/Hide Right Pane");
		rightPaneItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		MenuItem showInfoPaneItem = new MenuItem(windowMenu, SWT.PUSH);
		showInfoPaneItem.setText("&Show/Hide Info Pane");
		showInfoPaneItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent event)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void createAboutMenu(final Menu menuBar)
	{
		MenuItem aboutMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		aboutMenuHeader.setText("&?");

		Menu aboutMenu = new Menu(shell, SWT.DROP_DOWN);
		aboutMenuHeader.setMenu(aboutMenu);

		MenuItem aboutItem = new MenuItem(aboutMenu, SWT.PUSH);
		aboutItem.setText("&About");
	}
}
