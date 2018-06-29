package com.gameserver.human.redismsg;

import java.text.MessageFormat;

import com.common.LogReasons;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.msg.GCSlotRoomGift;
import com.gameserver.player.Player;
import com.gameserver.redis.IRedisMessage;

public class RoomGift implements IRedisMessage {
	
	private long passid;

	/** 发送者ID */
	private long send_playerId;
	/** 礼物ID */
	private int giftId;
	/** 发送类型 0 个人 1 全体 */
	private int send_type;
	/** 接收者ID在发送类型是0的时候有效果 */
	private long rece_playerId;

	/**魅力值 **/
	private int charm;
	
	
	public long getPassid() {
		return passid;
	}


	public void setPassid(long passid) {
		this.passid = passid;
	}


	public long getSend_playerId() {
		return send_playerId;
	}


	public void setSend_playerId(long send_playerId) {
		this.send_playerId = send_playerId;
	}


	public int getGiftId() {
		return giftId;
	}


	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}


	public int getSend_type() {
		return send_type;
	}


	public void setSend_type(int send_type) {
		this.send_type = send_type;
	}


	public long getRece_playerId() {
		return rece_playerId;
	}


	public void setRece_playerId(long rece_playerId) {
		this.rece_playerId = rece_playerId;
	}
	
	


	public int getCharm() {
		return charm;
	}


	public void setCharm(int charm) {
		this.charm = charm;
	}


	@Override
	public void execute() {
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passid);
		
		if(player != null){
			GCSlotRoomGift message = new GCSlotRoomGift();
			message.setGiftId(giftId);
			message.setRece_playerId(rece_playerId);
			message.setSend_playerId(send_playerId);
			message.setSend_type(send_type);
			player.sendMessage(message);
			
			if(send_type == 0){
				if(rece_playerId == player.getPassportId()){
					player.getHuman().setGiftId(message.getGiftId());
					player.getHuman().setModified();
				}
			}else{
				player.getHuman().setGiftId(message.getGiftId());
				player.getHuman().setModified();
			}
			//加魅力值
			String inDetailReason = MessageFormat.format(LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM.getReasonText(),message.getGiftId());
			player.getHuman().giveMoney(charm, Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, message.getGiftId(), 1);
		}

	}

}
