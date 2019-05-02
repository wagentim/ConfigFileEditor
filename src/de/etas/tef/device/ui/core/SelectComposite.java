package de.etas.tef.device.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;

public class SelectComposite extends AbstractComposite
{
	
	private Text txtFileSelect;
	private Button btnFileSelect;
	private Label labelFileSelect;
	
	private String currFilePath = Constants.EMPTY_STRING;

	protected SelectComposite(Composite parent, int style, IController controller)
	{
		super(parent, style, controller);
		
		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		initLabel(this);
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
	
	protected Label getLabel()
	{
		return labelFileSelect;
	}
	
	protected String getCurrentFilePath()
	{
		return currFilePath;
	}
	
	protected void initLabel(Composite comp)
	{
		labelFileSelect = new Label(comp, SWT.NULL);
		GridData gd = new GridData();
		gd.widthHint = Constants.LABEL_DEFAULT_WIDTH;
		labelFileSelect.setLayoutData(gd);
		labelFileSelect.setText(Constants.TXT_LABEL_FILE);
	}
	
	protected void txtSelectListener(){}
	
	protected void btnSelectListener()
	{
		currFilePath = fileSelector(this.getShell());
		
		if ( null == currFilePath )
		{
			currFilePath = Constants.EMPTY_STRING;
		}
		
		txtFileSelect.setText(currFilePath);
		fileSelected();
	}
	
	protected void fileSelected() {}
	
	protected void initText(Composite comp)
	{
		txtFileSelect = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData txtGD = new GridData(GridData.FILL_HORIZONTAL);
		txtFileSelect.setLayoutData(txtGD);
		txtFileSelect.setEditable(false);
	}
	
	protected void initButton(Composite comp)
	{
		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new GridLayout(1, false));
		
		btnFileSelect = new Button(c, SWT.PUSH);
		btnFileSelect.setText(Constants.TXT_BTN_SELECT);
		GridData gd = new GridData();
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		btnFileSelect.setLayoutData(gd);
		btnFileSelect.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				btnSelectListener();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});
	}

	protected String fileSelector(Shell shell)
	{
		FileDialog fd = new FileDialog(shell, SWT.APPLICATION_MODAL | SWT.OPEN);
		fd.setFilterExtensions(Constants.CONFIG_FILE_EXTENSION);
		fd.setFilterNames(Constants.CONFIG_FILE_NAME);
		return fd.open();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}
}
