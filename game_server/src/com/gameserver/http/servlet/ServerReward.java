package com.gameserver.http.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.http.vo.RewardVO;
import com.gameserver.mail.MailLogic;


/**
 *  全服奖励
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class ServerReward extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BufferedReader  br = new BufferedReader(new  InputStreamReader((ServletInputStream)req.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer("");  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                sb.append(temp);  
            }  
            br.close();  
            RewardVO rewardVO = JSONObject.parseObject(sb.toString(),RewardVO.class);
            //给单个人发送奖励
            if(rewardVO.getCharId() >0 ){
            	Globals.getPlayerCacheService().getPlayerCacheById(rewardVO.getCharId());
            	List<Long> recvIds = new ArrayList<Long>();
            	recvIds.add(rewardVO.getCharId());
            	MailLogic.getInstance().systemSendMail(null, rewardVO.getTitle(), rewardVO.getContent(), recvIds, rewardVO.getItemList());
            //给所有人发送奖励
            }else{
            	List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().getAll(HumanEntity.class);
            	for(HumanEntity humanEntity:humanEntityList){
            		List<Long> recvIds = new ArrayList<Long>();
            		recvIds.add(humanEntity.getPassportId());
            		MailLogic.getInstance().systemSendMail(null, rewardVO.getTitle(), rewardVO.getContent(), recvIds, rewardVO.getItemList());
            	}
            }
		}catch(Exception e){
			e.printStackTrace();
			resp.getWriter().print("{'errorCode':1}");
			return;
		}
		
		resp.getWriter().print("{'errorCode':0}");
	}
}
