module game
{
	export class BazoosigninCGMessage
	{
		public static CG_Bazoo_Signin(data:CGBazooSignin)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_SIGNIN, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
