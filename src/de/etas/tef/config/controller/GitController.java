package de.etas.tef.config.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Controller to handle all Git related tasks
 * 
 * @author UIH9FE
 *
 */
public class GitController
{
	public void createRepository() throws IOException, IllegalStateException, GitAPIException
	{
		 // run the init-call
        File dir = File.createTempFile("gitinit", ".test");
        
        if(!dir.delete()) {
            throw new IOException("Could not delete file " + dir);
        }

        // The Git-object has a static method to initialize a new repository
        
        try (Git git = Git.init()
                .setDirectory(dir)
                .call()) {
            System.out.println("Created a new repository at " + git.getRepository().getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(dir);

        dir = File.createTempFile("repoinit", ".test");
        if(!dir.delete()) {
            throw new IOException("Could not delete file " + dir);
        }

        // you can also create a Repository-object directly from the
        try (Repository repository = FileRepositoryBuilder.create(new File(dir.getAbsolutePath(), ".git"))) {
            System.out.println("Created a new repository at " + repository.getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(dir);
	}
}
