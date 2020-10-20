package de.etas.tef.config.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import de.etas.tef.config.helper.IConstants;

public final class Utils
{
	public static final Path getCurrentPath()
	{
		return Paths.get(IConstants.EMPTY_STRING).toAbsolutePath();
	}
	
	public static void main(String[] args)
	{
//		System.out.print(Utils.getCurrentPath().toString());
	}
}
