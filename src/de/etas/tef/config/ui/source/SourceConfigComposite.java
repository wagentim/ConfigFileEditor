package de.etas.tef.config.ui.source;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.IController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;
import de.etas.tef.config.ui.core.ConfigComposite;

public class SourceConfigComposite extends ConfigComposite
{
	
	public SourceConfigComposite(Composite composite, int style, IController controller)
	{
		super(composite, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

	@Override
	protected void initChildren()
	{
		new SourceFileSelectComposite(this, SWT.NONE, getController());
		new SourceTableComposite(this, SWT.NONE, getController());
	}

	@Override
	protected void setFilePath(String file)
	{
		getController().setInputConfigFile(file, getCompositeID());
		ActionManager.INSTANCE.sendAction(Constants.ACTION_DROP_SOURCE_NEW_FILE_SELECTED, file);
	}

	@Override
	protected int getCompositeID()
	{
		return CompositeID.COMPOSITE_LEFT;
	}
}
