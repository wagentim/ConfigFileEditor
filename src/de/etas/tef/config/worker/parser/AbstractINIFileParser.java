package de.etas.tef.config.worker.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValue;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;

public abstract class AbstractINIFileParser
{
	public ConfigFile read(Path filePath)
	{
		ConfigFile configFile = new ConfigFile();
		configFile.setFilePath(filePath);
//		System.out.println(filePath.getFileName().toString());

		try
		{
			Charset charset = Charset.forName("ISO-8859-1");
			List<String> lines = Files.readAllLines(filePath, charset);

			handleLines(configFile, lines);
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return configFile;
	}
	
	public ConfigFile read(InputStream stream)
	{
		ConfigFile configFile = new ConfigFile();

		try
		{
			
//			Charset charset = Charset.forName("ISO-8859-1");
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line = null;
			List<String> lines = new ArrayList<String>();
			while((line = br.readLine()) != null)
			{
				lines.add(line);
			}
			handleLines(configFile, lines);
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return configFile;
	}
	
	protected void handleLines(ConfigFile configFile, List<String> lines) 
	{
		ConfigBlock block = null;
		KeyValuePair pair = null;

		boolean fileStart = true;

		for (String s : lines)
		{
			s = s.trim();
			
			// comment for the whole config file
			if(fileStart)
			{
				if(s.isEmpty())
				{
					continue;
				}
				else if(isLineComment(s))
				{
					configFile.addComments(s);
				}
				else if(s.startsWith(IConstants.SYMBOL_LEFT_BRACKET))
				{
					fileStart = false;
					block = new ConfigBlock();
					block.setBlockName(extractBlockName(s));
					handleBlockAttribute(block, s);
					configFile.addConfigBlock(block);
				}
				else
				{
//					LoggerController.INSTANCE().addIniFileFormatError(IErroReason.ERROR_INI_FILE_START, s);
				}
			}
			else
			{
				pair = new KeyValuePair();

				if (isLineComment(s))
				{
					pair.setKey(s);
					pair.setType(KeyValuePair.TYPE_COMMENT);
					block.addParameterInLast(pair);
				}
				else if (s.contains(IConstants.SYMBOL_EQUAL))
				{
					extractkeyValue(pair, s, block);
					List<KeyValue> attrs = getAttributes(s);
					if(attrs != null && attrs.size() > 0)
					{
						pair.setAttributeList(attrs);
					}
					block.addParameterInLast(pair);
				}
				else if (s.startsWith(IConstants.SYMBOL_LEFT_BRACKET))
				{
					block = new ConfigBlock();
					block.setBlockName(extractBlockName(s));
					configFile.addConfigBlock(block);
				}
				else
				{
//					LoggerController.INSTANCE().addIniFileFormatError(block, IErroReason.ERROR_INI_FILE_PARAMETER, s);
				}
			}
		}
	}
	
	protected abstract void handleBlockAttribute(ConfigBlock block, String s);

	protected List<KeyValue> getAttributes(String s)
	{
//		System.out.println(s);
		
		int attrStart = s.indexOf(IConstants.SYMBOL_LEFT_ATTR_BRACKET);
		int attrEnd = s.indexOf(IConstants.SYMBOL_RIGHT_ATTR_BRACKET);
		
		if(attrStart < 0 || attrEnd < 0 || attrEnd < attrStart)
		{
			return null;
		}
		
		String attr = s.substring(attrStart + 1, attrEnd);
		StringTokenizer st = new StringTokenizer(attr, ",");
		
		if(st.countTokens() < 0)
		{
			return null;
		}
		
		List<KeyValue> result = new ArrayList<KeyValue>();
		
		while(st.hasMoreTokens())
		{
			KeyValue kv = Utils.parserKeyValue(st.nextToken());
			if(kv != null)
			{
				result.add(kv);
			}
		}
		
		if(result.size() > 0)
		{
			return result;
		}
		
		return null;
	}
	
	protected boolean isLineComment(String line)
	{
		return line.startsWith("--") || line.startsWith(";") || line.startsWith("#") || line.isEmpty();
	}
	
	protected String extractBlockName(String line)
	{
		int index = line.indexOf(IConstants.SYMBOL_RIGHT_BRACKET);
		
		return line.subSequence(1, index).toString();
	}
	
	protected void extractkeyValue(KeyValuePair kvp, String line, ConfigBlock block)
	{
		if(line.contains(IConstants.SYMBOL_LEFT_ATTR_BRACKET))
		{
			line = line.substring(0, line.indexOf(IConstants.SYMBOL_LEFT_ATTR_BRACKET));
		}
		
		StringTokenizer st = new StringTokenizer(line, IConstants.SYMBOL_EQUAL);
		
		if(st.countTokens() != 2)
		{
//			LoggerController.INSTANCE().addIniFileFormatError(block, IErroReason.ERROR_INI_FILE_PARAMETER, line);
		}
		
		int index = 0;
		StringBuilder sb = new StringBuilder();
		
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			
			if(index == 0)
			{
				kvp.setKey(token);
			}
			else if(index == 1)
			{
				sb.append(token);
			}
			else
			{
				sb.append(IConstants.SYMBOL_EQUAL);
				sb.append(token);
			}
			
			index++;
		}
		
		kvp.setValue(sb.toString());
		kvp.setType(KeyValuePair.TYPE_PARA);
	}
	
	protected void printConfigFile(ConfigFile cf)
	{
		if(cf == null)
		{
//			System.out.println("Config File is NULL");
			return;
		}
		
//		System.out.println("== " + cf.getFilePath().toFile().getAbsolutePath() + " ==");
		
		Iterator<ConfigBlock> it = cf.getConfigBlocks().iterator();
		while(it.hasNext())
		{
			ConfigBlock cb = it.next();
//			System.out.println(IConstants.SYMBOL_LEFT_BRACKET + cb.getBlockName() + IConstants.SYMBOL_RIGHT_BRACKET);
			
			Iterator<KeyValuePair> itkvp = cb.getAllParameters().iterator();
			
			while(itkvp.hasNext())
			{
				KeyValuePair kvp = itkvp.next();
				
				int type = kvp.getType();
				
				switch(type)
				{
				case KeyValuePair.TYPE_COMMENT:
					System.out.println(kvp.getKey());
					break;
				case KeyValuePair.TYPE_PARA:
					System.out.println(kvp.getKey() + IConstants.SYMBOL_EQUAL + kvp.getValue());
					break;
				}
			}
			
//			System.out.println();
		}
	}
}
