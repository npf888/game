module game
{
	export class ItemCGMessage
	{
		public static CG_Bazoo_Item_Buy_By_Gold(data:CGBazooItemBuyByGold)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ITEM_BUY_BY_GOLD, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Item_Clock_Change(data:CGBazooItemClockChange)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ITEM_CLOCK_CHANGE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Item_Request(data:CGBazooItemRequest)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_ITEM_REQUEST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Mall_Request(data:CGBazooMallRequest)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_MALL_REQUEST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Group_Send_Interactive_Item(data:CGGroupSendInteractiveItem)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_GROUP_SEND_INTERACTIVE_ITEM, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Send_Interactive_Item(data:CGSendInteractiveItem)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_SEND_INTERACTIVE_ITEM, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
