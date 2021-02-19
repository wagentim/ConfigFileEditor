package de.etas.tef.config.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractConfigElement
{
	private List<KeyValue> attributes = Collections.emptyList();
	
	protected boolean hasParameter(String parameter)
	{
		if(attributes == null || attributes.isEmpty())
		{
			return false;
		}
		
		Iterator<KeyValue> it = getAttributes().iterator();

		while (it.hasNext())
		{
			if (it.next().match(parameter))
			{
				return true;
			}
		}

		return false;
	}
	
	public List<KeyValue> getAttributes()
	{
		return attributes;
	}
	
	public void addAttribute(KeyValue kv)
	{
		if(attributes == null)
		{
			attributes = new ArrayList<KeyValue>();
		}
		
		attributes.add(kv);
	}
	
	public void setAttributeList(List<KeyValue> attris)
	{
		this.attributes = attris;
	}
}
