package com.gameserver.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.ScatterCrastalTemplate;
import com.gameserver.slot.template.ScatterCrystalRewardTemplate;

public class ScatterCrystalService implements InitializeRequired {

	public static final int type_money = 1;//金额
	public static final int type_times = 2;//次数
	public static final int level_enough_yes = 1;//等级足够
	public static final int level_enough_no = 0;//等级不够
	List<ScatterCrastalTemplate> scatterCrastalList = new ArrayList<ScatterCrastalTemplate>();
	List<ScatterCrystalRewardTemplate> scatterCrystalRewardList = new ArrayList<ScatterCrystalRewardTemplate>();
	List<Integer> weights = new ArrayList<Integer>();
	@Override
	public void init() {
		Map<Integer,ScatterCrastalTemplate> sctMap = Globals.getTemplateService().getAll(ScatterCrastalTemplate.class);
		for(ScatterCrastalTemplate scatterCrastalTemplate:sctMap.values()){
			scatterCrastalList.add(scatterCrastalTemplate);
		}
		
		Map<Integer,ScatterCrystalRewardTemplate> scrtMap = Globals.getTemplateService().getAll(ScatterCrystalRewardTemplate.class);
		for(ScatterCrystalRewardTemplate scatterCrystalRewardTemplate:scrtMap.values()){
			scatterCrystalRewardList.add(scatterCrystalRewardTemplate);
			weights.add(scatterCrystalRewardTemplate.getValue());
		}
		
	}
	public List<ScatterCrastalTemplate> getScatterCrastalList() {
		return scatterCrastalList;
	}
	public void setScatterCrastalList(
			List<ScatterCrastalTemplate> scatterCrastalList) {
		this.scatterCrastalList = scatterCrastalList;
	}
	public List<ScatterCrystalRewardTemplate> getScatterCrystalRewardList() {
		return scatterCrystalRewardList;
	}
	public void setScatterCrystalRewardList(
			List<ScatterCrystalRewardTemplate> scatterCrystalRewardList) {
		this.scatterCrystalRewardList = scatterCrystalRewardList;
	}
	/*
	 * 根据权值获取 一条数据
	 */
	public ScatterCrystalRewardTemplate getScatterByWeight(){
		
		List<ScatterCrystalRewardTemplate>  post = ArrayUtils.randomFromArray(scatterCrystalRewardList, ArrayUtils.intList2Array(weights), 1, false);
		return post.get(0);
	}
	

}
