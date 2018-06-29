package com.gameserver.slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.common.AfterInitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.JackpotDragonTemplate;
import com.gameserver.slot.template.SlotsListTemplate;

public class JackpotDragonService implements AfterInitializeRequired{

	List<JackpotDragonTemplate>  jackpotDragonTemplateList = new ArrayList<JackpotDragonTemplate>();
	Map<Integer,JackpotDragonTemplate>  jackpotDragonTemplateMap = new HashMap<Integer,JackpotDragonTemplate>();
	@Override
	public void afterInit() {
		Map<Integer,JackpotDragonTemplate> jackpotDragonTemplates = Globals.getTemplateService().getAll(JackpotDragonTemplate.class);
		for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragonTemplates.values()){
			jackpotDragonTemplateList.add(jackpotDragonTemplate);
			jackpotDragonTemplateMap.put(jackpotDragonTemplate.getJackpotNum(), jackpotDragonTemplate);
		}
		Collections.sort(jackpotDragonTemplateList,new Comparator<JackpotDragonTemplate>(){

			@Override
			public int compare(JackpotDragonTemplate o1,
					JackpotDragonTemplate o2) {
				if(o1.getInitial() < o2.getInitial()){
					return -1;
				}
				return 0;
			}
			
		});
		
		List<SlotsListTemplate> sList = Globals.getSlotService().getSlotsListTemplateList(23);
		Collections.sort(sList,new Comparator<SlotsListTemplate>(){
			
			@Override
			public int compare(SlotsListTemplate o1,
					SlotsListTemplate o2) {
				if(o1.getId() < o2.getId()){
					return -1;
				}
				return 1;
			}
			
		});
		/**
		 * 初始化奖池
		 */
		Integer slotType9 = 0;
		Integer slotType10 = 0;
		Integer slotType11 = 0;
		Integer slotType12 = 0;
		for(int i=0;i<sList.size();i++){
			if(i == 0){
				slotType9=sList.get(0).getId();
			}else if(i ==1 ){
				slotType10=sList.get(1).getId();
			}else if(i ==2 ){
				slotType11=sList.get(2).getId();
			}else if(i ==3 ){
				slotType12=sList.get(3).getId();
			}
		}
		Map<Integer,Slot> slotMap = Globals.getSlotService().getSlotList();
		Slot slotEntity9 = slotMap.get(slotType9);
		Slot slotEntity10 = slotMap.get(slotType10);
		Slot slotEntity11 = slotMap.get(slotType11);
		Slot slotEntity12 = slotMap.get(slotType12);
		//如果曾经初始化 过 就不在初始化
		if(StringUtils.isNotBlank(slotEntity9.getSlotType23Jackpot())){
			return;
		}
		Map<Integer,SlotsListTemplate> slotsListTemplates = Globals.getTemplateService().getAll( SlotsListTemplate.class);
		List<SlotsListTemplate> slotListTemplateList = new ArrayList<SlotsListTemplate>();
		for(SlotsListTemplate slotsListTemplate:slotsListTemplates.values()){
			//如果东方龙就取出来
			if(slotsListTemplate.getType() == 23){
				slotListTemplateList.add(slotsListTemplate);
			}
		}
		for(SlotsListTemplate slotsListTemplate:slotListTemplateList){
			long bet1 = slotsListTemplate.getBet1();
			long bet2 = slotsListTemplate.getBet2();
			long bet3 = slotsListTemplate.getBet3();
			long bet4 = slotsListTemplate.getBet4();
			long bet5 = slotsListTemplate.getBet5();
			
			//顺序取出所有的奖池
			List<JackpotDragonTemplate> jackpotDragons = jackpotDragonTemplateList;
			//bet1 的奖池
			String jackpotSingleList_bet1 = "";
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList_bet1+=(bet1*jackpotDragonTemplate.getInitial())/100+",";
			}
			jackpotSingleList_bet1 = bet1+"="+jackpotSingleList_bet1.substring(0,jackpotSingleList_bet1.length()-1);
			
			//bet2 的奖池
			String jackpotSingleList_bet2 = "";
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList_bet2+=(bet2*jackpotDragonTemplate.getInitial())/100+",";
			}
			jackpotSingleList_bet2 = bet2+"="+jackpotSingleList_bet2.substring(0,jackpotSingleList_bet2.length()-1);
			
			//bet3 的奖池
			String jackpotSingleList_bet3 = "";
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList_bet3+=(bet3*jackpotDragonTemplate.getInitial())/100+",";
			}
			jackpotSingleList_bet3 = bet3+"="+jackpotSingleList_bet3.substring(0,jackpotSingleList_bet3.length()-1);
			
			//bet4 的奖池
			String jackpotSingleList_bet4 = "";
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList_bet4+=(bet4*jackpotDragonTemplate.getInitial())/100+",";
			}
			jackpotSingleList_bet4 = bet4+"="+jackpotSingleList_bet4.substring(0,jackpotSingleList_bet4.length()-1);
			
			//bet5 的奖池
			String jackpotSingleList_bet5 = "";
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList_bet5+=(bet5*jackpotDragonTemplate.getInitial())/100+",";
			}
			jackpotSingleList_bet5 = bet5+"="+jackpotSingleList_bet5.substring(0,jackpotSingleList_bet5.length()-1);
			
			
			String finalFiveRewards = jackpotSingleList_bet1+";"+jackpotSingleList_bet2+";"+jackpotSingleList_bet3+";"+jackpotSingleList_bet4+";"+jackpotSingleList_bet5;
			//初始化奖池到数据库
			if(slotsListTemplate.getId() == slotType9){
				slotEntity9.setSlotType23Jackpot(finalFiveRewards);
				slotEntity9.setModified();
			}else if(slotsListTemplate.getId() == slotType10){
				slotEntity10.setSlotType23Jackpot(finalFiveRewards);
				slotEntity10.setModified();
			}else if(slotsListTemplate.getId() == slotType11){
				slotEntity11.setSlotType23Jackpot(finalFiveRewards);
				slotEntity11.setModified();
			}else if(slotsListTemplate.getId() == slotType12){
				slotEntity12.setSlotType23Jackpot(finalFiveRewards);
				slotEntity12.setModified();
			}
		}
	}
	public List<JackpotDragonTemplate> getJackpotDragonTemplateList() {
		return jackpotDragonTemplateList;
	}
	public void setJackpotDragonTemplateList(
			List<JackpotDragonTemplate> jackpotDragonTemplateList) {
		this.jackpotDragonTemplateList = jackpotDragonTemplateList;
	}
	public Map<Integer, JackpotDragonTemplate> getJackpotDragonTemplateMap() {
		return jackpotDragonTemplateMap;
	}
	public void setJackpotDragonTemplateMap(
			Map<Integer, JackpotDragonTemplate> jackpotDragonTemplateMap) {
		this.jackpotDragonTemplateMap = jackpotDragonTemplateMap;
	}
	//解析 此老虎机 5 个奖池的 信息
	public List<String> getJackpotList(String alljackpot,int curbet){
		String jackpot = getJackpot(alljackpot,curbet);
		List<String> jackpotList = new ArrayList<String>();
		if(StringUtils.isNotBlank(jackpot)){
			String[] jackpots = jackpot.split(",");
			if(jackpots != null && jackpots.length > 0){
				for(int i =0;i<jackpots.length;i++){
					jackpotList.add(jackpots[i]);
				}
			}
		}
		return jackpotList;
	}
	private String getJackpot(String jackpot,int curbet){
		if(StringUtils.isNotBlank(jackpot)){
			String[] rewards = jackpot.split(";");
			for(int i = 0;i<rewards.length;i++){
				String[] bet_reward = rewards[i].split("=");
				String reward = bet_reward[1];
				int bet = Integer.valueOf(bet_reward[0]).intValue();
				if(bet == curbet){
					return reward;
				}
			}
		}
		return null;
	}
	public String getStringAllJackpots(String alljackpot,String cursinglejackpot,int curbet){
		String newreward = "";
		if(StringUtils.isNotBlank(alljackpot)){
			String[] rewards = alljackpot.split(";");
			for(int i = 0;i<rewards.length;i++){
				String[] bet_reward = rewards[i].split("=");
				int bet = Integer.valueOf(bet_reward[0]).intValue();
				if(bet == curbet){
					String curRewardStr = bet+"="+cursinglejackpot;
					newreward+= curRewardStr+";";
				}else{
					newreward+= rewards[i]+";";
				}
			}
		}
		if(StringUtils.isNotBlank(newreward)){
			return newreward.substring(0,newreward.length()-1);
		}
		return null;
	}
	//给奖池排序
	public String sortJackPort(String reward){
		String str = "";
		List<String> list = new ArrayList<String>();
		String[] rs = reward.split(",");
		for(int i=0;i<rs.length;i++){
			list.add(rs[i]);
		}
		Collections.sort(list, new Comparator<Object>(){
			@Override
			public int compare(Object o1, Object o2) {
				String jk1 = (String)o1;
				long jk1Int = Long.valueOf(jk1).longValue();
				String jk2 = (String)o2;
				long jk2Int = Long.valueOf(jk2).longValue();
				if(jk1Int > jk2Int){
					return 1;
				}
				return -1;
			}
			
		});
		for(String s:list){
			str += s+",";
		}
		return str.substring(0,str.length()-1);
	}

}
