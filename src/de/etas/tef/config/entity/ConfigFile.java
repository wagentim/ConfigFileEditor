package de.etas.tef.config.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.etas.tef.config.helper.Utils;

public final class ConfigFile
{
	private Path filePath = null;
	private List<ConfigBlock> configBlocks = null;
	private List<String> comments = null;
	private Map<Integer, List<Problem>> problems = null;
	private Map<String, List<String>> mustHaveElement = null;
	
	public static final String MUST_HAVE = "musthave=true";
	
	public ConfigFile()
	{
		configBlocks = new ArrayList<ConfigBlock>();
	}
	
	public Path getFilePath()
	{
		return filePath;
	}
	public void setFilePath(Path filePath)
	{
		this.filePath = filePath;
	}
	public List<ConfigBlock> getConfigBlocks()
	{
		return configBlocks;
	}
	public void setConfigBlocks(List<ConfigBlock> configBlocks)
	{
		this.configBlocks = configBlocks;
	}
	public List<String> getComments()
	{
		return comments;
	}
	public void addComments(String comment)
	{
		if(comments == null)
		{
			comments = new ArrayList<String>();
		}
		
		comments.add(comment);
	}
	
	public void addConfigBlock(ConfigBlock cb)
	{
		if( null == cb )
		{
			return;
		}
		
		getConfigBlocks().add(cb);
	}
	
	public List<ConfigBlock> findConfigBlock(String name)
	{
		if(Utils.isStringEmpty(name))
		{
			return null;
		}
		
		Iterator<ConfigBlock> it = configBlocks.iterator();
		
		List<ConfigBlock> result = new ArrayList<ConfigBlock>();
		
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
			
			if(cb.getBlockName().equalsIgnoreCase(name))
			{
				result.add(cb);
			}
		}
		
		return result;
	}
	
	public Map<Integer, List<Problem>> getProblemList()
	{
		if(problems == null)
		{
			problems = new HashMap<Integer, List<Problem>>();
		}
		
		return problems;
	}
	
	public void addProblem(Problem p)
	{
		if(p == null)
		{
			return;
		}
		
		if(problems == null)
		{
			problems = new HashMap<Integer, List<Problem>>();
		}
		
		List<Problem> ps = problems.get(p.getProblemType());
		
		if(ps == null)
		{
			ps = new ArrayList<Problem>();
			problems.put(p.getProblemType(), ps);
		}
		
		ps.add(p);
	}
	
	public void removeProblem(Problem p)
	{
		if(problems == null || problems.isEmpty())
		{
			return;
		}
		
		List<Problem> ps = problems.get(p.getProblemType());
		
		if(ps == null)
		{
			return;
		}

		ps.remove(p);
	}
	
	public Map<String, List<String>> getMustHaveElement()
	{
		if(mustHaveElement == null || mustHaveElement.isEmpty())
		{
			mustHaveElement = new HashMap<String, List<String>>();
			extractMustHaveElement();
		}
		
		return mustHaveElement;
	}
	
	private void extractMustHaveElement()
	{
		Iterator<ConfigBlock> it = getConfigBlocks().iterator();
		
		while(it.hasNext())
		{
			findBlockParameter(it.next(), MUST_HAVE, mustHaveElement);
		}
	}
	
	private void findBlockParameter(ConfigBlock cb, String parameter, Map<String, List<String>> list)
	{
		List<String> keys = getKeyParameter(cb, parameter);
		
		if(keys == null || keys.isEmpty())
		{
			if(cb.hasParameter(parameter))
			{
				list.put(cb.getBlockName(), null);
			}
		}
		else
		{
			list.put(cb.getBlockName(), keys);
		}
	}

	private List<String> getKeyParameter(ConfigBlock cb, String parameter)
	{
		List<KeyValuePair> paras = cb.getAllParameters();
		if(paras == null || paras.isEmpty())
		{
			return null;
		}
		
		List<String> result = new ArrayList<String>();
		
		Iterator<KeyValuePair> keys = paras.iterator();
		
		while(keys.hasNext())
		{
			KeyValuePair kvp = keys.next();
			if(kvp.getType() == KeyValuePair.TYPE_COMMENT || kvp.getType() == KeyValuePair.TYPE_UNKNOWN)
			{
				continue;
			}
			
			if(kvp.hasParameter(parameter))
			{
				result.add(kvp.getKey());
			}
		}
		
		return result;
	}

	@Override
	public ConfigFile clone() throws CloneNotSupportedException
	{
		ConfigFile cf = new ConfigFile();
		
		cf.setFilePath(this.getFilePath());
		
		List<ConfigBlock> newList = new ArrayList<ConfigBlock>();
		List<String> commentList = new ArrayList<String>();
		
		Iterator<ConfigBlock> itcb = configBlocks.iterator();
		
		while(itcb.hasNext())
		{
			newList.add(itcb.next().clone());
		}
		
		if(comments != null)
		{
			Iterator<String> itc = comments.iterator();
			
			while(itc.hasNext())
			{
				commentList.add(itc.next());
			}
		}
		
		return cf;
	}
	
}
