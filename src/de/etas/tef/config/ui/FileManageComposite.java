package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class FileManageComposite extends AbstractComposite
{
	
	public FileManageComposite(Composite composite, int style, MainController controller)
	{
		super(composite, style, controller);
	}
	
	@Override
	protected void initComposite()
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_BOTH);
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		new SelectSearchComposite(this, SWT.NONE, controller);
		new ConfigFileListComposite(this, SWT.BORDER, controller);
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		
	}
}
