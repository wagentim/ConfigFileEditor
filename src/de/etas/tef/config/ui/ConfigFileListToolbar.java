package de.etas.tef.config.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;
import de.etas.tef.editor.message.MessageManager;

public class ConfigFileListToolbar extends AbstractComposite
{

	public ConfigFileListToolbar(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorBackground());
		
		
		MultiSearchComposite sc = new MultiSearchComposite(this, SWT.NONE, controller);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sc.setLayoutData(gd);
		
		ToolbarComponent tc = new ToolbarComponent(this, SWT.NONE, controller);
		List<Image> images = new ArrayList<Image>();
		images.add(controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH));
		tc.addItem(null, images, new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_START_SEARCH, null);
			}
			
		});
		
		images = new ArrayList<Image>();
		images.add(controller.getImageFactory().getImage(IImageConstants.IMAGE_RUN));
		tc.addItem(null, images, new SelectionAdapter()
		{
			
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_SEARCH_NEXT, null);
			}
			
		});

	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub
		
	}
	
}
