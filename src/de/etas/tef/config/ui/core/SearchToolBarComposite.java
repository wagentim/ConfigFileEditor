package de.etas.tef.config.ui.core;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class SearchToolBarComposite extends AbstractComposite
{

	private SearchComposite search;
	
	public SearchToolBarComposite(Composite parent, int style,
			MainController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		initMainComposite(this);
	}

	private void initMainComposite(SearchToolBarComposite searchToolBarComposite)
	{
		// TODO Auto-generated method stub
		
	}

}
