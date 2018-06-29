package com.gameserver.gift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.common.InitializeRequired;
import com.core.util.MathUtils;
import com.gameserver.common.Globals;
import com.gameserver.gift.enums.GiftTypeEnum;
import com.gameserver.gift.template.GiftTemplate;
import com.gameserver.gift.template.NewComerTemplate;

/**
 * 
 * @author 郭君伟
 *
 */
public class GiftService implements InitializeRequired{

	/** 全部模板集合数据  **/
	private Map<Integer,GiftTemplate> templateList = new HashMap<Integer,GiftTemplate>();
	
	
	/** 充值 产品ID  **/
	private Set<Integer> orderGiftSet = new HashSet<Integer>();
	
	/** 随机礼包集合 **/
	private List<GiftTemplate> giftTemplateList = new ArrayList<GiftTemplate>();
	//新手礼包  新
	private List<NewComerTemplate> newComerTemplateList = new ArrayList<NewComerTemplate>();
	
	@Override
	public void init() {
		
		Map<Integer,GiftTemplate> templateMap = Globals.getTemplateService().getAll(GiftTemplate.class);
		
		for(Entry<Integer,GiftTemplate> en : templateMap.entrySet()){
			   int id = en.getKey();
			   GiftTemplate ft = en.getValue();
			   templateList.put(id, ft);
			   
			   GiftTypeEnum giftTypeEnum = GiftTypeEnum.indexOf(ft.getGiftType());
			   
			   //如果是多种礼包
			   if(giftTypeEnum == GiftTypeEnum.GiftType3){
				   giftTemplateList.add(ft);
				   orderGiftSet.add(ft.getGift1());
				   orderGiftSet.add(ft.getGift2());
				   orderGiftSet.add(ft.getGift3());
			   }  
		}
		
		
		Map<Integer,NewComerTemplate> NewComerTemplateMap = Globals.getTemplateService().getAll(NewComerTemplate.class);
		
		for(NewComerTemplate NewComerTemplate:NewComerTemplateMap.values()){
			newComerTemplateList.add(NewComerTemplate);
		}
	}
	
	/**
	 * 充值的时候调用
	 * @param rechargeId 
	 * @return -1 出错了
	 */
	public int getGiftId(int rechargeId,int roleLv){
		if(orderGiftSet.contains(rechargeId)){
		     return getGiftNoId(roleLv);
		}
		return -1;
	}
	
	/**
	 * 时间到期随机礼包ID
	 * @return
	 */
	public int getGiftNoId(int level){
		
		 List<GiftTemplate> templateList = new ArrayList<GiftTemplate>();
		 for(GiftTemplate gift : giftTemplateList){
				if(gift.getLevelDown() <= level && level <= gift.getLevelUp()){
					templateList.add(gift);
				}
		 }
		 if(templateList.isEmpty()){
			 //没有对应等级礼包
			 return -1;
		 }
		int index = MathUtils.random(0, templateList.size()-1);
		return templateList.get(index).getId();
	}
	
	public GiftTemplate getTemplate(int giftId) {
		return templateList.get(giftId);
	}

	/**
	 * 验证
	 * @param id
	 * @param giftid
	 * @return
	 */
	public boolean islegitimate(int id,int giftid){
		if(templateList.containsKey(id)){
			GiftTemplate gt = templateList.get(id);
			if(gt.getGift1() == giftid || gt.getGift2() == giftid || gt.getGift3() == giftid){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 新手礼包  
	 * 在哪个时间段内
	 */
	
	public NewComerTemplate getNewComerTemplateByDay(int day){
		for(NewComerTemplate NewComerTemplate:newComerTemplateList){
			int start = NewComerTemplate.getGameTime1();
			int end = NewComerTemplate.getGameTime2();
			if(day >= start && day <= end){
				return NewComerTemplate;
			}
		}
		return null;
	}
}
