package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;

public class SelectComposite extends AbstractComposite
{
	
	private Text txtFileSelect;
	private Button btnFileSelect;
	private Label labelFileSelect;
	
	protected SelectComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);

		GridLayout layout = new GridLayout(3, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0; 
		layout.marginHeight = layout.marginWidth = 0;
		
		this.setLayout(layout);
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
		return txtFileSelect.getText();
	}
	
	protected void initLabel(Composite comp)
	{
		labelFileSelect = new Label(comp, SWT.NULL);
		GridData gd = new GridData();
		labelFileSelect.setLayoutData(gd);
		Image image = new Image(this.getDisplay(), SelectComposite.class.getClassLoader().getResourceAsStream("icons/file_36.png"));
		labelFileSelect.setImage(image);
	}
	
	protected void setCurrFilePath(String currFilePath)
	{
		
		if ( null == currFilePath || currFilePath.isEmpty())
		{
			return;
		}
		
		txtFileSelect.setText(currFilePath);
		
	}
	
	protected void initText(Composite comp)
	{
		txtFileSelect = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData txtGD = new GridData(GridData.FILL_HORIZONTAL);
		txtFileSelect.setLayoutData(txtGD);
		txtFileSelect.setEditable(false);
		txtFileSelect.setMessage("Select File Here");
	}
	
	protected void initButton(Composite comp)
	{
		Composite c = new Composite(comp, SWT.NONE);
		c.setLayout(new GridLayout(1, false));
		
		btnFileSelect = new Button(c, SWT.PUSH);
		btnFileSelect.setText(IConstants.TXT_BTN_SELECT);
		GridData gd = new GridData();
		gd.widthHint = IConstants.BTN_DEFAULT_WIDTH;
		btnFileSelect.setLayoutData(gd);
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
		FileDialog fd = new FileDialog(shell, SWT.APPLICATION_MODAL | SWT.OPEN);
		fd.setFilterExtensions(IConstants.CONFIG_FILE_EXTENSION);
		fd.setFilterNames(IConstants.CONFIG_FILE_NAME);
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
		
	}

}
