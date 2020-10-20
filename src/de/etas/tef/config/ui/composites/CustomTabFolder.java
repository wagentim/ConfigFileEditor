package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.etas.tef.config.controller.MainController;

public abstract class CustomTabFolder extends AbstractComposite
{
	
	protected TabFolder tabFolder;

	public CustomTabFolder(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	protected void initComposite()
	{
		super.initComposite();
		tabFolder = new TabFolder(this, SWT.HORIZONTAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gd);
	}
	
	protected TabItem addTab(String name, Control parent)
	{
		TabItem ti = new TabItem(tabFolder, SWT.NONE);
		ti.setText(name);
		ti.setControl(parent);
		
		return ti;
	}
}
