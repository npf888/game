package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * reel
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ReelTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 老虎机 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** 位置 */
	@ExcelCellBinding(offset = 6)
	protected int turn;

	/** 卷轴1 */
	@ExcelCellBinding(offset = 7)
	protected int reel1;

	/** 卷轴2 */
	@ExcelCellBinding(offset = 8)
	protected int reel2;

	/** 卷轴3 */
	@ExcelCellBinding(offset = 9)
	protected int reel3;

	/** 卷轴4 */
	@ExcelCellBinding(offset = 10)
	protected int reel4;

	/** 卷轴5 */
	@ExcelCellBinding(offset = 11)
	protected int reel5;

	/** 等级下限 */
	@ExcelCellBinding(offset = 12)
	protected int levelDown;

	/** 等级上限 */
	@ExcelCellBinding(offset = 13)
	protected int levelUp;


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
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
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
					6, "[老虎机]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getTurn() {
		return this.turn;
	}


	public final static int getTurnMinLimit() {
		return 0;
	}

	public void setTurn(int turn) {
		if (turn < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[位置]turn的值不得小于0");
		}
		this.turn = turn;
	}
	
	public int getReel1() {
		return this.reel1;
	}


	public final static int getReel1MinLimit() {
		return 0;
	}

	public void setReel1(int reel1) {
		if (reel1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[卷轴1]reel1的值不得小于0");
		}
		this.reel1 = reel1;
	}
	
	public int getReel2() {
		return this.reel2;
	}


	public final static int getReel2MinLimit() {
		return 0;
	}

	public void setReel2(int reel2) {
		if (reel2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[卷轴2]reel2的值不得小于0");
		}
		this.reel2 = reel2;
	}
	
	public int getReel3() {
		return this.reel3;
	}


	public final static int getReel3MinLimit() {
		return 0;
	}

	public void setReel3(int reel3) {
		if (reel3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[卷轴3]reel3的值不得小于0");
		}
		this.reel3 = reel3;
	}
	
	public int getReel4() {
		return this.reel4;
	}


	public final static int getReel4MinLimit() {
		return 0;
	}

	public void setReel4(int reel4) {
		if (reel4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[卷轴4]reel4的值不得小于0");
		}
		this.reel4 = reel4;
	}
	
	public int getReel5() {
		return this.reel5;
	}


	public final static int getReel5MinLimit() {
		return 0;
	}

	public void setReel5(int reel5) {
		if (reel5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[卷轴5]reel5的值不得小于0");
		}
		this.reel5 = reel5;
	}
	
	public int getLevelDown() {
		return this.levelDown;
	}


	public final static int getLevelDownMinLimit() {
		return 0;
	}

	public void setLevelDown(int levelDown) {
		if (levelDown < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[等级下限]levelDown的值不得小于0");
		}
		this.levelDown = levelDown;
	}
	
	public int getLevelUp() {
		return this.levelUp;
	}


	public final static int getLevelUpMinLimit() {
		return 0;
	}

	public void setLevelUp(int levelUp) {
		if (levelUp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[等级上限]levelUp的值不得小于0");
		}
		this.levelUp = levelUp;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ReelTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ReelTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ReelTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ReelTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", turn=" + turn + ", reel1=" + reel1 + ", reel2=" + reel2 + ", reel3=" + reel3 + ", reel4=" + reel4 + ", reel5=" + reel5 + ", levelDown=" + levelDown + ", levelUp=" + levelUp + ",]";
	}
}