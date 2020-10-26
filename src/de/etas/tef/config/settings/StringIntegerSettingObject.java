package de.etas.tef.config.settings;

import de.etas.tef.config.helper.IConstants;

public class StringIntegerSettingObject implements ISettingObject<String, Integer>
{
	private String key;
	private Integer value;
	
	public StringIntegerSettingObject()
	{
		initValue();
	}
	
	private void initValue()
	{
		key = IConstants.EMPTY_STRING;
		value = -1;
	}

	@Override
	public void saveSetting(String key, Integer value)
	{
		if(key != null && value != null && !key.isEmpty())
		{
			this.key = key;
			this.value = value;
		}
		
	}

	@Override
	public Integer getSettingObject(String key)
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[ \"");
		sb.append(key);
		sb.append("\" : ");
		sb.append(value);
		sb.append(" ]");
		return sb.toString();
	}

}
