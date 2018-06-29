package com.gameserver.slot.template;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class TournamentTemplate extends TournamentTemplateVO {
	
	/**老虎机集合 **/
	private Set<Integer> slotSet = new HashSet<Integer>();
	
	/**老虎机比赛时间集合 **/
	private List<Integer> durationList = new ArrayList<Integer>();
	
	/**奖励 1 2 3 **//*
	private List<Integer> reward = new ArrayList<Integer>();*/

	@Override
	public void check() throws TemplateConfigException {
		slotSet.add(this.slotNum1);
		slotSet.add(this.slotNum2);
		slotSet.add(this.slotNum3);
		slotSet.add(this.slotNum4);
		slotSet.add(this.slotNum5);
		
		durationList.add(this.duration1);
		durationList.add(this.duration2);
		durationList.add(this.duration3);
		
		/*reward.add(this.firstReward);
		reward.add(this.secondReward);
		reward.add(this.thirdReward);*/
	}

	@Override
	public void patchUp() throws Exception {
		
	}

	public Set<Integer> getSlotSet() {
		return slotSet;
	}

	public List<Integer> getDurationList() {
		return durationList;
	}

/*	public List<Integer> getReward(){
		return reward;
	}
*/	
}
