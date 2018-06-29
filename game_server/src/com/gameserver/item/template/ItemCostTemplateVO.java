package com.gameserver.item.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 道具
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ItemCostTemplateVO extends TemplateObject {

	/** 游戏类型 1 百家乐 2 德州 */
	@ExcelCellBinding(offset = 1)
	protected int gameId;

	/** 房间Id */
	@ExcelCellBinding(offset = 2)
	protected int roomId;

	/** 物品ID */
	@ExcelCellBinding(offset = 3)
	protected int itemId;

	/** 消耗筹码 */
	@ExcelCellBinding(offset = 4)
	protected int costChips;

	/** 魅力值 */
	@ExcelCellBinding(offset = 5)
	protected int changeCharm;


	public int getGameId() {
		return this.gameId;
	}



	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public int getRoomId() {
		return this.roomId;
	}



	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getCostChips() {
		return this.costChips;
	}



	public void setCostChips(int costChips) {
		this.costChips = costChips;
	}
	
	public int getChangeCharm() {
		return this.changeCharm;
	}



	public void setChangeCharm(int changeCharm) {
		this.changeCharm = changeCharm;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ItemCostTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ItemCostTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ItemCostTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ItemCostTemplateVO [  gameId=" + gameId + ", roomId=" + roomId + ", itemId=" + itemId + ", costChips=" + costChips + ", changeCharm=" + changeCharm + ",]";
	}
}