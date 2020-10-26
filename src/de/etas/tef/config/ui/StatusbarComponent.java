package de.etas.tef.config.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;

public class StatusbarComponent extends AbstractComposite
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private Label dateLabel;
	private ProgressBar pb;
	private Label imageProgressbar;
	private Label text1;
	private Label text2;
//	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public StatusbarComponent(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}
	
	protected void initComposite()
	{
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.heightHint = 16;
        this.setLayoutData(gridData);
        
        RowLayout layout = new RowLayout();
        layout.marginTop = layout.marginBottom = 0;
        layout.marginLeft = layout.marginRight = 3;
        this.setLayout(layout);
        this.setBackground(controller.getColorFactory().getColorBackground());
        
        Label imageTime = new Label(this, SWT.NONE);
        imageTime.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_CALENDAR_16));
        imageTime.setBackground(controller.getColorFactory().getColorBackground());
        
        dateLabel = new Label(this, SWT.BOLD);
        dateLabel.setText(" "+sdf.format(new Date())+" ");
        dateLabel.setBackground(controller.getColorFactory().getColorBackground());
        
        new Label(this, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
        
        imageProgressbar = new Label(this, SWT.NONE);
        imageProgressbar.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_PROGRESSBAR_16));
        imageProgressbar.setBackground(controller.getColorFactory().getColorBackground());
        imageProgressbar.setVisible(false);
        
        pb = new ProgressBar(this, SWT.SMOOTH);
        pb.setBackground(controller.getColorFactory().getColorBackground());
        pb.setMaximum(100);
        pb.setMinimum(0);
        pb.setVisible(false);
        
        new Label(this, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
        text1 = new Label(this, SWT.NONE);
        text1.setLayoutData(new RowData(150, 16));
        text1.setBackground(controller.getColorFactory().getColorBackground());
        new Label(this, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
        
        text2 = new Label(this, SWT.NONE);
        text2.setLayoutData(new RowData(30, 16));
        text2.setBackground(controller.getColorFactory().getColorBackground());
        new Label(this, SWT.SEPARATOR | SWT.VERTICAL | SWT.BOLD);
	}
	
	public void updateTime()
	{
		dateLabel.setText(" " + sdf.format(new Date()) + " ");
	}

	@Override
	public void receivedAction(int type, Object content)
	{
		if(type == IConstants.ACTION_PROGRESS_BAR_DISPLAY)
		{
			if(!pb.isVisible())
			{
				setProgressbarDisplay((boolean)content);
			}
			pb.setSelection(0);
		}
		else if(type == IConstants.ACTION_PROGRESS_BAR_UPDATE)
		{
			int value = (int)(100 * (double)content);
			
//			logger.info("Percentage: {}", value);
			pb.setSelection(value);
			
		}
	}
	
	private void setProgressbarDisplay(boolean display)
	{
		imageProgressbar.setVisible(display);
		pb.setVisible(display);
	}

}
