package com.gameserver.slot.handler.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportValue {

	private static final Logger logger = LoggerFactory.getLogger(ExportValue.class);
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
			logger.error("", e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return null;
	}
}
