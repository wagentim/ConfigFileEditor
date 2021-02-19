package de.etas.tef.config.worker.operation;

import java.io.IOException;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ChangeAction;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;

public class TextDeleteWorker extends AbstractOperation
{

	public TextDeleteWorker(String[] content, List<ConfigFilePath> currentList, Display display, MainController controller)
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
			return;
		}
		
		for(ConfigBlock cb : cbs)
		{
			handleDeleteKeyValue(cb, p);
		}

	}

	private void handleDeleteKeyValue(ConfigBlock cb, ConfigFilePath p)
	{
		List<KeyValuePair> paras = cb.findParameter(key);
		
		if(paras == null || paras.isEmpty())
		{
			return;
		}
		
		List<KeyValuePair> ps = cb.getAllParameters();
		
		for(KeyValuePair kvp : paras)
		{
			ps.remove(kvp);
			deleteParaChangeAction(cb, kvp, p);
		}
	}

	private void deleteParaChangeAction(ConfigBlock cb, KeyValuePair kvp, ConfigFilePath cfp)
	{
		ChangeAction ca = new ChangeAction(IConstants.DELET_KEY_VALUE);
		ca.setOldSection(cb.getBlockName());
		ca.setOldKey(kvp.getKey());
		ca.setOldValue(kvp.getValue());
		ca.setFile(cfp.getPath());
		changes.add(ca);
	}

}
