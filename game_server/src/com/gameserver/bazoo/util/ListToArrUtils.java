package com.gameserver.bazoo.util;

import java.util.List;

import com.gameserver.player.Player;

/**
 * list转换成 数组
 * @author JavaServer
 *
 */
public class ListToArrUtils {

	
	public static long[] listToArr(List<Player> list){
		long[] playerIdsArr =new long[list.size()];
		for(int i=0;i<list.size();i++){
			playerIdsArr[i]=list.get(i).getPassportId();
		}
		return playerIdsArr;
	}
	public static int[] listToIntegerArr(List<Player> list){
		int[] playerIdsArr =new int[list.size()];
		for(int i=0;i<list.size();i++){
			playerIdsArr[i]=(int)list.get(i).getPassportId();
		}
		return playerIdsArr;
	}
	
	
	public static int[] IntegerlistToArr(List<Integer> list){
		int[] playerIdsArr =new int[list.size()];
		for(int i=0;i<list.size();i++){
			playerIdsArr[i]=list.get(i);
		}
		return playerIdsArr;
	}
	public static long[] LonglistToArr(List<Long> list){
		long[] playerIdsArr =new long[list.size()];
		for(int i=0;i<list.size();i++){
			playerIdsArr[i]=list.get(i);
		}
		return playerIdsArr;
	}
	public static String playerIdToStr(List<Player> list){
		String playerIds = "";
		for(int i=0;i<list.size();i++){
			playerIds+=list.get(i).getPassportId();
		}
		return playerIds;
	}
}
