package com.core.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.constants.CommonErrorLogInfo;
import com.core.config.Config;
import com.core.script.IScriptEngine;
import com.core.script.impl.JSScriptManagerImpl;
import com.core.util.ErrorsUtil;

/**
 * 配置相关的辅助类
 * @author Thinker
 * 
 */
public class ConfigHelper
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

	/**
	 * 根据指定的配置类型<tt>configClass</tt>从<tt>configURL</tt>中加载配置
	 * 
	 * @param <T>
	 * @param configClass
	 *            配置的类型
	 * @param configURL
	 *            配置文件的URL,文件内容是一个以JavaScript编写的配置脚本
	 * @return 从configURL加载的配置对象
	 * @exception RuntimeException
	 *                从configClass构造对象失败时抛出此异常
	 * @exception IllegalArgumentException
	 *                配置验证失败时抛出此异常
	 * @exception IllegalStateException
	 *                从configUrl中加载内容失败时抛出此异常
	 */
	public static <T extends Config> T buildConfig(Class<T> configClass,URL configURL) 
	{
		if (configClass == null) 
		{
			throw new IllegalArgumentException(ErrorsUtil.error(CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "configClass"));
		}
		if (configURL == null)
		{
			throw new IllegalArgumentException(ErrorsUtil.error(CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "configURL"));
		}
		if (logger.isInfoEnabled())
		{
			logger.info("Load config [" + configClass + "] from [" + configURL+ "]");
		}
		T _config = null;
		try
		{
			_config = configClass.newInstance();
		} catch (InstantiationException e1)
		{
			throw new RuntimeException(e1);
		} catch (IllegalAccessException e1) 
		{
			throw new RuntimeException(e1);
		}
		IScriptEngine _jsEngine = new JSScriptManagerImpl("UTF-8");
		Map<String, Object> _bindings = new HashMap<String, Object>();
		_bindings.put("config", _config);
		Reader _r = null;
		String _scriptContent = null;
		try 
		{
			_r = new InputStreamReader(configURL.openStream(), "UTF-8");
			_scriptContent = IOUtils.toString(_r);
		} catch (IOException e)
		{
			throw new IllegalStateException("Can't load config from url ["+ configURL + "]");
		} finally 
		{
			IOUtils.closeQuietly(_r);
		}
		_jsEngine.runScript(_bindings, _scriptContent);
		_config.validate();
		return _config;
	}

	/**
	 * 获得配置文件的真实路径
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getConfigPath(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(fileName).getPath();
	}

	public static URL getConfigURL(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(fileName);
	}
}
