package de.etas.tef.config.ui.composites;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.IMessage;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.CustomTree;

public class ConfigBlockTree extends CustomTree
{

	public ConfigBlockTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IMessage.MSG_SET_FILE)
		{
			Path p = Paths.get((String)content);
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
