package de.etas.tef.config.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.eclipse.swt.widgets.Composite;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.editor.message.MessageManager;

public class ConfigTextEditor extends TextEditor
{

	private String replaceTxt = IConstants.EMPTY_STRING;
	private String searchTxt = IConstants.EMPTY_STRING;
	
	public ConfigTextEditor(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
		initComposite();
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_SEARCH_NEXT)
		{
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_GET_SEARCH_TEXT, content);
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_GET_REPLACE_TEXT, content);
			
			if(replaceTxt == null || replaceTxt.isEmpty())
			{
				return;
			}
			
			MessageManager.INSTANCE.sendMessage(IConstants.ACTION_REPLACE_TEXT, new String[] {searchTxt, replaceTxt});
			
		}
		else if(type == IConstants.ACTION_SET_SEARCH_TEXT)
		{
			this.searchTxt = (String)content;
		}
		else if( type == IConstants.ACTION_SET_REPLACE_TEXT)
		{
			this.replaceTxt = (String)content;
		}
	}

	public void setFile(Path file) throws IOException 
	{
		text.setText("");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) 
        {
        	sb.append(line);
        	sb.append("\r\n");
        }
        
        text.setText(sb.toString());
		br.close();
	}

}
