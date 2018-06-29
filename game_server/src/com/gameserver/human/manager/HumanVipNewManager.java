package com.gameserver.human.manager;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.human.msg.GCHumanChangeVip;
import com.gameserver.vipnew.HumanVipNew;
import com.gameserver.vipnew.VipNewServer;
import com.gameserver.vipnew.data.HumanVipNewInfoData;
import com.gameserver.vipnew.msg.GCVipNewData;
import com.gameserver.vipnew.template.VipNewTemplate;

/**
 * Vip 新系统管理器 业务逻辑操作
 * @author 郭君伟
 *
 */
public class HumanVipNewManager implements RoleDataHolder ,InitializeRequired{
	
	private Human owner;
	
	//当前vip数据
	private HumanVipNew vip;

	public HumanVipNewManager(Human owner) {
		this.owner = owner;
	}

	/**
	 * 加载数据库数据到内存
	 */
	public void load() {
		HumanVipNewEntity entity = Globals.getDaoService().getVipNewDao().getVipHumanById(owner.getPassportId());
		vip = new HumanVipNew();
		vip.setOwner(owner);
		if(entity == null){
			long now = Globals.getTimeService().now();
			vip.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANVIPID));
			vip.setHumanId(owner.getPassportId());
			vip.setVipLevel(0);
			vip.setCurPoint(0);
			vip.setCreateTime(Globals.getTimeService().now());
			vip.setInDb(false);
			vip.active();
			vip.setModified();
			return;
		}
		vip.fromEntity(entity);
	}
	
	
	
	/**
	 * 封装消息  
	 * 1 在角色第一次登陆的时候发给客户端
	 * 2 数据每次变化的时候发生
	 * @return
	 */
	public GCVipNewData buildVipData(){
		GCVipNewData data = new GCVipNewData();
		HumanVipNewInfoData info = HumanVipNewInfoData.getVipNewData(vip);
		data.setHumanVipNewInfoData(info);
		return data;
	}
	
	
	/**
	 * 增加VIP点数
	 * @param vipPoint 加成的点
	 */
    public void addPoint(int vipPoint) {
    	
    	int oldlevel = vip.getVipLevel();
    	
    	int oldCurPoint = vip.getCurPoint();
    	
    	VipNewServer server = Globals.getVipNewServer();
    	
    	int newLevel = oldlevel;
    	//加成以后的VIP点
    	int newCurPoint = oldCurPoint + server.getVipPointAddByLv(oldlevel,vipPoint);
    	int maxPoint = 0;
    	do{
    		VipNewTemplate template = server.getVipNewTemplate(newLevel+1);
    		
    		if(template == null){
    			break;
    		}
    		maxPoint = template.getVipPoint();
    		
    		if(newCurPoint >= maxPoint){
    			++newLevel;
    			newCurPoint = newCurPoint - maxPoint;
    		}
    	}while(newCurPoint > maxPoint);
    	
    	vip.setVipLevel(newLevel);
    	vip.setCurPoint(newCurPoint);
    	
    	vip.setModified();
    	if(newLevel > oldlevel){
    		this.owner.getHumanAchievementManager().updateVipLevel();
    	}
    	owner.sendMessage(buildVipData());
    	sendChangeVipMessage(owner,newLevel);
	}
    /**
     * 
     * 这个没有经验加成
     * 增加VIP点数
     * @param vipPoint 加成的点
     */
    public void addOnlyThePassPoint(int vipPoint) {
    	
    	int oldlevel = vip.getVipLevel();
    	
    	int oldCurPoint = vip.getCurPoint();
    	
    	VipNewServer server = Globals.getVipNewServer();
    	
    	int newLevel = oldlevel;
    	//加成以后的VIP点
    	int newCurPoint = oldCurPoint + vipPoint;
    	int maxPoint = 0;
    	do{
    		VipNewTemplate template = server.getVipNewTemplate(newLevel+1);
    		
    		if(template == null){
    			break;
    		}
    		maxPoint = template.getVipPoint();
    		
    		if(newCurPoint >= maxPoint){
    			++newLevel;
    			newCurPoint = newCurPoint - maxPoint;
    		}
    	}while(newCurPoint > maxPoint);
    	
    	vip.setVipLevel(newLevel);
    	vip.setCurPoint(newCurPoint);
    	
    	vip.setModified();
    	if(newLevel > oldlevel){
    		this.owner.getHumanAchievementManager().updateVipLevel();
    	}
    	owner.sendMessage(buildVipData());
    	sendChangeVipMessage(owner,newLevel);
    }
    
    /**
     * 每次 用户vip等级变化 都发送消息
     */
    private void sendChangeVipMessage(Human human,int newLevel){
    	GCHumanChangeVip gCHumanChangeVip = new GCHumanChangeVip();
		gCHumanChangeVip.setVip(newLevel);
		human.sendMessage(gCHumanChangeVip);
    }
    
    
    /**
     * 获取VIP等级
     * @return
     */
    public int getVipLv(){
    	return vip.getVipLevel();
    }
    
	
	@Override
	public void checkAfterRoleLoad() {
	}

	@Override
	public void checkBeforeRoleEnter() {
	}

	
	public void init() {
		
	}
	
	

	
	



	

}
