package de.etas.tef.config.worker.operation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.controller.LoggerController;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ChangeAction;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.helper.ProgressBarHelper;
import de.etas.tef.config.helper.Utils;

public abstract class AbstractOperation extends ProgressBarHelper implements Runnable
{
	protected final String section;
	protected final String key;
	protected final String value;
	protected final List<ConfigFilePath> currentList;
	protected List<ChangeAction> changes = Collections.emptyList();
	protected final MainController controller;
	
	public AbstractOperation(String[] content, List<ConfigFilePath> currentList, Display display, MainController controller)
	{
		super(display);
		this.section = content[0];
		this.key = content[1];
		this.value = content[2];
		this.currentList = currentList;
		this.controller = controller;
		this.changes = new ArrayList<ChangeAction>();
	}

	@Override
	public void run()
	{
		if(currentList == null || currentList.isEmpty())
		{
			return;
		}
		changes.clear();
		
		Iterator<ConfigFilePath> it = currentList.iterator();
		
		while(it.hasNext())
		{
			try
			{	
				ConfigFilePath cfp = it.next();
			
				if(!controller.isAlreadyBackup(cfp))
				{
					backupFile(cfp.getPath());
				}
				
				doOperation(cfp);
				
				display.asyncExec(new Runnable()
				{
					
					@Override
					public void run()
					{
						controller.updateList();
					}
				});
				
				controller.getIOController().writeFile(cfp.getPath(), cfp.getConfigFile());
			
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		printLogger();
	}
	
	protected void printLogger()
	{
		if(changes == null || changes.isEmpty())
		{
			return;
		}
		
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				LoggerController logController = controller.getLoggerController();
				logController.log(changes);
				
			}
		});
	}

	protected abstract void doOperation(ConfigFilePath cfp) throws IOException;
	
	protected void backupFile(Path p)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(p.getFileName().toString());
		sb.append(".");
		sb.append(Utils.getTimeStample());

		Path backupFile = Paths.get(p.getParent().toString() + File.separator + sb.toString());

		try
		{
			Files.copy(p, backupFile);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

}
