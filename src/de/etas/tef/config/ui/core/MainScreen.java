package de.etas.tef.config.ui.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.InfoBlockWriter;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;

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
	
	
	public final Image IMAGE_TITLE;
	public final Image IMAGE_PIN;
	public final Image IMAGE_EXIT;
	public final Image IMAGE_ABOUT;
	public final Image IMAGE_CONNECT;
	public final Image IMAGE_DISCONNECT;

	
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
		
		IMAGE_TITLE = new Image(display, "icons/file_24.png");
		IMAGE_PIN = new Image(display, "icons/pin.png");
		IMAGE_EXIT = new Image(display, "icons/exit.png");
		IMAGE_ABOUT = new Image(display, "icons/about.png");
		IMAGE_CONNECT = new Image(display, "icons/connect.png");
		IMAGE_DISCONNECT = new Image(display, "icons/disconnect.png");
		
		Shell shell = new Shell(display);
		shell.setText(Constants.TXT_APP_TITLE);
		shell.setImage(IMAGE_TITLE);
		
		initMainScreen(shell);
		initMenu(shell);
		initMainComponents(shell);
		initStatusBar(shell);
//		new OptionComposite(shell, SWT.BORDER, controller, CompositeID.COMPOSITE_ALONE);

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
	
	private void initStatusBar(Shell shell)
	{
		Group statusBarGroup = new Group(shell, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumHeight = 30;
		statusBarGroup.setLayoutData(gd);
		
		statusBarGroup.setEnabled(false);
		Label dateLabel = new Label(statusBarGroup, SWT.NONE);
		dateLabel.setText(" " + sdf.format(new Date()) + " ");
		dateLabel.setBounds(2, 10, 120, 16);
		
		Label statusLabel = new Label(statusBarGroup, SWT.NONE);
		statusLabel.setText(" Here is a status message ");
		statusLabel.setBounds(124, 10, 200, 16);
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
