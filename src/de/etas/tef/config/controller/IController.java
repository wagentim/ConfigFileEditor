package de.etas.tef.config.controller;

import de.etas.tef.config.entity.CellIndex;
import de.etas.tef.config.entity.ConfigBlock;

public interface IController
{
	String[] getBlockNames(boolean isSource);
	void setSelectedBlock(String blockName, boolean isSource);
	boolean isConnected();
	void setConnected(boolean isConnected);
	void updateParameter(CellIndex cell, String newValue, boolean isSource);
	void setCurrSourceConfigBlock(ConfigBlock cb);
	void setCurrTargetConfigBlock(ConfigBlock cb);
	ConfigBlock getCurrTargetConfigBlock();
	ConfigBlock getCurrSourceConfigBlock();
	public String getSourceFilePath();
	public void setSourceFilePath(String sourceFilePaht);
	public String getTargetFilePath();
	public void setTargetFilePath(String targetFilePath);
	void saveFile(String targetFilePath, boolean isSource);
	void deleteParameters(int[] selectedItems, String text, boolean isSource);
}
