package com.gameserver.collect;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.core.util.TimeUtils;
import com.gameserver.collect.template.ExchangeTemplate;
import com.gameserver.collect.template.ShavePrizeTemplate;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.msg.GCExpDouble;
/**
 * 收集系统
 * @author 郭君伟
 *
 */
public class CollectServer implements InitializeRequired {

	//抽奖池=======================================================
	private Map<Integer,List<ShavePrizeTemplate>> mapShave1 = new HashMap<Integer,List<ShavePrizeTemplate>>();
	private Map<Integer,int[]> mapShave2 = new HashMap<Integer,int[]>();
	//========================
	
	private  Map<Integer,ExchangeTemplate> exchange = new HashMap<Integer,ExchangeTemplate>();
	
	@Override
	public void init() {
		
		TemplateService templateService = Globals.getTemplateService();
		/**
		 * 魅力值
		 */
		exchange=templateService.getAll(ExchangeTemplate.class);
		
		Map<Integer,ShavePrizeTemplate> map1 = templateService.getAll(ShavePrizeTemplate.class);
		
		for(ShavePrizeTemplate st : map1.values()){
			int poolType = st.getPoolType();
			List<ShavePrizeTemplate> list = mapShave1.get(poolType);
			if(list == null){
				list = new ArrayList<>();
				mapShave1.put(poolType, list);
			}
			list.add(st);
		}
		
		for(Entry<Integer,List<ShavePrizeTemplate>> en : mapShave1.entrySet()){
			
			int poolType = en.getKey();
			
			List<ShavePrizeTemplate> list = en.getValue();
			
			int[] num = mapShave2.get(poolType);
			if(num == null){
				num = new int[list.size()];
				mapShave2.put(poolType, num);
			}
			for(int i = 0;i < list.size();i++){
				ShavePrizeTemplate spt = list.get(i);
				num[i] = spt.getProbability();
			}
		}
		
	}
	
	/**
	 * 获取兑换的东西
	 * @param id
	 * @return
	 */
	public ExchangeTemplate getExchange(int id){
		return exchange.get(id);
	}

	
	/**
	 * 这里不能容错
	 * @param poolType
	 * @return
	 */
    public ShavePrizeTemplate getShavePrizeTemplate(int poolType){
    	List<ShavePrizeTemplate> list = mapShave1.get(poolType);
    	
    	int[] nums = mapShave2.get(poolType);
    	
    	List<ShavePrizeTemplate>  post = ArrayUtils.randomFromArray(list, nums, 1, false);
    	
    	return post.get(0);
    }
    /**
     * 双倍经验加成 并且推送消息
     */
    public void setDoubleExpEndTime(Human human,int min){
    	long now = Globals.getTimeService().now();
		Date endTime = human.getDoubleExpEndTime();
		boolean pass = true;
		if(endTime !=null){
			//看看有没有过期
			if(endTime.getTime() > now){//没有过期
				pass=false;
				long doubleExpEndTime = endTime.getTime()+min*60*1000;
				human.setDoubleExpEndTime(TimeUtils.longToDate(doubleExpEndTime));
			}
		}
		//过期了 或者结束 时间 为空
		if(pass){
			long doubleExpEndTime = now+min*60*1000;
			human.setDoubleExpEndTime(TimeUtils.longToDate(doubleExpEndTime));
		}
		human.setModified();
		
		
		GCExpDouble GCExpDouble = new GCExpDouble();
		if(human.getDoubleExpEndTime() != null){
			long leftTime = (human.getDoubleExpEndTime().getTime()-Globals.getTimeService().now());
			if(leftTime <= 0){
				leftTime = 0;
			}else{
				leftTime = leftTime/1000;
			}
			GCExpDouble.setLeftTime(leftTime);
		}else{
			GCExpDouble.setLeftTime(0);
		}
		human.getPlayer().sendMessage(GCExpDouble);
    }
}
