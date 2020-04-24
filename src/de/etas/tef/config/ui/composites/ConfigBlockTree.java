package de.etas.tef.config.ui.composites;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import de.etas.tef.config.controller.IConstants;
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
		// TODO Auto-generated method stub

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

}
