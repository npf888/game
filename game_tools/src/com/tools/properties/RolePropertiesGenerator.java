package com.tools.properties;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.core.annotation.Comment;
import com.tools.msg.GeneratorHelper;

/**
 * 角色属性模板生成
 * @author Thinker
 * 
 */
public class RolePropertiesGenerator
{
	/** 日志 */
	private static final Logger logger = Logger.getLogger(RolePropertiesGenerator.class);

	/** 类型 */
	private static final String TYPE_INT = "int";
	/** 模板列表 */
	private static final String trPropertiesTemplates = "tr_properties.vm";
	/** 1、2级属性模板列表 */
	private static final String ABPropertiesTemplates = "properties_txt.vm";
	/** 生成客户端代码文件 */
	private static final String outputTrFile = "RoleProperties.as";
	/** 生成策划看的文件 */
	private static final String outputTrTxt = "TrRoleProperties.txt";
	/** 当前目录 */
	private static final String clientRootPath = "";
	/** 属性类名后缀 */
	private static final String[] propertyClassSuffixArray = { 
		"IntProperties", 
		"StrProperties", 
		"AProperty", 
		"BProperty", 
	};

	/**
	 * 初始化数据
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	private static <T> List<RolePropertiesObject> getPropertyObjectList(Class<T> clazz)
	{
		// 获取前缀
		String prefix = getPrefix(clazz);
		// 获取类注释
		Comment clazzComment = clazz.getAnnotation(Comment.class);
		
		// 创建属性列表
		List<RolePropertiesObject> properties = new ArrayList<RolePropertiesObject>();
		try
		{
			Field typeField = clazz.getField("TYPE");
			if (typeField != null
					&& TYPE_INT.equals(typeField.getType().getSimpleName())) 
			{
				int type = typeField.getInt(null);
				Field[] fields = clazz.getFields();
				for (Field field : fields)
				{
					Comment fieldComment = field.getAnnotation(Comment.class);
					if (fieldComment != null) 
					{
						// 判断是否存在包含注释的注解
						if (TYPE_INT.equals(field.getType().getSimpleName())) 
						{
							// 判断整形变量
							RolePropertiesObject obj = new RolePropertiesObject();
							obj.setPrefix(prefix);
							obj.setKey(field.getName());
							if (clazzComment == null)
							{
								obj.setComment(fieldComment.content());
							} else
							{
								obj.setComment(clazzComment.content() + " - " + fieldComment.content());
							}
							properties.add(obj);
						}
					}
				}
			}
		} catch (IllegalArgumentException e)
		{
			logger.error("IllegalArgumentException", e);
		} catch (IllegalAccessException e) 
		{
			logger.error("IllegalAccessException", e);
		} catch (SecurityException e) 
		{
			logger.error("SecurityException", e);
		} catch (NoSuchFieldException e) 
		{
			logger.error("NoSuchFieldException", e);
		}
		return properties;
	}

	/**
	 * 通过给定类对象获取前缀
	 * 
	 * @param clazz
	 * @return
	 */
	private static String getPrefix(Class<?> clazz) 
	{
		if (clazz == null)
		{
			return "";
		}

		String className = new String(clazz.getSimpleName());

		for (int i = 0; i < propertyClassSuffixArray.length; i++)
		{
			// 获取类名后缀
			String suffix = propertyClassSuffixArray[i];
			// 查找后缀索引位置
			int suffixIndex = className.indexOf(suffix);

			if (suffixIndex == -1) 
			{
				continue;
			}
			// 删除后缀字符串
			className = className.substring(0, suffixIndex);
		}

		return className.toUpperCase();
	}

	/**
	 * 生成客户端文件
	 * 
	 * @param intList
	 * @param strList
	 * @param templateFileName
	 * @param outputFileName
	 */
	private static void createClientFile(List<RolePropertiesObject> intList,List<RolePropertiesObject> strList, String templateFileName,
			String outputFileName) 
	{
		try 
		{
			VelocityContext context = new VelocityContext();
			context.put("intAttrs", intList);
			context.put("strAttrs", strList);
			String outputFilePath = clientRootPath + outputFileName;
			GeneratorHelper.generate(context, templateFileName, outputFilePath);
		} catch (Exception e)
		{
			logger.error("Unknown Exception", e);
		}
	}

	/**
	 * 生成客户端1\2级属性策划看的文件
	 * 
	 * @param AList
	 * @param BList
	 * @param templateFileName
	 * @param outputFileName
	 */
	private static void createClientABFile(List<RolePropertiesObject> AList,List<RolePropertiesObject> BList, String templateFileName,
			String outputFileName)
	{
		try 
		{
			VelocityContext context = new VelocityContext();
			context.put("AAttrs", AList);
			context.put("BAttrs", BList);
			context.put("Acomment", "一级属性");
			context.put("Bcomment", "二级属性");
			String outputFilePath = clientRootPath + outputFileName;
			GeneratorHelper.generate(context, templateFileName, outputFilePath);
		} catch (Exception e)
		{
			logger.error("Unknown Exception", e);
		}
	}

	/**
	 * 生成属性文件
	 */
	private static void createPropertyFiles() 
	{
		List<RolePropertiesObject> trIntList=new ArrayList<RolePropertiesObject>();
		List<RolePropertiesObject> trStrList=new ArrayList<RolePropertiesObject>();

		// 生成客户端文件
		createClientFile(trIntList,trStrList,trPropertiesTemplates,outputTrFile);

		List<RolePropertiesObject> propsAList=new ArrayList<RolePropertiesObject>();
		List<RolePropertiesObject> propsBList=new ArrayList<RolePropertiesObject>();

		// 生成策划看的文件
		createClientABFile(propsAList,propsBList,ABPropertiesTemplates,outputTrTxt);
	}

	/**
	 * 执行
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		createPropertyFiles();
		System.out.println("属性模板生成完毕。");
	}
}
