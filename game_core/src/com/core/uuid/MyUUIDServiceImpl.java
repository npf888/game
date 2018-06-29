package com.core.uuid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyUUIDServiceImpl  implements MyUUIDService {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(UUIDServiceImpl.class);

	/** 需要处理的UUID类型 */
	private final UUIDType[] types;
	/**My UUID64*/
	private final MyUUID64[] myUuid64 = new MyUUID64[UUIDType.values().length];
	/** 服的ID */
	private final int sid;

	private long now;

	/**
	 * @param types
	 *            包含的的UUIDType
	 * @param dbService
	 *            数据库代理服务
	 * @param rid
	 *            大区的ID
	 * @param sid
	 *            服的ID
	 * @param lid
	 *            线的ID
	 */
	public MyUUIDServiceImpl(UUIDType[] types, int sid,long now)
	{
		this.sid = sid;
		this.types = types;
		this.now = now;
		init();
	}

	@Override
	public long getNextUUID(long now,UUIDType uuidType)
	{
		return this.myUuid64[uuidType.getIndex()].getNextUUID(now);
	}


	public void init()
	{
		
		for (UUIDType _type : types)
		{
			this.myUuid64[_type.getIndex()] = MyUUID64.buildDefaultUUID(sid);
			MyUUID64 _uuid64 = this.myUuid64[_type.getIndex()];
			long curId = _uuid64.getCurUUID(now);
			logger.info("Get UUID for [sid:" + this.sid + ",type:" + _type + "] cur uuid[Hex:"+ Long.toHexString(curId)+ "] cur uuid["+curId+"]");
		}
	}
	
}
