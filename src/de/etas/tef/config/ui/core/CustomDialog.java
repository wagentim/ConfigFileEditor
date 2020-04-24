package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public abstract class CustomDialog extends Dialog
{
	protected Shell shell;
	protected final String title;
	protected final int buttonWidth = 70;
	
	public CustomDialog(final Shell shell, final String title)
	{
		this(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, title);
		this.shell = shell;
	}
	
	public CustomDialog(final Shell parent, int style, final String title)
	{
		super(parent, style);
		this.title = title;
	}

	public String[] open()
	{
		// Create the dialog window
		shell.setText(title);
		createContents(shell);
		Monitor primary = shell.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		shell.setBounds((Math.abs(area.width - getWidth())) / 2,
				Math.abs((area.height - getHeight())) / 2, getWidth(),
				getHeight());
		shell.open();
		
//		shell.pack();
		Display display = shell.getDisplay();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		// Return the entered value, or null
		
		return getResult();
	}
	
	protected abstract int getHeight();

	protected abstract int getWidth();

	protected abstract String[] getResult();

	protected abstract void createContents(final Shell shell);
}
