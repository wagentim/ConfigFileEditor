package de.etas.tef.config.entity;

import java.util.StringTokenizer;

import de.etas.tef.config.constant.IConstants;

public final class KeyValue 
{
	private String key = IConstants.EMPTY_STRING;
	private String value = IConstants.EMPTY_STRING;
	
	public KeyValue()
	{
		
	}
	
	public KeyValue(String key, String value)
	{
		setKey(key);
		setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean match(String input)
	{
		if(input == null || input.isEmpty() || !input.contains(IConstants.SYMBOL_EQUAL))
		{
			return false;
		}
		
		StringTokenizer st = new StringTokenizer(input, IConstants.SYMBOL_EQUAL);
		
		return st.nextToken().trim().equalsIgnoreCase(getKey().trim()) && st.nextToken().trim().equalsIgnoreCase(getValue().trim());
	}

	@Override
	public String toString() {
		return "KeyValue [key=" + key + ", value=" + value + "]";
	}
}
