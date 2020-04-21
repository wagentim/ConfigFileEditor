package de.etas.tef.config.ui.core;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.listener.IMessageListener;
import de.etas.tef.editor.message.MessageManager;

public abstract class AbstractComposite extends Composite implements IMessageListener
{
	
	protected final MainController controller;
	protected final Color defaultBackgroundColor;

	public AbstractComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style);
		this.controller = controller;
		MessageManager.INSTANCE.addMessageListener(this);
		defaultBackgroundColor = controller.getColorFactory().getColorWhite();
	}

}
