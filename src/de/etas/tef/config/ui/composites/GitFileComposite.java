package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.AbstractComposite;

public class GitFileComposite extends AbstractComposite
{

	public GitFileComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	@Override
	protected void initComposite()
	{
		super.initComposite();
		new GitFileToolbar(this, SWT.NONE, controller);
		new FileListTree(this, SWT.NONE, controller);
		
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub

	}

}
