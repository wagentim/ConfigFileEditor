package de.etas.tef.config.controller;

import java.util.HashMap;

import de.etas.tef.config.checker.FileNameChecker;
import de.etas.tef.config.checker.IChecker;

public final class CheckerFactory
{
	@SuppressWarnings("rawtypes")
	private HashMap<String, IChecker> checkerList  = new HashMap<String, IChecker>();
	
	public CheckerFactory()
	{
		init();
	}
	
	private void init()
	{
		checkerList.put(IConstants.CHECKER_FILE_NAME, new FileNameChecker());
	}
	
	public FileNameChecker getFileNameChecker()
	{
		return (FileNameChecker) checkerList.get(IConstants.CHECKER_FILE_NAME);
	}
}
