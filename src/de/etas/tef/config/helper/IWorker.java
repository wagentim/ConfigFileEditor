package de.etas.tef.config.helper;

import de.etas.tef.config.entity.ConfigFile;

public interface IWorker
{
	void blockSelected(String text, int index);
	ConfigFile read(String filePath);
	void write(String targetFilePath, ConfigFile cf);
	
}
