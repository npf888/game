module game
{
	export class BazoosigninGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazoosigninGCMessage;
		public static getInstance() : BazoosigninGCMessage
		{
			if(BazoosigninGCMessage.instance == null)
				BazoosigninGCMessage.instance = new BazoosigninGCMessage();
			return BazoosigninGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_SIGNIN, this.GC_BAZOO_SIGNIN);
		}

		public GC_BAZOO_SIGNIN(data:NetMessageIn) : void
		{
			let msg:GCBazooSignin = data.getContent<GCBazooSignin>();
			BazoosigninHandler.getInstance().GC_BAZOO_SIGNIN(msg);
		}
	}
}
