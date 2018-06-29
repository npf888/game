package com.robot.slot.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExportValue {

	/*
	 * 输出参数
	 */
	public static String exportValue(String targetFile,String reward){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(targetFile,true);
			String tab = reward+"----\r\n";
			out.write(tab.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
