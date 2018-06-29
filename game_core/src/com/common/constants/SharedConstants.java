package com.common.constants;


/**
 * 全局共享的常量
 * @author Thinker
 * 
 */
public final class SharedConstants
{
	private SharedConstants()
	{
		
	}

	/** 系统默认的编码,UTF-8 {@index} */
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final int TYPE_NULL = 0;
	
	/** 所有Excel中用于记录配置信息的id值 */
	public static final int CONFIG_TEMPLATE_DEFAULT_ID = 1;

	// GameServer状态相关定义
	/** GameServer状态：拥挤 */
	public static final int GS_STATUS_FULL = 0;
	/** GameServer状态：正常，人比较少 */
	public static final int GS_STATUS_NORMAL = 1;
	/** GameServer状态：推荐 */
	public static final int GS_STATUS_RECOMMEND = 2;
	/** GameServer状态：维护或者下线 */
	public static final int GS_STATUS_MAINTAIN = 3;
	/** GameServer状态的阈值 : 超过 1000 人就算拥挤 */
	public static final int GS_STATUS_FULL_LIMIT = 1000;
	/** GameServer向WorldServer的汇报间隔 秒,Game Server配置的汇报时间间隔不能低于该值 */
	public static final int MAX_GAMESERVER_REPORT_PERIOR = 1800;	
	/** GameServer的心跳间隔,单位为毫秒 */
	public static final int GS_HEART_BEAT_INTERVAL = 200;
	
	/** GameServer的心跳间隔,单位为毫秒 */
	public static final int TASK_HEART_BEAT_INTERVAL = 500;
	/* 聊天范围 */
	/** 私聊，一对一 */
	public static final int CHAT_SCOPE_PRIVATE = 0x00000001;
	/** 附近，同区域内的12个玩家 */
	public static final int CHAT_SCOPE_NEAR = 0x00000002;
	/** 地区，同一城市下的玩家 */
	public static final int CHAT_SCOPE_REGION = 0x00000004;
	/** 帮派，同一军团下的玩家 */
	public static final int CHAT_SCOPE_GUILD = 0x00000008;
	/** 世界 */
	public static final int CHAT_SCOPE_WORLD = 0x00000010;
	/** 国家，相同国家内的玩家 */
	public static final int CHAT_SCOPE_ALLIANCE = 0x00000020;
	/** 喇叭 */
	public static final int CHAT_SCOPE_TRUMPET = 0x00000040;
	/** 系统消息 */
	public static final int CHAT_SCOPE_SYSTEM = 0x00000080;
	/** 默认接收所有频道 */
	public static final int CHAT_SCOPE_DEFAULT = 0x000000FF;


	/* 玩家常量 */
	/** 有公会 */
	public static final int PLAYER_PARTY_HAVE = 1;
	/** 无公会 */
	public static final int PLAYER_PARTY_NONE = 2;
	/** 玩家角色名的最大长度 */
	public static final int PLAYER_ROLE_MAX_LEN = 16;
	/** 每个玩家最多可创建的角色数 */
	public static final int MAX_ROLE_PER_PLAYER = 1;


	/** 角色未进入游戏时默认的角色ID */
	public static final int DEFAULT_CHAR_ID_BEFORE_ENTER_GAME = -1;

	/* 权限相关 */
	/** 玩家 ： 默认权限 */
	public static final int ACCOUNT_ROLE_USER = 0;
	/** GM ：管理员权限 */
	public static final int ACCOUNT_ROLE_GM = 1;
	/** DEBUG : DEBUG权限 */
	public static final int ACCOUNT_ROLE_DEBUG = 2;

	

	/* 角色相关 */

	/** 角色姓名最小允许中文字符数 */
	public static final int MIN_NAME_LENGTH_ZHCN = 2;
	/** 角色姓名最大允许中文字符数 */
	public static final int MAX_NAME_LENGTH_ZHCN = 7;
	/** 角色姓名最小允许英文字符数 */
	public static final int MIN_NAME_LENGTH_ENG = 4;
	/** 角色姓名最大允许英文字符数 */
	public static final int MAX_NAME_LENGTH_ENG = 14;
	

	/* 对外Http接口相关 */
	/** 访问 local 平台所需的<b>默认的</b> MD5 KEY 值 */
	public static final String DEFAULT_LOCAL_MD5_KEY = "c762000b3eb6955de0862f435b28a8eb";
	
	/** 进行直充,请求的md5所需要的 KEY值 */
	public static final String HITHERE_MD5_KEY = "7545647f8bf84fb9be9a93209c5d0d91";
	
	/* 模版相关 */
	/** 取模版中的第一个元素（针对模版中只有一行的情况）*/
	public static final int FIRST_ID = 1;
	
	/** 所有不存在的名称 */
	public static final String NOT_EXIST_NAME = "null"; 
	
	public static final String OPERATION_COM_RENREN = "renren";

	public static final String OPERATION_COM_HITHERE = "hithere";

	/** 全服或国家喊话等级限制 */
	public static int SPEEK_LEVEL_LIMIT = 10;
	
	/** 用户最后行为数据量*/
	public static final int MAXACTIONNUM=50;
	
	/** 记录上行数据次数*/
	public static int uplinkCount=0;
	/** 记录上行数据大小*/
	public static int uplinkLen=0;
	/** 记录下行数据次数*/
	public static int downlinkCount=0;
	/** 记录下行数据大小*/
	public static int downlinkLen=0;
	/** 数据库响应时间*/
	public static long respondDBTime=0;
	
	
}
