package com.gameserver.relation.redismsg;

import com.common.constants.LangConstants;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;
import com.gameserver.slot.msg.CGSlotEnterRoom;

public class FriendSlotIdRoomIdRet implements IRedisMessage {
	
	
	private int result;
	
	private long palyerId;
	
	private int slotid;
	
	private String roomid;
	
	

	public long getPalyerId() {
		return palyerId;
	}



	public void setPalyerId(long palyerId) {
		this.palyerId = palyerId;
	}



	public int getSlotid() {
		return slotid;
	}



	public void setSlotid(int slotid) {
		this.slotid = slotid;
	}



	public String getRoomid() {
		return roomid;
	}



	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}



	public int getResult() {
		return result;
	}



	public void setResult(int result) {
		this.result = result;
	}



	@Override
	public void execute() {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(palyerId);
		if(player != null){
			if(result == 1){
				  CGSlotEnterRoom message = new CGSlotEnterRoom();
				  message.setRoomId(roomid);
				  message.setSlotId(slotid);
				  SlotHandlerFactory.getHandler().handleSlotEnterRoom(player, message);
			}else{
				//玩家已经下线 或者缓存不存在
				player.getHuman().sendSystemMessage(LangConstants.TEXAS_ROOM_FULL);
			}
		}
	}

}
