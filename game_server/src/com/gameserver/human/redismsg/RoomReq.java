package com.gameserver.human.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.human.msg.GCSlotRoomPlease;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class RoomReq implements IRedisMessage {
	
	private long passId;
	
	/** 邀请的好友IMG */
	private String friendImg;
	/** 邀请的好友名字 */
	private String friendName;
	/** 要求加入的老虎机ID */
	private int slotId;
	/** 房间ID */
	private String roomId;
	
	private long playerId;
	

	public long getPassId() {
		return passId;
	}



	public void setPassId(long passId) {
		this.passId = passId;
	}



	public String getFriendImg() {
		return friendImg;
	}



	public void setFriendImg(String friendImg) {
		this.friendImg = friendImg;
	}



	public String getFriendName() {
		return friendName;
	}



	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}



	public int getSlotId() {
		return slotId;
	}



	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}



	public String getRoomId() {
		return roomId;
	}



	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

   

	public long getPlayerId() {
		return playerId;
	}



	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}



	@Override
	public void execute() {
		 Player reqPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(passId);
	      if(reqPlayer != null){
	    	  GCSlotRoomPlease message = new GCSlotRoomPlease();
	    	  message.setFriendImg(friendImg);
	    	  message.setFriendName(friendName);
	    	  message.setSlotId(slotId);
	    	  message.setRoomId(roomId);
	    	  message.setPlayerId(playerId);
	    	  reqPlayer.sendMessage(message);
	      }
	}

}
