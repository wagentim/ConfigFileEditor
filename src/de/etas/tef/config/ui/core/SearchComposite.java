package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;

public class SearchComposite extends AbstractComposite
{
	
	protected MainController controller;
	private Text searchText;
	private Color WHITE;
	
	public SearchComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		WHITE = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0; 
		layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumHeight = 30;
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(WHITE);
		
		Image image = new Image(this.getDisplay(), SearchComposite.class.getClassLoader().getResourceAsStream("icons/search.png"));
		Label label = new Label(this, SWT.NONE);
		label.setImage(image);
		gd = new GridData();
		label.setLayoutData(gd);
		label.setBackground(WHITE);
		
		searchText = new Text(this, SWT.NONE);
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
	
	@Override
	public void receivedAction(int type, Object content)
	{
		
		if( type == IConstants.ACTION_NEW_FILE_SELECTED || type == IConstants.ACTION_DROP_NEW_FILE_SELECTED)
		{
			searchText.setText(IConstants.EMPTY_STRING);
		}
	}
}
