package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.db.model.HumanRefundEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.mail.MailLogic;
import com.gameserver.refund.HumanRefund;
import com.gameserver.refund.enums.RefundStatusEnum;

/**
 * 返还管理器
 * @author wayne
 *
 */
public class HumanRefundManager  implements RoleDataHolder, InitializeRequired{

   private Logger logger = Loggers.compensationLogger;
	
	private Human owner;
	private List<HumanRefund> humanRefundList = new ArrayList<HumanRefund>();
	
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanRefundManager(Human owner) {
		this.owner = owner;

	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public List<HumanRefund> getHumanRefundList(){
		return humanRefundList;
	}
	
	/**
	 * 加载补偿数据  */
	public void load(){
		logger.debug("玩家["+owner.getPassportId()+"]加载refund管理器");
		List<HumanRefundEntity> HumanRefundEntityList = Globals.getDaoService().getHumanRefundDao().getAllHumanRefunds(owner.getPassportId());
		
		if(HumanRefundEntityList!=null&&HumanRefundEntityList.size()>0){
			for(HumanRefundEntity humanRefundEntity:HumanRefundEntityList){
		
				HumanRefund humanRefund=new HumanRefund();
				humanRefund.setOwner(owner);
				humanRefund.fromEntity(humanRefundEntity);
				humanRefundList.add(humanRefund);
			}
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.debug("玩家["+owner.getPassportId()+"]初始化补偿");
		for( HumanRefund humanRefund : humanRefundList){
			if(humanRefund.getRefundStatus()== RefundStatusEnum.NON_SEND)
			{
				//发送邮件
				List<Long> recIds = new ArrayList<Long>();
				recIds.add(owner.getPassportId());
				MailLogic.getInstance().systemSendMailCompensation(null, humanRefund.getTitle(),humanRefund.getContent(), recIds, humanRefund.getItemList());
				humanRefund.setRefundStatus(RefundStatusEnum.SEND);
				humanRefund.setModified();
			}
		}
	}



	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
}
