package de.etas.tef.config.ui.composites;

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

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.core.IImageConstants;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.listener.IMessageListener;
import de.etas.tef.editor.message.MessageManager;

public class MainScreen implements IMessageListener
{
	private final MainController controller;
	
	private SashForm configCompositeSashForm = null;
	private MenuItem leftPaneItem;
	private MenuItem rightPaneItem;
	private MenuItem showInfoPaneItem;
	private MenuItem connectItem;
	private StyledText txtInfoBlock;
	private SashForm main;
	private Label dateLabel;

	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	

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

	private void initMenu(Shell shell)
	{
		Menu menuBar = new Menu(shell, SWT.BAR);
		
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
	    
	    MenuItem windowMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    windowMenuHeader.setText("Window");
	    
	    Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
	    windowMenuHeader.setMenu(windowMenu);
	    
	    leftPaneItem = new MenuItem(windowMenu, SWT.PUSH);
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
	    
	    rightPaneItem = new MenuItem(windowMenu, SWT.PUSH);
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
	    
	    showInfoPaneItem = new MenuItem(windowMenu, SWT.PUSH);
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
		
		Composite statusbar = new Composite(shell, SWT.BORDER);

        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.heightHint = 20;
        statusbar.setLayoutData(gridData);
        RowLayout layout = new RowLayout();
        layout.marginLeft = layout.marginTop = 0;
        statusbar.setLayout(layout);
        
        Label image = new Label(statusbar, SWT.NONE);
        
        dateLabel = new Label(statusbar, SWT.BOLD);
        dateLabel.setLayoutData(new RowData(150, -1));
        dateLabel.setText(" "+sdf.format(new Date())+" ");
        
        new Label(statusbar, SWT.SEPARATOR | SWT.VERTICAL);
	}
	
	private void initMainComponents(Composite shell)
	{
		main = new SashForm(shell, SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);
		
		new GitFileMainComposite(main, SWT.BORDER, controller);

//		configCompositeSashForm = new SashForm(main, SWT.HORIZONTAL);
//		gd = new GridData(GridData.FILL_BOTH);
//		configCompositeSashForm.setLayoutData(gd);
		
		new ConfigMainComposite(main, SWT.BORDER, controller);
		
		main.setWeights(new int[] { 1, 3 });
//		
//		leftConfigComposite = new ConfigMainComposite(configCompositeSashForm, SWT.BORDER, controller);
//		rightConfigComposite = new ConfigMainComposite(configCompositeSashForm, SWT.BORDER, controller);
//		
//		txtInfoBlock = new StyledText(main, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
//		gd = new GridData(GridData.FILL_BOTH);
//		gd.horizontalSpan = 3;
//		txtInfoBlock.setLayoutData(gd);
//		txtInfoBlock.setEditable(false);
//		txtInfoBlock.setVisible(false);
//		
//		new InfoBlockWriter(txtInfoBlock, controller);
//
//		main.setWeights(new int[]
//		{ 1, 0 });
//		
//		rightConfigComposite.setVisible(false);
//		configCompositeSashForm.setWeights(new int[] {1, 0});
	}

	private void initMainScreen(Composite shell)
	{
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.pack();
		shell.setBounds((Math.abs(area.width - IConstants.MAIN_SCREEN_WIDTH)) / 2,
				Math.abs((area.height - IConstants.MAIN_SCREEN_HEIGHT)) / 2, IConstants.MAIN_SCREEN_WIDTH,
				IConstants.MAIN_SCREEN_HEIGHT);

		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if (IConstants.ACTION_COMPOSITE_CHANGED == type)
		{
			boolean[] value = (boolean[])content;
			
			boolean left = value[0];
			boolean right = value[1];
			
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
