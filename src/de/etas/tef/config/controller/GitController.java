package de.etas.tef.config.controller;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
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
	private final String workingspace;
	
	public GitController(final String workingspace)
	{
		this.workingspace = workingspace;
	}
	
	// current it only check the local repository
	public boolean checkWorkingSpace()
	{
		if(workingspace == null || workingspace.isEmpty())
		{
			return false;
		}
		
		return isLocalRepositoryAvailable();
	}
	
	private boolean isLocalRepositoryAvailable()
	{
		RepositoryBuilder rb = new RepositoryBuilder();
		
		Repository r = null;
		
		try
		{
			r = rb.setGitDir(new File(workingspace)).build();
			
		} catch (IOException e)
		{
			r = null;
		}
		
		if( r == null )
		{
			return false;
		}
		
		return r.isBare();
	}
	
	public void createRepository() throws IOException, IllegalStateException, GitAPIException
	{
		try (Git git = Git.init().setDirectory(new File(workingspace)).call())
		{
			logger.info("Created a new repository at " + git.getRepository().getDirectory());
		}
	}
}
