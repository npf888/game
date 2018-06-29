package com.robot.bazoo.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiceNum {

	private PointNumber onePoint;
	private PointNumber twoPoint;
	private PointNumber threePoint;
	private PointNumber fourPoint;
	private PointNumber fivePoint;
	private PointNumber sixPoint;
	
	public DiceNum(){
		init();
	}
	
	
	public void init(){
		onePoint=new PointNumber();
		onePoint.setNumber(0);
		onePoint.setPoint(1);
		
		twoPoint=new PointNumber();
		twoPoint.setNumber(0);
		twoPoint.setPoint(2);
		
		threePoint=new PointNumber();
		threePoint.setNumber(0);
		threePoint.setPoint(3);
		
		fourPoint=new PointNumber();
		fourPoint.setNumber(0);
		fourPoint.setPoint(4);
		
		fivePoint=new PointNumber();
		fivePoint.setNumber(0);
		fivePoint.setPoint(5);
		
		sixPoint=new PointNumber();
		sixPoint.setNumber(0);
		sixPoint.setPoint(6);
	}
	
	
	public PointNumber getOnePoint() {
		return onePoint;
	}


	public void setOnePoint(PointNumber onePoint) {
		this.onePoint = onePoint;
	}


	public PointNumber getTwoPoint() {
		return twoPoint;
	}


	public void setTwoPoint(PointNumber twoPoint) {
		this.twoPoint = twoPoint;
	}


	public PointNumber getThreePoint() {
		return threePoint;
	}


	public void setThreePoint(PointNumber threePoint) {
		this.threePoint = threePoint;
	}


	public PointNumber getFourPoint() {
		return fourPoint;
	}


	public void setFourPoint(PointNumber fourPoint) {
		this.fourPoint = fourPoint;
	}


	public PointNumber getFivePoint() {
		return fivePoint;
	}


	public void setFivePoint(PointNumber fivePoint) {
		this.fivePoint = fivePoint;
	}


	public PointNumber getSixPoint() {
		return sixPoint;
	}


	public void setSixPoint(PointNumber sixPoint) {
		this.sixPoint = sixPoint;
	}


	public List<PointNumber> pointList(){
		List<PointNumber> pointList = new ArrayList<PointNumber>();
		pointList.add(this.getOnePoint());
		pointList.add(this.getTwoPoint());
		pointList.add(this.getThreePoint());
		pointList.add(this.getFourPoint());
		pointList.add(this.getFivePoint());
		pointList.add(this.getSixPoint());
		
		Collections.sort(pointList, new Comparator<PointNumber>() {

			@Override
			public int compare(PointNumber o1, PointNumber o2) {
				if(o1.getNumber()>o2.getNumber()){
					return 1;
				}else if(o1.getNumber()<o2.getNumber()){
					return -1;
				}else{
					return 0;
				}
			}
			
		});
		return pointList;
	}
	
	
	/**
	 * 判断用户叫的号 是对的 还是错误的
	 * @param num
	 * @param value
	 * @return
	 */
	public boolean judge(long num,long value){
		List<PointNumber> list = pointList();
		for(PointNumber pn:list){
			long number = pn.getNumber();
			long point = pn.getPoint();
			if(point == value){
				if(number >= num){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 判断还有没有对的号 可以叫
	 * @param num
	 * @param value
	 * @return
	 */
	public boolean judgeRightExist(long num,long value){
		List<PointNumber> list = pointList();
		for(PointNumber pn:list){
			long number = pn.getNumber();
			long point = pn.getPoint();
			long cur = Long.valueOf(number+""+point).longValue();
			long call = Long.valueOf(num+""+value).longValue();
			if(cur>call){
				return true;
			}
		}
		
		return false;
	}
	/**
	 * 如果还存在 可以叫的号 就返回一个
	 * @param num
	 * @param value
	 * @return
	 */
	public PointNumber judgeRightNumValue(long num,long value){
		List<PointNumber> list = pointList();
		for(PointNumber pn:list){
			long number = pn.getNumber();
			long point = pn.getPoint();
			if(point == 1){
				continue;
			}
			long cur = Long.valueOf(number+""+point).longValue();
			long call = Long.valueOf(num+""+value).longValue();
			if(cur>call){
				return pn;
			}
		}
		
		return null;
	}
	
	
}
