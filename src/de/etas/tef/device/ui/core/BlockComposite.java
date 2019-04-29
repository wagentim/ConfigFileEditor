package de.etas.tef.device.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;

public class BlockComposite extends AbstractComposite
{
	private Label labelBlock = null;
	private Combo comboBlock = null;
	private Button btnBlock = null;
	
	public BlockComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		initLabel(this);
		initCombo(this);
		initButton(this);
	}
	
	protected Label getLabel()
	{
		return labelBlock;
	}
	
	protected Combo getCombo()
	{
		return comboBlock;
	}
	
	protected Button getButton()
	{
		return btnBlock;
	}

	protected void initLabel(Composite comp)
	{
		labelBlock = new Label(comp, SWT.NULL);
		GridData gd = new GridData();
		gd.widthHint = Constants.LABEL_DEFAULT_WIDTH;
		labelBlock.setLayoutData(gd);
		labelBlock.setText(Constants.TXT_LABEL_BLOCK_LIST);
	}
	
	protected void comboSelectionListener(){}
	
	protected void initCombo(Composite comp)
	{
		comboBlock = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		comboBlock.setLayoutData(gd);
		
		comboBlock.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				comboSelectionListener();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
	}
	
	protected void initButton(Composite comp)
	{
		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new GridLayout(1, false));
		
		btnBlock = new Button(c, SWT.PUSH);
		btnBlock.setText(Constants.TXT_BTN_RUN);
		GridData gd = new GridData();
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		btnBlock.setLayoutData(gd);
		btnBlock.setVisible(false);
		
		btnBlock.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
	}

	protected void setAllComponentsEnable(boolean isEnable)
	{
		comboBlock.setEnabled(isEnable);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}
}
