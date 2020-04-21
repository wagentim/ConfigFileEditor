package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.controller.IConstants;

public abstract class CustomDialog extends Dialog
{
	protected final int width = 445;
	protected final int height = 100;
	protected final Shell parent;
	protected final String title;
	protected final int buttonWidth = 70;
	
	protected String input = IConstants.EMPTY_STRING;
	protected String message = IConstants.EMPTY_STRING;

	public CustomDialog(final Shell parent, final String title)
	{
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, title);
	}
	
	public CustomDialog(final Shell parent, int style, final String title)
	{
		super(parent, style);
		this.parent = parent;
		this.title = title;
	}

	public String getInput()
	{
		return input;
	}

	public void setInput(String title)
	{
		this.input = title;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String open()
	{
		// Create the dialog window
//		Shell shell = new Shell(getParent(), getStyle());
		parent.setText(title);
		createContents(parent);
		Monitor primary = parent.getDisplay().getPrimaryMonitor();
		Rectangle area = primary.getClientArea();
		parent.pack();
		parent.setBounds((Math.abs(area.width - width)) / 2,
				Math.abs((area.height - height)) / 2, width,
				height);
		parent.open();
		Display display = getParent().getDisplay();
		while (!parent.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		// Return the entered value, or null
		
		return input;
	}
	
	protected abstract void createContents(final Shell shell);
}
