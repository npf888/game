package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanGivealikeEntity;

public class HumanGivealikeDao extends BaseDao<HumanGivealikeEntity>{
		
		
		private static final String GET_LIKE_BY_SLOTTYPE_USERID_BET = "queryLikeByUserIdSlotTypeAndBet";
		private static final String GET_LIKE_BY_USERID = "queryLikeByUserId";

		public HumanGivealikeDao(DBService dbServ) {
			super(dbServ);
		}

		@Override
		protected Class<HumanGivealikeEntity> getEntityClazz() 
		{
			return HumanGivealikeEntity.class;
		}

		/**
		 * 更具用户ID和邮件类别从数据库中读取邮件列表
		 * @param UUID
		 * @return
		 */
		
		public List<HumanGivealikeEntity> getLikeByUserIdSlotTypeAndBet(long userId,int slotType,int slotBet)
		{
			return this._dbServ.findByNamedQueryAndNamedParam(GET_LIKE_BY_SLOTTYPE_USERID_BET,new String[]{"userId","slotType","slotBet"},new Object[]{userId,slotType,slotBet});
		}
		public List<HumanGivealikeEntity> getLikeByUserId(long userId)
		{
			return this._dbServ.findByNamedQueryAndNamedParam(GET_LIKE_BY_USERID,new String[]{"userId"},new Object[]{userId});
		}
		
		

}
