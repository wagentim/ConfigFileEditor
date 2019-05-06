package de.etas.tef.config.ui.core;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.IController;

public abstract class ConfigComposite extends AbstractComposite
{

	private GridData cgd = new GridData(GridData.FILL_HORIZONTAL);

	public ConfigComposite(Composite composite, int style, IController controller)
	{
		super(composite, style, controller);

		GridLayout layout = new GridLayout(1, false);
		this.setLayout(layout);
		this.setLayoutData(cgd);
		
		initComponent();
		initDropFunction(this);
	}
	
	protected void initDropFunction(Composite composite)
	{
		DropTarget dt = new DropTarget(composite, DND.DROP_DEFAULT | DND.DROP_MOVE);
		dt.setTransfer(new Transfer[]{ FileTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter()
		{
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
				
				String file = fileList[0];
				
				setFilePath(file);
			}
		});
	}

	protected abstract void setFilePath(String file);

	protected abstract void initComponent();
}
