package de.etas.tef.config.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.controller.SettingController.Setting;
import de.etas.tef.config.core.ColorFactory;
import de.etas.tef.config.core.ImageFactory;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.ui.composites.MainScreen;
import de.etas.tef.config.ui.composites.SelectWorkSpaceDialog;

public class MainController
{

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final ImageFactory imageFactory;
	private final ColorFactory colorFactory;
	private final Display display;
	
	private boolean quitProgram = false;
	
	private GitController gitController = null;
	private final SettingController settingController;
	
	private ConfigBlock copyBlock = null;
	private List<KeyValuePair> copyParameters = null;
	
	private boolean connected = false;
	
	public MainController(final Display display)
	{
		this.display = display;
		this.imageFactory = new ImageFactory(display);
		this.colorFactory = new ColorFactory(display);
		this.settingController = new SettingController();
		
		initController();
		initEnvironment();
	}
	
	private void initEnvironment()
	{
		String workingspace = getSetting().getWorkingSpace();
		boolean updateWorkingspace = false;
		
		if(workingspace == null || workingspace.isEmpty())
		{
			updateWorkingspace = true;
		}
		
		while( !quitProgram && (workingspace == null || workingspace.isEmpty() ))
		{
			Shell shell = new Shell(display);
			SelectWorkSpaceDialog d = new SelectWorkSpaceDialog(shell, this);
			workingspace = d.open();
			logger.info("Selected Working Space: {}", workingspace);
		}
		
		if( updateWorkingspace && workingspace != null && !workingspace.isEmpty() )
		{
			getSetting().setWorkingSpace(workingspace);
			settingController.saveSettingToFile();
		}
		
		if(!quitProgram)
		{
			gitController = new GitController(workingspace);
			
			boolean result = gitController.checkWorkingSpace();
			
			if(result)
			{
				logger.info("Using Git Repository Location: {}", workingspace);
				new MainScreen(display, this);
			}
			else
			{
				try
				{
					gitController.createRepository();
				} 
				catch (IllegalStateException | IOException | GitAPIException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isQuiteProgram()
	{
		return quitProgram;
	}

	public void setQuiteProgram(boolean quiteProgram)
	{
		this.quitProgram = quiteProgram;
	}

	private void initController()
	{
		settingController.loadSettingFromFile();
	}
	
	public Setting getSetting()
	{
		return this.settingController.getSetting();
	}
	
	public ImageFactory getImageFactory()
	{
		return this.imageFactory;
	}
	
	public ColorFactory getColorFactory()
	{
		return this.colorFactory;
	}
	
	public GitController getGitController()
	{
		return gitController;
	}

	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	public boolean isConnected()
	{
		return connected;
	}

	public void setCopyBlock(ConfigBlock selectedConfigBlock)
	{
		try
		{
			this.copyBlock = selectedConfigBlock.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}
	
	public ConfigBlock getCopyBlock()
	{
		ConfigBlock result = copyBlock;
		copyBlock = null;
		return result;
	}

	public void copyParameters(List<KeyValuePair> result)
	{
		this.copyParameters = result;
	}
	
	public List<KeyValuePair> getCopyParameters()
	{
		List<KeyValuePair> result = this.copyParameters;
		this.copyParameters = Collections.emptyList();
		return result;
	}
}
