package de.etas.tef.config.ui.composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.AbstractComposite;

public class ConfigFileTableSearchComposite extends AbstractComposite
{

	private Table configFileTable;
	private final Color bgTableHeader;

	public ConfigFileTableSearchComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		bgTableHeader = parent.getDisplay().getSystemColor(SWT.COLOR_GREEN);
	}

	protected void initComposite()
	{
		super.initComposite();
		new SearchComposite(this, SWT.NONE, controller);
		configFileTable = new Table(this, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		configFileTable.setLayoutData(gd);
		configFileTable.setLinesVisible(true);
		configFileTable.setHeaderVisible(true);

		TableColumn column = new TableColumn(configFileTable, SWT.NULL);
		column.setText("INI Files" );
		column.setWidth(250);
		gd = new GridData();
		column.setAlignment(SWT.CENTER);

	}

	private int calColumnWidth()
	{
		int columnCount = configFileTable.getColumnCount();

		if (columnCount == 0)
			return 10;

		Rectangle area = configFileTable.getClientArea();
		return area.width;
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}

}
