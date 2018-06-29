package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.msg.GCSlotType32BulletIn;
import com.gameserver.slot.template.BonusSpartaRewardTemplate;
import com.gameserver.slot.template.BonusSpartaTemplate;
import com.gameserver.slot.template.SocialitySpartaRewardTemplate;
import com.gameserver.slot.template.SocialitySpartaTemplate;

/**
 * 斯巴达 老虎机
 * @author JavaServer
 *
 */
public class SpartaService implements InitializeRequired {

	Map<Integer,List<SocialitySpartaTemplate>> socialitySpartaTemplateMap = new HashMap<Integer,List<SocialitySpartaTemplate>>();
	Map<Integer,List<SocialitySpartaRewardTemplate>> socialitySpartaRewardTemplateMap = new HashMap<Integer,List<SocialitySpartaRewardTemplate>>();
	Map<Integer,List<BonusSpartaTemplate>> bonusSpartaTemplateMap = new HashMap<Integer,List<BonusSpartaTemplate>>();
	Map<Integer,List<BonusSpartaRewardTemplate>> bonusSpartaRewardMap = new HashMap<Integer,List<BonusSpartaRewardTemplate>>();
	@Override
	public void init() {
		initSocialitySparta();
		initSocialitySpartaReward();
		initBonusSparta();
		initBonusSpartaReward();
	}
		 
	
	private void initSocialitySparta(){
		 Map<Integer, SocialitySpartaTemplate>  socialitySpartaTemplateList= Globals.getTemplateService().getAll(SocialitySpartaTemplate.class);
		 int startNum = -1;
		 for(SocialitySpartaTemplate bps: socialitySpartaTemplateList.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<SocialitySpartaTemplate> socialitySpartaList = new ArrayList<SocialitySpartaTemplate>();
				socialitySpartaList.add(bps);
				socialitySpartaTemplateMap.put(type, socialitySpartaList);
			}else{
				List<SocialitySpartaTemplate> socialitySpartaList = socialitySpartaTemplateMap.get(type);
				socialitySpartaList.add(bps);
			}
		 }
		
	}
	
	private void initSocialitySpartaReward(){
		Map<Integer, SocialitySpartaRewardTemplate>  socialitySpartaRewardTemplateList= Globals.getTemplateService().getAll(SocialitySpartaRewardTemplate.class);
		int startNum = -1;
		for(SocialitySpartaRewardTemplate bps: socialitySpartaRewardTemplateList.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<SocialitySpartaRewardTemplate> socialitySpartaRewardList = new ArrayList<SocialitySpartaRewardTemplate>();
				socialitySpartaRewardList.add(bps);
				socialitySpartaRewardTemplateMap.put(type, socialitySpartaRewardList);
			}else{
				List<SocialitySpartaRewardTemplate> socialitySpartaRewardList = socialitySpartaRewardTemplateMap.get(type);
				socialitySpartaRewardList.add(bps);
			}
		}
		
	}
		
	private void initBonusSparta(){
		Map<Integer, BonusSpartaTemplate>  bonusSpartaTemplateList= Globals.getTemplateService().getAll(BonusSpartaTemplate.class);
		int startNum = -1;
		for(BonusSpartaTemplate bps: bonusSpartaTemplateList.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusSpartaTemplate> sonusSpartaList = new ArrayList<BonusSpartaTemplate>();
				sonusSpartaList.add(bps);
				bonusSpartaTemplateMap.put(type, sonusSpartaList);
			}else{
				List<BonusSpartaTemplate> sonusSpartaList = bonusSpartaTemplateMap.get(type);
				sonusSpartaList.add(bps);
			}
		}
	}
	
	private void initBonusSpartaReward(){
		Map<Integer, BonusSpartaRewardTemplate>  bonusSpartaRewardTemplateList= Globals.getTemplateService().getAll(BonusSpartaRewardTemplate.class);
		int startNum = -1;
		for(BonusSpartaRewardTemplate bps: bonusSpartaRewardTemplateList.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusSpartaRewardTemplate> sonusSpartaRewardList = new ArrayList<BonusSpartaRewardTemplate>();
				sonusSpartaRewardList.add(bps);
				bonusSpartaRewardMap.put(type, sonusSpartaRewardList);
			}else{
				List<BonusSpartaRewardTemplate> sonusSpartaRewardList = bonusSpartaRewardMap.get(type);
				sonusSpartaRewardList.add(bps);
			}
		}
	}


	public Map<Integer, List<SocialitySpartaTemplate>> getSocialitySpartaTemplateMap() {
		return socialitySpartaTemplateMap;
	}


	public void setSocialitySpartaTemplateMap(
			Map<Integer, List<SocialitySpartaTemplate>> socialitySpartaTemplateMap) {
		this.socialitySpartaTemplateMap = socialitySpartaTemplateMap;
	}


	public Map<Integer, List<SocialitySpartaRewardTemplate>> getSocialitySpartaRewardTemplateMap() {
		return socialitySpartaRewardTemplateMap;
	}


	public void setSocialitySpartaRewardTemplateMap(
			Map<Integer, List<SocialitySpartaRewardTemplate>> socialitySpartaRewardTemplateMap) {
		this.socialitySpartaRewardTemplateMap = socialitySpartaRewardTemplateMap;
	}


	public Map<Integer, List<BonusSpartaTemplate>> getBonusSpartaTemplateMap() {
		return bonusSpartaTemplateMap;
	}


	public void setBonusSpartaTemplateMap(
			Map<Integer, List<BonusSpartaTemplate>> bonusSpartaTemplateMap) {
		this.bonusSpartaTemplateMap = bonusSpartaTemplateMap;
	}


	public Map<Integer, List<BonusSpartaRewardTemplate>> getBonusSpartaRewardMap() {
		return bonusSpartaRewardMap;
	}


	public void setBonusSpartaRewardMap(
			Map<Integer, List<BonusSpartaRewardTemplate>> bonusSpartaRewardMap) {
		this.bonusSpartaRewardMap = bonusSpartaRewardMap;
	}
		
	public List<Integer> getRewardList(int slotType){
		List<SocialitySpartaRewardTemplate> reList = socialitySpartaRewardTemplateMap.get(slotType);
		List<Integer> rewardList = new ArrayList<Integer>();
		for(SocialitySpartaRewardTemplate rr :reList){
			rewardList.add(rr.getRewardNum());
		}
		return rewardList;
	}
	/**
	 * 获取第几关的 数据
	 * @param slotNum
	 * @param type
	 * @return
	 */

	public List<BonusSpartaRewardTemplate> getBonusSpartaRewardTemplate(int slotNum,int type){
		List<BonusSpartaRewardTemplate> list = bonusSpartaRewardMap.get(slotNum);
		List<BonusSpartaRewardTemplate> list1 = new  ArrayList<BonusSpartaRewardTemplate>();
		for(BonusSpartaRewardTemplate ss:list){
			if(ss.getType() == type){
				list1.add(ss);
			}
		}
		return list1;
	}
	/**
	 * 社交转动的初始次数
	 */
	public int getSocialNum(int slotNum){
		List<SocialitySpartaTemplate> socialitySpartaTemplateList = socialitySpartaTemplateMap.get(slotNum);
		return socialitySpartaTemplateList.get(0).getSocialityNum();
	} 
	/**
	 * 城墙的 血量
	 */
	public int getWallNum(int slotNum){
		List<SocialitySpartaTemplate> socialitySpartaTemplateList = socialitySpartaTemplateMap.get(slotNum);
		return socialitySpartaTemplateList.get(0).getWallNum();
	} 
	/**
	 * 随机元素的数量
	 */
	public int getRandomNum(int slotNum){
		List<SocialitySpartaTemplate> socialitySpartaTemplateList = socialitySpartaTemplateMap.get(slotNum);
		return socialitySpartaTemplateList.get(0).getRandomNum();
	} 
	/**
	 * bonus 的数量
	 */
	public int getBounsNum(int slotNum){
		List<BonusSpartaTemplate> bonusSpartaList = bonusSpartaTemplateMap.get(slotNum);
		return bonusSpartaList.get(0).getBonusNum();
	} 
	/**
	 * sociality 根据攻击的次数 获取 百分比 的数量
	 */
	public int getSocialityNum(int slotNum,int selfAttackNum){
		List<SocialitySpartaRewardTemplate> socialitySpartaRewardList  = socialitySpartaRewardTemplateMap.get(slotNum);
		for(int i=0;i<socialitySpartaRewardList.size();i++){
			SocialitySpartaRewardTemplate ssr = socialitySpartaRewardList.get(i);
			if(i==(socialitySpartaRewardList.size()-1)){
				if(selfAttackNum >= ssr.getWallTimes()){
					return ssr.getRewardPercent();
				}else{
					return 0;
				}
			}
			SocialitySpartaRewardTemplate lastSsr = socialitySpartaRewardList.get(i+1);
			if(selfAttackNum >= ssr.getWallTimes()){
				if(selfAttackNum < lastSsr.getWallTimes()){
					return ssr.getRewardPercent();
				}
			}
		}
		
		return 0;
	} 
	/**
	 * sociality 根据固定住的wild数量 获取某个奖池
	 */
	public int getSocialityWildReward(int slotNum,int selfAttackNum){
		List<SocialitySpartaRewardTemplate> socialitySpartaRewardList  = socialitySpartaRewardTemplateMap.get(slotNum);
		for(int i=0;i<socialitySpartaRewardList.size();i++){
			SocialitySpartaRewardTemplate ssr = socialitySpartaRewardList.get(i);
			if(i==(socialitySpartaRewardList.size()-1)){
				if(selfAttackNum >= ssr.getWildNum()){
					return ssr.getRewardNum();
				}else{
					return 0;
				}
			}
			SocialitySpartaRewardTemplate lastSsr = socialitySpartaRewardList.get(i+1);
			if(selfAttackNum >= ssr.getWildNum()){
				if(selfAttackNum < lastSsr.getWildNum()){
					return ssr.getRewardNum();
				}
			}
		}
		return 0;
	} 
	
	
	/**
	 * 用户每次进入 到斯巴达 老虎机 就调用这个方法 显示现在还剩 多少攻城车 ，就可以打破城墙了
	 */
	
	public void getBullet(int slotType,Human human){
		if(slotType!=32){
			return;
		}
		//总的攻城次数
		int bulletNum = Globals.getSlotRoomService().getRoomBullet(slotType,human.getSlotRoomId());
		int wallNum = Globals.getSpartaService().getWallNum(slotType);
		int leftbulletNum = wallNum-bulletNum;
		//还没有攻破城墙
		GCSlotType32BulletIn gCSlotType32BulletIn = new GCSlotType32BulletIn();
		if(leftbulletNum > 0){
			gCSlotType32BulletIn.setBulletLeftNum(leftbulletNum);
			human.sendMessage(gCSlotType32BulletIn);
		//已经攻破城墙 leftbulletNum 就是0个
		}else{
			gCSlotType32BulletIn.setBulletLeftNum(0);
			human.sendMessage(gCSlotType32BulletIn);
		}
	}
}
