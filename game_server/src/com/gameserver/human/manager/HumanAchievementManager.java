package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanAchievementEntity;
import com.gameserver.achievement.HumanAchievement;
import com.gameserver.achievement.enums.AchState;
import com.gameserver.achievement.enums.ParamType;
import com.gameserver.achievement.enums.ServerType;
import com.gameserver.achievement.enums.SmalType;
import com.gameserver.achievement.pojo.CompleteState;
import com.gameserver.achievement.template.AchievementTemplate;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;

public class HumanAchievementManager implements RoleDataHolder ,InitializeRequired{
	
	private Human owner;
	
	private HumanAchievement achievement;
	
	
	public HumanAchievementManager(Human owner){
		this.owner = owner;
	}
	
	
	/**
	 * 加载数据库数据到内存
	 */
	public void load() {
		HumanAchievementEntity entity =  Globals.getDaoService().getHumanAchievementDao().getAchievementEntity(owner.getPassportId());
		
		achievement = new HumanAchievement();
		achievement.setOwner(owner);
		if(entity == null){
			long now = Globals.getTimeService().now();
			achievement.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.ACHIEVEMENT));
			achievement.setCharId(owner.getPassportId());
			achievement.setAchDateMap(Globals.getHumanAchievementServer().getProgData());
			achievement.setAchValueMap(Globals.getHumanAchievementServer().getAchValueMap());
			achievement.setSlotRotate(0);
			achievement.setSlotWin(0);
			achievement.setSlotSingleWin(0);
			achievement.setSlotWinNum(0);
			achievement.setCreateTime(now);
			achievement.setInDb(false);
			achievement.active();
			achievement.setModified();
			return;
		}
		//Loggers.achievementLogger.info(JSON.toJSONString(Globals.getHumanAchievementServer().getProgData()));
		achievement.fromEntity(entity);
		  
	}
	
	/**
	 * 校验
	 */
	@Override
	public void init() {
		
        Map<Integer, CompleteState> achValue = achievement.getAchValueMap();
		
		Map<Integer, Map<Integer, String>> achDate = achievement.getAchDateMap();
		
		boolean fly = false;
		for(Entry<Integer, CompleteState> en : Globals.getHumanAchievementServer().getAchValueMap().entrySet()){
			        int id = en.getKey();
			        CompleteState cs = en.getValue();
			        
			        if(!achValue.containsKey(id)){
			        	achValue.put(id, cs);
			        	fly = true;
			        } 
		}
		
		for(Entry<Integer, Map<Integer, String>> en : Globals.getHumanAchievementServer().getProgData().entrySet()){
			int serverType = en.getKey();
			
			 Map<Integer, String> map =  en.getValue();
			 
			 if(!achDate.containsKey(serverType)){
				 achDate.put(serverType, map);
				 fly = true;
			 }else{
				 Map<Integer, String> mapDate =   achDate.get(serverType);
				 
				 for(Entry<Integer, String> en1 : map.entrySet()){
					 int smallType = en1.getKey();
					 String value = en1.getValue();
					 if(!mapDate.containsKey(smallType)){
						 mapDate.put(smallType, value);
						 fly = true;
					 }
				 }
			 }
			
		}
		
		if(fly){
			achievement.setModified();
		}
	}
	
	

	@Override
	public void checkAfterRoleLoad() {
	}

	@Override
	public void checkBeforeRoleEnter() {
	}
	//===================================================
	
	public HumanAchievement getHumanAchievement(){
		return achievement;
	}
	//======================================================

	/**
	 * 登陆的时候调用
	 */
	public void updateLogin(){
		//判断是否是同一天登陆
		long lastLoginTime = this.owner.getLastLogoutTime();
		if(lastLoginTime == 0){
			lastLoginTime = this.owner.getLastLoginTime();
			this.owner.setLastLogoutTime(lastLoginTime);
		}
		long createTime = this.owner.getCreateTime();
		long now = Globals.getTimeService().now();
		
		//累计数据 （大类型  小类型   数据）
		Map<Integer, Map<Integer, String>> achDate = achievement.getAchDateMap();
		String value = achDate.get(ServerType.ServerType50.getIndex()).get(SmalType.SmalType1.getIndex());
		
		if(value.trim().equals("") && TimeUtils.isSameDay(now, createTime)){
			changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType1.getIndex(),"","");
			Globals.getActivityService().everyDay(this.owner);
		}else{
			if(!TimeUtils.isSameDay(now, lastLoginTime)){
				changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType1.getIndex(),"","");
				Globals.getActivityService().everyDay(this.owner);
			}
		}
		/**
		 * 处理好友请求的
		 */
		try{
			Globals.getActivityService().loginCheckRequestFriend(this.owner);
		}catch(Exception e){
			Loggers.achievementLogger.error("", e);
		}
		
	}
	
	/**
	 * 等级成就
	 */
	public void updateLevel(){
		long level = owner.getLevel();
		changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType2.getIndex(),String.valueOf(level),"");
	}
	
	/**
	 * 排行榜排名成就
	 * 登陆的时候查看昨天的排行榜
	 */
	public void updateRank(){
		//判断是否是同一天登陆
		long lastLoginTime = this.owner.getLastLoginTime();
		long now = Globals.getTimeService().now();	
		if(!TimeUtils.isSameDay(now, lastLoginTime)){//一天只查看一次
			String[] strs = Globals.getRankNewServer().getHumanRank(owner.getPassportId());
			changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType3.getIndex(),strs[0],"");
			changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType4.getIndex(),strs[1],"");
		}
	}
	
	/**
	 * VIP 等级成就
	 */
	public void updateVipLevel(){
		int vip = this.owner.getHumanVipNewManager().getVipLv();
		changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType5.getIndex(),String.valueOf(vip),"");
	}
	
	/**
	 * 首次充值
	 */
	public void updateFirstCharge(){
		changeAchDateMap(ServerType.ServerType50.getIndex(),SmalType.SmalType6.getIndex(),"","");
	}
	
	
	/**
	 * 好友成就
	 */
	public void updateFriend(){
		int friendSize = owner.getHumanRelationManager().getFriendList().size();
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType20.getIndex(),String.valueOf(friendSize),"");
	}
	
	/**
	 * 赠送好友礼物次数
	 */
	public void updateGiftGiving(){
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType21.getIndex(),"","");
	}
	
	/**
	 * 领取礼物
	 */
	public void updateReceiveGiving(){
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType22.getIndex(),"","");
	}
	
	/**
	 * FB 邀请
	 */
	public void updateFaceBook(){
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType23.getIndex(),"","");
	}
	
	/**
	 * 单发道具
	 */
	public void updateSingleProps(){
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType18.getIndex(),"","");
	}
	
	/**
	 * 群发道具
	 */
	public void updateAllProps(){
		changeAchDateMap(ServerType.ServerType51.getIndex(),SmalType.SmalType19.getIndex(),"","");
	}
	
    //  ==========================================================================================
	
	/**
	 * 全部游戏累计赢得筹码 11
	 * @param win
	 */
	public void updateAllGameWin(long win){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType7.getIndex(),String.valueOf(win),"");
	}
	
	/**
	 * 全部游戏累计押注筹码 11
	 * @param win
	 */
	public void updateAllGameBet(long bet){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType8.getIndex(),String.valueOf(bet),"");
	}
	
	/**
	 * 全部游戏累计次数 11
	 * @param win
	 */
	public void updateAllGamePlay(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType9.getIndex(),"","");
	}
	
	
	/**
	 * 全部游戏累计赢得次数 11
	 * @param win
	 */
	public void updateAllGameWinNum(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType10.getIndex(),"","");
	}
	
	/**
	 * 全部游戏单次赢固定值次数 11
	 * @param win
	 */
	public void updateAllGameSingleWin(long win){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType11.getIndex(),"",String.valueOf(win));
	}
	
	/**
	 * Big
	 * @param win
	 */
	public void updateAllGameBigWin(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType12.getIndex(),"","");
	}
	/**
	 * Mega
	 * @param win
	 */
	public void updateAllGameMega(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType13.getIndex(),"","");
	}
	/**
	 * Super
	 * @param win
	 */
	public void updateAllGameSuper(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType14.getIndex(),"","");
	}
	/**
	 * Eplc
	 * @param win
	 */
	public void updateAllGameEplc(){
		changeAchDateMap(ServerType.ServerType52.getIndex(),SmalType.SmalType15.getIndex(),"","");
	}
	
	//德州==========================================================
	
	/**
	 * 德州累计赢
	 * @param win
	 */
	public void updateTexasCumulativeWin(long win){
		changeAchDateMap(ServerType.ServerType2.getIndex(),SmalType.SmalType7.getIndex(),String.valueOf(win),"");
		updateAllGameWin(win);
		updateAllGameWinNum();
		updateAllGameSingleWin(win);
	}
	/**
	 * 德州累押注
	 * @param win
	 */
	public void updateTexasCumulativeBet(long bet){
		changeAchDateMap(ServerType.ServerType2.getIndex(),SmalType.SmalType8.getIndex(),String.valueOf(bet),"");
		
		updateAllGameBet(bet);
	}
	
	/**
	 * 德州累次数
	 * @param win
	 */
	public void updateTexasCumulativeNum(){
		changeAchDateMap(ServerType.ServerType2.getIndex(),SmalType.SmalType9.getIndex(),"","");
		updateAllGamePlay();
	}
	
	/**
	 * 牌型统计
	 * @param carType
	 */
	public void updateTexasCarType(int carType){
		changeAchDateMap(ServerType.ServerType2.getIndex(),SmalType.SmalType16.getIndex(),"",String.valueOf(carType));
	}
	
	//百家乐==========================================================
	/**
	 * 百家乐累计赢
	 * @param win
	 */
	public void updateBaccartCumulativeWin(long win){
		changeAchDateMap(ServerType.ServerType1.getIndex(),SmalType.SmalType7.getIndex(),String.valueOf(win),"");
		updateAllGameWin(win);
		updateAllGameWinNum();
		updateAllGameSingleWin(win);
	}
	
	/**
	 * 百家乐累押注
	 * @param win
	 */
	public void updateBaccartCumulativeBet(long bet){
		changeAchDateMap(ServerType.ServerType1.getIndex(),SmalType.SmalType8.getIndex(),String.valueOf(bet),"");
		updateAllGameBet(bet);
	}
	
	/**
	 * 百家乐累次数
	 * @param win
	 */
	public void updateBaccartCumulativeNum(){
		changeAchDateMap(ServerType.ServerType1.getIndex(),SmalType.SmalType9.getIndex(),"","");
		updateAllGamePlay();
	}
	//老虎机=============================================================
	
	/**
	 * 
	 * @param slotType
	 * @param win
	 */
	public void updateSlotWin(int slotType,long win){
		if(win <= 0)return;
		updateSlotCumulativeWin(slotType, win);
		updateSlotSingleWin(slotType, win);
		updateSlotCumulativeWinNum(slotType);
		
		updateAllGameWin(win);
		updateAllGameWinNum();
		updateAllGameSingleWin(win);
	}
	
	/**
	 * 老虎机累计赢筹码
	 * @param slotType 老虎机类型 11
	 * @param win
	 */
	public void updateSlotCumulativeWin(int slotType,long win){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		
		changeAchDateMap(slotLobbType,SmalType.SmalType7.getIndex(),String.valueOf(win),"");
	}
	
	/**
	 * 老虎机累计赢次数
	 * @param slotType 老虎机类型 11
	 * @param win
	 */
	public void updateSlotCumulativeWinNum(int slotType){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType10.getIndex(),"","");
	}
	
	
	/**
	 * 老虎机单次赢得筹码固定数量
	 * @param slotType 老虎机类型 11
	 * @param win
	 */
	public void updateSlotSingleWin(int slotType,long win){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType11.getIndex(),"",String.valueOf(win));
	}
	
	/**
	 * 老虎机累计押注 11
	 * @param win
	 */
	public void updateSlotCumulativeBet(int slotType,long bet){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType8.getIndex(),String.valueOf(bet),"");
		
		updateAllGameBet(bet);
	}
	
	/**
	 * 老虎机累次数  11
	 * @param win
	 */
	public void updateSlotCumulativeNum(int slotType){
		//大厅类型和ServerType 一样
	    int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType9.getIndex(),"","");
		updateAllGamePlay();
	}
	
	
	/**
	 * Big Win次数
	 * @param win
	 */
	public void updateSlotBigWinNum(int slotType){
		//大厅类型和ServerType 一样
	    int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
	    changeAchDateMap(slotLobbType,SmalType.SmalType12.getIndex(),"","");
	    updateAllGameBigWin();
	}
	
	/**
	 * Mega Win次数
	 * @param win
	 */
	public void updateSlotMegaWinNum(int slotType){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType13.getIndex(),"","");
		updateAllGameMega();
	}
	
	/**
	 * Super Win次数
	 * @param win
	 */
	public void updateSlotSuperWinNum(int slotType){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType14.getIndex(),"","");
		updateAllGameSuper();
	}
	
	/**
	 * Eplc Win次数
	 * @param win
	 */
	public void updateSlotEplcWinNum(int slotType){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType15.getIndex(),"","");
		updateAllGameEplc();
	}
	
	/**
	 *竞赛排名第一次数
	 * @param win 11
	 */
	public void updateSlotRanking(int slotType){
		//大厅类型和ServerType 一样
		int slotLobbType = Globals.getJackpotServer().getSlotType(slotType);
		changeAchDateMap(slotLobbType,SmalType.SmalType17.getIndex(),"","");
	}
	
	//======================================================================
	
	/**
	 * 
	 * @param win
	 * @param free false 免费
	 */
	public void updateSlotTotal(long win,boolean free){
		achievement.setSlotRotate(achievement.getSlotRotate()+1);
		if(!free){
			if(achievement.getSlotSingleWin() < win){
				achievement.setSlotSingleWin(win);
			}
			achievement.setSlotWin(achievement.getSlotWin()+win);
			
			if(win > 0){
				achievement.setSlotWinNum(achievement.getSlotWinNum()+1);
			}
		}
		achievement.setModified();
	}
	//=================================================================
	
	/**
	 * 条件累计
	 * @param serverType
	 * @param smallType
	 * @param value1
	 * @param value2
	 */
	public void changeAchDateMap(int serverType,int smallType,String value1,String value2){
		
		//累计数据 （大类型  小类型   数据）
		Map<Integer, Map<Integer, String>> achDate = achievement.getAchDateMap();
		
		if(achDate.containsKey(serverType)){
			
			Map<Integer, String> data = achDate.get(serverType);
			
			if(data.containsKey(smallType)){
				//累计值
				String valueData = data.get(smallType);
				boolean fly = false;
				
				int paramType = Globals.getHumanAchievementServer().getParamDate(serverType, smallType);
				
				if(paramType == ParamType.ParamType1.getIndex()){
					if(valueData.trim().equals("")){
						valueData = "0";
					}
					switch (SmalType.valueOf(smallType)) {
					case SmalType1://登陆
						//登陆天数累加
						int loginValue = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(loginValue));
						fly = true;
						break;
					case SmalType2://等级条件
						//等级直接覆盖
						data.put(smallType, value1);
						fly = true;
						break;
					/*case SmalType3://单次赢分排名
						//排名越小 月符合要求
						 if(Integer.parseInt(valueData) > Integer.parseInt(value1)){
							 data.put(smallType, value1);
							 fly = true;
						 }
						break;
					case SmalType4://总赢分排名
						//排名越小 月符合要求
						 if(Integer.parseInt(valueData) > Integer.parseInt(value1)){
							 data.put(smallType, value1);
							 fly = true;
						 }
						break;*/
					case SmalType5://VIP等级
						//VIP等级直接覆盖
						data.put(smallType, value1);
						fly = true;
						break;
					case SmalType6://首次充值
						//随便系一个值
						data.put(smallType, "1");
						fly = true;
						break;
					case SmalType7://累计赢得筹码
						//累计赢得筹码累加
						Long value7 = Long.valueOf(valueData)+Long.valueOf(value1);
						data.put(smallType, String.valueOf(value7));
						fly = true;
						break;
					case SmalType8://累计押注筹码
						//累计押注筹码累加
						Long value8 = Long.valueOf(valueData)+Long.valueOf(value1);
						data.put(smallType, String.valueOf(value8));
						fly = true;
						break;
					case SmalType9://累计游戏次数
						//累计游戏次数累加
						Long value9 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value9));
						fly = true;
						break;
					case SmalType10://老虎机累计赢得次数
						//老虎机累计赢得次数
						Long value10 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value10));
						fly = true;
						break;
					case SmalType12://BIG WIN
						//BIG WIN
						Long value12 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value12));
						fly = true;
						break;
					case SmalType13:
						Long value13 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value13));
						fly = true;
						break;
					case SmalType14:
						
						Long value14 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value14));
						fly = true;
						break;
					case SmalType15:
						Long value15 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value15));
						fly = true;
						break;
					case SmalType17:
						Long value17 = Long.valueOf(valueData)+1;
						data.put(smallType, String.valueOf(value17));
						fly = true;
						break;
					
					case SmalType18://单发互动道具
						//单发互动道具累加
						int  value18 = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(value18));
						fly = true;
						break;
					case SmalType19://群发互动道具
						//群发互动道具累加
						int  value19 = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(value19));
						fly = true;
						break;
					case SmalType20://好友数量
						  //新好友数量大于 当前好友数量才替换
						 if(Integer.parseInt(valueData) < Integer.parseInt(value1)){
							 data.put(smallType, value1);
							 fly = true;
						 }
						break;
					case SmalType21://送好友礼物次数
						//送好友礼物次数累加
						int giveValue = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(giveValue));
						fly = true;
						break;
					case SmalType22://领取好友礼物次数
						//领取好友礼物次数累加
						int  receiveValue = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(receiveValue));
						fly = true;
						break;
					case SmalType23://FB 邀请
						//FB 邀请累加
						int  fbValue = Integer.parseInt(valueData)+1;
						data.put(smallType, String.valueOf(fbValue));
						fly = true;
						break;
					default:
						break;
					}
				 
				}else if(paramType == ParamType.ParamType2.getIndex()){
					switch (SmalType.valueOf(smallType)) {
					case SmalType16://牌型
						Map<Integer,Integer> map = JSON.parseObject(valueData, HashMap.class);
						int carType = Integer.parseInt(value2);
						if(map.containsKey(carType)){
							map.put(carType, map.get(carType)+1);
							fly = true;
						}
						if(fly){
							data.put(smallType, JSON.toJSONString(map));
						}
						break;
					case SmalType11://单次赢取固定数量
						Map<Integer,Integer> map11 = JSON.parseObject(valueData, HashMap.class);
						int value11 = Integer.parseInt(value2);
						for(Entry<Integer,Integer> en : map11.entrySet()){
							      int v = en.getKey();
							      if(value11 >= v){
							    	  map11.put(v, en.getValue()+1);
							    	  fly = true;
							    }
						 }
						if(fly){
							data.put(smallType, JSON.toJSONString(map11));
						}
						break;
					case SmalType3:
						
						Map<Integer,Integer> map3 = JSON.parseObject(valueData, HashMap.class);
						int value3 = Integer.parseInt(value2);
						for(Entry<Integer,Integer> en : map3.entrySet()){
							      int v = en.getKey();
							      if(value3 >= v){
							    	  map3.put(v, en.getValue()+1);
							    	  fly = true;
							    }
						 }
						if(fly){
							data.put(smallType, JSON.toJSONString(map3));
						}
						
						break;
					case SmalType4:
						
						Map<Integer,Integer> map4 = JSON.parseObject(valueData, HashMap.class);
						int value4 = Integer.parseInt(value2);
						for(Entry<Integer,Integer> en : map4.entrySet()){
							      int v = en.getKey();
							      if(value4 >= v){
							    	  map4.put(v, en.getValue()+1);
							    	  fly = true;
							    }
						 }
						if(fly){
							data.put(smallType, JSON.toJSONString(map4));
						}
						
						break;
					default:
						break;
					}
				}
				if(fly){
			      //检查是否有满足成就的
				  changeAchValueMap(serverType,smallType);
				  achievement.setModified();
				//Loggers.achievementLogger.info(JSON.toJSONString(Globals.getHumanAchievementServer().getProgData()));
				}
			 
			}
		}
	}
	
	/**
	 * 条件检查是否完成
	 * @param serverType
	 * @param smallType
	 */
	public void changeAchValueMap(int serverType,int smallType){
		Map<Integer, Map<Integer, String>> achDate = achievement.getAchDateMap();
		
		Map<Integer, CompleteState> achValue = achievement.getAchValueMap();
		
		//当前值
		String currValue = achDate.get(serverType).get(smallType);
		
		//参数类型
		int paramType = Globals.getHumanAchievementServer().getParamDate(serverType, smallType);
		
		//获取这个大类型 小类型的成就
		List<AchievementTemplate> idList = Globals.getHumanAchievementServer().getIDS(serverType, smallType);
		
		//完成的成就ID
		List<Integer> completeIds = new ArrayList<Integer>();
	     boolean fly = false;
		for(AchievementTemplate template : idList){
			
			int id = template.getId();
			//达到条件的值1
			int condition1 = template.getCondition1();
			//条件2
			int condition2 = template.getCondition2();
	
			
			
			CompleteState cs = achValue.get(id);
			
			if(cs.getState() != AchState.AchState1.getIndex()){
				continue;
			}
			
			if(paramType == ParamType.ParamType1.getIndex()){
				
				//当前值
				int currValueInt = Integer.parseInt(currValue);
				
				if(smallType == SmalType.SmalType1.getIndex() ||smallType == SmalType.SmalType2.getIndex() || smallType == SmalType.SmalType5.getIndex() || smallType == SmalType.SmalType6.getIndex()
					|| smallType == SmalType.SmalType20.getIndex() || smallType == SmalType.SmalType21.getIndex() || smallType == SmalType.SmalType22.getIndex() || smallType == SmalType.SmalType23.getIndex()
					|| smallType == SmalType.SmalType18.getIndex()||smallType == SmalType.SmalType19.getIndex() || smallType == SmalType.SmalType7.getIndex()||smallType == SmalType.SmalType8.getIndex() || smallType == SmalType.SmalType9.getIndex()
					|| smallType == SmalType.SmalType10.getIndex() || smallType == SmalType.SmalType12.getIndex() || smallType == SmalType.SmalType13.getIndex()|| smallType == SmalType.SmalType14.getIndex() || smallType == SmalType.SmalType15.getIndex()
					|| smallType == SmalType.SmalType17.getIndex()){
					if(currValueInt >= condition1){
						//这个成就已经达到领取奖励的条件了
						cs.setState(AchState.AchState2.getIndex());
						long time = Globals.getTimeService().now();
						cs.setCompleteTime(time);
						completeIds.add(id);
						fly = true;
					}
				}/*else if(smallType == SmalType.SmalType3.getIndex() ||smallType == SmalType.SmalType4.getIndex()){
					if(currValueInt <= condition1){
						//这个成就已经达到领取奖励的条件了
						cs.setState(AchState.AchState2.getIndex());
						long time = Globals.getTimeService().now();
						cs.setCompleteTime(time);
						completeIds.add(id);
						fly = true;
					}
				}*/
				
			}else if(paramType == ParamType.ParamType2.getIndex()){
				
				if(smallType == SmalType.SmalType16.getIndex() || smallType == SmalType.SmalType11.getIndex() || smallType == SmalType.SmalType3.getIndex() ||smallType == SmalType.SmalType4.getIndex()){
					Map<Integer,Integer> map = JSON.parseObject(currValue, HashMap.class);
					if(map.containsKey(condition2)){
						int currV = map.get(condition2);
						if(currV >= condition1){
							cs.setState(AchState.AchState2.getIndex());
							long time = Globals.getTimeService().now();
							cs.setCompleteTime(time);
							completeIds.add(id);
							fly = true;
						}
					}
				}
			}
		
		}
		
		if(fly){
			achievement.setModified();
		}
	}

}
