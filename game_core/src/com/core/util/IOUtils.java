package com.core.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * @author Thinker
 *
 */
public class IOUtils
{
	public static void writeLengthString(DataOutput dout, String content,String charset) throws IOException 
	{
		if (content == null) 
		{
			content = "";
		}
		byte[] buf = content.getBytes(charset);
		dout.writeShort(buf.length);
		dout.write(buf);
	}

	/**
	 * 关闭输入流，并忽略任何异常
	 * @param in
	 */
	public static void closeInputStream(InputStream in)
	{
		if (in != null)
		{
			try
			{
				in.close();
			} catch (Exception ignore)
			{
				
			}
		}
	}
	
	public static String getString(InputStream in) throws IOException{
		
	       ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = 0;
			while((len=in.read(buff)) > 0){
				out.write(buff,0,len);
			}
			String param = new String(out.toByteArray(),"UTF-8");
			out.close();
			return param;
		}

}
