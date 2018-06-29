module game
{
	export class CommonGCMessage extends AbstractMessageReceiver
	{
		private static instance : CommonGCMessage;
		public static getInstance() : CommonGCMessage
		{
			if(CommonGCMessage.instance == null)
				CommonGCMessage.instance = new CommonGCMessage();
			return CommonGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_HANDSHAKE, this.GC_HANDSHAKE);
			this.register(NetMessageType.GC_PING, this.GC_PING);
			this.register(NetMessageType.GC_SYSTEM_MESSAGE, this.GC_SYSTEM_MESSAGE);
			this.register(NetMessageType.GC_SYSTEM_NOTICE, this.GC_SYSTEM_NOTICE);
		}

		public GC_HANDSHAKE(data:NetMessageIn) : void
		{
			let msg:GCHandshake = data.getContent<GCHandshake>();
			CommonHandler.getInstance().GC_HANDSHAKE(msg);
		}

		public GC_PING(data:NetMessageIn) : void
		{
			let msg:GCPing = data.getContent<GCPing>();
			CommonHandler.getInstance().GC_PING(msg);
		}

		public GC_SYSTEM_MESSAGE(data:NetMessageIn) : void
		{
			let msg:GCSystemMessage = data.getContent<GCSystemMessage>();
			CommonHandler.getInstance().GC_SYSTEM_MESSAGE(msg);
		}

		public GC_SYSTEM_NOTICE(data:NetMessageIn) : void
		{
			let msg:GCSystemNotice = data.getContent<GCSystemNotice>();
			CommonHandler.getInstance().GC_SYSTEM_NOTICE(msg);
		}
	}
}
