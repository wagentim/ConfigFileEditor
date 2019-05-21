package de.etas.tef.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellExecutor
{
	private static final String SHELL_POWER_SHELL ="powershell.exe -file ";
	private static final String device = "shells/get_devices.ps1";
	private static final String sw = "shells/get_install_sw.ps1";
	
	public void exec(String shellCommand, String shellFile, ShellContentHandler handler)
	{
		String command = shellCommand + shellFile;
		Process shellProcess;
		int count = 0;
		try
		{
			shellProcess = Runtime.getRuntime().exec(command);
			shellProcess.getOutputStream().close();
			
			String line;

//			System.out.println("Standard Output:");
			BufferedReader stdout = new BufferedReader(new InputStreamReader(
					shellProcess.getInputStream(), "utf-8"));
			while ((line = stdout.readLine()) != null)
			{
				System.out.println(line);
				count++;
			}
			stdout.close();
//			System.out.println("Standard Error:");
			BufferedReader stderr = new BufferedReader(new InputStreamReader(
					shellProcess.getErrorStream(), "utf-8"));
			while ((line = stderr.readLine()) != null)
			{
				System.out.println(line);
			}
			stderr.close();
			System.out.println("Number: " + count);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		ShellExecutor se = new ShellExecutor();
		
		se.exec(SHELL_POWER_SHELL, sw, null);
	}
}
