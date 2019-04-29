package de.etas.tef.config.helper;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
	

public final class Mapper
{
	public static Mapper INSTANCE = new Mapper();
	
	public String getText(int ID)
	{
		switch(ID)
		{
			case IDHelper.ID_SOURCE_FILE_BTN:	
				return Constants.TXT_BTN_SELECT;
			case IDHelper.ID_SOURCE_CONFIG_COMPOSITE:
				return Constants.TXT_LABEL_SOURCE;
			case IDHelper.ID_TARGET_CONFIG_COMPOSITE:
				return Constants.TXT_LABEL_TARGET;
			default: 
				return Constants.EMPTY_STRING;
		}
	}
	
	public Color getColor(int ID, final Display display)
	{
		switch(ID)
		{
			case IDHelper.ID_SOURCE_LABEL:
				return display.getSystemColor(SWT.COLOR_BLUE);
			case IDHelper.ID_TARGET_LABEL:
				return display.getSystemColor(SWT.COLOR_MAGENTA);
			default:
				return display.getSystemColor(SWT.COLOR_BLACK);
		}
	}
}
