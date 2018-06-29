package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 统一摇色子得返回(由服务器来统一摇色子  同时表示新一轮开始 )
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDiceUnifySwing extends GCMessage{
	
	/** 用户ID */
	private long passportId;
	/** 色子的值 */
	private int[] diceValues;

	public GCDiceUnifySwing (){
	}
	
	public GCDiceUnifySwing (
			long passportId,
			int[] diceValues ){
			this.passportId = passportId;
			this.diceValues = diceValues;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		passportId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		diceValues = new int[count];
		for(int i=0; i<count; i++){
			diceValues[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DICE_UNIFY_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DICE_UNIFY_SWING";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	
}