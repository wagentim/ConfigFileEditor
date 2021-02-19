package de.etas.tef.config.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;

/**
 * Load and store the template INI file
 * 
 * @author UIH9FE
 *
 */
public class TemplateController 
{
	public static final int TEMPLATE_MCD = 0x00;
	
	private ConfigFilePath currentTemplate;
	private Map<Integer, ConfigFilePath> templates = Collections.emptyMap();
	private MainController controller;
	private List<String> tempNames = new ArrayList<String>();
	private List<String> blocks = new ArrayList<String>();
	private List<String> keys = new ArrayList<String>();
	
	public TemplateController(MainController controller) 
	{
		templates = new HashMap<Integer, ConfigFilePath>();
		this.controller = controller;
		
		loadAllTemplate();
	}
	
	public List<String> getTemplateNames()
	{
		return tempNames;
	}
	
	public void loadAllTemplate()
	{
		// MCD Template
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("./template/MCD.ini");
		ConfigFile cf = controller.getINIFileParser(IConstants.INI_PARSER_TEMPLATE).read(is);
		cf.getMustHaveElement();
		ConfigFilePath cfp = new ConfigFilePath(null);
		cfp.setConfigFile(cf);
		templates.put(TEMPLATE_MCD, cfp);
		tempNames.add("MCD Template");
	}
	
	public void setTemplate(int type)
	{
		if(type == TEMPLATE_MCD)
		{
			currentTemplate = templates.get(TEMPLATE_MCD);
			updateBlocks();
		}
		else
		{
			currentTemplate = null;
		}
	}
	
	private void updateBlocks()
	{
		blocks.clear();
		if(currentTemplate == null)
		{
			return;
		}
		
		Iterator<ConfigBlock> it = currentTemplate.getConfigFile().getConfigBlocks().iterator();
		
		while(it.hasNext())
		{
			blocks.add(it.next().getBlockName());
		}
	}
	
	public ConfigFilePath getTempate()
	{
		return currentTemplate;
	}
	
	public List<String> getTempBlockNames()
	{
		return blocks;
	}
	
	private ConfigBlock findBlock(String name)
	{
		Iterator<ConfigBlock> it = currentTemplate.getConfigFile().getConfigBlocks().iterator();
		
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			
			if(cb.getBlockName().equalsIgnoreCase(name))
			{
				return cb;
			}
		}
		
		return null;
	}
	
	public List<String> getTempKeyNames(String block)
	{
		keys.clear();
		ConfigBlock cb = findBlock(block);
		
		if(cb == null)
		{
			return keys;
		}
		
		Iterator<KeyValuePair> it = cb.getAllParameters().iterator();
		
		while(it.hasNext())
		{
			keys.add(it.next().getKey());
		}
		
		return keys;
	}

}
