module game
{
	export class RelationGCMessage extends AbstractMessageReceiver
	{
		private static instance : RelationGCMessage;
		public static getInstance() : RelationGCMessage
		{
			if(RelationGCMessage.instance == null)
				RelationGCMessage.instance = new RelationGCMessage();
			return RelationGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_ADD_FRIEND, this.GC_ADD_FRIEND);
			this.register(NetMessageType.GC_APPLY_FRIEND, this.GC_APPLY_FRIEND);
			this.register(NetMessageType.GC_DELETE_FRIEND, this.GC_DELETE_FRIEND);
			this.register(NetMessageType.GC_FACEBOOK_FRIENDS_SYNC, this.GC_FACEBOOK_FRIENDS_SYNC);
			this.register(NetMessageType.GC_LOAD_FRIEND_LIST, this.GC_LOAD_FRIEND_LIST);
			this.register(NetMessageType.GC_LOAD_FRIEND_REQUEST_LIST, this.GC_LOAD_FRIEND_REQUEST_LIST);
			this.register(NetMessageType.GC_REQUEST_FRIEND, this.GC_REQUEST_FRIEND);
			this.register(NetMessageType.GC_REQUEST_FRIEND_SYNC, this.GC_REQUEST_FRIEND_SYNC);
		}

		public GC_ADD_FRIEND(data:NetMessageIn) : void
		{
			let msg:GCAddFriend = data.getContent<GCAddFriend>();
			RelationHandler.getInstance().GC_ADD_FRIEND(msg);
		}

		public GC_APPLY_FRIEND(data:NetMessageIn) : void
		{
			let msg:GCApplyFriend = data.getContent<GCApplyFriend>();
			RelationHandler.getInstance().GC_APPLY_FRIEND(msg);
		}

		public GC_DELETE_FRIEND(data:NetMessageIn) : void
		{
			let msg:GCDeleteFriend = data.getContent<GCDeleteFriend>();
			RelationHandler.getInstance().GC_DELETE_FRIEND(msg);
		}

		public GC_FACEBOOK_FRIENDS_SYNC(data:NetMessageIn) : void
		{
			let msg:GCFacebookFriendsSync = data.getContent<GCFacebookFriendsSync>();
			RelationHandler.getInstance().GC_FACEBOOK_FRIENDS_SYNC(msg);
		}

		public GC_LOAD_FRIEND_LIST(data:NetMessageIn) : void
		{
			let msg:GCLoadFriendList = data.getContent<GCLoadFriendList>();
			RelationHandler.getInstance().GC_LOAD_FRIEND_LIST(msg);
		}

		public GC_LOAD_FRIEND_REQUEST_LIST(data:NetMessageIn) : void
		{
			let msg:GCLoadFriendRequestList = data.getContent<GCLoadFriendRequestList>();
			RelationHandler.getInstance().GC_LOAD_FRIEND_REQUEST_LIST(msg);
		}

		public GC_REQUEST_FRIEND(data:NetMessageIn) : void
		{
			let msg:GCRequestFriend = data.getContent<GCRequestFriend>();
			RelationHandler.getInstance().GC_REQUEST_FRIEND(msg);
		}

		public GC_REQUEST_FRIEND_SYNC(data:NetMessageIn) : void
		{
			let msg:GCRequestFriendSync = data.getContent<GCRequestFriendSync>();
			RelationHandler.getInstance().GC_REQUEST_FRIEND_SYNC(msg);
		}
	}
}
