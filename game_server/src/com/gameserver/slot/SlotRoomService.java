package com.gameserver.slot;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.human.msg.GCFriendGame;
import com.gameserver.human.msg.GCSlotRoom1;
import com.gameserver.human.msg.GCSlotRoom2;
import com.gameserver.human.msg.GCSlotRoom3;
import com.gameserver.human.msg.GCSlotRoom4;
import com.gameserver.human.msg.GCSlotRoom5;
import com.gameserver.human.msg.GCSlotRoomGift;
import com.gameserver.human.redismsg.RoomGift;
import com.gameserver.player.Player;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.redis.enums.RedisSlotKey;
import com.gameserver.relation.Friend;
import com.gameserver.slot.data.HumanBroadcastInfo;
import com.gameserver.slot.msg.GCSlotEnter;
import com.gameserver.slot.redismsg.ChangRoom;
import com.gameserver.slot.redismsg.ChangeInfo2;
import com.gameserver.slot.redismsg.ChangeInfo3;
import com.gameserver.slot.redismsg.ChangeInfo4;
import com.gameserver.slot.redismsg.EnterSlot;
import com.gameserver.slot.redismsg.NotifyFriends;
import com.gameserver.slot.redismsg.OutRoom;
import com.gameserver.slot.redismsg.RoleRoom;

/**
 * 老虎机房间管理器
 * @author 郭君伟
 *
 */
public class SlotRoomService implements InitializeRequired {
	private Logger log = Loggers.slotLogger;
	
	public static final int roomNum = 5;

	private JedisPool pool;
	
	@Override
	public void init() {
		pool = Globals.getRedisService().getJedisPool();
	}
	
	/**
	 * 角色下线的时候调用
	 * @param player
	 */
	public void outLogin(Player player) {
		/*Human human = player.getHuman();
		if (human == null) {
			return;
		}
		HumanSlotManager manager = human.getHumanSlotManager();
		int slotId = manager.getSlotId();
		String roomId = human.getSlotRoomId();
		if (!roomId.equals("") && slotId != 0) {
			outSlot(player.getPassportId(), slotId, roomId);
		}*/
	}
	/**
	 * 角色下线的时候调用
	 * @param player
	 */
	public void outPlayerLogin(Player player,int slotId,String roomId){
		
		if(StringUtils.isNotBlank(roomId) && slotId != 0){
//			outSlot(player.getPassportId(),slotId,roomId);
		}
	}
	
	/**
	 * 进入老虎机 特定房间的老虎机
	 * @param player
	 * @param slotId
	 */
	public String enterSlotForRoomId(long passportId,int newSlotId,int oldSlotId,String newRoomId,String oldRoomId){
		String str = "";
		//获取房间人数
		int num = getRoomNum(newSlotId,newRoomId);
		if(num < roomNum && num != -1){
			if(!oldRoomId.equals("") && oldSlotId != 0){
				outSlot(passportId,oldSlotId,oldRoomId);
			}
			str = operatingDataRoom(passportId,newSlotId,newRoomId,num);
		}
		return str;
	}
	
	public void enterSlotzz(Player player,String str,Slot slot,String newRoomid,int newSlotId){
		if(!str.equals("")){
			player.getHuman().setSlotRoomId(newRoomid);
			//进入老虎机
			Globals.getSlotService().playerEnterSlot(player, slot);
			GCSlotEnter gcSlotEnter = new GCSlotEnter();
			gcSlotEnter.setSlotId(newSlotId);
			gcSlotEnter.setResultType(0);
			player.sendMessage(gcSlotEnter);
			String[] ids = str.split(",");
			Globals.getSlotRoomService().enterRoomNotice(ids,player.getHuman().getPassportId());
			Globals.getSlotRoomService().sendMemeberOneself(player,ids);
			Globals.getSlotRoomService().notifyFriends(player.getHuman());
			player.getHuman().getHumanSlotManager().getMsgCache().clear();
		}else{
			player.getHuman().sendSystemMessage(LangConstants.SLOT_ROOM_REQ3);
		}
	}
	
	
	
	/**
	 * 进入老虎机
	 * @param player
	 * @param oldslotId
	 * @param newSlot
	 */
	public void enterSlot(Player player,int oldslotId,int newSlot){
		Human human = player.getHuman();
		long passportId = human.getPassportId();
		String oldRoomId = human.getSlotRoomId();
		if(Globals.getServerComm().isAuthority()){
			if(!oldRoomId.equals("") && oldslotId != 0){
				outSlot(passportId,oldslotId,oldRoomId);
			}
			//设置新房间ID
			human.setSlotRoomId(operatingData(passportId,newSlot));
			String[] ids = getRoomMemeberSlot(player,newSlot);
			enterRoomNotice(ids,passportId);
			sendMemeberOneself(player,ids);
			notifyFriends(human);
		}else{ 
			sendEntrSlot(passportId,oldslotId,newSlot,oldRoomId,Globals.getServerConfig().getServerId());
		}
		
	}
	
	private void sendEntrSlot(long passportId,int oldslotId,int newSlotId,String roomId,String serverId){
		EnterSlot message = new EnterSlot();
		message.setPassportId(passportId);
		message.setOldslotId(oldslotId);
		message.setNewslotId(newSlotId);
		message.setRoomId(roomId);
		message.setServerid(serverId);
		Globals.getRedisService().sendRedisMsgToServer(Globals.getServerComm().getMinServerId(), message);
	}
	
	public void enterSlotRedis(long passportId,int oldslotId,int newSlotId,String roomId,String serverid){
		if(!roomId.equals("") && oldslotId != 0){
			outSlot(passportId,oldslotId,roomId);
		}
		String newRoomId = operatingData(passportId,newSlotId);
		sendRoomId(passportId,newRoomId,serverid);
	}
	
	private void sendRoomId(long passportId,String roomId,String serverid){
		ChangRoom message = new ChangRoom();
		message.setPassportId(passportId);
		message.setRoomId(roomId);
		Globals.getRedisService().sendRedisMsgToServer(serverid, message);
	}
	
	
	
	public void changeRoomId(long passportId,String roomId){
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
		player.getHuman().setSlotRoomId(roomId);
		String[] ids = getRoomMemeber(player);
		enterRoomNotice(ids,passportId);
		sendMemeberOneself(player,ids);
		notifyFriends(player.getHuman());
	}
	
	/**
	 * 获取房间其他玩家ID
	 * @param player
	 * @return
	 */
	public String[] getRoomMemeber(Player player){
		Human human = player.getHuman();
		int slotId = human.getHumanSlotManager().getSlotId();
		String roomid = human.getSlotRoomId();
		
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String ids = jedis.hget(slotRoomID, roomid);
			if(ids == null){
				return new String[0];
			}
			return ids.split(",");
		}finally{
			jedis.close();
//			jedis.close();
		}
		
	}
	/**
	 * 获取房间其他玩家ID
	 * @param player
	 * @return
	 */
	private String[] getRoomMemeberSlot(Player player,int slotId){
		Human human = player.getHuman();
		String roomid = human.getSlotRoomId();
		
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String ids = jedis.hget(slotRoomID, roomid);
			if(ids == null){
				return new String[0];
			}
			return ids.split(",");
		}finally{
			jedis.close();
//			jedis.close();
		}
	}
	
	/**
	 * 进入指定的房间
	 * @param passportId
	 * @param newSlotId
	 * @param newRoomId
	 */
	private String operatingDataRoom(long passportId,int slotId,String newRoomId,int num){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		String slotRoomNoenoughID = RedisSlotKey.slotRoomNoenoughID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String newIds = "";
			String str = jedis.hget(slotRoomID, newRoomId);
			if(!StringUtils.isNotEmpty(str)){
				newIds = String.valueOf(passportId);
			}else{
				newIds = jedis.hget(slotRoomID, newRoomId)+","+passportId;
			}
			 Map<String,String> map = new HashMap<String,String>();
			 map.put(newRoomId, newIds);
			 jedis.hmset(slotRoomID, map);
			 
			if(num+1 == roomNum){
				jedis.srem(slotRoomNoenoughID, newRoomId);
			}
			return newIds;
		}finally{
			jedis.close();
//			jedis.close();
		}
		
	}
	
	private String operatingData(long passportId,int slotId){
		String roomId = "";
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		String slotRoomIdkey = RedisSlotKey.slotRoomIdKey.getKey()+slotId;
		String slotRoomNoenoughID = RedisSlotKey.slotRoomNoenoughID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			if(!jedis.exists(slotRoomID) || !jedis.exists(slotRoomNoenoughID)){//房间已满或者第一次创建
				roomId = String.valueOf(jedis.incr(slotRoomIdkey));
				jedis.hset(slotRoomID, roomId, String.valueOf(passportId));
				jedis.sadd(slotRoomNoenoughID, roomId);
			}else{
				roomId = jedis.srandmember(slotRoomNoenoughID);
				String ids = jedis.hget(slotRoomID, roomId);
				 if(StringUtils.isNotEmpty(ids)){
					 ids = ids+","+passportId;
					 if(ids.split(",").length == roomNum){
						 jedis.srem(slotRoomNoenoughID, roomId);//删除房间已满
					 }
				 }else{
					 ids = String.valueOf(passportId);
				 }
				 Map<String,String> map = new HashMap<String,String>();
				 map.put(roomId, ids);
				 jedis.hmset(slotRoomID, map);
			}
		}finally{
			jedis.close();
		}
		return roomId;
	}
	
	
	
	/**
	 * 获取房间其他人信息
	 * @param ids
	 * @param player
	 */
	public void sendMemeberOneself(Player player,String[] ids){
		
		List<HumanBroadcastInfo> list = new ArrayList<HumanBroadcastInfo>();
		
		for(int i = 0;i < ids.length;i++){
			long passID = Long.valueOf(ids[i]);
			if(player.getPassportId() != passID){
				PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(passID);
				if(cache == null) continue;
				HumanBroadcastInfo indo = new HumanBroadcastInfo();
				indo.setPlayerId(cache.getPlayerId());
				indo.setCountries(cache.getCountries());
				indo.setGold(cache.getGold());
				indo.setImg(cache.getImg());
				indo.setLevel((int)cache.getLevel());
				indo.setName(cache.getName());
				indo.setGiftId(cache.getGiftId());
				indo.setIsRequest(0);
				indo.setVipLevel(cache.getVipLevel());
				indo.setGirlFlag(cache.getSex());
				String requestIds = player.getHuman().getRequestFriendIds();
				 if(StringUtils.isBlank(requestIds)){
				 }else{
					 String[] rIdArr = requestIds.split(",");
					 for(int j=0;j<rIdArr.length;j++){
						 if(Long.valueOf(rIdArr[j]).intValue()==cache.getPlayerId()){
							 indo.setIsRequest(1);
							 break;
						 }
					 }
				 }
				list.add(indo);
			}
		}
		GCSlotRoom1 message = new GCSlotRoom1();
		message.setHumanBroadcastInfo(list.toArray(new HumanBroadcastInfo[list.size()]));
		player.sendMessage(message);
	}
	
	/**
	 * 进入房间通知其他玩家
	 * @param ids
	 * @param passportId 被广播的玩家
	 */
	public void enterRoomNotice(String[] ids,long passportId){
		
		PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(passportId);
		if(cache == null)return;
		
		long playerId = cache.getPlayerId();
		String img = cache.getImg();
		String countries = cache.getCountries();
		String name = cache.getName();
		int level = (int)cache.getLevel();
		long gold = cache.getGold();
		
		for(String id : ids){
			
			if(id.equals("")){
				log.error("玩家ID【"+passportId+"】 房间人ID "+JSON.toJSONString(ids));
				continue;
			}
			
			long roleId = Long.valueOf(id);
			
			if(roleId == passportId)continue;

			Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(roleId);
			if(player != null){//玩家在当前服务器
				sendRoomMessage2(player,playerId,img,countries,level,name);
				sendRoomMessage3(player,playerId,gold);
			}else{//玩家没在当前服务器
				PlayerCacheInfo cacheplayer = Globals.getPlayerCacheService().getPlayerCacheById(passportId);
				RoleRoom roleRoom = new RoleRoom();
				roleRoom.setPassportId(roleId);
				roleRoom.setPlayerId(passportId);
				roleRoom.setImg(img);
				roleRoom.setCountries(countries);
				roleRoom.setLevel(level);
				roleRoom.setGold(gold);
				roleRoom.setName(name);
				String serverid = cacheplayer.getServerId();
				Globals.getRedisService().sendRedisMsgToServer(serverid, roleRoom);
			}
		}
	}
	
	
	public void sendRoomMessage2(Player player,long playerId,String img,String  countries,int level,String name){
		GCSlotRoom2 message = new GCSlotRoom2();
		message.setPlayerId(playerId);
		message.setImg(img);
		message.setCountries(countries);
		message.setLevel(level);
		message.setName(name);
		player.sendMessage(message);
	}
	
	public void sendRoomMessage3(Player player,long playerId,long gold){
		GCSlotRoom3 message = new GCSlotRoom3();
		message.setPlayerId(playerId);
		message.setGold(gold);
		player.sendMessage(message);
	}
	
	public void sendRoomMessage4(Player player,long playerId,int bigAward){
		GCSlotRoom4 message = new GCSlotRoom4();
		message.setPlayerId(playerId);
		message.setBigAward(bigAward);
		player.sendMessage(message);
	}
	
	
	
	
	/**
	 * 退出老虎机·
	 * @param passportId
	 * @param slotId
	 * @param roomId
	 */
	public void outSlot(long passportId,int slotId,String roomId){
		Player outplayer = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
		if(outplayer != null && outplayer.getHuman() != null){
			outplayer.getHuman().setGiftId(0);
	    	outplayer.getHuman().setModified();
		}
    	
    	
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		String slotRoomNoenoughID = RedisSlotKey.slotRoomNoenoughID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
		 String str = jedis.hget(slotRoomID, roomId);
		if(StringUtils.isNotEmpty(str) && outplayer != null){
			 String[] ids = str.split(",");
			 StringBuilder sb = new StringBuilder();
			    boolean fly = true;
			 	for(String id : ids){
			 		if(!id.equals(String.valueOf(passportId))){
			 			if(fly){
			 				fly = false;
			 				sb.append(id);
			 			}else{
			 				sb.append(","+id);
			 			}
			 			sendOutRoom(Long.valueOf(id),passportId,true);
			 		}
			 	}
			 	Map<String,String> map = new HashMap<String,String>();
			 	map.put(roomId, sb.toString());
			 	jedis.hmset(slotRoomID, map);
			 	jedis.sadd(slotRoomNoenoughID, roomId);
		}else{
			/**
			 * 斯巴达专用，如果该房间 不存在，就删除 相应的子弹
			 */
			jedis.hdel(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId);
			log.error("玩家 ID 【"+passportId+"】 退出老虎机ID"+slotRoomID+"】 房间号 【"+roomId+"】 失败");
		}
		}catch(Exception e){
			log.error("", e);
		}finally{
			jedis.close();
		}
	}
	
    public void sendOutRoom(long passportId,long outPassportId,boolean fly){
    	Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
    	if(player != null){
    		GCSlotRoom5 message = new GCSlotRoom5();
        	message.setPlayerId(outPassportId);
    		player.sendMessage(message);
    		
    	}else if(player == null && fly){
    		sendMessageOutRoom(passportId,outPassportId);
    	}
    }
    
    private void sendMessageOutRoom(long passportId,long outPassportId){
    	PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(passportId);
		if(playerCacheInfo != null){
			String serverId = playerCacheInfo.getServerId();
			OutRoom message = new OutRoom();
			message.setPassportId(passportId);
			message.setOutPassportId(outPassportId);
			Globals.getRedisService().sendRedisMsgToServer(serverId, message);
		}else{
			log.error("SlotRoomService.sendOutRoom() 退出房间 广播的玩家ID 【"+passportId+"】 不存在");
		}
		
    }
    
    //属性变化调用==================================
    public void change2(Player player){
      if(player.getHuman().getSlotRoomId().equals(""))return;
      String[] ids = getRoomMemeber(player);
      notice2_3Roles(player,ids,true);
    }
    
    public void change3(Player player){
    if(player.getHuman().getSlotRoomId().equals(""))return;
      String[] ids = getRoomMemeber(player);
      notice2_3Roles(player,ids,false);
    }
    
    public void change4(Player player,int type){
    	if(player.getHuman().getSlotRoomId().equals(""))return;
    	String[] ids = getRoomMemeber(player);
    	notice4Roles(player,ids,type);
    }
    //======================================
    
	private void notice2_3Roles(Player player, String[] ids,boolean fly) {
		Human human = player.getHuman();
		long passportId = player.getPassportId();
		String img = player.getImg();
		String countries = player.getCountries();
		String name = human.getName();
		int level = (int) human.getLevel();
		long gold = human.getGold();

		for (String id : ids) {
			if (id.equals(String.valueOf(passportId)))
				continue;
			if(StringUtils.isBlank(id)){
				continue;
			}
			long roleId = Long.valueOf(id);
			Player playerOther = Globals.getOnlinePlayerService().getPlayerByPassportId(roleId);
			if (playerOther != null) {// 玩家在当前服务器
				if(fly){
					sendRoomMessage2(playerOther, passportId, img, countries, level,name);
				}else{
					sendRoomMessage3(playerOther,passportId,gold);
				}
			} else {// 玩家没在当前服务器
				PlayerCacheInfo cacheplayer = Globals.getPlayerCacheService().getPlayerCacheById(roleId);
				if(cacheplayer != null){
					String serverid = cacheplayer.getServerId();
					if(fly){
						sendMessageRedis2(roleId,passportId,serverid,countries,img,level,name);
					}else{
						sendMessageRedis3(roleId,passportId,serverid,gold);
					}
				}
 
			}
		}
	}
	private void notice4Roles(Player player, String[] ids,int type) {
		
		long passportId = player.getPassportId();
		for (String id : ids) {
			
			long roleId = Long.valueOf(id);
			
			if (roleId == passportId)
				continue;
			
			Player playerOther = Globals.getOnlinePlayerService().getPlayerByPassportId(roleId);
			if (playerOther != null) {
				sendRoomMessage4(playerOther,passportId,type);
			} else {
				PlayerCacheInfo cacheplayer = Globals.getPlayerCacheService().getPlayerCacheById(roleId);
				if(cacheplayer != null){
					String serverid = cacheplayer.getServerId();
					sendMessageRedis4(roleId,passportId,serverid,type);
				}
 
			}
		}
	}
    
	private void sendMessageRedis2(long passportId,long playerId,String serverid,String countries,String img,int level,String name){
		ChangeInfo2 message = new ChangeInfo2();
		message.setPassportId(passportId);
		message.setCountries(countries);
		message.setImg(img);
		message.setLevel(level);
		message.setPlayerId(playerId);
		message.setName(name);
		Globals.getRedisService().sendRedisMsgToServer(serverid,message);
	}
	
	private void sendMessageRedis3(long passportId,long playerId,String serverid,long gold){
		ChangeInfo3 message = new ChangeInfo3();
		message.setPassportId(passportId);
		message.setPlayerId(playerId);
		message.setGold(gold);
		Globals.getRedisService().sendRedisMsgToServer(serverid,message);
	}
	
	private void sendMessageRedis4(long passportId,long playerId,String serverid,int bigAward){
		ChangeInfo4 message = new ChangeInfo4();
		message.setPassportId(passportId);
		message.setPlayerId(playerId);
		message.setBigAward(bigAward);
		Globals.getRedisService().sendRedisMsgToServer(serverid,message);
	}
	
	//============================================
	
	/**
	 * 获取老虎机房间人数
	 * @param player
	 * @param slotId
	 * @param roomId
	 * @return
	 */
	public int getRoomNum(int slotId,String roomId){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		String ids = "";
		Jedis jedis = pool.getResource();
		try{
			ids = jedis.hget(slotRoomID, roomId);
		}finally{
			jedis.close();
		}
		if(ids == null){
			return -1;
		}else{
			return ids.split(",").length;
		}
	}
	
	public void sendRoomGift(Player player,GCSlotRoomGift message,int slotId,String roomId,int charm){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		String ids = "";
		Jedis jedis = pool.getResource();
		try{
			ids = jedis.hget(slotRoomID, roomId);
		}finally{
			jedis.close();
		}
		
		int sendType = message.getSend_type();
		
		if(StringUtils.isNotEmpty(ids)){
			String[] roleIds = ids.split(",");
			for(String id : roleIds){
				long passId = Long.valueOf(id);
				//if(passId != player.getPassportId()){
					Player playeroth = Globals.getOnlinePlayerService().getPlayerByPassportId(passId);
					if(playeroth != null){
						playeroth.sendMessage(message);
						if(passId == player.getPassportId()){continue;}
						
						if(sendType == 0){
							long rece_playerId = message.getRece_playerId();
							if(rece_playerId == playeroth.getPassportId()){
								playeroth.getHuman().setGiftId(message.getGiftId());
								playeroth.getHuman().setModified();
							}
						}else{
							playeroth.getHuman().setGiftId(message.getGiftId());
							playeroth.getHuman().setModified();
						}
						
						//加魅力值
						String inDetailReason = MessageFormat.format(LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM.getReasonText(),message.getGiftId());
						playeroth.getHuman().giveMoney(charm, Currency.CHARM, true, LogReasons.CharmLogReason.ACCEPT_INTERACTIVE_ITEM, inDetailReason, message.getGiftId(), 1);
					}else{
						PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(passId);
						if(cache != null){
							Globals.getRedisService().sendRedisMsgToServer(cache.getServerId(), getRoomGift(message,charm,passId));
						}
					}
				//}
			}
			log.info("老虎机房间发送礼物   "+roleIds);
		}
	}
	
	
	private RoomGift getRoomGift(GCSlotRoomGift message,int charm,long passId){
		RoomGift rg = new RoomGift();
		rg.setGiftId(message.getGiftId());
		rg.setRece_playerId(message.getRece_playerId());
		rg.setSend_playerId(message.getSend_playerId());
		rg.setSend_type(message.getSend_type());
		rg.setPassid(passId);
		rg.setCharm(charm);
		return rg;
	}
	
	/**
	 * 进入游戏通知好友
	 * @param human
	 */
	public void notifyFriends(Human human){
		
		long passId = human.getPassportId();
		
		List<Friend> list = human.getHumanRelationManager().getFriendList();
		
		//PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(passId);
		//if(cache.getIsGame() == 0){
		for(Friend friend : list){
			    long friendId = friend.getFriendId();
				Player player =	Globals.getOnlinePlayerService().getPlayerByPassportId(friendId);
				if(player != null){
					player.sendMessage(sendFriendsRoom(passId));
				}else{
					PlayerCacheInfo friendIdcache = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
					if(friendIdcache != null){
						NotifyFriends message = new NotifyFriends();
						message.setFriendId(friendId);
						message.setPlayerId(passId);
						Globals.getRedisService().sendRedisMsgToServer(friendIdcache.getServerId(), message);
					}
				}
			
		   }
		//}
		

	}

	public GCFriendGame sendFriendsRoom(long passId){
		GCFriendGame message = new GCFriendGame();
		message.setFriendId(passId);
		return message;
	}
	
	/**
	 * 第32个老虎机(斯巴达) 小游戏专用的 参数，一个房间里所有的人 开炮的次数
	 */
	public void setRoomSlot32Bullet(int slotId,String roomId,int num) {
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String numStr = jedis.hget(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId);
			if(StringUtils.isBlank(numStr)){
				jedis.hset(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId,String.valueOf(num));
			}else{
				jedis.hset(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId,String.valueOf(Integer.valueOf(numStr).intValue()+num));
			}
		}finally{
			jedis.close();
//			jedis.close();
			
		}
	}
	/**
	 * 社交 的 设为0
	 * @param slotId
	 * @param roomId
	 * @param num
	 */
	public void setRoomSlot32BulletZero(int slotId,String roomId,int num){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String numStr = jedis.hget(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId);
			if(StringUtils.isNotBlank(numStr)){
				jedis.hset(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId,String.valueOf(0));
			}
		}finally{
			jedis.close();
//			jedis.close();
		}
	}
	
	public int getRoomBullet(int slotId,String roomId){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			String roomBullet = jedis.hget(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId);
			if(StringUtils.isBlank(roomBullet)){
				return 0;
			}else{
				return Integer.valueOf(roomBullet).intValue();
			}
		}catch(Exception e){
			Loggers.slotLogger.error("", e);
		}finally{
			jedis.close();
//			jedis.close();
		}
		return 0;
	}
	
	public void cleanRoomBullet(int slotId,String roomId){
		String slotRoomID = RedisSlotKey.slotRoomID.getKey()+slotId;
		Jedis jedis = pool.getResource();
		try{
			jedis.hset(slotRoomID, RedisSlotKey.bulletSlotRoom.getKey()+roomId,"0");
		}finally{
			jedis.close();
//			jedis.close();
		}
	}
}
