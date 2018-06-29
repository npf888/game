package com.gameserver.bazoo.insideData;

import org.apache.commons.lang.StringUtils;

import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

/**
 * 无双吹牛的临时数据
 * @author JavaServer
 *
 */
public class BazooTemporaryData {

	//用户 选择的模式
	private RoomNumber roomNumber;
	private RoomEveryUserInfo RoomEveryUserInfo;
	
	public BazooTemporaryData(Human owner){
		if(StringUtils.isNotBlank(owner.getBazooRoom())){
			this.roomNumber=RoomNumber.toRoomNumber(owner.getBazooRoom());
		}else{
			this.roomNumber = new RoomNumber();
		}
		RoomEveryUserInfo = new RoomEveryUserInfo();
	}
	public RoomNumber getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(RoomNumber roomNumber) {
		this.roomNumber = roomNumber;
	}
	public RoomEveryUserInfo getRoomEveryUserInfo() {
		return RoomEveryUserInfo;
	}
	public void setRoomEveryUserInfo(RoomEveryUserInfo roomEveryUserInfo) {
		RoomEveryUserInfo = roomEveryUserInfo;
	}

	
	
	
}
