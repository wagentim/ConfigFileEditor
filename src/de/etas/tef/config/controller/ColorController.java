package de.etas.tef.config.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * Handle Color
 * 
 * @author UIH9FE
 *
 */
public final class ColorController
{
	private Color BACKGROUND;
	private final Color WHITE;
	private final Color GRAY;
	private final Color BLACK;
	private final Color RED;
	private final Color BLUE;
	private final Color DARK_GREEN;
	private final Color LIGHT_GREEN;
	private final Color LIGHT_BLUE;
	
	public ColorController(final Display display)
	{
		BACKGROUND = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		WHITE = display.getSystemColor(SWT.COLOR_WHITE);
		GRAY = display.getSystemColor(SWT.COLOR_GRAY);
		BLACK = display.getSystemColor(SWT.COLOR_BLACK);
		RED = display.getSystemColor(SWT.COLOR_RED);
		BLUE = display.getSystemColor(SWT.COLOR_BLUE);
		DARK_GREEN = display.getSystemColor(SWT.COLOR_DARK_GREEN);
		LIGHT_GREEN = display.getSystemColor(SWT.COLOR_GREEN);
		LIGHT_BLUE = new Color(Display.getCurrent(), 240,236,254);
	}
	
	public Color getColorBackground()
	{
		BACKGROUND = WHITE;
		return BACKGROUND;
	}
	
	public Color getColorWhite()
	{
		return WHITE;
	}
	
	public Color getColorGray()
	{
		return GRAY;
	}
	
	public Color getColorBlack()
	{
		return BLACK;
	}
	
	public Color getColorRed()
	{
		return RED;
	}
	
	public Color getColorBlue()
	{
		return BLUE;
	}
	
	public Color getColorDarkGreen()
	{
		return DARK_GREEN;
	}
	
	public Color getColorGreen()
	{
		return LIGHT_GREEN;
	}
	
	public Color getColorLightBlue()
	{
		return LIGHT_BLUE;
	}
}
