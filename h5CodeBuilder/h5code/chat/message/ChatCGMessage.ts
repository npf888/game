module game
{
	export class ChatCGMessage
	{
		public static CG_Chat_Msg(data:CGChatMsg)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_CHAT_MSG, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
