package com.tools.serverstatus.datagen;

import com.common.constants.ServerTypes;
import com.tools.serverstatus.AgentServerStatus;
import com.tools.serverstatus.DbsServerStatus;
import com.tools.serverstatus.GameServerStatus;
import com.tools.serverstatus.LoginServerStatus;
import com.tools.serverstatus.StatusInfo;
import com.tools.serverstatus.WorldServerStatus;

/**
 *状态信息创建器
 * 
 * @author Thinker
 * 
 */
public class StatusInfoCreator
{
	/**
	 * 根据服务器类型创建服务器状态
	 * 
	 * @param serverType
	 * @return
	 */
	public static StatusInfo createStatusInfo(String serverType)
	{
		int serverIntType = Integer.parseInt(serverType);
		switch (serverIntType) 
		{
			case ServerTypes.DBS:
				return new DbsServerStatus();
			case ServerTypes.GAME:
				return new GameServerStatus();
			case ServerTypes.LOGIN:
				return new LoginServerStatus();
			case ServerTypes.WORLD:
				return new WorldServerStatus();
			case ServerTypes.AGENT:
				return new AgentServerStatus();
			default:
				return null;
		}
	}
}
