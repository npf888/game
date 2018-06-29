package com.gameserver.worldboss.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BossPropertyTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BossPropertyTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** icon */
	@ExcelCellBinding(offset = 3)
	protected String icon;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 4)
	protected String langDesc;

	/** 1.（airtime）秒内，所有wild连线伤害降低百分之（） 2.恢复自身最大血量的百分之（） 3.（airtime）秒内，收到伤害降低百分之（） 4.（airtime）秒内，有（）概率免疫伤害。 */
	@ExcelCellBinding(offset = 5)
	protected int type;

	/** 技能开始后持续时间（秒） */
	@ExcelCellBinding(offset = 6)
	protected int airtime;

	/** wild中奖连线伤害降低百分数。（除以100） */
	@ExcelCellBinding(offset = 7)
	protected int wildreduce;

	/** 恢复最大血量百分数（除以100） */
	@ExcelCellBinding(offset = 8)
	protected int recover1;

	/** 伤害减免百分数 */
	@ExcelCellBinding(offset = 9)
	protected int damagereduce;

	/** 免伤概率，百分数（除以100） */
	@ExcelCellBinding(offset = 10)
	protected int avoid;

	/** 基础血量 */
	@ExcelCellBinding(offset = 11)
	protected int blood;

	/** 击杀成功后：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	@ExcelCellBinding(offset = 12)
	protected int rewardNum1;

	/** 未击杀成功：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	@ExcelCellBinding(offset = 13)
	protected int rewardNum2;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}



	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getAirtime() {
		return this.airtime;
	}



	public void setAirtime(int airtime) {
		this.airtime = airtime;
	}
	
	public int getWildreduce() {
		return this.wildreduce;
	}



	public void setWildreduce(int wildreduce) {
		this.wildreduce = wildreduce;
	}
	
	public int getRecover1() {
		return this.recover1;
	}



	public void setRecover1(int recover1) {
		this.recover1 = recover1;
	}
	
	public int getDamagereduce() {
		return this.damagereduce;
	}



	public void setDamagereduce(int damagereduce) {
		this.damagereduce = damagereduce;
	}
	
	public int getAvoid() {
		return this.avoid;
	}



	public void setAvoid(int avoid) {
		this.avoid = avoid;
	}
	
	public int getBlood() {
		return this.blood;
	}



	public void setBlood(int blood) {
		this.blood = blood;
	}
	
	public int getRewardNum1() {
		return this.rewardNum1;
	}



	public void setRewardNum1(int rewardNum1) {
		this.rewardNum1 = rewardNum1;
	}
	
	public int getRewardNum2() {
		return this.rewardNum2;
	}



	public void setRewardNum2(int rewardNum2) {
		this.rewardNum2 = rewardNum2;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BossPropertyTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BossPropertyTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BossPropertyTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BossPropertyTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", icon=" + icon + ", langDesc=" + langDesc + ", type=" + type + ", airtime=" + airtime + ", wildreduce=" + wildreduce + ", recover1=" + recover1 + ", damagereduce=" + damagereduce + ", avoid=" + avoid + ", blood=" + blood + ", rewardNum1=" + rewardNum1 + ", rewardNum2=" + rewardNum2 + ",]";
	}
}