package de.etas.tef.config.worker.search;

import java.nio.file.Path;

import de.etas.tef.config.constant.IConstants;

public class SearchInfo
{
	private Path startPath = null;
	private String fileName = IConstants.EMPTY_STRING;
	private String sectionName = IConstants.EMPTY_STRING;
	private String keyName = IConstants.EMPTY_STRING;
	private String valueName = IConstants.EMPTY_STRING;
	
	public String getFileName()
	{
		return fileName;
	}
	public String getSectionName()
	{
		return sectionName;
	}
	public String getKeyName()
	{
		return keyName;
	}
	public String getValueName()
	{
		return valueName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public void setSectionName(String sectionName)
	{
		this.sectionName = sectionName;
	}
	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}
	public void setValueName(String valueName)
	{
		this.valueName = valueName;
	}
	public Path getStartPath()
	{
		return startPath;
	}
	public void setStartPath(Path startPath)
	{
		this.startPath = startPath;
	}
	
	
}
