package de.etas.tef.config.ui.composites;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.ui.core.TextEditor;

public class ConfigTextEditor extends TextEditor
{

	public ConfigTextEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		
	}

}
