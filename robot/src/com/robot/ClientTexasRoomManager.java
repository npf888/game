package com.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import com.robot.strategy.data.ClientTexasRoom;

public class ClientTexasRoomManager implements Callable<Integer> {

	private List<ClientTexasRoom> texasRoomList = new ArrayList<ClientTexasRoom>();

	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		for(ClientTexasRoom texasRoom : texasRoomList){
			texasRoom.tick();
		}
		return 0;
	}
	
	public void addTexasRoom(ClientTexasRoom room){
		texasRoomList.add(room);
	}
	
	public void removeTexasRoom(ClientTexasRoom room){
		texasRoomList.remove(room);
	}

}
