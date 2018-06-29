package com.gameserver.bazoo.service.room;

import com.core.enums.IndexedEnum;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.msg.GCBlackWhiteCallNum;
import com.gameserver.bazoo.msg.GCBlackWhiteEndCount;
import com.gameserver.bazoo.msg.GCBlackWhiteWhoTurn;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteEnd;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteSomeOneCall;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteSwingLeft;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteWaitSomeOne;
import com.gameserver.bazoo.msg.GCStateRoomCallDice;
import com.gameserver.bazoo.msg.GCStateRoomEnter;
import com.gameserver.bazoo.msg.GCStateRoomMatching;
import com.gameserver.bazoo.msg.GCStateRoomReady;
import com.gameserver.bazoo.msg.GCStateRoomRoundBegin;
import com.gameserver.bazoo.msg.GCStateRoomRoundGuess;
import com.gameserver.bazoo.msg.GCStateRoomRoundOpen;
import com.gameserver.bazoo.msg.GCStateRoomRoundResult;
import com.gameserver.bazoo.msg.GCStateRoomRoundTurn;
import com.gameserver.bazoo.msg.GCStateRoomShowHandAllSwing;
import com.gameserver.bazoo.msg.GCStateRoomShowHandRoundResult;
import com.gameserver.bazoo.msg.GCStateRoomShowHandSingleSwing;
import com.gameserver.bazoo.msg.GCStateRoomSingleSwingBegin;
import com.gameserver.bazoo.msg.GCStateRoomSingleSwingEnd;
import com.gameserver.common.msg.GCMessage;

/**
 * 
 * 房间的状态
 * @author JavaServer
 *
 */
public class RoomStateInfo {
	//当前房间的状态
	private IndexedEnum roomState;

	//房间状态对应的消息
	GCMessage stateMessage = null;
	
	public RoomStateInfo(){
		roomState=RoomState.stateRoomEnter;
	}
	
	
	
	
	//判断状态
	public int judgeState(){
		//如果当前房间 是 未开始  或者是 已结束  那么  当前进来的用户 就可以设置成 进行时（即已经参与进来）  反之 当前房间 正在进行中 那么 当前用户就得设置成不能参与 即观战
		if(this.roomState.getIndex() == RoomState.stateRoomEnter.getIndex() 
				|| this.roomState.getIndex()==RoomState.stateRoomMatching.getIndex()
				|| this.roomState.getIndex()==RoomState.stateRoomReady.getIndex()){
			return RoomEveryUserInfo.USER_STATUS_PARTIN;//参与
		}else{
			return RoomEveryUserInfo.USER_STATUS_WATCH;//观战
		}
	}
	
	
	
	/**
	 * 设置状态还有消息
	 * @param roomState
	 * @param gcMessage
	 */
	public void setRoomState(IndexedEnum roomState,GCMessage gcMessage){
		//设置状态 设置当前状态需要的消息
		this.roomState = roomState;
		/**
		 * 公共的
		 */
		if(roomState == RoomState.stateRoomEnter){
			GCStateRoomEnter GCStateRoomEnter = new GCStateRoomEnter();
			GCStateRoomEnter.setStatus(RoomState.stateRoomEnter.getIndex());
			this.stateMessage =GCStateRoomEnter;
			
			
		}else if(roomState == RoomState.stateRoomMatching){//匹配状态瞬间 完成
			GCStateRoomMatching GCStateRoomMatching = new GCStateRoomMatching();
			GCStateRoomMatching.setStatus(RoomState.stateRoomMatching.getIndex());
			this.stateMessage =GCStateRoomMatching;
			
		}else if(roomState == RoomState.stateRoomReady){
			GCStateRoomReady gc= (GCStateRoomReady)gcMessage;
			
			GCStateRoomReady GCStateRoomReady = new GCStateRoomReady();
			GCStateRoomReady.setStatus(RoomState.stateRoomReady.getIndex());
			GCStateRoomReady.setLeftSecond(gc.getLeftSecond());
			this.stateMessage =GCStateRoomReady;
			
		}else if(roomState == RoomState.stateRoomRoundBegin){
			GCStateRoomRoundBegin GCStateRoomRoundBegin = new GCStateRoomRoundBegin();
			GCStateRoomRoundBegin.setStatus(RoomState.stateRoomRoundBegin.getIndex());
			this.stateMessage =GCStateRoomRoundBegin;
			
			
			
			
		/**
		 * 经典模式
		 */	
		}else if(roomState == RoomState.ClassicalState.stateRoomRoundTurn){
			GCStateRoomRoundTurn gc= (GCStateRoomRoundTurn)gcMessage;
			
			GCStateRoomRoundTurn GCStateRoomRoundTurn = new GCStateRoomRoundTurn();
			GCStateRoomRoundTurn.setStatus(RoomState.ClassicalState.stateRoomRoundTurn.getIndex());
			GCStateRoomRoundTurn.setLeftSecond(gc.getLeftSecond());
			GCStateRoomRoundTurn.setWhoTurnPassportId(gc.getWhoTurnPassportId());
			this.stateMessage =GCStateRoomRoundTurn;
			
		}else if(roomState == RoomState.ClassicalState.stateRoomCallDice){
			GCStateRoomCallDice GCStateRoomCallDice = new GCStateRoomCallDice();
			GCStateRoomCallDice.setStatus(RoomState.ClassicalState.stateRoomCallDice.getIndex());
			this.stateMessage =GCStateRoomCallDice;
			
		}else if(roomState == RoomState.ClassicalState.stateRoomRoundOpen){
			GCStateRoomRoundOpen gc= (GCStateRoomRoundOpen)gcMessage;
			
			GCStateRoomRoundOpen GCStateRoomRoundOpen = new GCStateRoomRoundOpen();
			GCStateRoomRoundOpen.setStatus(RoomState.ClassicalState.stateRoomRoundOpen.getIndex());
			GCStateRoomRoundOpen.setRobMultiple(gc.getRobMultiple());
			GCStateRoomRoundOpen.setRobPassportId(gc.getRobPassportId());
			
			this.stateMessage =GCStateRoomRoundOpen;
			
		}else if(roomState == RoomState.ClassicalState.stateRoomRoundGuess){
			GCStateRoomRoundGuess GCStateRoomRoundGuess = new GCStateRoomRoundGuess();
			GCStateRoomRoundGuess.setStatus(RoomState.ClassicalState.stateRoomRoundGuess.getIndex());
			this.stateMessage =GCStateRoomRoundGuess;
			
		}else if(roomState == RoomState.ClassicalState.stateRoomRoundResult){
			GCStateRoomRoundResult gc= (GCStateRoomRoundResult)gcMessage;
			
			
			GCStateRoomRoundResult GCStateRoomRoundResult = new GCStateRoomRoundResult();
			GCStateRoomRoundResult.setStatus(RoomState.ClassicalState.stateRoomRoundResult.getIndex());
			GCStateRoomRoundResult.setEndCountInfo(gc.getEndCountInfo());
			this.stateMessage =GCStateRoomRoundResult;
			
			
			
			
			
		
		/**
		 * 牛牛模式
		 */
		}else if(roomState == RoomState.CowState.stateRoomSingleSwingBegin){
			GCStateRoomSingleSwingBegin GCStateRoomSingleSwingBegin = new GCStateRoomSingleSwingBegin();
			GCStateRoomSingleSwingBegin.setStatus(roomState.getIndex());
			this.stateMessage=GCStateRoomSingleSwingBegin;
			
		}else if(roomState == RoomState.CowState.stateRoomSingleSwingEnd){
			GCStateRoomSingleSwingEnd gc= (GCStateRoomSingleSwingEnd)gcMessage;
			
			GCStateRoomSingleSwingEnd GCStateRoomSingleSwingEnd = new GCStateRoomSingleSwingEnd();
			GCStateRoomSingleSwingEnd.setStatus(roomState.getIndex());
			GCStateRoomSingleSwingEnd.setDiceInfo(gc.getDiceInfo());
			this.stateMessage=GCStateRoomSingleSwingEnd;
			
		}else if(roomState == RoomState.CowState.stateRoomRoundResult){
			GCStateRoomRoundResult gc= (GCStateRoomRoundResult)gcMessage;
			
			
			GCStateRoomRoundResult GCStateRoomRoundResult = new GCStateRoomRoundResult();
			GCStateRoomRoundResult.setStatus(RoomState.CowState.stateRoomRoundResult.getIndex());
			GCStateRoomRoundResult.setEndCountInfo(gc.getEndCountInfo());
			this.stateMessage =GCStateRoomRoundResult;
		
			
			
			/**
			 * 梭哈 模式
			 */
			}else if(roomState == RoomState.ShowHandState.stateRoomShowHandAllSwing){
				GCStateRoomShowHandAllSwing gc = (GCStateRoomShowHandAllSwing)gcMessage;
				
				GCStateRoomShowHandAllSwing GCStateRoomShowHandAllSwing = new GCStateRoomShowHandAllSwing();
				GCStateRoomShowHandAllSwing.setStatus(roomState.getIndex());
				GCStateRoomShowHandAllSwing.setLeftTimes(gc.getLeftTimes());
				GCStateRoomShowHandAllSwing.setDiceInfo(gc.getDiceInfo());
				GCStateRoomShowHandAllSwing.setLeftSecond(gc.getLeftSecond());
				this.stateMessage=GCStateRoomShowHandAllSwing;
				
			}else if(roomState == RoomState.ShowHandState.stateRoomShowHandSingleSwing){
				GCStateRoomShowHandSingleSwing gc= (GCStateRoomShowHandSingleSwing)gcMessage;
				
				GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing = new GCStateRoomShowHandSingleSwing();
				GCStateRoomShowHandSingleSwing.setStatus(roomState.getIndex());
				GCStateRoomShowHandSingleSwing.setPassportId(gc.getPassportId());
				GCStateRoomShowHandSingleSwing.setLeftTimes(gc.getLeftTimes());
				GCStateRoomShowHandSingleSwing.setDiceInfo(gc.getDiceInfo());
				GCStateRoomShowHandSingleSwing.setShowHandBet(gc.getShowHandBet());
				GCStateRoomShowHandSingleSwing.setLeftSecond(gc.getLeftSecond());
				this.stateMessage=GCStateRoomShowHandSingleSwing;
				
			}else if(roomState == RoomState.ShowHandState.stateRoomShowHandRoundResult){
				GCStateRoomShowHandRoundResult gc= (GCStateRoomShowHandRoundResult)gcMessage;
				
				
				GCStateRoomShowHandRoundResult GCStateRoomShowHandRoundResult = new GCStateRoomShowHandRoundResult();
				GCStateRoomShowHandRoundResult.setStatus(roomState.getIndex());
				GCStateRoomShowHandRoundResult.setEndCountInfo(gc.getEndCountInfo());
				this.stateMessage =GCStateRoomShowHandRoundResult;
		
		/**
		 * 红黑单双 模式
		 */
			}else if(roomState == RoomState.BlackWhiteState.stateRoomBlackWhiteWaitSomeOne){
				GCBlackWhiteWhoTurn gc= (GCBlackWhiteWhoTurn)gcMessage;
				
				
				GCStateRoomBlackWhiteWaitSomeOne GCStateRoomBlackWhiteWaitSomeOne = new GCStateRoomBlackWhiteWaitSomeOne();
				GCStateRoomBlackWhiteWaitSomeOne.setWhoTurnPassportId(gc.getWhoTurnPassportId());
				GCStateRoomBlackWhiteWaitSomeOne.setLeftSecond(gc.getLeftSecond());
				this.stateMessage =GCStateRoomBlackWhiteWaitSomeOne;
				
			}else if(roomState == RoomState.BlackWhiteState.stateRoomBlackWhiteSomeOneCall){
				GCBlackWhiteCallNum gc= (GCBlackWhiteCallNum)gcMessage;
				
				
				GCStateRoomBlackWhiteSomeOneCall GCStateRoomBlackWhiteSomeOneCall = new GCStateRoomBlackWhiteSomeOneCall();
				GCStateRoomBlackWhiteSomeOneCall.setBlackWhiteBet(gc.getBlackWhiteBet());
				GCStateRoomBlackWhiteSomeOneCall.setBlackWhiteDiceInfo(gc.getBlackWhiteDiceInfo());
				GCStateRoomBlackWhiteSomeOneCall.setDiceType(gc.getDiceType());
				GCStateRoomBlackWhiteSomeOneCall.setKillNum(gc.getKillNum());
				GCStateRoomBlackWhiteSomeOneCall.setMultiple(gc.getMultiple());
				GCStateRoomBlackWhiteSomeOneCall.setWhoCallPassportId(gc.getWhoCallPassportId());
				this.stateMessage =GCStateRoomBlackWhiteSomeOneCall;
			}else if(roomState == RoomState.BlackWhiteState.stateRoomBlackWhiteSwingLeft){
				GCStateRoomBlackWhiteSwingLeft gc= (GCStateRoomBlackWhiteSwingLeft)gcMessage;
				this.stateMessage =gc;
				
			}else if(roomState == RoomState.BlackWhiteState.stateRoomBlackWhiteEnd){
				GCBlackWhiteEndCount gc= (GCBlackWhiteEndCount)gcMessage;
				
				GCStateRoomBlackWhiteEnd GCStateRoomBlackWhiteEnd = new GCStateRoomBlackWhiteEnd();
				GCStateRoomBlackWhiteEnd.setEndCountInfo(gc.getEndCountInfo());
				this.stateMessage =GCStateRoomBlackWhiteEnd;
			}
	
		
		
		
	}




	public IndexedEnum getRoomState() {
		return roomState;
	}
	public GCMessage getStateMessage() {
		return stateMessage;
	}

	
	
	
	
	
	
}
