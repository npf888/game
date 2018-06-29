package com.gameserver.baccart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.common.model.Card;
import com.core.heartbeat.ITickable;
import com.core.uuid.UUIDType;
import com.db.model.BaccartRoomModelEntity;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.data.BaccartCoinSyncData;
import com.gameserver.baccart.data.BaccartJackpotData;
import com.gameserver.baccart.data.BaccartLightData;
import com.gameserver.baccart.data.BaccartPlayerData;
import com.gameserver.baccart.data.BaccartRoomData;
import com.gameserver.baccart.data.BaccartSettleData;
import com.gameserver.baccart.data.PearlRoadData;
import com.gameserver.baccart.enums.BaccartBetType;
import com.gameserver.baccart.interfaces.IBaccaratListener;
import com.gameserver.baccart.msg.GCBaccartBet;
import com.gameserver.baccart.msg.GCBaccartClearTable;
import com.gameserver.baccart.msg.GCBaccartComplementTip;
import com.gameserver.baccart.msg.GCBaccartExit;
import com.gameserver.baccart.msg.GCBaccartJackpot;
import com.gameserver.baccart.msg.GCBaccartJoin;
import com.gameserver.baccart.msg.GCBaccartLight;
import com.gameserver.baccart.msg.GCBaccartList;
import com.gameserver.baccart.msg.GCBaccartPlayerJackpot;
import com.gameserver.baccart.msg.GCBaccartSeat;
import com.gameserver.baccart.msg.GCBaccartSettle;
import com.gameserver.baccart.msg.GCBaccartShuffle;
import com.gameserver.baccart.msg.GCBaccartStand;
import com.gameserver.baccart.msg.GCBaccartStartBet;
import com.gameserver.baccart.msg.GCBaccartSyncJoin;
import com.gameserver.baccart.msg.GCHumanBaccartCoinsSync;
import com.gameserver.baccart.template.BaccaratConfigTemplate;
import com.gameserver.baccart.template.BaccaratRoomTemplate;
import com.gameserver.baccart.template.JackpotTemplate;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.lobby.enums.GameType;
import com.gameserver.player.Player;

/**
 * 百家乐服务
 * @author wayne
 *
 */
public class BaccartService implements InitializeRequired,AfterInitializeRequired,ITickable,DestroyRequired{

	
	private Logger logger = Loggers.baccartRoomLogger;
	private static final long BROADCAST_JACKPOT_MIN = 100000;
	
	private List<BaccartRoom> baccartRoomList= new ArrayList<BaccartRoom>();
	private List<IBaccaratListener> baccartListenerList = new ArrayList<IBaccaratListener>();
	
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub 
		
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
		for(BaccartRoom room :baccartRoomList){
			room.destroy();
		}
		this.baccartRoomList.clear();
	}
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		for(BaccaratRoomTemplate baccaratRoomTemplate : Globals.getTemplateService().getAll(BaccaratRoomTemplate.class).values()){
			BaccartRoom tempRoom = newBaccartRoom(baccaratRoomTemplate);
			baccartRoomList.add(tempRoom);
		}
		
		
	}
	
	/**
	 * 添加监听器
	 * @param texasLisner
	 */
	public void addListener(IBaccaratListener baccaratListener){
		baccartListenerList.add(baccaratListener);
	}
	
	/**
	 * 移除监听器
	 * @param texasLisner
	 */
	public void removeListener(IBaccaratListener baccaratListener){
		baccartListenerList.remove(baccaratListener);
	}
	
	private static BaccartRoom newBaccartRoom( BaccaratRoomTemplate baccaratRoomTemplate){
		long now = Globals.getTimeService().now();
		BaccartRoomModelEntity tempBaccartRoomModelEntity= Globals.getDaoService().getBaccartRoomModelDao().getBaccartRoomByRoomId(baccaratRoomTemplate.getId());
		BaccartRoomModel tempBaccartRoomModel = new BaccartRoomModel();
		if(tempBaccartRoomModelEntity==null){
			tempBaccartRoomModel.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.BACCARTROOMMODELID));
			tempBaccartRoomModel.setStock(baccaratRoomTemplate.getStock());
			tempBaccartRoomModel.setJackpot(baccaratRoomTemplate.getJackpot());
			tempBaccartRoomModel.setCreateTime(now);
			tempBaccartRoomModel.setInDb(false);
			tempBaccartRoomModel.setRoomId(baccaratRoomTemplate.getId());
			
			tempBaccartRoomModel.active();
			tempBaccartRoomModel.setModified();
		}else{
			tempBaccartRoomModel.fromEntity(tempBaccartRoomModelEntity);
		}
		BaccartRoom tempBaccartRoom = new BaccartRoom(tempBaccartRoomModel);
		tempBaccartRoom.setId(baccaratRoomTemplate.getId());
		tempBaccartRoom.setMaxCarry(baccaratRoomTemplate.getMaxCarry());
		tempBaccartRoom.setMinCarry(baccaratRoomTemplate.getMinCarry());
		tempBaccartRoom.setTotalNum(baccaratRoomTemplate.getMaxNum());
		tempBaccartRoom.setTaxRate(baccaratRoomTemplate.getTax());
		tempBaccartRoom.setOpenLv(baccaratRoomTemplate.getOpenLv());
		tempBaccartRoom.init();
		return tempBaccartRoom;
	
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
		
		Iterator<BaccartRoom> iter =  this.baccartRoomList.iterator();
		while(iter.hasNext()){
			BaccartRoom tempBaccartRoom = iter.next();
			try{
				tempBaccartRoom.tick();
				
			}catch(Exception e){
				logger.error("百家乐房间["+tempBaccartRoom.getId()+"]出现异常["+e+"]");
				tempBaccartRoom.destroy();
				iter.remove();
				
				BaccaratRoomTemplate tempBaccaratRoomTemplate= Globals.getTemplateService().get(tempBaccartRoom.getId(),BaccaratRoomTemplate.class);
				BaccartRoom tempRoom=newBaccartRoom(tempBaccaratRoomTemplate);
				baccartRoomList.add(tempRoom);
			}
		}
	}
	
	/**
	 * 百家乐玩家加入
	 */
	public void onBaccartRoomPlayerJoin(BaccartRoom room,Player player){
		
		GCBaccartJoin gcBaccartJoin= buildGCBaccartJoin(room);
		player.sendMessage(gcBaccartJoin);
		
		GCBaccartSyncJoin gcBaccartSyncJoin= new GCBaccartSyncJoin();
		gcBaccartSyncJoin.setPlayerData(BaccartPlayerData.convertFromPlayer(player));
		syncMsg(room,player,gcBaccartSyncJoin);
		
	}
	
	/**
	 * 房间信息
	 * @param room
	 * @return
	 */
	private GCBaccartJoin buildGCBaccartJoin(BaccartRoom room){
		BaccartBetData[] tempBaccartBetDataArr = new BaccartBetData[room.getBaccartBetDataMap().size()];
		Set<BaccartBetType> tempKeys=room.getBaccartBetDataMap().keySet();
		int tempI=0;
		for(BaccartBetType tempBaccartBetType:tempKeys){
			BaccartBetData tempBaccartBetData = new BaccartBetData();
			
			tempBaccartBetData.setBetNum(room.getBaccartBetDataMap().get(tempBaccartBetType));
			tempBaccartBetData.setBetType(tempBaccartBetType.getIndex());
			tempBaccartBetDataArr[tempI]= tempBaccartBetData;
			tempI++;
		}
		
		GCBaccartJoin gcBaccartJoin = new GCBaccartJoin();
		gcBaccartJoin.setBetDataList(tempBaccartBetDataArr);
		gcBaccartJoin.setPearlRoadDataList(room.getPearlRoadDataList().toArray(new PearlRoadData[room.getPearlRoadDataList().size()]));
		gcBaccartJoin.setRoomState(room.getBaccartRoomState().getIndex());
		gcBaccartJoin.setJackpot(room.getBaccarRoomModel().getJackpot());
		gcBaccartJoin.setRoomId(room.getId());
		gcBaccartJoin.setRemainCards(room.getBaccartDeck().getCardList().size());
		gcBaccartJoin.setRemainTime(room.getRemainTime());
		
		List<Player> tempPlayerList = room.getBaccartRoomPlayerManager().getPlayerList();
		BaccartPlayerData [] tempBaccartPlayerDataArr = new BaccartPlayerData[tempPlayerList.size()];
		for(int i=0;i<tempPlayerList.size();i++){
			tempBaccartPlayerDataArr[i] = BaccartPlayerData.convertFromPlayer(tempPlayerList.get(i));
		}
		gcBaccartJoin.setPlayerDataList(tempBaccartPlayerDataArr);
		return gcBaccartJoin;
	}
	
	
	
	/**
	 * 百家乐玩家坐下
	 */
	public void onBaccartRoomPlayerSeat(BaccartRoom room,Player player,int pos){
		GCBaccartSeat gcBaccartSeat = new GCBaccartSeat();
		gcBaccartSeat.setPlayerId(player.getPassportId());
		gcBaccartSeat.setPos(pos);
		broadcastMsg(room, gcBaccartSeat);
	}
	
	/**
	 * 百家乐玩家站起
	 */
	public void onBaccartRoomPlayerStand(BaccartRoom room,Player player){
		GCBaccartStand gcBaccartStand = new GCBaccartStand();
		gcBaccartStand.setPlayerId(player.getPassportId());
		broadcastMsg(room, gcBaccartStand);
	}
	
	/**
	 * 百家乐玩家离开
	 */
	public void onBaccartRoomPlayerExit(BaccartRoom room,Player player){
		GCBaccartExit gcBaccartExit = new GCBaccartExit();
		gcBaccartExit.setPlayerId(player.getPassportId());
		broadcastMsg(room, gcBaccartExit);
	}
	
	/**
	 * 百家乐玩家下注
	 */
	public void onBaccartRoomPlayerBet(BaccartRoom room,Player player,BaccartBetData[] baccartBetDataList){
				
		GCBaccartBet gcBaccartBet = new GCBaccartBet();
		gcBaccartBet.setPlayerId(player.getPassportId());
		gcBaccartBet.setBetDataList(baccartBetDataList);
		broadcastMsg(room, gcBaccartBet);
		
		//判断是不是第一次下注
		if(BaccartBetData.betNumForBaccartBetDataList(baccartBetDataList) == BaccartBetData.betNumForBaccartBetDataMap(player.getHuman().getHumanBaccartManager().getBaccartBetDataMap())){
			for(IBaccaratListener tempBaccaratListener : this.baccartListenerList){
				tempBaccaratListener.onBaccaratPlay(player.getHuman(), room.getId());
			}
		}
		
	}
	
	/**
	 * 百家乐jack
	 */
	public void onBaccartRoomPlayerJackpot(BaccartRoom room,List<BaccartJackpotData> jackpotDataList){
		
		GCBaccartPlayerJackpot gcBaccartPlayerJackpot = new GCBaccartPlayerJackpot();
		gcBaccartPlayerJackpot.setJackpotDataList(jackpotDataList.toArray(new BaccartJackpotData[jackpotDataList.size()]));
		broadcastMsg(room,gcBaccartPlayerJackpot);
		
	
		
	}
	
	/**
	 * 百家乐jack
	 */
	public void onBaccartRoomPlayerJackpotBroadcast(BaccartRoom room,List<BaccartJackpotData> jackpotDataList){
		
	
		for(BaccartJackpotData tempBaccartJackpotData : jackpotDataList){
			if(tempBaccartJackpotData.getJackpot()>=BROADCAST_JACKPOT_MIN){
				Player tempPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(tempBaccartJackpotData.getPlayerId());
				if(tempPlayer==null)
					continue;
				Globals.getNoticeService().broadcastNoticeMulti(LangConstants.JACKPOT_BROADCAST, String.valueOf(GameType.BACCART.getIndex()),tempPlayer.getHuman().getName(),tempPlayer.getHuman().getImg(),String.valueOf(tempBaccartJackpotData.getJackpot()));
				//Globals.getNoticeService().broadcastNoticeMulti(LangConstants.BACCART_JACKPOT_BROADCAST, tempPlayer.getHuman().getName(),String.valueOf(tempBaccartJackpotData.getJackpot()));
			}
		}
		
	}
	
	/**
	 * 百家乐洗牌
	 * @param room
	 */
	public void onBaccartRoomShuffle(BaccartRoom room,int remainCardNums){
		logger.info("百家乐房间["+room.getId()+"]洗牌");
		GCBaccartShuffle gcBaccartShuffle= new GCBaccartShuffle();
		gcBaccartShuffle.setRemainCards(remainCardNums);
		//广播信息
		broadcastMsg(room,gcBaccartShuffle);
	}
	
	/**
	 * 百家乐开始下注
	 */
	public void onBaccartRoomStartBet(BaccartRoom room){
		logger.info("百家乐房间["+room.getId()+"]开始下注");
		GCBaccartStartBet gcBaccartStartBet= new GCBaccartStartBet();
		//广播信息
		broadcastMsg(room,gcBaccartStartBet);
	}
	
	/**
	 * 百家乐结算
	 */
	public void onBaccartRoomSettle(BaccartRoom room,List<Card> bankerCardList,List<Card> playerCardList,PearlRoadData pearlRoadData,List<BaccartSettleData> baccartSettleDataList){
		logger.info("百家乐房间["+room.getId()+"]开始结算");
		GCBaccartSettle gcBaccartSettle = new GCBaccartSettle();
		gcBaccartSettle.setPearlRoadData(pearlRoadData);
		gcBaccartSettle.setBankerCardList(Card.cardArray(bankerCardList));
		gcBaccartSettle.setPlayerCardList(Card.cardArray(playerCardList));
		gcBaccartSettle.setSettleDataList(baccartSettleDataList.toArray(new BaccartSettleData[baccartSettleDataList.size()]));
		broadcastMsg(room,gcBaccartSettle);
		
		//结束
		for(Player player : room.getBaccartRoomPlayerManager().getPlayerList()){
		
			for(IBaccaratListener tempBaccaratListener : this.baccartListenerList){
				tempBaccaratListener.onBaccaratPlayFinished(player.getHuman(), room.getId(), 	player.getHuman().getHumanBaccartManager().getLastResult());
			}
		}
		
	}
	
	
	/**
	 * 百家乐jackpot
	 * @param baccartRoom
	 * @param jackpot
	 */
	public void onBaccartRoomJackpot(BaccartRoom baccartRoom, long jackpot) {
		// TODO Auto-generated method stub
		logger.info("百家乐房间["+baccartRoom.getId()+"]jackpot剩余["+jackpot+"]");
		GCBaccartJackpot gcBaccartJackpot = new GCBaccartJackpot();
		gcBaccartJackpot.setJackpot(jackpot);
		
		broadcastMsg(baccartRoom,gcBaccartJackpot);
		
		//this.onBaccartRoomBeaconChanged(baccartRoom);
	}
	
	/**
	 * 同步明灯信息
	 * @param baccartRoom
	 */
	public void onBaccartRoomBeaconChanged(BaccartRoom baccartRoom) {
		BaccartLightData [] tempBaccartLightDataArr = new BaccartLightData[baccartRoom.getBaccartRoomPlayerManager().getPlayerList().size()];
		int i=0;
		for(Player player:baccartRoom.getBaccartRoomPlayerManager().getPlayerList()){
			BaccartLightData tempBaccartLightData= BaccartLightData.convertFromPlayer(player);
			tempBaccartLightDataArr[i]=tempBaccartLightData;
			i++;
		}
		GCBaccartLight gcBaccartLight = new GCBaccartLight();
		gcBaccartLight.setLightDataList(tempBaccartLightDataArr);
		broadcastMsg(baccartRoom,gcBaccartLight);
	}
	
	/**
	 * 百家乐清理桌面
	 */
	public void onBaccartRoomClear(BaccartRoom room){
		
		logger.info("百家乐房间["+room.getId()+"]清理台面");
		GCBaccartClearTable gcBaccartClearTable= new GCBaccartClearTable();
		//广播信息
		broadcastMsg(room,gcBaccartClearTable);
		
		//snap baccart
		String detailReason = MessageFormat.format( LogReasons.BaccaratRoomLogReason.SNAP.getReasonText(), room.buildRoomLogForRemote());
		Globals.getLogService().sendBaccaratRoomLog(null, LogReasons.BaccaratRoomLogReason.SNAP,detailReason, room.getId());
	}
	
	/**
	 * 同步筹码
	 */
	public void onBaccartRoomCoinsSync(BaccartRoom baccartRoom,
			List<BaccartCoinSyncData> tempBaccartCoinSyncDataList) {
		// TODO Auto-generated method stub
		GCHumanBaccartCoinsSync gcHumanBaccartCoinsSync= new GCHumanBaccartCoinsSync();
		gcHumanBaccartCoinsSync.setBaccartCoinSyncDataList(tempBaccartCoinSyncDataList.toArray(new BaccartCoinSyncData[tempBaccartCoinSyncDataList.size()]));
		//广播信息
		broadcastMsg(baccartRoom,gcHumanBaccartCoinsSync);
		
	}
	
	/**
	 * 发送信息给所有玩家
	 */
	public void broadcastMsg(BaccartRoom room,GCMessage msg) {
		for(Player  player: room.getBaccartRoomPlayerManager().getPlayerList()){
			player.sendMessage(msg);
		}
	}

	/**
	 * 同步信息给玩家
	 */
	public void syncMsg(BaccartRoom room,Player roomPlayer, GCMessage msg) {
		for(Player  player: room.getBaccartRoomPlayerManager().getPlayerList()){
			if(player == roomPlayer)
				continue;
			player.sendMessage(msg);
		}
	}
	
	/**
	 * 百家乐房间
	 * @param roomId
	 * @return
	 */
	public BaccartRoom baccartRoomById(int roomId){
		for(BaccartRoom tempBaccartRoom:baccartRoomList){
			if(tempBaccartRoom.getId() == roomId)
				return tempBaccartRoom;
		}
		return null;
	}
	
	
	
	/**
	 * 根据百家乐最高彩金池值
	 * @return
	 */
	public long getbaccartJackpotbyLevel(){
		
		long jackpot = 0l;
		for(BaccartRoom tempBaccartRoom:baccartRoomList){
			long jack = tempBaccartRoom.getBaccarRoomModel().getJackpot();
			if(jackpot < jack){
				jackpot = jack;
			}
		}
		return jackpot;
	}

	/**
	 * 百家乐房间信息
	 * @return
	 */
	public GCBaccartList buildGCBaccartList(){
		GCBaccartList gcBaccartList = new GCBaccartList();
		List<BaccartRoomData> tempBaccartRoomDataList = new ArrayList<BaccartRoomData>();
		for(BaccartRoom tempBaccartRoom:baccartRoomList){
			BaccartRoomData tempBaccartRoomData = new BaccartRoomData();
			tempBaccartRoomData.setRoomId(tempBaccartRoom.getId());
			tempBaccartRoomData.setNum(tempBaccartRoom.getBaccartRoomPlayerManager().getPlayerNum());
			tempBaccartRoomData.setClosed(tempBaccartRoom.getBaccarRoomModel().getClosed());
			tempBaccartRoomData.setJackpot(tempBaccartRoom.getBaccarRoomModel().getJackpot());
			tempBaccartRoomDataList.add(tempBaccartRoomData);
		}
		gcBaccartList.setBaccartRoomDataList(tempBaccartRoomDataList.toArray(new BaccartRoomData[tempBaccartRoomDataList.size()]));
		return gcBaccartList;
	}

	/**
	 * 百家乐配置
	 * @return
	 */
	public BaccaratConfigTemplate getBaccartConfigTemplate(){
		return Globals.getTemplateService().get(1, BaccaratConfigTemplate.class);
	}
	
	/**
	 * jackpot
	 */
	public JackpotTemplate jackpotTemplateByWinNums(long winNum){
		for(JackpotTemplate tempJackpotTemplate:Globals.getTemplateService().getAll(JackpotTemplate.class).values()){
			if(tempJackpotTemplate.getUpLimit()==-1){
				if(tempJackpotTemplate.getDownLimit()<=winNum){
					return tempJackpotTemplate;
				}
			}else if(tempJackpotTemplate.getDownLimit()<=winNum && winNum<tempJackpotTemplate.getUpLimit()){
				return tempJackpotTemplate;
			}
		}
		return null;
	}

	public void onBaccartRoomPlayerComplementCoinsTip(BaccartRoom baccartRoom,Player player,
			long complementGold) {
		// TODO Auto-generated method stub
		GCBaccartComplementTip gcBaccartPlayerComplementTip=  new GCBaccartComplementTip();
		gcBaccartPlayerComplementTip.setComplement(complementGold);
		player.sendMessage(gcBaccartPlayerComplementTip);
	}
	
	/**
	 * 最合适的房间
	 * @param player
	 * @return
	 */
	public BaccartRoom baccartRoomForQuickStartByPlayer(Player player) {
		
		List<BaccartRoom> list = getAppropriate((int)player.getHuman().getLevel());
		
		for (int i= list.size()-1;i>=0;i--){
			BaccartRoom tempBaccartRoom = list.get(i);
			//未开启
			if(tempBaccartRoom.ifClose())
				continue;
			//最大携带 大于自身筹码的一半
			if(tempBaccartRoom.getMaxCarry()>player.getHuman().getGold()/2)
				continue;
			
	
			return tempBaccartRoom;
		}
		
		//第一个房间
		for (int i= 0;i<list.size();i++){
			BaccartRoom tempBaccartRoom = list.get(i);
			//未开启
			if(tempBaccartRoom.ifClose())
				continue;
			//最小携带 大于自身筹码的一半
			if(tempBaccartRoom.getMinCarry()>player.getHuman().getGold())
				continue;
		
	
			return tempBaccartRoom;
			
		}
		
		return null;
	
	}
	
	private List<BaccartRoom> getAppropriate(int humanLevel){
		List<BaccartRoom> list = new ArrayList<BaccartRoom>();
		for(BaccartRoom br : baccartRoomList){
			
			if(br.getOpenLv() <= humanLevel){
				list.add(br);
			}
		}
		return list;
	}



}
