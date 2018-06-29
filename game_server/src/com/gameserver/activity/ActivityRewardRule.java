package com.gameserver.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gameserver.common.data.RandRewardData;

/**
 * 奖励规则
 * @author wayne
 *
 */
public class ActivityRewardRule {

	/**活动参数列表  key:ActivityConditionParamEnum  value:数量 */
	private HashMap<String,Integer> paramsMap = new HashMap<String,Integer>();
	/**奖励列表*/
	private List<RandRewardData> rewardList = new ArrayList<RandRewardData>();
	/**最大领奖次数*/
	private int maxDrows;


	public HashMap<String,Integer> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(HashMap<String,Integer> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public List<RandRewardData> getRewardList() {
		return rewardList;
	}
	public void setRewardList(List<RandRewardData> rewardList) {
		this.rewardList = rewardList;
	}
	public int getMaxDrows() {
		return maxDrows;
	}
	public void setMaxDrows(int maxDrows) {
		this.maxDrows = maxDrows;
	}

}
