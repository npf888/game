package com.gameserver.bazoo.service.room;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.common.InitializeRequired;
import com.gameserver.bazoo.data.PriRoomData;
import com.gameserver.bazoo.msg.CGRoomCreate;
import com.gameserver.player.Player;
/**
 * 私人房间的信息处理
 * @author JavaServer
 *
 */
public class BazooPriService implements InitializeRequired{

	
	private RoomService roomService;
	private BazooPubService bazooPubService;
	public BazooPriService(BazooPubService bazooPubService){
		this.bazooPubService=bazooPubService;
		this.roomService = bazooPubService.getRoomService();
	}
	
	
	@Override
	public void init() {
		
	}

	
	
	
	/**
	 * 创建私人房间
	 * @param player
	 * @param cgRoomCreate
	 */
	public void priRoomCreate(Player player, CGRoomCreate cgRoomCreate) {
		RoomNumber roomNumber = player.getHuman().getBazooRoomNumber();
		roomNumber.setModeType(cgRoomCreate.getModeType());
		roomNumber.setBet(cgRoomCreate.getBet());
		roomNumber.setPubOrPri(RoomNumber.PRI_ROOM);
		roomNumber.setRoomNum(-1);//当前要创建 房间所以的-1 表示 这个房间不存在
	}
	
	
	
	
	/**
	 * 搜索私人房间 根据房间号搜索 房间
	 * @param roomNumber
	 */
	public boolean roomPriSearch(String roomNumber) {
		RoomNumber rNumber = RoomNumber.toRoomNumber(roomNumber);
		Room room = roomService.getRoom(rNumber);
		if(room != null){
			return true;
		}
		return false;
		
	}


	public PriRoomData[] listAllPriRooms() {
		List<Room> rooms = roomService.getRooms();
		//过滤出所有的私人房间
		List<Room> priRooms = new ArrayList<Room>();
		if(rooms != null){
			for(Room room:rooms){
				if(room.getRoomNumber().getPubOrPri() == RoomNumber.PRI_ROOM){
					priRooms.add(room);
				}
			}
		}
		return getPriRoomDataArr(priRooms);
		
	}
	
	public PriRoomData[] getPriRoomDataArr(List<Room> priRooms){
		PriRoomData[] PriRoomDataArr = new PriRoomData[priRooms.size()];
		if(priRooms!= null && priRooms.size() > 0){
			for(int i=0;i<priRooms.size();i++){
				Room room = priRooms.get(i);
				PriRoomData PriRoomData = new PriRoomData();
				PriRoomData.setCreater(room.getPriRoomCreater().getHuman().getName());
				PriRoomData.setCreaterPassportId(room.getPriRoomCreater().getPassportId());
				PriRoomData.setFlag(room.getPriRoomCreater().getHuman().getGirl());
				PriRoomData.setImg(room.getPriRoomCreater().getHuman().getImg());
				if(StringUtils.isBlank(room.getPassword())){
					PriRoomData.setIsNeedPassword(1);
				}else{
					PriRoomData.setIsNeedPassword(0);
				}
				PriRoomData.setModeType(room.getRoomNumber().getModeType());
				PriRoomData.setNumTotalNum(room.getPlayersNotPureWatch().size()+"/6");
				PriRoomData.setRoomNumber(room.getRoomNumber().toString());
				PriRoomData.setBet(room.getRoomNumber().getBet());
				PriRoomDataArr[i]=PriRoomData;
			}
		}
		return PriRoomDataArr;
	}
}
