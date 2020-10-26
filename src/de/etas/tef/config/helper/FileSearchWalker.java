package de.etas.tef.config.helper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.editor.message.MessageManager;

public class FileSearchWalker implements Runnable
{
	private final String path;
	private final List<Path> files = new ArrayList<Path>();
	private final String[] pattern;
	private final Display display;
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public FileSearchWalker(final String path, String[] pattern, final Display display)
	{
		this.path = path;
		this.pattern = pattern;
		this.display = display;
	}

	@Override
	public void run()
	{
		if(path == null || path.isEmpty() || pattern == null || pattern.length < 1)
		{
			return;
		}
		
		Path p = Paths.get(path);
		
		if(!Files.exists(p))
		{
			return;
		}
		
		try
		{
			
			Iterator<Path> it = Files.list(p).iterator();
			
			List<Path> allFiles = new ArrayList<Path>();
			
			while(it.hasNext())
			{
				allFiles.add(it.next());
			}
			
			display.asyncExec(new Runnable()
			{
				
				@Override
				public void run()
				{
					MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_DISPLAY, true);
				}
			});
			
			int counter = 1;
			int total = allFiles.size();
			
			for(Path pth : allFiles)
			{
				if(Files.isDirectory(pth))
				{
					Files.walkFileTree(pth, new FileVisitor<Path>()
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
								
								if(findFile(file, pattern))
								{
									files.add(file);
								}
							}
							
							return FileVisitResult.CONTINUE;
						}
						
						@Override
						public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
						{
							// TODO Auto-generated method stub
							return FileVisitResult.CONTINUE;
						}
						
						@Override
						public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
						{
							// TODO Auto-generated method stub
							return FileVisitResult.CONTINUE;
						}
					});
				}
				else
				{
					if(findFile(pth, pattern))
					{
						files.add(pth);
					}	
				}
				
				double d = (double)counter / total;
				
//				logger.info("counter: {}, total: {}, value: {}", counter, total, d);
				counter++;
				
				
				display.asyncExec(new Runnable()
				{
					
					@Override
					public void run()
					{
						MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_UPDATE, d);
					}
				});
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
//		logger.info("Find Files: {}", files.size());
		
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_FILE_SEARCH_FINISHED, files);
				
			}
		});
	}
	
	private boolean findFile(Path path, String[] pattern)
	{
		String file = path.getFileName().toString();
		
		for(String pa : pattern)
		{
			if(file.endsWith(pa))
			{
				return true;
			}
		}
		
		return false;
	}

}
