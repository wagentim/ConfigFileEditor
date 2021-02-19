package de.etas.tef.config.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Table;

public class FileListTableKeyListener implements KeyListener
{
	
	private final Table table;
	
	public FileListTableKeyListener(final Table table)
	{
		this.table = table;
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		if(isCtrlPressed(event) && isCharacterPressed('a', event))
		{
			table.setSelection(table.getItems());
		}
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		// TODO Auto-generated method stub

	}
	
	protected boolean isCtrlPressed(KeyEvent event)
	{
		return (event.stateMask & SWT.CTRL) != 0;
	}
	
	protected boolean isBackspacePressed(KeyEvent event)
	{
		return event.keyCode == SWT.BS;
	}
	
	protected boolean isShiftPressed(KeyEvent event)
	{
		return event.keyCode == SWT.SHIFT;
	}
	
	protected boolean isAltPressed(KeyEvent event)
	{
		return event.keyCode == SWT.ALT;
	}
	
	protected boolean isCharacterPressed(char c, KeyEvent event)
	{
		return event.keyCode == c;
	}
}
