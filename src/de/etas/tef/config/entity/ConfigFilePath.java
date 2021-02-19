package de.etas.tef.config.entity;

import java.nio.file.Path;

public class ConfigFilePath
{
	private final Path path;
	private ConfigFile cf = null;
	
	public ConfigFilePath(final Path path)
	{
		this.path = path;
	}
	
	public ConfigFile getConfigFile()
	{
		return cf;
	}
	
	public void setConfigFile(ConfigFile cf)
	{
		this.cf = cf;
	}
	
	public Path getPath()
	{
		return path;
	}
}
