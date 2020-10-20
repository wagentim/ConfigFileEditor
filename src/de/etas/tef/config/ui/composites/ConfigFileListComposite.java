package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.app.ActionManager;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;

public class ConfigFileListComposite extends AbstractComposite
{

	private List configFileList;
	private final Color bgTableHeader;

	public ConfigFileListComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		bgTableHeader = parent.getDisplay().getSystemColor(SWT.COLOR_GREEN);
	}

	protected void initComposite()
	{
		super.initComposite();
		initToolbar();		
		
		configFileList = new List(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		configFileList.setLayoutData(gd);
	}
	
	private void initToolbar()
	{
		ToolBar tb = new ToolBar(this, SWT.FLAT);
		
		ToolItem searchFiles = new ToolItem(tb, SWT.PUSH);
		searchFiles.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_PLAY));
		searchFiles.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				ActionManager.INSTANCE.sendAction(IConstants.ACTION_GET_SELECTED_PATH, null);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SELECTED_PATH)
		{
			
		}
	}

}
