package de.etas.tef.config.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.etas.tef.config.controller.MainController;
import de.etas.tef.config.helper.IConstants;
import de.etas.tef.config.helper.IImageConstants;

public class SearchComposite extends AbstractComposite
{

	private Text searchText;

	public SearchComposite(Composite parent, int style, MainController controller)
	{
		super(parent, style, controller);
	}

	protected void initComposite()
	{

		GridLayout layout = new GridLayout(3, false);
		layout.marginTop = layout.marginBottom = layout.marginLeft = layout.marginRight = 0;
		layout.marginHeight = layout.marginWidth = 0;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalAlignment = GridData.CENTER;
		this.setLayout(layout);
		this.setLayoutData(gd);
		this.setBackground(controller.getColorFactory().getColorWhite());

		Label labelSearch = new Label(this, SWT.NONE);
		labelSearch.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_SEARCH));
		labelSearch.setBackground(controller.getColorFactory().getColorWhite());

		searchText = new Text(this, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		gd.verticalAlignment = GridData.CENTER;
		gd.verticalSpan = gd.horizontalSpan = 0;
		searchText.setLayoutData(gd);
		searchText.setMessage("Name Filter");

		Label labelRemove = new Label(this, SWT.NONE);
		labelRemove.setImage(controller.getImageFactory().getImage(IImageConstants.IMAGE_CANCEL));
		labelRemove.setVisible(false);
		labelRemove.setBackground(controller.getColorFactory().getColorBackground());

		searchText.addModifyListener(new ModifyListener()
		{

			@Override
			public void modifyText(ModifyEvent event)
			{
				String s = searchText.getText();

				if (s != null && !s.isEmpty())
				{
					labelRemove.setVisible(true);
				} else
				{
					labelRemove.setVisible(false);
				}
				
				searchText.redraw();
			}
		});

		labelRemove.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseUp(MouseEvent arg0)
			{
				searchText.setText(IConstants.EMPTY_STRING);

			}
		});
		
		searchText.addPaintListener(new PaintListener()
		{
			
			@Override
			public void paintControl(PaintEvent event)
			{
				Rectangle rect = searchText.getClientArea();
				GC gc  = event.gc;
				
				gc.setForeground(controller.getColorFactory().getColorDarkGreen());
				gc.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width, rect.y + rect.height - 1);
				gc.dispose();
			}

		});
		
	}

	@Override
	public void receivedAction(int type, Object content)
	{

		if (type == IConstants.ACTION_NEW_FILE_SELECTED || type == IConstants.ACTION_DROP_NEW_FILE_SELECTED)
		{
			searchText.setText(IConstants.EMPTY_STRING);
		}
	}
}
