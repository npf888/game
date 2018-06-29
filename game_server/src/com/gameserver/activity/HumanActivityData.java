package com.gameserver.activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动数据
 * @author wayne
 *
 */
public class HumanActivityData {
	
	/**
	 * 完成次数
	 */
	private List<Long> playCountList = new ArrayList<Long>();
	
	
	public HumanActivityData() {
	}
	
	public HumanActivityData(List<ActivityRewardRule> ruleList) {
		
		// TODO Auto-generated constructor stub
		for(int i=0;i<ruleList.size();i++)
		{
			playCountList.add(0l);
		}
	}

	public List<Long> getPlayCountList() {
		return playCountList;
	}

	public void setPlayCountList(List<Long> playCountList) {
		this.playCountList = playCountList;
	}
	

	/**
	 * 消耗金币
	 * @param index
	 */
	public void increaseGold(int index){
		this.playCountList.set(index, this.playCountList.get(index)+1);
	}
	public void increaseGold(int index,long value){
		this.playCountList.set(index, playCountList.get(index)+value);
	}
	/**
	 * 完成次数累加
	 * @param index
	 */
	public void increaseCount(int index){
		this.playCountList.set(index, this.playCountList.get(index)+1l);
	}
	public void increaseCountNew(int index,long value){
		this.playCountList.set(index, value);
	}
	
	/**
	 * 完成次数减少
	 * @param index
	 */
	public void downCount(int index){
		this.playCountList.set(index, this.playCountList.get(index)-1 < 0?0:this.playCountList.get(index)-1);
	}
	
}
