package de.etas.tef.config.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.ColorFactory;
import de.etas.tef.config.helper.ImageFactory;
import de.etas.tef.config.settings.SettingController;
import de.etas.tef.config.settings.SettingController.Setting;
import de.etas.tef.config.ui.MainScreen;

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
		
//		while( !quitProgram && ((repositoryLocal == null || repositoryLocal.isEmpty()) || (repositoryRemote == null || repositoryRemote.isEmpty()) ))
//		{
//			Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
//			SelectWorkSpaceDialog d = new SelectWorkSpaceDialog(shell, this);
//			d.setValues(repositoryRemote, repositoryLocal);
//			String[] result = d.open();
//			repositoryRemote = result[0];
//			repositoryLocal = result[1];
//			logger.info("Selected Working Space: {}", repositoryLocal);
//			logger.info("Selected Repository: {}", repositoryRemote);
//		}
		
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
			new MainScreen(display, this);
//			gitController = new GitController(repositoryRemote, repositoryLocal);
//			
//			if(gitController.isRepositoryAvailable())
//			{
//				new MainScreen(display, this);
//			}
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

	public List<Path> searchFileContent(List<Path> currentList, String content) 
	{
		List<Path> newList = new ArrayList<Path>();
		
		for(Path p : currentList)
		{
			try {
				if(containContent(p, content))
				{
					newList.add(p);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return newList;
	}
	
	private boolean containContent(Path path, String content) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
        String line = null;
        while ((line = br.readLine()) != null) 
        {
        	if(line.startsWith("--") || line.startsWith(";"))
        	{
        		continue;
        	}
        	
        	if(line.contains("="))
        	{
        		StringTokenizer st = new StringTokenizer(line, "=");
        		while(st.hasMoreTokens())
        		{
        			String key = st.nextToken();
        			
        			if(key.equalsIgnoreCase(content))
        			{
        				br.close();
        				return true;
        			}
        		}
        	}
        	else if(line.startsWith("["))
        	{
        		int end = line.indexOf("]");
        		if( end > 0)
        		{
        			line = line.substring(1, end);
//        			logger.info(line);
        		}
        		
        		if(line.equalsIgnoreCase(content))
        		{
        			br.close();
        			return true;
        		}
        	}
        }		
        
        br.close();
		return false;
	}

	public void replaceText(List<Path> currentList, String[] content) throws IOException 
	{
		if(currentList == null || currentList.isEmpty())
		{
			return;
		}
		
		String search = content[0];
		String replace = content[1];
		
		for(Path p : currentList)
		{
			backupFile(p);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(p.toFile())));
	        String line = null;
	        StringBuilder sb = new StringBuilder();
	        while ((line = br.readLine()) != null) 
	        {
	        	if(line.contains("="))
	        	{
	        		StringTokenizer st = new StringTokenizer(line, "=");
	        		StringBuilder s = new StringBuilder();
	        		int total = st.countTokens();
	        		
	        		if(total == 2)
	        		{
	        			int counter = 1;
	        			while(st.hasMoreTokens())
	        			{
	        				String key = st.nextToken();
	        				
	        				if(key.equalsIgnoreCase(search))
	        				{
	        					key = replace;
	        				}
	        				s.append(key);
	        				
	        				if(counter < total)
	        				{
	        					s.append("=");
	        				}
	        				
	        				counter++;
	        			}
	        			line = s.toString();
	        		}
	        	}
	        	
	        	sb.append(line);
	        	sb.append("\r\n");
	        }
	        br.close();
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(p.toFile())));
	        
	        bw.write(sb.toString());
	        bw.flush();
	        bw.close();
		}
	}
	
	private void backupFile(Path p)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");  
		LocalDateTime now = LocalDateTime.now(); 
		StringBuilder sb = new StringBuilder();
		sb.append(p.getFileName().toString());
		sb.append(".");
		sb.append(now.format(dtf));
		
		Path backupFile = Paths.get(p.getParent().toString() + File.separator + sb.toString());
		
	    try {
	        Files.copy(p, backupFile);
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
	}
}
