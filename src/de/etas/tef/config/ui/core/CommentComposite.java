package de.etas.tef.config.ui.core;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.CompositeID;
import de.etas.tef.config.helper.Constants;

public class CommentComposite extends AbstractComposite
{
	private Text commentBlock;
	private Button btnCommentSave;
	
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
		
		btnCommentSave = new Button(this, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.TOP, true, false, 1, 1);
		gd.widthHint = Constants.BTN_DEFAULT_WIDTH;
		btnCommentSave.setLayoutData(gd);
		btnCommentSave.setText("Save");
		btnCommentSave.setEnabled(false);
		
		btnCommentSave.addSelectionListener(new SelectionListener()
		{
			
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				ActionManager.INSTANCE.sendAction(Constants.ACTION_COMMENT_SAVED, getCompositeID(), commentBlock.getText());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
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
			btnCommentSave.setEnabled(!locked);
		}
	}
}
