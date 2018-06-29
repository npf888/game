package com.gameserver.http.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.db.model.HumanBazooRankEntity;
import com.db.model.HumanEntity;
import com.gameserver.bazoorank.HumanBazooRank;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
@SuppressWarnings("serial")
public class ChangeAgentDisplay extends HttpServlet{
	private Logger logger = Loggers.BAZOO;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String display = req.getParameter("display");
		String passportId = req.getParameter("passportId");
		if(StringUtils.isNotBlank(display) && StringUtils.isNotBlank(passportId)){
			
			Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(passportId));
			if(player != null){//在线
				//先改 排行榜
				HumanBazooRank HumanBazooRank = player.getHuman().getHumanBazooRankManager().getHumanBazooRank();
				HumanBazooRank.setBazooAgentDisplay(Integer.valueOf(display));
				HumanBazooRank.setModified();
				
				//再改用户
				player.getHuman().setBazooAgentDisplay(Integer.valueOf(display));
				player.getHuman().setModified();
			}else{//不在线
				//先改排行榜
				HumanBazooRankEntity entity = Globals.getDaoService().getBazooRankDao().getBazooRankByPassportId(Long.valueOf(passportId));
				entity.setBazooAgentDisplay(Integer.valueOf(display));
				Globals.getDaoService().getBazooRankDao().saveOrUpdate(entity);
				
				List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(Long.valueOf(passportId));
				if(humanEntityList != null && humanEntityList.size()>0){
					humanEntityList.get(0).setBazooAgentDisplay(Integer.valueOf(display));
					Globals.getDaoService().getHumanDao().saveOrUpdate(humanEntityList.get(0));
				}
			}
		}
	}

}
