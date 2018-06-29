package com.tools.excel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.core.util.FileUtil;

/**
 * Excel模板生成器
 * @author Thinker
 * 
 */
public class ExcelTemplateGenerator
{

	private static final String path_gs = "..\\game_server\\src\\";
	private static final String path_core = "..\\core\\src\\";
	private static final String path_cs = "TemplateTarget/";

	/** 模版配置文件 */
	private static final String CONFIG_XML = "excel/templateToGen.config.xml";

	private static final String MODEL_DIR = "excel/model/";

	private static final Pattern TEMPLATE_FIELD = Pattern
			.compile("([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(\\[([^\\s]*)\\])?\\s*(//\\s*?(.*))?");
	private static final Pattern ANOTATION_COLLECTION = Pattern
			.compile("^collection\\((\\d+),(\\d+)\\)$");
	private static final Pattern ANOTATION_ROWBINDING = Pattern
			.compile("^object\\((\\d+)\\)$");
	private static final Pattern ANOTATION_CELLBINDING = Pattern
			.compile("cell");
	private static final Pattern ANOTATION_NOTTRANSLATE = Pattern
			.compile("nottranslate");

	private static int lineNumber = 1;
	private static Set<String> types;


	public static void main(String[] args) throws IOException 
	{
		System.out.println("开始创建 Excel 模板配置代码");
		System.out.println("--------\n");

		(new ExcelTemplateGenerator()).gen();

		System.out.println("\n创建完成!");
	}

	private void gen() throws IOException 
	{
		FileUtil.delete(new File(getPath( "cs")));
		Properties _vp = new Properties();
		_vp.put("file.resource.loader.path", "config/excel/codeTmpl");
		try 
		{
			Velocity.init(_vp);
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}

		// 加在模版配置列表
		List<TemplateToGenConfig> cfgList = this.loadTemplateToGenConfig(CONFIG_XML);

		for (TemplateToGenConfig _conf : cfgList)
		{
			types = new HashSet<String>();
			lineNumber = 1;
			List<ExcelFieldObject> fields = loadDetailTemplateConfig(_conf);

			String _fileName = "";
			String _pkgName = "";
			if (_conf.getClazzName().contains("."))
			{
				_fileName = _conf.getClazzName().substring(_conf.getClazzName().lastIndexOf(".") + 1);
				_pkgName = _conf.getClazzName().substring(0,_conf.getClazzName().lastIndexOf("."));
			} else 
			{
				_fileName = _conf.getClazzName();
			}

			if (_conf.getSuperClazz().equals("TemplateObject"))
			{
				types.add("com.core.template.TemplateObject");
			}

			VelocityContext _context = new VelocityContext();
			_context.put("fields", fields);
			_context.put("packageName", _pkgName);
			_context.put("className", _fileName);
			_context.put("types", types);
			_context.put("comment", _conf.getClazzComment());
			_context.put("father", _conf.getSuperClazz());

			generateTemplate(_context, _fileName, _pkgName, "gs");
			generateClientTemplate(_context, _fileName, _pkgName, "cs");
		}
	}

	/**
	 * 读取要生成的模板的配置文件
	 * 
	 * @param fileName
	 * @return 
	 * 
	 */
	private List<TemplateToGenConfig> loadTemplateToGenConfig(String fileName) 
	{
		// 获取完整名称
		String filePath = getPhysicalPath(fileName);
		// 创建自定义加载器
		XmlDocLoader loader = new XmlDocLoader();

		// 加在模版配置列表并返回
		return loader.loadTemplateToGenConfigList(filePath);
	}

	/**
	 * 加载某一具体模板的详细信息
	 * 
	 * @param config
	 * @return
	 * @throws IOException
	 */
	private static List<ExcelFieldObject> loadDetailTemplateConfig(TemplateToGenConfig config) throws IOException
	{
		List<ExcelFieldObject> _fields = new ArrayList<ExcelFieldObject>();

		String _fileName = config.getTemplateConfigFileName();
		System.out.println(_fileName);
		String _tmpConfig = getPhysicalPath(MODEL_DIR + _fileName);

		BufferedReader _reader = new BufferedReader(new InputStreamReader(new FileInputStream(_tmpConfig)));
		String _line = null;

		while ((_line = _reader.readLine()) != null)
		{
			_line = _line.trim();
			if (_line.length() == 0) 
			{
				continue;
			}

			Matcher _matcher = TEMPLATE_FIELD.matcher(_line);
			if (!_matcher.matches()) 
			{
				lineNumber++;
				continue;
			}

			ExcelFieldObject _f = buildFieldObject(_matcher.group(1), _matcher
					.group(2), _matcher.group(4), _matcher.group(6),lineNumber + 1);
			_fields.add(_f);
		}
		return _fields;
	}

	/**
	 * 生成模板类
	 * 
	 * @param config
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateTemplate(VelocityContext context,
			String fileName, String pkgName, String path)
			throws UnsupportedEncodingException, IOException
	{
		StringWriter _readWriter = new StringWriter();
		try 
		{
			Velocity.mergeTemplate("TemplateVOClazz.velocity", "UTF-8", context,_readWriter);		
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}

		if (!pkgName.equals(""))
		{
			path = getPath(path);
			File _srcDist = new File(path + pkgName.replaceAll("\\.", "/"));
			if (!_srcDist.exists())
			{
				if (!_srcDist.mkdirs()) 
				{
					throw new RuntimeException("Can't create dir " + _srcDist);
				}
			}

            File targetFile = new File(_srcDist, fileName + ".java");
			Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
			_fileWriter.write(_readWriter.toString());
			_fileWriter.close();

            System.out.println("生成文件 : " + targetFile.getCanonicalPath());
		} else 
		{
            File targetFile = new File(fileName + ".java");
			Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
			_fileWriter.write(_readWriter.toString());
			_fileWriter.close();

            System.out.println("生成文件 : " + targetFile.getCanonicalPath());
		}
	}
	/**
	 * 生成客户端模板类
	 * 
	 * @param config
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateClientTemplate(VelocityContext context,
			String fileName, String pkgName, String path)
			throws UnsupportedEncodingException, IOException
	{
		StringWriter _readWriter = new StringWriter();
		try 
		{
			Velocity.mergeTemplate("TemplateVOClazz_cs.velocity", "UTF-8", context,_readWriter);
			
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}

		if (!pkgName.equals(""))
		{
			path = getPath(path);
			File _srcDist = new File(path);
			if (!_srcDist.exists())
			{
				if (!_srcDist.mkdirs()) 
				{
					throw new RuntimeException("Can't create dir " + _srcDist);
				}
			}

            File targetFile = new File(_srcDist, fileName + ".cs");
			Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
			_fileWriter.write(_readWriter.toString());
			_fileWriter.close();

            System.out.println("生成客户端文件 : " + targetFile.getCanonicalPath());
		} else 
		{
            File targetFile = new File(fileName + ".cs");
			Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8");
			_fileWriter.write(_readWriter.toString());
			_fileWriter.close();

            System.out.println("生成客户端文件 : " + targetFile.getCanonicalPath());
		}
	}
	/**
	 * 获取物理路径
	 * 
	 * @param fileName
	 * @return 
	 * @throws IllegalArgumentException if fileName == null or Empty 
	 * 
	 */
	private static String getPhysicalPath(String fileName)
	{
		if (fileName == null || 
			fileName.isEmpty())
		{
			throw new IllegalArgumentException("fileName is null");
		}

		// 获取类加载器
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// 获取文件 URL
		URL url = cl.getResource(fileName);
		
		if (url == null)
		{
			// 抛出运行时异常
			throw new RuntimeException("文件不存在 : " + fileName);
		} else
		{
			// 获取文件完整路径
			return url.getPath();
		}
	}

	/**
	 * 解析生成字段的anotation
	 * 
	 * @param str
	 * @return
	 */
	private static List<String> buildAnotation(String str, String type) 
	{
		List<String> _anotations = new ArrayList<String>();

		if (str == null || str.equals("")) 
		{
			str = "cell";
		}

		if (str.contains("nottranslate"))
		{
			str = str + ";cell";
		}

		String[] _arr = str.split(";");
		int _len = _arr.length;

		for (int i = 0; i < _len; i++) 
		{
			String tmp = _arr[i].toLowerCase();
			Matcher _matcher = ANOTATION_COLLECTION.matcher(tmp);

			if (_matcher.matches()) 
			{
				types.add("com.core.template.ExcelCollectionMapping");
				int _g = Integer.parseInt(_matcher.group(1));
				int _p = Integer.parseInt(_matcher.group(2));
				StringBuilder sb = new StringBuilder("@ExcelCollectionMapping(clazz = ");
				sb.append(type);
				sb.append(".class, collectionNumber = \"");
				for (int j = 0; j < _g; j++)
				{
					for (int k = 0; k < _p; k++) 
					{
						sb.append(lineNumber);
						if (k != _p - 1)
						{
							sb.append(",");
						}
						lineNumber++;
					}
					if (j != _g - 1) 
					{
						sb.append(";");
					}
				}
				sb.append("\")");
				_anotations.add(sb.toString());
				continue;
			}

			_matcher = ANOTATION_CELLBINDING.matcher(tmp);
			if (_matcher.matches())
			{
				StringBuilder sb = new StringBuilder("@ExcelCellBinding(offset = ");
				sb.append(lineNumber);
				sb.append(")");
				_anotations.add(sb.toString());
				lineNumber++;
				types.add("com.core.annotation.ExcelCellBinding");
				continue;
			}

			_matcher = ANOTATION_ROWBINDING.matcher(tmp);
			if (_matcher.matches()) 
			{
				int _line = Integer.parseInt(_matcher.group(1));
				_anotations.add("@ExcelRowBinding");
				lineNumber = lineNumber + _line;
				continue;
			}

			_matcher = ANOTATION_NOTTRANSLATE.matcher(tmp);
			if (_matcher.matches())
			{
				types.add("com.core.annotation.NotTranslate");
				_anotations.add("@NotTranslate");
				continue;
			}
		}
		return _anotations;
	}

	/**
	 * 解析字段类型
	 * 
	 * @param type
	 * @return
	 */
	private static String parseFieldType(String type) 
	{
		Pattern _map = Pattern.compile("Map<[a-zA-Z0-9]+,([^\\s]+)>");
		Pattern _list = Pattern.compile("List<([^\\s]+)>");
		Pattern _set = Pattern.compile("Set<([^\\s]+)>");
		Pattern _array = Pattern.compile("([a-zA-Z]+)\\[\\]");

		// Map类型的
		Matcher _matcher = _map.matcher(type);
		if (_matcher.matches()) 
		{
			types.add("java.util.Map");
			return _matcher.group(1);
		}

		// LIST
		_matcher = _list.matcher(type);
		if (_matcher.matches())
		{
			types.add("java.util.List");
			return _matcher.group(1);
		}

		// SET
		_matcher = _set.matcher(type);
		if (_matcher.matches()) 
		{
			types.add("java.util.Set");
			return _matcher.group(1);
		}

		// ARRAY
		_matcher = _array.matcher(type);
		if (_matcher.matches()) 
		{
			return _matcher.group(1);
		}

		return type;
	}

	private static final ExcelFieldObject buildFieldObject(String type,String name, String condition, String comment, int startLine)
	{
		ExcelFieldObject _f = null;

		Pattern _minValue = Pattern.compile("minvalue=(\\d+)");
		Pattern _maxValue = Pattern.compile("maxvalue=(\\d+)");
		Pattern _x = Pattern.compile("x=([^\\s]+)");
		Pattern _y = Pattern.compile("y=([^\\s]+)");
		Pattern _anot = Pattern
				.compile("(collection[^\\s]+)|(object[^\\s]+)|(nottranslate)|(cell)");
		Pattern _notNull = Pattern.compile("notnull=([^\\s]+)");
		Pattern _maxLen = Pattern.compile("maxlen=(\\d+)");
		Pattern _minLen = Pattern.compile("minlen=(\\d+)");

		StringBuilder anot = new StringBuilder();
		boolean x = false;
		boolean y = false;
		int maxValue = -1;
		int minValue = -1;
		boolean notNull = true;
		int minLen = -1;
		int maxLen = -1;

		if (condition == null) 
		{
			condition = "";
		}

		String[] _conds = condition.split(";");

		for (String _c : _conds) 
		{
			_c = _c.toLowerCase();
			Matcher _matcher = _anot.matcher(_c);

			if (_matcher.matches())
			{
				if (!anot.toString().equals("")) 
				{
					anot.append(";");
				}
				anot.append(_c);
				continue;
			}

			_matcher = _minValue.matcher(_c);
			if (_matcher.matches())
			{
				minValue = Integer.parseInt(_matcher.group(1));
				continue;
			}

			_matcher = _maxValue.matcher(_c);
			if (_matcher.matches()) 
			{
				maxValue = Integer.parseInt(_matcher.group(1));
				continue;
			}

			_matcher = _x.matcher(_c);
			if (_matcher.matches()) 
			{
				x = Boolean.parseBoolean(_matcher.group(1));
				if (x)
				{
					types.add("com.common.exception.TemplateConfigException");
				}
				continue;
			}

			_matcher = _y.matcher(_c);
			if (_matcher.matches()) 
			{
				y = Boolean.parseBoolean(_matcher.group(1));
				if (y) 
				{
					types.add("com.common.exception.TemplateConfigException");
				}
				continue;
			}

			_matcher = _notNull.matcher(_c);
			if (_matcher.matches())
			{
				notNull = Boolean.parseBoolean(_matcher.group(1));
				continue;
			}

			_matcher = _maxLen.matcher(_c);
			if (_matcher.matches())
			{
				maxLen = Integer.parseInt(_matcher.group(1));
				continue;
			}

			_matcher = _minLen.matcher(_c);
			if (_matcher.matches())
			{
				minLen = Integer.parseInt(_matcher.group(1));
				continue;
			}
		}

		if (notNull)
		{
			types.add("com.common.exception.TemplateConfigException");
		}

		if (notNull && type.equals("String"))
		{
			types.add("com.core.util.StringUtils");
		}

		if (!notNull && ( // 如果是非对象类型数据, 且 notNull=false
			type.equals("int") || 
			type.equals("float") || 
			type.equals("long") || 
			type.equals("short"))) 
		{
			types.add("com.common.exception.TemplateConfigException");
		}

		String _fieldType = parseFieldType(type);
		List<String> _anotations = buildAnotation(anot.toString(), _fieldType);

		_f = new ExcelFieldObject(type, name, _anotations, comment, x, y,
				maxValue, minValue, notNull, startLine, maxLen, minLen);

		return _f;
	}

	private static String getPath(String pkgName)
	{
		if (pkgName.contains("gs"))
		{
			return path_gs;
		}

		if (pkgName.contains("core"))
		{
			return path_core;
		}
		if (pkgName.contains("cs"))
		{
			return path_cs;
		}
		

		throw new RuntimeException("您在model_template_gen.config中配置了错误的包名："+ pkgName);
	}

}
