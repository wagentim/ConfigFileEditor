package de.etas.tef.config.main;

import java.util.Map;

import de.etas.tef.config.entity.ConfigBlock;

public interface IWorker
{
	void printAllBlockNames();
	void printAllBlocks();
	void printBlock(String blockName);
	void blockSelected(String text, int index);
	Map<String, ConfigBlock> exec(String filePath);
	void saveFile(String targetFilePath, Map<String, ConfigBlock> saveConfigBlocks);

}
