package com.gameserver.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.http.servlet.AddAndCostGold;
import com.gameserver.http.servlet.BazooRoom;
import com.gameserver.http.servlet.BeInvokedByWeixin;
import com.gameserver.http.servlet.BoquPay;
import com.gameserver.http.servlet.ChangeAgentDisplay;
import com.gameserver.http.servlet.ChangeHumanLevel;
import com.gameserver.http.servlet.Comesback;
import com.gameserver.http.servlet.ErrServlet;
import com.gameserver.http.servlet.JpushServlet;
import com.gameserver.http.servlet.MOLValidation;
import com.gameserver.http.servlet.NoticeRedPackage;
import com.gameserver.http.servlet.ObtainNumberOfGold;
import com.gameserver.http.servlet.OnlinePeople;
import com.gameserver.http.servlet.RankDataServlet;
import com.gameserver.http.servlet.Receivecode;
import com.gameserver.http.servlet.ServerNotice;
import com.gameserver.http.servlet.ServerReward;
import com.gameserver.http.servlet.ServiceInfo;
import com.gameserver.http.servlet.UpdateActivity;
import com.gameserver.http.servlet.UpdateFunction;
import com.gameserver.http.servlet.Validation;


public class HttpServer implements InitializeRequired{

	@Override
	public void init() {
	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				GameServerConfig config = Globals.getServerConfig();
				
		        Server servers = new Server();
		        
		        ServerConnector http = new ServerConnector(servers);
		        http.setHost(config.getHttpDataAddress());
		        http.setPort(config.getHttpDataPort());
		        http.setIdleTimeout(30000);
		        servers.addConnector(http);
				
				ServletHandler handler = new ServletHandler();
				
				servers.setHandler(handler);
				
				handler.addServletWithMapping(RankDataServlet.class, "/api/rankdata");
				handler.addServletWithMapping(Comesback.class, "/api/comesback");
				handler.addServletWithMapping(Validation.class, "/api/validation");
				handler.addServletWithMapping(ServiceInfo.class, "/api/serviceInfo");
				handler.addServletWithMapping(MOLValidation.class, "/api/molvalidation");
				handler.addServletWithMapping(BeInvokedByWeixin.class, "/api/beInvokedByWeixin");
				handler.addServletWithMapping(ChangeHumanLevel.class, "/api/changeHumanLevel");
				handler.addServletWithMapping(UpdateActivity.class, "/api/updateActivity");
				handler.addServletWithMapping(UpdateFunction.class, "/api/updateFunction");
				handler.addServletWithMapping(Receivecode.class, "/api/receivecode");
				handler.addServletWithMapping(JpushServlet.class, "/api/jpushServlet");
				handler.addServletWithMapping(ServerReward.class, "/api/rewardNotice");
				handler.addServletWithMapping(ServerNotice.class, "/api/serverNotice");
				handler.addServletWithMapping(OnlinePeople.class, "/api/online");
				handler.addServletWithMapping(BazooRoom.class, "/api/bazooRoom");
				handler.addServletWithMapping(AddAndCostGold.class, "/api/addAndCostGold");
				handler.addServletWithMapping(ChangeAgentDisplay.class, "/api/changeAgentDisplay");
				handler.addServletWithMapping(NoticeRedPackage.class, "/api/noticeRedPackage");
//				handler.addServletWithMapping(ThirdPartyRecharge.class, "/api/thirdPartyRecharge");
				handler.addServletWithMapping(ObtainNumberOfGold.class, "/api/obtainNumberOfGold");
				handler.addServletWithMapping(BoquPay.class, "/api/boquPay");
				handler.addServletWithMapping(ErrServlet.class, "/");
				
				try {
					servers.start();
					servers.join();
				} catch (Exception e) {
					Loggers.httpLogger.error("", e);
				}
				
			}
		}).start();
		
	}

}
