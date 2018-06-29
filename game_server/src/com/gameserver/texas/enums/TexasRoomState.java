package com.gameserver.texas.enums;

import com.core.enums.IndexedEnum;

public enum TexasRoomState implements IndexedEnum{
	/**初始化**/
	INIT(0),
	/**等待游戏人员足够**/
	WAIT_PEOPLE(1),
	/**人员足够等待开场**/
	WAIT(2),
	/**发牌等待客户端动画时间 **/
	PREFLOP_WAIT(3),
	/**低牌下注 **/
	PREFLOP(4),//
	/** 低牌边池客户端动画**/
	PREFLOP_SIDE_POOL(5),//
	/** 第二轮翻牌 **/
	FLOP_WAIT(6),//
	/**第二轮下注 **/
	FLOP(7),//
	/** 第二轮边池**/
	FLOP_SIDE_POOL(8),//
	/** **/
	TURN_WAIT(9),//
	/** **/
	TURN(10),//
	/** **/
	TURN_SIDE_POOL(11),//
	/** **/
	RIVER_WAIT(12),//
	/** **/
	RIVER(13),//
	/** **/
	RIVER_SIDE_POOL(14),//
	/**结算 **/
	END(15);//
	
	private int index;
	
	private  TexasRoomState(int index){
		this.index = index;
	}
		
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	@Override
	public String toString(){
		switch(this)
		{
		case INIT:
			return "初始化";
		case WAIT_PEOPLE:
			return "等人";
		case WAIT:
			return "等待开始";
		case PREFLOP_WAIT:
			return "preflop wait";
		case PREFLOP_SIDE_POOL:
			return "preflop side pool";
		case PREFLOP:
			return "preflop";
		case FLOP:
			return "flop";
		case FLOP_SIDE_POOL:
			return "flop side pool";
		case FLOP_WAIT:
			return "flop wait";
		case TURN:
			return "turn";
		case TURN_WAIT:
			return "turn wait";
		case TURN_SIDE_POOL:
			return "turn side pool";
		case RIVER:
			return "river";
		case RIVER_WAIT:
			return "river wait";
		case RIVER_SIDE_POOL:
			return "river side pool";
		case END:
			return "end";
		default:
			return "";
		}
	}
}
