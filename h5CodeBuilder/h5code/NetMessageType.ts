module game
{
	export enum NetMessageType
	{
		NUMBER_PER_SYS=350,
		NUMBER_PER_SUB_SYS=50,
		CG_CHAT_MSG=20001,
		GC_CHAT_MSG=20002,
		CG_ADMIN_COMMAND=501,
		GC_SYSTEM_MESSAGE=502,
		GC_PING=503,
		CG_PING=504,
		GC_SYSTEM_NOTICE=505,
		CG_HANDSHAKE=506,
		GC_HANDSHAKE=507,
		CG_CHECK_PLAYER_LOGIN=851,
		GC_CHECK_PLAYER_LOGIN=852,
		GC_NOTIFY_EXCEPTION=853,
		CG_PLAYER_ENTER=854,
		CG_ENTER_SCENE=855,
		GC_ENTER_SCENE=856,
		CG_QUERY_PLAYER_INFO=857,
		GC_QUERY_PLAYER_INFO=858,
		CG_QUERY_PLAYER_INFO_NAME=859,
		GC_QUERY_PLAYER_INFO_NAME=860,
		CG_CLIENT_VERSION=861,
		GC_CLIENT_VERSION=862,
		GC_HUMAN_DETAIL_INFO=1551,
		GC_ROLE_SYMBOL_CHANGED_LONG=1552,
		CG_HUMAN_CHANGE_NAME=1553,
		GC_HUMAN_CHANGE_NAME=1554,
		CG_HUMAN_CHANGE_SEX=1555,
		GC_HUMAN_CHANGE_SEX=1556,
		CG_HUMAN_CHANGE_IMG=1557,
		GC_HUMAN_CHANGE_IMG=1558,
		GC_HUMAN_VIDEO_NUM=1561,
		GC_CHANEAGE_COUNTRIES=1562,
		CG_CHANEAGE_COUNTRIES=1563,
		GC_FRIEND_GAME=1570,
		GC_HUMAN_CHANGE_VIP=1572,
		CG_LOAD_MAIL_LIST=3651,
		GC_LOAD_MAIL_LIST=3652,
		GC_UPDATE_MAIL_LIST=3661,
		CG_LOAD_FRIEND_LIST=4001,
		GC_LOAD_FRIEND_LIST=4002,
		CG_LOAD_FRIEND_REQUEST_LIST=4003,
		GC_LOAD_FRIEND_REQUEST_LIST=4004,
		CG_REQUEST_FRIEND=4005,
		GC_REQUEST_FRIEND=4006,
		GC_REQUEST_FRIEND_SYNC=4007,
		CG_APPLY_FRIEND=4008,
		GC_APPLY_FRIEND=4009,
		GC_ADD_FRIEND=4010,
		CG_DELETE_FRIEND=4011,
		GC_DELETE_FRIEND=4012,
		CG_FACEBOOK_FRIENDS_SYNC=4020,
		GC_FACEBOOK_FRIENDS_SYNC=4021,
		GC_NOTICE_INFO_DATA=4701,
		GC_NOTICE_INFO_DATA_MULTI=4702,
		CG_FB_INVITE=5058,
		GC_FB_INVITE=5059,
		CG_FB_INVITE_GET_REWARD=5060,
		GC_FB_INVITE_GET_REWARD=5061,
		GC_MISC_FB_INFO_DATA=5062,
		CG_FB_GET_REWARD=5063,
		GC_FB_GET_REWARD=5064,
		CG_FB_THUMB_REWARD=5065,
		GC_FB_THUMB_REWARD=5066,
		CG_REQUEST_ORDER=5751,
		GC_REQUEST_ORDER=5752,
		CG_VALIDATE_ORDER=5753,
		GC_VALIDATE_ORDER=5754,
		GC_ORDER_INFO_DATA_LIST=5755,
		GC_REQUEST_ORDER_THIRD_PARTY=5769,
		CG_SEND_INTERACTIVE_ITEM=6451,
		GC_SEND_INTERACTIVE_ITEM=6452,
		CG_GROUP_SEND_INTERACTIVE_ITEM=6453,
		GC_GROUP_SEND_INTERACTIVE_ITEM=6454,
		GC_BAZOO_BASE=13168,
		CG_ROOM_ENTER=13101,
		CG_ROOM_OUT=13102,
		GC_ROOM_ENTER=13103,
		GC_DICE_UNIFY_SWING=13104,
		CG_DICE_SINGLE_SWING=13105,
		CG_TALK_BIG=13106,
		GC_TALK_BIG=13107,
		CG_ROB_OPEN=13108,
		GC_ROB_OPEN=13109,
		CG_GUESS_BIG_SMALL=13110,
		GC_GUESS_BIG_SMALL=13111,
		GC_END_COUNT=13112,
		GC_ROOM_OUT=13113,
		CG_MODE_CHOSE=13114,
		GC_DICE_SINGLE_SWING=13115,
		GC_MODE_CHOSE=13116,
		CG_ROOM_CREATE=13117,
		GC_ROOM_MATCHED=13118,
		CG_ROOM_PRI_SEARCH=13119,
		CG_ROOM_PRI_JOIN=13120,
		GC_ROOM_PRI_SEARCH=13121,
		GC_ROOM_CREATE=13122,
		GC_DICE_USER_SHOULD_SWING=13123,
		GC_GUESS_OPEN=13124,
		GC_ROOM_STATE=13125,
		GC_ROOM_INIT=13126,
		GC_STATE_ROOM_ENTER=13127,
		GC_STATE_ROOM_MATCHING=13128,
		GC_STATE_ROOM_READY=13129,
		GC_STATE_ROOM_ROUND_BEGIN=13130,
		GC_STATE_ROOM_ROUND_TURN=13131,
		GC_STATE_ROOM_CALL_DICE=13132,
		GC_STATE_ROOM_ROUND_OPEN=13133,
		GC_STATE_ROOM_ROUND_GUESS=13134,
		GC_STATE_ROOM_ROUND_RESULT=13135,
		CG_BAZOO_HEART_BEAT=13136,
		GC_BAZOO_HEART_BEAT=13137,
		GC_ROOM_ENTER_NOT_ALLOW=13138,
		CG_BAZOO_CHANGE_NAME=13139,
		GC_BAZOO_CHANGE_NAME=13140,
		CG_ROOM_PUB_JOIN=13141,
		CG_BAZOO_HALL_STATUS=13142,
		GC_ROOM_BE_REMOVEED=13143,
		GC_ROOM_MATCHEDING=13144,
		CG_ROOM_PRI_LIST=13145,
		GC_ROOM_PRI_LIST=13146,
		GC_ROOM_PRI_JOIN=13147,
		GC_ROBOT_DICE_UNIFY_SWING=13148,
		GC_ROBOT_WHICH_ROOM_TO_GOIN=13149,
		CG_BAZOO_MAGIC_FACE=13150,
		GC_BAZOO_MAGIC_FACE=13151,
		CG_BAZOO_SEND_GIFT=13152,
		GC_BAZOO_SEND_GIFT=13153,
		CG_BAZOO_RED_PACKAGE=13154,
		GC_BAZOO_RED_PACKAGE=13155,
		GC_BLACK_WHITE_ALL_SWING=13156,
		GC_BLACK_WHITE_WHO_TURN=13157,
		CG_BLACK_WHITE_CALL_NUM=13158,
		GC_BLACK_WHITE_CALL_NUM=13159,
		GC_BLACK_WHITE_END_COUNT=13160,
		GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL=13161,
		GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE=13162,
		GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT=13163,
		GC_STATE_ROOM_BLACK_WHITE_END=13164,
		CG_BAZOO_BOQU=13165,
		GC_BAZOO_BOQU=13166,
		CG_BAZOO_NEWGUY_PROCESS=13167,
		CG_BAZOO_FACEBOOK_ADD_GOLD=13168,
		GC_COW_BAZOO_BASE=13458,
		GC_COW_UNIFY_SWING=13451,
		CG_COW_SINGLE_SWING=13452,
		GC_COW_END_UNIFY_SWING=13453,
		GC_COW_SINGLE_SWING=13454,
		GC_COW_SINGLE_SWING_BEGIN=13455,
		GC_COW_SINGLE_SWING_END=13456,
		GC_STATE_ROOM_SINGLE_SWING_BEGIN=13457,
		GC_STATE_ROOM_SINGLE_SWING_END=13458,
		GC_SHOW_HAND_BAZOO_BASE=13813,
		GC_SHOW_HAND_UNIFY_SWING=13801,
		GC_SHOW_HAND_LITTLE_SWING=13802,
		CG_SHOW_HAND_SINGLE_SWING=13803,
		GC_SHOW_HAND_SINGLE_SWING=13804,
		GC_STATE_ROOM_SHOW_HAND_ALL_SWING=13805,
		GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING=13806,
		GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT=13807,
		CG_SHOW_HAND_SINGLE_SWICH=13808,
		GC_SHOW_HAND_SINGLE_SWICH=13809,
		CG_SHOW_HAND_SINGLE_SWICH_CANCEL=13810,
		GC_SHOW_HAND_SINGLE_SWICH_CANCEL=13811,
		GC_SHOW_HAND_END_COUNT=13812,
		GC_BAZOO_HALL_STATUS=13813,
		GC_BAZOO_SIGNIN_BASE=14152,
		CG_BAZOO_SIGNIN=14151,
		GC_BAZOO_SIGNIN=14152,
		GC_BAZOO_RANK_BASE=14503,
		CG_BAZOO_RANK_REQUEST=14501,
		GC_BAZOO_RANK_REQUEST=14502,
		CG_BAZOO_RANK_TOTAL_GOLD_REQUEST=14503,
		CG_BAZOO_TASK=14851,
		GC_BAZOO_TASK=14852,
		CG_BAZOO_GET_REWARD=14853,
		CG_BAZOO_ACHIEVE_TASK=14854,
		GC_BAZOO_ACHIEVE_TASK=14855,
		GC_BAZOO_GET_REWARD=14856,
		GC_BAZOO_ACHIEVE_BASE=15203,
		CG_BAZOO_ACHIEVE_FIRST=15201,
		CG_BAZOO_ACHIEVE=15202,
		GC_BAZOO_ACHIEVE=15203,
		CG_BAZOO_MALL_REQUEST=15551,
		GC_BAZOO_MALL_REQUEST=15552,
		CG_BAZOO_ITEM_REQUEST=15553,
		GC_BAZOO_ITEM_REQUEST=15554,
		CG_BAZOO_ITEM_CLOCK_CHANGE=15555,
		GC_BAZOO_ITEM_CLOCK_CHANGE=15556,
		CG_BAZOO_ITEM_BUY_BY_GOLD=15557,
		GC_BAZOO_ITEM_BUY_BY_GOLD=15558,
		GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL=15559,
		GC_BAZOO_NEW_GUY=15901,
		CG_BAZOO_NEW_GUY_PROCESS=15901
	};
}