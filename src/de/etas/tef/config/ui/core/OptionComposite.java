package de.etas.tef.config.ui.core;

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
	private Button btnLeft;
	private Button btnRight;

	public OptionComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
		
		this.setLayout(new GridLayout(4, false));
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
				
				if(selected)
				{
					if(btnLeft.getSelection() && btnRight.getSelection())
					{
						getController().setConnected(true);
					}
				}
				else
				{
					btnConnect.setSelection(false);
					getController().setConnected(false);
				}
				
				ActionManager.INSTANCE.sendAction(Constants.ACTION_CONNECT_SELECTED, selected);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		btnLeft = new Button(parent, SWT.CHECK);
		gd = new GridData();
		btnLeft.setLayoutData(gd);
		btnLeft.setText(Constants.TXT_BTN_LEFT);
		btnLeft.setSelection(true);
		btnLeft.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				checkOptionSelection(Constants.ACTION_LEFT_SELECTED);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent event)
			{
				
			}
		});
		
		btnRight = new Button(parent, SWT.CHECK);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		btnRight.setLayoutData(gd);
		btnRight.setText(Constants.TXT_BTN_RIGHT);
		btnRight.setSelection(false);
		btnRight.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				checkOptionSelection(Constants.ACTION_RIGHT_SELECTED);
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
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				
			}
		});
		
		btnAcceptSource.setVisible(false);
	}

	protected void checkOptionSelection(int currentAction)
	{
		boolean isLeftSelected = btnLeft.getSelection();
		boolean isRightSelected = btnRight.getSelection();
		
		if(isLeftSelected && isRightSelected)
		{
			btnConnect.setEnabled(true);
		}
		else 
		{
			btnConnect.setSelection(false);
			getController().setConnected(false);
			btnConnect.setEnabled(false);
			
			if( !isLeftSelected && !isRightSelected)
			{
				if(Constants.ACTION_LEFT_SELECTED == currentAction)
				{
					btnRight.setSelection(true);
				}
				else
				{
					btnLeft.setSelection(true);
				}
			}
		}
		
		ActionManager.INSTANCE.sendAction(Constants.ACTION_COMPOSITE_CHANGED, new boolean[] {btnLeft.getSelection(), btnRight.getSelection()});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}

}
