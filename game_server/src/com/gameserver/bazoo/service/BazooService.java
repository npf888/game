package com.gameserver.bazoo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.bazoo.enums.RedisBazooKey;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛的 服务  用 redis 存储
 * @author JavaServer
 *
 */
public class BazooService implements InitializeRequired{
	private Logger logger = Loggers.BAZOO;
	/**
	 * 已经满的房间号
	 */
	private Map<Integer,List<String>> fullBazooRoomNumMap = new HashMap<Integer,List<String>>();
	
	/**
	 * 未满的的房间号
	 */
	private Map<Integer,List<String>> notFullBazooRoomNumMap = new HashMap<Integer,List<String>>();
	
	
	public void init() {
		//每次重启服务器这两个都清空
		fullBazooRoomNumMap.clear();
		notFullBazooRoomNumMap.clear();
		//根据配置来 初始化 每个bet场的房间
		List<Integer> betList = new ArrayList<Integer>();
		betList.add(10);
		betList.add(50);
		betList.add(100);
		for(int i=0;i<betList.size();i++){
			List<String> fullBazooRoomNumList = new ArrayList<String>();
			fullBazooRoomNumMap.put(betList.get(i), fullBazooRoomNumList);
			List<String> notFullBazooRoomNumList = new ArrayList<String>();
			notFullBazooRoomNumMap.put(betList.get(i), notFullBazooRoomNumList);
		}
		
		
	}


	/**
	 * 进入 房间
	 * @param player
	 * @param cgRoomEnter
	 */
	@SuppressWarnings("unchecked")
	public void enterBazooRoom(Player player, CGRoomEnter cgRoomEnter) {
		//当前的倍场
		int bet = cgRoomEnter.getBet();
		String bazooRoomBet =RedisBazooKey.bazooRoomNum.getKey()+bet;
		
		//组装一下房间号
		String bazooRoom = player.getHuman().getBazooRoom();
		String bazooRoomNum =RedisBazooKey.bazooRoomNum.getKey()+bazooRoom;
		logger.info("---bazooRoom-"+player.getPassportId()
				   +"---当前的bet:bazooRoomBet:"+bazooRoomBet
				   +"---当前的用户属性房间:bazooRoom:"+bazooRoom
				   +"---当前的房间号:bazooRoomNum:"+bazooRoomNum);
		//在redis创建房间 或者进入房间
		Jedis jedis = null;
		try{
			
			jedis = Globals.getRedisService().getJedisPool().getResource();
			//所有的 用户ID用","分割
			String ids = jedis.hget(bazooRoomBet, bazooRoomNum);
			//看下用户以前在的房间 还有没有自己， 如果有的话 就把 自己干掉
			if(StringUtils.isNotBlank(ids)){
				String[] idArr = ids.split(",");
				for(int i=0;i<idArr.length;i++){
					//如果存在 就杀掉自己
					if(idArr[i].equals(player.getPassportId())){
						//如果已经 爆满的房间里 包含 这个房间号 ，那么 就 把他删掉
						if(fullBazooRoomNumMap.get(bet).contains(bazooRoomNum)){
							fullBazooRoomNumMap.get(bet).remove(bazooRoomNum);
						}
						//如果未满的 房间 不 包含 这个 房间  就给他加上
						if(!notFullBazooRoomNumMap.get(bet).contains(bazooRoomNum)){
							notFullBazooRoomNumMap.get(bet).add(bazooRoomNum);
						}
						break;
					}
				}
			}
			
			//房间允许 的总数量  根据配置文件 来获取
			int totalRoomUserNum = 6;
			
			//进入 已存在 未满的房间
			if(notFullBazooRoomNumMap.get(bet).size() >0){
				//在这里 永远取 index=0的房间   
				String notFullbazooRoom =  notFullBazooRoomNumMap.get(bet).get(0);
				
				String notFullIds = jedis.hget(bazooRoomBet, notFullbazooRoom);
				String newIds = handlerIds(notFullIds,
						String.valueOf(player.getPassportId()),
						totalRoomUserNum,
						bet,
						notFullbazooRoom);
				//放到redis里
				if(StringUtils.isNotBlank(newIds)){
					jedis.hset(bazooRoomBet, notFullbazooRoom, newIds);
				}
				
				
			//没有 未满的 房间   创建新的房间
			}else{
				Map<String,String> bazooRoomMap = jedis.hgetAll(bazooRoomBet);
				List<Integer> keyList = new ArrayList<Integer>();
				for(String keys :bazooRoomMap.keySet()){
					keyList.add(Integer.valueOf(keys.substring(keyList.indexOf("-")+1,keys.length())));
				}
				Collections.sort(keyList, new Comparator<Integer>(){

					@Override
					public int compare(Integer o1, Integer o2) {
						if(o1.intValue()>o2.intValue()){
							return -1;
						}
						return 1;
					}
					
				});
				
				Integer newRoomNum = keyList.get(keyList.size()-1)+1;
				String newRoomNumKey =RedisBazooKey.bazooRoomNum.getKey()+newRoomNum;
				jedis.hset(bazooRoomBet, newRoomNumKey, player.getPassportId()+"");
				notFullBazooRoomNumMap.get(bet).add(newRoomNumKey);
			}
			
			
			
		}catch(Exception e){
			logger.error("redis 有问题了："+e);
		}finally{
			jedis.close();
		}
		
	}
	
	
	
	
	
	/**
	 * 以旧换新 
	 * @param ids
	 * @param id
	 * @param totalRoomUserNum 
	 * @return
	 */

	private String handlerIds(String ids,String id, int totalRoomUserNum,int bet,String notFullbazooRoom){
		
		if(StringUtils.isBlank(ids)){
			return null;
		}
		String[] idsArr = ids.split(",");
		boolean containId = false;
		for(int i=0;i<idsArr.length;i++){
			if(idsArr[i].contains(id)){
				containId=true;
				break;
			}
		}
		
		if(!containId){
			String newIds = "";
			for(int i=0;i<idsArr.length;i++){
				newIds+=idsArr[i]+",";
			}
			newIds+=id;
			//如果满了就将他 移除
			if((idsArr.length+1)==totalRoomUserNum){
				notFullBazooRoomNumMap.get(bet).remove(notFullbazooRoom);
				if(!fullBazooRoomNumMap.get(bet).contains(notFullbazooRoom)){
					fullBazooRoomNumMap.get(bet).add(notFullbazooRoom);
				}
			}
			return newIds;
		}else{
			return ids;
		}
	}


}
