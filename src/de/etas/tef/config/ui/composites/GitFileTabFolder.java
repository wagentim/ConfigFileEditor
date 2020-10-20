package de.etas.tef.config.ui.composites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.ui.core.CustomTabFolder;

public class GitFileTabFolder extends CustomTabFolder
{
	
	private Table historyTable, commitsTable;
	private List<TabItem> tabItems = Collections.emptyList();

	public GitFileTabFolder(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		tabItems = new ArrayList<TabItem>();
		initComposite();
	}
	
	@Override
	protected void initComposite()
	{
		super.initComposite();
		historyTable = createTable(IConstants.ARRAY_TABLE_HISTORY_HEADER);
		tabItems.add(addTab(IConstants.TXT_FILE_HISTORY, historyTable));
		
		
		commitsTable = createTable(IConstants.ARRAY_TABLE_COMMITS_HEADER);
		tabItems.add(addTab(IConstants.TXT_COMMITS, commitsTable));
	}

	private Table createTable(String[] headers)
	{
		Table table = new Table(tabFolder, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		for (int i = 0; i < headers.length; i++)
		{
			TableColumn column = new TableColumn(table, SWT.NONE);
			switch(i)
			{
			case 0:
				column.setWidth(50);
				break;
			case 1:
				column.setWidth(150);
				break;
			default:
				column.setWidth(70);
				break;
			}
			column.setText(headers[i]);
		}
		
		return table;
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}

}
