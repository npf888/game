package com.gameserver.bazoo.service.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.BazooRoomCreateEntity;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;

/**
 * 经典模式中的 房间
 * @author JavaServer
 *
 */
public class RoomService {
	
	
		private Logger logger = Loggers.BAZOO;
		/**
		 * 所有 房间
		 */
		private List<Room> rooms = new CopyOnWriteArrayList<Room>();
		
		
		private AssistRoomService assistRoomService;
		
		private List<BazooRoomCreate> bazooRoomCreateList = new ArrayList<BazooRoomCreate>();
		
		public RoomService(AssistRoomService assistRoomService){
			this.assistRoomService=assistRoomService;
			init();
		}
		
		
		public void init(){
			List<BazooRoomCreateEntity> bazooRoomCreateEntityList = Globals.getDaoService().getBazooRoomCreateDao().getAllRoomNumberFromBazooRoomCreate();
			if(bazooRoomCreateEntityList != null && bazooRoomCreateEntityList.size()>0){
				for(BazooRoomCreateEntity entity :bazooRoomCreateEntityList){
					BazooRoomCreate bazooRoomCreate = new BazooRoomCreate();
					bazooRoomCreate.fromEntity(entity);
					bazooRoomCreateList.add(bazooRoomCreate);
				}
			}
		}
		
		
		
		/**
		 * 创建房间
		 */
		public RoomNumber createRoom(Player player,int maxNum){
			RoomNumber roomNumber = player.getHuman().getBazooRoomNumber();
			roomNumber.setRoomNum(this.getNextRoomNumNew(roomNumber));
			Room room = new Room(roomNumber.toString(),maxNum);
			rooms.add(room);
			logger.info("[无双吹牛]---[创建房间]---[成功]---[房间号::"+roomNumber.toString()+"]---[当前用户ID::"+player.getPassportId()+"]");
			room.getRoomStateInfo().setRoomState(RoomState.stateRoomEnter,null);
			Globals.getLogService().sendDiceClassicalRoomLog(player.getHuman(),
					LogReasons.DiceClassicalRoomLogReason.create, 
					LogReasons.DiceClassicalRoomLogReason.create.getReasonText(), 
					0, roomNumber.toString(), 
					LogReasons.DiceClassicalRoomLogReason.create.getReason(),
					room.getRoomTotalNum(), getRoomEveryIds(roomNumber));
			return roomNumber;
			
		}
		
		
		/**
		 * 创建房间时 获取下一个没有用过的房间号
		 */
		private int getNextRoomNum(RoomNumber curRoomNumber){
			int roomNum = 0;
			if(rooms.size()>0){
				for(Room room:rooms){
					RoomNumber roomNumber = room.getRoomNumber();
					if(roomNumber.getModeType() == curRoomNumber.getModeType()
							&& roomNumber.getPubOrPri() == curRoomNumber.getPubOrPri()
							&& roomNumber.getBet() == curRoomNumber.getBet()){
						if(roomNum<roomNumber.getRoomNum()){
							roomNum=roomNumber.getRoomNum();
						}
					}
				}
			}
			roomNum++;
			return roomNum;
		}
		
		
		/**
		 * 新的 获取房间号
		 * 创建房间时 获取下一个没有用过的房间号
		 */
		private int getNextRoomNumNew(RoomNumber curRoomNumber){
			int roomNum = 0;
			boolean exist = false;
			for(BazooRoomCreate bazooRoomCreate:this.getBazooRoomCreateList()){
				if(bazooRoomCreate.getModeTypePriPubBet().equals(curRoomNumber.toStringForNumber())){
					exist=true;
					//看看时间有没有隔一天（隔一天 摇重新开始）
					//如果房间号 超过 100 0000这么多个在从新开始
					int number = bazooRoomCreate.getNumber();
					if(number<1000000){
						roomNum = number+1;
					}else{
						bazooRoomCreate.setCreateTime(new Date());
					}
					bazooRoomCreate.setNumber(roomNum);
					bazooRoomCreate.setModified();
					break;
				}
			}
			
			if(!exist){
				BazooRoomCreate bazooRoomCreate = new BazooRoomCreate();
				bazooRoomCreate.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.BAZOOROOMCREATE));
				bazooRoomCreate.setCreateTime(new Date());
				bazooRoomCreate.setModeTypePriPubBet(curRoomNumber.toStringForNumber());
				bazooRoomCreate.setNumber(0);
				bazooRoomCreate.setInDb(false);
				bazooRoomCreate.active();
				bazooRoomCreate.setModified();
				this.getBazooRoomCreateList().add(bazooRoomCreate);
			}
			roomNum++;
			return roomNum;
		}
		
		
		/**
		 * 获取相应房间的每个人的ID,并且用","连接起来
		 * @param player
		 * @return
		 */
		public String getRoomEveryIds(RoomNumber roomNumber){
			
			Room room = this.getRoom(roomNumber);
			if(room != null){
				return room.getRoomEveryIds();
			}else{
				return null;
			}
		}
		
		/**
		 * 添加用户
		 */
		public boolean addUserToRoom(Player player,int maxNum){
			RoomEveryUserInfo curRoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
			curRoomEveryUserInfo.setIsInRoom(RoomEveryUserInfo.USER_IN_ROOM,player.getPassportId());
			RoomNumber curRoomNumber = player.getHuman().getBazooRoomNumber();
			//取出当前未满的房间
			Room notFullRoom = null;
			if(curRoomNumber.getPubOrPri() == RoomNumber.PUB_ROOM){//公共房间
				notFullRoom = this.getNotfullRoom(curRoomNumber);
			}else{//私人房间
				notFullRoom = this.getRoom(curRoomNumber);
			}
			//如果有 就把用户按顺序 加进去
			if(notFullRoom != null){
				List<Player> roomAllPlayers = notFullRoom.getPlayers();
				logger.info("[无双吹牛]---[添加用户]---[当前用户要进入的房间号::"+notFullRoom.getRoomNumber().toString()+"]");
				//如果当前房间存在 要添加的用户 则 直接返回
				for(Player p:roomAllPlayers){
					logger.info("[无双吹牛]---[添加用户]---[当前当前房间内的用户ID::"+p.getPassportId()+"]");
					if(p.getPassportId() == player.getPassportId()){
						logger.info("[无双吹牛]---[添加用户]---[当前用户已存在,不能再添加::"+player.getPassportId()+"]");
						return true;//用户已存在
					}
				}
				// 然后 把当前的用户 插入到 最小的 没有人占用的位置
				int littleNullSeat = notFullRoom.getLittleSeat();
				player.getHuman().getBazooRoomEveryUserInfo().setSeat(notFullRoom.getLittleSeat());
				logger.info("[无双吹牛"+notFullRoom.getRoomNumber().toString()+"]---[用户获取的位置:"+player.getPassportId()+"]---[位置::"+littleNullSeat+"]");
				curRoomNumber.setRoomNum(notFullRoom.getRoomNumber().getRoomNum());
				player.getHuman().setBazooRoom(curRoomNumber.toString());
				player.getHuman().setBazooRoomNumber(curRoomNumber);
				player.getHuman().setModified();
				roomAllPlayers.add(player);
				if(notFullRoom.getPlayersNotPureWatch().size() == 2){//设置成 匹配状态
					notFullRoom.getRoomStateInfo().setRoomState(RoomState.stateRoomMatching,null);
				}
				//修改用户 数据库的房间属性
				//给用户设置上房间号
				
				logger.info("[无双吹牛]---[进入房间]---[成功]---"
						  + "[房间号为:"+curRoomNumber.toString()+"]---"
						  + "[房间总人数:"+notFullRoom.getRoomTotalNum()+"]---"
						  + "[房间参与人数:"+notFullRoom.getRoomTotalNum(RoomEveryUserInfo.USER_STATUS_PARTIN)+"]---"
						  + "[房间参观人数:"+notFullRoom.getRoomTotalNum(RoomEveryUserInfo.USER_STATUS_PURE_WATCH)+"]---"
						  + "[当前用户的ID:"+player.getPassportId()+"]");
				
				//检查 房间是否已满
				notFullRoom.checkRoomFull();;
				
			//所有房间都已经 爆满 重新创建一个房间
			}else{
				createRoom(player,maxNum);
				//把自己添加进去
				addUserToRoom(player,maxNum);
			}
			
			return false;
			
		}
		
		/**
		 * 获取 第一个未满的房间
		 * @return
		 */
		private Room getNotfullRoom(RoomNumber cpRoomNumber){
			if(rooms.size()>0){
				for(Room room: rooms){
					if(room.compareRoomNumberBet(cpRoomNumber)){
						return room;
					}
				}
			}
			return null;
		}
		
		
		/**
		 * 某个模式 下有多少bet场 ,每个bet场 有多少人
		 * @param player
		 * @return
		 */
		public Map<Integer,Integer> getRoomMemByModeType(int modeType,List<Integer> betList) {
			// roomNumber   房间
			Map<String,List<Player>> betPlayerMap = new HashMap<String,List<Player>>();
			
			for(Room room:rooms){
				RoomNumber roomNumber = room.getRoomNumber();
				if(roomNumber.getModeType() == modeType && roomNumber.getPubOrPri() == RoomNumber.PUB_ROOM){//过滤出当前模式下所有的 公共房间
					List<Player> playerList = room.getPlayers();
					betPlayerMap.put(roomNumber.toString(), playerList);
				}
			}
			/**
			 * 遍历 每个 bet场 有多少人
			 */
			Map<Integer,Integer> betNum = new HashMap<Integer,Integer>();
			for(int i=0;i<betList.size();i++){
				int bet = betList.get(i);
				betNum.put(bet, 0);
			}
			//取出 相应模式下 所有的房间 的数量
			if(betPlayerMap !=null && betPlayerMap.size()>0){
				for(Entry<String,List<Player>> entry: betPlayerMap.entrySet()){
					RoomNumber roomNumber=RoomNumber.toRoomNumber(entry.getKey());
					List<Player> pList = entry.getValue();
					if(betNum.containsKey(roomNumber.getBet())){
						int num = betNum.get(roomNumber.getBet());
						betNum.put(roomNumber.getBet(), num+pList.size());
					}
				}
			}
			return betNum;
		}
		
		/**
		 * 每次开始新的 一局的时候 都要 判断一下用户 是否 还在房间里
		 * @return
		 */
		public List<Player> judgePlayerExist(RoomNumber roomNumber){
			List<Player> playerList = this.getRoom(roomNumber).getPlayers();
			List<Player> shouldDeletePlayerList = new ArrayList<Player>();
			if(playerList != null && playerList.size()>0){
				for(Player p:playerList){
					if(p.getHuman().getBazooRoomEveryUserInfo().getIsInRoom() == RoomEveryUserInfo.USER_NOT_IN_ROOM){//说明用户退出了房间
						shouldDeletePlayerList.add(p);
					}
				}
				playerList.removeAll(shouldDeletePlayerList);
			}
			return shouldDeletePlayerList;
			
		}
		
		
		/**
		 * 通过房间获取所有人
		 * @param player
		 * @return
		 */
		public Room getRoom(Player player){
			RoomNumber roomNumber = RoomNumber.toRoomNumber(player.getHuman().getBazooRoom());
			for(Room room:rooms){
				if(room.compareRoomNumber(roomNumber)){
					return room;
				}
			}
			return null;
		}
		
		
		/**
		 * 通过房间号获取房间
		 * @param player
		 * @return
		 */
		public Room getRoom(RoomNumber roomNumber){
			for(Room room:rooms){
				if(room.compareRoomNumber(roomNumber)){
					return room;
				}
			}
			return null;
		}
		/**
		 * 房间没有人了 就移除房间
		 * @param player
		 * @return
		 */
		public Room removeRoom(RoomNumber roomNumber){
			Room shuldBeRemoveRoom = null;
			for(Room room:rooms){
				if(room.compareRoomNumber(roomNumber)){
					//先clean
					room.cleanRoom(true,true);
					//玩游戏的人都走了 就剩下纯粹的观光者 或者没有人了  移除房间
					List<Player> notPureWatchPlayers = room.getPlayersNotPureWatch();
					if(notPureWatchPlayers.size()<=0){
						shuldBeRemoveRoom=room;
						break;
					}
				}
			}
			//没有人了
			if(shuldBeRemoveRoom != null){
				rooms.remove(shuldBeRemoveRoom);
			}
			return this.getRoom(roomNumber);
		}
		/**
		 * 纯粹的删除房间 
		 * @param roomNumber
		 * @return
		 */
		public Room removePurRoom(RoomNumber roomNumber){
			boolean shuldBeRemove =false;
			Room shuldBeRemoveRoom = null;
			for(Room room:rooms){
				if(room.compareRoomNumber(roomNumber)){
					//玩游戏的人都走了 就剩下纯粹的观光者 或者没有人了  移除房间
					List<Player> notPureWatchPlayers = room.getPlayersNotPureWatch();
					int notInRoomSize = 0;
					if(notPureWatchPlayers.size()==1){
						for(Player player:notPureWatchPlayers){
							int isInRoom = player.getHuman().getBazooRoomEveryUserInfo().getIsInRoom();
							if(isInRoom == RoomEveryUserInfo.USER_NOT_IN_ROOM){
								notInRoomSize+=1;
							}
						}
					}
					if(notInRoomSize == notPureWatchPlayers.size()){
						shuldBeRemove=true;
						shuldBeRemoveRoom=room;
					}
					
				}
			}
			//没有人了
			if(shuldBeRemove){
				rooms.remove(shuldBeRemoveRoom);
				
			}
			return this.getRoom(roomNumber);
		}
		
		
		
		
		public AssistRoomService getAssistRoomService() {
			return assistRoomService;
		}
		public void setAssistRoomService(AssistRoomService assistRoomService) {
			this.assistRoomService = assistRoomService;
		}




		/**
		 * 获取所有房间
		 */
		public List<Room> getRooms() {
			return this.rooms;
			
		}



		/**
		 * 强制删除某个房间 
		 * 不管任何 事情
		 * @param rNumber
		 */

		public void removeRoomForce(RoomNumber roomNumber) {
			Room shuldBeRemoveRoom = null;
			for(Room room:rooms){
				if(room.compareRoomNumber(roomNumber)){
					shuldBeRemoveRoom=room;
				}
			}
			//强制删除
			if(shuldBeRemoveRoom != null){
				rooms.remove(shuldBeRemoveRoom);
			}
			
		}


		public void addGoldForRobot(Room room){
			List<Player> players = room.getPlayersPartIn();
			for(Player player:players){
				//如果是机器人 同时 金币 又不够了 所以 加钱
				long gold = 1000000;
				long finalGold = gold*10-player.getHuman().getGold();
				if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT && finalGold>gold*5){
					//增加货币
					player.getHuman().giveMoney(finalGold, Currency.GOLD, true, LogReasons.GoldLogReason.ROBOT_GIVE_GOLD, LogReasons.GoldLogReason.ROBOT_GIVE_GOLD.getReasonText(), -1, 1);
				}
			}
		}
		
		
		public List<BazooRoomCreate> getBazooRoomCreateList() {
			return bazooRoomCreateList;
		}
		public void setBazooRoomCreateList(List<BazooRoomCreate> bazooRoomCreateList) {
			this.bazooRoomCreateList = bazooRoomCreateList;
		}
		

		

		
		
		
		
		
		
		
		
		
}
