package de.etas.tef.config.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;

public class IOController
{
	static final int TAG_FILE_COMMENT_START = 0x00;
	static final int TAG_FILE_COMMENT_FINISH = 0x01;

	static final int TAG_BLOCK_COMMENT_START = 0x02;
	static final int TAG_BLOCK_COMMENT_FINISH = 0x03;

	static final int TAG_BLOCK_NAME_START = 0x04;
	static final int TAG_BLOCK_NAME_FINISH = 0x05;

	static final int TAG_FILE_NEW = 0x06;

	private static final String CHARSET_UTF_8 = "UTF-8";

	public static void testCharset(String fileName)
	{
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		for (String k : charsets.keySet())
		{
			int line = 0;
			boolean success = true;
			try (BufferedReader b = Files.newBufferedReader(Paths.get(fileName), charsets.get(k)))
			{
				while (b.ready())
				{
					b.readLine();
					line++;
				}
			} catch (IOException e)
			{
				success = false;
				System.out.println(k + " failed on line " + line);
			}
			if (success)
				System.out.println("*************************  Successs " + k);
		}
	}

	public void writeStringToFile(Path filePath, String content) throws IOException
	{
		FileWriter fw = new FileWriter(filePath.toFile().getAbsoluteFile(), true);
	    BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.flush();
		bw.close();

	}

	public void writeFile(Path filePath, Object result) throws IOException
	{
		if (!Utils.validFile(filePath, true))
		{

			return;
		}

		if (!(result instanceof ConfigFile) || null == result)
		{
			return;
		}

		ConfigFile configFile = (ConfigFile) result;

		BufferedWriter bw = Files.newBufferedWriter(filePath, Charset.forName(CHARSET_UTF_8));

		String s = IConstants.EMPTY_STRING;

		try
		{
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
					bw.write(System.lineSeparator());
				}

				sb = new StringBuffer();

				sb.append(IConstants.SYMBOL_LEFT_BRACKET);
				sb.append(cb.getBlockName());
				sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
				sb.append(System.lineSeparator());

				Iterator<KeyValuePair> iter = cb.getAllParameters().iterator();

				while (iter.hasNext())
				{
					KeyValuePair pair = iter.next();

					if (pair.getType() == KeyValuePair.TYPE_PARA)
					{
						sb.append(pair.getKey().trim());
						sb.append(" ");
						sb.append(IConstants.SYMBOL_EQUAL);
						sb.append(" ");
						sb.append(pair.getValue().trim());
						sb.append(System.lineSeparator());
					} else
					{
						sb.append(pair.getKey());
						sb.append(System.lineSeparator());
					}

				}
				bw.write(sb.toString());
				bw.write(System.lineSeparator());
				bw.flush();
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
