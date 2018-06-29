module game
{
	export class PlayerGCMessage extends AbstractMessageReceiver
	{
		private static instance : PlayerGCMessage;
		public static getInstance() : PlayerGCMessage
		{
			if(PlayerGCMessage.instance == null)
				PlayerGCMessage.instance = new PlayerGCMessage();
			return PlayerGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_CHECK_PLAYER_LOGIN, this.GC_CHECK_PLAYER_LOGIN);
			this.register(NetMessageType.GC_CLIENT_VERSION, this.GC_CLIENT_VERSION);
			this.register(NetMessageType.GC_ENTER_SCENE, this.GC_ENTER_SCENE);
			this.register(NetMessageType.GC_NOTIFY_EXCEPTION, this.GC_NOTIFY_EXCEPTION);
			this.register(NetMessageType.GC_QUERY_PLAYER_INFO, this.GC_QUERY_PLAYER_INFO);
			this.register(NetMessageType.GC_QUERY_PLAYER_INFO_NAME, this.GC_QUERY_PLAYER_INFO_NAME);
		}

		public GC_CHECK_PLAYER_LOGIN(data:NetMessageIn) : void
		{
			let msg:GCCheckPlayerLogin = data.getContent<GCCheckPlayerLogin>();
			PlayerHandler.getInstance().GC_CHECK_PLAYER_LOGIN(msg);
		}

		public GC_CLIENT_VERSION(data:NetMessageIn) : void
		{
			let msg:GCClientVersion = data.getContent<GCClientVersion>();
			PlayerHandler.getInstance().GC_CLIENT_VERSION(msg);
		}

		public GC_ENTER_SCENE(data:NetMessageIn) : void
		{
			let msg:GCEnterScene = data.getContent<GCEnterScene>();
			PlayerHandler.getInstance().GC_ENTER_SCENE(msg);
		}

		public GC_NOTIFY_EXCEPTION(data:NetMessageIn) : void
		{
			let msg:GCNotifyException = data.getContent<GCNotifyException>();
			PlayerHandler.getInstance().GC_NOTIFY_EXCEPTION(msg);
		}

		public GC_QUERY_PLAYER_INFO(data:NetMessageIn) : void
		{
			let msg:GCQueryPlayerInfo = data.getContent<GCQueryPlayerInfo>();
			PlayerHandler.getInstance().GC_QUERY_PLAYER_INFO(msg);
		}

		public GC_QUERY_PLAYER_INFO_NAME(data:NetMessageIn) : void
		{
			let msg:GCQueryPlayerInfoName = data.getContent<GCQueryPlayerInfoName>();
			PlayerHandler.getInstance().GC_QUERY_PLAYER_INFO_NAME(msg);
		}
	}
}
