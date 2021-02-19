package de.etas.tef.config.controller;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.settings.SettingController;
import de.etas.tef.config.settings.SettingController.Setting;
import de.etas.tef.config.ui.MainScreen;
import de.etas.tef.config.worker.parser.AbstractINIFileParser;
import de.etas.tef.config.worker.parser.IniFileParser;
import de.etas.tef.config.worker.parser.TemplateIniFileParser;
import de.etas.tef.config.worker.search.BasicSearchEngine;
import de.etas.tef.config.worker.search.SearchInfo;
import de.etas.tef.config.worker.sorter.ConfigFileSorter;
import de.etas.tef.editor.message.MessageManager;

public class MainController
{

//	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final ImageController imageFactory;
	private final ColorController colorFactory;
	private final SettingController settingController;
	private final Display display;
	private final IniFileParser normalINIparser;
	private final TemplateIniFileParser tempINIparser;
	private final CheckerController valiController;
	private final TemplateController tempController;
	private final ConfigFileSorter sorter;
	private final IOController ioController;
	private final LoggerController loggerController;
	
	private boolean connected = false;
	
	private List<ConfigFilePath> alreadyBackedFile = new ArrayList<ConfigFilePath>();
	
	private BasicSearchEngine searchEngine = null;
	
	// current search result files
	private List<ConfigFilePath> currentFileList = Collections.emptyList();
	
	public MainController(final Display display)
	{
		this.display = display;
		this.imageFactory = new ImageController(display);
		this.colorFactory = new ColorController(display);
		this.settingController = new SettingController();
		this.searchEngine = new BasicSearchEngine(display, this);
		this.normalINIparser = new IniFileParser();
		this.tempINIparser = new TemplateIniFileParser();
		this.valiController = new CheckerController(display, this);
		this.tempController = new TemplateController(this);
		this.sorter = new ConfigFileSorter(this);
		this.ioController = new IOController();
		this.loggerController = new LoggerController(this);
		new MainScreen(display, this);
	}
	
	public void setTemplate(int type)
	{
		tempController.setTemplate(type);
	}
	
	public TemplateController getTemplateController()
	{
		return tempController;
	}
	
	public List<String> getTemplateNames()
	{
		return tempController.getTemplateNames();
	}
	
	public ConfigFile parserINIFile(Path filePath, int type)
	{
		return getINIFileParser(type).read(filePath);
	}
	
	public Setting getSetting()
	{
		return this.settingController.getSetting();
	}
	
	public ImageController getImageFactory()
	{
		return this.imageFactory;
	}
	
	public ColorController getColorFactory()
	{
		return this.colorFactory;
	}
	
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	public boolean isConnected()
	{
		return connected;
	}

	public void search(SearchInfo si) 
	{
		searchEngine.search(si);
	}
	
	public AbstractINIFileParser getINIFileParser(int type)
	{
		switch(type)
		{
		case IConstants.INI_PARSER_NORMAL:
			return normalINIparser;
		case IConstants.INI_PARSER_TEMPLATE:
			return tempINIparser;
		}
		
		return normalINIparser;
	}

	public void setCurrentFileList(List<ConfigFilePath> list) 
	{
		this.currentFileList = list;
	}

	public void validating(int value) 
	{

		if(currentFileList == null || currentFileList.size() < 0)
		{
			return;
		}
		
		valiController.check(value, currentFileList);
	}
	
	public ConfigFilePath getCurrentTemplate()
	{
		return tempController.getTempate();
	}
	
	/**
	 * Sort the {@code ConfigBlock} with predefine the sequence.
	 * Same sequence as template
	 * 
	 * @param blocks
	 */
	
	public ConfigFileSorter getSorter()
	{
		return sorter;
	}
	
	public List<ConfigFilePath> getCurrentFileList()
	{
		return currentFileList;
	}

	public void exit(Shell shell)
	{
		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		mb.setText("Exit Confirmation");
		mb.setMessage("Do you really want to Exit?");

		boolean done = mb.open() == SWT.YES;

		if (done)
		{
			System.exit(0);
		}
		
	}

	public void deleteINIFiles(List<ConfigFilePath> deleteItems)
	{
		if(currentFileList == null || currentFileList.isEmpty() || deleteItems == null || deleteItems.isEmpty())
		{
			return;
		}
		
		Iterator<ConfigFilePath> it = deleteItems.iterator();
		
		while(it.hasNext())
		{
			ConfigFilePath cfp = it.next();
			currentFileList.remove(cfp);
		}
	}

	public IOController getIOController()
	{
		return ioController;
		
	}
	
	public boolean isAlreadyBackup(ConfigFilePath cfp)
	{
		if(alreadyBackedFile.contains(cfp))
		{
			return true;
		}
		
		alreadyBackedFile.add(cfp);
		return false;
	}
	
	public LoggerController getLoggerController()
	{
		return loggerController;
	}

	public void updateList()
	{
		MessageManager.INSTANCE.sendMessage(IConstants.ACTION_UPDATE_FILE_LIST, currentFileList);
	}
}
