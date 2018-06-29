module game
{
	export class MailCGMessage
	{
		public static CG_Load_Mail_List(data:CGLoadMailList)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_LOAD_MAIL_LIST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
