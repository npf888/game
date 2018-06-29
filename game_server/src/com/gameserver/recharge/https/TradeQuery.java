package com.gameserver.recharge.https;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.HttpsUtil;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.result.TradeQueryResult;

public class TradeQuery implements IIoOperation {
	
    private Logger logger = Loggers.mycardMessageLogger;
	
	private Human human;
	
	private String authCode;
	
	private volatile TradeQueryResult tqr;
	
	private Map<String,Object> paramMap = new HashMap<String,Object>();
	
	public TradeQuery(Human human,String authCode){
        this.human = human;
        this.authCode = authCode;
		paramMap.put("AuthCode", authCode);
		logger.info(TradeQuery.class.getName()+" start = "+JSON.toJSONString(paramMap));
	}

	@Override
	public int doStart() {
		
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		String result = HttpsUtil.doGet(Parameter.url2, paramMap, true);
		if(result != null){
			 tqr = JSON.parseObject(result, TradeQueryResult.class);
			 logger.info(TradeQuery.class.getName()+" Request Data ==  "+result);
		}else{
			logger.error(TradeQuery.class.getName()+"  Request error  ");
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		
		if(tqr != null && tqr.getReturnCode().equals("1")){
			String payResult = tqr.getPayResult();
			//订单号
			String facTradeSeq = tqr.getFacTradeSeq();
			if(payResult.equals("3")){
				String paymentType = tqr.getPaymentType();
				String myCardTradeNo = tqr.getMyCardTradeNo();
				
				HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(Long.valueOf(facTradeSeq));
				if(hro != null){
					hro.setPaymentType(paymentType);
					hro.setMyCardTradeNo(myCardTradeNo);
					hro.setModified();
				    Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new PaymentConfirm(human,authCode));
				    logger.error(TradeQuery.class.getName()+" facTradeSeq = "+facTradeSeq+"   success !!! ");
				}else{
					logger.error(TradeQuery.class.getName()+" facTradeSeq = "+facTradeSeq+"   There is no");
				}
			}else{
				HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(Long.valueOf(facTradeSeq));
				if(hro != null){
					human.getHumanRechargeManager().cancelOrder(hro);
				}
				 logger.error(TradeQuery.class.getName()+" facTradeSeq = "+facTradeSeq+"   cancel ");
			}
		}
		return STAGE_STOP_DONE;
	}

}
