package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.DeviceEntity;


/**
 * 
 * 客户端设备记录DAO
 * 
 * @author Thinker
 *
 */
public class DeviceDao extends BaseDao<DeviceEntity>
{
	// 根据userid查询客户端设备记录
	public static final String QUERY_DEVICE_BY_USERID = "queryDeviceByUserid";
	
	
	public DeviceDao(DBService dbService)
	{
		super(dbService);
	}

	@Override
	protected Class<DeviceEntity> getEntityClazz()
	{
		return DeviceEntity.class;
	}
	
	/**
	 * 根据流水id查找对应的流水信息
	 * 
	 * @param transcationid
	 * @return
	 */
	
	public List<DeviceEntity> queryDeviceByUserid(long userid)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_DEVICE_BY_USERID, new String[] { "userid" },
				new Object[] { userid });
	}
}
