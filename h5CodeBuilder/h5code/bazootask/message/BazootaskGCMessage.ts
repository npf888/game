module game
{
	export class BazootaskGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazootaskGCMessage;
		public static getInstance() : BazootaskGCMessage
		{
			if(BazootaskGCMessage.instance == null)
				BazootaskGCMessage.instance = new BazootaskGCMessage();
			return BazootaskGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_ACHIEVE_TASK, this.GC_BAZOO_ACHIEVE_TASK);
			this.register(NetMessageType.GC_BAZOO_GET_REWARD, this.GC_BAZOO_GET_REWARD);
			this.register(NetMessageType.GC_BAZOO_TASK, this.GC_BAZOO_TASK);
		}

		public GC_BAZOO_ACHIEVE_TASK(data:NetMessageIn) : void
		{
			let msg:GCBazooAchieveTask = data.getContent<GCBazooAchieveTask>();
			BazootaskHandler.getInstance().GC_BAZOO_ACHIEVE_TASK(msg);
		}

		public GC_BAZOO_GET_REWARD(data:NetMessageIn) : void
		{
			let msg:GCBazooGetReward = data.getContent<GCBazooGetReward>();
			BazootaskHandler.getInstance().GC_BAZOO_GET_REWARD(msg);
		}

		public GC_BAZOO_TASK(data:NetMessageIn) : void
		{
			let msg:GCBazooTask = data.getContent<GCBazooTask>();
			BazootaskHandler.getInstance().GC_BAZOO_TASK(msg);
		}
	}
}
