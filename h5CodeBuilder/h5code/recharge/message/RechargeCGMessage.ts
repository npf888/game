module game
{
	export class RechargeCGMessage
	{
		public static CG_Request_Order(data:CGRequestOrder)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_REQUEST_ORDER, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Validate_Order(data:CGValidateOrder)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_VALIDATE_ORDER, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
