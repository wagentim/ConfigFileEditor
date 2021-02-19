package de.etas.tef.config.helper;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.editor.message.MessageManager;

public class ProgressBarHelper
{
	protected final Display display;
	
	public ProgressBarHelper(final Display display)
	{
		this.display = display;
	}
	
	protected void showProgressbar()
	{
		display.asyncExec(new Runnable()
		{
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_DISPLAY, true);
			}
		});
	}
	
	protected void hideProgressbar()
	{
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_DISPLAY, false);
			}
		});
	}
	
	protected void setProgressbar(double value)
	{
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_UPDATE, value);
			}
		});
	}
}
