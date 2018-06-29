package com.gameserver.human.manager;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.gift.HumanGift;
import com.gameserver.gift.msg.GCRequestGift;
import com.gameserver.human.Human;

/**
 * 角色随机礼包管理器
 * @author 郭君伟
 *
 */
public class HumanGiftManager implements RoleDataHolder, InitializeRequired {

    private Human owner;
	
	//当前vip数据
	private HumanGift humanGift;

	public HumanGiftManager(Human owner) {
		this.owner = owner;
	}

	/**
	 * 加载数据库数据到内存
	 */
	public void load() {
		
		HumanGiftEntity entity =  Globals.getDaoService().getHumanGiftDao().getHumanGiftById(owner.getPassportId());
		
		humanGift = new HumanGift();
		humanGift.setOwner(owner);
		
		if(entity == null){
			long now = Globals.getTimeService().now();
			humanGift.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANGIFT));
			humanGift.setCharId(owner.getPassportId());
			humanGift.setGiftid(0);
			humanGift.setRefreshTime(0l);
			humanGift.setCreateTime(now);
			humanGift.setInDb(false);
			humanGift.active();
			humanGift.setModified();//入库
			return;
		}
		
		humanGift.fromEntity(entity);
		
	}
	
	
	/** 加载完数据初始化 **/
	@Override
	public void init() {
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public HumanGift getHumanGift() {
		return humanGift;
	}

	public void setHumanGift(HumanGift humanGift) {
		this.humanGift = humanGift;
	}

	/**
	 * 更新礼包
	 * @param productId
	 */
    public void updateGift(int productId) {
    	//随机礼包
		int giftId = Globals.getGiftService().getGiftId(productId,(int)owner.getLevel());
		
		if(giftId != -1){
			
			long now = Globals.getTimeService().now();
			
			humanGift.setGiftid(giftId);
			//重新设置倒计时
			humanGift.setRefreshTime(now);
			
			humanGift.setModified();
			
			//通知客户端
			GCRequestGift gift = new GCRequestGift();
			gift.setGiftId(giftId);
			gift.setStartTime(now);
			owner.sendMessage(gift);
		}
	 }

    /**
     * 更新数据库
     * @param giftId
     * @param refreshTime
     */
    public void updateHumanGift(int giftId,long refreshTime){
    	humanGift.setGiftid(giftId);
    	humanGift.setRefreshTime(refreshTime);
    	humanGift.setModified();
    }
    
    
    /**
	 * 
	 * @param productId
	 */
	public boolean isPurchase(int productId) {
		
		return Globals.getGiftService().islegitimate(humanGift.getGiftid(), productId);
	}
	
    
	@Override
	public void checkAfterRoleLoad() {
	}

	@Override
	public void checkBeforeRoleEnter() {
	}

	

	

}
