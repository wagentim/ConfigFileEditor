package de.etas.tef.config.worker.checker;
import de.etas.tef.config.entity.ConfigFilePath;

public interface IChecker 
{
	void check(ConfigFilePath file);
}
