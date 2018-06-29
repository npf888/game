package com.logserver.util;

import java.net.URL;


/**
 * 
 * @author Thinker
 *
 */
public class ResourcePathUtil
{

	/**
	 * 在指定资源路径找到给定资源文件，并返回资源文件的完整路径 查找顺序如下 1. 查找目标.jar的压缩包内路径 2. 在程序的启动目录查找
	 * 
	 * @param resourceFileName
	 * @return
	 * @exception RuntimeException
	 */
	public static URL getRootPathWithoutSlash(String resourceFileName)
	{
		try
		{
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			URL _url = classLoader.getResource(resourceFileName);
			URL _url = ResourcePathUtil.class.getResource("/" + resourceFileName);
			if (_url == null)
			{
				return null;
			}
			return _url;
		} catch (Exception e) 
		{
			throw new RuntimeException("Can't find the resource file [" + resourceFileName + "] in the class path.");
		}
	}

	public static URL getRootPath(String resourceFileName)
	{
		try 
		{
			return ResourcePathUtil.class.getResource("/" + resourceFileName);
		} catch (Exception e) 
		{
			throw new RuntimeException("Can't find the resource file [" + resourceFileName + "] in the class path.");
		}
	}

}
