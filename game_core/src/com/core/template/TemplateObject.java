package com.core.template;

import com.common.exception.ConfigException;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;



/**
 * 
 * @author Thinker
 *
 */
@ExcelRowBinding
public abstract class TemplateObject
{
	public static int NULL_ID = 0;

	@ExcelCellBinding(offset = 0)
	protected int id;

	/** Excel 文件名称 */
	private String _xlsFileName;
	/** 工作表单名称 */
	private String _sheetName;
	/** 模板服务 */
	private TemplateService _tmplServ = null;

	/**
	 * 获取 ID
	 * 
	 * @return 
	 * 
	 */
	public final int getId() 
	{
		return id;
	}

	/**
	 * 设置 ID
	 * 
	 * @param id 
	 * 
	 */
	public final void setId(int id) 
	{
		this.id = id;
	}

	/**
	 * <pre>
	 * 在{@link TemplateServiceImpl}加载完所有的模板对象之后调用，主要用于检查各个模板
	 * 表配置是否正确，如果不正确，应抛出{@link ConfigException}类型的异常，并在异常
	 * 消息中记录详细的出错信息
	 * </pre>
	 * @throws TemplateConfigException TODO
	 */
	public abstract void check() throws TemplateConfigException;

	/**
	 * <pre>
	 * 在{@link TemplateServiceImpl}加载完所有的模板对象之后调用，主要用于构建各个模板
	 * 对象之间的依赖关系
	 * </pre>
	 * @throws Exception 
	 */
	public void patchUp() throws Exception 
	{
	}

	/**
	 * 获取所属 Excel 文件名称
	 * 
	 * @return
	 */
	public String getXlsFileName()
	{
		return this._xlsFileName;
	}

	/**
	 * 设置所属 Excel 文件名称
	 * 
	 * @param value
	 */
	public void setXlsFileName(String value)
	{
		this._xlsFileName = value;
	}

	/**
	 * 返回此模板的名字
	 * 
	 * @return
	 */
	public String getSheetName() 
	{
		return this._sheetName;
	}

	/**
	 * 设置此模版所对应的 Excel 页签名称
	 * 
	 * @param value
	 */
	public void setSheetName(String value) 
	{
		this._sheetName = value;
	}

	/**
	 * 获取模板服务
	 * 
	 * @return 
	 * 
	 */
	public TemplateService getTemplateService()
	{
		return this._tmplServ;
	}

	/**
	 * 设置模板服务
	 * 
	 * @param value 
	 * 
	 */
	void setTemplateService(TemplateService value)
	{
		this._tmplServ = value;
	}
}
