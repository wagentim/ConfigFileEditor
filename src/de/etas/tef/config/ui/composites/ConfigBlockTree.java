package de.etas.tef.config.ui.composites;

import org.eclipse.swt.widgets.Composite;

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

}
