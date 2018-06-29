package com.robot.strategy.data;
import java.util.ArrayList;
import java.util.List;


import com.gameserver.texas.enums.RoomPlayerState;


public class ClientTexasRoomPlayerManager {

	/**坐下的玩家排序列表*/
	private List<ClientRoomPlayer> roomPlayerList = new ArrayList<ClientRoomPlayer>();


	public ClientTexasRoomPlayerManager() {

	}
	

	
	/**
	 * 获得玩家列表
	 */
	public List<ClientRoomPlayer> getRoomPlayerList()
	{
		return roomPlayerList;
	}

	/**
	 * 加入玩家
	 * @param playerId
	 */
	public ClientRoomPlayer addPlayer(ClientRoomPlayer roomPlayer) {
		
		//按位置插入
		int index = -1;
		for(int i=0;i<roomPlayerList.size();i++)
		{
			ClientRoomPlayer tempPlayer = roomPlayerList.get(i);
			if(roomPlayer.getPos()<tempPlayer.getPos())
			{
				break;
			}
			index = i;
		}
		
		roomPlayerList.add(index+1, roomPlayer);
		
		return roomPlayer ;
		
	}
	
	/**
	 * 移除玩家
	 * @param playerId
	 */
	public void removePlayer(ClientRoomPlayer roomPlayer) {
	
		roomPlayerList.remove(roomPlayer);

	}


	/**
	 * 获取玩家靠座位
	 */
	public ClientRoomPlayer getRoomPlayerByPos(int pos)
	{
		for(ClientRoomPlayer roomPlayer  : roomPlayerList)
		{
			if(roomPlayer.getPos() == pos)
				return roomPlayer;
		}
		return null;
	}



	public ClientRoomPlayer getNextBetter(int pos) {
		
		int start = roomPlayerList.size();
		for(int i=0;i< roomPlayerList.size();i++)
		{
			ClientRoomPlayer roomPlayer = roomPlayerList.get(i);
			
			if(roomPlayer.getPos() > pos)
			{
				if(i<start)
				{
					start = i;
				}
				if( roomPlayer.getPlayerState() == RoomPlayerState.GAMING)
					return roomPlayer;
			}
		}
		
		for(int i=0;i<start;i++)
		{
			ClientRoomPlayer roomPlayer = roomPlayerList.get(i);
		
			if( roomPlayer.getPlayerState() == RoomPlayerState.GAMING)
				return roomPlayer;
			
		}
		
		return null;
	}
	

	/**
	 * 获取玩家靠座位
	 */
	public ClientRoomPlayer getRoomPlayerByPlayerId(long playerId)
	{
		for(ClientRoomPlayer roomPlayer  : roomPlayerList)
		{
			if(roomPlayer.getPlayerId() == playerId)
				return roomPlayer;
		}
		return null;
	}




}
