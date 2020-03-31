package de.etas.tef.config.ui.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.InfoBlockWriter;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;
import de.etas.tef.editor.action.ActionManager;

public class MainScreen implements IActionListener
{
	private ConfigComposite leftConfigComposite = null;
	private ConfigComposite rightConfigComposite = null;
	private SashForm configCompositeSashForm = null;
	private final MainController controller;
	private MenuItem leftPaneItem;
	private MenuItem rightPaneItem;
	private MenuItem showInfoPaneItem;
	private MenuItem connectItem;
	private StyledText txtInfoBlock;
	private SashForm main;
	private Label dateLabel;
	
	
	public final Image IMAGE_TITLE;
	public final Image IMAGE_PIN;
	public final Image IMAGE_EXIT;
	public final Image IMAGE_ABOUT;
	public final Image IMAGE_CONNECT;
	public final Image IMAGE_DISCONNECT;
	public final Image IMAGE_TIME;

	
	private static boolean isLeftSelected = true;
	private static boolean isRightSelected = false;
	private static boolean isConnected = false;
	private static boolean isInfoPaneShow = false;
	
	private static final int fromLeft = 0x00;
	private static final int fromRight = 0x01;
	private static final int fromConnect = 0x02;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	

	public MainScreen()
	{
		controller = new MainController();
		ActionManager.INSTANCE.addActionListener(this);
		
		Display display = new Display();
		
		IMAGE_TITLE = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/file_24.png"));
		IMAGE_PIN = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/pin.png"));
		IMAGE_EXIT = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/exit.png"));
		IMAGE_ABOUT = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/about.png"));
		IMAGE_CONNECT = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/connect.png"));
		IMAGE_DISCONNECT = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/disconnect.png"));
		IMAGE_TIME = new Image(display, MainScreen.class.getClassLoader().getResourceAsStream("icons/connect.png"));
		
		Shell shell = new Shell(display);
		shell.setText(Constants.TXT_APP_TITLE);
		shell.setImage(IMAGE_TITLE);
		
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
		
		initMainScreen(shell);
		initMenu(shell);
		initMainComponents(shell);
		
		initStatusBar(shell);
		
		Runnable timer = new Runnable()
		{
			public void run()
			{
				dateLabel.setText(" " + sdf.format(new Date()) + " ");
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

	private void initDaemonThread()
	{
		Thread updateTimeThread = new Thread()
		{
			public void run()
			{
				while (true)
				{
					Display.getDefault().syncExec(new Runnable()
					{
						@Override
						public void run()
						{
							dateLabel.setText(getTime());
						}
					});

					try
					{
						sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		};
		updateTimeThread.setDaemon(true);
		updateTimeThread.start();
	}

	private void initMenu(Shell shell)
	{
		Menu menuBar = new Menu(shell, SWT.BAR);
		
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");

	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);

	    MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileExitItem.setText("E&xit");
	    fileExitItem.setImage(IMAGE_EXIT);
	    fileExitItem.addSelectionListener(new SelectionAdapter()
		{
	    	@Override
			public void widgetSelected(SelectionEvent arg0)
			{
	    		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
	    		mb.setText("Exit Confirmation");
				mb.setMessage("Do you really want to Exit?");

				boolean done = mb.open() == SWT.YES;
				
				if( done )
				{
					System.exit(0);
				}
			}
		});
	    
	    MenuItem functionMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    functionMenuHeader.setText("F&unction");
	    
	    Menu functionMenu = new Menu(shell, SWT.DROP_DOWN);
	    functionMenuHeader.setMenu(functionMenu);
	    
	    connectItem = new MenuItem(functionMenu, SWT.PUSH);
	    connectItem.setText("&Disconnect");
	    connectItem.setImage(IMAGE_DISCONNECT);
	    connectItem.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				isConnected = !isConnected;
				checkOptionSelection(fromConnect);
				connectItem.setText(isConnected ? "Connet" : "Disconnect");
				controller.setConnected(isConnected);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    
	    MenuItem windowMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    windowMenuHeader.setText("Window");
	    
	    Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
	    windowMenuHeader.setMenu(windowMenu);
	    
	    leftPaneItem = new MenuItem(windowMenu, SWT.PUSH);
	    leftPaneItem.setText("Show/Hide Left Pane");
	    leftPaneItem.setImage(IMAGE_PIN);
	    leftPaneItem.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				isLeftSelected = !isLeftSelected;
				checkOptionSelection(fromLeft);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    
	    rightPaneItem = new MenuItem(windowMenu, SWT.PUSH);
	    rightPaneItem.setText("Show/Hide Right Pane");
	    rightPaneItem.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				isRightSelected = !isRightSelected;
				checkOptionSelection(fromRight);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    
	    showInfoPaneItem = new MenuItem(windowMenu, SWT.PUSH);
	    showInfoPaneItem.setText("&Show/Hide Info Pane");
	    showInfoPaneItem.setImage(IMAGE_ABOUT);
	    showInfoPaneItem.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				isInfoPaneShow = !isInfoPaneShow;
				txtInfoBlock.setVisible(isInfoPaneShow);
				if(isInfoPaneShow)
				{
					main.setWeights(new int[]{5, 1});
				}
				else
				{
					main.setWeights(new int[]{1, 0});
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	    
	    MenuItem aboutMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    aboutMenuHeader.setText("&?");

	    Menu aboutMenu = new Menu(shell, SWT.DROP_DOWN);
	    aboutMenuHeader.setMenu(aboutMenu);

	    MenuItem aboutItem = new MenuItem(aboutMenu, SWT.PUSH);
	    aboutItem.setText("&About");
	    
	    shell.setMenuBar(menuBar);
	}
	
	private String getTime()
	{
		return (" " + sdf.format(new Date()) + " ");
	}
	
	private void initStatusBar(Shell shell)
	{
		
		Composite statusbar = new Composite(shell, SWT.BORDER);

        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.heightHint = 16;
        statusbar.setLayoutData(gridData);
        RowLayout layout = new RowLayout();
        layout.marginLeft = layout.marginTop = 0;
        statusbar.setLayout(layout);
        
        Label image = new Label(statusbar, SWT.NONE);
        image.setImage(IMAGE_TIME);
        
        dateLabel = new Label(statusbar, SWT.BOLD);
        dateLabel.setLayoutData(new RowData(150, -1));
        dateLabel.setText(" "+sdf.format(new Date())+" ");
        
        new Label(statusbar, SWT.SEPARATOR | SWT.VERTICAL);
	}
	
	protected void checkOptionSelection(int fromWhich)
	{
		if(isLeftSelected && isRightSelected)
		{
		}
		else 
		{
			isConnected = false;
			
			if( !isLeftSelected && !isRightSelected)
			{
				switch(fromWhich)
				{
					case fromLeft: isRightSelected = true; break;
					case fromRight: isLeftSelected = true; break;
				}
			}
		}
		
		if(isLeftSelected)
		{
			leftPaneItem.setImage(IMAGE_PIN);
		}
		else
		{
			leftPaneItem.setImage(null);
		}
		
		if(isRightSelected)
		{
			rightPaneItem.setImage(IMAGE_PIN);
		}
		else
		{
			rightPaneItem.setImage(null);
		}
		
		connectItem.setImage(isConnected ? IMAGE_CONNECT : IMAGE_DISCONNECT);
		
		ActionManager.INSTANCE.sendAction(Constants.ACTION_COMPOSITE_CHANGED, CompositeID.COMPOSITE_ALONE, new boolean[] {isLeftSelected, isRightSelected});
	}

	private void initMainComponents(Composite shell)
	{
		main = new SashForm(shell, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);

		configCompositeSashForm = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		configCompositeSashForm.setLayoutData(gd);
		
		leftConfigComposite = new ConfigComposite(configCompositeSashForm, SWT.BORDER, controller, CompositeID.COMPOSITE_LEFT);
		rightConfigComposite = new ConfigComposite(configCompositeSashForm, SWT.BORDER, controller, CompositeID.COMPOSITE_RIGHT);
		
		txtInfoBlock = new StyledText(main, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		txtInfoBlock.setVisible(false);
		
		new InfoBlockWriter(txtInfoBlock, controller);

		main.setWeights(new int[]
		{ 1, 0 });
		
		rightConfigComposite.setVisible(false);
		configCompositeSashForm.setWeights(new int[] {1, 0});
	}

	private void initMainScreen(Composite shell)
	{
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.pack();
		shell.setBounds((Math.abs(area.width - Constants.MAIN_SCREEN_WIDTH)) / 2,
				Math.abs((area.height - Constants.MAIN_SCREEN_HEIGHT)) / 2, Constants.MAIN_SCREEN_WIDTH,
				Constants.MAIN_SCREEN_HEIGHT);

		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
	}

	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		if (Constants.ACTION_COMPOSITE_CHANGED == type)
		{
			boolean[] value = (boolean[])content;
			
			boolean left = value[0];
			boolean right = value[1];
			
			leftConfigComposite.setVisible(left);
			rightConfigComposite.setVisible(right);
			
			if(left && right)
			{
				configCompositeSashForm.setWeights(new int[]{ 1, 1 });
			}
			else if(!left && right)
			{
				configCompositeSashForm.setWeights(new int[]{ 0, 1 });
			}
			else if(left && !right)
			{
				configCompositeSashForm.setWeights(new int[]{ 1, 0 });
			}
		}
	}
}
