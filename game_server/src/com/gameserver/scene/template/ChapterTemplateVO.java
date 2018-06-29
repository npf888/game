package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 剧情章节表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ChapterTemplateVO extends TemplateObject {

	/** 剧情ID */
	@ExcelCellBinding(offset = 1)
	protected int storyID;

	/** 章节描述 */
	@ExcelCellBinding(offset = 2)
	protected String description;

	/** 章节类型 */
	@ExcelCellBinding(offset = 3)
	protected int chapterType;

	/** 章节参数A */
	@ExcelCellBinding(offset = 4)
	protected int paramA;

	/** 章节参数B */
	@ExcelCellBinding(offset = 5)
	protected int paramB;


	public int getStoryID() {
		return this.storyID;
	}



	public void setStoryID(int storyID) {
		this.storyID = storyID;
	}
	
	public String getDescription() {
		return this.description;
	}



	public void setDescription(String description) {
		if (StringUtils.isEmpty(description)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[章节描述]description不可以为空");
		}
		this.description = description;
	}
	
	public int getChapterType() {
		return this.chapterType;
	}



	public void setChapterType(int chapterType) {
		this.chapterType = chapterType;
	}
	
	public int getParamA() {
		return this.paramA;
	}



	public void setParamA(int paramA) {
		this.paramA = paramA;
	}
	
	public int getParamB() {
		return this.paramB;
	}



	public void setParamB(int paramB) {
		this.paramB = paramB;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ChapterTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ChapterTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ChapterTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ChapterTemplateVO [  storyID=" + storyID + ", description=" + description + ", chapterType=" + chapterType + ", paramA=" + paramA + ", paramB=" + paramB + ",]";
	}
}