package de.etas.tef.config.controller;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;

public interface IController
{
	void setInputConfigFile(String filePath);
	void deleteParameters(int[] selectedItems, String blockName);
	void saveFile(String targetFilePath);
	void parameterChanged(CellIndex cell, String newValue);
	ConfigBlock getSelectedConfigBlock();
	String[] getAllBlocks();
	void setSelectedBlock(String blockName);
	IController getParent();
	void setParent(IController parent);
	void setShowConfigBlocks(String text);
	String[] getShowConfigBlocks();
	void updateBlockName(String oldValue, String newValue);
	boolean isEditingLocked();
	void setEditingLocked(boolean isEditingLocked);
	void parameterAdded(KeyValuePair kvp);
	ConfigFile getConfigFile();
}
