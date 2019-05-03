package de.etas.tef.device.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.controller.InfoBlockWriter;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;
import de.etas.tef.device.ui.source.SourceConfigComposite;
import de.etas.tef.device.ui.target.TargetConfigComposite;

public class MainScreen implements IActionListener
{
	private TargetConfigComposite targetConfigComponent = null;
	private SourceConfigComposite sourceConfigComponent = null;
	private SashForm sf = null;

	public MainScreen()
	{
		IController controller = new MainController();
		ActionManager.INSTANCE.addActionListener(this);

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText(Constants.TXT_APP_TITLE);

		initMainScreen(shell);
		initMainComponents(shell, controller);
		new OptionComposite(shell, SWT.BORDER, controller);

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

	private void initMainComponents(Composite shell, IController controller)
	{
		SashForm main = new SashForm(shell, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);

		sf = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		main.setLayoutData(gd);

		sourceConfigComponent = new SourceConfigComposite(sf, SWT.BORDER, controller);
		targetConfigComponent = new TargetConfigComposite(sf, SWT.BORDER, controller);
		
		SashForm sfComment = new SashForm(main, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		sfComment.setLayoutData(gd);

		Text txtInfoBlock = new Text(sfComment, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		
		Text commentBlock = new Text(sfComment, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		commentBlock.setLayoutData(gd);
		commentBlock.setEditable(false);
		
		commentBlock.setVisible(false);
		
		sfComment.setWeights(new int[]{1, 0});
		
		new InfoBlockWriter(txtInfoBlock, commentBlock, controller);

		main.setWeights(new int[]
		{ 4, 1 });
		
		targetConfigComponent.setVisible(false);
		sf.setWeights(new int[] {1, 0});
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
	public void receivedAction(int type, Object content)
	{
		if (Constants.ACTION_COMPOSITE_CHANGED == type)
		{
			boolean[] value = (boolean[])content;
			
			boolean left = value[0];
			boolean right = value[1];
			
			sourceConfigComponent.setVisible(left);
			targetConfigComponent.setVisible(right);
			
			if(left && right)
			{
				sf.setWeights(new int[]{ 1, 1 });
			}
			else if(!left && right)
			{
				sf.setWeights(new int[]{ 0, 1 });
			}
			else if(left && !right)
			{
				sf.setWeights(new int[]{ 1, 0 });
			}
		}
	}
}
