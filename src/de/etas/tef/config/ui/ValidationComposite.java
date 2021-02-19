package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.constant.IImageConstants;
import de.etas.tef.config.controller.MainController;

public class ValidationComposite extends AbstractComposite
{
	private Button checkDuplicated;
	private Button checkMissing;
	private Button checkUnknown;
	
	private ToolItem run;
	
	public ValidationComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	@Override
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = 2;
		GridData gd = new GridData(GridData.FILL_VERTICAL);
		this.setLayout(layout);
//		gd.widthHint = 200;
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		
		ToolBar tb = new ToolBar(this, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		tb.setLayoutData(gd);
		tb.setBackground(controller.getColorFactory().getColorBackground());
		run = new ToolItem(tb, SWT.PUSH);
		Image image = controller.getImageFactory().getImage(IImageConstants.IMAGE_RUN);
		run.setImage(image);
		
		run.addSelectionListener(new SelectionAdapter() 
		{
			
			@Override
			public void widgetSelected(SelectionEvent event) 
			{
				
				int value = getSelChecker();
				controller.validating(value);
			}
			
		});
		
		checkDuplicated = new Button(this, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		checkDuplicated.setLayoutData(gd);
		checkDuplicated.setText(IConstants.TXT_DUPLICATED_SECTION);
		checkDuplicated.setBackground(controller.getColorFactory().getColorBackground());
//		checkDuplicated.setForeground(controller.getColorFactory().getColorBlue());
		checkDuplicated.setSelection(true);
		
		checkMissing = new Button(this, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		checkMissing.setLayoutData(gd);
		checkMissing.setText(IConstants.TXT_MISSING_ITEM);
		checkMissing.setBackground(controller.getColorFactory().getColorBackground());
//		checkMissing.setForeground(controller.getColorFactory().getColorGreen());
		checkMissing.setSelection(true);
		
		checkUnknown = new Button(this, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		checkUnknown.setLayoutData(gd);
		checkUnknown.setText(IConstants.TXT_UNKNOWN_ITEM);
		checkUnknown.setBackground(controller.getColorFactory().getColorBackground());
//		checkUnknown.setForeground(controller.getColorFactory().getColorRed());
		checkUnknown.setSelection(true);
	}
	
	private int getSelChecker()
	{
		int dup = checkDuplicated.getSelection() == true ? IConstants.SEL_DUPLICATE : 0;
		int miss = checkMissing.getSelection() == true ? IConstants.SEL_MISSING : 0;
		int unk = checkUnknown.getSelection() == true ? IConstants.SEL_UNKNOWN : 0;
		
		return dup | miss | unk;
	}
	
	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub

	}

}
