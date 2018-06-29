package com.gameserver.texas.manager;

import java.util.ArrayList;
import java.util.List;

import com.core.util.Assert;
import com.core.util.MathUtils;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.player.Player;
import com.gameserver.texas.enums.RoomPlayerState;

/**
 * 德州管理器
 * @author wayne
 *
 */
public class TexasRoomPlayerManager {
	
	

	/**坐下的玩家排序列表*/
	private List<Player> roomPlayerList = new ArrayList<Player>();
	/**等候玩家*/
	private List<Player> waitingPlayerList = new ArrayList<Player>();
	

	
	/**剩余位置集合*/
	private List<Integer> remainPositionList = new ArrayList<Integer>();
	
	
	
	public TexasRoomPlayerManager( int maxPlayerCount) {
		
		for(int i=0;i<maxPlayerCount;i++)
		{
			remainPositionList.add(i);
		}
	}
	
	/**
	 * 房间玩家信息
	 */
	public Player getRoomPlayerByPlayer(Player player)
	{
		for(Player roomPlayer: roomPlayerList)
		{
			if(roomPlayer == player)
				return roomPlayer;
		}
		return null;
	}
	
	/**
	 * 获得玩家列表
	 * 
	 * 获取坐下的玩家列表，也就是参与游戏的玩家，等待的玩家不算
	 */
	public List<Player> getRoomPlayerList()
	{
		return roomPlayerList;
	}

	/**
	 * 获得等待列表
	 * @return
	 */
	public List<Player> getWaitingPlayerList() {
		return waitingPlayerList;
	}



	/**
	 * 加入玩家
	 * @param playerId
	 */
	public Player addPlayer(Player player,int pos) {
		
		Assert.isTrue(!containPlayer(player),"玩家已经在桌上"); 
		
		//分配位置
		int len =remainPositionList.size();
		Assert.isTrue(len!=0,"没有位置了");
		
		if(pos == -1)
		{
		//可优化 变成随机位置
		  pos = remainPositionList.remove(len-1);
		}
		else
		{
			Assert.isTrue(remainPositionList.contains(pos),"位置不存在");
			remainPositionList.remove((Integer)pos);
		}
		
		HumanTexasManager humanTexasManager= player.getHuman().getHumanTexasManager();
		humanTexasManager.setPos(pos);
		//按位置插入
		int index = -1;
		for(int i=0;i<roomPlayerList.size();i++)
		{
			Player tempPlayer = roomPlayerList.get(i);
			if(humanTexasManager.getPos()<tempPlayer.getHuman().getHumanTexasManager().getPos())
			{
				break;
			}
			index = i;
		}
		
		roomPlayerList.add(index+1, player);
		
		return player ;
		
	}
	
	/**
	 * 加入玩家
	 * @param playerId
	 */
	public Player addWaitingPlayer(Player player) {
		Assert.isTrue(!containPlayer(player),"玩家已经在桌上"); 
		
		waitingPlayerList.add(player);
	
		return player ;
		
	}
	

	/**
	 * 移除等候玩家
	 * @param playerId
	 */
	public Player removeWaitingPlayer(Player player) {
	
		Assert.isTrue(!containPlayer(player),"玩家不在桌上"); 
		waitingPlayerList.remove(player);
	
		return player ;
		
	}
	
	/**
	 * 移除玩家
	 * @param playerId
	 */
	public void removePlayer(Player player) {
		HumanTexasManager humanTexasManager= player.getHuman().getHumanTexasManager();
		remainPositionList.add(humanTexasManager.getPos());
		roomPlayerList.remove(player);
	
	}

	/**
	 * 当前场景中是否包含某个玩家
	 * 
	 * @param pl
	 * @return
	 */
	public boolean containPlayer(Player player) {
		return this.roomPlayerList.contains(player);
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean containWaitingPlayer(Player player) {
		return this.waitingPlayerList.contains(player);
	}
	
	public int indexOfWaitingPlayer(Player player) {
		return this.waitingPlayerList.indexOf(player);
	}
	
	public Player removeWaitingPlayerAtIndex(int index)
	{
		return this.waitingPlayerList.remove(index);
	}
	
	/**
	 * 下一个玩家
	 */
	public Player getNextBetter(int pos){
		
		int start = roomPlayerList.size();
		for(int i=0;i< roomPlayerList.size();i++)
		{
			Player roomPlayer = roomPlayerList.get(i);
			HumanTexasManager humanTexasManager = roomPlayer.getHuman().getHumanTexasManager();
			if(humanTexasManager.getPos() > pos)
			{
				if(i<start)
				{
					start = i;
				}
				if( humanTexasManager.getPlayerState() == RoomPlayerState.GAMING)
					return roomPlayer;
			}
		}
		
		for(int i=0;i<start;i++)
		{
			Player roomPlayer = roomPlayerList.get(i);
			HumanTexasManager humanTexasManager = roomPlayer.getHuman().getHumanTexasManager();
		
			if( humanTexasManager.getPlayerState() == RoomPlayerState.GAMING)
				return roomPlayer;
			
		}
		
		return null;
	}

	
	/**
	 * 获取玩家靠座位
	 */
	public Player getRoomPlayerByPos(int pos)
	{
		for(Player roomPlayer  : roomPlayerList)
		{
			HumanTexasManager humanTexasManager = roomPlayer.getHuman().getHumanTexasManager();
			if(humanTexasManager.getPos() == pos)
				return roomPlayer;
		}
		return null;
	}
	

	/**
	 * 获取随机庄家
	 */
	public int getRandomBankerPos(){
		float randomIndex = MathUtils.random(0f, (float)roomPlayerList.size());
		return roomPlayerList.get((int)randomIndex).getHuman().getHumanTexasManager().getPos();
	}
	

}
