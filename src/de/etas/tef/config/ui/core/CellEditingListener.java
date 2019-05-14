package de.etas.tef.config.ui.core;

import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.listener.IActionListener;

public abstract class CellEditingListener implements MouseListener, IActionListener, KeyListener
{
	
	private final Composite composite;
	private final IController controller;
	private final int compositeID;

	private ControlEditor editor = null;
	private String newValue = Constants.EMPTY_STRING;
	private String oldValue = Constants.EMPTY_STRING;
	protected boolean isLocked = true;
	
	public CellEditingListener(Composite composite, IController controller, int compositeID)
	{
		this.composite = composite;
		this.controller = controller;
		this.compositeID = compositeID;
		ActionManager.INSTANCE.addActionListener(this);
	}

	protected Composite getComposite()
	{
		return composite;
	}
	
	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDown(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseUp(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	protected int getCompositeID()
	{
		return this.compositeID;
	}
	
	protected IController getController()
	{
		return controller;
	}
}
