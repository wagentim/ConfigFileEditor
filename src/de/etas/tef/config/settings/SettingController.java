package de.etas.tef.config.settings;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.Utils;

/**
 * Handle all Setting file related actions
 * The setting file will use Json format
 * 
 * @author UIH9FE
 *
 */
public final class SettingController
{
	private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	private final Gson gson;
	private Setting setting = null;
	
	public class Setting
	{
		private String repositoryLocal = IConstants.EMPTY_STRING;
		private String repositoryRemote = IConstants.EMPTY_STRING;
		
		public String getRepositoryRemote()
		{
			return repositoryRemote;
		}

		public void setRepositoryRemote(String repositoryRemote)
		{
			if(repositoryRemote == null || repositoryRemote.isEmpty())
			{
				logger.error("Error -> Set repository remote wrong");
			}
			else
			{
				this.repositoryRemote = repositoryRemote;
			}
		}

		public void setRepositoryLocal(final String repositoryLocal)
		{
			if(repositoryLocal == null || repositoryLocal.isEmpty())
			{
				logger.error("Error -> Set repository local wrong");
			}
			else
			{
				this.repositoryLocal = repositoryLocal;
			}
		}
		
		public String getRepositoryLocal()
		{
			return this.repositoryLocal;
		}
	}
	
	public SettingController()
	{
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	public void loadSettingFromFile()
	{
        Path path = getSettingFilePath();
        
		try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
		{

			setting = gson.fromJson(reader, Setting.class);

		} catch (IOException e)
		{
			//e.printStackTrace();
			setting = null;
			logger.error("Error -> Loading Setting File: " + path.toString());
		}
	}
	
	public void loadSettingFromString(final String content)
	{
		if(content != null && !content.isEmpty())
		{
			setting = gson.fromJson(content, Setting.class);
		}
		else
		{
			setting = null;
		}
	}
	
	public String saveSettingToString()
	{
		if(setting != null)
		{
			return gson.toJson(setting);
		}
		
		return IConstants.EMPTY_STRING;
	}
	
	public Path getSettingFilePath()
	{
		String file = Utils.getCurrentPath().toString() + File.separator + IConstants.SETTING_FILE;
		logger.info("Loading Setting File: " + file);
		
        Path path = Paths.get(file);
        
        if( !Files.exists(path) )
        {
        	try
			{
				Files.createFile(path);
			} 
        	catch (IOException e)
			{
        		logger.error("Create Setting File failed: " + file);
				e.printStackTrace();
			}
        }
        
        return path;
	}
	
	public void saveSettingToFile()
	{
        Path path = getSettingFilePath();
        
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8))
		{
			gson.toJson(setting, writer);
			logger.info("Write Settings to file: {}", path.toString());

		} catch (IOException e)
		{
			setting = null;
			logger.error("Error -> Loading Setting File: " + path.toString());
		}
	}
	
	public Setting getSetting()
	{
		if(this.setting == null)
		{
			this.setting = new Setting();
		}
		
		return this.setting;
	}
}
