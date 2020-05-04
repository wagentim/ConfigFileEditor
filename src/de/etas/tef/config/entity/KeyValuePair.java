package de.etas.tef.config.entity;

import de.etas.tef.config.controller.IConstants;

public class KeyValuePair
{
	private String key = IConstants.SYMBOL_INIT_FILE_COMMENT_DASH;
	private String value = IConstants.SYMBOL_INIT_FILE_COMMENT_DASH;
	private String other = IConstants.EMPTY_STRING;
	private String forthValue = IConstants.EMPTY_STRING;
	private String comment = IConstants.EMPTY_STRING;
	
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
		setKey(IConstants.EMPTY_STRING);
		setValue(IConstants.EMPTY_STRING);
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
				+ ", comment=" + comment + "]";
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	@Override
	public KeyValuePair clone() throws CloneNotSupportedException 
	{
        KeyValuePair clone = new KeyValuePair();
		clone.setKey(this.getKey());
		clone.setValue(this.getValue());
		clone.setOther(this.getOther());
		clone.setForthValue(this.getForthValue());
		clone.setComment(this.getComment());
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
