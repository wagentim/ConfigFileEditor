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

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.CustomDialog;

public class SelectWorkSpaceDialog extends CustomDialog
{
	protected final MainController controller;

	public SelectWorkSpaceDialog(Shell parent, MainController controller)
	{
		super(parent, "Select Working Space");
		this.controller = controller;
	}

	@Override
	protected void createContents(Shell shell)
	{
		shell.setLayout(new GridLayout(5, true));
		new Label(shell, SWT.NONE).setText("Working Space:");

		// Create the text box extra wide to show long paths
		final Text text = new Text(shell, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		text.setLayoutData(data);

		// Clicking the button will allow the user
		// to select a directory
		Button button = new Button(shell, SWT.PUSH);
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
					text.setText(dir);
					setInput(dir);
				}
			}
		});

		final Label label = new Label(shell, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		label.setLayoutData(data);

		button = new Button(shell, SWT.PUSH);
		button.setText("OK");
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				parent.close();
				controller.setQuiteProgram(false);
			}
		});

		button = new Button(shell, SWT.PUSH);
		button.setText("Cancel");
		data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		data.widthHint = buttonWidth;
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				controller.setQuiteProgram(true);
				parent.close();
			}
		});

	}
}
