
package com.tools.app;


import java.io.File;
import java.net.URL;

import com.common.constants.LangConstants;
import com.tools.i18n.SysLangGenerator;

/**
 * 多语言 Excel 生成工具
 * 
 * @author Thinker
 *
 */
public class App_SysLangGen
{
	public static void main(String[] args)
	{
		// 获取 Excel 文件名称
		String i18nXls = getWorkspaceDir() + "/resources/i18n/zh_CN/sys_lang.xls";
		// 输出提示信息
		System.out.println("创建 " + i18nXls);

		// 创建文件
		(new SysLangGenerator()).gen(new Class<?>[] { LangConstants.class }, i18nXls);

		// 输出完成消息
		System.out.println("创建完成!");
	}
	/**
	 * 获取工作空间目录
	 * @return 
	 * 
	 */
	private static String getWorkspaceDir()
	{
		// 获取当前资源路径
		URL url = Thread.currentThread().getContextClassLoader().getResource(".");
		// 当前工作目录
		String workspaceDir = url.getPath() + "../..";

		try 
		{
			// 获取工作目录
			return (new File(workspaceDir)).getCanonicalPath();
		} catch (Exception ex)
		{
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}
}
