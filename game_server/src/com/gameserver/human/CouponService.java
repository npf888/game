package com.gameserver.human;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.template.CouponTemplate;
/**
 * 优惠券的service
 * @author JavaServer
 *
 */
public class CouponService implements InitializeRequired {

	List<CouponTemplate> couponTemplateList = new ArrayList<CouponTemplate>();
	List<Integer> weightList = new ArrayList<Integer>();
	@Override
	public void init() {
		Map<Integer, CouponTemplate> reCouponTemplateMap = Globals.getTemplateService().getAll(CouponTemplate.class);
		for(CouponTemplate couponTemplate:reCouponTemplateMap.values()){
			couponTemplateList.add(couponTemplate);
			weightList.add(couponTemplate.getWeight());
		}
		
	}
	
	
	
	public List<CouponTemplate> getCouponTemplateList() {
		return couponTemplateList;
	}
	public void setCouponTemplateList(List<CouponTemplate> couponTemplateList) {
		this.couponTemplateList = couponTemplateList;
	}
	public List<Integer> getWeightList() {
		return weightList;
	}
	public void setWeightList(List<Integer> weightList) {
		this.weightList = weightList;
	}
	
	public Object getPost(List<?> list1,List<Integer> list2){
        List<?>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
		return post.get(0);
//        return couponTemplateList.get(1);
	}
	
	public CouponTemplate getPost(){
		return (CouponTemplate)getPost(couponTemplateList,weightList);
	}
	
	

}
