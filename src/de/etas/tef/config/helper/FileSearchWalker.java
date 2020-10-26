package de.etas.tef.config.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
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
		
		if(!p.isAbsolute())
		{
			p = p.toAbsolutePath();
		}
		
		if(!Files.exists(p))
		{
			return;
		}
		
		try
		{
			
			String[] allFiles = new File(path).list();
			
			display.asyncExec(new Runnable()
			{
				
				@Override
				public void run()
				{
					MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_DISPLAY, true);
				}
			});
			
			int counter = 1;
			int total = allFiles.length;
			
			for(String s : allFiles)
			{
				Path pth = Paths.get(s);
				
				if(Files.isDirectory(Paths.get(s)))
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
								String fname = file.getFileName().toString();
								
								if(findFile(fname, pattern))
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
					if(findFile(s, pattern))
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
	
	private boolean findFile(String file, String[] pattern)
	{
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
