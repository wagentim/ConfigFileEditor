package de.etas.tef.config.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.worker.checker.DuplicatedKeyValidator;
import de.etas.tef.config.worker.checker.DuplicatedSectionValidator;
import de.etas.tef.config.worker.checker.IChecker;
import de.etas.tef.config.worker.checker.MissingItemValidator;
import de.etas.tef.editor.message.MessageManager;

public final class CheckerController 
{
	private final DuplicatedSectionValidator dSectionChecker;
	private final DuplicatedKeyValidator dKeyChecker;
	private final MissingItemValidator missChecker;
	private final Display display;
	
	public CheckerController(final Display display, final MainController controller) 
	{
		dSectionChecker = new DuplicatedSectionValidator(controller);
		dKeyChecker = new DuplicatedKeyValidator(controller);
		missChecker = new MissingItemValidator(controller);
		this.display = display;
	}

	public void check(int value, List<ConfigFilePath> currentFileList) 
	{
		Thread t = new Thread(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				doValidating(value, currentFileList);
			}
		});
		
		t.start();
	}
	
	private void doValidating(int value, List<ConfigFilePath> currentFileList)
	{
		List<IChecker> checkers = getChecker(value);
		
		if(checkers == null || checkers.size() < 0)
		{
			return;
		}
		
		Iterator<ConfigFilePath> files = currentFileList.iterator();
		
		while(files.hasNext())
		{
			
			ConfigFilePath cf = files.next();
			Iterator<IChecker> it = checkers.iterator();
			
			while(it.hasNext())
			{
				IChecker checker = it.next();
				checker.check(cf);
			}
		}
		
		display.asyncExec(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_VALIDATION_FINISHED, null);
			}
		});
	}
	
	private List<IChecker> getChecker(int value)
	{
		if(value == 0)
		{
			return null;
		}
		
		List<IChecker> result = new ArrayList<IChecker>();
		
		if((value & IConstants.SEL_DUPLICATE) == IConstants.SEL_DUPLICATE)
		{
			// add more checkers here
			result.add(dSectionChecker);
			result.add(dKeyChecker);
		}
		
		if((value & IConstants.SEL_MISSING) == IConstants.SEL_MISSING)
		{
			result.add(missChecker);
		}
		
		return result;
	}
}