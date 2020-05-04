package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.AbstractComposite;

public class GitFileMainComposite extends AbstractComposite
{

	public GitFileMainComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComposite();
	}
	
	@Override
	protected void initComposite()
	{
		super.initComposite();
		new GitFileToolbar(this, SWT.NONE, controller);
		SashForm sf = new SashForm(this, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		sf.setLayoutData(gd);
		new GitFileTree(sf, SWT.NONE, controller);
		new GitFileTabFolder(sf, SWT.NONE, controller);
		
		sf.setWeights(new int[] {2, 1});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

}
