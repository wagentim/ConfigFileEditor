package de.etas.tef.config.ui;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.FileSearchWalker;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;
import de.etas.tef.editor.message.MessageManager;

public class ConfigFileListComposite extends AbstractComposite
{

	private Table configFileList;

	public ConfigFileListComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
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
		ToolbarComponent tc = new ToolbarComponent(this, SWT.NONE, controller);
		List<Image> images = new ArrayList<Image>();
		images.add(controller.getImageFactory().getImage(IImageConstants.IMAGE_RUN));
		tc.addButton(null, images, new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_GET_SELECTED_PATH, null);
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
		if(type == IConstants.ACTION_SELECTED_SEARCH_PATH)
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
