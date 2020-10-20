package de.etas.tef.config.ui.composites;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;

public class GitFileToolbar extends CustomToolbar
{
	
	private static final String[] addDropdownAdd = {IConstants.TXT_TOOLBAR_ADD_FILE, IConstants.TXT_TOOLBAR_ADD_DIR};
	private static final String[] addDropdonwHistory = {IConstants.TXT_TOOLBAR_FILE_HISTORY, IConstants.TXT_TOOLBAR_COMMIT_HISTORY};

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
		
		ToolItem ti = addDropdownItem("Add", controller.getImageFactory().getImage(IImageConstants.IMAGE_ADD), addDropdownAdd, "Add new File or Directory to Repository");
		
		
		
		addNewSTDItem("Remove", controller.getImageFactory().getImage(IImageConstants.IMAGE_REMOVE), new SelectionAdapter()
		{
		}, "Delete File or Directory from Repository");
		
		addDropdownItem("History", controller.getImageFactory().getImage(IImageConstants.IMAGE_HISTORY), addDropdonwHistory, "Show File History or Commit History");
	}

}
