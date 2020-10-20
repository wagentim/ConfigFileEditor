package de.etas.tef.config.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.etas.tef.config.helper.IConstants;

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
	
	/**
	 * Check if the repository read or not
	 * @return
	 */
	public boolean isRepositoryAvailable()
	{
		if(repositoryLocal == null || repositoryLocal.isEmpty() || repositoryRemote == null || repositoryRemote.isEmpty())
		{
			logger.error("No correct repository link!");
			return false;
		}
		
		Path path = null;
		
		if(!Files.exists(path = Paths.get(repositoryLocal)))
		{
			try
			{
				Files.createDirectories(path);
				createRepository();
			} 
			catch (IOException e)
			{
				logger.error("Create Working Dir failed: {}", path.toString());
				return false;
			}
		}
		else if(!Files.exists(path = Paths.get(getLocalRepositoryGitDir())))
		{
			createRepository();
		}
		else
		{
			initGit();
		}
		
		try
		{
			return assertNotNull( git.getRepository().exactRef( Constants.HEAD ) );
		} 
		catch (NoWorkTreeException | IOException e)
		{
			return false;
		}
		
	}
	
	public void addFileToRepository(String fileName)
	{
		File newFile = new File(repositoryLocal + File.separator + fileName);
        try
		{
			newFile.createNewFile();
			git.add().addFilepattern("myfile").call();
			logger.info("Add new File: {} into the repository", newFile.toString());
		} 
        catch (IOException | GitAPIException e)
		{
			e.printStackTrace();
		}
	}
	
	public void listFileHistory(Path filePath)
	{

	}
	
	public List<RevCommit> getAllCommits(String fileName)
	{
		Repository repository = git.getRepository();
		
		List<RevCommit> commits = new ArrayList<RevCommit>();
		
		ObjectId lastCommitId =	null;
		
		try
		{
			lastCommitId = repository.resolve(Constants.HEAD);
		} catch (RevisionSyntaxException | IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // a RevWalk allows to walk over commits based on some filtering that is defined
        try (RevWalk revWalk = new RevWalk(repository)) 
        {
            RevCommit commit =	null;
			try
			{
				commit = revWalk.parseCommit(lastCommitId);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            // and using commit's tree find the path
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);

            // now try to find a specific file
            try (TreeWalk treeWalk = new TreeWalk(repository)) {
                try
				{
					treeWalk.addTree(tree);
					treeWalk.setRecursive(true);
					treeWalk.setFilter(PathFilter.create(fileName));
					if (!treeWalk.next()) {
						throw new IllegalStateException("Did not find expected file: " + fileName);
					}
					
//					ObjectId objectId = treeWalk.getObjectId(0);
//					ObjectLoader loader = repository.open(objectId);
//					
//					loader.copyTo(System.out);
					
					commits.add(commit);

				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

            revWalk.dispose();
        }
        
        return commits;
	}
	
	public int addUntractedFile(String path, String name)
	{
		if(path == null || name == null || path.isEmpty() || name.isEmpty())
		{
			return IConstants.OPERATION_INPUT_ERROR;
		}
		
		Path p = Paths.get(path + File.separator + name + File.separator + IConstants.TXT_DEFAULT_FILE);
		
		try
		{
			Files.createFile(p);
		} 
		catch (IOException e)
		{
			logger.error("Create File Failed: {}", p.toString());
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
	
	public void listAddedFiles()
	{
		try
		{
			Status status = git.status().call();
			Set<String> added = status.getAdded();
			
			for(String s : added)
			{
				logger.info("Added Files: {}", s);
			}
			
		} 
		catch (NoWorkTreeException | GitAPIException e)
		{
			e.printStackTrace();
		}
	}
	
	public int listUntrackedFiles(String filePath)
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
				logger.info("Untracted Files: {}", s);
			}
			
		} 
		catch (NoWorkTreeException | GitAPIException e)
		{
			e.printStackTrace();
		}
		
		return IConstants.OPERATION_SUCCESS;
	}
	
	public void initGit()
	{
		try
		{
			git = Git.open(new File(repositoryLocal, ".git"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			logger.error("Initial Git Object error: \n{}", e.getMessage());
		}
		
		repository = git.getRepository();
	}
	
	public RevCommit commitToRepository(String commitMessage)
	{
		try
		{
			RevCommit commit = git.commit().setMessage(commitMessage).call();
			logger.info("Successfully commit to the repository");
			return commit;
		} 
		catch (GitAPIException e)
		{
			e.printStackTrace();
			return null;
		}
		
    }
	
	public void listAllCommits()
	{
		try {
			
            Iterable<RevCommit> logs = git.log().call();
            int count = 0;
            for (RevCommit rev : logs) {
                System.out.println("Commit: " + rev + ", name: " + rev.getName() + ", id: " + rev.getId().getName());
                count++;
            }
            
            System.out.println("Had " + count + " commits overall on current branch");

            logs = git.log().addPath("test.ini").call();
            count = 0;
            for (RevCommit rev : logs) {
                System.out.println("Commit: " + rev + ", name: " + rev.getName() + ", id: " + rev.getId().getName());
                count++;
            }
            System.out.println("Had " + count + " commits on test.ini");
        }
		catch(GitAPIException | RevisionSyntaxException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean assertNotNull(Ref ref)
	{
		return ref != null;
	}
	
	public String getLocalRepositoryGitDir()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(repositoryLocal);
		sb.append(File.separator);
		sb.append(".git");
		
		return sb.toString();
	}

	public void createRepository()
	{
		try
		{
			repository = FileRepositoryBuilder.create(new File(getLocalRepositoryGitDir()));
			repository.create();
			git = Git.open(new File(getLocalRepositoryGitDir()));
			logger.info("Create the new Repository in: {}", repository.getDirectory().toString());
			
			addFileToRepository(IConstants.TXT_REPOSITORY_README_FILE);
			commitToRepository("Create Repository and add readme.md");
			
		} 
		catch (IOException e)
		{
			logger.error("Create Local Repository Failed: {}", getLocalRepositoryGitDir());
		}
	}

//	public static void main(String[] args) throws IOException
//	{
//		Path p = Paths.get("D:\\git\\test.ini");
//		GitController c = new GitController("default", "D:\\git");
//		
////		c.createRepository();
//		c.initGit();
////		c.addFileToRepository("test");
//		RevCommit ct = c.commitToRepository("hello");
//		System.out.println(ct.getCommitTime());
//	}
}
