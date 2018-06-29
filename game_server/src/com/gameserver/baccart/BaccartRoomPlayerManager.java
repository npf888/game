package com.gameserver.baccart;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.player.Player;

/**
 * 百家乐玩家管理器
 * @author wayne
 *
 */
public class BaccartRoomPlayerManager {
	
	/**玩家列表*/
	private List<Player> playerList = new ArrayList<Player>();

	/**剩余位置集合*/
	private List<Integer> remainPositionList = new ArrayList<Integer>();
	
	
	public BaccartRoomPlayerManager(int seatNum){
		for(int i=0;i<seatNum;i++){
			remainPositionList.add(i);
		}
	}
	
	public void addPlayer(Player player){
	
		playerList.add(player);
	}
	
	public void removePlayer(Player player){
		playerList.remove(player);
	}
	
	public List<Player> getPlayerList(){
		return playerList;
	}
	
	
	/**
	 * 玩家数
	 * @return
	 */
	public int getPlayerNum(){
		return playerList.size();
	}
	

	
	public boolean hasPos(int pos){
		for(int tempPos : remainPositionList){
			if(pos == tempPos){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取位置
	 */
	public int assignPos(int pos){
		if(pos ==-1){
			if(remainPositionList.size()==0)
				return -1;
			return remainPositionList.remove(0);
		}
		
		for(int tempPos : remainPositionList){
			if(pos == tempPos){
				remainPositionList.remove((Integer)tempPos);
				return tempPos;
			}
		}
		return -1;
	
	}
	
	/**
	 * 归还位置
	 */
	public void returnPos(int pos){
		if(pos == -1)
			return;
		
		remainPositionList.add(pos);
	}
	
}
