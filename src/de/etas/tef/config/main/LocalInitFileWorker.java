package de.etas.tef.config.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Constants;

public class LocalInitFileWorker extends AbstractWorker
{
	private Map<String, ConfigBlock> configs = null;
	
	public LocalInitFileWorker()
	{
		super();
		
		if( null == configs )
		{
			configs = new HashMap<String, ConfigBlock>();
		}
		
	}
	public void printAllBlockNames()
	{
		if( null != configs )
		{
			Collection<ConfigBlock> values = configs.values();
			Iterator<ConfigBlock> it = values.iterator();
			while(it.hasNext())
			{
				System.out.println(it.next().getBlockName());
			}
		}
	}
	
	public void printAllBlocks()
	{
		if( null != configs )
		{
			Set<String> keys = configs.keySet();
			Iterator<String> itKey = keys.iterator();
			while(itKey.hasNext())
			{
				String key = itKey.next();
				System.out.println(key);
				
				printBlock(key);
				
				System.out.println("---------------------");
			}
		}
	}

	@Override
	public void printBlock(String key)
	{
		ConfigBlock block = configs.get(key);
		if( null != block )
		{
			List<KeyValuePair> paras = block.getAllParameters();
			
			if( !paras.isEmpty() )
			{
				for(int i = 0; i < paras.size(); i++)
				{
					System.out.println(paras.get(i).toString());
				}
			}
		}
	}

	@Override
	public Map<String, ConfigBlock> exec(String filePath)
	{
		if( null == filePath || filePath.isEmpty() )
		{
			return Collections.emptyMap();
		}
		
		configs = new HashMap<String, ConfigBlock>();
		
		File file = new File(filePath);
		
		try
		{
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;
			int index = 0;
			ConfigBlock currentConfigBlock = null;
			
			while( null != (s = br.readLine() ) )
			{
				s = s.trim();
				
				if( s.isEmpty() || s.startsWith("--") || s.startsWith(";") )
				{
					continue;
				}
				else
				{
					if(s.startsWith("["))
					{
						currentConfigBlock = new ConfigBlock();
						currentConfigBlock.setIndex(index);
						index++;
						
						int endIndex = s.indexOf("]");
						
						if( endIndex <= 1 )
						{
							currentConfigBlock.setBlockName("######");
						}
						else
						{
							currentConfigBlock.setBlockName(s.substring(1, endIndex));
						}
						configs.put(currentConfigBlock.getBlockName(), currentConfigBlock);
					}
					else
					{
						if( null == currentConfigBlock )
						{
							ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR: Fail Config Block Title -> " + s);
						}
						else
						{
							int split = s.indexOf("=");
							
							if( split < 1 )
							{
								ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR: Parameter -> " + s);
							}
							else
							{
								KeyValuePair kvp = new KeyValuePair();
								kvp.setKey(s.substring(0, split));
								kvp.setValue(s.substring(split+1, s.length()));
								currentConfigBlock.addParameter(kvp);
							}
						}
					}
				}
			}
			
			return configs;
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return configs;
	}


	public void blockSelected(String text, int index)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveFile(String targetFilePath, Map<String, ConfigBlock> saveConfigBlocks)
	{
		if( null == targetFilePath || targetFilePath.isEmpty() || null == saveConfigBlocks)
		{
			return;
		}
		
		File f = new File(targetFilePath);
		
		try
		{
			@SuppressWarnings("resource")
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			
			Collection<ConfigBlock> values = saveConfigBlocks.values();
			
			Iterator<ConfigBlock> it = values.iterator();
			
			StringBuffer sb = null;
			
			while(it.hasNext())
			{
				ConfigBlock cb = it.next();
				sb = new StringBuffer();
				
				sb.append("[");
				sb.append(cb.getBlockName());
				sb.append("]");
				sb.append("\n");
				
				Iterator<KeyValuePair> iter = cb.getAllParameters().iterator();
				
				while(iter.hasNext())
				{
					KeyValuePair pair = iter.next();
					
					sb.append(pair.getKey());
					sb.append("=");
					sb.append(pair.getValue());
					sb.append("\n");
				}
				
				sb.append("\n");
				bw.write(sb.toString());
				bw.flush();
			}
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Override
//	public void setSelectedBlock(int currentComboxIndex)
//	{
////		notifier.clearTable();
//		Iterator<String> it = configs.keySet().iterator();
//		
//		if( currentComboxIndex <= 0 )
//		{
//			return;
//		}
//		
//		int i = 0;
//		
//		while( i < currentComboxIndex )
//		{
//			it.next();
//			i++;
//		}
//		setSelectedBlock(it.next());
//	}

}
