package de.etas.tef.config.helper;

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
			Files.walkFileTree(p, new FileVisitor<Path>()
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
						
						for(String pa : pattern)
						{
							if(fname.endsWith(pa))
							{
								files.add(file);
							}
//							if(fname.matches(pa))
//							{
//								files.add(file);
//							}
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
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		display.asyncExec(new Runnable()
		{
			
			@Override
			public void run()
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_FILE_SEARCH_FINISHED, files);
				
			}
		});
	}

}
