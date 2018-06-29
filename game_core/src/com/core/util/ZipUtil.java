package com.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import SevenZip.Compression.LZMA.Encoder;

/**
 * 压缩实用工具类
 * 
 * @author Thinker
 * 
 */
public final class ZipUtil
{
	/**
	 * 类默认构造器
	 * 
	 */
	private ZipUtil()
	{
		
	}

	/**
	 * 将字符串压缩成 7z 格式的字节数组
	 * 
	 * @param src
	 * @return 
	 * 
	 */
	public static byte[] compressTo7z(String src) 
	{
		if (src == null || src.isEmpty()) 
		{
			return new byte[0];
		}

		// 获取字节数组
		byte[] byteArr = src.getBytes();
		// 创建读入流
		ByteArrayInputStream ins = new ByteArrayInputStream(byteArr);
		// 创建写出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			// 创建 7z 编码器
			Encoder _7z = new Encoder();
			// 设置工作模式和属性
			_7z.SetEndMarkerMode(true);
			_7z.WriteCoderProperties(out);

			for (int i = 0; i < 8; i++)
			{
				// 写出文件头
				out.write((int)(-1 >>> (8 * i)) & 0xFF);
			}

			// 使用 LZMA 算法压缩
			_7z.Code(ins, out, -1, -1, null);
		} catch (Exception ex)
		{
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}

		// 返回字节数组
		return out.toByteArray();
	}
}
