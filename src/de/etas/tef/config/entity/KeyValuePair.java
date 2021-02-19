package de.etas.tef.config.entity;

import de.etas.tef.config.constant.IConstants;

public class KeyValuePair extends AbstractConfigElement
{
	public static final int TYPE_PARA = 0x00;
	public static final int TYPE_COMMENT = 0x01;
	public static final int TYPE_UNKNOWN = 0x02;

	private String key = IConstants.SYMBOL_INIT_FILE_COMMENT_DASH;
	private String value = IConstants.SYMBOL_INIT_FILE_COMMENT_DASH;
	private int type = TYPE_UNKNOWN;
	
	public KeyValuePair(String key, String value) {
		setKey(key);
		setValue(value);
	}
	
	public KeyValuePair() {
	}
	
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
		setType(TYPE_UNKNOWN);
	}
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	@Override
	public KeyValuePair clone() throws CloneNotSupportedException 
	{
        KeyValuePair clone = new KeyValuePair();
		clone.setKey(this.getKey());
		clone.setValue(this.getValue());
		clone.setType(this.getType());
        return clone;
    }

	@Override
	public String toString() {
		return "KeyValuePair [key=" + key + ", value=" + value + ", type=" + type + "]";
	}
}
