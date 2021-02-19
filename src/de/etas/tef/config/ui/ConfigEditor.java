package de.etas.tef.config.ui;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.entity.ConfigBlock;
import de.etas.tef.config.entity.ConfigFile;
import de.etas.tef.config.entity.KeyValuePair;

public class ConfigEditor extends TextEditor
{

	public ConfigEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComposite();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SET_CONFIG_BLOCK)
		{
			setConfigBlock((ConfigBlock)content);
		}
		else if(type == IConstants.ACTION_SET_CONFIG_FILE)
		{
			setConfigFile((ConfigFile)content);
		}
	}
	
	private void setConfigFile(ConfigFile cf) {
		List<ConfigBlock> configBlocks = cf.getConfigBlocks();
		if(configBlocks == null || configBlocks.size() == 0)
		{
			text.setText(IConstants.EMPTY_STRING);
			return;
		}
		
		Iterator<ConfigBlock> itb = cf.getConfigBlocks().iterator();
		StringBuilder sb = new StringBuilder();

		while(itb.hasNext())
		{
			ConfigBlock cb = itb.next();
			Iterator<KeyValuePair> it = cb.getAllParameters().iterator();
			sb.append(IConstants.SYMBOL_LEFT_BRACKET);
			sb.append(cb.getBlockName());
			sb.append(IConstants.SYMBOL_RIGHT_BRACKET);
			sb.append(IConstants.SYMBOL_NEW_LINE);
			while(it.hasNext())
			{
				addKeyValue(sb, it.next());
			}
			sb.append(IConstants.SYMBOL_NEW_LINE);
		}
		
		text.setText(sb.toString());
	}

	public void setConfigBlock(ConfigBlock cb)
	{
		if( cb == null )
		{
			text.setText(IConstants.EMPTY_STRING);
			return;
		}
		
		Iterator<KeyValuePair> it = cb.getAllParameters().iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext())
		{
			addKeyValue(sb, it.next());
		}
		
		text.setText(sb.toString());
	}
	
	public void addKeyValue(StringBuilder sb, KeyValuePair kvp)
	{	
		int type = kvp.getType();
		
		sb.append(kvp.getKey());
		switch(type)
		{
		case KeyValuePair.TYPE_PARA:
			sb.append(IConstants.SYMBOL_EQUAL);
			sb.append(kvp.getValue());
			break;
		}
		
		sb.append(IConstants.SYMBOL_NEW_LINE);
	}
}
