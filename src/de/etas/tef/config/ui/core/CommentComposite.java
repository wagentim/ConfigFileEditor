package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class CommentComposite extends AbstractComposite
{
	private Text commentBlock;
	
	public CommentComposite(Composite parent, int style, MainController controller, int compositeID)
	{
		super(parent, style, controller, compositeID);
		
		this.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.minimumHeight = 50;
		this.setLayoutData(gd);
		
		commentBlock = new Text(this, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		commentBlock.setLayoutData(new GridData(GridData.FILL_BOTH));
		commentBlock.setEditable(false);
	}
	
	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		if( compositeID != getCompositeID() && compositeID != CompositeID.COMPOSITE_ALONE )
		{
			return;
		}
		
		if( Constants.ACTION_LOCK_SELECTION_CHANGED == type)
		{
			boolean locked = (boolean)content;
			
			commentBlock.setEditable(!locked);
		}
	}
}
