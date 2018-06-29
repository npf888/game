package com.gameserver.common.i18n;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.core.i18n.I18NDictionary;
import com.core.i18n.SysLangService;
import com.core.i18n.impl.SysLangServiceImpl;
import com.core.template.TemplateService;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.i18n.I18NReloadable.Parameter;
import com.gameserver.common.i18n.I18NReloadable.Result;

/**
 * 多语言管理
 * @author Thinker
 */
public class LangServiceImpl implements LangService
{
	/** 系统内部的多语言配置 */
	public static final String SYSLANGS = "syslangs";
	/** 过滤词配置 */
	public static final String DIRTYWORDS = "dirtywords";
	
	/** 系统多语言词典 */
	private SysLangService sysDict;
	/** excel多语言词典 */
	private I18NDictionary<String, String> excelDict;
	/** 配置 */
	private final Map<String, String> configs = new HashMap<String, String>();
	/** 全局名字查找表 */
	private NameLookupTable nameLUT;

	/**
	 * 
	 * @param configs
	 */
	public LangServiceImpl(Map<String, String> configs, String charset)
	{
		sysDict = new SysLangServiceImpl(configs.get(SYSLANGS));
		sysDict.init();
		sysDict.check();
		
		this.configs.putAll(configs);
		this.nameLUT = new NameLookupTable();
	}

	@Override
	public String readSysLang(Integer key, Object... params) {
		return this.sysDict.read(key, params);
	}

	@Override
	public boolean afterReload(IResult result) {
		if (result == null) {
			return false;
		}
		I18NReloadable.Result _result = (Result) result;
		if (SYSLANGS.equals(_result.param.langName)) {
			// 替换多语言
			this.sysDict = (SysLangService) _result.dict;
			return true;
		}

		return false;
	}

	@Override
	public IResult reload(IParameter parameter) {
		I18NReloadable.Parameter _param = (Parameter) parameter;
		if (SYSLANGS.equals(_param.langName)) {
			// 加载sys的多语言
			SysLangService _reload = new SysLangServiceImpl(this.configs
					.get(SYSLANGS));
			return new I18NReloadable.Result(_reload, _param);
		}
		return null;
	}

	/**
	 * 构建LangManager实现
	 * 
	 * @param gameServerConfig
	 * @return
	 */
	public static LangService buildLangService(GameServerConfig gameServerConfig) {
		final String langBasePath = gameServerConfig.getResourceFullPath(
				gameServerConfig.getI18nDir(), gameServerConfig.getLanguage());
		Map<String, String> langConfigs = new HashMap<String, String>();
		langConfigs.put(LangServiceImpl.SYSLANGS, langBasePath + File.separator
				+ "sys_lang.xls");
		langConfigs.put(LangServiceImpl.DIRTYWORDS, langBasePath + File.separator
				+ "dirtywords.xls");
		return new LangServiceImpl(langConfigs, gameServerConfig.getCharset());
	}

	@Override
	public String readExcelLang(String key) {
		if(excelDict != null)
		{
			return excelDict.read(key);
		}else{
			return null;
		}
	}

	@Override
	public String readSysLang(Integer key) {
		return sysDict.read(key);
	}

	@Override
	public NameLookupTable getNameLookupTable() {
		return nameLUT;
	}




	
	/**
	 * 检查名称的存在性
	 */
	private void check(TemplateService templateService) {

	}


	@Override
	public void onAllExcelLangRead() {
		// 销毁excel多语言词典
		excelDict = null;
	}

	@Override
	public String readSysLang(Integer key, Object param) {
		return this.sysDict.read(key, new Object[] { param });
	}

	@Override
	public String readSysLang(Integer key, Object param1, Object param2) {
		return this.sysDict.read(key, new Object[] { param1, param2 });
	}

	@Override
	public SysLangService getSysLangSerivce() {
		return this.sysDict;
	}

	@Override
	public void initNameLookupTable(TemplateService templateManager) {
		// TODO Auto-generated method stub
		
	}
}
