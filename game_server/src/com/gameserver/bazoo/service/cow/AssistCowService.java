package com.gameserver.bazoo.service.cow;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.msg.GCCowSingleSwing;
import com.gameserver.bazoo.msg.GCCowSingleSwingEnd;
import com.gameserver.bazoo.msg.GCCowUnifySwing;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.player.Player;

/**
 * 辅助BazooCowService 的 service 将一些乱遭的 方法 放到 这里边
 * @author JavaServer
 *
 */
public class AssistCowService {
	/**
	 * 向所有人发消息
	 */
	public void swingAllMessage(Player player,RoomEveryUserInfo roomEveryUserInfo,Player banker,int swingStatus){
		
		GCCowUnifySwing GCCowUnifySwing = new GCCowUnifySwing();
		GCCowUnifySwing.setCowNameInt(roomEveryUserInfo.getCowUserInfo().getCowNum());
		GCCowUnifySwing.setOneRoundTime(7);
		List<Integer> dices = roomEveryUserInfo.getDiceValues();
		int[] diceArr = new int[dices.size()];
		for(int i=0;i<dices.size();i++){
			diceArr[i]=dices.get(i);
		}
		List<Integer> redDiceValues = roomEveryUserInfo.getCowUserInfo().getRedDiceValues();
		int[] redDiceValuesArr = new int[redDiceValues.size()];
		for(int i=0;i<redDiceValues.size();i++){
			redDiceValuesArr[i]=redDiceValues.get(i);
		}
		
		GCCowUnifySwing.setRedDiceValues(redDiceValuesArr);
		GCCowUnifySwing.setDiceValues(diceArr);
		if(banker != null){
			GCCowUnifySwing.setPassportId(banker.getPassportId());
		}
		//在房间内的才发送消息
		player.sendBazooMessage(GCCowUnifySwing);
		
	}
	
	public GCCowSingleSwingEnd getGCCowSingleSwingEnd(Room room) {
		List<Player> players = room.getPlayersPartIn();
		List<DiceInfo> DiceInfoList = new ArrayList<DiceInfo>();
		for(Player p:players){
			DiceInfo diceInfo = new DiceInfo();
			List<Integer> diceValues = p.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			int[] diceValuesArr = new int[diceValues.size()];
			for(int i=0;i<diceValues.size();i++){
				diceValuesArr[i] = diceValues.get(i);
			}
			diceInfo.setPassportId(p.getPassportId());
			diceInfo.setDiceValues(diceValuesArr);
			diceInfo.setCowNameInt(p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getCowNum());
			List<Integer> redDiceValues = p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().getRedDiceValues();
			int[] redDiceValuesArr = new int[redDiceValues.size()];
			for(int i=0;i<redDiceValues.size();i++){
				redDiceValuesArr[i] = redDiceValues.get(i);
			}
			diceInfo.setRedDiceValues(redDiceValuesArr);
			DiceInfoList.add(diceInfo);
		}
		DiceInfo[] diceInfoArr = new DiceInfo[DiceInfoList.size()];
		for(int i=0;i<DiceInfoList.size();i++){
			diceInfoArr[i]=DiceInfoList.get(i);
		}
		GCCowSingleSwingEnd GCCowSingleSwingEnd = new GCCowSingleSwingEnd();
		GCCowSingleSwingEnd.setDiceInfo(diceInfoArr);
		
		return GCCowSingleSwingEnd;
	}

	public void swingSingleMessage(Player p, RoomEveryUserInfo roomEveryUserInfo) {
		GCCowSingleSwing  GCCowSingleSwing = new GCCowSingleSwing();
		GCCowSingleSwing.setCowNameInt(roomEveryUserInfo.getCowUserInfo().getCowNum());
		List<Integer> dices = roomEveryUserInfo.getDiceValues();
		int[] diceArr = new int[dices.size()];
		for(int i=0;i<dices.size();i++){
			diceArr[i]=dices.get(i);
		}
		List<Integer> redDiceValues = roomEveryUserInfo.getCowUserInfo().getRedDiceValues();
		int[] redDiceValuesArr = new int[redDiceValues.size()];
		for(int i=0;i<redDiceValues.size();i++){
			redDiceValuesArr[i]=redDiceValues.get(i);
		}
		
		GCCowSingleSwing.setRedDiceValues(redDiceValuesArr);
		GCCowSingleSwing.setDiceValues(diceArr);
		GCCowSingleSwing.setPassportId(p.getPassportId());
		p.sendBazooMessage(GCCowSingleSwing);
		
	}
}
