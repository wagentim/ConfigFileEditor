package de.etas.tef.config.settings;

import de.etas.tef.config.constant.IConstants;

public class StringStringSettingObject implements ISettingObject<String, String>
{
	private String key;
	private String value;
	
	public StringStringSettingObject()
	{
		initValues();
	}

	@Override
	public String getSettingObject(String key)
	{
		if(key.contentEquals(this.key))
		{
			return value;
		}
		
		return IConstants.EMPTY_STRING;
	}

	@Override
	public void saveSetting(String key, String value)
	{
		if(key != null && value != null && !key.isEmpty() && !value.isEmpty())
		{
			this.key = key;
			this.value = value;
		}
		else
		{
			initValues();
		}
	}
	
	private void initValues()
	{
		key = IConstants.EMPTY_STRING;
		value = IConstants.EMPTY_STRING;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[ \"");
		sb.append(key);
		sb.append("\" : \"");
		sb.append(value);
		sb.append("\" ]");
		return sb.toString();
	}

}
