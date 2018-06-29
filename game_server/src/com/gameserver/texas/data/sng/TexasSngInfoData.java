package com.gameserver.texas.data.sng;

import com.gameserver.texas.template.SngMatchTemplate;

/**
 * sng
 * @author wayne
 *
 */
public class TexasSngInfoData implements Comparable<TexasSngInfoData>{
	//id
	private int id;
	//是否开启
	private int openUp;
	//人数
	private int nums;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOpenUp() {
		return openUp;
	}
	public void setOpenUp(int openUp) {
		this.openUp = openUp;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	
	public static TexasSngInfoData convertFromSngMatchTemplate(SngMatchTemplate sngMatchTemplate){
		TexasSngInfoData texasSngInfoData = new TexasSngInfoData();
		texasSngInfoData.setId(sngMatchTemplate.getId());
		texasSngInfoData.setOpenUp(sngMatchTemplate.getOpenUp());
		texasSngInfoData.setNums(0);
		return texasSngInfoData;
	}
	
	@Override
	public int compareTo(TexasSngInfoData texasSngInfoData) {
		// TODO Auto-generated method stub	
		if(this.getId()<texasSngInfoData.getId()){
			return -1;
		}
		if(this.getId()==texasSngInfoData.getId()){
			return 0;
		}
		return 1;
	}
	
}
