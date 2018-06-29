package com.robot.bazoo.Util;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;

public class ClassicalLogicUtil {

	private Logger logger = Loggers.BAZOO;
	
	public static int getDice(List<Integer> diceValues,int value,boolean isOnePoint){
		//计算 某个房间有几个 几 是否正确
			
			int theValue = 0;
				/**
				 * 如果是顺子  就直接过
				 */
				Collections.sort(diceValues);
				int diceValue=0;
				int dv = 0;
				for(Integer dice:diceValues){
					if(diceValue != dice.intValue()){
						diceValue=dice;
						dv++;
					}
				}
				if(dv==5){//说明是顺子
					return 0;
				}
				
				diceValue=0;
				dv = 0;
				int numberOne = 0;
				if(isOnePoint){//如果没有叫过 才能算万能符
					for(Integer dice:diceValues){
						if(diceValue != dice.intValue()){
							diceValue=dice;
							dv++;
						}
						if(dice.intValue() == 1){//说明有万能符 1
							numberOne=1;
						}
					}
				}
				/**
				 * 豹子  多给一个 
				 */
				if(dv==1){//说明是豹子
					theValue++;
				}else if(dv==2 && numberOne == 1){//说明是有万能符 的豹子
					theValue++;
				}
				
				/**
				 * 1是万能符
				 */
				for(Integer dice:diceValues){
					if(dice.intValue() == value || (numberOne == 1 && dice.intValue() == 1)){
						theValue++;
					}
				}
			return theValue;
	}
}
