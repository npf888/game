module game
{
	export class ChatGCMessage extends AbstractMessageReceiver
	{
		private static instance : ChatGCMessage;
		public static getInstance() : ChatGCMessage
		{
			if(ChatGCMessage.instance == null)
				ChatGCMessage.instance = new ChatGCMessage();
			return ChatGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_CHAT_MSG, this.GC_CHAT_MSG);
		}

		public GC_CHAT_MSG(data:NetMessageIn) : void
		{
			let msg:GCChatMsg = data.getContent<GCChatMsg>();
			ChatHandler.getInstance().GC_CHAT_MSG(msg);
		}
	}
}
