package de.etas.tef.config.log;

import java.nio.file.Path;

import de.etas.tef.config.entity.ConfigBlock;

public final class LoggerController
{
	public static final LoggerController handler = new LoggerController();
	
	public static LoggerController INSTANCE()
	{
		return handler;
	}
	
	public void addModifyInfo(String oldValue, String newValue)
	{
		
	}
	
	public void error(String errorInfo)
	{
		
	}
	
	public void info(String info)
	{
		
	}
	
	public void addIniFileFormatError(String reason, String text)
	{
		System.out.println("INI Start: " + text);
	}
	
	public void addIniFileFormatError(ConfigBlock cb, String reason, String text)
	{
		System.out.println("INI Paras: " + text);
	}
}
