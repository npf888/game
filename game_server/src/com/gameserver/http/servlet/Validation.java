package com.gameserver.http.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.db.model.HumanRechargeOrderEntity;
import com.gameserver.common.Globals;
import com.gameserver.recharge.enums.OrderStatus;
import com.gameserver.recharge.enums.TopUpType;
import com.gameserver.recharge.template.RechargeTemplate;

public class Validation extends HttpServlet {
	
	private static final long serialVersionUID = 849190438520341068L;
	private Logger logger = Loggers.mycardvalidationMessageLogger;
	 

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
		
       String myCardTradeNo = req.getParameter("MyCardTradeNo");
       List<HumanRechargeOrderEntity> list = new ArrayList<HumanRechargeOrderEntity>();
       if(myCardTradeNo != null){
    	   List<HumanRechargeOrderEntity> list1 = Globals.getDaoService().getRechargeOrderDao().getMyCardTradeNo(myCardTradeNo);
    	   if(list1 != null){
    		   list = list1;
    	   }
    	   logger.info("MyCardTradeNo    "+myCardTradeNo);
    	   
       }else{
    	   String startDateTime1  = req.getParameter("StartDateTime");
		   String endDateTime1  = req.getParameter("EndDateTime");
    	long startDateTime;
		long endDateTime;
		try {
			
			   String[] str1 = startDateTime1.split("T");
			   String[] str2 = endDateTime1.split("T");
			   startDateTime = TimeUtils.getYMDHMSTime(str1[0]+" "+str1[1]);
			   endDateTime = TimeUtils.getYMDHMSTime(str2[0]+" "+str2[1]);
			   List<HumanRechargeOrderEntity> list1 = Globals.getDaoService().getRechargeOrderDao().getStartAndEnd(startDateTime,endDateTime);
			   if(list1 != null){
				   list = list1;
			   }
		} catch (ParseException e) {
			logger.error("", e);
		}
    	   logger.info("startDateTime    "+startDateTime1+"    endDateTime"+endDateTime1);
       }
       
       StringBuilder sb = new StringBuilder();
       
       for(HumanRechargeOrderEntity entity : list){
    	   int stat = entity.getOrderStatus();
    	  if( (stat == OrderStatus.PENDING.getIndex() || stat == OrderStatus.VALIDATE.getIndex()) && entity.getTopUpType() == TopUpType.MYCARD.getIndex()){
    		  
    		  String paymentType = entity.getPaymentType();
       	   
       	   String tradeSeq = entity.getTradeSeq();
       	   
       	   String MyCardTradeNo = entity.getMyCardTradeNo();
       	   
       	   long FacTradeSeq = entity.getId();
       	   
       	   long customerId  = entity.getCharId(); 
       	   
       	  //获取模板数据
      	   RechargeTemplate rechargeTemplate =  Globals.getRechargeService().getTemplate(entity.getChannelId(), entity.getProductId());
      	       
      	   int amount = rechargeTemplate.getNum();
       	   
       	   String Currency = "TWD";
       	   
       	    long TradeDateTime = entity.getCreateTime();
       	   
        	String str = TimeUtils.formatYMDHMSTime(TradeDateTime);
		    String[] ss = str.split(" ");
		    String createTime = ss[0]+"T"+ss[1];
       	   
       	   sb.append(paymentType).append(",").append(tradeSeq).append(",")
       	   .append(MyCardTradeNo).append(",").append(FacTradeSeq).append(",")
       	   .append(customerId).append(",").append(amount).append(",")
       	   .append(Currency).append(",").append(createTime).append("<BR>");
    	  }
    	   
    	  
       }
       logger.info(sb.toString());
       PrintWriter writer = resp.getWriter();
		writer.write(sb.toString());
		writer.close();
	    
	}

}
