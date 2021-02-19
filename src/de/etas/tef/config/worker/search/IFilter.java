package de.etas.tef.config.worker.search;

import de.etas.tef.config.entity.ConfigFilePath;

public interface IFilter
{
	boolean check(ConfigFilePath file, SearchInfo si);
}
