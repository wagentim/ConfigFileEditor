package de.etas.tef.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

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
	private final Setting setting;
	
	class Setting
	{
		String workingSpace = IConstants.EMPTY_STRING;
		final Gson gson;
		
		public Setting(final Gson gson)
		{
			this.gson = gson;
		}
		
		public boolean setWorkingSpace(final String workingSpace)
		{
			if(workingSpace == null || workingSpace.isEmpty())
			{
				logger.error("Set workingspace wrong");
				return false;
			}
			
			this.workingSpace = workingSpace;
			return true;
		}
		
		public boolean loadSettingsFromFile(final String input)
		{
			return true;
		}
	}
	
	public SettingController()
	{
		this.setting = new Setting(new Gson());
	}
}
