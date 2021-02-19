package de.etas.tef.config.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.Problem;
import de.etas.tef.config.helper.Utils;

public class LoggerComposite extends AbstractComposite
{
	private TabFolder tf;
	private StyledText stLogger;
	private StyledText stProblem;

	public LoggerComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	@Override
	protected void initComposite()
	{
		super.initComposite();
		tf = new TabFolder(this, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tf.setLayoutData(gd);

		stLogger = new StyledText(tf, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		stLogger.setLayoutData(gd);
		stLogger.setEditable(false);

		stProblem = new StyledText(tf, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		stProblem.setLayoutData(gd);
		stProblem.setEditable(false);

		TabItem ti = new TabItem(tf, SWT.NONE);
		ti.setControl(stLogger);
		ti.setText("LOGGER");

		ti = new TabItem(tf, SWT.NONE);
		ti.setControl(stProblem);
		ti.setText("PROBLEM");
	}

	private void updateProblems(Map<Integer, List<Problem>> problems)
	{
		stProblem.setText(IConstants.EMPTY_STRING);

		if (!Utils.validMap(problems))
		{
			return;
		}
		
		Iterator<Integer> it = problems.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		
		while(it.hasNext())
		{
			Integer type = it.next();
			List<Problem> probs = problems.get(type);
			
			for(Problem p : probs)
			{
				sb.append(p.getProblemDescription());
				sb.append(IConstants.SYMBOL_NEW_LINE);
			}
		}
		
		stProblem.setText(sb.toString());

	}

	@SuppressWarnings("unchecked")
	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SHOW_PROBLEM)
		{
			if(tf.getSelectionIndex() != 1)
			{
				tf.setSelection(1);
			}
			Map<Integer, List<Problem>> problems = (Map<Integer, List<Problem>>)content;
			updateProblems(problems);
		}
		else if(type == IConstants.ACTION_ADD_LOG)
		{
			if(tf.getSelectionIndex() != 0)
			{
				tf.setSelection(0);
			}
			
			stLogger.append((String)content);
			stLogger.setTopIndex(stLogger.getLineCount() - 1);
		}
	}

}
