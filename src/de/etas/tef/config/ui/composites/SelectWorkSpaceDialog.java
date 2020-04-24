package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.CustomDialog;

public class SelectWorkSpaceDialog extends CustomDialog
{
	protected final MainController controller;
	
	protected String repositoryRemote = IConstants.EMPTY_STRING;
	protected String repositoryLocal = IConstants.EMPTY_STRING;
	
	private Text text;
	private Text text1;

	public SelectWorkSpaceDialog(Shell shell, MainController controller)
	{
		super(shell, "Select Working Space");
		this.controller = controller;
	}
	
	public String getRepositoryRemote()
	{
		return repositoryRemote;
	}



	public void setRepositoryRemote(String repositoryRemote)
	{
		this.repositoryRemote = repositoryRemote;
	}



	public String getRepositoryLocal()
	{
		return repositoryLocal;
	}



	public void setRepositoryLocal(String repositoryLocal)
	{
		this.repositoryLocal = repositoryLocal;
	}


	@Override
	protected void createContents(Shell shell)
	{
		shell.setLayout(new GridLayout(5, true));
		new Label(shell, SWT.NONE).setText(IConstants.TXT_REPOSITORY_REMOTE);

		// Create the text box extra wide to show long paths
		text = new Text(shell, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		text.setLayoutData(data);
		text.setEditable(false);
		text.setText(getRepositoryRemote());

		// Clicking the button will allow the user
		// to select a directory
		Button button = new Button(shell, SWT.PUSH);
		button.setText(IConstants.TXT_BROWSE);
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				DirectoryDialog dlg = new DirectoryDialog(shell);

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(text.getText());

				// Change the title bar text
				dlg.setText("Repository Directory");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a Repository");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null)
				{
					// Set the text box to the new selection
					text.setText(dir);
					setRepositoryRemote(dir);
				}
			}
		});
		button.setEnabled(false);
		
		new Label(shell, SWT.NONE).setText(IConstants.TXT_REPOSITORY_LOCAL);

		// Create the text box extra wide to show long paths
		text1 = new Text(shell, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		text1.setLayoutData(data);
		text1.setEditable(false);
		text1.setText(getRepositoryLocal());

		// Clicking the button will allow the user
		// to select a directory
		button = new Button(shell, SWT.PUSH);
		button.setText("Browse...");
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				DirectoryDialog dlg = new DirectoryDialog(shell);

				// Set the initial filter path according
				// to anything they've selected or typed in
				dlg.setFilterPath(text.getText());

				// Change the title bar text
				dlg.setText("Working Space Directory");

				// Customizable message displayed in the dialog
				dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dlg.open();
				if (dir != null)
				{
					// Set the text box to the new selection
					text1.setText(dir);
					setRepositoryLocal(dir);
				}
			}
		});

		final Label label = new Label(shell, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		label.setLayoutData(data);

		button = new Button(shell, SWT.PUSH);
		button.setText(IConstants.TXT_OK);
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				shell.dispose();
				controller.setQuiteProgram(false);
			}
		});

		button = new Button(shell, SWT.PUSH);
		button.setText(IConstants.TXT_CANCEL);
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				shell.dispose();
				controller.setQuiteProgram(true);
			}
		});

	}
	
	@Override
	protected String[] getResult()
	{
		return new String[]{getRepositoryRemote(), getRepositoryLocal()};
	}

	@Override
	protected int getHeight()
	{
		return 125;
	}

	@Override
	protected int getWidth()
	{
		return 530;
	}
	
	public void setValues(String repositoryRemote, String repositoryLocal)
	{
		setRepositoryRemote(repositoryRemote == null? "IGNOR NOW" : "IGNOR NOW");
		setRepositoryLocal(repositoryLocal == null? IConstants.EMPTY_STRING : repositoryLocal);
	}
}
