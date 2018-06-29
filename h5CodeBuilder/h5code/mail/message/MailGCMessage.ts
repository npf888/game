module game
{
	export class MailGCMessage extends AbstractMessageReceiver
	{
		private static instance : MailGCMessage;
		public static getInstance() : MailGCMessage
		{
			if(MailGCMessage.instance == null)
				MailGCMessage.instance = new MailGCMessage();
			return MailGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_LOAD_MAIL_LIST, this.GC_LOAD_MAIL_LIST);
			this.register(NetMessageType.GC_UPDATE_MAIL_LIST, this.GC_UPDATE_MAIL_LIST);
		}

		public GC_LOAD_MAIL_LIST(data:NetMessageIn) : void
		{
			let msg:GCLoadMailList = data.getContent<GCLoadMailList>();
			MailHandler.getInstance().GC_LOAD_MAIL_LIST(msg);
		}

		public GC_UPDATE_MAIL_LIST(data:NetMessageIn) : void
		{
			let msg:GCUpdateMailList = data.getContent<GCUpdateMailList>();
			MailHandler.getInstance().GC_UPDATE_MAIL_LIST(msg);
		}
	}
}
