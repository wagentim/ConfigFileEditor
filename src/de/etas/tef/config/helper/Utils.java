package de.etas.tef.config.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.KeyValue;

public final class Utils
{
	public static final Path getCurrentPath()
	{
		return Paths.get(IConstants.EMPTY_STRING).toAbsolutePath();
	}

	public static KeyValue parserKeyValue(String s)
	{
		if (s == null || s.isEmpty())
		{
			return null;
		}

		int index = s.indexOf(IConstants.SYMBOL_EQUAL);

		if (index < 0)
		{
			return null;
		}

		return new KeyValue(s.substring(0, index), s.substring(index + 1, s.length()));
	}

	public static String removeSpace(String input)
	{
		if (input == null || input.isEmpty())
		{
			return IConstants.EMPTY_STRING;
		}

		if (input.contains(IConstants.SYMBOL_EQUAL))
		{
			StringTokenizer st = new StringTokenizer(input, IConstants.SYMBOL_EQUAL);
			StringBuilder s = new StringBuilder();
			int total = st.countTokens();
			int counter = 1;
			while (st.hasMoreTokens())
			{
				String key = st.nextToken().trim();
				s.append(key);
				if (counter < total)
				{
					s.append(IConstants.SYMBOL_EQUAL);
				}
				counter++;
			}

			return s.toString();
		}

		return input.trim();
	}

	public static boolean isContentSame(String input_1, String input_2)
	{
		if (input_1 == null && input_2 == null)
		{
			return true;
		}

		if (input_1.isEmpty() && input_2.isEmpty())
		{
			return true;
		}

		if (input_1 == null || input_2 == null)
		{
			return false;
		}

		if (input_1.isEmpty() || input_2.isEmpty())
		{
			return false;
		}

		return input_1.trim().equalsIgnoreCase(input_2.trim());
	}

	public static boolean validFile(Path filePath, boolean createNewFile)
	{
		LinkOption[] options = { LinkOption.NOFOLLOW_LINKS };

		if (Files.notExists(filePath, options) && createNewFile)
		{
			try
			{
				Files.createFile(filePath);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return true;
	}

	public static boolean isStringEmpty(String s)
	{
		if (s == null || s.isEmpty())
		{
			return true;
		}

		return false;
	}

	public static <T> boolean validList(List<T> input)
	{
		if (input == null || input.isEmpty())
		{
			return false;
		}

		return true;
	}
	
	public static <T, S> boolean validMap(Map<T, S> input)
	{
		if(input == null || input.isEmpty())
		{
			return false;
		}
		
		return true;
	}
	
	public static String getTimeStample()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		return now.format(dtf);
	}
	
	public static String getTime()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return "["+now.format(dtf)+"]";
	}
	
}
