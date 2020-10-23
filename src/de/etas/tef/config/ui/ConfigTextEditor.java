package de.etas.tef.config.ui;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;

public class ConfigTextEditor extends TextEditor
{

	public ConfigTextEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComposite();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

}
