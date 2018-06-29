package com.gameserver.activity;


import java.util.List;

import com.gameserver.activity.enums.ActivityConditionParamEnum;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 公共规则
 * @author wayne
 *
 */
public class CommonRule implements IActivityRule{
	
	private static CommonRule instance = new CommonRule();
	public static CommonRule getInstance(){
		return instance;
	}
	
	/**
	 * 
	 *  返回true:表示有活动达到了领取奖励的条件
	 * @Override
	 */
	public boolean ifRewardStatusChanged(Human human,HumanActivity humanActivity) {

		//活动类型 完成次数
		HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
		
		//允许领取次数 已经领取次数
		List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
	
		//活动模板数据
		Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
		
		boolean ifChange = false;
		
		for(int i=0;i< activity.getRuleList().size();i++){
			
			//获取游戏规则
			ActivityRewardRule rule =  activity.getRuleList().get(i);
			
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
			
			//判断是否拥有所有次数
			if(tempHumanRewardActivityDetailData.getAllowDrawCount()<rule.getMaxDrows()){
				
				//累积值
				int drawCount = humanActivityData.getPlayCountList().get(i).intValue();	
				
				drawCount = drawCount<rule.getMaxDrows() ? drawCount:rule.getMaxDrows();
				
				if(drawCount != tempHumanRewardActivityDetailData.getAllowDrawCount()){
					tempHumanRewardActivityDetailData.setAllowDrawCount(drawCount);
					ifChange = true;
				}
				
			}

		}
		
		return ifChange;
	}
	
	/**
	 * 改变领取规则
	 * @param human
	 * @param humanActivity
	 * @param enumParam
	 * @return
	 */
    public boolean ifRewardStatusChangedSlot(Human human,HumanActivity humanActivity,ActivityConditionParamEnum enumParam) {

		//活动类型 完成次数
		HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
		
		//允许领取次数 已经领取次数
		List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
	
		//活动模板数据
		Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
		
		boolean ifChange = false;
		
		for(int i=0;i< activity.getRuleList().size();i++){
			
			//获取游戏规则
			ActivityRewardRule rule =  activity.getRuleList().get(i);
			//人物自己的数据
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
			
			//累积值
			long drawCount = humanActivityData.getPlayCountList().get(i);	
			
			//规定的次数
			int count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
			
			if(drawCount >= count){//累计规定的次数
				 //条件达到 设置允许领取次数
				if(tempHumanRewardActivityDetailData.getAllowDrawCount() != rule.getMaxDrows()){
					//设置可以领取次数为配置的数据
					tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
					ifChange = true;//可以领取
				}
			}
		}
		
		return ifChange;
	}
    /**
     * 有最大次数的规则
     * @param human
     * @param humanActivity
     * @param enumParam
     * @return
     */
    public boolean ifRewardStatusChangedForMaxNum(Human human,HumanActivity humanActivity,ActivityConditionParamEnum enumParam) {
    	
    	//活动类型 完成次数
    	HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
    	
    	//允许领取次数 已经领取次数
    	List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
    	
    	//活动模板数据
    	Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
    	
    	boolean ifChange = false;
    	
    	for(int i=0;i< activity.getRuleList().size();i++){
    		//获取游戏规则
    		ActivityRewardRule rule =  activity.getRuleList().get(i);
    		//人物自己的数据
    		HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
    		
    		//累积值
    		long drawCount = humanActivityData.getPlayCountList().get(i);	
    		//规定的次数
    		Integer count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
    		if(count != null){
    			//假如 一个活动的一个规则 每点击 10次，可以领取 5 次 奖金，那么每到10这个节点就触发一次(10、20、30、40、50)
    			if(drawCount != 0 && drawCount%count == 0){//累计规定的次数
    				int hasDrawCount = tempHumanRewardActivityDetailData.getHasDrawCount();
    				//条件达到 设置允许领取次数
    				if(hasDrawCount <= rule.getMaxDrows()){
    					//设置最大允许次数（满足条件才设置这个值，不满足不设置这个值，因为  前端领取的状态，要用这个值判断）
    					tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
    					//设置可以领取次数为配置的数据
    					ifChange = true;//可以领取
    					//每次满足一次条件，就清空相应的playCountList 进行下一轮
    					humanActivityData.getPlayCountList().set(i,0l);
    				}
    			}
    		}
    	}
    	
    	return ifChange;
    }
	
    
    
    /**
     * 累计充值 用的比对
     */
    public boolean ifGrandRewardStatusChanged(Human human,HumanActivity humanActivity,ActivityConditionParamEnum enumParam) {

		//活动类型 完成次数
		HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
		
		//允许领取次数 已经领取次数
		List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
	
		//活动模板数据
		Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
		
		boolean ifChange = false;
		
		for(int i=0;i< activity.getRuleList().size();i++){
			
			//获取游戏规则
			ActivityRewardRule rule =  activity.getRuleList().get(i);
			//人物自己的数据
			HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
			//累积值
			long drawCount = humanActivityData.getPlayCountList().get(i);	
			
			//规定的次数
			int count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
			
			if(drawCount >= count){//累计规定的次数
				tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
				 //条件达到 设置允许领取次数
				if(tempHumanRewardActivityDetailData.getHasDrawCount() < rule.getMaxDrows()){
					ifChange = true;//可以领取
				}
			}
		}
		
		return ifChange;
	}
    /**
     * 
     * 比较美元的  涉及到了 小数点
     * 累计充值 用的比对
     */
    public boolean ifGrandRewardStatusChangedDollar(Human human,HumanActivity humanActivity,ActivityConditionParamEnum enumParam) {
    	
    	//活动类型 完成次数
    	HumanActivityData humanActivityData = humanActivity.getHumanActivityData();
    	
    	//允许领取次数 已经领取次数
    	List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
    	
    	//活动模板数据
    	Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
    	
    	boolean ifChange = false;
    	
    	for(int i=0;i< activity.getRuleList().size();i++){
    		
    		//获取游戏规则
    		ActivityRewardRule rule =  activity.getRuleList().get(i);
    		//人物自己的数据
    		HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
    		//累积值
    		long drawCount = humanActivityData.getPlayCountList().get(i);	
    		//0:表示真钱（美元）     1:表示次数
			if(enumParam.getIndex() == ActivityConditionParamEnum.Condition22.getIndex()){
				long drawCountPre =  drawCount/100;
				long drawCountBack =  drawCount%100;
				int count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
				//规定的次数
				//除完 以后的 前边的整数值 只要 大于  count, 整个 数 就是大于，如果前边的 整数值 小于 count ，那么整个数 就小于 count ，不用比较余数了
				if(drawCountPre >= count){//累计规定的次数
					tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
					//条件达到 设置允许领取次数
					if(tempHumanRewardActivityDetailData.getHasDrawCount() < rule.getMaxDrows()){
						ifChange = true;//可以领取
					}
				}
			}else{
				//规定的次数
				int count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
				
				if(drawCount >= count){//累计规定的次数
					tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
					//条件达到 设置允许领取次数
					if(tempHumanRewardActivityDetailData.getHasDrawCount() < rule.getMaxDrows()){
						ifChange = true;//可以领取
					}
				}
			}
    	}
    	
    	return ifChange;
    }
    /**
     * 全服累计充值 用的比对
     */
    public boolean ifFullServerGrandRewardStatusChanged(Human human,HumanActivity pubHumanActivity,HumanActivity humanActivity,ActivityConditionParamEnum enumParam) {
    	
    	//活动类型 完成次数
    	HumanActivityData humanActivityData = pubHumanActivity.getHumanActivityData();
    	
    	//允许领取次数 已经领取次数
    	List<HumanRewardActivityDetailData> humanRewardActivityDataList = humanActivity.getHumanRewardActivityDetailDataList();
    	
    	//活动模板数据
    	Activity activity = Globals.getActivityService().getActivityById(humanActivity.getActivityId());
    	
    	boolean ifChange = false;
    	
    	for(int i=0;i< activity.getRuleList().size();i++){
    		
    		//获取游戏规则
    		ActivityRewardRule rule =  activity.getRuleList().get(i);
    		//人物自己的数据
    		HumanRewardActivityDetailData tempHumanRewardActivityDetailData = humanRewardActivityDataList.get(i);
    		//累积值
    		long drawCount = humanActivityData.getPlayCountList().get(i);	
    		
    		//规定的次数
    		int count = rule.getParamsMap().get(String.valueOf(enumParam.getIndex()));
    		
    		if(drawCount >= count){//累计规定的次数
    			tempHumanRewardActivityDetailData.setAllowDrawCount(rule.getMaxDrows());
    			//条件达到 设置允许领取次数
    			if(tempHumanRewardActivityDetailData.getHasDrawCount() < rule.getMaxDrows()){
    				ifChange = true;//可以领取
    			}
    		}
    	}
    	
    	return ifChange;
    }
   

}
