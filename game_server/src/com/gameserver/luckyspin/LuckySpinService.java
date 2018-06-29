package com.gameserver.luckyspin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.LogReasons.LuckySpinLogReason;
import com.core.util.MathUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanLuckySpinManager;
import com.gameserver.luckyspin.msg.GCSpinZhuanpanNofree;
import com.gameserver.luckyspin.template.LuckyMatchTemplate;
import com.gameserver.luckyspin.template.TurntableExtra;
import com.gameserver.luckyspin.template.TurntableTemplate;

public class LuckySpinService implements InitializeRequired,AfterInitializeRequired{
	
	private List<Integer> freeWeightList = new ArrayList<Integer>();
	
	private List<TurntableTemplate> turntableList = new ArrayList<TurntableTemplate>();
	
	private  List<List<Integer>> weightListOfList = new ArrayList<List<Integer>>();
	
	private  List<List<Integer>> weightListOfFreeList = new ArrayList<List<Integer>>();
	
	private int poolSize = 0;
	
	//==========================================
	private List<LuckyMatchTemplate> matchList = new ArrayList<LuckyMatchTemplate>();
	private  List<List<Integer>> matchWeightListOfList = new ArrayList<List<Integer>>();
	private int matchPoolSize= 0;
	
	private Map<Integer,TurntableExtra> teMap = new HashMap<Integer,TurntableExtra>();
	
	private static Integer id = 1;
	
	private int pid = 0;
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		loadLuckySpin();
		loadLuckyMatch();
	}
	
	private void loadLuckySpin(){
		turntableList.addAll(Globals.getTemplateService().getAll(TurntableTemplate.class).values());
		
		for(int i=0;i<turntableList.size();i++){
			
			TurntableTemplate tempTurntableTemplate = turntableList.get(i);
			
			int type = tempTurntableTemplate.getType();
			//
			poolSize = tempTurntableTemplate.getPools().size();
			
			if(type == 1){
				combiningData(weightListOfList,tempTurntableTemplate);
			}else{
				combiningData(weightListOfFreeList,tempTurntableTemplate);
			}
			
			if(tempTurntableTemplate.getPidID() != 0){
				pid = tempTurntableTemplate.getPidID();
			}
			
			
			
		}
		
		for(TurntableExtra te : Globals.getTemplateService().getAll(TurntableExtra.class).values()){
			teMap.put(te.getId(), te);
		}
	}
	
	private void combiningData(List<List<Integer>> list,TurntableTemplate tempTurntableTemplate){
		for(int j=0;j<poolSize;j++){
			if(list.size()<=j){
				list.add(new ArrayList<Integer>());
			}
			List<Integer> tempWeightList=list.get(j);
			tempWeightList.add(tempTurntableTemplate.getPools().get(j));
		}
	}
	
	private void loadLuckyMatch(){
		matchList.addAll(Globals.getTemplateService().getAll(LuckyMatchTemplate.class).values());
		
		for(int i=0;i<matchList.size();i++){
			LuckyMatchTemplate tempLuckyMatchTemplate = matchList.get(i);
			matchPoolSize = tempLuckyMatchTemplate.getPools().size();
			for(int j=0;j<tempLuckyMatchTemplate.getPools().size();j++){
				if(matchWeightListOfList.size()<=j){
					matchWeightListOfList.add(new ArrayList<Integer>());
				}
				List<Integer> tempWeightList=matchWeightListOfList.get(j);
				tempWeightList.add(tempLuckyMatchTemplate.getPools().get(j));
			}
		}
	}

	@Override
	public void init() {}
	
	public List<TurntableTemplate>  getTurntableList(){
		return this.turntableList;
	}
	
	/**
	 * 免费抽取
	 * @return
	 */
	public TurntableTemplate spinFree(){
		int pos= MathUtils.random(freeWeightList.toArray(new Integer[freeWeightList.size()]));
		return turntableList.get(pos);
	}
	
	/**
	 * 免费抽取
	 * @return
	 */
	public TurntableTemplate spinFreeNew(int poolPos){
		int pos = poolPos%weightListOfFreeList.size();
		List<Integer> tempWeightList = weightListOfFreeList.get(pos);
		int index = MathUtils.random(tempWeightList.toArray(new Integer[tempWeightList.size()]));
		return turntableList.get(index);
	}
	
	/**
	 * 抽取池子
	 * @param poolPos
	 * @return
	 */
	public TurntableTemplate spin(int poolPos){
		List<Integer> tempWeightList = weightListOfList.get(poolPos);
		int pos= MathUtils.random(tempWeightList.toArray(new Integer[tempWeightList.size()]));
		return turntableList.get(pos);
	}
	/**
	 * 抽取池子
	 * @param poolPos
	 * @return
	 */
	public TurntableTemplate spinNew(int poolPos){
		int pos = poolPos%weightListOfFreeList.size();
		List<Integer> tempWeightList = weightListOfList.get(pos);
		int index = MathUtils.random(tempWeightList.toArray(new Integer[tempWeightList.size()]));
		return turntableList.get(index+12);
	}
	
	public int getPoolSize(){
		return this.poolSize;
	}
	
	public int getMatchPoolSize(){
		return this.matchPoolSize;
	}
	/**
	 * 抽取池子
	 * @param poolPos
	 * @return
	 */
	public LuckyMatchTemplate match(int poolPos){
		List<Integer> tempWeightList = matchWeightListOfList.get(poolPos);
		int pos= MathUtils.random(tempWeightList.toArray(new Integer[tempWeightList.size()]));
		return matchList.get(pos);
	}
	
	/**
	 * 获取额外奖励加成数据
	 * @return
	 */
	public TurntableExtra getTurntableExtra(){
		return teMap.get(id);
	}
	
	/**
	 * true ：可以
	 * @param pID
	 * @return
	 */
	public boolean isBigWheel(int pID){
		return pID != 0 && pID == this.pid;
	}
	
	public int getPid(){
		return this.pid;
	}
	public void spinZhuanpan(Human human){
		
        HumanLuckySpinManager hls = human.getHumanLuckySpinManager();
		
		HumanLuckySpin humanLuckySpin = hls.getHumanLuckySpin();
		
		List<RandRewardData> listData = new ArrayList<RandRewardData>();
		
		 int poolPos = humanLuckySpin.getTimes();
			
		 TurntableTemplate turntableTemplate = spinNew(poolPos);
		 
		 int index = turntableTemplate.getId();
		 
		 humanLuckySpin.setTimes(poolPos+1);
		 humanLuckySpin.setModified();
		 
		 GCSpinZhuanpanNofree message = new GCSpinZhuanpanNofree();
		 message.setIsFree(human.getHumanLuckySpinManager().ifFree()?1:0);
		 message.setPoint(index);
		 human.getPlayer().sendMessage(message);
		 
		 listData = turntableTemplate.getRewardList();
		 
		 human.giveMoney(listData.get(0).getRewardCount(), Currency.GOLD, false, LogReasons.GoldLogReason.ACHIEVEMENT, LogReasons.GoldLogReason.ACHIEVEMENT.getReasonText(), -1, 1);
		 
		//CommonLogic.getInstance().giveRandReward(human, listData, GoldLogReason.LUCKYSPIN, DiamondLogReason.LUCKY_SPIN_GET, CharmLogReason.LUCKYSPIN, ItemLogReason.LUCKY_SPIN, false);
			
		 Globals.getLogService().sendLuckySpinLog(human, LuckySpinLogReason.SPIN, LuckySpinLogReason.SPIN.getReasonText(), 0, index);
	}
}
