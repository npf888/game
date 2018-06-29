module game
{
	export class HumanCGMessage
	{
		public static CG_Chaneage_Countries(data:CGChaneageCountries)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_CHANEAGE_COUNTRIES, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Human_Change_Img(data:CGHumanChangeImg)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_HUMAN_CHANGE_IMG, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Human_Change_Name(data:CGHumanChangeName)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_HUMAN_CHANGE_NAME, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Human_Change_Sex(data:CGHumanChangeSex)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_HUMAN_CHANGE_SEX, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
