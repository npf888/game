package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回 魔法表情
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooMagicFace extends GCMessage{
	
	/** 谁发的 */
	private long sendPassportId;
	/** 给谁发的 */
	private long receivePassportId;
	/** 发送的魔法表情 */
	private String magicFace;

	public GCBazooMagicFace (){
	}
	
	public GCBazooMagicFace (
			long sendPassportId,
			long receivePassportId,
			String magicFace ){
			this.sendPassportId = sendPassportId;
			this.receivePassportId = receivePassportId;
			this.magicFace = magicFace;
	}

	@Override
	protected boolean readImpl() {
		sendPassportId = readLong();
		receivePassportId = readLong();
		magicFace = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(sendPassportId);
		writeLong(receivePassportId);
		writeString(magicFace);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_MAGIC_FACE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_MAGIC_FACE";
	}

	public long getSendPassportId(){
		return sendPassportId;
	}
		
	public void setSendPassportId(long sendPassportId){
		this.sendPassportId = sendPassportId;
	}

	public long getReceivePassportId(){
		return receivePassportId;
	}
		
	public void setReceivePassportId(long receivePassportId){
		this.receivePassportId = receivePassportId;
	}

	public String getMagicFace(){
		return magicFace;
	}
		
	public void setMagicFace(String magicFace){
		this.magicFace = magicFace;
	}
}