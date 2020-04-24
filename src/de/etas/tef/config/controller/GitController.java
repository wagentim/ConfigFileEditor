package de.etas.tef.config.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller to handle all Git related tasks
 * 
 * @author UIH9FE
 *
 */
public final class GitController
{
	private static final Logger logger = LoggerFactory.getLogger(GitController.class);
	
	
	private final String repositoryLocal;
	private final String repositoryRemote;
	private Repository repository = null;
	private Git git = null;
	
	public GitController(final String repositoryRemote, final String repositoryLocal)
	{
		this.repositoryRemote = repositoryRemote;
		this.repositoryLocal = repositoryLocal;
	}
	
	// current it only check the local repository
	public boolean checkRepository()
	{
		if(repositoryLocal == null || repositoryLocal.isEmpty() || repositoryRemote == null || repositoryRemote.isEmpty())
		{
			return false;
		}
		
		Path path = null;
		
//		if(!Files.exists(path = Paths.get(repositoryRemote)))
//		{
//			try
//			{
//				Files.createDirectories(path);
//			} 
//			catch (IOException e)
//			{
//				logger.error("Create Repositroy Dir failed: {}", path.toString());
//				return false;
//			}
//		}
		
		if(!Files.exists(path = Paths.get(repositoryLocal)))
		{
			try
			{
				Files.createDirectories(path);
			} 
			catch (IOException e)
			{
				logger.error("Create Working Dir failed: {}", path.toString());
				return false;
			}
		}
		
		String gitDir = repositoryLocal + File.separator + ".git";
		
		if(!Files.exists(Paths.get(gitDir)))
		{
			initRepository();
		}
		
		initGit();
		
		return true;
	}
	
	public int addUntractedFile(String path, String name)
	{
		if(path == null || name == null || path.isEmpty() || name.isEmpty())
		{
			return IConstants.OPERATION_INPUT_ERROR;
		}
		
		Path p = Paths.get(path + File.separator + name);
		
		try
		{
			Files.createFile(p);
		} 
		catch (IOException e)
		{
			logger.error("Create File Failed: {}", path.toString());
			return IConstants.OPERATION_FAILED;
		}
		
		try
		{
			git.add().setUpdate(true).addFilepattern(p.getFileName().toString()).call();
		} 
		catch (GitAPIException e)
		{
			logger.error("Add Untract File Failed: {}", path.toString());
			return IConstants.OPERATION_FAILED;
		}
		
		return IConstants.OPERATION_SUCCESS;
	}
	
	public int isFileInTrack(String filePath)
	{
		if(filePath == null || filePath.isEmpty())
		{
			return IConstants.OPERATION_INPUT_ERROR;
		}
		
		Path p = Paths.get(filePath);
		if(!Files.exists(p))
		{
			logger.error("The File physically not exist: {}", p.toString());
			return IConstants.OPERATION_INPUT_ERROR;
		}
		
		try
		{
			Status status = git.status().call();
			Set<String> untracted = status.getUntracked();
			
			for(String s : untracted)
			{
				logger.info(s);
			}
			
		} 
		catch (NoWorkTreeException | GitAPIException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return IConstants.OPERATION_SUCCESS;
	}
	
	private void initGit()
	{
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		
		try
		{
			repository = builder.setGitDir(new File(repositoryLocal)).readEnvironment().findGitDir().build();
			git = new Git(repository);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private void initRepository()
	{
		try
		{
			Git git = Git.init().setDirectory(new File(repositoryLocal)).call();
			logger.info("Create repository: {}", git.getRepository().getDirectory());
		} 
		catch (IllegalStateException | GitAPIException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createRepository() throws IOException, IllegalStateException, GitAPIException
	{
		try (Git git = Git.init().setDirectory(new File(repositoryLocal)).call())
		{
			logger.info("Created a new repository at " + git.getRepository().getDirectory());
		}
	}
}
