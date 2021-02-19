package de.etas.tef.config.listener;

import java.awt.Desktop;
import java.io.IOException;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.etas.tef.config.entity.ConfigFilePath;

public class OpenFileSelectionListener implements SelectionListener {

	private final ConfigFilePath p;
	
	public OpenFileSelectionListener(final ConfigFilePath p) {
		this.p = p;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		if(p == null)
		{
			return;
		}
		
		try {
			Desktop.getDesktop().open(p.getPath().toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
