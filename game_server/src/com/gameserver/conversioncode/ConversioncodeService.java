package com.gameserver.conversioncode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.Conversioncode;
import com.gameserver.common.Globals;

/**
 * 
 * @author 郭君伟
 *
 */
public class ConversioncodeService implements InitializeRequired{
	
	
	private Map<String,ConversioncodeData>  codeMap = new HashMap<String,ConversioncodeData>();
	

	@Override
	public void init() {
		List<Conversioncode> list = Globals.getDaoService().getConversioncodeDao().getAllConversioncodeEntity();
		if(list != null && !list.isEmpty()){
			long currTime = Globals.getTimeService().now();
			for(Conversioncode entity : list){
				long endTime = entity.getEndTime();
				if(endTime > currTime){
					ConversioncodeData date = new ConversioncodeData();
					date.fromEntity(entity);
					codeMap.put(date.getConversionCode(), date);
				}
			}
		}
	}
	
	/**
	 * 获取兑换码数据
	 * @param code
	 * @return
	 */
	public ConversioncodeData getData(String code){
		return codeMap.get(code);
	}
	
	/**
	 * 添加兑换信息
	 * @param code
	 * @param gold
	 * @param endTime
	 */
	public void putDate(String code,long gold,long endTime){
		long currTime = Globals.getTimeService().now();
		if(currTime < endTime && gold > 0){
			ConversioncodeData data = new ConversioncodeData();
			data.setDbId(Globals.getUUIDService().getNextUUID(currTime,UUIDType.ACHIEVEMENT));
			data.setConversionCode(code);
			data.setGold(gold);
			data.setEndTime(endTime);
			data.setIsdelete(0);
			data.setCreateTime(currTime);
			data.setUpdateTime(currTime);
			data.setInDb(false);
			data.active();
			data.setModified();
			codeMap.put(code, data);
		}
	}
	
	
	/**
	 * 改变状态
	 * @param state 兑换码
	 * @param state 0 恢复 1 失效
	 */
	public void changeStata(String code,int state){
		if(codeMap.containsKey(code)){
			ConversioncodeData data = codeMap.get(code);
			data.setIsdelete(state);
			data.setModified();
		}
	}
	
	public void addDate(String code,long gold,long endTime,String codeType){
		long currTime = Globals.getTimeService().now();
		if(currTime < endTime && gold > 0){
			ConversioncodeData data = new ConversioncodeData();
			data.setDbId(Globals.getUUIDService().getNextUUID(currTime,UUIDType.ACHIEVEMENT));
			data.setConversionCode(code);
			data.setGold(gold);
			data.setEndTime(endTime);
			data.setIsdelete(0);
			data.setCreateTime(currTime);
			data.setUpdateTime(currTime);
			data.setCodeType(Integer.valueOf(codeType));
			data.setInDb(false);
			data.active();
			data.setModified();
			codeMap.put(code, data);
		}
	}
	
	public void editStata(String code,long gold,long endTime,String codeType){
		if(codeMap.containsKey(code)){
			long currTime = Globals.getTimeService().now();
			ConversioncodeData data = codeMap.get(code);
			data.setGold(gold);
			data.setEndTime(endTime);
			data.setCodeType(Integer.valueOf(codeType));
			data.setUpdateTime(currTime);
			data.setModified();
		}
	}
	public void deleteStata(String code){
		if(codeMap.containsKey(code)){
			long currTime = Globals.getTimeService().now();
			ConversioncodeData data = codeMap.get(code);
			data.setIsdelete(1);
			data.setUpdateTime(currTime);
			data.setModified();
		}
	}
	

}
