package com.gameserver.relation.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class FriendSlotIdRoomId implements IRedisMessage {

	private long palyerId;
	
	private long friendId;
	
	private String serverid;
	
	public long getFriendId() {
		return friendId;
	}



	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}



	public String getServerid() {
		return serverid;
	}



	public void setServerid(String serverid) {
		this.serverid = serverid;
	}



	public long getPalyerId() {
		return palyerId;
	}



	public void setPalyerId(long palyerId) {
		this.palyerId = palyerId;
	}



	@Override
	public void execute() {
		
		Player player =  Globals.getOnlinePlayerService().getPlayerByPassportId(friendId);
		
		FriendSlotIdRoomIdRet message = new FriendSlotIdRoomIdRet();
		message.setResult(0);
		message.setPalyerId(palyerId);
		message.setSlotid(0);
		message.setRoomid("");
		if(player != null){
			Human human = player.getHuman();
			
			String slotRoomId = human.getSlotRoomId();
			
			int slotId = human.getHumanSlotManager().getSlotId();
			
			if(!slotRoomId.equals("")){
				message.setResult(1);
				message.setSlotid(slotId);
				message.setRoomid(slotRoomId);
			}
		}
		
		Globals.getRedisService().sendRedisMsgToServer(serverid, message);
	}

}
