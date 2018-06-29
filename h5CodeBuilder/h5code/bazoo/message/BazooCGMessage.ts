module game
{
	export class BazooCGMessage
	{
		public static CG_Bazoo_Boqu(data:CGBazooBoqu)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_BOQU, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Change_Name(data:CGBazooChangeName)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_CHANGE_NAME, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Facebook_Add_Gold(data:CGBazooFacebookAddGold)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_FACEBOOK_ADD_GOLD, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Hall_Status(data:CGBazooHallStatus)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_HALL_STATUS, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Heart_Beat(data:CGBazooHeartBeat)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_HEART_BEAT, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Magic_Face(data:CGBazooMagicFace)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_MAGIC_FACE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Bazoo_Newguy_Process(data:CGBazooNewguyProcess)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BAZOO_NEWGUY_PROCESS, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Black_White_Call_Num(data:CGBlackWhiteCallNum)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_BLACK_WHITE_CALL_NUM, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Cow_Single_Swing(data:CGCowSingleSwing)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_COW_SINGLE_SWING, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Dice_Single_Swing(data:CGDiceSingleSwing)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_DICE_SINGLE_SWING, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Guess_Big_Small(data:CGGuessBigSmall)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_GUESS_BIG_SMALL, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Mode_Chose(data:CGModeChose)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_MODE_CHOSE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Rob_Open(data:CGRobOpen)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROB_OPEN, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Create(data:CGRoomCreate)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_CREATE, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Enter(data:CGRoomEnter)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_ENTER, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Out(data:CGRoomOut)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_OUT, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Pri_Join(data:CGRoomPriJoin)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_PRI_JOIN, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Pri_List(data:CGRoomPriList)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_PRI_LIST, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Pri_Search(data:CGRoomPriSearch)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_PRI_SEARCH, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Room_Pub_Join(data:CGRoomPubJoin)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_ROOM_PUB_JOIN, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Show_Hand_Single_Swich(data:CGShowHandSingleSwich)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_SHOW_HAND_SINGLE_SWICH, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Show_Hand_Single_Swich_Cancel(data:CGShowHandSingleSwichCancel)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_SHOW_HAND_SINGLE_SWICH_CANCEL, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Show_Hand_Single_Swing(data:CGShowHandSingleSwing)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_SHOW_HAND_SINGLE_SWING, data);
			NetMessageHandler.sendMessage(msgOut);
		}

		public static CG_Talk_Big(data:CGTalkBig)
		{
			let msgOut : NetMessageOut = new NetMessageOut(NetMessageType.CG_TALK_BIG, data);
			NetMessageHandler.sendMessage(msgOut);
		}

	}
}
