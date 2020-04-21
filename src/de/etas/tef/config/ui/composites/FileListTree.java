package de.etas.tef.config.ui.composites;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.CustomTree;

public class FileListTree extends CustomTree
{

	public FileListTree(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}

	@Override
	protected String getRootNodeName()
	{
		return IConstants.TXT_FILES;
	}

}
