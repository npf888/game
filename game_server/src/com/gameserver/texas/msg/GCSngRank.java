package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州sng名次
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSngRank extends GCMessage{
	
	/** pos */
	private int pos;
	/** rank */
	private int sngRank;

	public GCSngRank (){
	}
	
	public GCSngRank (
			int pos,
			int sngRank ){
			this.pos = pos;
			this.sngRank = sngRank;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		sngRank = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeInteger(sngRank);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SNG_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SNG_RANK";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public int getSngRank(){
		return sngRank;
	}
		
	public void setSngRank(int sngRank){
		this.sngRank = sngRank;
	}
}