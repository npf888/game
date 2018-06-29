package com.gameserver.recharge.https;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.HttpUtil;
import com.core.util.HttpsUtil;
import com.core.util.SHA;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.result.ListSDKTradeQueryData;
import com.gameserver.recharge.result.SDKTradeQueryResult;

/**
 * 验证订单
 * @author 郭君伟
 *
 */
public class SDKTradeQuery implements IIoOperation {
	
	 private Logger logger = Loggers.mycardMessageLogger;
	
	 private Human human;
	 
	 private Map<String, Object> paramMap = new HashMap<String, Object>();
	 
	 private volatile SDKTradeQueryResult stqr;
	 
	 public SDKTradeQuery(Human human,String facTradeSeq){
		 this.human = human;
		 paramMap.put("FacServiceId",Parameter.facServiceId);
		 paramMap.put("FacTradeSeq", facTradeSeq);
		 paramMap.put("StartDateTime","");
		 paramMap.put("EndDateTime","");
		 paramMap.put("CancelStatus","2");
		 String preHashValue = Parameter.facServiceId+facTradeSeq+"2"+Parameter.key;
	    	
	     String urlEncode = HttpUtil.encode(preHashValue);
	    	
	     String hash = SHA.SHA256(urlEncode);
	     
		 paramMap.put("Hash",hash);
		
		 logger.info(SDKTradeQuery.class.getName()+"    start = "+ JSON.toJSONString(paramMap));
	 }
	 

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		String result = HttpsUtil.doGet(Parameter.url4, paramMap, true);
		if(result != null){
			stqr = JSON.parseObject(result, SDKTradeQueryResult.class);
			 logger.info(SDKTradeQuery.class.getName()+" Request Data ==  "+result);
		}else{
			 logger.error(SDKTradeQuery.class.getName()+"  Request error  ");
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		
		if(stqr != null){
			List<ListSDKTradeQueryData> list = stqr.getListSDKTradeQuery();
			for(ListSDKTradeQueryData data : list){
				if(data.getPaymentType().equals("3")){
					HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(Long.valueOf(data.getFacTradeSeq()));
					if(hro != null){
						Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new TradeQuery(human,hro.getAuthCode()));
						logger.error(SDKTradeQuery.class.getName()+" facTradeSeq = "+data.getFacTradeSeq()+"   success !!! ");
					}else{
						logger.error(SDKTradeQuery.class.getName()+" facTradeSeq = "+data.getFacTradeSeq()+"   There is no");
					}
				}else{
					 logger.error(SDKTradeQuery.class.getName()+"  Pay for failure   "+JSON.toJSONString(data));
				}
			}
		}
		return STAGE_STOP_DONE;
	}

}
