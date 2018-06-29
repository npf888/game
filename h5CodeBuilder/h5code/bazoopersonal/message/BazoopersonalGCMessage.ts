module game
{
	export class BazoopersonalGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazoopersonalGCMessage;
		public static getInstance() : BazoopersonalGCMessage
		{
			if(BazoopersonalGCMessage.instance == null)
				BazoopersonalGCMessage.instance = new BazoopersonalGCMessage();
			return BazoopersonalGCMessage.instance;
		}
		public collectObservers() : void
		{
		}

	}
}
