package de.etas.tef.config.worker.parser;

import java.util.List;

import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.KeyValue;
/**
 * Paser for the Template INI File
 * 
 * [Section] <Attri_1, Attri_2...>
 * Key = Value <Attri_1, Attri_2...>
 * 
 * The comments line will be ignored (the line start with "--" and ";" will be ignored)
 * 
 * @author UIH9FE
 *
 */
public class TemplateIniFileParser extends AbstractINIFileParser
{

	protected void handleBlockAttribute(ConfigBlock block, String s) 
	{
		List<KeyValue> attris = getAttributes(s);
		if(attris == null || attris.size() < 0)
		{
			return;
		}
		
		block.setAttributeList(attris);
		
	}

}
