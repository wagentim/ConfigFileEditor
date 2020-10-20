package de.etas.tef.config.ui.composites;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.listener.IMessageListener;
import de.etas.tef.editor.message.MessageManager;

public abstract class AbstractComposite extends Composite implements IMessageListener
{
	
	protected final MainController controller;
	protected final Color defaultBackgroundColor;
	protected final Composite parent;

	public AbstractComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style);
		this.controller = controller;
		this.parent = parent;
		MessageManager.INSTANCE.addMessageListener(this);
		defaultBackgroundColor = controller.getColorFactory().getColorWhite();
		initComposite();
	}
	
	protected void initComposite() 
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		GridData gd = new GridData(GridData.FILL_BOTH);
		this.setLayout(layout);
		this.setLayoutData(gd);
	}

}
