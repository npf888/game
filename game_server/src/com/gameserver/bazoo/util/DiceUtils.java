package com.gameserver.bazoo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 工具类
 * @author JavaServer
 *
 */
public class DiceUtils {

	
	
	/*** 将list色子 变成 字符串***/
	public static String getStrFromDiceList(List<Integer> diceValues){
		if(diceValues == null || diceValues.size() ==0){
			return null;
		}
		String str = "";
		for(Integer diceValue:diceValues){
			str+=diceValue+",";
		}
		
		return str.substring(0, str.length()-1);
		
	}
	/*** 将list色子 变成 数组***/
	public static int[] getArrFromDiceList(List<Integer> diceValues){
		int[] diceArr = new int[diceValues.size()];
		for(int i=0;i<diceValues.size();i++){
			diceArr[i]=diceValues.get(i);
		}
		return diceArr;
		
	}
	/*** 将list色子 排序***/
	public static void sortDiceList(List<Integer> diceValues){
		Collections.sort(diceValues, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1>o2){
					return 1;
				}else if(o1<o2){
					return -1;
				}else{
					return 0;
				}
			}
		});
		
	}
	
	
	/*** 将list色子 变成 数组***/
	public static List<Integer> getListFromArr(int[] diceArr){
		List<Integer> diceList = new ArrayList<Integer>();
		for(int i=0;i<diceArr.length;i++){
			diceList.add(diceArr[i]);
		}
		return diceList;
		
	}
}
