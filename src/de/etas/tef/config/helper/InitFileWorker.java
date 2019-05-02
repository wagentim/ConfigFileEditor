package de.etas.tef.config.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;

import de.etas.tef.config.action.ActionManager;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;

public class InitFileWorker implements IIniFileWorker
{
	
	@Override
	public void readFile(String filePath, Object result) throws IOException
	{
		if( !Validator.INSTANCE().validFile(filePath, true) )
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "Cannot find file: " + filePath);
			
			return;
		}
		
		if( !(result instanceof ConfigFile ) || null == result)
		{
			throw new RuntimeException("Cannot cast the input object to " + ConfigFile.class.getName());
		}
		
		// start parsing the configure *.ini file
		
		ConfigFile cf = (ConfigFile)result;
		
		Path path = Paths.get(filePath);
		
		BufferedReader br = Files.newBufferedReader(path);
		
		String currentLine = null;
		
		StringBuffer sb = new StringBuffer();
		
		ConfigBlock cb = null;
		
		int status = TAG_FILE_NEW;
		
		int lineCount = 1;
		
		while ( null != (currentLine = br.readLine()) )
		{
			currentLine = currentLine.trim();	// remove the empty character
			
			if( TAG_FILE_NEW == status )
			{
				if( currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_DASH) || 
						currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_SEMICOLON)
						)
				{
					status = TAG_FILE_COMMENT_START;
				}
				else if ( currentLine.startsWith(Constants.SYMBOL_LEFT_BRACKET) )
				{
					status = TAG_BLOCK_NAME_START;
					cb = new ConfigBlock();
					cf.getConfigBlocks().add(cb);
				}
				else if (currentLine.isEmpty())
				{
					status = TAG_BLOCK_COMMENT_START;
					cb = new ConfigBlock();
					cf.getConfigBlocks().add(cb);
					continue;
				}
				else
				{
					ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR by Parsing File Comments in line: " + lineCount);
					break;
				}
			}
			
			if( TAG_BLOCK_COMMENT_START == status )
			{
				if( currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_DASH) || 
						currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_SEMICOLON)
						)
				{
					sb.delete(0, sb.length());
					sb.append(cb.getComments()).append(System.lineSeparator()).append(currentLine);
					cb.setComments(sb.toString());
				}
				else if ( currentLine.startsWith(Constants.SYMBOL_LEFT_BRACKET) )
				{
					status = TAG_BLOCK_NAME_START;
				}
				else if (currentLine.isEmpty())
				{
					continue;
				}
				else
				{
					ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR by Parsing Block Comments in line: " + lineCount);
					break;
				}
			}
			
			if( TAG_FILE_COMMENT_START == status )
			{
				
				if (currentLine.isEmpty())
				{
					status = TAG_BLOCK_COMMENT_START;
					cb = new ConfigBlock();
					cf.getConfigBlocks().add(cb);
					continue;
				}
				else if ( currentLine.startsWith(Constants.SYMBOL_LEFT_BRACKET) )
				{
					status = TAG_BLOCK_NAME_START;
				}
				else
				{
					sb.delete(0, sb.length());
					sb.append(cf.getComments()).append(System.lineSeparator()).append(currentLine);
					cf.setComments(sb.toString());
				}
			}
			
			if( TAG_BLOCK_NAME_START == status )
			{
				if( null == cb )
				{
					cb = new ConfigBlock();
					cf.getConfigBlocks().add(cb);
				}
				
				parserBlockName(currentLine, lineCount, cb);
				status = TAG_BLOCK_NAME_FINISH;
				continue;
			}
			
			if( TAG_BLOCK_NAME_FINISH == status )
			{
				if( currentLine.isEmpty() )
				{
					continue;
				}
				else if( currentLine.startsWith(Constants.SYMBOL_LEFT_BRACKET) )
				{
					cb = new ConfigBlock();
					cf.getConfigBlocks().add(cb);
					parserBlockName(currentLine, lineCount, cb);
					status = TAG_BLOCK_NAME_FINISH;
					continue;
				}
				else
				{
					int split = currentLine.indexOf(Constants.SYMBOL_EQUAL);
					
					if( split < 0 )
					{
						if( currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_DASH) || 
								currentLine.startsWith(Constants.SYMBOL_INIT_FILE_COMMENT_SEMICOLON)
								)
						{
							status = TAG_BLOCK_COMMENT_START;
							cb = new ConfigBlock();
							cf.getConfigBlocks().add(cb);
							sb.delete(0, sb.length());
							sb.append(cb.getComments()).append(System.lineSeparator()).append(currentLine);
							cb.setComments(sb.toString());
						}
						else
						{
							ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR by Parsing Parameter in line: " + lineCount);
						}
					}
					else
					{
						KeyValuePair pair = new KeyValuePair();
						
						pair.setKey(currentLine.substring(0, split));
						pair.setValue(currentLine.substring(split + 1, currentLine.length()));
						
						cb.addParameterInLast(pair);
					}
				}
			}
			
			lineCount++;
		}
	}
	
	private void parserBlockName(String currentLine, int lineCount, ConfigBlock configBlock)
	{
		int index = currentLine.indexOf(Constants.SYMBOL_RIGHT_BRACKET);
		
		if( index < 1 )
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "ERROR by parsing block name in line: " + lineCount);
			configBlock.setBlockName(currentLine.substring(1, currentLine.length()));
		}
		else
		{
			configBlock.setBlockName(currentLine.substring(1, currentLine.length() - 1));
		}
	}
		
	@Override
	public void writeFile(String filePath, Object result) throws IOException
	{
		if( !Validator.INSTANCE().validFile(filePath, true) )
		{
			ActionManager.INSTANCE.sendAction(Constants.ACTION_LOG_WRITE_ERROR, "Cannot find file: " + filePath);
			
			return;
		}
		
		if( !(result instanceof ConfigFile ) || null == result)
		{
			throw new RuntimeException("Cannot cast the input object to " + ConfigFile.class.getName());
		}
		
		ConfigFile configFile = (ConfigFile)result;
		
		Path path = Paths.get(filePath);
		
		BufferedWriter bw = Files.newBufferedWriter(path);
		
		String s = Constants.EMPTY_STRING;

		try
		{
			s = configFile.getComments();
			
			if( null != s && !s.isEmpty())
			{
				bw.write(s);
				bw.write(System.lineSeparator());
				bw.flush();
			}
			Collection<ConfigBlock> values = configFile.getConfigBlocks();
			
			Iterator<ConfigBlock> it = values.iterator();
			
			StringBuffer sb = null;
			
			while (it.hasNext())
			{
				ConfigBlock cb = it.next();
				
				s = cb.getComments();
				
				if( null != s && !s.isEmpty())
				{
					bw.write(s);
					bw.write(System.lineSeparator());
				}
				
				sb = new StringBuffer();
				
				sb.append(Constants.SYMBOL_LEFT_BRACKET);
				sb.append(cb.getBlockName());
				sb.append(Constants.SYMBOL_RIGHT_BRACKET);
				sb.append(System.lineSeparator());
				
				Iterator<KeyValuePair> iter = cb.getAllParameters().iterator();
				
				while (iter.hasNext())
				{
					KeyValuePair pair = iter.next();
					
					sb.append(pair.getKey());
					sb.append(Constants.SYMBOL_EQUAL);
					sb.append(pair.getValue());
					sb.append(System.lineSeparator());
				}
				
				bw.write(sb.toString());
				bw.write(System.lineSeparator());
				bw.flush();
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
