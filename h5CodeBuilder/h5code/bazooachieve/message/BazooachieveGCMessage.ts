module game
{
	export class BazooachieveGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazooachieveGCMessage;
		public static getInstance() : BazooachieveGCMessage
		{
			if(BazooachieveGCMessage.instance == null)
				BazooachieveGCMessage.instance = new BazooachieveGCMessage();
			return BazooachieveGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_ACHIEVE, this.GC_BAZOO_ACHIEVE);
		}

		public GC_BAZOO_ACHIEVE(data:NetMessageIn) : void
		{
			let msg:GCBazooAchieve = data.getContent<GCBazooAchieve>();
			BazooachieveHandler.getInstance().GC_BAZOO_ACHIEVE(msg);
		}
	}
}
