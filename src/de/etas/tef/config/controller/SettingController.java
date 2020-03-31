package de.etas.tef.config.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.etas.tef.config.core.Utils;
import de.etas.tef.config.listener.IMessageListener;

/**
 * Handle all Setting file related actions
 * The setting file will use Json format
 * 
 * @author UIH9FE
 *
 */
public final class SettingController implements IMessageListener
{
	private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
	private final Gson gson;
	private Setting setting = null;
	
	class Setting
	{
		String workingSpace = IConstants.EMPTY_STRING;
		
		public void setWorkingSpace(final String workingSpace)
		{
			if(workingSpace == null || workingSpace.isEmpty())
			{
				logger.error("Set workingspace wrong");
			}
			else
			{
				this.workingSpace = workingSpace;
			}
		}
		
		public String getWorkingSpace()
		{
			return this.workingSpace;
		}
	}
	
	public SettingController()
	{
		this.gson = new Gson();
	}
	
	public void loadSetting()
	{
		String file = Utils.getCurrentPath().toString() + File.separator + IConstants.SETTING_FILE;
		logger.info("Loading Setting File: " + file);
		
		setting = gson.fromJson(file, Setting.class);
	}

	@Override
	public void receivedAction(int type, int compositeID, Object content)
	{
		// TODO Auto-generated method stub
		
	}
}
