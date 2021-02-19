package de.etas.tef.config.worker.operation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ChangeAction;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;

public class TextAddWorker extends AbstractOperation
{

	public TextAddWorker(String[] content, List<ConfigFilePath> currentList, Display display, MainController controller)
	{
		super(content, currentList, display, controller);
	}

	protected void doOperation(ConfigFilePath p) throws IOException
	{

		ConfigFile cf = p.getConfigFile();

		if (cf == null)
		{
			return;
		}

		List<ConfigBlock> cbs = cf.findConfigBlock(section);

		if (cbs == null || cbs.isEmpty())
		{
			cbs = handleAddSection(p);
		}
		
		
		if(key == null || key.isEmpty())
		{
			return;
		}
		
		for(ConfigBlock cb : cbs)
		{
			handleAddKeyValue(cb, p);
		}

	}

	private void handleAddKeyValue(ConfigBlock cb, ConfigFilePath p)
	{
		List<KeyValuePair> paras = cb.findParameter(key);
		
		if(paras == null || paras.isEmpty())
		{
			KeyValuePair kvp = new KeyValuePair();
			kvp.setKey(key);
			kvp.setValue(value);
			kvp.setType(KeyValuePair.TYPE_PARA);
			cb.addParameterInLast(kvp);
			addParaChangeAction(cb, kvp, p);
		}
		else
		{
			for(KeyValuePair kvp : paras)
			{
				String orgValue = kvp.getValue().trim();
				
				if(!orgValue.equalsIgnoreCase(value))
				{
					display.syncExec(new Runnable()
					{
						@Override
						public void run()
						{
							MessageBox mb = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
							mb.setText("Add Value Confirmation");
							StringBuilder sb = new StringBuilder();
							sb.append("File: ");
							sb.append(p.getPath().toFile().getAbsolutePath());
							sb.append(IConstants.SYMBOL_NEW_LINE);
							sb.append("Old Parameter: ");
							sb.append(kvp.getKey());
							sb.append(IConstants.SYMBOL_EQUAL);
							sb.append(kvp.getValue());
							sb.append(IConstants.SYMBOL_NEW_LINE);
							sb.append("New Parameter: ");
							sb.append(kvp.getKey());
							sb.append(IConstants.SYMBOL_EQUAL);
							sb.append(value);
							sb.append(IConstants.SYMBOL_NEW_LINE);
							sb.append("Replace with new Value?");
							sb.append(IConstants.SYMBOL_NEW_LINE);
							mb.setMessage(sb.toString());

							boolean yes = mb.open() == SWT.YES;
							
							if(yes)
							{
								String oldValue = kvp.getValue();
								kvp.setValue(value);
								replaceParaChangeAction(cb, kvp, p, oldValue);
							}
						}
					});
				}
			}
		}
	}
	
	private void replaceParaChangeAction(ConfigBlock cb, KeyValuePair kvp, ConfigFilePath cfp, String oldValue)
	{
		ChangeAction ca = new ChangeAction(IConstants.UPDATE_VALUE);
		ca.setOldSection(cb.getBlockName());
		ca.setOldKey(kvp.getKey());
		ca.setOldValue(kvp.getValue());
		ca.setNewValue(kvp.getValue());
		ca.setFile(cfp.getPath());
		changes.add(ca);
	}

	private void addParaChangeAction(ConfigBlock cb, KeyValuePair kvp, ConfigFilePath cfp)
	{
		ChangeAction ca = new ChangeAction(IConstants.ADD_KEY_VALUE);
		ca.setNewSection(cb.getBlockName());
		ca.setNewKey(kvp.getKey());
		ca.setNewValue(kvp.getValue());
		ca.setFile(cfp.getPath());
		changes.add(ca);
	}

	private List<ConfigBlock> handleAddSection(ConfigFilePath cfp)
	{
		List<ConfigBlock> cb = cfp.getConfigFile().findConfigBlock(section);
		
		if(cb == null || cb.isEmpty())
		{
			cb = new ArrayList<ConfigBlock>();
			ConfigBlock ncb = new ConfigBlock();
			ncb.setBlockName(section.trim());
			cb.add(ncb);
			cfp.getConfigFile().addConfigBlock(ncb);
			addBlockChangeAction(ncb, cfp);
		}

		return cb;
	}
	
	private void addBlockChangeAction(ConfigBlock cb, ConfigFilePath cfp)
	{
		ChangeAction ca = new ChangeAction(IConstants.ADD_SECTION);
		ca.setNewSection(cb.getBlockName());
		ca.setFile(cfp.getPath());
		changes.add(ca);
	}
}
