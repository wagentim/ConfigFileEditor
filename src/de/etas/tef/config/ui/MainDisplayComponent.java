package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.listener.IMessageListener;
import de.etas.tef.editor.message.MessageManager;

public class MainDisplayComponent implements IMessageListener
{
	private final Shell shell;
	private final MainController controller;
	private SashForm mainArea;
	
	public MainDisplayComponent(final Shell shell, final MainController controller)
	{
		this.shell = shell;
		this.controller = controller;
		
		createDisplayComponent();
	}
	
	private void createDisplayComponent()
	{
		MessageManager.INSTANCE.addMessageListener(this);
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginBottom = 10;
		shell.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		shell.setLayoutData(gd);
		
		mainArea = new SashForm(shell, SWT.HORIZONTAL);
		mainArea.setBackground(controller.getColorFactory().getColorBackground());
		gd = new GridData(GridData.FILL_BOTH);
		mainArea.setLayoutData(gd);
		new FileManageComposite(mainArea, SWT.NONE, controller);
		new ConfigMainComposite(mainArea, SWT.BORDER, controller);
		
		mainArea.setWeights(new int[] {1, 0});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_OPEN_INI_FILE)
		{
			int[] weights = mainArea.getWeights();
			
			if(weights[weights.length - 1] <= 0)
			{
				mainArea.setWeights(new int[] {1, 3});
			}
		}
	}
}
