module game
{
	export class BazoogiftGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazoogiftGCMessage;
		public static getInstance() : BazoogiftGCMessage
		{
			if(BazoogiftGCMessage.instance == null)
				BazoogiftGCMessage.instance = new BazoogiftGCMessage();
			return BazoogiftGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_RED_PACKAGE, this.GC_BAZOO_RED_PACKAGE);
			this.register(NetMessageType.GC_BAZOO_SEND_GIFT, this.GC_BAZOO_SEND_GIFT);
		}

		public GC_BAZOO_RED_PACKAGE(data:NetMessageIn) : void
		{
			let msg:GCBazooRedPackage = data.getContent<GCBazooRedPackage>();
			BazoogiftHandler.getInstance().GC_BAZOO_RED_PACKAGE(msg);
		}

		public GC_BAZOO_SEND_GIFT(data:NetMessageIn) : void
		{
			let msg:GCBazooSendGift = data.getContent<GCBazooSendGift>();
			BazoogiftHandler.getInstance().GC_BAZOO_SEND_GIFT(msg);
		}
	}
}
