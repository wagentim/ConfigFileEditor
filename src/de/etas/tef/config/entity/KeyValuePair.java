package de.etas.tef.config.entity;

import de.etas.tef.config.helper.Constants;

public class KeyValuePair
{
	private String key = Constants.EMPTY_STRING;
	private String value = Constants.EMPTY_STRING;
	private String other = Constants.EMPTY_STRING;
	private String forthValue = Constants.EMPTY_STRING;
	
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public void clean()
	{
		setKey(Constants.EMPTY_STRING);
		setValue(Constants.EMPTY_STRING);
	}
	
		public String getOther()
	{
		return other;
	}
	public void setOther(String other)
	{
		this.other = other;
	}
	
	
	
	@Override
	public String toString()
	{
		return "KeyValuePair [key=" + key + ", value=" + value + ", other=" + other + ", forthValue=" + forthValue
				+ "]";
	}
	
	@Override
	public KeyValuePair clone() throws CloneNotSupportedException 
	{
        KeyValuePair clone = null;
        clone = new KeyValuePair();
		clone.setKey(this.getKey());
		clone.setValue(this.getValue());
		clone.setOther(this.getOther());
		clone.setForthValue(this.getForthValue());
        return clone;
    }
	public String getForthValue()
	{
		return forthValue;
	}
	public void setForthValue(String forthValue)
	{
		this.forthValue = forthValue;
	}
	
}
