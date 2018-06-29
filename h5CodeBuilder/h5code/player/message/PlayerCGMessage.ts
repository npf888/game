module game
{
	export class PlayerCGMessage
	{
		public static CG_Check_Player_Login(data:CGCheckPlayerLogin)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_CHECK_PLAYER_LOGIN, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Client_Version(data:CGClientVersion)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_CLIENT_VERSION, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Enter_Scene(data:CGEnterScene)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ENTER_SCENE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Player_Enter(data:CGPlayerEnter)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_PLAYER_ENTER, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Query_Player_Info(data:CGQueryPlayerInfo)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_QUERY_PLAYER_INFO, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Query_Player_Info_Name(data:CGQueryPlayerInfoName)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_QUERY_PLAYER_INFO_NAME, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
