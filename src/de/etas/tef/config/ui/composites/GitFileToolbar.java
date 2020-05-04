package de.etas.tef.config.ui.composites;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.core.IImageConstants;
import de.etas.tef.config.ui.core.CustomToolbar;

public class GitFileToolbar extends CustomToolbar
{
	
	private static final String[] addDropdownItems = {"Add File", "Add Directory"};

	public GitFileToolbar(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComponent();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		// TODO Auto-generated method stub

	}
	
	protected void initComponent()
	{
		super.initComponent();
		setShowText(false);
		ToolItem ti = addDropdownItem("Add", controller.getImageFactory().getImage(IImageConstants.IMAGE_ADD), addDropdownItems, "Add new File or Directory to Repository");
		addNewSTDItem("Remove", controller.getImageFactory().getImage(IImageConstants.IMAGE_REMOVE), new SelectionAdapter()
		{
		}, "Delete File or Directory from Repository");
		addNewSTDItem("History", controller.getImageFactory().getImage(IImageConstants.IMAGE_HISTORY), new SelectionAdapter()
		{
		}, "Show Commit History of Tracted File");
	}

}
