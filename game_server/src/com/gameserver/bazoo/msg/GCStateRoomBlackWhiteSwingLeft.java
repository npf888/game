package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 17
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomBlackWhiteSwingLeft extends GCMessage{
	
	/** 变化的倍数 */
	private int multiple;
	/** 所有需要重摇的用户的ID */
	private long[] passportId;
	/** 所有需要重摇的用户剩余的色子数 */
	private int[] leftDiceNum;

	public GCStateRoomBlackWhiteSwingLeft (){
	}
	
	public GCStateRoomBlackWhiteSwingLeft (
			int multiple,
			long[] passportId,
			int[] leftDiceNum ){
			this.multiple = multiple;
			this.passportId = passportId;
			this.leftDiceNum = leftDiceNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		multiple = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		passportId = new long[count];
		for(int i=0; i<count; i++){
			passportId[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		leftDiceNum = new int[count];
		for(int i=0; i<count; i++){
			leftDiceNum[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(multiple);
		writeShort(passportId.length);
		for(int i=0; i<passportId.length; i++){
			writeLong(passportId[i]);
		}
		writeShort(leftDiceNum.length);
		for(int i=0; i<leftDiceNum.length; i++){
			writeInteger(leftDiceNum[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT";
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	public long[] getPassportId(){
		return passportId;
	}

	public void setPassportId(long[] passportId){
		this.passportId = passportId;
	}	

	public int[] getLeftDiceNum(){
		return leftDiceNum;
	}

	public void setLeftDiceNum(int[] leftDiceNum){
		this.leftDiceNum = leftDiceNum;
	}	
}