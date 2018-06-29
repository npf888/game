package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubMemberEntity;

public class ClubMemberListDao extends BaseDao<ClubMemberEntity> {

	private static final String SELECT_QUERY = "selectAllMembers";
	private static final String selectMembersByPlayerId = "selectMembersByPlayerId";
	private static final String[] selectMembersByPlayerId_params = new String[] { "id" };

	private static final String  selectMembersByClubId = "selectMembersByClubId";
	private static final String[] selectMembersByClubId_params = new String[] { "club_id" };
	
	//根据个人信息查找
	public ClubMemberListDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<ClubMemberEntity> getEntityClazz() {
		return ClubMemberEntity.class;
	}

	/**
	 * @return
	 */
	public List<ClubMemberEntity> getClubMembersById(long clubId){
		List<ClubMemberEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(selectMembersByClubId,selectMembersByClubId_params,new Object[]{clubId});
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
	
	/**
	 * @return
	 */
	public ClubMemberEntity getClubMembersByPlayerId(long playerId){
		List<ClubMemberEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(selectMembersByPlayerId,selectMembersByPlayerId_params,new Object[]{playerId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	/**
	 * @return
	 */
	public List<ClubMemberEntity> getClubMembersAll(){
		List<ClubMemberEntity> entities= this._dbServ.findByNamedQuery(SELECT_QUERY);
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
}
