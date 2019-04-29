package de.etas.tef.device.ui.core;


import org.eclipse.swt.SWT;
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
		new SourceConfigComposite(shell, SWT.BORDER, controller);
        new TargetConfigComposite(shell, SWT.BORDER, controller);
        initInfoBlock(shell, controller);
        new OptionComposite(shell, SWT.BORDER, controller);
        
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
	
	private void initInfoBlock(Shell shell, IController controller)
	{
		Text txtInfoBlock = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txtInfoBlock.setLayoutData(gd);
		txtInfoBlock.setEditable(false);
		new InfoBlockWriter(txtInfoBlock, controller);
		
	}

//	private void initFunctionSelection(Shell shell)
//	{
//		btnGpibAddr = new Button(shell, SWT.CHECK);
//		btnGpibAddr.setText(Constants.TXT_GPIB_ADDR);
//		
//		btnGpibAddr.addSelectionListener(new SelectionListener()
//		{
//			
//			@Override
//			public void widgetSelected(SelectionEvent e)
//			{
//				
//				boolean status = btnGpibAddr.getSelection();
//				infoWriter.logInfo("GPIB Adress Template: " + (status ? "True" : "False"));
//				gpibAddrSelected(status);
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0)
//			{
//				
//			}
//		});
//	}

//	protected void gpibAddrSelected(boolean selected)
//	{
//		if( selected )
//		{
//			comboxConfigBlocks.setEnabled(false);
//		}
//		else
//		{
//			comboxConfigBlocks.setEnabled(true);
//			
//			if( comboxSelectedIndex < 0 )
//			{
//				comboxSelectedIndex = 0;
//			}
//			
//			comboxConfigBlocks.setSelection(new Point(comboxSelectedIndex, comboxSelectedIndex));
//		}
//		
//		controller.GPIBAdreeSelected(selected, comboxSelectedIndex);
//	}

	

//	private void initBtnSave(Shell shell)
//	{
//		btnSave = new Button(shell, SWT.PUSH);
//		btnSave.setText(Constants.TXT_BTN_SAVE);
//		GridData gd = new GridData(GridData.FILL);
//		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
//		gd.verticalIndent = 10;
//		btnSave.setLayoutData(gd);
//		btnSave.setVisible(false);
//	}
	

	
}
