module game
{
	export class BazoogiftCGMessage
	{
		public static CG_Bazoo_Red_Package(data:CGBazooRedPackage)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_RED_PACKAGE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Send_Gift(data:CGBazooSendGift)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_SEND_GIFT, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
