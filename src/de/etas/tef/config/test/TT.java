package de.etas.tef.config.test;

import de.etas.tef.config.entity.ConfigFile;

public class TT 
{
	private static String fileIn = "T:\\Bin Huang\\ate-tools.ini";
	private static String fileIn_1 = "T:\\Bin Huang\\ES630.ini";
	private static String fileOut = "T:\\Bin Huang\\ate-tools_out.ini";
	private static String fileOut_1 = "T:\\Bin Huang\\ES630_out.ini";
	public static void main(String[] args)
	
	{
		FileExec fe = new FileExec();
		ConfigFile cf = fe.read(fileIn);
		fe.save(fileOut, cf);
		
//		ConfigFile cf = fe.read(fileIn_1);
//		fe.save(fileOut_1, cf);
	}  

}