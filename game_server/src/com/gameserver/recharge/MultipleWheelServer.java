package com.gameserver.recharge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.recharge.template.MultipleWheel;
import com.gameserver.recharge.template.WheelConditionTemplate;
/**
 * 
 * @author 郭君伟
 *
 */
public class MultipleWheelServer implements InitializeRequired {
	
	private Set<Integer> setPid = new HashSet<Integer>();
	
	private Map<Integer,MultipleWheel> mapData = new HashMap<Integer,MultipleWheel>();
	//所有需要翻倍的pid
	private List<Integer> wheelConditionList = new ArrayList<Integer>();
	
	private List<MultipleWheel> listTemp = new ArrayList<MultipleWheel>();
	private int[] list;

	@Override
	public void init() {
		
		Map<Integer,MultipleWheel> bounsTigerMap = Globals.getTemplateService().getAll(MultipleWheel.class);
		
		for(MultipleWheel en : bounsTigerMap.values()){
			
			 int pid = en.getPidID();
			 
			 
			 listTemp.add(en);
			 
			 setPid.add(pid);
			 
			 mapData.put(en.getId(), en);
		}
		
		list = new int[listTemp.size()];
		
		for(int i = 0;i < listTemp.size();i++){
			list[i] = listTemp.get(i).getValue();
		}
		
		Map<Integer,WheelConditionTemplate> wheelConditionTemplateMap = Globals.getTemplateService().getAll(WheelConditionTemplate.class);
		for(WheelConditionTemplate wheelCondition:wheelConditionTemplateMap.values()){
			wheelConditionList.add(wheelCondition.getPid());
		}
		
	}
	
	/**
	 * 是否翻倍转盘支付
	 * @param pid
	 * @return
	 */
	public boolean isPid(int pid){
		return setPid.contains(pid);
	}
	/**
	 * 是否翻倍转盘支付
	 * @param pid
	 * @return
	 */
	public boolean isPidNew(int pid){
		return wheelConditionList.contains(pid);
	}

	public int getId(){
		List<MultipleWheel>  post = ArrayUtils.randomFromArray(listTemp, list, 1, false);
		return post.get(0).getId();
	}
	
	/**
	 * 获得倍数
	 * @param id
	 * @return
	 */
	public int getMultiple(int id){
		
		MultipleWheel mw = mapData.get(id);
		
		return mw.getMultiple()/100 - 1;
	}

	public List<Integer> getWheelConditionList() {
		return wheelConditionList;
	}

	public void setWheelConditionList(List<Integer> wheelConditionList) {
		this.wheelConditionList = wheelConditionList;
	}
	
	
	
	
}
