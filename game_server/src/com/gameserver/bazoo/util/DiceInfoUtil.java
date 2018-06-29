package com.gameserver.bazoo.util;

import java.util.List;

import com.gameserver.bazoo.data.BlackWhiteBet;
import com.gameserver.bazoo.data.BlackWhiteDiceInfo;
import com.gameserver.bazoo.data.BlackWhiteNum;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.data.EndCountInfo;

public class DiceInfoUtil {

	public static DiceInfo[] getArrFromList(List<DiceInfo> diceInfoList){
	
		DiceInfo[] DiceInfoArr = new DiceInfo[diceInfoList.size()];
		for(int i=0;i<diceInfoList.size();i++){
			DiceInfo DiceInfo = diceInfoList.get(i);
			DiceInfoArr[i]=DiceInfo;
		}
		
		return DiceInfoArr;
	}
	public static EndCountInfo[] getArrFromEndList(List<EndCountInfo> EndCountInforList){
		
		EndCountInfo[] EndCountInfoArr = new EndCountInfo[EndCountInforList.size()];
		for(int i=0;i<EndCountInforList.size();i++){
			EndCountInfo EndCountInfor = EndCountInforList.get(i);
			EndCountInfoArr[i]=EndCountInfor;
		}
		
		return EndCountInfoArr;
	}
	public static BlackWhiteDiceInfo[] getArrFromEndListTwo(List<BlackWhiteDiceInfo> BlackWhiteDiceInfoList){
		
		BlackWhiteDiceInfo[] BlackWhiteDiceInfoArr = new BlackWhiteDiceInfo[BlackWhiteDiceInfoList.size()];
		for(int i=0;i<BlackWhiteDiceInfoList.size();i++){
			BlackWhiteDiceInfo EndCountInfor = BlackWhiteDiceInfoList.get(i);
			BlackWhiteDiceInfoArr[i]=EndCountInfor;
		}
		
		return BlackWhiteDiceInfoArr;
	}
	public static BlackWhiteBet[] getArrFromEndListThree(List<BlackWhiteBet> BlackWhiteBetList){
		
		BlackWhiteBet[] BlackWhiteBetArr = new BlackWhiteBet[BlackWhiteBetList.size()];
		for(int i=0;i<BlackWhiteBetList.size();i++){
			BlackWhiteBet BlackWhiteBet = BlackWhiteBetList.get(i);
			BlackWhiteBetArr[i]=BlackWhiteBet;
		}
		
		return BlackWhiteBetArr;
	}
}
