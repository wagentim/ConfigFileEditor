package de.etas.tef.config.worker.operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Display;

import de.etas.tef.config.constant.IConstants;
import de.etas.tef.config.entity.ConfigFilePath;
import de.etas.tef.config.entity.KeyValuePair;
import de.etas.tef.config.helper.Utils;
import de.etas.tef.editor.message.MessageManager;

public class TextReplaceWorkerWholeText implements Runnable {

	private final String[] content;
	private final List<ConfigFilePath> currentList;
	private final Display display;
	private final Map<ConfigFilePath, List<KeyValuePair>> changes = new HashMap<ConfigFilePath, List<KeyValuePair>>();

	public TextReplaceWorkerWholeText(String[] content, List<ConfigFilePath> currentList, Display display) {
		this.content = content;
		this.currentList = currentList;
		this.display = display;
	}

	@Override
	public void run() {
		try {
			exec();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exec() throws IOException {
		String search = content[0];
		String replace = content[1];

		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_DISPLAY, true);
			}
		});

		int progressCounter = 1;

		String newSearch = Utils.removeSpace(search);

		for (ConfigFilePath p : currentList) {
			
			backupFile(p.getPath());

			List<KeyValuePair> updates = new ArrayList<KeyValuePair>();
			changes.put(p, updates);

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(p.getPath().toFile())));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (line.trim().equalsIgnoreCase(search)) {
					updates.add(new KeyValuePair(line, replace));
					line = replace;
				} else {
					String newLine = Utils.removeSpace(line);

					if (newSearch.equalsIgnoreCase(newLine)) {
						updates.add(new KeyValuePair(line, replace));
						line = replace;
					}
				}

//	        	if(line.contains("="))
//	        	{
//	        		StringTokenizer st = new StringTokenizer(line, "=");
//	        		StringBuilder s = new StringBuilder();
//	        		int total = st.countTokens();
//	        		
//	        		if(total == 2)
//	        		{
//	        			int counter = 1;
//	        			while(st.hasMoreTokens())
//	        			{
//	        				String key = st.nextToken();
//	        				
//	        				if(key.equalsIgnoreCase(search))
//	        				{
//	        					key = replace;
//	        				}
//	        				s.append(key);
//	        				
//	        				if(counter < total)
//	        				{
//	        					s.append("=");
//	        				}
//	        				
//	        				counter++;
//	        			}
//	        			line = s.toString();
//	        		}
//	        	}

				sb.append(line);
				sb.append("\r\n");
			}
			br.close();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(p.getPath().toFile())));

			bw.write(sb.toString());
			bw.flush();
			bw.close();

			double d = (double) progressCounter / currentList.size();

			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_UPDATE, d);
				}
			});

			progressCounter++;
		}

		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				MessageManager.INSTANCE.sendMessage(IConstants.ACTION_PROGRESS_BAR_HIDE, null);
			}
		});

		printReports();
	}

	private void printReports() {
		
		Path currPath = Paths.get(Utils.getCurrentPath().toString() + File.separator + "log.txt");

		if (!Files.exists(currPath, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createFile(currPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try (BufferedWriter writer = Files.newBufferedWriter(currPath, Charset.forName("UTF-8"))) {
			
			Set<ConfigFilePath> files = changes.keySet();
			
			writer.write("%%%  " + getTimeStample() + "   %%%\r\n\r\n\r\n");
			
			for(ConfigFilePath p : files)
			{
				writer.write("************** " + p.getPath().toString() + " **************\r\n" );
				
				List<KeyValuePair> updates = changes.get(p);
				
				for(KeyValuePair kv : updates)
				{
					writer.write(kv.getKey() + " -> " + kv.getValue() + "\r\n");
				}
				
				writer.write("************** FINISH **************\r\n\r\n" );
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void backupFile(Path p) {
		StringBuilder sb = new StringBuilder();
		sb.append(p.getFileName().toString());
		sb.append(".");
		sb.append(getTimeStample());

		Path backupFile = Paths.get(p.getParent().toString() + File.separator + sb.toString());

		try {
			Files.copy(p, backupFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private String getTimeStample() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		return now.format(dtf);
	}

}
