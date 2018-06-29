package com.gameserver.worldboss.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BossTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BossTemplateVO extends TemplateObject {

	/** 每天boss 开始的时间 */
	@ExcelCellBinding(offset = 1)
	protected String starttime;

	/** 持续时间 分钟 */
	@ExcelCellBinding(offset = 2)
	protected int continuetime;

	/** 击杀增长血量，百分比（除以100） */
	@ExcelCellBinding(offset = 3)
	protected int increase;

	/** 未击杀减少量，百分比（除以100） */
	@ExcelCellBinding(offset = 4)
	protected int reduce;

	/** 技能触发时间（秒） */
	@ExcelCellBinding(offset = 5)
	protected int skillstart;

	/** 伤害加成百分数 */
	@ExcelCellBinding(offset = 6)
	protected int vip0;

	/** 伤害加成百分数 */
	@ExcelCellBinding(offset = 7)
	protected int vip1;

	/** 伤害加成百分数 */
	@ExcelCellBinding(offset = 8)
	protected int vip2;

	/** 伤害加成百分数 */
	@ExcelCellBinding(offset = 9)
	protected int vip3;

	/** 伤害加成百分数 */
	@ExcelCellBinding(offset = 10)
	protected int vip4;

	/** 伤害加成，百分数 */
	@ExcelCellBinding(offset = 11)
	protected int vip5;

	/** big中大奖伤害加成 */
	@ExcelCellBinding(offset = 12)
	protected int bigwin;

	/** mega中大奖伤害加成 */
	@ExcelCellBinding(offset = 13)
	protected int megawin;

	/** super中大奖伤害加成 */
	@ExcelCellBinding(offset = 14)
	protected int superwin;

	/** epic中大奖伤害加成 */
	@ExcelCellBinding(offset = 15)
	protected int epicwin;

	/** 开关 0.开启 1.关闭 */
	@ExcelCellBinding(offset = 16)
	protected int onoff;

	/** 最后一击奖励物品ID */
	@ExcelCellBinding(offset = 17)
	protected int rewardlastID;

	/** 最后一击奖励物品数量 */
	@ExcelCellBinding(offset = 18)
	protected int rewardNum;


	public String getStarttime() {
		return this.starttime;
	}



	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	public int getContinuetime() {
		return this.continuetime;
	}



	public void setContinuetime(int continuetime) {
		this.continuetime = continuetime;
	}
	
	public int getIncrease() {
		return this.increase;
	}



	public void setIncrease(int increase) {
		this.increase = increase;
	}
	
	public int getReduce() {
		return this.reduce;
	}



	public void setReduce(int reduce) {
		this.reduce = reduce;
	}
	
	public int getSkillstart() {
		return this.skillstart;
	}



	public void setSkillstart(int skillstart) {
		this.skillstart = skillstart;
	}
	
	public int getVip0() {
		return this.vip0;
	}



	public void setVip0(int vip0) {
		this.vip0 = vip0;
	}
	
	public int getVip1() {
		return this.vip1;
	}



	public void setVip1(int vip1) {
		this.vip1 = vip1;
	}
	
	public int getVip2() {
		return this.vip2;
	}



	public void setVip2(int vip2) {
		this.vip2 = vip2;
	}
	
	public int getVip3() {
		return this.vip3;
	}



	public void setVip3(int vip3) {
		this.vip3 = vip3;
	}
	
	public int getVip4() {
		return this.vip4;
	}



	public void setVip4(int vip4) {
		this.vip4 = vip4;
	}
	
	public int getVip5() {
		return this.vip5;
	}



	public void setVip5(int vip5) {
		this.vip5 = vip5;
	}
	
	public int getBigwin() {
		return this.bigwin;
	}



	public void setBigwin(int bigwin) {
		this.bigwin = bigwin;
	}
	
	public int getMegawin() {
		return this.megawin;
	}



	public void setMegawin(int megawin) {
		this.megawin = megawin;
	}
	
	public int getSuperwin() {
		return this.superwin;
	}



	public void setSuperwin(int superwin) {
		this.superwin = superwin;
	}
	
	public int getEpicwin() {
		return this.epicwin;
	}



	public void setEpicwin(int epicwin) {
		this.epicwin = epicwin;
	}
	
	public int getOnoff() {
		return this.onoff;
	}



	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}
	
	public int getRewardlastID() {
		return this.rewardlastID;
	}



	public void setRewardlastID(int rewardlastID) {
		this.rewardlastID = rewardlastID;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}



	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BossTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BossTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BossTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BossTemplateVO [  starttime=" + starttime + ", continuetime=" + continuetime + ", increase=" + increase + ", reduce=" + reduce + ", skillstart=" + skillstart + ", vip0=" + vip0 + ", vip1=" + vip1 + ", vip2=" + vip2 + ", vip3=" + vip3 + ", vip4=" + vip4 + ", vip5=" + vip5 + ", bigwin=" + bigwin + ", megawin=" + megawin + ", superwin=" + superwin + ", epicwin=" + epicwin + ", onoff=" + onoff + ", rewardlastID=" + rewardlastID + ", rewardNum=" + rewardNum + ",]";
	}
}