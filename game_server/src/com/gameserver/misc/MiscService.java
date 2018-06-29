package com.gameserver.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.misc.template.FbInviteTemplate;
import com.gameserver.misc.template.OnlineRewardTemplate;

/**
 * 
 * @author wayne
 *
 */
public class MiscService implements InitializeRequired,AfterInitializeRequired{
	
	private Logger logger = Loggers.miscLogger;
	
	private List<OnlineRewardTemplate> onlineRewardTempalteList = new ArrayList<OnlineRewardTemplate> ();
	private List<FbInviteTemplate> fbInviteTemplateList = new ArrayList<FbInviteTemplate>();
	
	
	public List<FbInviteTemplate> getFbInviteTemplateList(){
		return this.fbInviteTemplateList;
	}
	
	@Override
	public void afterInit() {
		
		// TODO Auto-generated method stub
		logger.info("misc service after init");
		onlineRewardTempalteList.addAll(Globals.getTemplateService().getAll(OnlineRewardTemplate.class).values());
		fbInviteTemplateList.addAll(Globals.getTemplateService().getAll(FbInviteTemplate.class).values());
		
		Collections.sort(this.fbInviteTemplateList, new Comparator<FbInviteTemplate>(){

			@Override
			public int compare(FbInviteTemplate arg0, FbInviteTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId()==arg1.getId())
					return 0;
				if(arg0.getId()>arg1.getId())
					return 1;
				else{
					return -1;
				}
			
			}
			
		});
		
		Collections.sort(this.onlineRewardTempalteList, new Comparator<OnlineRewardTemplate>(){

			@Override
			public int compare(OnlineRewardTemplate arg0, OnlineRewardTemplate arg1) {
				// TODO Auto-generated method stub
				if(arg0.getId()==arg1.getId())
					return 0;
				if(arg0.getId()>arg1.getId())
					return 1;
				else{
					return -1;
				}
			
			}
			
		});
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.info("misc service init");
	}
	
	public List<OnlineRewardTemplate> getOnlineRewardTempalteList(){
		return onlineRewardTempalteList;
	}
	
	public OnlineRewardTemplate getFirstOnlineRewardTemplate(){
		return onlineRewardTempalteList.get(0);
	}
	
	/**
	 * 在线奖励
	 * @param index
	 * @return
	 */
	public OnlineRewardTemplate onlineRewardTemplate(int index){
		return Globals.getTemplateService().get(index, OnlineRewardTemplate.class);
	}

	/**
	 * 下一档在线奖励
	 * @param index
	 * @return
	 */
	public OnlineRewardTemplate getNextOnlineRewardTemplate(int index){

	 	Iterator<OnlineRewardTemplate> iter =  onlineRewardTempalteList.iterator();
	 	while(iter.hasNext())
	 	{
	 		if(iter.next().getId() == index){
	 			if(iter.hasNext())
	 				return iter.next();
	 		}
	 	}
	 	return null;
	}
	
	/**
	 * 补充奖励 方便拓展
	 * @param humanMisc
	 */
	public void complementInviteRewardList(HumanMisc humanMisc) {
		// TODO Auto-generated method stub
		if(humanMisc.getFbInviteRewardList().size() == fbInviteTemplateList.size()){
			return;
		}
		
		//没有减少
		if(humanMisc.getFbInviteRewardList().size() > fbInviteTemplateList.size()){
			return;
		}
		int complement =  fbInviteTemplateList.size()-humanMisc.getFbInviteRewardList().size();
		for(int i=0;i<complement;i++){
			humanMisc.getFbInviteRewardList().add(0);
		}
		
	}
	
	 
}
