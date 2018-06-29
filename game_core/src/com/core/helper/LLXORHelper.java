package com.core.helper;

/**
 * 异或加密的工具类
 * @author Thinker
 *  
 */
public class LLXORHelper 
{
	/** 加密钥匙 */
	private final static int XOR_KEY = 100;
	
	/**
	 * 加密
	 * @param buf
	 * @param size
	 */
	public static void NFX_EnCode(byte[] buf,int size)
	{
		int tmpi;
		int conf;
		int tmpc;
		conf = XOR_KEY%255; conf = conf>=0?conf:-conf;
		for( tmpi = 0; tmpi < size; tmpi++ )
		{
			tmpc = buf[tmpi];
			tmpc += conf;
			if(tmpc > 255) tmpc -= 256;
			buf[tmpi] = (byte) (((byte)tmpc) ^ 0xff);
		}
	}

	/**
	 * 解密
	 * @param buf
	 * @param size
	 */
	public static void NFX_DeCode(byte[] buf,int size)
	{
		int tmpi;
		int conf;
		int tmpc;
		conf = XOR_KEY%255; conf = conf>=0?conf:-conf;
		for( tmpi = 0; tmpi < size; tmpi++ )
		{
			tmpc = buf[tmpi] ^ 0xff;
			tmpc = tmpc>=conf?tmpc-conf:256+tmpc-conf;
			
			buf[tmpi] = (byte)tmpc;
		}
	}
}