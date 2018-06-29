module game
{
	export class BazoonewguyGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazoonewguyGCMessage;
		public static getInstance() : BazoonewguyGCMessage
		{
			if(BazoonewguyGCMessage.instance == null)
				BazoonewguyGCMessage.instance = new BazoonewguyGCMessage();
			return BazoonewguyGCMessage.instance;
		}
		public collectObservers() : void
		{
		}

	}
}
