package de.etas.tef.config.ui;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.FileSearchWalker;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;
import de.etas.tef.config.listener.TableKeyListener;

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
		
		new ConfigFileListToolbar(this, SWT.NONE, controller);
		
		configFileList = new Table(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		GridData gd = new GridData(GridData.FILL_BOTH);
		configFileList.setLayoutData(gd);
		configFileList.setHeaderVisible(false);
		configFileList.setLinesVisible(false);
		new TableColumn(configFileList, SWT.NONE);
		configFileList.addControlListener(new ControlAdapter()
		{
			
			@Override
			public void controlResized(ControlEvent event)
			{
				adjustColumnSize();
			}
			
		});
		
		configFileList.addKeyListener(new TableKeyListener(configFileList));
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
			TableItem ti = new TableItem(configFileList, SWT.NONE);
			ti.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_FILE));
			ti.setText(0, getDisplayString(p));
			ti.setData(p);
			
//			int bg = configFileList.getItemCount() % 2;
//			if(bg == 0)
//			{
//				ti.setBackground(controller.getColorFactory().getColorLightBlue());
//			}
		}
		
		adjustColumnSize();
	}
	
	private String getDisplayString(Path p)
	{
		
//		StringBuilder sb = new StringBuilder();
//		Path parent = p.getParent();
//		
//		if(parent != null)
//		{
//			sb.append(p.getParent().getFileName().toString());
//		}
//		
//		
//		sb.append(File.separator);
//		sb.append(p.getFileName().toString());
//		
//		return sb.toString();
		
		return p.toString();
	}
	
	private void adjustColumnSize()
	{
		Rectangle rect = configFileList.getClientArea();
		configFileList.getColumn(0).setWidth(rect.width);
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
			List<Path> files = (List<Path>)content;
			updateList(files);
		}
	}

}
