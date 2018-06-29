package com.logserver;

import com.core.msg.BaseMessage;

/**
 * 实现BaseLogMessage
 * @author Thinker
 */
public abstract class BaseLogMessage extends BaseMessage
{
	private String tableName;// 日志表名
	
	private int id;// 对应数据库主键	
	
	private int logType; // 日志类型 SharedConstants.LOG_TYPE_XXX
	
	private long logTime; // 日志时间，Java中的统一时间
	
	private long accountId; // 账号信息
	
	private int regionId; // 服务器区ID
	
	private int serverId; // 服务器ID
	
	private long level; // 用户当前级别	
	
	private int reason; // 日子的原因
	
	private String accountName;
	
	
	private String param; // 附加参数
	
	private long createTime;// 创建时间
	
	public BaseLogMessage() {

	}

	public BaseLogMessage(int type, long time, int rid, int sid, long aid,
			String accountName, long level,
			int reason, String param) 
	{
		this.logType = type;
		this.logTime = time;
		this.regionId = rid;
		this.serverId = sid;
		this.accountId = aid;
		this.accountName = accountName;

		this.level = level;		
	
		this.reason = reason;
		this.param = param;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName
	 *            the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	/**
	 * @return the reason
	 */
	public int getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(int reason) {
		this.reason = reason;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * 读取子类的日志内容
	 * 
	 * @return
	 */
	abstract protected boolean readLogContent();

	/**
	 * 写入子类的日志内容
	 * 
	 * @return
	 */
	abstract protected boolean writeLogContent();

	@Override
	protected boolean readImpl() {
		logType = readInt();
		logTime = readLong();
		regionId = readInt();
		serverId = readInt();
		accountId = readLong();
	
		level = readInt();

		accountName = readString();

		this.reason = readInt();
		this.param = readString();
		return readLogContent();
	}

	@Override
	protected boolean writeImpl() {
		writeInt(logType);
		writeLong(logTime);
		writeInt(regionId);
		writeInt(serverId);
		writeLong(accountId);

		writeInt(level);

		writeString(accountName);

		writeInt(this.reason);
		writeString(this.param);
		return writeLogContent();
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public long getLogTime() {
		return logTime;
	}

	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}
	


	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}
}
