package com.gameserver.club.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * UnlockLevelTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class UnlockLevelTemplateVO extends TemplateObject {

	/** 俱乐部解锁 */
	@ExcelCellBinding(offset = 1)
	protected int clubunlock;

	/** 收集解锁 */
	@ExcelCellBinding(offset = 2)
	protected int collection;


	public int getClubunlock() {
		return this.clubunlock;
	}



	public void setClubunlock(int clubunlock) {
		this.clubunlock = clubunlock;
	}
	
	public int getCollection() {
		return this.collection;
	}



	public void setCollection(int collection) {
		this.collection = collection;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, UnlockLevelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends UnlockLevelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, UnlockLevelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "UnlockLevelTemplateVO [  clubunlock=" + clubunlock + ", collection=" + collection + ",]";
	}
}