package de.etas.tef.device.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;

public class OptionComposite extends AbstractComposite
{
	
	private Button btnConnect;
	private Button btnAcceptSource;
	private Button btnGPIB;

	public OptionComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		initComponents(this);
	}

	private void initComponents(Composite parent)
	{
		btnConnect = new Button(parent, SWT.CHECK);
		GridData gd = new GridData();
		btnConnect.setLayoutData(gd);
		btnConnect.setText(Constants.TXT_BTN_CONNECT);
		btnConnect.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				boolean selected = btnConnect.getSelection();
				getController().setConnected(selected);
				ActionManager.INSTANCE.sendAction(Constants.ACTION_CONNECT_SELECTED, selected);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		btnGPIB = new Button(parent, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		btnGPIB.setLayoutData(gd);
		btnGPIB.setText(Constants.TXT_BTN_GPIB);
		btnGPIB.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_GPIB_SELECTED, btnGPIB.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
		
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(new GridLayout(1, false));
		btnAcceptSource = new Button(c, SWT.PUSH);
		GridData bgd = new GridData();
		bgd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		btnAcceptSource.setLayoutData(bgd);
		btnAcceptSource.setText(Constants.TXT_BTN_ACCEPT_SOURCE);
		btnAcceptSource.setEnabled(false);
		
		btnAcceptSource.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_TAKE_SOURCE_PARAMETERS_START, null);
				getController().startAcceptSourceParameter();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if( (type == Constants.ACTION_CONNECT_SELECTED) )
		{
//			boolean value = (boolean)content;
//			btnAcceptSource.setEnabled(value);
		}
	}

}
