package de.etas.tef.config.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.IConstants;

public class LoadIniFile implements Runnable
{

	private BufferedReader br = null;
	private final Path filePath;
	private ConfigFile configFile;

	public LoadIniFile(final Path filePath)
	{
		this.filePath = filePath;
	}

	public ConfigFile read()
	{
		configFile = new ConfigFile();

		try
		{
			br = new BufferedReader(new FileReader(filePath.toString()));
			// define variables
			String s;
			StringBuffer sb = new StringBuffer();
			List<ConfigBlock> blockList = null;
			ConfigBlock block = null;
			KeyValuePair pair = null;
			List<KeyValuePair> paraList = null;

			boolean fileStart = true;
			boolean blockStart = true;
			boolean paraStart = true;

			int index = 0;

			while (null != (s = br.readLine()))
			{
				s = s.trim();

				if (!s.isEmpty() && 0 == index && (!s.startsWith("--") && !s.startsWith(";")))
				{
					fileStart = false;
					blockList = new ArrayList<ConfigBlock>();
					configFile.setConfigBlocks(blockList);
					block = new ConfigBlock();
					index++;
				}

				if (fileStart)
				{
					if (s.isEmpty())
					{
						fileStart = false;
						configFile.setComments(sb.toString());
						sb.delete(0, sb.length());
						blockList = new ArrayList<ConfigBlock>();
						configFile.setConfigBlocks(blockList);
						block = new ConfigBlock();
					} else if (s.startsWith("--") || s.startsWith(";"))
					{
						sb.append(s + IConstants.SYMBOL_NEW_LINE);
					}
				} else
				{
					if (blockStart)
					{
						if (s.startsWith("["))
						{
							blockStart = false;
							paraStart = true;
							block.setComments(sb.toString());
							block.setBlockName(s.substring(1, s.indexOf("]")));
							paraList = new ArrayList<KeyValuePair>();
							block.setParameters(paraList);
							blockList.add(block);
							sb.delete(0, sb.length());
						} else if (s.startsWith("--") || s.startsWith(";"))
						{
							sb.append(s + IConstants.SYMBOL_NEW_LINE);
						}
					} else
					{
						if (paraStart)
						{
							if (s.isEmpty())
							{
								paraStart = false;
								blockStart = true;
								block = new ConfigBlock();
							} else
							{
								int split = s.indexOf("=");

								if (split < 1)
								{
								} else
								{
									pair = new KeyValuePair();
									pair.setKey(s.substring(0, split));
									pair.setValue(s.substring(split + 1, s.length()));
									paraList.add(pair);
								}
							}
						}
					}
				}
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configFile;
	}

	public void save(String targetFilePath, ConfigFile configFile)
	{

		BufferedWriter bw;

		File f = new File(targetFilePath);
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (null == configFile)
		{
			return;
		}

		String s = IConstants.EMPTY_STRING;

		try
		{
			bw = new BufferedWriter(new FileWriter(f));

			s = configFile.getComments();

			if (null != s && !s.isEmpty())
			{
				bw.write(s);
				bw.write(IConstants.SYMBOL_NEW_LINE);
				bw.flush();
			}
			Collection<ConfigBlock> values = configFile.getConfigBlocks();

			Iterator<ConfigBlock> it = values.iterator();

			StringBuffer sb = null;

			while (it.hasNext())
			{
				ConfigBlock cb = it.next();

				s = cb.getComments();

				if (null != s && !s.isEmpty())
				{
					bw.write(s);
					bw.write(IConstants.SYMBOL_NEW_LINE);
				}

				sb = new StringBuffer();

				sb.append("[");
				sb.append(cb.getBlockName());
				sb.append("]");
				sb.append("\n");

				Iterator<KeyValuePair> iter = cb.getAllParameters().iterator();

				while (iter.hasNext())
				{
					KeyValuePair pair = iter.next();

					sb.append(pair.getKey());
					sb.append("=");
					sb.append(pair.getValue());
					sb.append("\n");
				}

				bw.write(sb.toString());
				bw.write(IConstants.SYMBOL_NEW_LINE);
				bw.flush();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		if (filePath == null || !Files.exists(filePath))
		{
			return;
		}

	}
}
