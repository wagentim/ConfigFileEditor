package de.etas.tef.config.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.constant.IImageConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.Problem;
import de.etas.tef.config.listener.FileListMouseListener;
import de.etas.tef.config.listener.FileListTableKeyListener;
import de.etas.tef.config.listener.OpenFileSelectionListener;
import de.etas.tef.config.listener.OpenLocationSelectionListener;
import de.etas.tef.editor.message.MessageManager;

public class ConfigFileListComposite extends AbstractComposite
{

	private Table configFileList;

	public ConfigFileListComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	protected void initComposite()
	{
		super.initComposite();

//		ToolBar tb = new ToolBar(this, SWT.NONE);
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		tb.setLayoutData(gd);
//		ToolItem ti = new ToolItem(tb, SWT.PUSH);
//		ti.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_DELETE));
//		ti.addSelectionListener(new DeleteItemSelectionListener());

		configFileList = new Table(this, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
		GridData gd = new GridData(GridData.FILL_BOTH);
		configFileList.setLayoutData(gd);
		configFileList.setHeaderVisible(false);
		configFileList.setLinesVisible(false);
		new TableColumn(configFileList, SWT.NONE);
		new TableColumn(configFileList, SWT.NONE);
		configFileList.addControlListener(new ControlAdapter()
		{

			@Override
			public void controlResized(ControlEvent event)
			{
				adjustColumnSize();
			}

		});

		configFileList.addKeyListener(new FileListTableKeyListener(configFileList));

		configFileList.addMouseListener(new FileListMouseListener());

		createRightMenu();
	}

	protected Menu createRightMenu()
	{
		Menu rightClickMenu = new Menu(configFileList);
		configFileList.setMenu(rightClickMenu);

		rightClickMenu.addMenuListener(new MenuAdapter()
		{
			@Override
			public void menuShown(MenuEvent e)
			{
				MenuItem[] items = rightClickMenu.getItems();

				for (int i = 0; i < items.length; i++)
				{
					items[i].dispose();
				}

				TableItem[] selections = configFileList.getSelection();

				if (selections == null || selections.length < 1)
				{
					return;
				}

				TableItem item = selections[0];

				if (null == item)
				{
					return;
				}

				MenuItem openFileLocation = new MenuItem(rightClickMenu, SWT.NONE);
				openFileLocation.setText("Open File Location");
				openFileLocation
						.addSelectionListener(new OpenLocationSelectionListener((ConfigFilePath) item.getData()));

				MenuItem openFile = new MenuItem(rightClickMenu, SWT.NONE);
				openFile.setText("Open File");
				openFile.addSelectionListener(new OpenFileSelectionListener((ConfigFilePath) item.getData()));

				MenuItem deleteItem = new MenuItem(rightClickMenu, SWT.NONE);
				deleteItem.setText("Delete Item");
				deleteItem.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_DELETE));
				deleteItem.addSelectionListener(new DeleteItemSelectionListener());
			}
		});

		return rightClickMenu;
	}

	class DeleteItemSelectionListener implements SelectionListener
	{

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0)
		{

		}

		@Override
		public void widgetSelected(SelectionEvent event)
		{
			MessageBox mb = new MessageBox(configFileList.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			mb.setText("Delete Confirmation");
			mb.setMessage("Do you really want to Delete Files?");

			boolean done = mb.open() == SWT.YES;

			if (!done)
			{
				return;
			}

			TableItem[] item = configFileList.getSelection();

			if (null == item || item.length < 1)
			{
				return;
			}
			
			List<ConfigFilePath> deleteItems = new ArrayList<ConfigFilePath>();
			
			for(TableItem ti : item)
			{
				deleteItems.add((ConfigFilePath) ti.getData());
			}
			
			controller.deleteINIFiles(deleteItems);
			updateList(controller.getCurrentFileList());
		}
	}

	private void updateList(List<ConfigFilePath> files)
	{
		configFileList.removeAll();

		if (files == null || files.isEmpty())
		{
			return;
		}

		for (ConfigFilePath p : files)
		{
			TableItem ti = new TableItem(configFileList, SWT.NONE);
			ti.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_FILE));
			ti.setText(0, getDisplayString(p));
			ti.setText(1, IConstants.DATE_TIME_FORMAT.format(new Date(p.getPath().toFile().lastModified())));
			ti.setData(p);

			int bg = configFileList.getItemCount() % 2;
			if (bg == 1)
			{
				ti.setBackground(controller.getColorFactory().getColorLightBlue());
			}
		}

		adjustColumnSize();
		MessageManager.INSTANCE.sendMessage(IConstants.ACTION_UPDATE_FILE_LIST_NUM, files);
	}

	private String getDisplayString(ConfigFilePath p)
	{

		return p.getPath().toString();
	}

	private void adjustColumnSize()
	{
		Rectangle rect = configFileList.getClientArea();
		configFileList.getColumn(0).setWidth((int) (rect.width * 0.7));
		configFileList.getColumn(1).setWidth((int) (rect.width * 0.3));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void receivedAction(int type, Object content)
	{
		if (type == IConstants.ACTION_SELECTED_SEARCH_PATH)
		{

		} else if (type == IConstants.ACTION_UPDATE_FILE_LIST)
		{
			List<ConfigFilePath> list = (List<ConfigFilePath>) content;
			controller.setCurrentFileList(list);
			updateList(list);

		} else if (type == IConstants.ACTION_VALIDATION_FINISHED)
		{
			checkList();
		} else if (type == IConstants.ACTION_REPLACE_TEXT)
		{
		}
	}

	private void checkList()
	{
		TableItem[] items = configFileList.getItems();

		if (items == null || items.length < 1)
		{
			return;
		}

		for (TableItem ti : items)
		{
			ConfigFilePath cfp = (ConfigFilePath) ti.getData();

			Map<Integer, List<Problem>> problems = cfp.getConfigFile().getProblemList();

			if (problems != null && problems.size() > 0)
			{
				ti.setBackground(controller.getColorFactory().getColorRed());
			}
		}
	}
}
