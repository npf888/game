module game
{
	export class RechargeGCMessage extends AbstractMessageReceiver
	{
		private static instance : RechargeGCMessage;
		public static getInstance() : RechargeGCMessage
		{
			if(RechargeGCMessage.instance == null)
				RechargeGCMessage.instance = new RechargeGCMessage();
			return RechargeGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_ORDER_INFO_DATA_LIST, this.GC_ORDER_INFO_DATA_LIST);
			this.register(NetMessageType.GC_REQUEST_ORDER, this.GC_REQUEST_ORDER);
			this.register(NetMessageType.GC_REQUEST_ORDER_THIRD_PARTY, this.GC_REQUEST_ORDER_THIRD_PARTY);
			this.register(NetMessageType.GC_VALIDATE_ORDER, this.GC_VALIDATE_ORDER);
		}

		public GC_ORDER_INFO_DATA_LIST(data:NetMessageIn) : void
		{
			let msg:GCOrderInfoDataList = data.getContent<GCOrderInfoDataList>();
			RechargeHandler.getInstance().GC_ORDER_INFO_DATA_LIST(msg);
		}

		public GC_REQUEST_ORDER(data:NetMessageIn) : void
		{
			let msg:GCRequestOrder = data.getContent<GCRequestOrder>();
			RechargeHandler.getInstance().GC_REQUEST_ORDER(msg);
		}

		public GC_REQUEST_ORDER_THIRD_PARTY(data:NetMessageIn) : void
		{
			let msg:GCRequestOrderThirdParty = data.getContent<GCRequestOrderThirdParty>();
			RechargeHandler.getInstance().GC_REQUEST_ORDER_THIRD_PARTY(msg);
		}

		public GC_VALIDATE_ORDER(data:NetMessageIn) : void
		{
			let msg:GCValidateOrder = data.getContent<GCValidateOrder>();
			RechargeHandler.getInstance().GC_VALIDATE_ORDER(msg);
		}
	}
}
