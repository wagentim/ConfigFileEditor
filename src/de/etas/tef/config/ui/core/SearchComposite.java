package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.IController;

public class SearchComposite extends AbstractComposite
{
	
	protected IController controller;
	
	public SearchComposite(Composite parent, int style, IController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0; 
		layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumHeight = 30;
		this.setLayout(layout);
		this.setLayoutData(gd);
		
		Image image = new Image(this.getDisplay(), "icons/search_24.png");
		Label label = new Label(this, SWT.NONE);
		label.setImage(image);
		gd = new GridData();
		label.setLayoutData(gd);
		
		Text searchText = new Text(this, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalSpan = gd.horizontalSpan = 0;
		searchText.setLayoutData(gd);
		searchText.setMessage("Search");
		
		searchText.addModifyListener(new ModifyListener()
		{
			
			@Override
			public void modifyText(ModifyEvent event)
			{
				
			}
		});
		
		this.controller = controller;
	}
}
