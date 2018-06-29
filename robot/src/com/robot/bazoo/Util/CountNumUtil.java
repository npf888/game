package com.robot.bazoo.Util;

import java.util.ArrayList;
import java.util.List;

import com.robot.bazoo.data.PointNumber;

/**
 * 计算数量
 * @author JavaServer
 *
 */
public class CountNumUtil {

	public static List<PointNumber> getPointNumber(List<Integer> list){
		
		if(list == null){
			return null;
		}
		
		int onePointNumber=0;
		int twoPointNumber=0;
		int threePointNumber=0;
		int fourPointNumber=0;
		int fivePointNumber=0;
		int sixPointNumber=0;
		for(Integer point:list){
			if(point.intValue() == 1){
				onePointNumber++;
			}else if(point.intValue() == 2){
				twoPointNumber++;
			}else if(point.intValue() == 3){
				threePointNumber++;
			}else if(point.intValue() == 4){
				fourPointNumber++;
			}else if(point.intValue() == 5){
				fivePointNumber++;
			}else if(point.intValue() == 6){
				sixPointNumber++;
			}
		}
		
		List<PointNumber> pnList = new ArrayList<PointNumber>();
		PointNumber pnOne = new PointNumber();
		pnOne.setPoint(1);
		pnOne.setNumber(onePointNumber);
		pnList.add(pnOne);
		
		PointNumber pnTwo = new PointNumber();
		pnTwo.setPoint(2);
		pnTwo.setNumber(twoPointNumber);
		pnList.add(pnTwo);
		
		PointNumber pnThree = new PointNumber();
		pnThree.setPoint(3);
		pnThree.setNumber(threePointNumber);
		pnList.add(pnThree);
		
		PointNumber pnFour = new PointNumber();
		pnFour.setPoint(4);
		pnFour.setNumber(fourPointNumber);
		pnList.add(pnFour);
		
		PointNumber pnFive = new PointNumber();
		pnFive.setPoint(5);
		pnFive.setNumber(fivePointNumber);
		pnList.add(pnFive);
		
		PointNumber pnSix = new PointNumber();
		pnSix.setPoint(6);
		pnSix.setNumber(sixPointNumber);
		pnList.add(pnSix);
		
		return pnList;
		
	}
}
