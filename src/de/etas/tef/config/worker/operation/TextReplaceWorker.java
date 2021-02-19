package de.etas.tef.config.worker.operation;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ChangeAction;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;

public class TextReplaceWorker extends AbstractOperation
{

	public TextReplaceWorker(String[] content, List<ConfigFilePath> currentList, Display display,
			MainController controller)
	{
		super(content, currentList, display, controller);
	}

	@Override
	protected void doOperation(ConfigFilePath cfp) throws IOException
	{
		ConfigFile cf = cfp.getConfigFile();
		
		if(cf == null)
		{
			return;
		}
		
		List<ConfigBlock> cbs = cf.findConfigBlock(section);
		
		if(cbs == null || cbs.isEmpty())
		{
			return;
		}
		
		Iterator<ConfigBlock> paras = cbs.iterator();
		
		while(paras.hasNext())
		{
			ConfigBlock cb = paras.next();
			
			List<KeyValuePair> ps = cb.findParameter(key);
			
			if(ps != null && ps.size() > 0)
			{
				for(KeyValuePair kvp : ps)
				{
					String oldValue = kvp.getValue();
					kvp.setValue(value);
					replaceParaChangeAction(cb, kvp, cfp, oldValue);
				}
			}
		}
	}
	
	private void replaceParaChangeAction(ConfigBlock cb, KeyValuePair kvp, ConfigFilePath cfp, String oldValue)
	{
		ChangeAction ca = new ChangeAction(IConstants.UPDATE_VALUE);
		ca.setOldSection(cb.getBlockName());
		ca.setOldKey(kvp.getKey());
		ca.setOldValue(oldValue);
		ca.setNewValue(kvp.getValue());
		ca.setFile(cfp.getPath());
		changes.add(ca);
	}
}
