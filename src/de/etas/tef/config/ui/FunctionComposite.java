package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.etas.tef.config.controller.MainController;

public class FunctionComposite extends AbstractComposite
{

	public FunctionComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	@Override
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 208;
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		
//		final TabFolder tabFolder = new TabFolder(this, SWT.NONE);
//		gd = new GridData(GridData.FILL_BOTH);
//		tabFolder.setLayoutData(gd);
//		
//		TabItem validationItem = new TabItem(tabFolder, SWT.NULL);
//		validationItem.setText("Checker");
//		validationItem.setControl(new ValidationComposite(tabFolder, SWT.BORDER, controller));
//		
//		TabItem loggerItem = new TabItem(tabFolder, SWT.NULL);
//		loggerItem.setText("Logger");
//		loggerItem.setControl(new LoggerComposite(tabFolder, SWT.BORDER, controller));
		
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

}
