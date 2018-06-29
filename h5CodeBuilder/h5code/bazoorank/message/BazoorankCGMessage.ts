module game
{
	export class BazoorankCGMessage
	{
		public static CG_Bazoo_Rank_Request(data:CGBazooRankRequest)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_RANK_REQUEST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Rank_Total_Gold_Request(data:CGBazooRankTotalGoldRequest)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_RANK_TOTAL_GOLD_REQUEST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
