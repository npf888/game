package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanMonthWeekEntity;

public class HumanMonthWeekDao extends BaseDao<HumanMonthWeekEntity> {
	
	private static final String HUMAN_MONTH_WEEK_BY_USERID_AND_MNTYPE = "monthWeekEntityByUserIdAndMnType";

	public HumanMonthWeekDao(DBService dbServ) {
		super(dbServ);
	}

	public HumanMonthWeekEntity getHumanMonthWeekEntityByUserIdAndMnType(long userId,int type){
		List<HumanMonthWeekEntity> list = this._dbServ.findByNamedQueryAndNamedParam(HUMAN_MONTH_WEEK_BY_USERID_AND_MNTYPE, 
				new String[]{"userId","mwType"}, new Object[]{userId,type});
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
	
	
	@Override
	protected Class<HumanMonthWeekEntity> getEntityClazz() {
		return HumanMonthWeekEntity.class;
	}

	
}
