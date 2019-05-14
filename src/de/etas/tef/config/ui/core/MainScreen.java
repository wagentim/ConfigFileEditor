package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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

	public MainScreen()
	{
		controller = new MainController();
		ActionManager.INSTANCE.addActionListener(this);

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(Constants.TXT_APP_TITLE);
		Image image = new Image(display, "icons/file_24.png");
		shell.setImage(image);

		initMainScreen(shell);
		initMenu(shell);
		initMainComponents(shell);
		new OptionComposite(shell, SWT.BORDER, controller, CompositeID.COMPOSITE_ALONE);

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
		
	}

	private void initMainComponents(Composite shell)
	{
		SashForm main = new SashForm(shell, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);

		configCompositeSashForm = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		configCompositeSashForm.setLayoutData(gd);
		
		leftConfigComposite = new ConfigComposite(configCompositeSashForm, SWT.BORDER, controller, CompositeID.COMPOSITE_LEFT);
		rightConfigComposite = new ConfigComposite(configCompositeSashForm, SWT.BORDER, controller, CompositeID.COMPOSITE_RIGHT);
		
		SashForm sfComment = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		sfComment.setLayoutData(gd);

		StyledText txtInfoBlock = new StyledText(sfComment, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		
		Text commentBlock = new Text(sfComment, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		commentBlock.setLayoutData(gd);
		commentBlock.setEditable(true);
		
		new InfoBlockWriter(txtInfoBlock, commentBlock, controller);

		main.setWeights(new int[]
		{ 4, 1 });
		
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
