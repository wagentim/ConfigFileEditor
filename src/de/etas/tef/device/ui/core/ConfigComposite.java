package de.etas.tef.device.ui.core;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.IController;

public abstract class ConfigComposite extends AbstractComposite
{

	private GridData cgd = new GridData(GridData.FILL_HORIZONTAL);

	public ConfigComposite(Composite composite, int style, IController controller)
	{
		super(composite, style, controller);

		GridLayout layout = new GridLayout(1, false);
		this.setLayout(layout);
		this.setLayoutData(cgd);
		
		initComponent();
	}
	
	protected abstract void initComponent();
}
