module game
{
	export class ItemGCMessage extends AbstractMessageReceiver
	{
		private static instance : ItemGCMessage;
		public static getInstance() : ItemGCMessage
		{
			if(ItemGCMessage.instance == null)
				ItemGCMessage.instance = new ItemGCMessage();
			return ItemGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_ITEM_BUY_BY_GOLD, this.GC_BAZOO_ITEM_BUY_BY_GOLD);
			this.register(NetMessageType.GC_BAZOO_ITEM_CLOCK_CHANGE, this.GC_BAZOO_ITEM_CLOCK_CHANGE);
			this.register(NetMessageType.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL, this.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL);
			this.register(NetMessageType.GC_BAZOO_ITEM_REQUEST, this.GC_BAZOO_ITEM_REQUEST);
			this.register(NetMessageType.GC_BAZOO_MALL_REQUEST, this.GC_BAZOO_MALL_REQUEST);
			this.register(NetMessageType.GC_GROUP_SEND_INTERACTIVE_ITEM, this.GC_GROUP_SEND_INTERACTIVE_ITEM);
			this.register(NetMessageType.GC_SEND_INTERACTIVE_ITEM, this.GC_SEND_INTERACTIVE_ITEM);
		}

		public GC_BAZOO_ITEM_BUY_BY_GOLD(data:NetMessageIn) : void
		{
			let msg:GCBazooItemBuyByGold = data.getContent<GCBazooItemBuyByGold>();
			ItemHandler.getInstance().GC_BAZOO_ITEM_BUY_BY_GOLD(msg);
		}

		public GC_BAZOO_ITEM_CLOCK_CHANGE(data:NetMessageIn) : void
		{
			let msg:GCBazooItemClockChange = data.getContent<GCBazooItemClockChange>();
			ItemHandler.getInstance().GC_BAZOO_ITEM_CLOCK_CHANGE(msg);
		}

		public GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL(data:NetMessageIn) : void
		{
			let msg:GCBazooItemClockChangeToAll = data.getContent<GCBazooItemClockChangeToAll>();
			ItemHandler.getInstance().GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL(msg);
		}

		public GC_BAZOO_ITEM_REQUEST(data:NetMessageIn) : void
		{
			let msg:GCBazooItemRequest = data.getContent<GCBazooItemRequest>();
			ItemHandler.getInstance().GC_BAZOO_ITEM_REQUEST(msg);
		}

		public GC_BAZOO_MALL_REQUEST(data:NetMessageIn) : void
		{
			let msg:GCBazooMallRequest = data.getContent<GCBazooMallRequest>();
			ItemHandler.getInstance().GC_BAZOO_MALL_REQUEST(msg);
		}

		public GC_GROUP_SEND_INTERACTIVE_ITEM(data:NetMessageIn) : void
		{
			let msg:GCGroupSendInteractiveItem = data.getContent<GCGroupSendInteractiveItem>();
			ItemHandler.getInstance().GC_GROUP_SEND_INTERACTIVE_ITEM(msg);
		}

		public GC_SEND_INTERACTIVE_ITEM(data:NetMessageIn) : void
		{
			let msg:GCSendInteractiveItem = data.getContent<GCSendInteractiveItem>();
			ItemHandler.getInstance().GC_SEND_INTERACTIVE_ITEM(msg);
		}
	}
}
