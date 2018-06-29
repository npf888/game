package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * WinnerWheelConfigTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class WinnerWheelConfigTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 2)
	protected String icon;

	/** 转盘编号 */
	@ExcelCellBinding(offset = 3)
	protected int wheeltype;

	/** 金币区间开始 */
	@ExcelCellBinding(offset = 4)
	protected long start;

	/** 金币区间结束 */
	@ExcelCellBinding(offset = 5)
	protected long end;

	/** 中奖开关 0.关 1.开 */
	@ExcelCellBinding(offset = 6)
	protected int bigswitch;

	/** 中奖开关 0.关 1.开 */
	@ExcelCellBinding(offset = 7)
	protected int megaswitch;

	/** 中奖开关 0.关 1.开 */
	@ExcelCellBinding(offset = 8)
	protected int superswitch;

	/** 中奖开关 0.关 1.开 */
	@ExcelCellBinding(offset = 9)
	protected int epicswitch;

	/** 中奖开关 0.关 1.开 */
	@ExcelCellBinding(offset = 10)
	protected int jackpotswitch;

	/** 对应商品的ID */
	@ExcelCellBinding(offset = 11)
	protected int pid;


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
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getWheeltype() {
		return this.wheeltype;
	}



	public void setWheeltype(int wheeltype) {
		this.wheeltype = wheeltype;
	}
	
	public long getStart() {
		return this.start;
	}



	public void setStart(long start) {
		this.start = start;
	}
	
	public long getEnd() {
		return this.end;
	}



	public void setEnd(long end) {
		this.end = end;
	}
	
	public int getBigswitch() {
		return this.bigswitch;
	}



	public void setBigswitch(int bigswitch) {
		this.bigswitch = bigswitch;
	}
	
	public int getMegaswitch() {
		return this.megaswitch;
	}



	public void setMegaswitch(int megaswitch) {
		this.megaswitch = megaswitch;
	}
	
	public int getSuperswitch() {
		return this.superswitch;
	}



	public void setSuperswitch(int superswitch) {
		this.superswitch = superswitch;
	}
	
	public int getEpicswitch() {
		return this.epicswitch;
	}



	public void setEpicswitch(int epicswitch) {
		this.epicswitch = epicswitch;
	}
	
	public int getJackpotswitch() {
		return this.jackpotswitch;
	}



	public void setJackpotswitch(int jackpotswitch) {
		this.jackpotswitch = jackpotswitch;
	}
	
	public int getPid() {
		return this.pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, WinnerWheelConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends WinnerWheelConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, WinnerWheelConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "WinnerWheelConfigTemplateVO [  langDesc=" + langDesc + ", icon=" + icon + ", wheeltype=" + wheeltype + ", start=" + start + ", end=" + end + ", bigswitch=" + bigswitch + ", megaswitch=" + megaswitch + ", superswitch=" + superswitch + ", epicswitch=" + epicswitch + ", jackpotswitch=" + jackpotswitch + ", pid=" + pid + ",]";
	}
}