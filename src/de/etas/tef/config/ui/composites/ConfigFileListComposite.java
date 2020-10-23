package de.etas.tef.config.ui.composites;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.FileSearchWalker;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;
import de.etas.tef.editor.message.MessageManager;

public class ConfigFileListComposite extends AbstractComposite
{

	private Table configFileList;
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
		
		configFileList = new Table(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		configFileList.setLayoutData(gd);
		configFileList.setHeaderVisible(false);
		configFileList.setLinesVisible(false);
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
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_GET_SELECTED_PATH, null);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void updateList(java.util.List<Path> files)
	{
		configFileList.removeAll();
		
		if(files == null || files.isEmpty())
		{
			return;
		}
		
		configFileList.setData(files);
		
		for(Path p : files)
		{
			String parent = p.getParent().getFileName().toString();
			TableItem ti = new TableItem(configFileList, SWT.NONE);
			
			
			ti.setText(parent + File.separator + p.getFileName().toString());
			ti.setData(p);
		}
		
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SELECTED_PATH)
		{
			String[] pattern = {"ini"};
			
			Thread search = new Thread(new FileSearchWalker((String)content, pattern, this.getDisplay()));
			search.start();
			
		}
		else if(type == IConstants.ACTION_FILE_SEARCH_FINISHED)
		{
			@SuppressWarnings("unchecked")
			java.util.List<Path> files = (java.util.List<Path>)content;
			
			updateList(files);
		}
	}

}
