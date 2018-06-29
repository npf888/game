package com.core.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.common.TemplateLevelRange;
import com.common.constants.Loggers;
import com.common.exception.ConfigException;
import com.core.encrypt.XorDecryptedInputStream;
import com.core.util.JdomUtils;
import com.core.util.StringUtils;


/**
 * 模板数据管理器，在服务器启动时加载所有excel模板数据
 * 
 * 
 */
public class TemplateService implements ITemplateService {

	/** 所有通过模板文件转换而成的模板对象的实例 */
	private Map<Class<?>, Map<Integer, TemplateObject>> templateObjects;

	private List<TemplateConfig> templateConfigs;
	private TemplateFileParser objectsParser;
	private String resourceFolder;
	private boolean isXorLoad;

	public TemplateService(String resourceFolder) {
		this(resourceFolder, true);
	}

	public TemplateService(String resourceFolder, boolean isXorLoad) {
		this.resourceFolder = resourceFolder;
		this.isXorLoad = isXorLoad;
	}

	@Override
	public void init(URL cfgPath) {
		this.loadConfig(cfgPath);
		templateObjects = new HashMap<Class<?>, Map<Integer, TemplateObject>>();
		objectsParser = new TemplateFileParser();
		InputStream is = null;
		String fileName = null;
		for (TemplateConfig cfg : templateConfigs) {
			/*if(cfg.getFileName().equals("TexasRoom.xls")){
				int ii = 9;
			}*/
			Loggers.templateLogger.info(cfg.toString());
			try {
				fileName = cfg.getFileName();
				if (fileName == null) {// 没有配置文件名可能是parser内部自己处理
					this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
							templateObjects, null);
					continue;
				}
				Loggers.templateLogger.info("loading template " + fileName);
				String xlsPath = resourceFolder + File.separator
						+ cfg.getFileName();
				if (!isXorLoad) {
					is = new FileInputStream(xlsPath);
				} else {
					is = new XorDecryptedInputStream(xlsPath);
				}
				this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),templateObjects, is);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConfigException(
						"Errors occurs while parsing xls file:" + fileName, e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		patchUpAndCheck();
	}

	/**
	 * 重新加载xls文件数据
	 * 
	 * @param cfgPath
	 * @deprecated
	 */
	public <T extends TemplateObject> void reLoad(String fileName,Class<T> clazz)
	{
		if(templateObjects==null) return;
		removeAll(clazz);
		InputStream is = null;
		Class<?>[] fileSheetClasses = new Class<?>[1];
		fileSheetClasses[0]=clazz;
		TemplateConfig cfg = new TemplateConfig(fileName,fileSheetClasses);
			Loggers.templateLogger.info(cfg.toString());
			try {
				String xlsPath = resourceFolder + File.separator
				+ cfg.getFileName();
				if (!isXorLoad) {
					is = new FileInputStream(xlsPath);
				} else {
					is = new XorDecryptedInputStream(xlsPath);
				}
				this.getTemplateParser(cfg).parseXlsFile(cfg.getClasses(),
						templateObjects, is);
				is.close();
			} catch (Exception e) {
				throw new ConfigException(
						"Errors occurs while parsing xls file:" + cfg.getFileName(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}
	
	/**
	 * 加载配置文件
	 * 
	 * @param cfgPath
	 */
	@SuppressWarnings("unchecked")
	private void loadConfig(URL cfgPath) {
		Element root = JdomUtils.getRootElemet(cfgPath);
		templateConfigs = new ArrayList<TemplateConfig>();
		// templateFiles = new TreeMap<String, Class<?>[]>();
		List<Element> fileElements = root.getChildren();
		for (Element fileElement : fileElements) {
			String fileName = fileElement.getAttributeValue("name");
			String parserClassName = fileElement.getAttributeValue("parser");
			List<Element> sheetElements = fileElement.getChildren();
			Class<?>[] fileSheetClasses = new Class<?>[sheetElements.size()];
			for (int i = 0; i < sheetElements.size(); i++) {
				Element sheet = sheetElements.get(i);
				String className = sheet.getAttributeValue("class");
				if (StringUtils.isEmpty(className)) {
					fileSheetClasses[i] = null;
					continue;
				}
				try {
					Class<?> clazz = Class.forName(className);
					fileSheetClasses[i] = clazz;
				} catch (ClassNotFoundException e) {
					Loggers.templateLogger.error("", e);
					throw new ConfigException(e);
				}
			}
			TemplateConfig templateConfig = new TemplateConfig(fileName,
					fileSheetClasses);
			if (parserClassName != null
					&& (parserClassName = parserClassName.trim()).length() > 0) {
				templateConfig.setParserClassName(parserClassName);
			}
			templateConfigs.add(templateConfig);
		}
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> T get(int id, Class<T> clazz) {
		Map<Integer, TemplateObject> map = templateObjects.get(clazz);
		return (T) map.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> Map<Integer, T> getAll(Class<T> clazz) {
		return (Map<Integer, T>) templateObjects.get(clazz);
	}

	/**
	 * 返回所有类别的template列表
	 * 
	 * @return
	 */
	public Map<Class<?>, Map<Integer, TemplateObject>> getAllClassTemplateMaps() {
		return Collections.unmodifiableMap(templateObjects);
	}

	@Override
	@Deprecated
	public <T extends TemplateObject> void add(T t) {
		Map<Integer, TemplateObject> objs = templateObjects.get(t.getClass());
		if (objs == null || objs.containsKey(t.getId())) {
			return;
		}
		objs.put(t.getId(), t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends TemplateObject> Map<Integer, T> removeAll(Class<T> clazz) {
		return (Map<Integer, T>) templateObjects.remove(clazz);
	}

	@Override
	public <T extends TemplateObject> boolean isTemplateExist(int id,
			Class<T> clazz) {
		Map<Integer, TemplateObject> map = templateObjects.get(clazz);
		if (null != map) {
			return null == map.get(id) ? false : true;
		}
		return false;
	}

	/**
	 * 重加载数据
	 * 
	 * @param fileName Excel 文件名称
	 * @param sheetIndex 工作表单索引
	 * @param needCheck 是否需要检查数据
	 * @return
	 * @deprecated
	 */
	public boolean reload(String fileName, int sheetIndex, boolean needCheck) {
		if (templateConfigs != null) {
			for (TemplateConfig cfg : templateConfigs) {
				if (fileName.equals(cfg.getFileName())) {
					Loggers.templateLogger.info(cfg.toString());
					String xlsPath = resourceFolder + File.separator + fileName;
					TemplateFileParser tmplFileParser = this.getTemplateParser(cfg);
					if(cfg.getClasses().length == 0)
					{
						try {
							InputStream is = null;
							if (isXorLoad) {
								is = new FileInputStream(xlsPath);
							} else {
								is = new XorDecryptedInputStream(xlsPath);
							}
							tmplFileParser.parseXlsFile(null,templateObjects, is);
						}catch (Exception e) {
							throw new ConfigException(
									"Errors occurs while parsing xls file:" + fileName, e);
						} 
					}
					else
					{
						tmplFileParser.parseXlsFile(xlsPath, sheetIndex,
								cfg.getClasses()[sheetIndex], templateObjects);
					}

					if (needCheck) {
						patchUpAndCheck();
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 进行合法性校验，并构建模板间对象依赖关系
	 */
	private void patchUpAndCheck() {
		boolean hasError = false;
		Collection<Map<Integer, TemplateObject>> tplObjMaps = templateObjects
				.values();
		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps) {
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates) {
				try {
					templateObject.patchUp();
				} catch (Exception e) {
					Loggers.gameLogger.error("[excel数据整合]", e);
					hasError = true;
				}
			}
		}
		for (Map<Integer, TemplateObject> tplObjMap : tplObjMaps) {
			Collection<TemplateObject> templates = tplObjMap.values();
			for (TemplateObject templateObject : templates) {
				try {
					templateObject.check();
				} catch (Exception e) {
					Loggers.gameLogger.error("[excel启动检查]", e);
					hasError = true;
				}
			}
		}
		// sheet范围的全局检查
		sheetCheck();
		if (hasError) {
			System.exit(1);
		}
	}
	
	private void sheetCheck() {
		LevelRangeValidater validater = new LevelRangeValidater();
		for (Map.Entry<Class<?>, Map<Integer, TemplateObject>> entry : templateObjects
				.entrySet()) {
			Class<?> entryClass = entry.getKey();
			if (!isTemplateLevelRange(entryClass)) {
				continue;
			}
			List<TemplateLevelRange> levelRangeList = new ArrayList<TemplateLevelRange>();
			for (TemplateObject object : entry.getValue().values()) {
				levelRangeList.add((TemplateLevelRange) object);
			}
			validater.checkLevelRanges(levelRangeList);
		}
	}
	
	private boolean isTemplateLevelRange(Class<?> clazz) {
		for (Class<?> interf : clazz.getInterfaces()) {
			if (interf == TemplateLevelRange.class) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据配置取得解析器
	 * 
	 * @param cfg
	 * @return
	 */
	private TemplateFileParser getTemplateParser(TemplateConfig cfg) {
		if (cfg.getParserClassName() != null
				&& cfg.getParserClassName().length() > 0) {
			// 使用指定的解析器
			try {
				Class<?> clazz = Class.forName(cfg.getParserClassName());
				Constructor<?> constructor = clazz.getConstructor();
				return (TemplateFileParser) constructor.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			// 默认的解析器
			return objectsParser;
		}
	}

	/**
	 * 获取模版配置
	 * 
	 * @return
	 */
	public List<TemplateConfig> getTemplateCfgs() {
		return this.templateConfigs;
	}
	
	//查找最适合玩家的场次
}
