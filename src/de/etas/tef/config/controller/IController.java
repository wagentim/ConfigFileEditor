package de.etas.tef.config.controller;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;

public interface IController
{
	// new methods
	void setInputConfigFile(String filePath, int compositeID);
	void deleteParameters(int[] selectedItems, String text, int compositeID);
	void saveFile(String targetFilePath, int compositeID);
	void parameterChanged(CellIndex cell, String newValue, int compositeID);
	ConfigBlock getCurrTargetConfigBlock();
	ConfigBlock getCurrSourceConfigBlock();
	void setConnected(boolean isConnected);
	String[] getAllBlocks(int compositeID);
	void setSelectedBlock(String blockName, int compositeID);
	boolean isConnected();
	void setCurrSourceConfigBlock(ConfigBlock cb);
	void setCurrTargetConfigBlock(ConfigBlock cb);
}
