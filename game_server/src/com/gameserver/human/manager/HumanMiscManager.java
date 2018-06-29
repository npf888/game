package com.gameserver.human.manager;



import com.common.InitializeRequired;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanMiscEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.misc.HumanMisc;
import com.gameserver.misc.data.HumanMiscFBInfoData;
import com.gameserver.misc.data.HumanMiscInfoData;
import com.gameserver.misc.msg.GCMiscFbInfoData;
import com.gameserver.misc.msg.GCMiscInfoData;
import com.gameserver.misc.template.OnlineRewardTemplate;


/**misc
 * 
 * @author wayne
 *
 */
public class HumanMiscManager implements RoleDataHolder, InitializeRequired{
	/** 主人 */
	private Human owner;
	private HumanMisc humanMisc;

	public HumanMiscManager(Human owner){
		this.owner=owner;
	}
	
	public Human getOwner() {
		return owner;
	}
	

	public void setOwner(Human owner) {
		this.owner = owner;
	}
	
	public HumanMisc getHumanMisc(){
		return humanMisc;
	}
	
	public void load()
	{
		HumanMiscEntity humanMiscEntity = Globals.getDaoService()
				.getHumanMiscDao().getMiscByCharId(owner.getPassportId());
		humanMisc = new HumanMisc();
		if (humanMiscEntity == null) {
			long now = Globals.getTimeService().now();
			
			humanMisc.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANMISCID));
			humanMisc.setCharId(owner.getPassportId());
			humanMisc.setCreateTime(Globals.getTimeService().now());
			humanMisc.setOnlineTime(0);
			humanMisc.setCurrentOnlineRewardId(Globals.getMiscService().getFirstOnlineRewardTemplate().getId());
			humanMisc.setLastGetTime(Globals.getTimeService().now());
			humanMisc.setOwner(owner);
			humanMisc.setInDb(false);
			humanMisc.active();
			humanMisc.setModified();
			return;
		}
		humanMisc.setOwner(owner);
		humanMisc.fromEntity(humanMiscEntity);
	}


	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		checkIfRefresh();
		/*if(owner.getLastLogoutTime() > humanMisc.getLastGetTime()){
			
			humanMisc.setOnlineTime(humanMisc.getOnlineTime()+ owner.getLastLogoutTime()-humanMisc.getLastGetTime());
			humanMisc.setLastGetTime(Globals.getTimeService().now());
			humanMisc.setModified();
			
		}*/
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 更新
	 */
	public void checkIfRefresh(){
		
		long now = Globals.getTimeService().now();
		
		if(!TimeUtils.isSameDay(now, humanMisc.getUpdateTime())){
			humanMisc.setOnlineTime(0);
			humanMisc.setLastGetTime(Globals.getTimeService().now());
			humanMisc.setCurrentOnlineRewardId(Globals.getMiscService().getFirstOnlineRewardTemplate().getId());
			humanMisc.setModified();
		}
		
		//判断是否跨周
		/*if(!TimeUtils.isInSameWeek(now,  humanMisc.getUpdateTime())){
			humanMisc.getFbInviteRewardList().clear();
			humanMisc.getFbInviteList().clear();
		}*/
		
		long time = Globals.getConfigTempl().getFbInviteTime()*60*60*1000;
		if(humanMisc.getRefreshTime() <= now){
			humanMisc.getFbInviteRewardList().clear();
			humanMisc.getFbInviteList().clear();
			humanMisc.setRefreshTime(now+time);
		}
		
		Globals.getMiscService().complementInviteRewardList(humanMisc);
		
	}
	

	
	/**
	 * 领取奖励
	 */
	public void getCurrentOnlineReward(int index){
		OnlineRewardTemplate onlineRewardTemplate= Globals.getMiscService().getNextOnlineRewardTemplate(index);
		if(onlineRewardTemplate == null){
			
			humanMisc.setCurrentOnlineRewardId(0);
		}else{
			
			humanMisc.setCurrentOnlineRewardId(onlineRewardTemplate.getId());
		}
		humanMisc.setOnlineTime(0);
		humanMisc.setLastGetTime(Globals.getTimeService().now());
		humanMisc.setModified();
	}
	
	/**
	 * 累计时间
	 */
	public void updateOnlineTime(){
		long total = humanMisc.getOnlineTime() + Globals.getTimeService().now()- humanMisc.getLastGetTime();
		humanMisc.setOnlineTime(total);
		humanMisc.setModified();
	}
	
	/**
	 * misc数据
	 * @return
	 */
	public GCMiscInfoData buildGCMiscInfoData() {
		// TODO Auto-generated method stub
		GCMiscInfoData gcMiscInfoData = new GCMiscInfoData();
		HumanMiscInfoData humanMiscInfoData = HumanMiscInfoData.convertFromHumanMisc(humanMisc);
		gcMiscInfoData.setHumanMiscInfoData(humanMiscInfoData);
		return gcMiscInfoData;
	}
	
	/**
	 * 首冲
	 * @return
	 */
	public boolean ifFirstRecharge(){
		if(humanMisc.getFirstRechargeTime()==0)
			return true;
		return false;
	}
	
	/**
	 * 首冲
	 */
	public void firstRecharge(){
		humanMisc.setFirstRechargeTime(Globals.getTimeService().now());
		humanMisc.setModified();
		owner.sendMessage(buildGCMiscInfoData());
	}
	
	
	/**
	 * 是否重命名过
	 */
	public boolean ifRename(){
		return humanMisc.getRenameTimes()>0;
	}
	
	/**
	 * 重命名
	 */
	public void rename(){
		humanMisc.setRenameTimes(humanMisc.getRenameTimes()+1);
		humanMisc.setModified();
	}
	
	/**
	 * 是否是新手引导
	 */
	public boolean ifNewUser(){
		return humanMisc.getNewUser() == 0 ;
	}
	
	/**
	 * 完成新手引导
	 */
	public void finishNewUser(){
		humanMisc.setNewUser(1);
		humanMisc.setModified();
	}
	
	/**
	 * 判断是否领取过fb的奖励
	 */
	public boolean ifGetInviteRewardById(int rId){
		//越界
		if (rId>=humanMisc.getFbInviteRewardList().size() || rId<0){
			return false;
		}
		Integer val =humanMisc.getFbInviteRewardList().get(rId);
	
		if(val==0)
			return false;
		return true;
	}
	/**
	 * fB邀请人数
	 */
	public int numOfFBInvite(){
		return humanMisc.getFbInviteList().size();
	}
	
	/**
	 * 记录信息
	 * @param rId
	 */
	public void recordGetInviteRewardById(int rId){
		//一般不会发生
		if (rId>=humanMisc.getFbInviteRewardList().size() || rId<0){
			return;
		}
		humanMisc.getFbInviteRewardList().set(rId, 1);
		humanMisc.setModified();
	}
	
	/**
	 * 是否领取奖励
	 * true:已经领取过了 
	 */
	public boolean ifGetFbReward(){
		return humanMisc.getFbReward()!=0;
	}
	
	public void getFbReward() {
		// TODO Auto-generated method stub
		humanMisc.setFbReward(1);
		humanMisc.setModified();
	}
	
	public boolean ifFbThumb(){
		return humanMisc.getFbThumb()!=0;
	}
	
	public void getFbThumb() {
		humanMisc.setFbThumb(1);
		humanMisc.setModified();
	}
	
	
	/**
	 * fb misc数据
	 * @return
	 */
	public GCMiscFbInfoData buildGCMiscFBInfoData() {
		// TODO Auto-generated method stub
		GCMiscFbInfoData gcMiscFBInfoData = new GCMiscFbInfoData();
		HumanMiscFBInfoData humanMiscFBInfoData = HumanMiscFBInfoData.convertFromHumanMisc(humanMisc);
		gcMiscFBInfoData.setHumanMiscFBInfoData(humanMiscFBInfoData);
		return gcMiscFBInfoData;
	}

	
}
