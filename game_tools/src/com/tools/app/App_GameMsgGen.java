package com.tools.app;

import com.gameserver.common.msg.MessageType;
import com.tools.msg.MessageGenerator;
import com.tools.msg.MessageGenerator.Settings;

/**
 * 消息生成器, 根据 /config/msg/model/ 目录中的 XML 文件生成消息类.
 * 消息类以 CG 或者 GC 开头,
 * <ul>
 * <li>CG = Client to GameServer</li>
 * <li>GC = GameServer to Client</li>
 * </ul>
 * CG 和 GC 消息主要是用于客户端与服务器端通信所用
 * @author Thinker
 */
public class App_GameMsgGen
{

	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		// 创建配置对象
		Settings settings = new Settings();
		settings.setClientRootPath("../game_tools/target/");
		settings.setGameServerRootPath("../game_server/src/com/gameserver/");
		settings.setMsgTypeClazz(MessageType.class);
		// 生成消息
		MessageGenerator.putSettings(settings);
		MessageGenerator.main(args);
	}
}
