module game
{
	export class BazoonewguyCGMessage
	{
		public static CG_Bazoo_New_Guy_Process(data:CGBazooNewGuyProcess)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_NEW_GUY_PROCESS, data);
			NetMessageHandler.sendMessage(msgOut);
		}
	}
}
