package de.etas.tef.config.worker.search;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.helper.ProgressBarHelper;
import de.etas.tef.editor.message.MessageManager;

public class BasicSearchEngine extends ProgressBarHelper
{
	
	private List<ConfigFilePath> result = new ArrayList<ConfigFilePath>();
	private final FileNameFilter fnf;
	private final FileContentFilter fcf;
	private final MainController controller;
	
	public BasicSearchEngine(final Display display, final MainController controller) {
		super(display);
		this.controller = controller;
		fnf = new FileNameFilter(controller);
		fcf = new FileContentFilter(controller);
	}

	public void search(SearchInfo si) {
		
		Thread worker = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					clearResultList();
					doSearch(si);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		worker.start();
	}
	
	protected void clearResultList() {
		result.clear();
	}
	
	protected void addResult(ConfigFilePath p)
	{
		result.add(p);
	}
	
	protected void searchFile(Path directory, SearchInfo si) throws IOException
	{
		Files.walkFileTree(directory, new FileVisitor<Path>()
		{
			
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
			{
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
			{
				if(!Files.isDirectory(file))
				{
					checkFile(file, si);
				}
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
			{
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
			{
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	private void checkFile(Path file, SearchInfo si)
	{
		ConfigFilePath cfp = new ConfigFilePath(file);
		boolean fileNameOK = fnf.check(cfp, si);
		
		if(!fileNameOK)
		{
			return;
		}

		ConfigFile cf = controller.parserINIFile(file, IConstants.INI_PARSER_NORMAL);
		cfp.setConfigFile(cf);
		
		boolean fileContentOK = fcf.check(cfp, si);
		
		if(fileNameOK && fileContentOK)
		{
			addResult(cfp);
		}
	}

	protected void doSearch(SearchInfo si) throws IOException
	{
		Iterator<Path> it = Files.list(si.getStartPath()).iterator();
		
		List<Path> allFiles = new ArrayList<Path>();
		
		while(it.hasNext())
		{
			allFiles.add(it.next());
		}
		
		showProgressbar();
		
		int counter = 1;
		int total = allFiles.size();
		
		for(Path pth : allFiles)
		{
			if(Files.isDirectory(pth))
			{
				searchFile(pth, si);
			}
			else
			{
				checkFile(pth, si);
			}
			
			double d = (double)counter / total;
			counter++;
			setProgressbar(d);
		}
		
		hideProgressbar();
		sendResult();
	}
	
	protected void sendResult()
	{
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_UPDATE_FILE_LIST, result);
			}
		});
	}
}
