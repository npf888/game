package com.core.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


/**
 * 数据压缩和解压缩
 * @author Thinker
 *
 */
public class LLZlibHelper 
{
	public LLZlibHelper()
	{
	}
	private static int cachesize = 1024; 
	private static Inflater decompresser = new Inflater(); 
	private static Deflater compresser = new Deflater(); 
	
	/**
	 * 压缩数据
	 * @param input
	 * @return
	 */
	public static byte[] compressBytes(byte input[]) 
	{ 
		compresser.reset(); 
		compresser.setInput(input); 
		compresser.finish(); 
		byte output[] = new byte[0]; 
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length); 
		try 
		{ 
			byte[] buf = new byte[input.length]; 
			int got; 
			while (!compresser.finished()) 
			{ 
				got = compresser.deflate(buf); 
				o.write(buf, 0, got); 
			} 
			output = o.toByteArray(); 
		} finally 
		{ 
			try 
			{ 
				o.close(); 
			} catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
		} 
		return output; 
	} 
	
	/**
	 * 解压缩数据
	 * @param input
	 * @return
	 */
	public static byte[] decompressBytes(byte input[]) 
	{ 
		byte output[] = new byte[0]; 
		decompresser.reset(); 
		decompresser.setInput(input); 
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length); 
		try 
		{ 
			byte[] buf = new byte[input.length];
			int got; 
			while (!decompresser.finished()) 
			{ 
				got = decompresser.inflate(buf); 
				o.write(buf, 0, got); 
			} 
			output = o.toByteArray(); 
		}catch(Exception e) 
		{ 
			e.printStackTrace(); 
		}finally 
		{ 
			try 
			{ 
				o.close(); 
			} catch (IOException e) 
			{ 
				e.printStackTrace(); 
			} 
		} 
		return output; 
	} 
}
