module game
{
	export class BazooachieveCGMessage
	{
		public static CG_Bazoo_Achieve(data:CGBazooAchieve)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ACHIEVE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Achieve_First(data:CGBazooAchieveFirst)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ACHIEVE_FIRST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
