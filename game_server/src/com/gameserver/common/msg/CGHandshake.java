package com.gameserver.common.msg;

import com.common.constants.DisconnectReason;
import com.core.msg.BaseMinaMessage;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.misc.handler.MiscHandlerFactory;
import com.gameserver.misc.msg.CGFbGetReward;
import com.gameserver.misc.msg.CGFbThumbReward;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerConstants;
import com.gameserver.player.async.SavePlayerRoleOperation;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.startup.MinaGameClientSession;

/**
 * 客户端用于时间同步的消息:因为有特殊代码不是生成的
 * 
 * @author Thinker
 */
public class CGHandshake extends BaseMinaMessage<MinaGameClientSession> {
	
	/**玩家ID **/
	private String id = "0";
	/**1 代表重新连接 **/
	private int fly = 0;
	
	/** 设备码  */
	private String deviceId;
	
	public CGHandshake() {}

	@Override
	protected boolean readImpl() {
		this.id = this.readString();
		this.fly = this.readInt();
		this.deviceId = this.readString();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		writeString(id);
		writeInt(fly);
		writeString(deviceId);
		return true;
	}

	@Override
	public short getType() {
		return MessageType.CG_HANDSHAKE;
	}

	@Override
	public String getTypeName() {
		return "CG_HANDSHAKE";
	}
	
	

	public void setId(String id) {
		this.id = id;
	}

	public void setFly(int fly) {
		this.fly = fly;
	}
	
	

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Override
	public void execute() {
		
		OnlinePlayerService onPlayer = Globals.getOnlinePlayerService();
		
		Player player = onPlayer.getPlayerByPassportId(Long.valueOf(id));
		
		if(player != null && fly == 1){
			
			//去除旧得Session 添加新的
			onPlayer.removeSession(player.getSession());
			//新的Session
			MinaGameClientSession mgc = this.getSession();
			
			player.setSession(mgc);
			
			mgc.setPlayer(player);
			
			player.setClientIp(mgc.getIp());

			Globals.getOnlinePlayerService().putPlayer(mgc,player);
			
			player.sendMessage(new GCHandshake());
			
		}else{//10分钟到时间了
			String ip = this.getSession().getIp();
			if(player != null && fly == 0){ //或者玩家从另一个客户端登陆
				String oldIp = player.getClientIp();
				if(ip != null && oldIp != null){
					/*String ip1 = ip.split(":")[0];
					String ip2 = oldIp.split(":")[0];*/
					if(!ip.equals(oldIp) && !deviceId.equals(player.getDeviceId())){
						player.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(), ""));
					}
				}
				
				Human human = player.getHuman();
				if(human != null){
					Globals.getSlotRoomService().outLogin(player);
					SavePlayerRoleOperation _saveTask = new SavePlayerRoleOperation(player,PlayerConstants.CHARACTER_INFO_MASK_BASE, true);
					Globals.getAsyncService().createOperationAndExecuteAtOnce(_saveTask);
					//Globals.getSceneService().onPlayerLeaveScene(player, null);
				}
				Globals.getOnlinePlayerService().removeSession(player.getSession());
				Globals.getOnlinePlayerService().removePlayerMandatory(player);

//				Globals.getActivityService().init();
			}
			
			Player connectClient = new Player(this.getSession());
			
			connectClient.setClientIp(ip);
			
			Globals.getOnlinePlayerService().putPlayer(this.getSession(),connectClient);
			
			connectClient.sendMessage(new GCHandshake());
			
		}
		
		
	}

	/*private void afterLogin(Player player) {
		//facebook 第一次登录后给奖励
		CGFbGetReward CGFbGetReward = new CGFbGetReward();
		MiscHandlerFactory.getHandler().handleFbGetReward(player, CGFbGetReward);
		
		CGFbThumbReward CGFbThumbReward = new CGFbThumbReward();
		MiscHandlerFactory.getHandler().handleFbThumbReward(player, CGFbThumbReward);
	}*/
	
}