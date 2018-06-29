module game
{
	export class BazooGCMessage extends AbstractMessageReceiver
	{
		private static instance : BazooGCMessage;
		public static getInstance() : BazooGCMessage
		{
			if(BazooGCMessage.instance == null)
				BazooGCMessage.instance = new BazooGCMessage();
			return BazooGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_BAZOO_BOQU, this.GC_BAZOO_BOQU);
			this.register(NetMessageType.GC_BAZOO_CHANGE_NAME, this.GC_BAZOO_CHANGE_NAME);
			this.register(NetMessageType.GC_BAZOO_HALL_STATUS, this.GC_BAZOO_HALL_STATUS);
			this.register(NetMessageType.GC_BAZOO_HEART_BEAT, this.GC_BAZOO_HEART_BEAT);
			this.register(NetMessageType.GC_BAZOO_MAGIC_FACE, this.GC_BAZOO_MAGIC_FACE);
			this.register(NetMessageType.GC_BLACK_WHITE_ALL_SWING, this.GC_BLACK_WHITE_ALL_SWING);
			this.register(NetMessageType.GC_BLACK_WHITE_CALL_NUM, this.GC_BLACK_WHITE_CALL_NUM);
			this.register(NetMessageType.GC_BLACK_WHITE_END_COUNT, this.GC_BLACK_WHITE_END_COUNT);
			this.register(NetMessageType.GC_BLACK_WHITE_WHO_TURN, this.GC_BLACK_WHITE_WHO_TURN);
			this.register(NetMessageType.GC_COW_END_UNIFY_SWING, this.GC_COW_END_UNIFY_SWING);
			this.register(NetMessageType.GC_COW_SINGLE_SWING, this.GC_COW_SINGLE_SWING);
			this.register(NetMessageType.GC_COW_SINGLE_SWING_BEGIN, this.GC_COW_SINGLE_SWING_BEGIN);
			this.register(NetMessageType.GC_COW_SINGLE_SWING_END, this.GC_COW_SINGLE_SWING_END);
			this.register(NetMessageType.GC_COW_UNIFY_SWING, this.GC_COW_UNIFY_SWING);
			this.register(NetMessageType.GC_DICE_SINGLE_SWING, this.GC_DICE_SINGLE_SWING);
			this.register(NetMessageType.GC_DICE_UNIFY_SWING, this.GC_DICE_UNIFY_SWING);
			this.register(NetMessageType.GC_DICE_USER_SHOULD_SWING, this.GC_DICE_USER_SHOULD_SWING);
			this.register(NetMessageType.GC_END_COUNT, this.GC_END_COUNT);
			this.register(NetMessageType.GC_GUESS_BIG_SMALL, this.GC_GUESS_BIG_SMALL);
			this.register(NetMessageType.GC_GUESS_OPEN, this.GC_GUESS_OPEN);
			this.register(NetMessageType.GC_MODE_CHOSE, this.GC_MODE_CHOSE);
			this.register(NetMessageType.GC_ROB_OPEN, this.GC_ROB_OPEN);
			this.register(NetMessageType.GC_ROBOT_DICE_UNIFY_SWING, this.GC_ROBOT_DICE_UNIFY_SWING);
			this.register(NetMessageType.GC_ROBOT_WHICH_ROOM_TO_GOIN, this.GC_ROBOT_WHICH_ROOM_TO_GOIN);
			this.register(NetMessageType.GC_ROOM_BE_REMOVEED, this.GC_ROOM_BE_REMOVEED);
			this.register(NetMessageType.GC_ROOM_CREATE, this.GC_ROOM_CREATE);
			this.register(NetMessageType.GC_ROOM_ENTER, this.GC_ROOM_ENTER);
			this.register(NetMessageType.GC_ROOM_ENTER_NOT_ALLOW, this.GC_ROOM_ENTER_NOT_ALLOW);
			this.register(NetMessageType.GC_ROOM_INIT, this.GC_ROOM_INIT);
			this.register(NetMessageType.GC_ROOM_MATCHED, this.GC_ROOM_MATCHED);
			this.register(NetMessageType.GC_ROOM_MATCHEDING, this.GC_ROOM_MATCHEDING);
			this.register(NetMessageType.GC_ROOM_OUT, this.GC_ROOM_OUT);
			this.register(NetMessageType.GC_ROOM_PRI_JOIN, this.GC_ROOM_PRI_JOIN);
			this.register(NetMessageType.GC_ROOM_PRI_LIST, this.GC_ROOM_PRI_LIST);
			this.register(NetMessageType.GC_ROOM_PRI_SEARCH, this.GC_ROOM_PRI_SEARCH);
			this.register(NetMessageType.GC_ROOM_STATE, this.GC_ROOM_STATE);
			this.register(NetMessageType.GC_SHOW_HAND_END_COUNT, this.GC_SHOW_HAND_END_COUNT);
			this.register(NetMessageType.GC_SHOW_HAND_LITTLE_SWING, this.GC_SHOW_HAND_LITTLE_SWING);
			this.register(NetMessageType.GC_SHOW_HAND_SINGLE_SWICH, this.GC_SHOW_HAND_SINGLE_SWICH);
			this.register(NetMessageType.GC_SHOW_HAND_SINGLE_SWICH_CANCEL, this.GC_SHOW_HAND_SINGLE_SWICH_CANCEL);
			this.register(NetMessageType.GC_SHOW_HAND_SINGLE_SWING, this.GC_SHOW_HAND_SINGLE_SWING);
			this.register(NetMessageType.GC_SHOW_HAND_UNIFY_SWING, this.GC_SHOW_HAND_UNIFY_SWING);
			this.register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_END, this.GC_STATE_ROOM_BLACK_WHITE_END);
			this.register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL, this.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL);
			this.register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT, this.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT);
			this.register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE, this.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE);
			this.register(NetMessageType.GC_STATE_ROOM_CALL_DICE, this.GC_STATE_ROOM_CALL_DICE);
			this.register(NetMessageType.GC_STATE_ROOM_ENTER, this.GC_STATE_ROOM_ENTER);
			this.register(NetMessageType.GC_STATE_ROOM_MATCHING, this.GC_STATE_ROOM_MATCHING);
			this.register(NetMessageType.GC_STATE_ROOM_READY, this.GC_STATE_ROOM_READY);
			this.register(NetMessageType.GC_STATE_ROOM_ROUND_BEGIN, this.GC_STATE_ROOM_ROUND_BEGIN);
			this.register(NetMessageType.GC_STATE_ROOM_ROUND_GUESS, this.GC_STATE_ROOM_ROUND_GUESS);
			this.register(NetMessageType.GC_STATE_ROOM_ROUND_OPEN, this.GC_STATE_ROOM_ROUND_OPEN);
			this.register(NetMessageType.GC_STATE_ROOM_ROUND_RESULT, this.GC_STATE_ROOM_ROUND_RESULT);
			this.register(NetMessageType.GC_STATE_ROOM_ROUND_TURN, this.GC_STATE_ROOM_ROUND_TURN);
			this.register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_ALL_SWING, this.GC_STATE_ROOM_SHOW_HAND_ALL_SWING);
			this.register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT, this.GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT);
			this.register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING, this.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING);
			this.register(NetMessageType.GC_STATE_ROOM_SINGLE_SWING_BEGIN, this.GC_STATE_ROOM_SINGLE_SWING_BEGIN);
			this.register(NetMessageType.GC_STATE_ROOM_SINGLE_SWING_END, this.GC_STATE_ROOM_SINGLE_SWING_END);
			this.register(NetMessageType.GC_TALK_BIG, this.GC_TALK_BIG);
		}

		public GC_BAZOO_BOQU(data:NetMessageIn) : void
		{
			let msg:GCBazooBoqu = data.getContent<GCBazooBoqu>();
			BazooHandler.getInstance().GC_BAZOO_BOQU(msg);
		}

		public GC_BAZOO_CHANGE_NAME(data:NetMessageIn) : void
		{
			let msg:GCBazooChangeName = data.getContent<GCBazooChangeName>();
			BazooHandler.getInstance().GC_BAZOO_CHANGE_NAME(msg);
		}

		public GC_BAZOO_HALL_STATUS(data:NetMessageIn) : void
		{
			let msg:GCBazooHallStatus = data.getContent<GCBazooHallStatus>();
			BazooHandler.getInstance().GC_BAZOO_HALL_STATUS(msg);
		}

		public GC_BAZOO_HEART_BEAT(data:NetMessageIn) : void
		{
			let msg:GCBazooHeartBeat = data.getContent<GCBazooHeartBeat>();
			BazooHandler.getInstance().GC_BAZOO_HEART_BEAT(msg);
		}

		public GC_BAZOO_MAGIC_FACE(data:NetMessageIn) : void
		{
			let msg:GCBazooMagicFace = data.getContent<GCBazooMagicFace>();
			BazooHandler.getInstance().GC_BAZOO_MAGIC_FACE(msg);
		}

		public GC_BLACK_WHITE_ALL_SWING(data:NetMessageIn) : void
		{
			let msg:GCBlackWhiteAllSwing = data.getContent<GCBlackWhiteAllSwing>();
			BazooHandler.getInstance().GC_BLACK_WHITE_ALL_SWING(msg);
		}

		public GC_BLACK_WHITE_CALL_NUM(data:NetMessageIn) : void
		{
			let msg:GCBlackWhiteCallNum = data.getContent<GCBlackWhiteCallNum>();
			BazooHandler.getInstance().GC_BLACK_WHITE_CALL_NUM(msg);
		}

		public GC_BLACK_WHITE_END_COUNT(data:NetMessageIn) : void
		{
			let msg:GCBlackWhiteEndCount = data.getContent<GCBlackWhiteEndCount>();
			BazooHandler.getInstance().GC_BLACK_WHITE_END_COUNT(msg);
		}

		public GC_BLACK_WHITE_WHO_TURN(data:NetMessageIn) : void
		{
			let msg:GCBlackWhiteWhoTurn = data.getContent<GCBlackWhiteWhoTurn>();
			BazooHandler.getInstance().GC_BLACK_WHITE_WHO_TURN(msg);
		}

		public GC_COW_END_UNIFY_SWING(data:NetMessageIn) : void
		{
			let msg:GCCowEndUnifySwing = data.getContent<GCCowEndUnifySwing>();
			BazooHandler.getInstance().GC_COW_END_UNIFY_SWING(msg);
		}

		public GC_COW_SINGLE_SWING(data:NetMessageIn) : void
		{
			let msg:GCCowSingleSwing = data.getContent<GCCowSingleSwing>();
			BazooHandler.getInstance().GC_COW_SINGLE_SWING(msg);
		}

		public GC_COW_SINGLE_SWING_BEGIN(data:NetMessageIn) : void
		{
			let msg:GCCowSingleSwingBegin = data.getContent<GCCowSingleSwingBegin>();
			BazooHandler.getInstance().GC_COW_SINGLE_SWING_BEGIN(msg);
		}

		public GC_COW_SINGLE_SWING_END(data:NetMessageIn) : void
		{
			let msg:GCCowSingleSwingEnd = data.getContent<GCCowSingleSwingEnd>();
			BazooHandler.getInstance().GC_COW_SINGLE_SWING_END(msg);
		}

		public GC_COW_UNIFY_SWING(data:NetMessageIn) : void
		{
			let msg:GCCowUnifySwing = data.getContent<GCCowUnifySwing>();
			BazooHandler.getInstance().GC_COW_UNIFY_SWING(msg);
		}

		public GC_DICE_SINGLE_SWING(data:NetMessageIn) : void
		{
			let msg:GCDiceSingleSwing = data.getContent<GCDiceSingleSwing>();
			BazooHandler.getInstance().GC_DICE_SINGLE_SWING(msg);
		}

		public GC_DICE_UNIFY_SWING(data:NetMessageIn) : void
		{
			let msg:GCDiceUnifySwing = data.getContent<GCDiceUnifySwing>();
			BazooHandler.getInstance().GC_DICE_UNIFY_SWING(msg);
		}

		public GC_DICE_USER_SHOULD_SWING(data:NetMessageIn) : void
		{
			let msg:GCDiceUserShouldSwing = data.getContent<GCDiceUserShouldSwing>();
			BazooHandler.getInstance().GC_DICE_USER_SHOULD_SWING(msg);
		}

		public GC_END_COUNT(data:NetMessageIn) : void
		{
			let msg:GCEndCount = data.getContent<GCEndCount>();
			BazooHandler.getInstance().GC_END_COUNT(msg);
		}

		public GC_GUESS_BIG_SMALL(data:NetMessageIn) : void
		{
			let msg:GCGuessBigSmall = data.getContent<GCGuessBigSmall>();
			BazooHandler.getInstance().GC_GUESS_BIG_SMALL(msg);
		}

		public GC_GUESS_OPEN(data:NetMessageIn) : void
		{
			let msg:GCGuessOpen = data.getContent<GCGuessOpen>();
			BazooHandler.getInstance().GC_GUESS_OPEN(msg);
		}

		public GC_MODE_CHOSE(data:NetMessageIn) : void
		{
			let msg:GCModeChose = data.getContent<GCModeChose>();
			BazooHandler.getInstance().GC_MODE_CHOSE(msg);
		}

		public GC_ROB_OPEN(data:NetMessageIn) : void
		{
			let msg:GCRobOpen = data.getContent<GCRobOpen>();
			BazooHandler.getInstance().GC_ROB_OPEN(msg);
		}

		public GC_ROBOT_DICE_UNIFY_SWING(data:NetMessageIn) : void
		{
			let msg:GCRobotDiceUnifySwing = data.getContent<GCRobotDiceUnifySwing>();
			BazooHandler.getInstance().GC_ROBOT_DICE_UNIFY_SWING(msg);
		}

		public GC_ROBOT_WHICH_ROOM_TO_GOIN(data:NetMessageIn) : void
		{
			let msg:GCRobotWhichRoomToGoin = data.getContent<GCRobotWhichRoomToGoin>();
			BazooHandler.getInstance().GC_ROBOT_WHICH_ROOM_TO_GOIN(msg);
		}

		public GC_ROOM_BE_REMOVEED(data:NetMessageIn) : void
		{
			let msg:GCRoomBeRemoveed = data.getContent<GCRoomBeRemoveed>();
			BazooHandler.getInstance().GC_ROOM_BE_REMOVEED(msg);
		}

		public GC_ROOM_CREATE(data:NetMessageIn) : void
		{
			let msg:GCRoomCreate = data.getContent<GCRoomCreate>();
			BazooHandler.getInstance().GC_ROOM_CREATE(msg);
		}

		public GC_ROOM_ENTER(data:NetMessageIn) : void
		{
			let msg:GCRoomEnter = data.getContent<GCRoomEnter>();
			BazooHandler.getInstance().GC_ROOM_ENTER(msg);
		}

		public GC_ROOM_ENTER_NOT_ALLOW(data:NetMessageIn) : void
		{
			let msg:GCRoomEnterNotAllow = data.getContent<GCRoomEnterNotAllow>();
			BazooHandler.getInstance().GC_ROOM_ENTER_NOT_ALLOW(msg);
		}

		public GC_ROOM_INIT(data:NetMessageIn) : void
		{
			let msg:GCRoomInit = data.getContent<GCRoomInit>();
			BazooHandler.getInstance().GC_ROOM_INIT(msg);
		}

		public GC_ROOM_MATCHED(data:NetMessageIn) : void
		{
			let msg:GCRoomMatched = data.getContent<GCRoomMatched>();
			BazooHandler.getInstance().GC_ROOM_MATCHED(msg);
		}

		public GC_ROOM_MATCHEDING(data:NetMessageIn) : void
		{
			let msg:GCRoomMatcheding = data.getContent<GCRoomMatcheding>();
			BazooHandler.getInstance().GC_ROOM_MATCHEDING(msg);
		}

		public GC_ROOM_OUT(data:NetMessageIn) : void
		{
			let msg:GCRoomOut = data.getContent<GCRoomOut>();
			BazooHandler.getInstance().GC_ROOM_OUT(msg);
		}

		public GC_ROOM_PRI_JOIN(data:NetMessageIn) : void
		{
			let msg:GCRoomPriJoin = data.getContent<GCRoomPriJoin>();
			BazooHandler.getInstance().GC_ROOM_PRI_JOIN(msg);
		}

		public GC_ROOM_PRI_LIST(data:NetMessageIn) : void
		{
			let msg:GCRoomPriList = data.getContent<GCRoomPriList>();
			BazooHandler.getInstance().GC_ROOM_PRI_LIST(msg);
		}

		public GC_ROOM_PRI_SEARCH(data:NetMessageIn) : void
		{
			let msg:GCRoomPriSearch = data.getContent<GCRoomPriSearch>();
			BazooHandler.getInstance().GC_ROOM_PRI_SEARCH(msg);
		}

		public GC_ROOM_STATE(data:NetMessageIn) : void
		{
			let msg:GCRoomState = data.getContent<GCRoomState>();
			BazooHandler.getInstance().GC_ROOM_STATE(msg);
		}

		public GC_SHOW_HAND_END_COUNT(data:NetMessageIn) : void
		{
			let msg:GCShowHandEndCount = data.getContent<GCShowHandEndCount>();
			BazooHandler.getInstance().GC_SHOW_HAND_END_COUNT(msg);
		}

		public GC_SHOW_HAND_LITTLE_SWING(data:NetMessageIn) : void
		{
			let msg:GCShowHandLittleSwing = data.getContent<GCShowHandLittleSwing>();
			BazooHandler.getInstance().GC_SHOW_HAND_LITTLE_SWING(msg);
		}

		public GC_SHOW_HAND_SINGLE_SWICH(data:NetMessageIn) : void
		{
			let msg:GCShowHandSingleSwich = data.getContent<GCShowHandSingleSwich>();
			BazooHandler.getInstance().GC_SHOW_HAND_SINGLE_SWICH(msg);
		}

		public GC_SHOW_HAND_SINGLE_SWICH_CANCEL(data:NetMessageIn) : void
		{
			let msg:GCShowHandSingleSwichCancel = data.getContent<GCShowHandSingleSwichCancel>();
			BazooHandler.getInstance().GC_SHOW_HAND_SINGLE_SWICH_CANCEL(msg);
		}

		public GC_SHOW_HAND_SINGLE_SWING(data:NetMessageIn) : void
		{
			let msg:GCShowHandSingleSwing = data.getContent<GCShowHandSingleSwing>();
			BazooHandler.getInstance().GC_SHOW_HAND_SINGLE_SWING(msg);
		}

		public GC_SHOW_HAND_UNIFY_SWING(data:NetMessageIn) : void
		{
			let msg:GCShowHandUnifySwing = data.getContent<GCShowHandUnifySwing>();
			BazooHandler.getInstance().GC_SHOW_HAND_UNIFY_SWING(msg);
		}

		public GC_STATE_ROOM_BLACK_WHITE_END(data:NetMessageIn) : void
		{
			let msg:GCStateRoomBlackWhiteEnd = data.getContent<GCStateRoomBlackWhiteEnd>();
			BazooHandler.getInstance().GC_STATE_ROOM_BLACK_WHITE_END(msg);
		}

		public GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL(data:NetMessageIn) : void
		{
			let msg:GCStateRoomBlackWhiteSomeOneCall = data.getContent<GCStateRoomBlackWhiteSomeOneCall>();
			BazooHandler.getInstance().GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL(msg);
		}

		public GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT(data:NetMessageIn) : void
		{
			let msg:GCStateRoomBlackWhiteSwingLeft = data.getContent<GCStateRoomBlackWhiteSwingLeft>();
			BazooHandler.getInstance().GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT(msg);
		}

		public GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE(data:NetMessageIn) : void
		{
			let msg:GCStateRoomBlackWhiteWaitSomeOne = data.getContent<GCStateRoomBlackWhiteWaitSomeOne>();
			BazooHandler.getInstance().GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE(msg);
		}

		public GC_STATE_ROOM_CALL_DICE(data:NetMessageIn) : void
		{
			let msg:GCStateRoomCallDice = data.getContent<GCStateRoomCallDice>();
			BazooHandler.getInstance().GC_STATE_ROOM_CALL_DICE(msg);
		}

		public GC_STATE_ROOM_ENTER(data:NetMessageIn) : void
		{
			let msg:GCStateRoomEnter = data.getContent<GCStateRoomEnter>();
			BazooHandler.getInstance().GC_STATE_ROOM_ENTER(msg);
		}

		public GC_STATE_ROOM_MATCHING(data:NetMessageIn) : void
		{
			let msg:GCStateRoomMatching = data.getContent<GCStateRoomMatching>();
			BazooHandler.getInstance().GC_STATE_ROOM_MATCHING(msg);
		}

		public GC_STATE_ROOM_READY(data:NetMessageIn) : void
		{
			let msg:GCStateRoomReady = data.getContent<GCStateRoomReady>();
			BazooHandler.getInstance().GC_STATE_ROOM_READY(msg);
		}

		public GC_STATE_ROOM_ROUND_BEGIN(data:NetMessageIn) : void
		{
			let msg:GCStateRoomRoundBegin = data.getContent<GCStateRoomRoundBegin>();
			BazooHandler.getInstance().GC_STATE_ROOM_ROUND_BEGIN(msg);
		}

		public GC_STATE_ROOM_ROUND_GUESS(data:NetMessageIn) : void
		{
			let msg:GCStateRoomRoundGuess = data.getContent<GCStateRoomRoundGuess>();
			BazooHandler.getInstance().GC_STATE_ROOM_ROUND_GUESS(msg);
		}

		public GC_STATE_ROOM_ROUND_OPEN(data:NetMessageIn) : void
		{
			let msg:GCStateRoomRoundOpen = data.getContent<GCStateRoomRoundOpen>();
			BazooHandler.getInstance().GC_STATE_ROOM_ROUND_OPEN(msg);
		}

		public GC_STATE_ROOM_ROUND_RESULT(data:NetMessageIn) : void
		{
			let msg:GCStateRoomRoundResult = data.getContent<GCStateRoomRoundResult>();
			BazooHandler.getInstance().GC_STATE_ROOM_ROUND_RESULT(msg);
		}

		public GC_STATE_ROOM_ROUND_TURN(data:NetMessageIn) : void
		{
			let msg:GCStateRoomRoundTurn = data.getContent<GCStateRoomRoundTurn>();
			BazooHandler.getInstance().GC_STATE_ROOM_ROUND_TURN(msg);
		}

		public GC_STATE_ROOM_SHOW_HAND_ALL_SWING(data:NetMessageIn) : void
		{
			let msg:GCStateRoomShowHandAllSwing = data.getContent<GCStateRoomShowHandAllSwing>();
			BazooHandler.getInstance().GC_STATE_ROOM_SHOW_HAND_ALL_SWING(msg);
		}

		public GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT(data:NetMessageIn) : void
		{
			let msg:GCStateRoomShowHandRoundResult = data.getContent<GCStateRoomShowHandRoundResult>();
			BazooHandler.getInstance().GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT(msg);
		}

		public GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING(data:NetMessageIn) : void
		{
			let msg:GCStateRoomShowHandSingleSwing = data.getContent<GCStateRoomShowHandSingleSwing>();
			BazooHandler.getInstance().GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING(msg);
		}

		public GC_STATE_ROOM_SINGLE_SWING_BEGIN(data:NetMessageIn) : void
		{
			let msg:GCStateRoomSingleSwingBegin = data.getContent<GCStateRoomSingleSwingBegin>();
			BazooHandler.getInstance().GC_STATE_ROOM_SINGLE_SWING_BEGIN(msg);
		}

		public GC_STATE_ROOM_SINGLE_SWING_END(data:NetMessageIn) : void
		{
			let msg:GCStateRoomSingleSwingEnd = data.getContent<GCStateRoomSingleSwingEnd>();
			BazooHandler.getInstance().GC_STATE_ROOM_SINGLE_SWING_END(msg);
		}

		public GC_TALK_BIG(data:NetMessageIn) : void
		{
			let msg:GCTalkBig = data.getContent<GCTalkBig>();
			BazooHandler.getInstance().GC_TALK_BIG(msg);
		}
	}
}
