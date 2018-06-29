module game
{
	export class BazootaskCGMessage
	{
		public static CG_Bazoo_Achieve_Task(data:CGBazooAchieveTask)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ACHIEVE_TASK, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Get_Reward(data:CGBazooGetReward)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_GET_REWARD, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Task(data:CGBazooTask)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_TASK, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
