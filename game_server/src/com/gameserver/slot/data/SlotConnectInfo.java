package com.gameserver.slot.data;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.SlotJackpotNewTemplate;
import com.gameserver.slot.template.SlotJackpotTemplate;

public class SlotConnectInfo {
	
	/**连线 **/
	private PaylinesTemplate paylinesTemplate;
	/**赔率 **/
	private int pay;
    /**位置 **/
	private List<Integer> posList = new ArrayList<Integer>();

	/** 彩金模板数据 **/
	private SlotJackpotTemplate sjt; 
	/** 彩金模板数据 **/
	private SlotJackpotNewTemplate sjtNEW; 
	
	/** 是否5个 jackPort 彩金**/
	private boolean jackPort = false;
	
	private int payId;
	
	private int jackpotid;

	/**
	 * @return the paylinesTemplate
	 */
	public PaylinesTemplate getPaylinesTemplate() {
		return paylinesTemplate;
	}


	/**
	 * @param paylinesTemplate the paylinesTemplate to set
	 */
	public void setPaylinesTemplate(PaylinesTemplate paylinesTemplate) {
		this.paylinesTemplate = paylinesTemplate;
	}


	public List<Integer> getPosList() {
		return posList;
	}

	public void  setPay(int pay){
		this.pay = pay;
	}
	public int getPay() {
		// TODO Auto-generated method stub
		return this.pay;
	}


	public SlotJackpotTemplate getSjt() {
		return sjt;
	}


	public void setSjt(SlotJackpotTemplate sjt) {
		this.sjt = sjt;
	}


	public boolean isJackPort() {
		return jackPort;
	}


	public void setJackPort(boolean jackPort) {
		this.jackPort = jackPort;
	}


	public int getPayId() {
		return payId;
	}


	public void setPayId(int payId) {
		this.payId = payId;
	}


	public SlotJackpotNewTemplate getSjtNEW() {
		return sjtNEW;
	}


	public void setSjtNEW(SlotJackpotNewTemplate sjtNEW) {
		this.sjtNEW = sjtNEW;
	}


	public int getJackpotid() {
		return jackpotid;
	}


	public void setJackpotid(int jackpotid) {
		this.jackpotid = jackpotid;
	}

	
	
}
