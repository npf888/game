package com.gameserver.http.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.util.HttpsUtil;
import com.core.util.IOUtils;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.http.json.ComesbackVO;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.https.Parameter;
import com.gameserver.recharge.https.TradeQuery;
import com.gameserver.recharge.result.PaymentConfirmResult;
import com.gameserver.recharge.result.TradeQueryResult;

/**
 * 
 * @author 郭君伟
 *
 */
public class Comesback extends HttpServlet {
	
	 private Logger logger = Loggers.mycardcomesbackMessageLogger;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		InputStream in = req.getInputStream();
		
	    String param = IOUtils.getString(in);
	    
	    logger.info("Request parameters  "+param);
	    //IP地址
	    //req.getRemoteAddr();
	    
	    if(param != null && !param.trim().equals("")){
	    	String str = param.split("=")[1];
	    	ComesbackVO vo = JSON.parseObject(str,ComesbackVO.class);
	    	
	    	logger.info("ComesbackVO  "+JSON.toJSONString(vo));
	    	//订单号
	    	List<String> list = vo.getFacTradeSeq();
	    	
	    	for(String orderId : list){
	    		
	    	    HumanRechargeOrderEntity entity = Globals.getDaoService().getRechargeOrderDao().getRechargeOrders(Long.valueOf(orderId));
	    		
	    	    if(entity == null || entity.getOrderStatus() == OrderStatus.PENDING.getIndex() ||  entity.getOrderStatus() == OrderStatus.VALIDATE.getIndex()){
	    	    	logger.info("This order has been processed !!!  "+orderId);
	    	    	continue;
	    	    }
	    		long charid = entity.getCharId();
	    		
	    		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(charid);
	    		
	    		if(player != null){
	    			logger.info("success!!!  "+charid);
	    			 Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(new TradeQuery(player.getHuman(),entity.getAuthCode()));
	    		}else{
	    			logger.info("Players didn't online  "+charid);
	    			Map<String,Object> paramMap = new HashMap<String,Object>();
	    			paramMap.put("AuthCode", entity.getAuthCode());
	    			String result = HttpsUtil.doGet(Parameter.url2, paramMap, true);
	    			
	    			if(result != null){
	    				TradeQueryResult tqr = JSON.parseObject(result, TradeQueryResult.class);
	    				 logger.info(" 3.3 Request Data ==  "+result);
	    				 if(tqr != null && tqr.getReturnCode().equals("1")){
	    						String payResult = tqr.getPayResult();
	    						//订单号
	    						String facTradeSeq = tqr.getFacTradeSeq();
	    						if(payResult.equals("3")){
	    							
	    							String paymentType = tqr.getPaymentType();
	    							String myCardTradeNo = tqr.getMyCardTradeNo();
	    							
	    							entity.setPaymentType(paymentType);
    								entity.setMyCardTradeNo(myCardTradeNo);
    								
    								String resultPay = HttpsUtil.doGet(Parameter.url3, paramMap, true);
    								if(resultPay != null){
    									PaymentConfirmResult pcr = JSON.parseObject(result, PaymentConfirmResult.class);
    									if(pcr != null && pcr.getReturnCode().equals("1")){
    										 //订单号
    										entity.setOrderStatus(OrderStatus.PENDING.getIndex());
    										//修改数据库
    										Globals.getDaoService().getRechargeOrderDao().update(entity);
    									 }
    									logger.info(" 3.4 Request Data ==  "+result);
    								}else{
    									logger.error("  Request error    ");
    								}
	    							
	    						}else{
	    							 logger.error(" facTradeSeq = "+facTradeSeq+"   cancel ");
	    						}
	    					}
	    			}else{
	    				logger.error("  Request error  ");
	    			}
	    			
	    		}
	    	}
	    }else{
	    	logger.error("parameters error !!! ");
	    }
		in.close();
	}
	

	
}
