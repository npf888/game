package com.gameserver.bazoo.service.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.core.util.RandomUtil;

/**
 * 
 * 牛牛 模式
 * 摇色子
 * @author JavaServer
 *
 */
public class ShakeDice {

	
	/**
	 * 摇 某个 钟里所有的色子
	 * @param dices
	 * @return 
	 */
	public List<Integer> shakeAll(List<Integer> dices){
		dices.clear();
		for(int i=0;i<5;i++){
			dices.add(singleShake());
		}
		return dices;
	}
	
	
	
	
	/**
	 * 获取每个人 摇的色子的数量
	 * @return
	 */
	public List<Integer> getDiceList(){
		//五个色子
		int diceNum = 5;
		List<Integer> diceList = new ArrayList<Integer>();
		//5个色子 相当于 摇5次
		for(int i=0;i<diceNum;i++){
			//每个色子的 值
			diceList.add(singleShake());
		}
		
		return diceList;
	}
	
	/**
	 * 传入 几个色子 摇 几个色子
	 * @return
	 */
	public List<Integer> getDiceListByNum(int diceNum){
		List<Integer> diceList = new ArrayList<Integer>();
		//5个色子 相当于 摇5次
		for(int i=0;i<diceNum;i++){
			//每个色子的 值
			diceList.add(singleShake());
		}
		
		return diceList;
	}
	
	
	
	
	/**
	 * 单独摇某个钟里   某几个 色子
	 * @param dices 用户所有色子
	 * @param valueList  哪几个 value 的色子 需要 摇
	 * @return  重摇指定色子之后的 一组色子
	 */
	public List<Integer> shakeSome(List<Integer> dices,List<Integer> valueList){
		//用户摇两个 就是 两个 ，用户摇三个 这里就是三个
		List<Integer> newDiceList = new ArrayList<Integer>();
		//已经摇过的 index
		List<Integer> indexHasShaking = new ArrayList<Integer>();
		if(valueList!=null && valueList.size()>0){
			for(Integer value:valueList){
				for(int i=0;i<dices.size();i++){
					if(!indexHasShaking.contains(i)){
						if(value.intValue() == dices.get(i)){
							int v = singleShake();
							dices.set(i, v);
							newDiceList.add(v);
							indexHasShaking.add(i);
							break;
						}
					}
				}
			}
		}else{
			for(int i=0;i<dices.size();i++){
				int v = singleShake();
				dices.set(i, v);
			}
		}
		return dices;
		
	}
	
	//单独摇一个色子
	private int singleShake(){
		return RandomUtil.nextInt(1, 7);
	}
}
