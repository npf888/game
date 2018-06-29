package com.gameserver.activity.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * WeeklyMonthlyPackTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WeeklyMonthlyPackTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 礼包类型，0表示周礼包，1表示月礼包 */
	@ExcelCellBinding(offset = 2)
	protected int packType;

	/** VIP等级 */
	@ExcelCellBinding(offset = 3)
	protected int vipLevel;

	/** 礼包持续时间，单位：秒 */
	@ExcelCellBinding(offset = 4)
	protected int packDuration;

	/** 礼包刷新时间 */
	@ExcelCellBinding(offset = 5)
	protected int packCycle;

	/** Recharge表对应的pid */
	@ExcelCellBinding(offset = 6)
	protected int pid;

	/** 购买弹窗显示： 额外获得的百分比（如：配置200，显示200% MORE） */
	@ExcelCellBinding(offset = 7)
	protected int extraPercentage;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public int getPackType() {
		return this.packType;
	}


	public final static int getPackTypeMinLimit() {
		return 0;
	}

	public void setPackType(int packType) {
		if (packType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[礼包类型，0表示周礼包，1表示月礼包]packType的值不得小于0");
		}
		this.packType = packType;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}


	public final static int getVipLevelMinLimit() {
		return 0;
	}

	public void setVipLevel(int vipLevel) {
		if (vipLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[VIP等级]vipLevel的值不得小于0");
		}
		this.vipLevel = vipLevel;
	}
	
	public int getPackDuration() {
		return this.packDuration;
	}


	public final static int getPackDurationMinLimit() {
		return 0;
	}

	public void setPackDuration(int packDuration) {
		if (packDuration < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[礼包持续时间，单位：秒]packDuration的值不得小于0");
		}
		this.packDuration = packDuration;
	}
	
	public int getPackCycle() {
		return this.packCycle;
	}


	public final static int getPackCycleMinLimit() {
		return 0;
	}

	public void setPackCycle(int packCycle) {
		if (packCycle < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[礼包刷新时间]packCycle的值不得小于0");
		}
		this.packCycle = packCycle;
	}
	
	public int getPid() {
		return this.pid;
	}


	public final static int getPidMinLimit() {
		return 0;
	}

	public void setPid(int pid) {
		if (pid < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[Recharge表对应的pid]pid的值不得小于0");
		}
		this.pid = pid;
	}
	
	public int getExtraPercentage() {
		return this.extraPercentage;
	}


	public final static int getExtraPercentageMinLimit() {
		return 0;
	}

	public void setExtraPercentage(int extraPercentage) {
		if (extraPercentage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[购买弹窗显示： 额外获得的百分比（如：配置200，显示200% MORE）]extraPercentage的值不得小于0");
		}
		this.extraPercentage = extraPercentage;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WeeklyMonthlyPackTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WeeklyMonthlyPackTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WeeklyMonthlyPackTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WeeklyMonthlyPackTemplateVO [  langDesc=" + langDesc + ", packType=" + packType + ", vipLevel=" + vipLevel + ", packDuration=" + packDuration + ", packCycle=" + packCycle + ", pid=" + pid + ", extraPercentage=" + extraPercentage + ",]";
	}
}