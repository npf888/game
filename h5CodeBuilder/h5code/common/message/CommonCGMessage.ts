module game
{
	export class CommonCGMessage
	{
		public static CG_Handshake(data:CGHandshake)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_HANDSHAKE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Ping(data:CGPing)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_PING, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
