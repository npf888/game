module game
{
	export class BazoorankGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazoorankGCMessage;
		public static getInstance() : BazoorankGCMessage
		{
			if(BazoorankGCMessage.instance == null)
				BazoorankGCMessage.instance = new BazoorankGCMessage();
			return BazoorankGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_RANK_REQUEST, this.GC_BAZOO_RANK_REQUEST);
		}

		public GC_BAZOO_RANK_REQUEST(data:NetMessageIn) : void
		{
			let msg:GCBazooRankRequest = data.getContent<GCBazooRankRequest>();
			BazoorankHandler.getInstance().GC_BAZOO_RANK_REQUEST(msg);
		}
	}
}
