package com.tools.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 自定义加载器
 * 
 * @author Thinker
 *
 */
class XmlDocLoader
{
	/** XPath 对象 */
	private static final XPath XPATH_OBJ = XPathFactory.newInstance().newXPath();

	/**
	 * 要生成的模版的配置文件列表
	 * 
	 * @return 
	 * 
	 */
	public List<TemplateToGenConfig> loadTemplateToGenConfigList(String fileName) {
		if (fileName == null || 
			fileName.isEmpty()) 
		{
			// 如果文件名为空, 
			// 则直接返回空列表!
			return Collections.emptyList();
		}

		// 获取 XML 文档对象
		Document xmlDoc = getXmlDoc(fileName);

		if (xmlDoc == null) 
		{
			return Collections.emptyList();
		}

		// 获取所有的 <template> 标记
		NodeList tmplNL = (NodeList)xpathGet(xmlDoc, "/templateToGen/template", XPathConstants.NODESET);

		// 创建结果列表
		List<TemplateToGenConfig> resultList = new ArrayList<TemplateToGenConfig>();

		for (int i = 0; i < tmplNL.getLength(); i++)
		{
			// 获取 <template> 标记
			Node templateTag = tmplNL.item(i);
			// 获取模版配置对象
			TemplateToGenConfig ttgc = this.getTemplateToGenConfig(templateTag);

			if (ttgc == null) 
			{
				continue;
			} else
			{
				resultList.add(ttgc);
			}
		}

		return resultList;
	}

	/**
	 * 获取模版配置对象
	 * 
	 * @param templateTag &lt;template&gt; 标记
	 * @return 
	 * 
	 */
	private TemplateToGenConfig getTemplateToGenConfig(Node templateTag)
	{
		if (templateTag == null) 
		{
			return null;
		}

		// 获取模版配置文件名称
		String templateConfigFileName = (String)xpathGet(
			templateTag, 
			"templateConfigFileName/text()", 
			XPathConstants.STRING);

		// 获取类名称
		String clazzName = (String)xpathGet(
			templateTag, 
			"clazzName/text()", 
			XPathConstants.STRING);

		// 获取类注释
		String clazzComment = (String)xpathGet(
			templateTag, 
			"clazzComment/text()", 
			XPathConstants.STRING);

		// 获取父类
		String superClazz = (String)xpathGet(
			templateTag, 
			"superClazz/text()", 
			XPathConstants.STRING);

		// 创建并返回模版配置
		return new TemplateToGenConfig(
			templateConfigFileName, 
			clazzName, 
			clazzComment, 
			superClazz);
	}

	/**
	 * 获取 XML 文档对象
	 * 
	 * @param fileName
	 * @return 
	 * 
	 */
	private static final Document getXmlDoc(String fileName)
	{
		if (fileName == null || 
			fileName.isEmpty())
		{
			return null;
		}

		try
		{
			// 创建文档构建器
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			// 解析文件并返回结果
			return db.parse(fileName);
		} catch (Exception ex)
		{
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 通过 XPath 表达式获取数据
	 * 
	 * @param item
	 * @param xpathExpr
	 * @param returnType
	 * @return 
	 * 
	 */
	private static final Object xpathGet(
		Object item, 
		String xpathExpr, 
		QName returnType) 
	{

		if (item == null) 
		{
			// 被演算的项目为空, 
			// 则直接退出!
			return null;
		}

		if (xpathExpr == null || xpathExpr.isEmpty())
		{
			// XPath 表达式为空, 
			// 则直接退出!
			return null;
		}

		if (returnType == null)
		{
			// 返回值类型为空, 
			// 则直接退出!
			return null;
		}

		try 
		{
			// 编译 XPath 表达式
			XPathExpression exprObj = XPATH_OBJ.compile(xpathExpr);
			// 演算并返回结果
			return exprObj.evaluate(item, returnType);
		} catch (Exception ex)
		{
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}
}
