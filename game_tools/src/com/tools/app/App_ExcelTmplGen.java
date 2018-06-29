package com.tools.app;

import com.tools.excel.ExcelTemplateGenerator;

/**
 * Excel 模板生成器, 
 * 根据 /config/excel/model/ 目录中的文本文件
 * 生成以 "xxxTmplVO" 命名的 JAVA 类.
 * 生成前会检查 templateToGen.config.xml 文件, 跳过不需要生成的文件
 * @author Thinker
 * 
 */
public class App_ExcelTmplGen
{

	public static void main(String[] args)
	{
		try
		{
			// 生成 Excel 模板
			ExcelTemplateGenerator.main(args);
		} catch (Exception ex)
		{
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}
}
