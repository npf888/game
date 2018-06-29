package com.gameserver.baccart.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gameserver.baccart.enums.BaccartBetType;

/**
 * 百家乐押注种类
 * @author wayne
 *
 */
public class BaccartBetData {
	private int betType;
	private long betNum;
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public long getBetNum() {
		return betNum;
	}
	public void setBetNum(long betNum) {
		this.betNum = betNum;
	}
	
	/**
	 * 计算下注的总和
	 * @param baccartBetDataList
	 * @return
	 */
	public static long betNumForBaccartBetDataList(BaccartBetData[] baccartBetDataList){
		long betNum = 0;
		for(BaccartBetData tempBaccartBetData:baccartBetDataList){
			betNum += tempBaccartBetData.getBetNum();
		}
		return betNum;
	}
	
	/**
	 * 计算下注的总和
	 * @param baccartBetDataList
	 * @return
	 */
	public static long betNumForBaccartBetDataMap(HashMap<BaccartBetType,Long> baccartBetTypeMap){
		long betNum = 0;
		for(Long tempBet:baccartBetTypeMap.values()){
			betNum += tempBet;
		}
		return betNum;
	}
	

	
	/**
	 * 结合下注数据
	 * @param baccartBetDataList
	 * @param baccartBetDataArr
	 */
	public static List<BaccartBetData>  combine(List<BaccartBetData> baccartBetDataList,BaccartBetData[] baccartBetDataArr){
		List<BaccartBetData> tempBaccartBetDataList = new ArrayList<BaccartBetData>();
		tempBaccartBetDataList.addAll(baccartBetDataList);
		for(BaccartBetData tempBaccartBetData1 : baccartBetDataArr){
			boolean found = false;
			for(BaccartBetData tempBaccartBetData2:tempBaccartBetDataList){
				
				if(tempBaccartBetData1.getBetType() == tempBaccartBetData2.getBetType()){
					tempBaccartBetData2.setBetNum(tempBaccartBetData1.getBetNum()+ tempBaccartBetData2.getBetNum());
					found = true;
					break;
				}	
			}
			
			if(!found){
				BaccartBetData tempBaccartBetData = new BaccartBetData();
				tempBaccartBetData.setBetNum(tempBaccartBetData1.getBetNum());
				tempBaccartBetData.setBetType(tempBaccartBetData1.getBetType());
				tempBaccartBetDataList.add(tempBaccartBetData);
			}
		}
		return tempBaccartBetDataList;
	}
	
	@Override
	public String toString(){
		return "下注类型["+BaccartBetType.valueOf(this.betType)+"],下注量["+this.betNum+"]";
	}
}
