package com.tools.excel;

/**
 * 要生成的模版的配置文件
 * 
 * @author Thinker
 * 
 */
public class TemplateToGenConfig
{
	/** 模版配置文件名称 */
	private String _templateConfigFileName;
	/** 全称类名 */
	private String _clazzName;
	/** 类的注释 */
	private String _clazzComment;
	/** 父类 */
	private String _superClazz;

	/**
	 * 类参数构造器
	 * 
	 * @param templateConfigFileName
	 * @param clazzName
	 * @param clazzComment
	 * @param superClazz 
	 * 
	 */
	public TemplateToGenConfig(
		String templateConfigFileName, 
		String clazzName, 
		String clazzComment, 
		String superClazz)
	{

		this._templateConfigFileName = templateConfigFileName;
		this._clazzName = clazzName;
		this._clazzComment = clazzComment;
		this._superClazz = superClazz;

		if (superClazz == null || 
			superClazz.isEmpty()) 
		{
			this._superClazz = "TemplateObject";
		}
	}

	/**
	 * 获取模版配置文件名称
	 * 
	 * @return 
	 * 
	 */
	public String getTemplateConfigFileName()
	{
		return this._templateConfigFileName;
	}

	/**
	 * 设置模版配置文件名称
	 * 
	 * @param value
	 * 
	 */
	public void setTemplateConfFileName(String value)
	{
		this._templateConfigFileName = value;
	}

	/**
	 * 获取类名称
	 * 
	 * @return 
	 * 
	 */
	public String getClazzName() 
	{
		return this._clazzName;
	}

	/**
	 * 设置类名称
	 * 
	 * @param value 
	 * 
	 */
	public void setClazzName(String value) 
	{
		this._clazzName = value;
	}

	/**
	 * 获取类注释
	 * 
	 * @return 
	 * 
	 */
	public String getClazzComment() 
	{
		return this._clazzComment;
	}

	/**
	 * 设置类注释
	 * 
	 * @param value 
	 * 
	 */
	public void setClazzComment(String value)
	{
		this._clazzComment = value;
	}

	/**
	 * 获取父类
	 * 
	 * @return 
	 * 
	 */
	public String getSuperClazz()
	{
		return this._superClazz;
	}

	/**
	 * 设置父类
	 * 
	 * @param value 
	 * 
	 */
	public void setSupperClazz(String value) 
	{
		this._superClazz = value;
	}
}
