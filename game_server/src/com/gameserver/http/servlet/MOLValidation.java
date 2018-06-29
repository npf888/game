package com.gameserver.http.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.util.MD5Util;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.data.MolValidationOrder;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.msg.GCMolOrder;

@SuppressWarnings("serial")
public class MOLValidation extends HttpServlet {

	 private Logger logger = Loggers.MOLVALIDATION;
	 

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        resp.setContentType("text/html;charset=UTF-8");
			
	       String amount = req.getParameter("amount");
	       String applicationCode = req.getParameter("applicationCode");
	       String referenceId =  req.getParameter("referenceId");
	       String paymentId = req.getParameter("paymentId");
	       String version =  req.getParameter("version");
	       String currencyCode = req.getParameter("currencyCode");
	       String paymentStatusCode = req.getParameter("paymentStatusCode");
	       String paymentStatusDate = req.getParameter("paymentStatusDate");
	       String customerId = req.getParameter("customerId");
	       String signature = req.getParameter("signature");
	       logger.info("========================================================================================");
	       
	       logger.info("参数  { amount= "+amount+"} + { applicationCode="+applicationCode+" } + { currencyCode="+currencyCode+"} + { customerId="+customerId+"} + { paymentId="+paymentId+" } +  { paymentStatusCode="+paymentStatusCode+" } +  { paymentStatusDate="+paymentStatusDate+"} +  { referenceId="+referenceId+"} + { version="+version+"}+{signature="+signature+"}");
	       
	      // String secretKey = "Y0Y9N5c9cssT3ap6TqSIaMcFnwTKQiK3";
	       String secretKey = "Ph6mgq8XmY2Ah8zUz4WCzdGuxrL7xenl";
	       /**
	        * { amount } + { applicationCode } + { currencyCode } + { customerId } + { paymentId } +  { paymentStatusCode } +  { paymentStatusDate } +  { referenceId } + { version }  
	        */
	       StringBuilder sb = new StringBuilder();
	       
	       if(amount != null && !amount.trim().equals("")){
	    	   sb.append(amount.trim());
	       }
	       if(applicationCode != null && !applicationCode.trim().equals("")){
	    	   sb.append(applicationCode.trim());
	       }
	       if(currencyCode != null && !currencyCode.trim().equals("")){
	    	   sb.append(currencyCode.trim());
	       }
	       if(customerId != null && !customerId.trim().equals("")){
	    	   sb.append(customerId.trim());
	       }
	       if(paymentId != null && !paymentId.trim().equals("")){
	    	   sb.append(paymentId.trim());
	       }
	       if(paymentStatusCode != null && !paymentStatusCode.trim().equals("")){
	    	   sb.append(paymentStatusCode.trim());
	       }
	       if(paymentStatusDate != null && !paymentStatusDate.trim().equals("")){
	    	   sb.append(paymentStatusDate.trim());
	       }
	       if(referenceId != null && !referenceId.trim().equals("")){
	    	   sb.append(referenceId.trim());
	       }
	       if(version != null && !version.trim().equals("")){
	    	   sb.append(version.trim());
	       }
	        
	       sb.append(secretKey);
	       
	      String md5Str =  MD5Util.encodeByMD5(sb.toString());
	      logger.info("生成的字符串  = "+sb.toString());
	      logger.info("生成的MD5String = "+md5Str);
	       if(md5Str.equals(signature)){//md5验证通过
	    	   
	    	    //角色ID
	    	    long charid = Long.valueOf(customerId);
	    	    //订单ID
	    	    long orderID = Long.valueOf(referenceId);
	    		
	    		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(charid);
	    		
	    		if(player != null){
	    			
	    			Human human = player.getHuman();
	    			
	    			HumanRechargeOrder hro = human.getHumanRechargeManager().getOrderById(orderID);
	    			
	    			if(hro != null && hro.getOrderStatus() == OrderStatus.NON_VALIDATE){
	    				GCMolOrder message = new GCMolOrder();
	    				List<MolValidationOrder> list = new ArrayList<MolValidationOrder>();
	    				MolValidationOrder mv = new MolValidationOrder();
	    				mv.setReferenceId(referenceId);
	    				mv.setPaymentId(paymentId);
	    				list.add(mv);
	    				message.setMolValidationOrder(list.toArray(new MolValidationOrder[list.size()]));
	    				player.sendMessage(message);
	    				
	    				hro.setCurrencyCode(currencyCode);
	    				hro.setAmountmol(Integer.parseInt(amount));
	    				hro.setPaymentIdmol(paymentId);
	    				hro.setOrderStatus(OrderStatus.PENDING);
	    				hro.setModified();
	    				logger.info("角色在线 ="+JSON.toJSONString(hro));
	    				logger.info("发往客户端消息 =referenceId["+referenceId+"]  +paymentId["+paymentId+"]");
	    			}else{
	    				if(hro == null){
	    					logger.info("角色在线 订单已经处理过了!!!");
	    				}else{
	    					logger.info("订单状态已经标记为未处理状态！！！");
	    				}
	    				
	    			}
	    		}else{//角色不在线 获取订单然后
	    			
	    	      HumanRechargeOrderEntity entity = Globals.getDaoService().getRechargeOrderDao().getRechargeOrders(orderID);
	    	      
	    	      if(entity != null && entity.getOrderStatus() == OrderStatus.NON_VALIDATE.getIndex()){
	    	    	    //订单号
						entity.setOrderStatus(OrderStatus.PENDING.getIndex());
						entity.setAmountmol(Integer.parseInt(amount));
						entity.setCurrencyCode(currencyCode);
						entity.setPaymentIdmol(paymentId);
						//修改数据库
						Globals.getDaoService().getRechargeOrderDao().update(entity);
						logger.info("角色不在线状态以成功标注");
	    	      }else{
	    	    	  if(entity == null){
	    	    		  logger.info("订单不存在");
	    	    	  }else{
	    	    		  logger.info("订单已经标准状态为 "+entity.getOrderStatus());
	    	    	  }
	    	      }
	    	}
	    		
	       }else{
	    	   logger.info("验证失败！！！");
	       }
	        
		    
		}
}
