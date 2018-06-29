package com.gameserver.scene.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.heartbeat.HeartBeatAble;
import com.core.heartbeat.ITickable;
import com.core.util.Assert;
import com.gameserver.common.Globals;
import com.gameserver.scene.Scene;
import com.gameserver.texas.TexasRoom;
import com.gameserver.texas.TexasService;
import com.gameserver.texas.template.SngMatchTemplate;
import com.gameserver.texas.template.TexasRoomTemplate;

/**
 * 场景房间管理
 * @author wayne
 *
 */
public class SceneRoomManager implements ITickable,HeartBeatAble, InitializeRequired, DestroyRequired{
	
	private Logger logger = Loggers.texasRoomLogger;
	
	/** 当前场景对象 **/
	private Scene scene;

	/** 房间数组*/
	private Map<Integer,List<TexasRoom>> roomListMap = new HashMap<Integer,List<TexasRoom>>();
	/**sng房间*/
	private Map<Integer,List<TexasRoom>> sngRoomListMap = new HashMap<Integer,List<TexasRoom>>();
	/**vip房间*/
	private List<TexasRoom> vipRoomList = new ArrayList<TexasRoom>();
	
	/**德州服务*/
	private TexasService texasService;

	
	
	public SceneRoomManager(Scene scene) {
		this.scene = scene;
	//	this.maxRoomCount = maxRoomCount;
		
	}
	
	@Override
	public void init() {
	
		this.texasService = Globals.getTexasService();
		
		
		//初始化德州房间列表
		for(TexasRoomTemplate texasRoomTemplate :this.texasService.getTexasRoomTemplateList()){
			List<TexasRoom> tempList = new ArrayList<TexasRoom>();
			roomListMap.put(texasRoomTemplate.getId(),tempList);
		}
		
		//初始化sng房间列表
		for(SngMatchTemplate sngMatchTemplate : this.texasService.getSngMathTemplateList()){
			List<TexasRoom> tempList = new ArrayList<TexasRoom>();
			sngRoomListMap.put(sngMatchTemplate.getId(), tempList);
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 查找可加入的房间
	 */
	public TexasRoom searchRoom(int roomType){
		List<TexasRoom> roomList = roomListMap.get(roomType);
		Assert.notNull(roomList,"房间类型不存在");
		
		//查找有位置房间
		for( int i=0;i<roomList.size();i++){
			TexasRoom room = roomList.get(i);
			if(room.hasSeat())
			{
				return room;
			}
		}
	
		return null;
	}
	

	/**
	 * 查找德州房间
	 */
	public TexasRoom searchRoomByRoomId(long  roomId){
		
		
		for(List<TexasRoom> roomList:roomListMap.values()){
			for(TexasRoom room :roomList){
				if(room.getRid() == roomId)
					return room;
			}
		}
	
		return null;
	}
	
	/**
	 * 添加房间
	 * @param texasRoom
	 */
	public int addRoom(TexasRoom texasRoom)
	{
		List<TexasRoom> roomList = roomListMap.get(texasRoom.getId());
		Assert.notNull(roomList,"德州房间类型不存在");
		Assert.isTrue(roomList.add(texasRoom),"德州房间添加失败");
		return roomList.size()-1;
	}
	
	/**
	 * 移除房间
	 * @param texasRoom
	 */
	public void removeRoom(TexasRoom texasRoom)
	{
		List<TexasRoom> roomList = roomListMap.get(texasRoom.getId());
		Assert.notNullOrEmpty(roomList,"德州房间类型不存在");
		Assert.isTrue(roomList.remove(texasRoom),"德州房间移除失败");
	}
	
	/**
	 * 获得当前房间数
	 */
	
	public int getCurrentRoomCount(int roomType){
		List<TexasRoom> roomList = roomListMap.get(roomType);
		Assert.notNull(roomList,"德州房间类型不存在");
		return roomList.size();
	}
	
	/**
	 * 获取最大房间数
	 * @param roomType
	 * @return
	 */
	public int getMaxRoomCount(int roomType){
		TexasRoomTemplate texasRoomTemplate = Globals.getTemplateService().get(roomType, TexasRoomTemplate.class);
		return texasRoomTemplate.getOpenRoomNum();
	}
	
	
	/**
	 * 查找可加入的sng房间
	 */
	public TexasRoom searchSNGRoom(int sngId){
		List<TexasRoom> roomList = sngRoomListMap.get(sngId);
		Collections.sort(roomList,new Comparator<TexasRoom>(){

			@Override
			public int compare(TexasRoom arg0, TexasRoom arg1) {
				// TODO Auto-generated method stub
				int tempSize1 = arg0.getPlayerManager().getRoomPlayerList().size();
				int tempSize2 = arg1.getPlayerManager().getRoomPlayerList().size();
				if( tempSize1> tempSize2)
					return -1;
				else if(tempSize1 == tempSize2)
					return 0;
				else
					return 1;
			}
			
		});
		Assert.notNull(roomList,"房间类型不存在");
		
		//查找有位置房间
		for( int i=0;i<roomList.size();i++){
			TexasRoom room = roomList.get(i);
			if(room.getCurrentGames()!=0)
				continue;
			if(room.hasSeat())
			{
				return room;
			}
		}
	
		return null;
	}
	
	/**
	 * 添加sng 房间
	 */
	public int addSNGRoom(int sngId,TexasRoom texasRoom){
		List<TexasRoom> roomList = sngRoomListMap.get(sngId);
		Assert.notNull(roomList,"德州房间类型不存在");
		Assert.isTrue(roomList.add(texasRoom),"德州房间添加失败");
		return roomList.size()-1;
	}
	
	
	
	/**
	 * 获得当前房间数
	 */
	
	public int getCurrentSNGRoomCount(int sngId){
		List<TexasRoom> roomList = sngRoomListMap.get(sngId);
		Assert.notNull(roomList,"德州房间类型不存在");
		return roomList.size();
	}
	
	/**
	 * 查找vip房间
	 */
	public TexasRoom searchVipRoom(long vipId){
	
		//查找有位置房间
		for( int i=0;i<vipRoomList.size();i++){
			TexasRoom room = vipRoomList.get(i);
			if(room.getRid()!=vipId)
				continue;
			return room;
		}
	
		return null;
	}
	
	/**
	 * 添加vip房间
	 */
	public void addVipRoom(TexasRoom room){
		vipRoomList.add(room);
	}
	
	/**
	 * vip房间
	 */
	public List<TexasRoom> getVipRoomList(){
		return vipRoomList;
	}
	
	/**
	 * 获取最大房间数
	 * @param roomType
	 * @return
	 */
	public int getMaxSNGRoomCount(int sngId){
		SngMatchTemplate sngMatchTemplate = Globals.getTemplateService().get(sngId, SngMatchTemplate.class);
		return sngMatchTemplate.getOpenRoomNum();
	}
	
	


	/**
	 * 处理场景内玩家的输入输出消息
	 */
	public void tick() {
		

		for(List<TexasRoom> roomList :sngRoomListMap.values())
		{
			Iterator<TexasRoom> sngIterator = roomList.iterator();
			while(sngIterator.hasNext()){
				TexasRoom room = sngIterator.next();
				try{
					
					room.tick();
					
				}
				catch(Exception e){
					logger.error("德州房间["+room.getId()+"]异常",e);
					Globals.getLogService().sendExceptionLog(LogReasons.ExceptionLogReason.DEFAULT_EXCEPTION.getReasonText(),e);
					room.destroy();
					sngIterator.remove();
				}
			}
		
		}
		
		
		
		for(List<TexasRoom> roomList :roomListMap.values())
		{
		
			Iterator<TexasRoom> iterator = roomList.iterator();
			while(iterator.hasNext()){
				TexasRoom room = iterator.next();
				try{	
					room.tick();
				}
				catch(Exception e){
					logger.error("德州房间["+room.getId()+"]异常",e);
					Globals.getLogService().sendExceptionLog(LogReasons.ExceptionLogReason.DEFAULT_EXCEPTION.getReasonText(),e);
					room.destroy();
					iterator.remove();
				}
			}
		}
		
		Iterator<TexasRoom> vipIter = vipRoomList.iterator();
		while(vipIter.hasNext()){
			TexasRoom room = vipIter.next();
			try{
				if(room.isEmpty())
				{
					vipIter.remove();
					continue;
				}
				room.tick();
			}
			catch(Exception e){
				logger.error("德州房间["+room.getId()+"]异常",e);
				room.destroy();
				vipIter.remove();
			}
		}
	}
	
	/**
	 * 获取房间
	 * @param roomType
	 * @param index
	 * @return
	 */
	public TexasRoom roomForRoomTypeAndIndex(int roomType,int index)
	{
		
		List<TexasRoom> tempList = roomListMap.get(roomType);
		Assert.notNullOrEmpty(tempList,"没有查询的房间");
		Assert.isTrue(tempList.size()>index,"房间超过列表序列");
		return tempList.get(index);
	}
	
	@Override
	public void heartBeat() {
//		List<Integer> removePlayerId = new ArrayList<Integer>();
//		Integer playerId = null;
//
//		for (int i = 0; i < playerIds.size(); i++) {
//			playerId = playerIds.get(i);
//			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
//			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
//			if (player == null || player.getSceneId() != scene.getId()) {
//				removePlayerId.add(playerId);
//				continue;
//			}
//			player.heartBeat();
//		}
//
//		playerIds.removeAll(removePlayerId);
//		scenePlayerIds.removeAll(removePlayerId);
//
//		mirrorPlayerNum = playerIds.size();
	}
	



}
