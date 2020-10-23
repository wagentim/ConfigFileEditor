package de.etas.tef.config.ui.composites;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.etas.tef.config.controller.MainController;

public class Statusbar extends AbstractComposite
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	private Label dateLabel;

	public Statusbar(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite()
	{
		super.initComposite();
		Composite statusbar = new Composite(parent, SWT.BORDER);

        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.heightHint = 20;
        statusbar.setLayoutData(gridData);
        RowLayout layout = new RowLayout();
        layout.marginLeft = layout.marginTop = 0;
        statusbar.setLayout(layout);
        
        Label image = new Label(statusbar, SWT.NONE);
        
        dateLabel = new Label(statusbar, SWT.BOLD);
        dateLabel.setLayoutData(new RowData(150, -1));
        dateLabel.setText(" "+sdf.format(new Date())+" ");
        
        new Label(statusbar, SWT.SEPARATOR | SWT.VERTICAL);
	}
	
	public void updateTime()
	{
		dateLabel.setText(" " + sdf.format(new Date()) + " ");
	}

	@Override
	public void receivedAction(int type, Object content)
	{

	}

}
