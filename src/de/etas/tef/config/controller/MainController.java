package de.etas.tef.config.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.core.ColorFactory;
import de.etas.tef.config.core.ImageFactory;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.settings.SettingController;
import de.etas.tef.config.settings.SettingController.Setting;
import de.etas.tef.config.ui.composites.MainScreen;
import de.etas.tef.config.ui.composites.SelectWorkSpaceDialog;

public class MainController
{

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final ImageFactory imageFactory;
	private final ColorFactory colorFactory;
	private final CheckerFactory checkerFactory;
	private final INIFileController iniFileController;
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
		this.checkerFactory = new CheckerFactory();
		this.settingController = new SettingController();
		this.iniFileController = new INIFileController();
		
		initController();
		initEnvironment();
	}
	
	public ConfigFile parserINIFile(Path filePath)
	{
		ConfigFile cf = new ConfigFile();
		
		try
		{
			iniFileController.readFile(filePath.toString(), cf);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return cf;
	}
	
	private void initEnvironment()
	{
		String repositoryRemote = getSetting().getRepositoryRemote();
		String repositoryLocal = getSetting().getRepositoryLocal();
		
		boolean updateRepository = false;
		boolean updateWorkingspace = false;
		
		if(repositoryRemote == null || repositoryRemote.isEmpty())
		{
			updateRepository = true;
		}
		
		if(repositoryLocal == null || repositoryLocal.isEmpty())
		{
			updateWorkingspace = true;
		}
		
		while( !quitProgram && ((repositoryLocal == null || repositoryLocal.isEmpty()) || (repositoryRemote == null || repositoryRemote.isEmpty()) ))
		{
			Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
			SelectWorkSpaceDialog d = new SelectWorkSpaceDialog(shell, this);
			d.setValues(repositoryRemote, repositoryLocal);
			String[] result = d.open();
			repositoryRemote = result[0];
			repositoryLocal = result[1];
			logger.info("Selected Working Space: {}", repositoryLocal);
			logger.info("Selected Repository: {}", repositoryRemote);
		}
		
		if( updateWorkingspace && repositoryLocal != null && !repositoryLocal.isEmpty() )
		{
			getSetting().setRepositoryLocal(repositoryLocal);
			settingController.saveSettingToFile();
		}
		
		if( updateRepository && repositoryRemote != null && !repositoryRemote.isEmpty() )
		{
			getSetting().setRepositoryRemote(repositoryRemote);
			settingController.saveSettingToFile();
		}
		
		if(!quitProgram)
		{
			gitController = new GitController(repositoryRemote, repositoryLocal);
			
			if(gitController.isRepositoryAvailable())
			{
				new MainScreen(display, this);
			}
		}
		else
		{
			System.exit(0);
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
	
	public CheckerFactory getCheckerFactory()
	{
		return this.checkerFactory;
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
