package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class ConfigMainComposite extends AbstractComposite
{
	private SashForm sfHorizontal;
	private SashForm svVertical;
	
	public ConfigMainComposite(Composite composite, int style, MainController controller)
	{
		super(composite, style, controller);
	}
	
	@Override
	protected void initComposite()
	{
		// setting for the composite
		super.initComposite();
		
//		new FunctionComposite(this, SWT.NONE, controller);
		svVertical = new SashForm(this, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		svVertical.setLayoutData(gd);
		
		sfHorizontal = new SashForm(svVertical, SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_BOTH);
		sfHorizontal.setLayoutData(gd);
		
		new ConfigBlockTree(sfHorizontal, SWT.NONE, controller);
		new ConfigTextEditor(sfHorizontal, SWT.NONE, controller);
		
		sfHorizontal.setWeights(new int[]{1,2});
		
		new LoggerComposite(svVertical, SWT.NONE, controller);
		svVertical.setWeights(new int[]{3,1});		
		
		initDropFunction(this);
	}
	
	protected void initDropFunction(Composite composite)
	{
		DropTarget dt = new DropTarget(composite, DND.DROP_DEFAULT | DND.DROP_MOVE);
		dt.setTransfer(new Transfer[]{ FileTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter()
		{
			@Override
			public void drop(DropTargetEvent event)
			{
				String fileList[] = null;
				
				FileTransfer ft = FileTransfer.getInstance();
				
				if (ft.isSupportedType(event.currentDataType))
				{
					fileList = (String[]) event.data;
				}
				else
				{
					return;
				}
				
				String filePath = fileList[0];
				
				if(!filePath.toLowerCase().trim().endsWith(".ini"))
				{
					return;
				}
				
			}
		});
	}

	@Override
	public void receivedAction(int type, Object content)
	{
	}
}
