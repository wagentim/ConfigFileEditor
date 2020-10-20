package de.etas.tef.config.ui.composites;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import de.etas.tef.config.controller.IMessage;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.ui.core.CustomTree;

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
		if(type == IMessage.MSG_SET_FILE)
		{
			Path p = Paths.get((String)content);
			
			if(Files.exists(p))
			{
				String fileName = p.getFileName().toString();
				updateRootNode(fileName, IConstants.DATA_PATH, p.toString());
				ConfigFile cf = controller.parserINIFile(p);
				createTree(cf);
				root.setExpanded(true);
			}
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
