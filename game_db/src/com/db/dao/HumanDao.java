package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.dao.BaseDao;
import com.db.model.HumanBazooRankEntity;
import com.db.model.HumanEntity;

/**
 * 人物角色数据管理操作类
 * @author Thinker
 * 
 */
public class HumanDao extends BaseDao<HumanEntity>
{
	/** 查询语句名称 ： 根据账号ID获取所有 characterInfo */
	public static final String QUERY_GET_CHARACTERS_BY_PID = "queryCharactersByPid";
	/** 查询语句名称 ： 根据姓名获取 CharacterInfo */
	public static final String QUERY_GET_CHARACTER_BY_NAME = "queryCharacterByName";
	public static final String QUERY_GET_CHARACTER_BY_NAME_ALL = "queryCharacterByNameAll";
	/** 更新玩家当日累计在线时间 */
	public static final String UPDATE_USER_ONLINE_TIME = "updateUserOnlineTime";
	/** 查询：根据角色ID查询character */
	public static final String QUERY_CHARACTER_BY_UUID = "queryCharacterByUUID";
	/** 根据场景 Id 查询玩家角色列表 */
	public static final String QUERY_HUMANS_BY_SCENE_ID = "queryHumansBySceneId";	
	/** 根据场景 Id 查询玩家角色列表 */
	public static final String QUERY_HUMANS_BY_ROLE_KIND = "queryHumansByRoleKind";	
	/**查询所有玩家 */
	public static final String QUERY_ALL_CHARACTERS = "queryAllCharacter";
	/** 获取当前所有玩家现存人民币金子数量*/
	public static final String QUERY_ALL_CHARGE_GOLD="queryAllPlayerchargeGolds";
	
	public static final String QUERY_HUMANINFO_BY_ID ="queryHumanInfoById";
	/**根据时间段获取玩家数据**/
	public static final String QUERY_HUMANINFO_BY_TIMESPAN ="queryHumanInfoByTimeSpan";
	/**根据离线组队机器人查询玩家**/
	public static final String QUERY_HUMANINFO_BY_OFFLINETEAMROBOT ="queryHumanInfoByOfflineTeamRobot";
	/**根据等级查询离线组队玩家**/
	public static final String QUERY_HUMANINFO_BY_LEVEL ="queryHumanInfoByLevel";
	
	
	/**无双吹牛 的排行榜**/
	public static final String SELECT_PLAYER_ORDER_BY_GOLD ="selectPlayerOrderByGold";
	
	
	
	
	public HumanDao(DBService dbService)
	{
		super(dbService);
	}

	@Override
	protected Class<HumanEntity> getEntityClazz() 
	{
		return HumanEntity.class;
	}
	
	/**
	 * 根据账号ID从数据库获取所有角色
	 */
	public List<HumanEntity> loadHumans(long passportId)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_GET_CHARACTERS_BY_PID, new String[] { "passportId" },
				new Object[] { passportId });
	}
	/**
	 * 根据角色名获取第一个HumanEntit
	 */
	public HumanEntity loadHuman(String name)
	{
		List<HumanEntity> _charList = _dbServ.findByNamedQueryAndNamedParam(QUERY_GET_CHARACTER_BY_NAME, new String[] { "name" },
				new Object[] { name });
		if (_charList.size() > 0)
		{
			return (HumanEntity) _charList.get(0);
		}
		return null;
	}
	/**
	 * 根据角色名获取第一个HumanEntit
	 */
	public List<HumanEntity> loadHumanAll(String name)
	{
		List<HumanEntity> _charList = _dbServ.findByNamedQueryAndNamedParam(QUERY_GET_CHARACTER_BY_NAME_ALL, new String[] { "name" },
				new Object[] { name });
		return _charList;
	}
	/**
	 * 根据角色名获取第一个HumanEntit
	 *//*
	public List<HumanEntity> loadHumanAll(String name)
	{
		List<HumanEntity> _charList = _dbServ.findByNamedQueryAndNamedParam(QUERY_GET_CHARACTER_BY_NAME_ALL, new String[] { "name" },
				new Object[] { "%"+name+"%" });
		return _charList;
	}*/

	/**
	 * 根据UUID从数据库中查询相应角色
	 */
	public List<HumanEntity> queryHumanByUUID(long UUID)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_CHARACTER_BY_UUID,new String[] { "id" }, new Object[] { UUID });
	}
	/**
	 * 根据场景 Id 加载玩家角色列表
	 */
	public List<HumanEntity> queryHumansBySceneId(int sceneId)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_HUMANS_BY_SCENE_ID,
			new String[] { "sceneId" }, new Object[] { sceneId });
	}
	
	/**
	 * 根据角色类型加载玩家角色列表
	 */
	public List<HumanEntity> queryHumansByRoleKind(int roleKind)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_HUMANS_BY_ROLE_KIND,
			new String[] { "roleKind" }, new Object[] { roleKind });
	}
	
	public List<HumanEntity> queryAllHuman()
	{
		return _dbServ.findByNamedQuery(QUERY_ALL_CHARACTERS);
	}
	/***
	 * 获取当前所有玩家现存人民币金子数量
	 */
	public long queryAllPlayerchargeGolds()
	{
		Long returnValue = _dbServ.findUniqueResultByNamedQuery(QUERY_ALL_CHARGE_GOLD);
		if (returnValue ==null) return 0L;
		
		return returnValue;
	}
	/**
	 * 根据ID查询用户名
	 * @return
	 */
	public String[] queryUserNameById(long id)
	{
		List<Object[]> humanInfo = this._dbServ.findByNamedQueryAndNamedParam(QUERY_HUMANINFO_BY_ID,new String[] { "id"},new Object[]{id});
		if (humanInfo == null || humanInfo.size() == 0)
		{
			return null;
		}
		
		if(humanInfo.get(0)!=null&&humanInfo.get(0).length>0)
		{
			String human []=new String[2];
			human[0]= humanInfo.get(0)[0]+"";
			human[1]= humanInfo.get(0)[1]+"";
			return human;
			
		}
		return null;
	}
	
	/**
	 * 根据时间段获取数据
	 * @param minDay
	 * @param maxDay
	 * @return
	 */
	public List<Object[]> queryUserNameByTimeSpan(int minDay,int maxDay)
	{
		List<Object[]> humanInfo = this._dbServ.findByNamedQueryAndNamedParam(QUERY_HUMANINFO_BY_TIMESPAN,new String[]{"minDay", "maxDay"}, new Object[] {minDay, maxDay});
		return humanInfo;
	}
	
	/**
	 *根据离线组队机器人查询玩家
	 */
	public List<Object[]> queryHumanInfoByOfflineTeamRobot()
	{
		List<Object[]> humanInfo = this._dbServ.findByNamedQuery(QUERY_HUMANINFO_BY_OFFLINETEAMROBOT);
		return humanInfo;
	}
	/**
	 * 根据等级查询玩家信息
	 * @param level
	 * @return
	 */
	public List<Object[]> queryUserNameByLevel(int level)
	{
		List<Object[]> humanInfo = this._dbServ.findByNamedQueryAndNamedParam(QUERY_HUMANINFO_BY_LEVEL,new String[]{"level"}, new Object[] {level});
		return humanInfo;
	}
	
	
	public List<HumanEntity> selectPlayerByLevel(long level0, long level1)
	{
		List<HumanEntity> l = this._dbServ.findByNamedQueryAndNamedParam("selectPlayerByLevel",new String[]{"level0", "level1"}, new Object[] {level0, level1});
		return l;
	}
	public List<HumanEntity> selectPlayerByOffLineTime(long ts)
	{
		List<HumanEntity> l = this._dbServ.findByNamedQueryAndNamedParam("selectPlayerByOffLineTime",new String[]{"ts"}, new Object[] {ts});
		return l;
	}
	
	
	/**
	 * 无双吹牛 的排行榜
	 */
	
	public List<HumanEntity> getPlayerRankByGoldDesc()
	{
		int perPage = 100;
		int start = 0;
		List<HumanEntity> HumanEntityList=this._dbServ.findByNamedQueryAndNamedParam(SELECT_PLAYER_ORDER_BY_GOLD,new String[]{},new Object[]{},perPage,start);
		return HumanEntityList;
	}
	
}
