package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsHallowmasRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsHallowmasRewardTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 3)
	protected int slotsNum;

	/** 关卡 0为初始 */
	@ExcelCellBinding(offset = 4)
	protected int checkpoint;

	/** 每关出现的鬼魂个数 */
	@ExcelCellBinding(offset = 5)
	protected int numberGhosts;

	/** 每关出现的小孩个数 */
	@ExcelCellBinding(offset = 6)
	protected int numberChildren;

	/** 有效击打时间;秒 */
	@ExcelCellBinding(offset = 7)
	protected int validTime;

	/** 打中鬼的次数 */
	@ExcelCellBinding(offset = 8)
	protected int hitTheGhost;

	/** 保底奖金： 0;有 1;无 */
	@ExcelCellBinding(offset = 9)
	protected int guaranteed;

	/** 奖励 */
	@ExcelCellBinding(offset = 10)
	protected int reward;


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
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getCheckpoint() {
		return this.checkpoint;
	}


	public final static int getCheckpointMinLimit() {
		return 0;
	}

	public void setCheckpoint(int checkpoint) {
		if (checkpoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[关卡 0为初始]checkpoint的值不得小于0");
		}
		this.checkpoint = checkpoint;
	}
	
	public int getNumberGhosts() {
		return this.numberGhosts;
	}


	public final static int getNumberGhostsMinLimit() {
		return 0;
	}

	public void setNumberGhosts(int numberGhosts) {
		if (numberGhosts < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[每关出现的鬼魂个数]numberGhosts的值不得小于0");
		}
		this.numberGhosts = numberGhosts;
	}
	
	public int getNumberChildren() {
		return this.numberChildren;
	}


	public final static int getNumberChildrenMinLimit() {
		return 0;
	}

	public void setNumberChildren(int numberChildren) {
		if (numberChildren < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[每关出现的小孩个数]numberChildren的值不得小于0");
		}
		this.numberChildren = numberChildren;
	}
	
	public int getValidTime() {
		return this.validTime;
	}


	public final static int getValidTimeMinLimit() {
		return 0;
	}

	public void setValidTime(int validTime) {
		if (validTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[有效击打时间;秒]validTime的值不得小于0");
		}
		this.validTime = validTime;
	}
	
	public int getHitTheGhost() {
		return this.hitTheGhost;
	}


	public final static int getHitTheGhostMinLimit() {
		return 0;
	}

	public void setHitTheGhost(int hitTheGhost) {
		if (hitTheGhost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[打中鬼的次数]hitTheGhost的值不得小于0");
		}
		this.hitTheGhost = hitTheGhost;
	}
	
	public int getGuaranteed() {
		return this.guaranteed;
	}


	public final static int getGuaranteedMinLimit() {
		return 0;
	}

	public void setGuaranteed(int guaranteed) {
		if (guaranteed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[保底奖金： 0;有 1;无]guaranteed的值不得小于0");
		}
		this.guaranteed = guaranteed;
	}
	
	public int getReward() {
		return this.reward;
	}


	public final static int getRewardMinLimit() {
		return 0;
	}

	public void setReward(int reward) {
		if (reward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[奖励]reward的值不得小于0");
		}
		this.reward = reward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsHallowmasRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsHallowmasRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsHallowmasRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsHallowmasRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", slotsNum=" + slotsNum + ", checkpoint=" + checkpoint + ", numberGhosts=" + numberGhosts + ", numberChildren=" + numberChildren + ", validTime=" + validTime + ", hitTheGhost=" + hitTheGhost + ", guaranteed=" + guaranteed + ", reward=" + reward + ",]";
	}
}