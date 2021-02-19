package de.etas.tef.config.entity;

import java.nio.file.Path;

import de.etas.tef.config.constant.IConstants;

public class ChangeAction
{
	private Path file = null;
	private String oldSection = IConstants.EMPTY_STRING;
	private String oldKey = IConstants.EMPTY_STRING;
	private String oldValue = IConstants.EMPTY_STRING;
	private String newSection = IConstants.EMPTY_STRING;
	private String newKey = IConstants.EMPTY_STRING;
	private String newValue = IConstants.EMPTY_STRING;
	private int type = IConstants.UNKNOWN;
	
	public ChangeAction(int type)
	{
		this.type = type;
	}
	
	public Path getFile()
	{
		return file;
	}
	public void setFile(Path file)
	{
		this.file = file;
	}
	public String getOldSection()
	{
		return oldSection;
	}
	public void setOldSection(String oldSection)
	{
		this.oldSection = oldSection;
	}
	public String getOldKey()
	{
		return oldKey;
	}
	public void setOldKey(String oldKey)
	{
		this.oldKey = oldKey;
	}
	public String getOldValue()
	{
		return oldValue;
	}
	public void setOldValue(String oldValue)
	{
		this.oldValue = oldValue;
	}
	public String getNewSection()
	{
		return newSection;
	}
	public void setNewSection(String newSection)
	{
		this.newSection = newSection;
	}
	public String getNewKey()
	{
		return newKey;
	}
	public void setNewKey(String newKey)
	{
		this.newKey = newKey;
	}
	public String getNewValue()
	{
		return newValue;
	}
	public void setNewValue(String newValue)
	{
		this.newValue = newValue;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}
