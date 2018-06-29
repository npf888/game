module game
{
	export class RelationCGMessage
	{
		public static CG_Apply_Friend(data:CGApplyFriend)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_APPLY_FRIEND, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Delete_Friend(data:CGDeleteFriend)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_DELETE_FRIEND, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Facebook_Friends_Sync(data:CGFacebookFriendsSync)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_FACEBOOK_FRIENDS_SYNC, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Load_Friend_List(data:CGLoadFriendList)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_LOAD_FRIEND_LIST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Load_Friend_Request_List(data:CGLoadFriendRequestList)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_LOAD_FRIEND_REQUEST_LIST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Request_Friend(data:CGRequestFriend)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_REQUEST_FRIEND, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
