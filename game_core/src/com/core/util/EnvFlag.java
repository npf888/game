package com.core.util;



/**
 * 运行模式标记
 * @author Thinker
 *
 */
public enum EnvFlag
{
	/**
	 *开发模式, 运行于开发机使用的模式
	 */
	DevelopMode("__WAR_DEVELOP_MODE__");



	private final String envVar;

	private Tribool value = Tribool.Unknow;

	private EnvFlag(String envVar)
	{
		this.envVar = envVar;
	}

	public boolean marked() 
	{
		if (value.equals(Tribool.Unknow))
			value = Tribool.wrap(System.getenv(envVar) != null);

		return Tribool.True.equals(value);
	}
	
	public boolean unmarked()
	{
		return !marked();
	}
}
