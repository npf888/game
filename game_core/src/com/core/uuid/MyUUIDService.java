package com.core.uuid;

/**
 * 基于时间的uuid
 * @author wayne
 *
 */
public interface MyUUIDService {

	/**
	 * 取得指定类型的下一个UUID
	 * 
	 * @param uuidType
	 * @return
	 */
	public abstract long getNextUUID(long now,UUIDType uuidType);

}
