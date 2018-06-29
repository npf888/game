package com.gameserver.bazoo.service.room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.service.showHand.Card;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.player.Player;

/**
 * 一个房间
 * @author JavaServer
 *
 */
public class Room {
	protected Logger logger = Loggers.BAZOO;
	
	//当前房间的状态
	private RoomStateInfo roomStateInfo;
	//房间状态  true 满了，false 没满
	private boolean fullOrNot;
	//房间号
	private RoomNumber roomNumber;
	//房间里的人
	private List<Player> players;
	//房间公共 的 , 用户 玩游戏的信息
	private PlayerInfo playerInfo;
	
	//房间密码
	private String password;
	private Player priRoomCreater;
	//参与游戏的总人数（不包括观光者）,创建 房间的时候 由配置文件 决定
	private int maxNum;
	//将要被删除的人
	List<Player> shuldBeDeletedPlayers = new ArrayList<Player>();
	
	
	public Room(String roomNumber,int maxNum){
		this.roomStateInfo= new RoomStateInfo();
		this.maxNum=maxNum;
		this.roomNumber=RoomNumber.toRoomNumber(roomNumber);
		fullOrNot=false;
		players=new ArrayList<Player>();
		playerInfo= new PlayerInfo();
	}
	
	
	
	
	
	/**
	 * 比对一下 房间号 是否相等
	 * @return
	 */
	public boolean compareRoomNumber(RoomNumber roomNumber){
//		logger.info("[无双吹牛---[比对房间号]][本房间："+this.getRoomNumber().toString()+"]");
//		logger.info("[无双吹牛---[比对房间号]][当前任："+roomNumber.toString()+"]");
		if(this.getRoomNumber().getModeType() != roomNumber.getModeType()){
			return false;
		}else if(this.getRoomNumber().getPubOrPri() != roomNumber.getPubOrPri()){
			return false;
		}else if(this.getRoomNumber().getBet() != roomNumber.getBet()){
			return false;
		}else if(this.getRoomNumber().getRoomNum() != roomNumber.getRoomNum()){
			return false;
		}else{
//			logger.info("[无双吹牛---[比对房间号]][返回值："+true+"]");
			return true;
		}
	}
	/**
	 * 比对一下 房间 到 bet场 这一步 是否相等 并且判断房间 是否已满 （主要是用于用户加入到 某个 bet场的 房间 使用）
	 * @return
	 */
	public boolean compareRoomNumberBet(RoomNumber roomNumber){
		
		if(this.getRoomNumber().getModeType() != roomNumber.getModeType()){
			return false;
		}else if(this.getRoomNumber().getPubOrPri() != roomNumber.getPubOrPri()){
			return false;
		}else if(this.getRoomNumber().getBet() != roomNumber.getBet()){
			return false;
		}else if((this.getPlayersNotPureWatch().size()>=this.getMaxNum())){//满了
			return false;
		}else{//没有满
			return true;
		}
	}
	
	
	/**
	 * 添加用户（如果存在就不要添加了）
	 */
	public void addPlayer(Player player){
		boolean exist = false;
		for(Player p:this.players){
			if(p.getPassportId() == player.getPassportId()){
				exist=true;
			}
		}
		//如果不存在就加上
		if(!exist){
			this.players.add(player);
		}
	}
	
	
	
	/**
	 * 获取相应房间的每个人的ID,并且用","连接起来
	 * @param player
	 * @return
	 */
	public String getRoomEveryIds(){
		
		String totalNum = "";
		for(int i=0;i<players.size();i++){
			Player p = players.get(i);
			if(p != null){
				totalNum+=p.getPassportId()+",";
			}
		}
		return totalNum;
	}
	
	
	/**
	 * 获取相应房间的总人数
	 * @param player
	 * @return
	 */
	public int getRoomTotalNum(){
		return players.size();
	}
	/**
	 * 获取 参与游戏的人数   USER_STATUS_WATCH=0    USER_STATUS_PARTIN=1;
	 * 
	 * 获取 参观者的人数  USER_STATUS_PURE_WATCH=2
	 * @param player
	 * @return
	 */
	public int getRoomTotalNum(int status){
		int totalNum=0;
		for(Player player:players){
			int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
			if(status == RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
				if(userStatus == RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
					totalNum++;
				}
			}else{
				if(userStatus != RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
					totalNum++;
				}
			}
		}
		return totalNum;
	}
	/**
	 * 获取正在 玩的人的 人数
	 * @return
	 */
	public int getRoomTotalNumPartIn(){
		int totalNum=0;
		for(Player player:players){
			int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
			if(userStatus == RoomEveryUserInfo.USER_STATUS_PARTIN){
				totalNum++;
			}
		}
		return totalNum;
	}
	
	/**
	 * 
	 * 获取 本局 紧紧是 partIn的用户
	 * @param player
	 * @return
	 */
	public List<Player> getPlayersPartIn(){
		List<Player> notPureWatchPlayers = new ArrayList<Player>();
		for(Player player:players){
			int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
			if(userStatus == RoomEveryUserInfo.USER_STATUS_PARTIN){
				notPureWatchPlayers.add(player);
			}
		}
		return notPureWatchPlayers;
	}
	/**
	 * 
	 * 获取 所有的非 纯粹的观光者
	 * @param player
	 * @return
	 */
	public List<Player> getPlayersNotPureWatch(){
		List<Player> notPureWatchPlayers = new ArrayList<Player>();
		for(Player player:players){
			int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
			if(userStatus != RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
				notPureWatchPlayers.add(player);
			}
		}
		return notPureWatchPlayers;
	}
	
	/**
	 * 获取最小的位置
	 * @return
	 */
	public int getLittleSeat(){
		
		List<Player> players = this.getPlayersNotPureWatch();
		List<Integer> seatList = new ArrayList<Integer>();
		for(Player player:players){
			seatList.add(player.getHuman().getBazooRoomEveryUserInfo().getSeat());
		}
		Collections.sort(seatList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1.intValue() > o2.intValue()){
					return 1;
				}else if(o1.intValue() < o2.intValue()){
					return -1;
				}else{
					return 0;
				}
			}
		});
		int littleNullSeat =0 ;
		for(Integer seat:seatList){
			if(littleNullSeat == seat.intValue()){
				littleNullSeat++;
			}else{
				break;
			}
		}
		
		return littleNullSeat;
	}
	
	

	
	/**
	 * 查看房间是否已满 true 满了，false 没有满
	 * 只查看  参与 和 将要参与 游戏的玩家 ，不包括纯粹的 观光者 
	 * @param player
	 * @return
	 */
	public boolean checkRoomFull(){
		int totalNum=0;
		for(Player player:players){
			int userStatus = player.getHuman().getBazooRoomEveryUserInfo().getUserStatus();
			if(userStatus != RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
				totalNum++;
			}
		}
		if(totalNum >= this.getMaxNum()){
			this.setFullOrNot(true);
		}else{
			this.setFullOrNot(false);
		}
		return this.isFullOrNot();
	}
	
	/**
	 * 
	 * 清理房间
	 * 在每一局 开始的时候调用
	 * 
	 * @param player
	 * @return
	 */
	public void cleanRoom(boolean refresh,boolean refreshSeat){
		//先删除 不在房间的用户  
		shuldBeDeletedPlayers.clear();
		Collection<Long> onlinePlayerUUIDs = Globals.getOnlinePlayerService().getAllOnlinePlayerRoleUUIDs();
		for(Player player:players){
			int isInRoom = player.getHuman().getBazooRoomEveryUserInfo().getIsInRoom();
			boolean isIn = false;
			//如果用户不在内存里了 删除掉
			for(Long roleId:onlinePlayerUUIDs){
				//这个循环单独用于无双吹牛
				Player p=Globals.getOnlinePlayerService().getPlayer(roleId);
				if(p.getPassportId() == player.getPassportId()){
					isIn=true;
				}
			}
			if(!isIn){
				shuldBeDeletedPlayers.add(player);
				continue;
			}
			if(isInRoom == RoomEveryUserInfo.USER_NOT_IN_ROOM){
				shuldBeDeletedPlayers.add(player);
			}
		}
		/**
		 * 如果 用户钱不够 就踢除(将要参与玩的用户)
		 * 经典 至少 bet*1 
		 * 牛牛 至少 bet*50
		 * 梭哈 至少 bet*20
		 */
		List<Player> notPureWatchPlayers = this.getPlayersNotPureWatch();
		for(Player p:notPureWatchPlayers){
			if(judgeGold(p.getHuman().getGold(),roomNumber)){
				shuldBeDeletedPlayers.add(p);
			}
		}
		players.removeAll(shuldBeDeletedPlayers);
		/**
		 * 重新给用户的 seat(位置) 排序
		 */
		List<Player> notPurePlayers = this.getPlayersNotPureWatch();
		Collections.sort(notPurePlayers, new Comparator<Player>(){

			@Override
			public int compare(Player o1, Player o2) {
				int o1Seat = o1.getHuman().getBazooRoomEveryUserInfo().getSeat();
			    int o2Seat = o2.getHuman().getBazooRoomEveryUserInfo().getSeat();
			    if(o1Seat > o2Seat){
			    	return 1;
			    }else if(o1Seat < o2Seat){
			    	return -1;
			    	
			    }else{
			    	return 0;
			    }
			}
			
		});
		for(int i=0;i<notPurePlayers.size();i++){
			Player p = notPurePlayers.get(i);
			if(refreshSeat){
				p.getHuman().getBazooRoomEveryUserInfo().setSeat(i);
			}
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().setShakeNum(0);
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getLostPlayers().clear();
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getWinPlayers().clear();
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getShakeIndexs().clear();
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getRedDiceValues().clear();
			
			//主要是梭哈模式这边需要清空
			p.getHuman().getBazooRoomEveryUserInfo().setMoney(0l);
			p.getHuman().getBazooRoomEveryUserInfo().setMultiple(0);;
			p.getHuman().getBazooRoomEveryUserInfo().getDiceValues().clear();
			p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().setCowNum(-1);
			p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().setCard(new Card());
			p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setKillNum(0);
		}
		//设置房间状态  是否已满
		checkRoomFull();
		//把 watch的用户 状态置为 partIn
		for(Player p:players){
			RoomEveryUserInfo roomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
			int userStatus = roomEveryUserInfo.getUserStatus();
			if(userStatus != RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
				roomEveryUserInfo.setUserStatus(RoomEveryUserInfo.USER_STATUS_PARTIN);
			}
		}
		
		// 初始化 房间总信息 
		this.getPlayerInfo().init(refresh);
		//添加机器人
		Globals.getBazooRpcService().cleanRobot(this.getRoomNumber().toString());
	}
	
	public static boolean judgeGold(long gold,RoomNumber roomNumber){
		int modeType = roomNumber.getModeType();
		int bet = roomNumber.getBet();
		if(modeType == RoomNumber.MODE_TYPE_CLASSICAL){
			if(gold < bet*1){
				return true;
			}
		}else if(modeType == RoomNumber.MODE_TYPE_COW){
			if(gold < bet*50){
				return true;
			}
		}else if(modeType == RoomNumber.MODE_TYPE_SHOWHAND){
			if(gold < bet*20){
				return true;
			}
		}
		
		return false;
	}
	public static long getNeedGold(RoomNumber roomNumber){
		int modeType = roomNumber.getModeType();
		int bet = roomNumber.getBet();
		if(modeType == RoomNumber.MODE_TYPE_CLASSICAL){
			return bet;
		}else if(modeType == RoomNumber.MODE_TYPE_COW){
			return bet*50;
		}else if(modeType == RoomNumber.MODE_TYPE_SHOWHAND){
			return bet*20;
		}
		
		return bet;
	}
	/**
	 * 用户中途 退出 只需要 置一个 状态
	 * 但是如果是游客 就 直接把他删除掉
	 * @param player
	 */
	public void deletePlayer(Player player){
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[退出房间-修改状态-1]---[当前用户::"+player.getPassportId()+"]");
		if(this.players.contains(player)){
			RoomEveryUserInfo roomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[退出房间-修改状态-2]---[getIsInRoom::"+roomEveryUserInfo.getIsInRoom()+"][USER_IN_ROOM:"+RoomEveryUserInfo.USER_IN_ROOM+"]");
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[退出房间-修改状态-3]---[getIsInRoom::"+(roomEveryUserInfo.getIsInRoom() == RoomEveryUserInfo.USER_IN_ROOM)+"]");
			if(roomEveryUserInfo.getIsInRoom() == RoomEveryUserInfo.USER_IN_ROOM){
				roomEveryUserInfo.setIsInRoom(RoomEveryUserInfo.USER_NOT_IN_ROOM,player.getPassportId());
				logger.info("[无双吹牛"+roomNumber.toString()+"]---[退出房间-修改状态-4]---[当前用户::"+player.getPassportId()+"]");
				
				
				// 用户进去是 游客 退出的时候 还是游客 就 直接把删除掉
				if(roomEveryUserInfo.getUserStatus() != RoomEveryUserInfo.USER_STATUS_PARTIN){
					this.getPlayers().remove(player);
					GCRoomOut GCRoomOut = new GCRoomOut();
					GCRoomOut.setBeRemovedPassportIds(new long[]{player.getPassportId()});
					for(Player p:this.getPlayers()){
						p.sendMessage(GCRoomOut);
					}
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 获取下一个摇色子的用户
	 * @param roomPlayerList
	 * @return
	 */
	public Player getNextPlayer(Player player) {
		for(int n=0;n<players.size();n++){
			int playerSeat = player.getHuman().getBazooRoomEveryUserInfo().getSeat();
			int seat = (playerSeat+n+1)%players.size();
			for(Player nextPlayer:players){
				if(nextPlayer.getHuman().getBazooRoomEveryUserInfo().getSeat() == seat){
					//当前用户 是处于观战状态  或者是 纯粹的 观看者(只看 不参与游戏的)
					if(nextPlayer.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == com.gameserver.bazoo.service.room.RoomEveryUserInfo.USER_STATUS_WATCH
							||nextPlayer.getHuman().getBazooRoomEveryUserInfo().getUserStatus() == com.gameserver.bazoo.service.room.RoomEveryUserInfo.USER_STATUS_PURE_WATCH){
						break;
					}
					logger.info("[无双吹牛]---[开始玩家]---[座位]---[playerSeat:"+playerSeat+"-seat"+seat+"]");
					return nextPlayer;
				}
				
			}
		}
		return null;
	}
	
	/**
	 * 通过ID查找用户
	 * @param passportId
	 * @return
	 */
	public Player getPlayerById(long passportId) {
		for(Player player:players){
			if(player.getPassportId() == passportId){
				return player;
			}
		}
		return null;
	}

	
	
	public RoomNumber getRoomNumber() {
		return roomNumber;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
	public boolean isFullOrNot() {
		return fullOrNot;
	}
	public void setFullOrNot(boolean fullOrNot) {
		this.fullOrNot = fullOrNot;
	}
	public int getMaxNum() {
		return maxNum;
	}

	public RoomStateInfo getRoomStateInfo() {
		return roomStateInfo;
	}

	public void setRoomStateInfo(RoomStateInfo roomStateInfo) {
		this.roomStateInfo = roomStateInfo;
	}

	public List<Player> getShuldBeDeletedPlayers() {
		return shuldBeDeletedPlayers;
	}

	public void setShuldBeDeletedPlayers(List<Player> shuldBeDeletedPlayers) {
		this.shuldBeDeletedPlayers = shuldBeDeletedPlayers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Player getPriRoomCreater() {
		return priRoomCreater;
	}


	public void setPriRoomCreater(Player priRoomCreater) {
		this.priRoomCreater = priRoomCreater;
	}

	
	


	

	public void sendMessage(List<Player> roomPlayerList,GCMessage msg){
		for(int j=0;j<roomPlayerList.size();j++){
			Player pp = roomPlayerList.get(j);
			if(pp.getHuman().getBazooRoomEveryUserInfo().getIsInRoom() != RoomEveryUserInfo.USER_NOT_IN_ROOM){
				pp.sendMessage(msg);
			}
		}
	}






	
	
	
	
	
}
