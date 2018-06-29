package com.gameserver.signin;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.signin.template.CumulativeSignInTemplate;
import com.gameserver.signin.template.SignInTemplate;

/**
 * 签到服务
 * @author wayne
 *
 */
public class SignInService implements InitializeRequired,AfterInitializeRequired{

	private Logger logger = Loggers.signInLogger;
	
	/**模板服务*/
	private TemplateService templateService;

	private Map<Integer,SignInTemplate> signInTemplateMap = new HashMap<Integer,SignInTemplate>();
	private Map<Integer,CumulativeSignInTemplate> cumulativeSignInTemplateMap = new HashMap<Integer,CumulativeSignInTemplate>();
	
	public SignInService(){
	}
	

	@Override
	public void init() {
		logger.info("sign in service init");

	}
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		logger.info("sign in service after init");

		templateService = Globals.getTemplateService();
	
		for(SignInTemplate signInTemplate: templateService.getAll(SignInTemplate.class).values()){
			signInTemplateMap.put(signInTemplate.getDay(), signInTemplate);
		
		}
		
		for(CumulativeSignInTemplate cumulativeSignInTemplate: templateService.getAll(CumulativeSignInTemplate.class).values()){
			cumulativeSignInTemplateMap.put(cumulativeSignInTemplate.getDay(), cumulativeSignInTemplate);
		}
	}
	
	/**
	 * 每天奖励
	 * @param day
	 * @return
	 */
	public RandRewardData getRandRewardDataForSignIn(int day){
		SignInTemplate signInTemplate = signInTemplateMap.get(day);
		if(signInTemplate == null)
			return null;
		return signInTemplate.getRandRewardData();
	}
	
	/**
	 * 累计奖励
	 * @param day
	 * @return
	 */
	public RandRewardData getRandRewardDataForCumulativeSignIn(int day){
		CumulativeSignInTemplate cumulativeSignInTemplate = cumulativeSignInTemplateMap.get(day);
		if(cumulativeSignInTemplate == null)
			return null;
			
		return cumulativeSignInTemplate.getRandRewardData();
	}
	
	public RandRewardData getRandRewardDataForSignInNew(int day){
		SignInTemplate signInTemplate = signInTemplateMap.get(day);
		if(signInTemplate == null)
			return new RandRewardData();
		return signInTemplate.getRandRewardData();
	}
	
	public int getSignSize(){
		return signInTemplateMap.size();
	}

}
