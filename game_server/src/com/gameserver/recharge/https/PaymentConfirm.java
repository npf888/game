package com.gameserver.recharge.https;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.HttpsUtil;
import com.gameserver.human.Human;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.RechargeLogic;
import com.gameserver.recharge.result.PaymentConfirmResult;

public class PaymentConfirm implements IIoOperation {
	
	private Logger logger = Loggers.mycardMessageLogger;
	
	private Human human;
	
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	private  volatile PaymentConfirmResult pcr;
	
	public PaymentConfirm(Human human,String authCode){
		this.human = human;
		paramMap.put("AuthCode", authCode);
		logger.info(PaymentConfirm.class.getName()+" Start  "+JSON.toJSONString(paramMap));
	}

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		String result = HttpsUtil.doGet(Parameter.url3, paramMap, true);
		if(result != null){
			pcr = JSON.parseObject(result, PaymentConfirmResult.class);
			logger.info(PaymentConfirm.class.getName()+" Request Data ==  "+result);
		}else{
			logger.error(PaymentConfirm.class.getName()+"  Request error    ");
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		 if(pcr != null && pcr.getReturnCode().equals("1")){
			 //订单号
			String facTradeSeq =  pcr.getFacTradeSeq();
			HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(Long.valueOf(facTradeSeq));
			if(hro != null){
				RechargeLogic.onRechargeMyCard(human.getPlayer(),Long.valueOf(facTradeSeq),0);
				logger.error(PaymentConfirm.class.getName()+" facTradeSeq  success !!! "+facTradeSeq);
			}else{
				logger.error(PaymentConfirm.class.getName()+" facTradeSeq  There is no"+facTradeSeq);
			}
		 }
		return STAGE_STOP_DONE;
	}

}
