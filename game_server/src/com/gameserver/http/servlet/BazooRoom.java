package com.gameserver.http.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.constants.Loggers;
import com.gameserver.bazoo.service.TimeTask.BazooScheduleImpl;
import com.gameserver.bazoo.service.TimeTask.BazooTask;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.taskSchedule.ClassicalTask;
import com.gameserver.bazoo.taskSchedule.ShowShandTask;
import com.gameserver.common.Globals;
import com.gameserver.http.json.BazooRoomVO;
import com.gameserver.http.json.BazooTaskVO;
import com.gameserver.http.json.PlayerVO;
import com.gameserver.player.Player;
/**
 * 
 * 房间信息
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class BazooRoom extends HttpServlet {
	protected Logger logger = Loggers.BAZOO;
	//查看所有的房间
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String roomNumber = req.getParameter("roomNumber");
		List<Room> rooms = Globals.getBazooPubService().getRoomService().getRooms();
		List<BazooScheduleImpl> bazooScheduleImplList = Globals.getBazooPubService().getBazooScheduleService().getBzaooScheduleList();
		if(rooms != null && rooms.size() >0){
			List<BazooRoomVO> BazooRoomVOList = new ArrayList<BazooRoomVO>();
			for(Room room:rooms){
				if(StringUtils.isNotBlank(roomNumber)){
					if(!room.getRoomNumber().toString().equals(roomNumber.trim())){
						continue;
					}
				}
				BazooRoomVO BazooRoomVO = new BazooRoomVO();
				BazooRoomVO.setFullOrNot(room.isFullOrNot());
				BazooRoomVO.setState(room.getRoomStateInfo().getRoomState().getIndex());
				BazooRoomVO.setRoomNumber(room.getRoomNumber());
				List<PlayerVO> playerVOS = new ArrayList<PlayerVO>();
				for(Player p:room.getPlayers()){
					PlayerVO PlayerVO = new PlayerVO();
					PlayerVO.setPassportId(p.getPassportId());
					BazooRoomVO.setPlayerVOS(playerVOS);
					// 0:用户已经退出房间，1:用户没有退出房间
					PlayerVO.setInRoom(p.getHuman().getBazooRoomEveryUserInfo().getIsInRoom());//
					logger.info("[无双吹牛"+room.getRoomNumber().toString()+"]---[HTTP]---[当前用户::"+p.getPassportId()+"][inRoom::"+p.getHuman().getBazooRoomEveryUserInfo().getIsInRoom()+"]");
					playerVOS.add(PlayerVO);
				}
				BazooRoomVO.setPlayerVOS(playerVOS);
				BazooRoomVOList.add(BazooRoomVO);
				
				for(BazooScheduleImpl impl:bazooScheduleImplList){
					if(impl.getRoomNubmer().equals(room.getRoomNumber().toString())){
						BazooTaskVO BazooTaskVO = new BazooTaskVO();
						BazooTaskVO.setRoomNubmer(impl.getRoomNubmer());
						BazooTaskVO.setStop(impl.isStop());
						BazooTaskVO.setTime(impl.getTime());
						BazooTask BazooTask = impl.getBazooTask();
						if(BazooTask instanceof ClassicalTask){
							ClassicalTask task = (ClassicalTask)BazooTask;
							BazooTaskVO.getNextPlayerIds().add(task.getNextPlayer().getPassportId());
						}else if(BazooTask instanceof ShowShandTask){
							for(Player p:room.getPlayerInfo().getLastMans()){
								BazooTaskVO.getNextPlayerIds().add(p.getPassportId());
							}
						}
						BazooRoomVO.getBazooTaskVOS().add(BazooTaskVO);
					}
				}
			}
			
			String roomsStr = JSONArray.toJSONString(BazooRoomVOList);
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().print(roomsStr);
		}else{
			JSONObject jb = new JSONObject();
			jb.put("message", "null");
			resp.getWriter().print(jb.toJSONString());
		}
	}
	//操作 （通过参数设置各种各样的操作）
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String option = req.getParameter("option").trim();
		//强制删除房间
		if("delete".equals(option)){
			try{
				String passportId = req.getParameter("passportId");
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(passportId));
				Room room = Globals.getBazooPubService().getRoomService().getRoom(player);
				Globals.getBazooPubService().getBazooScheduleService().stopBazooSchedule(room.getRoomNumber().toString(),player);
				Globals.getBazooPubService().getRoomService().removeRoomForce(room.getRoomNumber());
			}catch(Exception e){
				String roomNumber = req.getParameter("roomNumber");
				Room room = Globals.getBazooPubService().getRoomService().getRoom(RoomNumber.toRoomNumber(roomNumber));
				if(room != null){
					Globals.getBazooPubService().getBazooScheduleService().forceStopBazooSchedule(room.getRoomNumber().toString());
					Globals.getBazooPubService().getRoomService().removeRoomForce(room.getRoomNumber());
				}
				
			}
		}
		JSONObject jb = new JSONObject();
		jb.put("message", "OK");
		resp.getWriter().print(jb.toJSONString());
	}
}
