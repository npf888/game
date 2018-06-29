package com.gameserver.recharge.https;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.HttpUtil;
import com.core.util.HttpsUtil;
import com.core.util.SHA;
import com.gameserver.human.Human;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.msg.GCMycardAuthcode;
import com.gameserver.recharge.result.AuthCodeResultData;

/**
 * 获取验证码
 * @author 郭君伟
 *
 */
public class AuthCodeInfo implements IIoOperation {

	 private Logger logger = Loggers.mycardMessageLogger;
	
	 private Human human;
	 
	 private Map<String, Object> paramMap = new HashMap<String, Object>();
	 
	 private volatile  AuthCodeResultData info  = null;
	 
	 private long orderID;
	
	
	public AuthCodeInfo(Human human, Map<String, Object> paramMap, long orderID){
		this.human = human;
    	String FacServiceId = (String)paramMap.get("FacServiceId");
    	String FacTradeSeq = (String)paramMap.get("FacTradeSeq");
    	String TradeType = (String)paramMap.get("TradeType");
    	String CustomerId = (String)paramMap.get("CustomerId");
    	String ProductName =(String) paramMap.get("ProductName");
    	String Amount = (String)paramMap.get("Amount");
    	String Currency = (String)paramMap.get("Currency");
    	String SandBoxMode = (String)paramMap.get("SandBoxMode");
    	
    	String preHashValue = FacServiceId+FacTradeSeq+TradeType+CustomerId+ProductName+Amount+Currency+SandBoxMode+Parameter.key;
    	
    	String urlEncode = HttpUtil.encode(preHashValue);
    	
    	String hash = SHA.SHA256(urlEncode);
    	
    	paramMap.put("Hash", hash);
    	paramMap.put("ServerId", "");
    	paramMap.put("PaymentType", "");
    	paramMap.put("ItemCode", "");
    	
    	this.paramMap = paramMap;
    	this.orderID = orderID;
		
	}
	
	

	@Override
	public int doStart() {
		logger.info(AuthCodeInfo.class.getName()+"  角色ID == "+human.getPassportId()+"   开始请求授权码"); 
		logger.info(AuthCodeInfo.class.getName()+"  授权码请求参数   === "+JSON.toJSONString(paramMap)); 
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		 String result = HttpsUtil.doGet(Parameter.url1, paramMap, true);
		 if(result != null){
			  info = JSON.parseObject(result, AuthCodeResultData.class);
			 if(info.getReturnCode().equals("1")){
				 logger.info(AuthCodeInfo.class.getName()+"  角色ID == "+human.getPassportId()+"   SUCCESS == "+result);
			 }else{
				 logger.error(AuthCodeInfo.class.getName()+"  角色ID == "+human.getPassportId()+"  fail == "+result);
			 }
		 }else{
			 logger.error(AuthCodeInfo.class.getName()+"  角色ID == "+human.getPassportId()+"  fail !!!");
		 }
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		GCMycardAuthcode message = new GCMycardAuthcode();
		
		  if(info != null && info.getReturnCode().equals("1")){
			  message.setReturnCode("1");
			  message.setAuthCode(info.getAuthCode());
			  HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderID);
			  hro.setAuthCode(info.getAuthCode());
			  hro.setTradeSeq(info.getTradeSeq());
			  hro.setModified();
		  }else{
			  message.setReturnCode("0");
			  message.setAuthCode("");
		  }
		 human.sendMessage(message);
		 logger.info(AuthCodeInfo.class.getName()+"  角色ID == "+human.getPassportId()+"  发送消息  "+JSON.toJSONString(message));
		return STAGE_STOP_DONE;
	}

}
