package de.etas.tef.config.ui;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.helper.IConstants;

public class ConfigBlockTree extends CustomTree
{

	public ConfigBlockTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComponent();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if (type == IConstants.ACTION_PARSER_INI_FINISH)
		{
			ConfigFile cf = (ConfigFile)content;
			updateRootNode(cf.getFilePath().getFileName().toString(), IConstants.DATA_PATH, cf.getFilePath());
			createTree(cf);
			root.setExpanded(true);
		}
	}

	public void createTree(ConfigFile configFile)
	{
		root.removeAll();
		List<ConfigBlock> blocks = configFile.getConfigBlocks();

		Iterator<ConfigBlock> it = blocks.iterator();

		while (it.hasNext())
		{
			ConfigBlock cb = it.next();
			TreeItem ti = new TreeItem(root, SWT.NONE);
			ti.setText(cb.getBlockName());
			ti.setData(IConstants.DATA_VALUE);
		}
	}

	@Override
	protected String getRootNodeName()
	{
		return IConstants.TXT_INI_FILE_DEFAULT;
	}

	@Override
	protected SelectionListener getTreeRightMenuSelectionListener()
	{
		// TODO Auto-generated method stub
		return new SelectionAdapter()
		{
		};
	}

	@Override
	protected void createCustomRightMenu(Menu rightClickMenu)
	{

	}

	@Override
	protected void loadData()
	{
	}

	@Override
	protected MouseListener getMouseListener()
	{
		return new MouseAdapter()
		{
		};
	}

}
