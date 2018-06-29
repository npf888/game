package com.gameserver.recharge.https;

import java.util.List;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.HttpsUtil;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeLogic;
import com.gameserver.recharge.msg.GCOrderAmazonDelivery;
import com.gameserver.recharge.msg.GCValidateOrder;
import com.gameserver.recharge.result.AmazonVerifyReceiptVO;

public class AmazonVerifyReceipt implements IIoOperation {
	
	 private Logger logger = Loggers.AMAZONINFO;
	 
	
	//客户亚马逊ID
	private String userId;
	//收据ID
	private String receiptId;
	//订单
	private long orderId;
	
	private Human human;
	
	private volatile AmazonVerifyReceiptVO vo;
	
	public AmazonVerifyReceipt(Human human,String userId,String receiptId,long orderId){
		this.human = human;
		this.userId = userId;
		this.receiptId = receiptId;
		this.orderId = orderId;
	}

	@Override
	public int doStart() {
		
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		logger.info("start====================================================================");
		logger.info("userId =="+userId);
		logger.info("receiptId =="+receiptId);
		logger.info("orderId =="+orderId);
		logger.info("charId =="+human.getPassportId());
		//共享密钥
		String sharekey = "2:MD7a201-L93Cg-dJhqejJpNgboW3wwVRWs0hcZFiCooYM89_suDVbdQiwdN1WTCF:hA_f4ADNfz5uLMKvJ2VDDQ==";
		
		//String url = "http://127.0.0.1/RVSSandbox/version/1.0/verifyReceiptId/developer/"+sharekey+"/user/"+userId+"/receiptId/"+receiptId;
		String url = "https://appstore-sdk.amazon.com/version/1.0/verifyReceiptId/developer/"+sharekey+"/user/"+userId+"/receiptId/"+receiptId;
		try {
			logger.info("URL ==  "+url);
			//List<String> result = HttpUtil.getUrlAmazon(url);
			List<String> result = HttpsUtil.getUrlAmazon(url, null, true);
			if(result.size() == 2){
				vo = JSON.parseObject(result.get(1),AmazonVerifyReceiptVO.class);
			}
			logger.info("result == "+JSON.toJSONString(result));
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("end====================================================================");
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		
		 if(vo != null){
			 HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderId);
			 hro.setUserId(userId);
			 hro.setReceiptId(receiptId);
			 hro.setModified();
			 //发货
			 RechargeLogic.onRecharge(human.getPlayer(), orderId,0);
			 
			 GCOrderAmazonDelivery gc = new GCOrderAmazonDelivery();
			 gc.setReceiptId(receiptId);
			 human.sendMessage(gc);
		 }else{
			GCValidateOrder gcValidateOrder = new GCValidateOrder();
		     gcValidateOrder.setOrderId(orderId);
			 gcValidateOrder.setResult(0);
			 gcValidateOrder.setRandRewardList(new RandRewardData[0]);
			 human.sendMessage(gcValidateOrder);
		 }
		return STAGE_STOP_DONE;
	}

}
