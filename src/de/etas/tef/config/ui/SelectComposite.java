package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.editor.message.MessageManager;

public class SelectComposite extends AbstractComposite
{
	
	private Text txtFileSelect;
	private Button btnFileSelect;
	
	protected SelectComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0; 
		layout.marginHeight = layout.marginWidth = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		this.setBackground(controller.getColorFactory().getColorBackground());
		
		initText(this);
		initButton(this);
	}
	
	protected Text getText()
	{
		return txtFileSelect;
	}
	
	protected Button getButton()
	{
		return btnFileSelect;
	}
	
	protected String getCurrentFilePath()
	{
		return txtFileSelect.getText();
	}
	
	protected void setCurrFilePath(String currFilePath)
	{
		
		if ( null == currFilePath )
		{
			currFilePath = IConstants.EMPTY_STRING;
		}
		
		txtFileSelect.setText(currFilePath);
	}
	
	protected void initText(Composite comp)
	{
		txtFileSelect = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData txtGD = new GridData(GridData.FILL_HORIZONTAL);
		txtFileSelect.setLayoutData(txtGD);
		txtFileSelect.setEditable(false);
		txtFileSelect.setMessage("Select Directory");
		txtFileSelect.setBackground(controller.getColorFactory().getColorBackground());
	}
	
	protected void initButton(Composite comp)
	{
		btnFileSelect = new Button(this, SWT.PUSH);
		btnFileSelect.setText(IConstants.TXT_BTN_DIRECTORY);
		GridData gd = new GridData();
		gd.widthHint = IConstants.BTN_DEFAULT_WIDTH;
		btnFileSelect.setLayoutData(gd);
		btnFileSelect.setBackground(controller.getColorFactory().getColorBackground());
		btnFileSelect.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				setCurrFilePath(fileSelector(getShell()));
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});
	}

	protected String fileSelector(Shell shell)
	{
		DirectoryDialog fd = new DirectoryDialog(shell, SWT.APPLICATION_MODAL | SWT.OPEN);
		return fd.open();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub
		if( type == IConstants.ACTION_DROP_NEW_FILE_SELECTED )
		{
			setCurrFilePath((String)content);
		}
		else if( type == IConstants.ACTION_GET_SELECTED_PATH )
		{
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SELECTED_SEARCH_PATH, txtFileSelect.getText());
		}
	}
}