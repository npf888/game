package com.robot.bazoo.Util;

import java.util.Random;

import com.core.util.RandomUtil;

public class ChanceUtil {

	
	public static boolean random(int num){
		Random random = new Random();
		int ran = random.nextInt(11);
		
		if(ran <= num){
			return true;
		}
		return false;
	}
	public static boolean randomHundred(int num){
		int ran = RandomUtil.nextInt(1,101);
		if(ran <= num){
			return true;
		}
		return false;
	}
}
