package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 弹劾状态
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubTanheState extends GCMessage{
	
	/** 不可弹劾:0 可弹劾:1 弹劾进行中:2 弹劾成功:3 弹劾失败：4 */
	private int result;
	/** 同意人数 */
	private int agree;
	/** 拒绝人数 */
	private int refuse;
	/** 个人状态 1 同意 2 拒绝 0 未表态 */
	private int selfState;

	public GCClubTanheState (){
	}
	
	public GCClubTanheState (
			int result,
			int agree,
			int refuse,
			int selfState ){
			this.result = result;
			this.agree = agree;
			this.refuse = refuse;
			this.selfState = selfState;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		agree = readInteger();
		refuse = readInteger();
		selfState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		writeInteger(agree);
		writeInteger(refuse);
		writeInteger(selfState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_TANHE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_TANHE_STATE";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public int getAgree(){
		return agree;
	}
		
	public void setAgree(int agree){
		this.agree = agree;
	}

	public int getRefuse(){
		return refuse;
	}
		
	public void setRefuse(int refuse){
		this.refuse = refuse;
	}

	public int getSelfState(){
		return selfState;
	}
		
	public void setSelfState(int selfState){
		this.selfState = selfState;
	}
}