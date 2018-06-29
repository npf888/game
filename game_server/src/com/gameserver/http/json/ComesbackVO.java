package com.gameserver.http.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;

public class ComesbackVO {

	private String ReturnCode;
	private String ReturnMsg;
	private String FacServiceId;
	private String TotalNum;
	
	
	private List<String> FacTradeSeq = new ArrayList<String>();

	public String getReturnCode() {
		return ReturnCode;
	}

	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}

	public String getReturnMsg() {
		return ReturnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}

	public String getFacServiceId() {
		return FacServiceId;
	}

	public void setFacServiceId(String facServiceId) {
		FacServiceId = facServiceId;
	}

	public String getTotalNum() {
		return TotalNum;
	}

	public void setTotalNum(String totalNum) {
		TotalNum = totalNum;
	}

	public List<String> getFacTradeSeq() {
		return FacTradeSeq;
	}

	public void setFacTradeSeq(List<String> facTradeSeq) {
		FacTradeSeq = facTradeSeq;
	}
	
	public static void main(String[] args) {
		String str = "{'ReturnCode':'1','ReturnMsg':'QueryOK','FacServiceId':'nfeiGC','TotalNum':1,'FacTradeSeq':['6209188169546107946']}";
		
		ComesbackVO vo = JSON.parseObject(str,ComesbackVO.class);
		Loggers.serverLogger.info(vo.getFacTradeSeq().get(0));
	}
	
	
}
