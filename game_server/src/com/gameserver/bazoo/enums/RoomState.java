package com.gameserver.bazoo.enums;

import com.core.enums.IndexedEnum;

/**
 * 房间的状态 
 * @author JavaServer
 *
 */
public enum RoomState implements IndexedEnum {
	/**
	 * 公共状态
	 */
	/** 新一轮 的状态 **/
	stateRoomEnter(0,"stateRoomEnter"),
	/** 匹配状态 **/
	stateRoomMatching(1,"stateRoomMatching"),
	/** 倒计时  还有多长时间 开始 统一摇色子 **/
	stateRoomReady(2,"stateRoomReady"),
	/** 收到统一摇色子的消息 之后的状态**/
	stateRoomRoundBegin(3,"stateRoomRoundBegin"),
	
	;

	
	private int index;
	private String key;
	
	@Override
	public int getIndex() {
		return index;
	}
	public String getKey() {
		return key;
	}
	
	private RoomState(int index,String key){
		this.index = index;
		this.key = key;
	}
	
	
	/**
	 * 枚举不能继承 只能使用内部类的方式
	 * 内部类
	 */
	
	/*** 经典模式  ***/
	public enum ClassicalState implements IndexedEnum{
		/**
		 * 后边是私有的状态是公共状态
		 */
		/** 没有叫号 ，处于等待状态**/
		stateRoomRoundTurn(4,"stateRoomRoundTurn"),
		/** 已经叫号（和上边 的 5 循环切换 状态） **/
		stateRoomCallDice(5,"stateRoomCallDice"),
		/** 抢开之后的状态 **/
		stateRoomRoundOpen(6,"stateRoomRoundOpen"),
		/** 竞猜的状态**/
		stateRoomRoundGuess(7,"stateRoomRoundGuess"),
		/** 结算 中 的状态 **/
		stateRoomRoundResult(8,"stateRoomRoundResult"),
		;

		
		private int index;
		private String key;
		
		@Override
		public int getIndex() {
			return index;
		}
		public String getKey() {
			return key;
		}
		
		private ClassicalState(int index,String key){
			this.index = index;
			this.key = key;
		}
	}
	/*** 牛牛模式  ***/
	public enum CowState implements IndexedEnum{
		/** 重摇开始到 结束之前  的状态  **/
		stateRoomSingleSwingBegin(9,"stateRoomSingleSwingBegin"),
		/** 重摇结束到 结算之前  的状态 **/
		stateRoomSingleSwingEnd(10,"stateRoomSingleSwingEnd"),
		/** 结算 中 的状态 **/
		stateRoomRoundResult(11,"stateRoomRoundResult"),
		;

		
		private int index;
		private String key;
		
		@Override
		public int getIndex() {
			return index;
		}
		public String getKey() {
			return key;
		}
		
		private CowState(int index,String key){
			this.index = index;
			this.key = key;
		}
	}
	/*** 梭哈模式  ***/
	public enum ShowHandState implements IndexedEnum{
		
		/** 统一摇完色子 之后 等待 1、2秒 的 时间状态  **/
		stateRoomShowHandAllSwing(12,"stateRoomShowHandAllSwing"),
		/** 每个人单独摇 的状态（大部分 时间都在这个状态） **/
		stateRoomShowHandSingleSwing(13,"stateRoomShowHandSingleSwing"),
		/** 摇完 到进入下一局 之前的状态 **/
		stateRoomShowHandRoundResult(14,"stateRoomShowHandRoundResult"),
		;


		private int index;
		private String key;
		
		@Override
		public int getIndex() {
			return index;
		}
		public String getKey() {
			return key;
		}
		
		private ShowHandState(int index,String key){
			this.index = index;
			this.key = key;
		}
		
	}
	/*** 梭哈模式  ***/
	public enum BlackWhiteState implements IndexedEnum{
		
		/** 某个用户刚叫完的状态 **/
		stateRoomBlackWhiteSomeOneCall(15,"stateRoomBlackWhiteSomeOneCall"),
		/** 等待某个用户叫号中的状态 **/
		stateRoomBlackWhiteWaitSomeOne(16,"stateRoomBlackWhiteWaitSomeOne"),
		/** 摇完剩下色子的状态 **/
		stateRoomBlackWhiteSwingLeft(17,"stateRoomBlackWhiteSwingLeft"),
		/** 进入下一轮前线的状态 **/
		stateRoomBlackWhiteEnd(18,"stateRoomBlackWhiteEnd"),
		;
		
		
		private int index;
		private String key;
		
		@Override
		public int getIndex() {
			return index;
		}
		public String getKey() {
			return key;
		}
		
		private BlackWhiteState(int index,String key){
			this.index = index;
			this.key = key;
		}
		
	}
	
}
