module game
{
	export class HumanGCMessage extends AbstractMessageReceiver
	{
		private static instance : HumanGCMessage;
		public static getInstance() : HumanGCMessage
		{
			if(HumanGCMessage.instance == null)
				HumanGCMessage.instance = new HumanGCMessage();
			return HumanGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_CHANEAGE_COUNTRIES, this.GC_CHANEAGE_COUNTRIES);
			this.register(NetMessageType.GC_FRIEND_GAME, this.GC_FRIEND_GAME);
			this.register(NetMessageType.GC_HUMAN_CHANGE_IMG, this.GC_HUMAN_CHANGE_IMG);
			this.register(NetMessageType.GC_HUMAN_CHANGE_NAME, this.GC_HUMAN_CHANGE_NAME);
			this.register(NetMessageType.GC_HUMAN_CHANGE_SEX, this.GC_HUMAN_CHANGE_SEX);
			this.register(NetMessageType.GC_HUMAN_CHANGE_VIP, this.GC_HUMAN_CHANGE_VIP);
			this.register(NetMessageType.GC_HUMAN_DETAIL_INFO, this.GC_HUMAN_DETAIL_INFO);
			this.register(NetMessageType.GC_ROLE_SYMBOL_CHANGED_LONG, this.GC_ROLE_SYMBOL_CHANGED_LONG);
		}

		public GC_CHANEAGE_COUNTRIES(data:NetMessageIn) : void
		{
			let msg:GCChaneageCountries = data.getContent<GCChaneageCountries>();
			HumanHandler.getInstance().GC_CHANEAGE_COUNTRIES(msg);
		}

		public GC_FRIEND_GAME(data:NetMessageIn) : void
		{
			let msg:GCFriendGame = data.getContent<GCFriendGame>();
			HumanHandler.getInstance().GC_FRIEND_GAME(msg);
		}

		public GC_HUMAN_CHANGE_IMG(data:NetMessageIn) : void
		{
			let msg:GCHumanChangeImg = data.getContent<GCHumanChangeImg>();
			HumanHandler.getInstance().GC_HUMAN_CHANGE_IMG(msg);
		}

		public GC_HUMAN_CHANGE_NAME(data:NetMessageIn) : void
		{
			let msg:GCHumanChangeName = data.getContent<GCHumanChangeName>();
			HumanHandler.getInstance().GC_HUMAN_CHANGE_NAME(msg);
		}

		public GC_HUMAN_CHANGE_SEX(data:NetMessageIn) : void
		{
			let msg:GCHumanChangeSex = data.getContent<GCHumanChangeSex>();
			HumanHandler.getInstance().GC_HUMAN_CHANGE_SEX(msg);
		}

		public GC_HUMAN_CHANGE_VIP(data:NetMessageIn) : void
		{
			let msg:GCHumanChangeVip = data.getContent<GCHumanChangeVip>();
			HumanHandler.getInstance().GC_HUMAN_CHANGE_VIP(msg);
		}

		public GC_HUMAN_DETAIL_INFO(data:NetMessageIn) : void
		{
			let msg:GCHumanDetailInfo = data.getContent<GCHumanDetailInfo>();
			HumanHandler.getInstance().GC_HUMAN_DETAIL_INFO(msg);
		}

		public GC_ROLE_SYMBOL_CHANGED_LONG(data:NetMessageIn) : void
		{
			let msg:GCRoleSymbolChangedLong = data.getContent<GCRoleSymbolChangedLong>();
			HumanHandler.getInstance().GC_ROLE_SYMBOL_CHANGED_LONG(msg);
		}
	}
}
