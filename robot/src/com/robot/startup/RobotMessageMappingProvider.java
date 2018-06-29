package com.robot.startup;

import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.gameserver.club.msg.GCClubChangeName;
import com.gameserver.club.msg.GCClubSign;
import com.gameserver.notice.msg.GCNoticeInfoData;
import com.gameserver.baccart.msg.GCBaccartRevive;
import com.gameserver.misc.msg.GCFbInviteGetReward;
import com.gameserver.bazootask.msg.GCBazooGetReward;
import com.gameserver.bazoo.msg.GCCowEndUnifySwing;
import com.gameserver.slot.msg.GCSlotType33BonusList;
import com.gameserver.bazoogift.msg.GCBazooSendGift;
import com.gameserver.slot.msg.GCSlotTournamentStartData;
import com.gameserver.slot.msg.GCSlotType25BounsInfo;
import com.gameserver.slot.msg.GCSlotType26Bouns;
import com.gameserver.slot.msg.GCSngRankInfo;
import com.gameserver.slot.msg.GCSlotType31BonusOne;
import com.gameserver.activity.msg.GCUpdateHumanActivityReward;
import com.gameserver.gift.msg.GCRequestGift;
import com.gameserver.player.msg.GCQueryPlayerInfoName;
import com.gameserver.slot.msg.GCHumanJackpotReward;
import com.gameserver.texas.msg.GCSngRank;
import com.gameserver.lobby.msg.GCGametypeJackpot;
import com.gameserver.monthcard.msg.GCMonthCardGet;
import com.gameserver.slot.msg.GCSlotType32LeftBulletNum;
import com.gameserver.common.msg.GCHandshake;
import com.gameserver.activity.msg.GCMonthOrWeek;
import com.gameserver.bazoo.msg.GCBazooChangeName;
import com.gameserver.shop.msg.GCShopList;
import com.gameserver.texas.msg.GCHumanTexasInfoDataSearch;
import com.gameserver.slot.msg.GCSlotType20BounsNew;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteSwingLeft;
import com.gameserver.baccart.msg.GCBaccartClearTable;
import com.gameserver.club.msg.GCClubInvate;
import com.gameserver.bazoo.msg.GCRoomEnter;
import com.gameserver.texas.msg.GCTexasSmallBlind;
import com.gameserver.club.msg.GCClubEdit;
import com.gameserver.slot.msg.GCSlotType38Jackpot;
import com.gameserver.newbie.msg.GCSlotNewbie;
import com.gameserver.slot.msg.GCSlotEnter;
import com.gameserver.slot.msg.GCSlotType25WildInfo;
import com.gameserver.club.msg.GCClubInvateList;
import com.gameserver.slot.msg.GCSlotType13SendBouns;
import com.gameserver.item.msg.GCBazooItemClockChangeToAll;
import com.gameserver.bazoo.msg.GCCowSingleSwingBegin;
import com.gameserver.human.msg.GCHumanChangeImg;
import com.gameserver.texas.msg.GCHumanTexasSngInfoData;
import com.gameserver.slot.msg.GCSlotType12Free;
import com.gameserver.bazoo.msg.GCStateRoomRoundBegin;
import com.gameserver.mail.msg.GCDealWithReward;
import com.gameserver.common.msg.GCPing;
import com.gameserver.luckyspin.msg.GCSpinZhuanpanFree;
import com.gameserver.collect.msg.GCCardExchange;
import com.gameserver.human.msg.GCHumanChangeName;
import com.gameserver.task.msg.GCDailyTaskGet;
import com.gameserver.texas.msg.GCTexasBigBlind;
import com.gameserver.bazoo.msg.GCShowHandSingleSwich;
import com.gameserver.item.msg.GCBazooItemRequest;
import com.gameserver.newbie.msg.GCSavePoint;
import com.gameserver.bazoo.msg.GCStateRoomRoundGuess;
import com.gameserver.human.msg.GCHumanChangeVip;
import com.gameserver.slot.msg.GCSlotError;
import com.gameserver.lobby.msg.GCNewJackpot;
import com.gameserver.lobby.msg.GCJackpotListData;
import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.slot.msg.GCSlotType31BonusTwo;
import com.gameserver.human.msg.GCHumanChangeSex;
import com.gameserver.bazoo.msg.GCStateRoomRoundOpen;
import com.gameserver.mail.msg.GCReceiveAll;
import com.gameserver.activity.msg.GCHumanActivityRewardDataList;
import com.gameserver.bazoorank.msg.GCBazooRankRequest;
import com.gameserver.bazoo.msg.GCBlackWhiteAllSwing;
import com.gameserver.human.msg.GCHumanVideoNum;
import com.gameserver.task.msg.GCTaskInfoData;
import com.gameserver.relation.msg.GCFacebookFriendsSync;
import com.gameserver.relation.msg.GCLoadFriendRequestList;
import com.gameserver.activity.msg.GCActivityList;
import com.gameserver.baccart.msg.GCBaccartComplement;
import com.gameserver.baccart.msg.GCBaccartComplementTip;
import com.gameserver.club.msg.GCClubInfoGet;
import com.gameserver.player.msg.GCCheckPlayerLogin;
import com.gameserver.ranknew.msg.GCHumanRank;
import com.gameserver.texas.msg.GCTexasComplement;
import com.gameserver.signin.msg.GCSignIn;
import com.gameserver.bazoo.msg.GCBlackWhiteEndCount;
import com.gameserver.texas.msg.GCTexasHandCard;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.texas.msg.GCTexasExit;
import com.gameserver.human.msg.GCSlotRoomPlease;
import com.gameserver.baccart.msg.GCBaccartAuto;
import com.gameserver.recharge.msg.GCOrderAmazonDelivery;
import com.gameserver.misc.msg.GCNewUser;
import com.gameserver.relation.msg.GCRequestFriendSync;
import com.gameserver.misc.msg.GCMiscFbInfoData;
import com.gameserver.bazoo.msg.GCRoomEnterNotAllow;
import com.gameserver.slot.msg.GCGetSlotCachemsg;
import com.gameserver.slot.msg.GCSlotType25Bouns;
import com.gameserver.recharge.msg.GCCouponExist;
import com.gameserver.club.msg.GCClubSearchResult;
import com.gameserver.slot.msg.GCSlotType30Bouns;
import com.gameserver.slot.msg.GCSlotType24BounsBar;
import com.gameserver.givealike.msg.GCGetGivealikeInfo;
import com.gameserver.rank.msg.GCCommonRank;
import com.gameserver.vip.msg.GCVipCreateRoom;
import com.gameserver.texas.msg.GCTexasFlop;
import com.gameserver.slot.msg.GCSlotType26WildInfo;
import com.gameserver.item.msg.GCBazooItemBuyByGold;
import com.gameserver.lobby.msg.GCJackpotLevelData;
import com.gameserver.bazoo.msg.GCShowHandSingleSwichCancel;
import com.gameserver.texas.msg.GCTexasCoinsSync;
import com.gameserver.misc.msg.GCFbThumbReward;
import com.gameserver.vip.msg.GCVipBuy;
import com.gameserver.slot.msg.GCSlotType29Bouns;
import com.gameserver.signin.msg.GCSignInInfoData;
import com.gameserver.club.msg.GCClubRankingList;
import com.gameserver.texas.msg.GCTexasButtomDeal;
import com.gameserver.slot.msg.GCSlotType14RuneBonus;
import com.gameserver.club.msg.GCClubMemberList;
import com.gameserver.bazoo.msg.GCShowHandEndCount;
import com.gameserver.misc.msg.GCMiscInfoData;
import com.gameserver.collect.msg.GCCollectInit;
import com.gameserver.item.msg.GCSendInteractiveItem;
import com.gameserver.weekcard.msg.GCHumanWeekCardInfoData;
import com.gameserver.club.msg.GCClubTanheVote;
import com.gameserver.texas.msg.GCTexasList;
import com.gameserver.givealike.msg.GCIsnotGivealike;
import com.gameserver.vip.msg.GCVipGet;
import com.gameserver.bazoo.msg.GCStateRoomShowHandRoundResult;
import com.gameserver.texas.msg.GCTexasBankerPos;
import com.gameserver.slot.msg.GCSlotType20Bouns;
import com.gameserver.slot.msg.GCSlotType24BounsSamba;
import com.gameserver.slot.msg.GCSlotType28BounsInfo;
import com.gameserver.player.msg.GCClientVersion;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.human.msg.GCFriendGame;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.club.msg.GCClubKick;
import com.gameserver.worldboss.msg.GCGetRankInfo;
import com.gameserver.slot.msg.GCSlotType32SocialContact;
import com.gameserver.slot.msg.GCSlotType24BounsGameStart;
import com.gameserver.vip.msg.GCHumanVipInfoData;
import com.gameserver.texas.msg.GCTexasSngList;
import com.gameserver.baccart.msg.GCBaccartSeat;
import com.gameserver.item.msg.GCGroupSendInteractiveItem;
import com.gameserver.texas.msg.GCTexasClearTable;
import com.gameserver.slot.msg.GCSlotType32SpecialList;
import com.gameserver.mail.msg.GCUpdateMailList;
import com.gameserver.bazoo.msg.GCBlackWhiteWhoTurn;
import com.gameserver.item.msg.GCBazooItemClockChange;
import com.gameserver.slot.msg.GCSlotType12Choose;
import com.gameserver.club.msg.GCClubApplyOp;
import com.gameserver.collect.msg.GCRaffle;
import com.gameserver.slot.msg.GCSlotType32WildInfo;
import com.gameserver.bazoo.msg.GCRoomPriSearch;
import com.gameserver.slot.msg.GCSlotType10Scatter;
import com.gameserver.baccart.msg.GCBaccartSettle;
import com.gameserver.relation.msg.GCDeleteFriend;
import com.gameserver.bazoo.msg.GCStateRoomRoundResult;
import com.gameserver.slot.msg.GCSlotType14PreyBonus;
import com.gameserver.slot.msg.GCSlotList;
import com.gameserver.texas.msg.GCTexasTurn;
import com.gameserver.mail.msg.GCDeleteMail;
import com.gameserver.slot.msg.GCRemoveSlotSlot;
import com.gameserver.club.msg.GCClubApplyList;
import com.gameserver.player.msg.GCEnterScene;
import com.gameserver.club.msg.GCClubJoinResult;
import com.gameserver.slot.msg.GCSlotType32BulletIn;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.achievement.msg.GCAchInfo;
import com.gameserver.achievement.msg.GCReceiveAch;
import com.gameserver.texas.msg.GCHumanTexasInfoData;
import com.gameserver.bazoo.msg.GCShowHandUnifySwing;
import com.gameserver.bazoo.msg.GCBazooMagicFace;
import com.gameserver.texas.msg.GCHumanTexasExp;
import com.gameserver.baccart.msg.GCBaccartLight;
import com.gameserver.vipnew.msg.GCVipNewData;
import com.gameserver.slot.msg.GCSlotType3BounsStart;
import com.gameserver.slot.msg.GCSlotType31WildInfo;
import com.gameserver.texas.msg.GCJoinTexas;
import com.gameserver.baccart.msg.GCBaccartList;
import com.gameserver.bazoo.msg.GCRoomMatcheding;
import com.gameserver.texas.msg.GCTexasRiver;
import com.gameserver.texas.msg.GCTexasSeat;
import com.gameserver.worldboss.msg.GCSelfAttackBloodInfo;
import com.gameserver.slot.msg.GCSlotType28Bouns;
import com.gameserver.mail.msg.GCSendMail;
import com.gameserver.texas.msg.GCTexasRoomInfo;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.gameserver.player.msg.GCQueryPlayerInfo;
import com.gameserver.human.msg.GCRoleSymbolChangedLong;
import com.gameserver.bazoo.msg.GCStateRoomShowHandAllSwing;
import com.gameserver.club.msg.GCClubSignData;
import com.gameserver.misc.msg.GCKoreanSb;
import com.gameserver.texas.msg.GCJoinTexasVipFailed;
import com.gameserver.texas.msg.GCTexasFollow;
import com.gameserver.recharge.msg.GCValidateOrder;
import com.gameserver.slot.msg.GCSlotType23BounsInfo;
import com.gameserver.club.msg.GCClubLeave;
import com.gameserver.slot.msg.GCSlotType28ScatterInfo;
import com.gameserver.task.msg.GCTaskProgress;
import com.gameserver.guide.msg.GCPayGuide;
import com.gameserver.item.msg.GCHumanBagInfoData;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.human.msg.GCLevelGiftMore;
import com.gameserver.bazoo.msg.GCGuessOpen;
import com.gameserver.activity.msg.GCStillHaveActivityGold;
import com.gameserver.bazoo.msg.GCStateRoomCallDice;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteWaitSomeOne;
import com.gameserver.bazoo.msg.GCStateRoomEnter;
import com.gameserver.club.msg.GCClubDonate;
import com.gameserver.bazoo.msg.GCBazooBoqu;
import com.gameserver.worldboss.msg.GCBossStartEndInfo;
import com.gameserver.bazooachieve.msg.GCBazooAchieve;
import com.gameserver.newbie.msg.GCGetSavePoint;
import com.gameserver.recharge.msg.GCObtainCoupon;
import com.gameserver.task.msg.GCTaskInfoDataChange;
import com.gameserver.collect.msg.GCGetVouchers;
import com.gameserver.slot.msg.GCSlotType24SendBouns;
import com.gameserver.relation.msg.GCApplyFriend;
import com.gameserver.baccart.msg.GCBaccartJoin;
import com.gameserver.ranknew.msg.GCRankList;
import com.gameserver.shop.msg.GCBuyItem;
import com.gameserver.mail.msg.GCLoadMailList;
import com.gameserver.bazoogift.msg.GCBazooRedPackage;
import com.gameserver.bazootask.msg.GCBazooAchieveTask;
import com.gameserver.slot.msg.GCSlotType14Bonus;
import com.gameserver.lobby.msg.GCAllSlotNewJackpots;
import com.gameserver.baccart.msg.GCBaccartExit;
import com.gameserver.texas.msg.GCLeaveTexas;
import com.gameserver.recharge.msg.GCOrderInfoDataList;
import com.gameserver.luckyspin.msg.GCLuckySpin;
import com.gameserver.bazoo.msg.GCEndCount;
import com.gameserver.bazoo.msg.GCBlackWhiteCallNum;
import com.gameserver.baccart.msg.GCHumanBaccartCoinsSync;
import com.gameserver.chat.msg.GCChatMsg;
import com.gameserver.notice.msg.GCNoticeInfoDataMulti;
import com.gameserver.lobby.msg.GCSlotNewJackpots;
import com.gameserver.slot.msg.GCSlotType30BounsInfo;
import com.gameserver.item.msg.GCBazooMallRequest;
import com.gameserver.baccart.msg.GCBaccartJackpot;
import com.gameserver.slot.msg.GCSlotType31Bonus;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.slot.msg.GCRotaryTable;
import com.gameserver.club.msg.GCClubDonateData;
import com.gameserver.recharge.msg.GCDoubleTurn;
import com.gameserver.bazoo.msg.GCCowUnifySwing;
import com.gameserver.worldboss.msg.GCRefreshBossInfo;
import com.gameserver.slot.msg.GCSlotType24Bouns;
import com.gameserver.recharge.msg.GCMolOrder;
import com.gameserver.baccart.msg.GCBaccartBet;
import com.gameserver.relation.msg.GCFriendGiftGet;
import com.gameserver.slot.msg.GCSlotType15Bouns;
import com.gameserver.texas.msg.GCTexasVipList;
import com.gameserver.relation.msg.GCFriendGift;
import com.gameserver.baccart.msg.GCBaccartShuffle;
import com.gameserver.slot.msg.GCSlotType31BonusThree;
import com.gameserver.activity.msg.GCGetActivityReward;
import com.gameserver.relation.msg.GCStrangerList;
import com.gameserver.recharge.msg.GCMycardAuthcode;
import com.gameserver.bazoo.msg.GCRoomBeRemoveed;
import com.gameserver.slot.msg.GCSlotType27BounsInfo;
import com.gameserver.human.msg.GCHumanDetailInfo;
import com.gameserver.worldboss.msg.GCBeforeStart;
import com.gameserver.relation.msg.GCAddFriend;
import com.gameserver.luckyspin.msg.GCSpinZhuanpanNofree;
import com.gameserver.slot.msg.GCSlotType32Bonus;
import com.gameserver.slot.msg.GCTournamentGetList;
import com.gameserver.bazoo.msg.GCStateRoomRoundTurn;
import com.gameserver.slot.msg.GCSlotType38BonusTrigger;
import com.gameserver.slot.msg.GCSlotType38Bonus;
import com.gameserver.mail.msg.GCReadMail;
import com.gameserver.bazoo.msg.GCShowHandLittleSwing;
import com.gameserver.conversioncode.msg.GCConversionCode;
import com.gameserver.texas.msg.GCTexasPlayerTurn;
import com.gameserver.bazoo.msg.GCGuessBigSmall;
import com.gameserver.club.msg.GCClubInfo;
import com.gameserver.bazoo.msg.GCStateRoomShowHandSingleSwing;
import com.gameserver.bazoosignin.msg.GCBazooSignin;
import com.gameserver.baccart.msg.GCBaccartPlayerJackpot;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.luckyspin.msg.GCBigZhuanpan;
import com.gameserver.club.msg.GCClubNoteList;
import com.gameserver.misc.msg.GCFbGetReward;
import com.gameserver.relation.msg.GCFriendGiftSync;
import com.gameserver.slot.msg.GCSlotGetRank;
import com.gameserver.worldboss.msg.GCReturnBlood;
import com.gameserver.bazoo.msg.GCDiceSingleSwing;
import com.gameserver.treasury.msg.GCTreasury;
import com.gameserver.club.msg.GCClubNoteDelete;
import com.gameserver.texas.msg.GCTexasTips;
import com.gameserver.bazoo.msg.GCStateRoomMatching;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteSomeOneCall;
import com.gameserver.slot.msg.GCSlotType23InitReward;
import com.gameserver.misc.msg.GCFbInvite;
import com.gameserver.slot.msg.GCSlotType38Pumpkin;
import com.gameserver.bazoo.msg.GCRoomState;
import com.gameserver.slot.msg.GCSlotType26BounsInfo;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.club.msg.GCClubTanheState;
import com.gameserver.club.msg.GCClubGetGift;
import com.gameserver.slot.msg.GCSlotType32BulletOut;
import com.gameserver.relation.msg.GCRequestFriend;
import com.gameserver.slot.msg.GCSlotType31SpecificWildInfo;
import com.gameserver.baccart.msg.GCBaccartStartBet;
import com.gameserver.slot.msg.GCSlotType14AppleBonus;
import com.gameserver.worldboss.msg.GCGetBossInfo;
import com.gameserver.misc.msg.GCOnlineReward;
import com.gameserver.bazoo.msg.GCStateRoomSingleSwingEnd;
import com.gameserver.bazoo.msg.GCDiceUnifySwing;
import com.gameserver.slot.msg.GCSlotType29WildInfo;
import com.gameserver.human.msg.GCExpDouble;
import com.gameserver.recharge.msg.GCRequestOrderThirdParty;
import com.gameserver.human.msg.GCChaneageCountries;
import com.gameserver.slot.msg.GCSlotType20;
import com.gameserver.slot.msg.GCSlotType22;
import com.gameserver.relation.msg.GCLoadFriendGiftList;
import com.gameserver.slot.msg.GCSlotType13Bouns;
import com.gameserver.bazoo.msg.GCRoomCreate;
import com.gameserver.slot.msg.GCSlotType21BounsInfo;
import com.gameserver.bazoo.msg.GCRoomPriJoin;
import com.gameserver.slot.msg.GCSlotType16;
import com.gameserver.slot.msg.GCSlotType17;
import com.gameserver.slot.msg.GCSlotType18;
import com.gameserver.slot.msg.GCSlotType19;
import com.gameserver.texas.msg.GCTexasPeopleSetting;
import com.gameserver.texas.msg.GCTexasLook;
import com.gameserver.activity.msg.GCFunctionLeftTime;
import com.gameserver.slot.msg.GCSlotType10;
import com.gameserver.human.msg.GCSlotRoom3;
import com.gameserver.slot.msg.GCSlotType11;
import com.gameserver.human.msg.GCSlotRoom4;
import com.gameserver.slot.msg.GCSlotType12;
import com.gameserver.human.msg.GCSlotRoom1;
import com.gameserver.texas.msg.GCTexasAllIn;
import com.gameserver.human.msg.GCSlotRoom2;
import com.gameserver.slot.msg.GCSlotType14;
import com.gameserver.activity.msg.GCMonthWeekLeftTime;
import com.gameserver.relation.msg.GCLoadFriendList;
import com.gameserver.activity.msg.GCHunamnProgress;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.msg.GCRobotDiceUnifySwing;
import com.gameserver.slot.msg.GCSlotTournamentNotOpen;
import com.gameserver.recharge.msg.GCRequestOrder;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.slot.msg.GCSlotType15BounsStart;
import com.gameserver.slot.msg.GCSlotType28WildInfo;
import com.gameserver.activity.msg.GCUpdateActivity;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.human.msg.GCSlotRoom5;
import com.gameserver.baccart.msg.GCBaccartStand;
import com.gameserver.slot.msg.GCSlotType29BounsInfo;
import com.gameserver.texas.msg.GCTexasPrepareRestart;
import com.gameserver.texas.msg.GCTexasComplementNum;
import com.gameserver.slot.msg.GCWinnerWheel;
import com.gameserver.club.msg.GCClubPromate;
import com.gameserver.luckyspin.msg.GCLuckyMatch;
import com.gameserver.slot.msg.GCSlotRankList;
import com.gameserver.collect.msg.GCCharmExchange;
import com.gameserver.bazoo.msg.GCStateRoomReady;
import com.gameserver.bazoo.msg.GCShowHandSingleSwing;
import com.gameserver.slot.msg.GCSlotGetReward;
import com.gameserver.texas.msg.GCSyncJoinTexas;
import com.gameserver.texas.msg.GCTexasSidePool;
import com.gameserver.gift.msg.GCNewComerGift;
import com.gameserver.bazoo.msg.GCStateRoomSingleSwingBegin;
import com.gameserver.bazoo.msg.GCCowSingleSwingEnd;
import com.gameserver.texas.msg.GCTexasGiveUp;
import com.gameserver.texas.msg.GCTexasSettleGiveup;
import com.gameserver.activity.msg.GCHunamnProgressSingle;
import com.gameserver.texas.msg.GCTexasSettle;
import com.gameserver.slot.msg.GCFreeSlotReward;
import com.gameserver.bazoo.msg.GCCowSingleSwing;
import com.gameserver.monthcard.msg.GCHumanMonthCardInfoData;
import com.gameserver.bazootask.msg.GCBazooTask;
import com.gameserver.texas.msg.GCTexasAddBet;
import com.gameserver.slot.msg.GCSlotType38Wild;
import com.gameserver.baccart.msg.GCBaccartSyncJoin;
import com.gameserver.bazoo.msg.GCRoomPriList;
import com.gameserver.baccart.msg.GCHumanBaccart;
import com.gameserver.bazoo.msg.GCStateRoomBlackWhiteEnd;
import com.gameserver.weekcard.msg.GCWeekCardGet;
import com.gameserver.club.msg.GCClubList;
import com.gameserver.luckyspin.msg.GCLuckySpinInfoData;
import com.gameserver.slot.msg.GCSlotType21Bouns;
import com.gameserver.human.msg.GCSlotRoomGift;
import com.gameserver.club.msg.GCClubJoin2;
/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class RobotMessageMappingProvider {
	
	public static final short[] msgTypes = new short[]{
		MessageType.GC_CLUB_CHANGE_NAME,
		MessageType.GC_CLUB_SIGN,
		MessageType.GC_NOTICE_INFO_DATA,
		MessageType.GC_BACCART_REVIVE,
		MessageType.GC_FB_INVITE_GET_REWARD,
		MessageType.GC_BAZOO_GET_REWARD,
		MessageType.GC_COW_END_UNIFY_SWING,
		MessageType.GC_SLOT_TYPE33_BONUS_LIST,
		MessageType.GC_BAZOO_SEND_GIFT,
		MessageType.GC_SLOT_TOURNAMENT_START_DATA,
		MessageType.GC_SLOT_TYPE25_BOUNS_INFO,
		MessageType.GC_SLOT_TYPE26_BOUNS,
		MessageType.GC_SNG_RANK_INFO,
		MessageType.GC_SLOT_TYPE31_BONUS_ONE,
		MessageType.GC_UPDATE_HUMAN_ACTIVITY_REWARD,
		MessageType.GC_REQUEST_GIFT,
		MessageType.GC_QUERY_PLAYER_INFO_NAME,
		MessageType.GC_HUMAN_JACKPOT_REWARD,
		MessageType.GC_SNG_RANK,
		MessageType.GC_GAMETYPE_JACKPOT,
		MessageType.GC_MONTH_CARD_GET,
		MessageType.GC_SLOT_TYPE32_LEFT_BULLET_NUM,
		MessageType.GC_HANDSHAKE,
		MessageType.GC_MONTH_OR_WEEK,
		MessageType.GC_BAZOO_CHANGE_NAME,
		MessageType.GC_SHOP_LIST,
		MessageType.GC_HUMAN_TEXAS_INFO_DATA_SEARCH,
		MessageType.GC_SLOT_TYPE20_BOUNS_NEW,
		MessageType.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT,
		MessageType.GC_BACCART_CLEAR_TABLE,
		MessageType.GC_CLUB_INVATE,
		MessageType.GC_ROOM_ENTER,
		MessageType.GC_TEXAS_SMALL_BLIND,
		MessageType.GC_CLUB_EDIT,
		MessageType.GC_SLOT_TYPE38_JACKPOT,
		MessageType.GC_SLOT_NEWBIE,
		MessageType.GC_SLOT_ENTER,
		MessageType.GC_SLOT_TYPE25_WILD_INFO,
		MessageType.GC_CLUB_INVATE_LIST,
		MessageType.GC_SLOT_TYPE13_SEND_BOUNS,
		MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL,
		MessageType.GC_COW_SINGLE_SWING_BEGIN,
		MessageType.GC_HUMAN_CHANGE_IMG,
		MessageType.GC_HUMAN_TEXAS_SNG_INFO_DATA,
		MessageType.GC_SLOT_TYPE12_FREE,
		MessageType.GC_STATE_ROOM_ROUND_BEGIN,
		MessageType.GC_DEAL_WITH_REWARD,
		MessageType.GC_PING,
		MessageType.GC_SPIN_ZHUANPAN_FREE,
		MessageType.GC_CARD_EXCHANGE,
		MessageType.GC_HUMAN_CHANGE_NAME,
		MessageType.GC_DAILY_TASK_GET,
		MessageType.GC_TEXAS_BIG_BLIND,
		MessageType.GC_SHOW_HAND_SINGLE_SWICH,
		MessageType.GC_BAZOO_ITEM_REQUEST,
		MessageType.GC_SAVE_POINT,
		MessageType.GC_STATE_ROOM_ROUND_GUESS,
		MessageType.GC_HUMAN_CHANGE_VIP,
		MessageType.GC_SLOT_ERROR,
		MessageType.GC_NEW_JACKPOT,
		MessageType.GC_JACKPOT_LIST_DATA,
		MessageType.GC_BAZOO_HALL_STATUS,
		MessageType.GC_SLOT_TYPE31_BONUS_TWO,
		MessageType.GC_HUMAN_CHANGE_SEX,
		MessageType.GC_STATE_ROOM_ROUND_OPEN,
		MessageType.GC_RECEIVE_ALL,
		MessageType.GC_HUMAN_ACTIVITY_REWARD_DATA_LIST,
		MessageType.GC_BAZOO_RANK_REQUEST,
		MessageType.GC_BLACK_WHITE_ALL_SWING,
		MessageType.GC_HUMAN_VIDEO_NUM,
		MessageType.GC_TASK_INFO_DATA,
		MessageType.GC_FACEBOOK_FRIENDS_SYNC,
		MessageType.GC_LOAD_FRIEND_REQUEST_LIST,
		MessageType.GC_ACTIVITY_LIST,
		MessageType.GC_BACCART_COMPLEMENT,
		MessageType.GC_BACCART_COMPLEMENT_TIP,
		MessageType.GC_CLUB_INFO_GET,
		MessageType.GC_CHECK_PLAYER_LOGIN,
		MessageType.GC_HUMAN_RANK,
		MessageType.GC_TEXAS_COMPLEMENT,
		MessageType.GC_SIGN_IN,
		MessageType.GC_BLACK_WHITE_END_COUNT,
		MessageType.GC_TEXAS_HAND_CARD,
		MessageType.GC_SYSTEM_MESSAGE,
		MessageType.GC_TEXAS_EXIT,
		MessageType.GC_SLOT_ROOM_PLEASE,
		MessageType.GC_BACCART_AUTO,
		MessageType.GC_ORDER_AMAZON_DELIVERY,
		MessageType.GC_NEW_USER,
		MessageType.GC_REQUEST_FRIEND_SYNC,
		MessageType.GC_MISC_FB_INFO_DATA,
		MessageType.GC_ROOM_ENTER_NOT_ALLOW,
		MessageType.GC_GET_SLOT_CACHEMSG,
		MessageType.GC_SLOT_TYPE25_BOUNS,
		MessageType.GC_COUPON_EXIST,
		MessageType.GC_CLUB_SEARCH_RESULT,
		MessageType.GC_SLOT_TYPE30_BOUNS,
		MessageType.GC_SLOT_TYPE24_BOUNS_BAR,
		MessageType.GC_GET_GIVEALIKE_INFO,
		MessageType.GC_COMMON_RANK,
		MessageType.GC_VIP_CREATE_ROOM,
		MessageType.GC_TEXAS_FLOP,
		MessageType.GC_SLOT_TYPE26_WILD_INFO,
		MessageType.GC_BAZOO_ITEM_BUY_BY_GOLD,
		MessageType.GC_JACKPOT_LEVEL_DATA,
		MessageType.GC_SHOW_HAND_SINGLE_SWICH_CANCEL,
		MessageType.GC_TEXAS_COINS_SYNC,
		MessageType.GC_FB_THUMB_REWARD,
		MessageType.GC_VIP_BUY,
		MessageType.GC_SLOT_TYPE29_BOUNS,
		MessageType.GC_SIGN_IN_INFO_DATA,
		MessageType.GC_CLUB_RANKING_LIST,
		MessageType.GC_TEXAS_BUTTOM_DEAL,
		MessageType.GC_SLOT_TYPE14_RUNE_BONUS,
		MessageType.GC_CLUB_MEMBER_LIST,
		MessageType.GC_SHOW_HAND_END_COUNT,
		MessageType.GC_MISC_INFO_DATA,
		MessageType.GC_COLLECT_INIT,
		MessageType.GC_SEND_INTERACTIVE_ITEM,
		MessageType.GC_HUMAN_WEEK_CARD_INFO_DATA,
		MessageType.GC_CLUB_TANHE_VOTE,
		MessageType.GC_TEXAS_LIST,
		MessageType.GC_ISNOT_GIVEALIKE,
		MessageType.GC_VIP_GET,
		MessageType.GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT,
		MessageType.GC_TEXAS_BANKER_POS,
		MessageType.GC_SLOT_TYPE20_BOUNS,
		MessageType.GC_SLOT_TYPE24_BOUNS_SAMBA,
		MessageType.GC_SLOT_TYPE28_BOUNS_INFO,
		MessageType.GC_CLIENT_VERSION,
		MessageType.GC_DICE_USER_SHOULD_SWING,
		MessageType.GC_FRIEND_GAME,
		MessageType.GC_ROOM_MATCHED,
		MessageType.GC_CLUB_KICK,
		MessageType.GC_GET_RANK_INFO,
		MessageType.GC_SLOT_TYPE32_SOCIAL_CONTACT,
		MessageType.GC_SLOT_TYPE24_BOUNS_GAME_START,
		MessageType.GC_HUMAN_VIP_INFO_DATA,
		MessageType.GC_TEXAS_SNG_LIST,
		MessageType.GC_BACCART_SEAT,
		MessageType.GC_GROUP_SEND_INTERACTIVE_ITEM,
		MessageType.GC_TEXAS_CLEAR_TABLE,
		MessageType.GC_SLOT_TYPE32_SPECIAL_LIST,
		MessageType.GC_UPDATE_MAIL_LIST,
		MessageType.GC_BLACK_WHITE_WHO_TURN,
		MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE,
		MessageType.GC_SLOT_TYPE12_CHOOSE,
		MessageType.GC_CLUB_APPLY_OP,
		MessageType.GC_RAFFLE,
		MessageType.GC_SLOT_TYPE32_WILD_INFO,
		MessageType.GC_ROOM_PRI_SEARCH,
		MessageType.GC_SLOT_TYPE10_SCATTER,
		MessageType.GC_BACCART_SETTLE,
		MessageType.GC_DELETE_FRIEND,
		MessageType.GC_STATE_ROOM_ROUND_RESULT,
		MessageType.GC_SLOT_TYPE14_PREY_BONUS,
		MessageType.GC_SLOT_LIST,
		MessageType.GC_TEXAS_TURN,
		MessageType.GC_DELETE_MAIL,
		MessageType.GC_REMOVE_SLOT_SLOT,
		MessageType.GC_CLUB_APPLY_LIST,
		MessageType.GC_ENTER_SCENE,
		MessageType.GC_CLUB_JOIN_RESULT,
		MessageType.GC_SLOT_TYPE32_BULLET_IN,
		MessageType.GC_ROBOT_WHICH_ROOM_TO_GOIN,
		MessageType.GC_ACH_INFO,
		MessageType.GC_RECEIVE_ACH,
		MessageType.GC_HUMAN_TEXAS_INFO_DATA,
		MessageType.GC_SHOW_HAND_UNIFY_SWING,
		MessageType.GC_BAZOO_MAGIC_FACE,
		MessageType.GC_HUMAN_TEXAS_EXP,
		MessageType.GC_BACCART_LIGHT,
		MessageType.GC_VIP_NEW_DATA,
		MessageType.GC_SLOT_TYPE3_BOUNS_START,
		MessageType.GC_SLOT_TYPE31_WILD_INFO,
		MessageType.GC_JOIN_TEXAS,
		MessageType.GC_BACCART_LIST,
		MessageType.GC_ROOM_MATCHEDING,
		MessageType.GC_TEXAS_RIVER,
		MessageType.GC_TEXAS_SEAT,
		MessageType.GC_SELF_ATTACK_BLOOD_INFO,
		MessageType.GC_SLOT_TYPE28_BOUNS,
		MessageType.GC_SEND_MAIL,
		MessageType.GC_TEXAS_ROOM_INFO,
		MessageType.GC_TALK_BIG,
		MessageType.GC_QUERY_PLAYER_INFO,
		MessageType.GC_ROLE_SYMBOL_CHANGED_LONG,
		MessageType.GC_STATE_ROOM_SHOW_HAND_ALL_SWING,
		MessageType.GC_CLUB_SIGN_DATA,
		MessageType.GC_KOREAN_SB,
		MessageType.GC_JOIN_TEXAS_VIP_FAILED,
		MessageType.GC_TEXAS_FOLLOW,
		MessageType.GC_VALIDATE_ORDER,
		MessageType.GC_SLOT_TYPE23_BOUNS_INFO,
		MessageType.GC_CLUB_LEAVE,
		MessageType.GC_SLOT_TYPE28_SCATTER_INFO,
		MessageType.GC_TASK_PROGRESS,
		MessageType.GC_PAY_GUIDE,
		MessageType.GC_HUMAN_BAG_INFO_DATA,
		MessageType.GC_ROOM_INIT,
		MessageType.GC_LEVEL_GIFT_MORE,
		MessageType.GC_GUESS_OPEN,
		MessageType.GC_STILL_HAVE_ACTIVITY_GOLD,
		MessageType.GC_STATE_ROOM_CALL_DICE,
		MessageType.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE,
		MessageType.GC_STATE_ROOM_ENTER,
		MessageType.GC_CLUB_DONATE,
		MessageType.GC_BAZOO_BOQU,
		MessageType.GC_BOSS_START_END_INFO,
		MessageType.GC_BAZOO_ACHIEVE,
		MessageType.GC_GET_SAVE_POINT,
		MessageType.GC_OBTAIN_COUPON,
		MessageType.GC_TASK_INFO_DATA_CHANGE,
		MessageType.GC_GET_VOUCHERS,
		MessageType.GC_SLOT_TYPE24_SEND_BOUNS,
		MessageType.GC_APPLY_FRIEND,
		MessageType.GC_BACCART_JOIN,
		MessageType.GC_RANK_LIST,
		MessageType.GC_BUY_ITEM,
		MessageType.GC_LOAD_MAIL_LIST,
		MessageType.GC_BAZOO_RED_PACKAGE,
		MessageType.GC_BAZOO_ACHIEVE_TASK,
		MessageType.GC_SLOT_TYPE14_BONUS,
		MessageType.GC_ALL_SLOT_NEW_JACKPOTS,
		MessageType.GC_BACCART_EXIT,
		MessageType.GC_LEAVE_TEXAS,
		MessageType.GC_ORDER_INFO_DATA_LIST,
		MessageType.GC_LUCKY_SPIN,
		MessageType.GC_END_COUNT,
		MessageType.GC_BLACK_WHITE_CALL_NUM,
		MessageType.GC_HUMAN_BACCART_COINS_SYNC,
		MessageType.GC_CHAT_MSG,
		MessageType.GC_NOTICE_INFO_DATA_MULTI,
		MessageType.GC_SLOT_NEW_JACKPOTS,
		MessageType.GC_SLOT_TYPE30_BOUNS_INFO,
		MessageType.GC_BAZOO_MALL_REQUEST,
		MessageType.GC_BACCART_JACKPOT,
		MessageType.GC_SLOT_TYPE31_BONUS,
		MessageType.GC_ROB_OPEN,
		MessageType.GC_ROTARY_TABLE,
		MessageType.GC_CLUB_DONATE_DATA,
		MessageType.GC_DOUBLE_TURN,
		MessageType.GC_COW_UNIFY_SWING,
		MessageType.GC_REFRESH_BOSS_INFO,
		MessageType.GC_SLOT_TYPE24_BOUNS,
		MessageType.GC_MOL_ORDER,
		MessageType.GC_BACCART_BET,
		MessageType.GC_FRIEND_GIFT_GET,
		MessageType.GC_SLOT_TYPE15_BOUNS,
		MessageType.GC_TEXAS_VIP_LIST,
		MessageType.GC_FRIEND_GIFT,
		MessageType.GC_BACCART_SHUFFLE,
		MessageType.GC_SLOT_TYPE31_BONUS_THREE,
		MessageType.GC_GET_ACTIVITY_REWARD,
		MessageType.GC_STRANGER_LIST,
		MessageType.GC_MYCARD_AUTHCODE,
		MessageType.GC_ROOM_BE_REMOVEED,
		MessageType.GC_SLOT_TYPE27_BOUNS_INFO,
		MessageType.GC_HUMAN_DETAIL_INFO,
		MessageType.GC_BEFORE_START,
		MessageType.GC_ADD_FRIEND,
		MessageType.GC_SPIN_ZHUANPAN_NOFREE,
		MessageType.GC_SLOT_TYPE32_BONUS,
		MessageType.GC_TOURNAMENT_GET_LIST,
		MessageType.GC_STATE_ROOM_ROUND_TURN,
		MessageType.GC_SLOT_TYPE38_BONUS_TRIGGER,
		MessageType.GC_SLOT_TYPE38_BONUS,
		MessageType.GC_READ_MAIL,
		MessageType.GC_SHOW_HAND_LITTLE_SWING,
		MessageType.GC_CONVERSION_CODE,
		MessageType.GC_TEXAS_PLAYER_TURN,
		MessageType.GC_GUESS_BIG_SMALL,
		MessageType.GC_CLUB_INFO,
		MessageType.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING,
		MessageType.GC_BAZOO_SIGNIN,
		MessageType.GC_BACCART_PLAYER_JACKPOT,
		MessageType.GC_MODE_CHOSE,
		MessageType.GC_BIG_ZHUANPAN,
		MessageType.GC_CLUB_NOTE_LIST,
		MessageType.GC_FB_GET_REWARD,
		MessageType.GC_FRIEND_GIFT_SYNC,
		MessageType.GC_SLOT_GET_RANK,
		MessageType.GC_RETURN_BLOOD,
		MessageType.GC_DICE_SINGLE_SWING,
		MessageType.GC_TREASURY,
		MessageType.GC_CLUB_NOTE_DELETE,
		MessageType.GC_TEXAS_TIPS,
		MessageType.GC_STATE_ROOM_MATCHING,
		MessageType.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL,
		MessageType.GC_SLOT_TYPE23_INIT_REWARD,
		MessageType.GC_FB_INVITE,
		MessageType.GC_SLOT_TYPE38_PUMPKIN,
		MessageType.GC_ROOM_STATE,
		MessageType.GC_SLOT_TYPE26_BOUNS_INFO,
		MessageType.GC_SLOT_SLOT,
		MessageType.GC_CLUB_TANHE_STATE,
		MessageType.GC_CLUB_GET_GIFT,
		MessageType.GC_SLOT_TYPE32_BULLET_OUT,
		MessageType.GC_REQUEST_FRIEND,
		MessageType.GC_SLOT_TYPE31_SPECIFIC_WILD_INFO,
		MessageType.GC_BACCART_START_BET,
		MessageType.GC_SLOT_TYPE14_APPLE_BONUS,
		MessageType.GC_GET_BOSS_INFO,
		MessageType.GC_ONLINE_REWARD,
		MessageType.GC_STATE_ROOM_SINGLE_SWING_END,
		MessageType.GC_DICE_UNIFY_SWING,
		MessageType.GC_SLOT_TYPE29_WILD_INFO,
		MessageType.GC_EXP_DOUBLE,
		MessageType.GC_REQUEST_ORDER_THIRD_PARTY,
		MessageType.GC_CHANEAGE_COUNTRIES,
		MessageType.GC_SLOT_TYPE20,
		MessageType.GC_SLOT_TYPE22,
		MessageType.GC_LOAD_FRIEND_GIFT_LIST,
		MessageType.GC_SLOT_TYPE13_BOUNS,
		MessageType.GC_ROOM_CREATE,
		MessageType.GC_SLOT_TYPE21_BOUNS_INFO,
		MessageType.GC_ROOM_PRI_JOIN,
		MessageType.GC_SLOT_TYPE16,
		MessageType.GC_SLOT_TYPE17,
		MessageType.GC_SLOT_TYPE18,
		MessageType.GC_SLOT_TYPE19,
		MessageType.GC_TEXAS_PEOPLE_SETTING,
		MessageType.GC_TEXAS_LOOK,
		MessageType.GC_FUNCTION_LEFT_TIME,
		MessageType.GC_SLOT_TYPE10,
		MessageType.GC_SLOT_ROOM3,
		MessageType.GC_SLOT_TYPE11,
		MessageType.GC_SLOT_ROOM4,
		MessageType.GC_SLOT_TYPE12,
		MessageType.GC_SLOT_ROOM1,
		MessageType.GC_TEXAS_ALL_IN,
		MessageType.GC_SLOT_ROOM2,
		MessageType.GC_SLOT_TYPE14,
		MessageType.GC_MONTH_WEEK_LEFT_TIME,
		MessageType.GC_LOAD_FRIEND_LIST,
		MessageType.GC_HUNAMN_PROGRESS,
		MessageType.GC_ROOM_OUT,
		MessageType.GC_ROBOT_DICE_UNIFY_SWING,
		MessageType.GC_SLOT_TOURNAMENT_NOT_OPEN,
		MessageType.GC_REQUEST_ORDER,
		MessageType.GC_BAZOO_HEART_BEAT,
		MessageType.GC_SLOT_TYPE15_BOUNS_START,
		MessageType.GC_SLOT_TYPE28_WILD_INFO,
		MessageType.GC_UPDATE_ACTIVITY,
		MessageType.GC_NOTIFY_EXCEPTION,
		MessageType.GC_SLOT_ROOM5,
		MessageType.GC_BACCART_STAND,
		MessageType.GC_SLOT_TYPE29_BOUNS_INFO,
		MessageType.GC_TEXAS_PREPARE_RESTART,
		MessageType.GC_TEXAS_COMPLEMENT_NUM,
		MessageType.GC_WINNER_WHEEL,
		MessageType.GC_CLUB_PROMATE,
		MessageType.GC_LUCKY_MATCH,
		MessageType.GC_SLOT_RANK_LIST,
		MessageType.GC_CHARM_EXCHANGE,
		MessageType.GC_STATE_ROOM_READY,
		MessageType.GC_SHOW_HAND_SINGLE_SWING,
		MessageType.GC_SLOT_GET_REWARD,
		MessageType.GC_SYNC_JOIN_TEXAS,
		MessageType.GC_TEXAS_SIDE_POOL,
		MessageType.GC_NEW_COMER_GIFT,
		MessageType.GC_STATE_ROOM_SINGLE_SWING_BEGIN,
		MessageType.GC_COW_SINGLE_SWING_END,
		MessageType.GC_TEXAS_GIVE_UP,
		MessageType.GC_TEXAS_SETTLE_GIVEUP,
		MessageType.GC_HUNAMN_PROGRESS_SINGLE,
		MessageType.GC_TEXAS_SETTLE,
		MessageType.GC_FREE_SLOT_REWARD,
		MessageType.GC_COW_SINGLE_SWING,
		MessageType.GC_HUMAN_MONTH_CARD_INFO_DATA,
		MessageType.GC_BAZOO_TASK,
		MessageType.GC_TEXAS_ADD_BET,
		MessageType.GC_SLOT_TYPE38_WILD,
		MessageType.GC_BACCART_SYNC_JOIN,
		MessageType.GC_ROOM_PRI_List,
		MessageType.GC_HUMAN_BACCART,
		MessageType.GC_STATE_ROOM_BLACK_WHITE_END,
		MessageType.GC_WEEK_CARD_GET,
		MessageType.GC_CLUB_LIST,
		MessageType.GC_LUCKY_SPIN_INFO_DATA,
		MessageType.GC_SLOT_TYPE21_BOUNS,
		MessageType.GC_SLOT_ROOM_GIFT,
		MessageType.GC_CLUB_JOIN2,
	};
	public static void fill(Map<Short, Class<?>> msgs) {
		
		msgs.put(MessageType.GC_CLUB_CHANGE_NAME, GCClubChangeName.class);
		msgs.put(MessageType.GC_CLUB_SIGN, GCClubSign.class);
		msgs.put(MessageType.GC_NOTICE_INFO_DATA, GCNoticeInfoData.class);
		msgs.put(MessageType.GC_BACCART_REVIVE, GCBaccartRevive.class);
		msgs.put(MessageType.GC_FB_INVITE_GET_REWARD, GCFbInviteGetReward.class);
		msgs.put(MessageType.GC_BAZOO_GET_REWARD, GCBazooGetReward.class);
		msgs.put(MessageType.GC_COW_END_UNIFY_SWING, GCCowEndUnifySwing.class);
		msgs.put(MessageType.GC_SLOT_TYPE33_BONUS_LIST, GCSlotType33BonusList.class);
		msgs.put(MessageType.GC_BAZOO_SEND_GIFT, GCBazooSendGift.class);
		msgs.put(MessageType.GC_SLOT_TOURNAMENT_START_DATA, GCSlotTournamentStartData.class);
		msgs.put(MessageType.GC_SLOT_TYPE25_BOUNS_INFO, GCSlotType25BounsInfo.class);
		msgs.put(MessageType.GC_SLOT_TYPE26_BOUNS, GCSlotType26Bouns.class);
		msgs.put(MessageType.GC_SNG_RANK_INFO, GCSngRankInfo.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_BONUS_ONE, GCSlotType31BonusOne.class);
		msgs.put(MessageType.GC_UPDATE_HUMAN_ACTIVITY_REWARD, GCUpdateHumanActivityReward.class);
		msgs.put(MessageType.GC_REQUEST_GIFT, GCRequestGift.class);
		msgs.put(MessageType.GC_QUERY_PLAYER_INFO_NAME, GCQueryPlayerInfoName.class);
		msgs.put(MessageType.GC_HUMAN_JACKPOT_REWARD, GCHumanJackpotReward.class);
		msgs.put(MessageType.GC_SNG_RANK, GCSngRank.class);
		msgs.put(MessageType.GC_GAMETYPE_JACKPOT, GCGametypeJackpot.class);
		msgs.put(MessageType.GC_MONTH_CARD_GET, GCMonthCardGet.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_LEFT_BULLET_NUM, GCSlotType32LeftBulletNum.class);
		msgs.put(MessageType.GC_HANDSHAKE, GCHandshake.class);
		msgs.put(MessageType.GC_MONTH_OR_WEEK, GCMonthOrWeek.class);
		msgs.put(MessageType.GC_BAZOO_CHANGE_NAME, GCBazooChangeName.class);
		msgs.put(MessageType.GC_SHOP_LIST, GCShopList.class);
		msgs.put(MessageType.GC_HUMAN_TEXAS_INFO_DATA_SEARCH, GCHumanTexasInfoDataSearch.class);
		msgs.put(MessageType.GC_SLOT_TYPE20_BOUNS_NEW, GCSlotType20BounsNew.class);
		msgs.put(MessageType.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT, GCStateRoomBlackWhiteSwingLeft.class);
		msgs.put(MessageType.GC_BACCART_CLEAR_TABLE, GCBaccartClearTable.class);
		msgs.put(MessageType.GC_CLUB_INVATE, GCClubInvate.class);
		msgs.put(MessageType.GC_ROOM_ENTER, GCRoomEnter.class);
		msgs.put(MessageType.GC_TEXAS_SMALL_BLIND, GCTexasSmallBlind.class);
		msgs.put(MessageType.GC_CLUB_EDIT, GCClubEdit.class);
		msgs.put(MessageType.GC_SLOT_TYPE38_JACKPOT, GCSlotType38Jackpot.class);
		msgs.put(MessageType.GC_SLOT_NEWBIE, GCSlotNewbie.class);
		msgs.put(MessageType.GC_SLOT_ENTER, GCSlotEnter.class);
		msgs.put(MessageType.GC_SLOT_TYPE25_WILD_INFO, GCSlotType25WildInfo.class);
		msgs.put(MessageType.GC_CLUB_INVATE_LIST, GCClubInvateList.class);
		msgs.put(MessageType.GC_SLOT_TYPE13_SEND_BOUNS, GCSlotType13SendBouns.class);
		msgs.put(MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE_TO_ALL, GCBazooItemClockChangeToAll.class);
		msgs.put(MessageType.GC_COW_SINGLE_SWING_BEGIN, GCCowSingleSwingBegin.class);
		msgs.put(MessageType.GC_HUMAN_CHANGE_IMG, GCHumanChangeImg.class);
		msgs.put(MessageType.GC_HUMAN_TEXAS_SNG_INFO_DATA, GCHumanTexasSngInfoData.class);
		msgs.put(MessageType.GC_SLOT_TYPE12_FREE, GCSlotType12Free.class);
		msgs.put(MessageType.GC_STATE_ROOM_ROUND_BEGIN, GCStateRoomRoundBegin.class);
		msgs.put(MessageType.GC_DEAL_WITH_REWARD, GCDealWithReward.class);
		msgs.put(MessageType.GC_PING, GCPing.class);
		msgs.put(MessageType.GC_SPIN_ZHUANPAN_FREE, GCSpinZhuanpanFree.class);
		msgs.put(MessageType.GC_CARD_EXCHANGE, GCCardExchange.class);
		msgs.put(MessageType.GC_HUMAN_CHANGE_NAME, GCHumanChangeName.class);
		msgs.put(MessageType.GC_DAILY_TASK_GET, GCDailyTaskGet.class);
		msgs.put(MessageType.GC_TEXAS_BIG_BLIND, GCTexasBigBlind.class);
		msgs.put(MessageType.GC_SHOW_HAND_SINGLE_SWICH, GCShowHandSingleSwich.class);
		msgs.put(MessageType.GC_BAZOO_ITEM_REQUEST, GCBazooItemRequest.class);
		msgs.put(MessageType.GC_SAVE_POINT, GCSavePoint.class);
		msgs.put(MessageType.GC_STATE_ROOM_ROUND_GUESS, GCStateRoomRoundGuess.class);
		msgs.put(MessageType.GC_HUMAN_CHANGE_VIP, GCHumanChangeVip.class);
		msgs.put(MessageType.GC_SLOT_ERROR, GCSlotError.class);
		msgs.put(MessageType.GC_NEW_JACKPOT, GCNewJackpot.class);
		msgs.put(MessageType.GC_JACKPOT_LIST_DATA, GCJackpotListData.class);
		msgs.put(MessageType.GC_BAZOO_HALL_STATUS, GCBazooHallStatus.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_BONUS_TWO, GCSlotType31BonusTwo.class);
		msgs.put(MessageType.GC_HUMAN_CHANGE_SEX, GCHumanChangeSex.class);
		msgs.put(MessageType.GC_STATE_ROOM_ROUND_OPEN, GCStateRoomRoundOpen.class);
		msgs.put(MessageType.GC_RECEIVE_ALL, GCReceiveAll.class);
		msgs.put(MessageType.GC_HUMAN_ACTIVITY_REWARD_DATA_LIST, GCHumanActivityRewardDataList.class);
		msgs.put(MessageType.GC_BAZOO_RANK_REQUEST, GCBazooRankRequest.class);
		msgs.put(MessageType.GC_BLACK_WHITE_ALL_SWING, GCBlackWhiteAllSwing.class);
		msgs.put(MessageType.GC_HUMAN_VIDEO_NUM, GCHumanVideoNum.class);
		msgs.put(MessageType.GC_TASK_INFO_DATA, GCTaskInfoData.class);
		msgs.put(MessageType.GC_FACEBOOK_FRIENDS_SYNC, GCFacebookFriendsSync.class);
		msgs.put(MessageType.GC_LOAD_FRIEND_REQUEST_LIST, GCLoadFriendRequestList.class);
		msgs.put(MessageType.GC_ACTIVITY_LIST, GCActivityList.class);
		msgs.put(MessageType.GC_BACCART_COMPLEMENT, GCBaccartComplement.class);
		msgs.put(MessageType.GC_BACCART_COMPLEMENT_TIP, GCBaccartComplementTip.class);
		msgs.put(MessageType.GC_CLUB_INFO_GET, GCClubInfoGet.class);
		msgs.put(MessageType.GC_CHECK_PLAYER_LOGIN, GCCheckPlayerLogin.class);
		msgs.put(MessageType.GC_HUMAN_RANK, GCHumanRank.class);
		msgs.put(MessageType.GC_TEXAS_COMPLEMENT, GCTexasComplement.class);
		msgs.put(MessageType.GC_SIGN_IN, GCSignIn.class);
		msgs.put(MessageType.GC_BLACK_WHITE_END_COUNT, GCBlackWhiteEndCount.class);
		msgs.put(MessageType.GC_TEXAS_HAND_CARD, GCTexasHandCard.class);
		msgs.put(MessageType.GC_SYSTEM_MESSAGE, GCSystemMessage.class);
		msgs.put(MessageType.GC_TEXAS_EXIT, GCTexasExit.class);
		msgs.put(MessageType.GC_SLOT_ROOM_PLEASE, GCSlotRoomPlease.class);
		msgs.put(MessageType.GC_BACCART_AUTO, GCBaccartAuto.class);
		msgs.put(MessageType.GC_ORDER_AMAZON_DELIVERY, GCOrderAmazonDelivery.class);
		msgs.put(MessageType.GC_NEW_USER, GCNewUser.class);
		msgs.put(MessageType.GC_REQUEST_FRIEND_SYNC, GCRequestFriendSync.class);
		msgs.put(MessageType.GC_MISC_FB_INFO_DATA, GCMiscFbInfoData.class);
		msgs.put(MessageType.GC_ROOM_ENTER_NOT_ALLOW, GCRoomEnterNotAllow.class);
		msgs.put(MessageType.GC_GET_SLOT_CACHEMSG, GCGetSlotCachemsg.class);
		msgs.put(MessageType.GC_SLOT_TYPE25_BOUNS, GCSlotType25Bouns.class);
		msgs.put(MessageType.GC_COUPON_EXIST, GCCouponExist.class);
		msgs.put(MessageType.GC_CLUB_SEARCH_RESULT, GCClubSearchResult.class);
		msgs.put(MessageType.GC_SLOT_TYPE30_BOUNS, GCSlotType30Bouns.class);
		msgs.put(MessageType.GC_SLOT_TYPE24_BOUNS_BAR, GCSlotType24BounsBar.class);
		msgs.put(MessageType.GC_GET_GIVEALIKE_INFO, GCGetGivealikeInfo.class);
		msgs.put(MessageType.GC_COMMON_RANK, GCCommonRank.class);
		msgs.put(MessageType.GC_VIP_CREATE_ROOM, GCVipCreateRoom.class);
		msgs.put(MessageType.GC_TEXAS_FLOP, GCTexasFlop.class);
		msgs.put(MessageType.GC_SLOT_TYPE26_WILD_INFO, GCSlotType26WildInfo.class);
		msgs.put(MessageType.GC_BAZOO_ITEM_BUY_BY_GOLD, GCBazooItemBuyByGold.class);
		msgs.put(MessageType.GC_JACKPOT_LEVEL_DATA, GCJackpotLevelData.class);
		msgs.put(MessageType.GC_SHOW_HAND_SINGLE_SWICH_CANCEL, GCShowHandSingleSwichCancel.class);
		msgs.put(MessageType.GC_TEXAS_COINS_SYNC, GCTexasCoinsSync.class);
		msgs.put(MessageType.GC_FB_THUMB_REWARD, GCFbThumbReward.class);
		msgs.put(MessageType.GC_VIP_BUY, GCVipBuy.class);
		msgs.put(MessageType.GC_SLOT_TYPE29_BOUNS, GCSlotType29Bouns.class);
		msgs.put(MessageType.GC_SIGN_IN_INFO_DATA, GCSignInInfoData.class);
		msgs.put(MessageType.GC_CLUB_RANKING_LIST, GCClubRankingList.class);
		msgs.put(MessageType.GC_TEXAS_BUTTOM_DEAL, GCTexasButtomDeal.class);
		msgs.put(MessageType.GC_SLOT_TYPE14_RUNE_BONUS, GCSlotType14RuneBonus.class);
		msgs.put(MessageType.GC_CLUB_MEMBER_LIST, GCClubMemberList.class);
		msgs.put(MessageType.GC_SHOW_HAND_END_COUNT, GCShowHandEndCount.class);
		msgs.put(MessageType.GC_MISC_INFO_DATA, GCMiscInfoData.class);
		msgs.put(MessageType.GC_COLLECT_INIT, GCCollectInit.class);
		msgs.put(MessageType.GC_SEND_INTERACTIVE_ITEM, GCSendInteractiveItem.class);
		msgs.put(MessageType.GC_HUMAN_WEEK_CARD_INFO_DATA, GCHumanWeekCardInfoData.class);
		msgs.put(MessageType.GC_CLUB_TANHE_VOTE, GCClubTanheVote.class);
		msgs.put(MessageType.GC_TEXAS_LIST, GCTexasList.class);
		msgs.put(MessageType.GC_ISNOT_GIVEALIKE, GCIsnotGivealike.class);
		msgs.put(MessageType.GC_VIP_GET, GCVipGet.class);
		msgs.put(MessageType.GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT, GCStateRoomShowHandRoundResult.class);
		msgs.put(MessageType.GC_TEXAS_BANKER_POS, GCTexasBankerPos.class);
		msgs.put(MessageType.GC_SLOT_TYPE20_BOUNS, GCSlotType20Bouns.class);
		msgs.put(MessageType.GC_SLOT_TYPE24_BOUNS_SAMBA, GCSlotType24BounsSamba.class);
		msgs.put(MessageType.GC_SLOT_TYPE28_BOUNS_INFO, GCSlotType28BounsInfo.class);
		msgs.put(MessageType.GC_CLIENT_VERSION, GCClientVersion.class);
		msgs.put(MessageType.GC_DICE_USER_SHOULD_SWING, GCDiceUserShouldSwing.class);
		msgs.put(MessageType.GC_FRIEND_GAME, GCFriendGame.class);
		msgs.put(MessageType.GC_ROOM_MATCHED, GCRoomMatched.class);
		msgs.put(MessageType.GC_CLUB_KICK, GCClubKick.class);
		msgs.put(MessageType.GC_GET_RANK_INFO, GCGetRankInfo.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_SOCIAL_CONTACT, GCSlotType32SocialContact.class);
		msgs.put(MessageType.GC_SLOT_TYPE24_BOUNS_GAME_START, GCSlotType24BounsGameStart.class);
		msgs.put(MessageType.GC_HUMAN_VIP_INFO_DATA, GCHumanVipInfoData.class);
		msgs.put(MessageType.GC_TEXAS_SNG_LIST, GCTexasSngList.class);
		msgs.put(MessageType.GC_BACCART_SEAT, GCBaccartSeat.class);
		msgs.put(MessageType.GC_GROUP_SEND_INTERACTIVE_ITEM, GCGroupSendInteractiveItem.class);
		msgs.put(MessageType.GC_TEXAS_CLEAR_TABLE, GCTexasClearTable.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_SPECIAL_LIST, GCSlotType32SpecialList.class);
		msgs.put(MessageType.GC_UPDATE_MAIL_LIST, GCUpdateMailList.class);
		msgs.put(MessageType.GC_BLACK_WHITE_WHO_TURN, GCBlackWhiteWhoTurn.class);
		msgs.put(MessageType.GC_BAZOO_ITEM_CLOCK_CHANGE, GCBazooItemClockChange.class);
		msgs.put(MessageType.GC_SLOT_TYPE12_CHOOSE, GCSlotType12Choose.class);
		msgs.put(MessageType.GC_CLUB_APPLY_OP, GCClubApplyOp.class);
		msgs.put(MessageType.GC_RAFFLE, GCRaffle.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_WILD_INFO, GCSlotType32WildInfo.class);
		msgs.put(MessageType.GC_ROOM_PRI_SEARCH, GCRoomPriSearch.class);
		msgs.put(MessageType.GC_SLOT_TYPE10_SCATTER, GCSlotType10Scatter.class);
		msgs.put(MessageType.GC_BACCART_SETTLE, GCBaccartSettle.class);
		msgs.put(MessageType.GC_DELETE_FRIEND, GCDeleteFriend.class);
		msgs.put(MessageType.GC_STATE_ROOM_ROUND_RESULT, GCStateRoomRoundResult.class);
		msgs.put(MessageType.GC_SLOT_TYPE14_PREY_BONUS, GCSlotType14PreyBonus.class);
		msgs.put(MessageType.GC_SLOT_LIST, GCSlotList.class);
		msgs.put(MessageType.GC_TEXAS_TURN, GCTexasTurn.class);
		msgs.put(MessageType.GC_DELETE_MAIL, GCDeleteMail.class);
		msgs.put(MessageType.GC_REMOVE_SLOT_SLOT, GCRemoveSlotSlot.class);
		msgs.put(MessageType.GC_CLUB_APPLY_LIST, GCClubApplyList.class);
		msgs.put(MessageType.GC_ENTER_SCENE, GCEnterScene.class);
		msgs.put(MessageType.GC_CLUB_JOIN_RESULT, GCClubJoinResult.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_BULLET_IN, GCSlotType32BulletIn.class);
		msgs.put(MessageType.GC_ROBOT_WHICH_ROOM_TO_GOIN, GCRobotWhichRoomToGoin.class);
		msgs.put(MessageType.GC_ACH_INFO, GCAchInfo.class);
		msgs.put(MessageType.GC_RECEIVE_ACH, GCReceiveAch.class);
		msgs.put(MessageType.GC_HUMAN_TEXAS_INFO_DATA, GCHumanTexasInfoData.class);
		msgs.put(MessageType.GC_SHOW_HAND_UNIFY_SWING, GCShowHandUnifySwing.class);
		msgs.put(MessageType.GC_BAZOO_MAGIC_FACE, GCBazooMagicFace.class);
		msgs.put(MessageType.GC_HUMAN_TEXAS_EXP, GCHumanTexasExp.class);
		msgs.put(MessageType.GC_BACCART_LIGHT, GCBaccartLight.class);
		msgs.put(MessageType.GC_VIP_NEW_DATA, GCVipNewData.class);
		msgs.put(MessageType.GC_SLOT_TYPE3_BOUNS_START, GCSlotType3BounsStart.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_WILD_INFO, GCSlotType31WildInfo.class);
		msgs.put(MessageType.GC_JOIN_TEXAS, GCJoinTexas.class);
		msgs.put(MessageType.GC_BACCART_LIST, GCBaccartList.class);
		msgs.put(MessageType.GC_ROOM_MATCHEDING, GCRoomMatcheding.class);
		msgs.put(MessageType.GC_TEXAS_RIVER, GCTexasRiver.class);
		msgs.put(MessageType.GC_TEXAS_SEAT, GCTexasSeat.class);
		msgs.put(MessageType.GC_SELF_ATTACK_BLOOD_INFO, GCSelfAttackBloodInfo.class);
		msgs.put(MessageType.GC_SLOT_TYPE28_BOUNS, GCSlotType28Bouns.class);
		msgs.put(MessageType.GC_SEND_MAIL, GCSendMail.class);
		msgs.put(MessageType.GC_TEXAS_ROOM_INFO, GCTexasRoomInfo.class);
		msgs.put(MessageType.GC_TALK_BIG, GCTalkBig.class);
		msgs.put(MessageType.GC_QUERY_PLAYER_INFO, GCQueryPlayerInfo.class);
		msgs.put(MessageType.GC_ROLE_SYMBOL_CHANGED_LONG, GCRoleSymbolChangedLong.class);
		msgs.put(MessageType.GC_STATE_ROOM_SHOW_HAND_ALL_SWING, GCStateRoomShowHandAllSwing.class);
		msgs.put(MessageType.GC_CLUB_SIGN_DATA, GCClubSignData.class);
		msgs.put(MessageType.GC_KOREAN_SB, GCKoreanSb.class);
		msgs.put(MessageType.GC_JOIN_TEXAS_VIP_FAILED, GCJoinTexasVipFailed.class);
		msgs.put(MessageType.GC_TEXAS_FOLLOW, GCTexasFollow.class);
		msgs.put(MessageType.GC_VALIDATE_ORDER, GCValidateOrder.class);
		msgs.put(MessageType.GC_SLOT_TYPE23_BOUNS_INFO, GCSlotType23BounsInfo.class);
		msgs.put(MessageType.GC_CLUB_LEAVE, GCClubLeave.class);
		msgs.put(MessageType.GC_SLOT_TYPE28_SCATTER_INFO, GCSlotType28ScatterInfo.class);
		msgs.put(MessageType.GC_TASK_PROGRESS, GCTaskProgress.class);
		msgs.put(MessageType.GC_PAY_GUIDE, GCPayGuide.class);
		msgs.put(MessageType.GC_HUMAN_BAG_INFO_DATA, GCHumanBagInfoData.class);
		msgs.put(MessageType.GC_ROOM_INIT, GCRoomInit.class);
		msgs.put(MessageType.GC_LEVEL_GIFT_MORE, GCLevelGiftMore.class);
		msgs.put(MessageType.GC_GUESS_OPEN, GCGuessOpen.class);
		msgs.put(MessageType.GC_STILL_HAVE_ACTIVITY_GOLD, GCStillHaveActivityGold.class);
		msgs.put(MessageType.GC_STATE_ROOM_CALL_DICE, GCStateRoomCallDice.class);
		msgs.put(MessageType.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE, GCStateRoomBlackWhiteWaitSomeOne.class);
		msgs.put(MessageType.GC_STATE_ROOM_ENTER, GCStateRoomEnter.class);
		msgs.put(MessageType.GC_CLUB_DONATE, GCClubDonate.class);
		msgs.put(MessageType.GC_BAZOO_BOQU, GCBazooBoqu.class);
		msgs.put(MessageType.GC_BOSS_START_END_INFO, GCBossStartEndInfo.class);
		msgs.put(MessageType.GC_BAZOO_ACHIEVE, GCBazooAchieve.class);
		msgs.put(MessageType.GC_GET_SAVE_POINT, GCGetSavePoint.class);
		msgs.put(MessageType.GC_OBTAIN_COUPON, GCObtainCoupon.class);
		msgs.put(MessageType.GC_TASK_INFO_DATA_CHANGE, GCTaskInfoDataChange.class);
		msgs.put(MessageType.GC_GET_VOUCHERS, GCGetVouchers.class);
		msgs.put(MessageType.GC_SLOT_TYPE24_SEND_BOUNS, GCSlotType24SendBouns.class);
		msgs.put(MessageType.GC_APPLY_FRIEND, GCApplyFriend.class);
		msgs.put(MessageType.GC_BACCART_JOIN, GCBaccartJoin.class);
		msgs.put(MessageType.GC_RANK_LIST, GCRankList.class);
		msgs.put(MessageType.GC_BUY_ITEM, GCBuyItem.class);
		msgs.put(MessageType.GC_LOAD_MAIL_LIST, GCLoadMailList.class);
		msgs.put(MessageType.GC_BAZOO_RED_PACKAGE, GCBazooRedPackage.class);
		msgs.put(MessageType.GC_BAZOO_ACHIEVE_TASK, GCBazooAchieveTask.class);
		msgs.put(MessageType.GC_SLOT_TYPE14_BONUS, GCSlotType14Bonus.class);
		msgs.put(MessageType.GC_ALL_SLOT_NEW_JACKPOTS, GCAllSlotNewJackpots.class);
		msgs.put(MessageType.GC_BACCART_EXIT, GCBaccartExit.class);
		msgs.put(MessageType.GC_LEAVE_TEXAS, GCLeaveTexas.class);
		msgs.put(MessageType.GC_ORDER_INFO_DATA_LIST, GCOrderInfoDataList.class);
		msgs.put(MessageType.GC_LUCKY_SPIN, GCLuckySpin.class);
		msgs.put(MessageType.GC_END_COUNT, GCEndCount.class);
		msgs.put(MessageType.GC_BLACK_WHITE_CALL_NUM, GCBlackWhiteCallNum.class);
		msgs.put(MessageType.GC_HUMAN_BACCART_COINS_SYNC, GCHumanBaccartCoinsSync.class);
		msgs.put(MessageType.GC_CHAT_MSG, GCChatMsg.class);
		msgs.put(MessageType.GC_NOTICE_INFO_DATA_MULTI, GCNoticeInfoDataMulti.class);
		msgs.put(MessageType.GC_SLOT_NEW_JACKPOTS, GCSlotNewJackpots.class);
		msgs.put(MessageType.GC_SLOT_TYPE30_BOUNS_INFO, GCSlotType30BounsInfo.class);
		msgs.put(MessageType.GC_BAZOO_MALL_REQUEST, GCBazooMallRequest.class);
		msgs.put(MessageType.GC_BACCART_JACKPOT, GCBaccartJackpot.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_BONUS, GCSlotType31Bonus.class);
		msgs.put(MessageType.GC_ROB_OPEN, GCRobOpen.class);
		msgs.put(MessageType.GC_ROTARY_TABLE, GCRotaryTable.class);
		msgs.put(MessageType.GC_CLUB_DONATE_DATA, GCClubDonateData.class);
		msgs.put(MessageType.GC_DOUBLE_TURN, GCDoubleTurn.class);
		msgs.put(MessageType.GC_COW_UNIFY_SWING, GCCowUnifySwing.class);
		msgs.put(MessageType.GC_REFRESH_BOSS_INFO, GCRefreshBossInfo.class);
		msgs.put(MessageType.GC_SLOT_TYPE24_BOUNS, GCSlotType24Bouns.class);
		msgs.put(MessageType.GC_MOL_ORDER, GCMolOrder.class);
		msgs.put(MessageType.GC_BACCART_BET, GCBaccartBet.class);
		msgs.put(MessageType.GC_FRIEND_GIFT_GET, GCFriendGiftGet.class);
		msgs.put(MessageType.GC_SLOT_TYPE15_BOUNS, GCSlotType15Bouns.class);
		msgs.put(MessageType.GC_TEXAS_VIP_LIST, GCTexasVipList.class);
		msgs.put(MessageType.GC_FRIEND_GIFT, GCFriendGift.class);
		msgs.put(MessageType.GC_BACCART_SHUFFLE, GCBaccartShuffle.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_BONUS_THREE, GCSlotType31BonusThree.class);
		msgs.put(MessageType.GC_GET_ACTIVITY_REWARD, GCGetActivityReward.class);
		msgs.put(MessageType.GC_STRANGER_LIST, GCStrangerList.class);
		msgs.put(MessageType.GC_MYCARD_AUTHCODE, GCMycardAuthcode.class);
		msgs.put(MessageType.GC_ROOM_BE_REMOVEED, GCRoomBeRemoveed.class);
		msgs.put(MessageType.GC_SLOT_TYPE27_BOUNS_INFO, GCSlotType27BounsInfo.class);
		msgs.put(MessageType.GC_HUMAN_DETAIL_INFO, GCHumanDetailInfo.class);
		msgs.put(MessageType.GC_BEFORE_START, GCBeforeStart.class);
		msgs.put(MessageType.GC_ADD_FRIEND, GCAddFriend.class);
		msgs.put(MessageType.GC_SPIN_ZHUANPAN_NOFREE, GCSpinZhuanpanNofree.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_BONUS, GCSlotType32Bonus.class);
		msgs.put(MessageType.GC_TOURNAMENT_GET_LIST, GCTournamentGetList.class);
		msgs.put(MessageType.GC_STATE_ROOM_ROUND_TURN, GCStateRoomRoundTurn.class);
		msgs.put(MessageType.GC_SLOT_TYPE38_BONUS_TRIGGER, GCSlotType38BonusTrigger.class);
		msgs.put(MessageType.GC_SLOT_TYPE38_BONUS, GCSlotType38Bonus.class);
		msgs.put(MessageType.GC_READ_MAIL, GCReadMail.class);
		msgs.put(MessageType.GC_SHOW_HAND_LITTLE_SWING, GCShowHandLittleSwing.class);
		msgs.put(MessageType.GC_CONVERSION_CODE, GCConversionCode.class);
		msgs.put(MessageType.GC_TEXAS_PLAYER_TURN, GCTexasPlayerTurn.class);
		msgs.put(MessageType.GC_GUESS_BIG_SMALL, GCGuessBigSmall.class);
		msgs.put(MessageType.GC_CLUB_INFO, GCClubInfo.class);
		msgs.put(MessageType.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING, GCStateRoomShowHandSingleSwing.class);
		msgs.put(MessageType.GC_BAZOO_SIGNIN, GCBazooSignin.class);
		msgs.put(MessageType.GC_BACCART_PLAYER_JACKPOT, GCBaccartPlayerJackpot.class);
		msgs.put(MessageType.GC_MODE_CHOSE, GCModeChose.class);
		msgs.put(MessageType.GC_BIG_ZHUANPAN, GCBigZhuanpan.class);
		msgs.put(MessageType.GC_CLUB_NOTE_LIST, GCClubNoteList.class);
		msgs.put(MessageType.GC_FB_GET_REWARD, GCFbGetReward.class);
		msgs.put(MessageType.GC_FRIEND_GIFT_SYNC, GCFriendGiftSync.class);
		msgs.put(MessageType.GC_SLOT_GET_RANK, GCSlotGetRank.class);
		msgs.put(MessageType.GC_RETURN_BLOOD, GCReturnBlood.class);
		msgs.put(MessageType.GC_DICE_SINGLE_SWING, GCDiceSingleSwing.class);
		msgs.put(MessageType.GC_TREASURY, GCTreasury.class);
		msgs.put(MessageType.GC_CLUB_NOTE_DELETE, GCClubNoteDelete.class);
		msgs.put(MessageType.GC_TEXAS_TIPS, GCTexasTips.class);
		msgs.put(MessageType.GC_STATE_ROOM_MATCHING, GCStateRoomMatching.class);
		msgs.put(MessageType.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL, GCStateRoomBlackWhiteSomeOneCall.class);
		msgs.put(MessageType.GC_SLOT_TYPE23_INIT_REWARD, GCSlotType23InitReward.class);
		msgs.put(MessageType.GC_FB_INVITE, GCFbInvite.class);
		msgs.put(MessageType.GC_SLOT_TYPE38_PUMPKIN, GCSlotType38Pumpkin.class);
		msgs.put(MessageType.GC_ROOM_STATE, GCRoomState.class);
		msgs.put(MessageType.GC_SLOT_TYPE26_BOUNS_INFO, GCSlotType26BounsInfo.class);
		msgs.put(MessageType.GC_SLOT_SLOT, GCSlotSlot.class);
		msgs.put(MessageType.GC_CLUB_TANHE_STATE, GCClubTanheState.class);
		msgs.put(MessageType.GC_CLUB_GET_GIFT, GCClubGetGift.class);
		msgs.put(MessageType.GC_SLOT_TYPE32_BULLET_OUT, GCSlotType32BulletOut.class);
		msgs.put(MessageType.GC_REQUEST_FRIEND, GCRequestFriend.class);
		msgs.put(MessageType.GC_SLOT_TYPE31_SPECIFIC_WILD_INFO, GCSlotType31SpecificWildInfo.class);
		msgs.put(MessageType.GC_BACCART_START_BET, GCBaccartStartBet.class);
		msgs.put(MessageType.GC_SLOT_TYPE14_APPLE_BONUS, GCSlotType14AppleBonus.class);
		msgs.put(MessageType.GC_GET_BOSS_INFO, GCGetBossInfo.class);
		msgs.put(MessageType.GC_ONLINE_REWARD, GCOnlineReward.class);
		msgs.put(MessageType.GC_STATE_ROOM_SINGLE_SWING_END, GCStateRoomSingleSwingEnd.class);
		msgs.put(MessageType.GC_DICE_UNIFY_SWING, GCDiceUnifySwing.class);
		msgs.put(MessageType.GC_SLOT_TYPE29_WILD_INFO, GCSlotType29WildInfo.class);
		msgs.put(MessageType.GC_EXP_DOUBLE, GCExpDouble.class);
		msgs.put(MessageType.GC_REQUEST_ORDER_THIRD_PARTY, GCRequestOrderThirdParty.class);
		msgs.put(MessageType.GC_CHANEAGE_COUNTRIES, GCChaneageCountries.class);
		msgs.put(MessageType.GC_SLOT_TYPE20, GCSlotType20.class);
		msgs.put(MessageType.GC_SLOT_TYPE22, GCSlotType22.class);
		msgs.put(MessageType.GC_LOAD_FRIEND_GIFT_LIST, GCLoadFriendGiftList.class);
		msgs.put(MessageType.GC_SLOT_TYPE13_BOUNS, GCSlotType13Bouns.class);
		msgs.put(MessageType.GC_ROOM_CREATE, GCRoomCreate.class);
		msgs.put(MessageType.GC_SLOT_TYPE21_BOUNS_INFO, GCSlotType21BounsInfo.class);
		msgs.put(MessageType.GC_ROOM_PRI_JOIN, GCRoomPriJoin.class);
		msgs.put(MessageType.GC_SLOT_TYPE16, GCSlotType16.class);
		msgs.put(MessageType.GC_SLOT_TYPE17, GCSlotType17.class);
		msgs.put(MessageType.GC_SLOT_TYPE18, GCSlotType18.class);
		msgs.put(MessageType.GC_SLOT_TYPE19, GCSlotType19.class);
		msgs.put(MessageType.GC_TEXAS_PEOPLE_SETTING, GCTexasPeopleSetting.class);
		msgs.put(MessageType.GC_TEXAS_LOOK, GCTexasLook.class);
		msgs.put(MessageType.GC_FUNCTION_LEFT_TIME, GCFunctionLeftTime.class);
		msgs.put(MessageType.GC_SLOT_TYPE10, GCSlotType10.class);
		msgs.put(MessageType.GC_SLOT_ROOM3, GCSlotRoom3.class);
		msgs.put(MessageType.GC_SLOT_TYPE11, GCSlotType11.class);
		msgs.put(MessageType.GC_SLOT_ROOM4, GCSlotRoom4.class);
		msgs.put(MessageType.GC_SLOT_TYPE12, GCSlotType12.class);
		msgs.put(MessageType.GC_SLOT_ROOM1, GCSlotRoom1.class);
		msgs.put(MessageType.GC_TEXAS_ALL_IN, GCTexasAllIn.class);
		msgs.put(MessageType.GC_SLOT_ROOM2, GCSlotRoom2.class);
		msgs.put(MessageType.GC_SLOT_TYPE14, GCSlotType14.class);
		msgs.put(MessageType.GC_MONTH_WEEK_LEFT_TIME, GCMonthWeekLeftTime.class);
		msgs.put(MessageType.GC_LOAD_FRIEND_LIST, GCLoadFriendList.class);
		msgs.put(MessageType.GC_HUNAMN_PROGRESS, GCHunamnProgress.class);
		msgs.put(MessageType.GC_ROOM_OUT, GCRoomOut.class);
		msgs.put(MessageType.GC_ROBOT_DICE_UNIFY_SWING, GCRobotDiceUnifySwing.class);
		msgs.put(MessageType.GC_SLOT_TOURNAMENT_NOT_OPEN, GCSlotTournamentNotOpen.class);
		msgs.put(MessageType.GC_REQUEST_ORDER, GCRequestOrder.class);
		msgs.put(MessageType.GC_BAZOO_HEART_BEAT, GCBazooHeartBeat.class);
		msgs.put(MessageType.GC_SLOT_TYPE15_BOUNS_START, GCSlotType15BounsStart.class);
		msgs.put(MessageType.GC_SLOT_TYPE28_WILD_INFO, GCSlotType28WildInfo.class);
		msgs.put(MessageType.GC_UPDATE_ACTIVITY, GCUpdateActivity.class);
		msgs.put(MessageType.GC_NOTIFY_EXCEPTION, GCNotifyException.class);
		msgs.put(MessageType.GC_SLOT_ROOM5, GCSlotRoom5.class);
		msgs.put(MessageType.GC_BACCART_STAND, GCBaccartStand.class);
		msgs.put(MessageType.GC_SLOT_TYPE29_BOUNS_INFO, GCSlotType29BounsInfo.class);
		msgs.put(MessageType.GC_TEXAS_PREPARE_RESTART, GCTexasPrepareRestart.class);
		msgs.put(MessageType.GC_TEXAS_COMPLEMENT_NUM, GCTexasComplementNum.class);
		msgs.put(MessageType.GC_WINNER_WHEEL, GCWinnerWheel.class);
		msgs.put(MessageType.GC_CLUB_PROMATE, GCClubPromate.class);
		msgs.put(MessageType.GC_LUCKY_MATCH, GCLuckyMatch.class);
		msgs.put(MessageType.GC_SLOT_RANK_LIST, GCSlotRankList.class);
		msgs.put(MessageType.GC_CHARM_EXCHANGE, GCCharmExchange.class);
		msgs.put(MessageType.GC_STATE_ROOM_READY, GCStateRoomReady.class);
		msgs.put(MessageType.GC_SHOW_HAND_SINGLE_SWING, GCShowHandSingleSwing.class);
		msgs.put(MessageType.GC_SLOT_GET_REWARD, GCSlotGetReward.class);
		msgs.put(MessageType.GC_SYNC_JOIN_TEXAS, GCSyncJoinTexas.class);
		msgs.put(MessageType.GC_TEXAS_SIDE_POOL, GCTexasSidePool.class);
		msgs.put(MessageType.GC_NEW_COMER_GIFT, GCNewComerGift.class);
		msgs.put(MessageType.GC_STATE_ROOM_SINGLE_SWING_BEGIN, GCStateRoomSingleSwingBegin.class);
		msgs.put(MessageType.GC_COW_SINGLE_SWING_END, GCCowSingleSwingEnd.class);
		msgs.put(MessageType.GC_TEXAS_GIVE_UP, GCTexasGiveUp.class);
		msgs.put(MessageType.GC_TEXAS_SETTLE_GIVEUP, GCTexasSettleGiveup.class);
		msgs.put(MessageType.GC_HUNAMN_PROGRESS_SINGLE, GCHunamnProgressSingle.class);
		msgs.put(MessageType.GC_TEXAS_SETTLE, GCTexasSettle.class);
		msgs.put(MessageType.GC_FREE_SLOT_REWARD, GCFreeSlotReward.class);
		msgs.put(MessageType.GC_COW_SINGLE_SWING, GCCowSingleSwing.class);
		msgs.put(MessageType.GC_HUMAN_MONTH_CARD_INFO_DATA, GCHumanMonthCardInfoData.class);
		msgs.put(MessageType.GC_BAZOO_TASK, GCBazooTask.class);
		msgs.put(MessageType.GC_TEXAS_ADD_BET, GCTexasAddBet.class);
		msgs.put(MessageType.GC_SLOT_TYPE38_WILD, GCSlotType38Wild.class);
		msgs.put(MessageType.GC_BACCART_SYNC_JOIN, GCBaccartSyncJoin.class);
		msgs.put(MessageType.GC_ROOM_PRI_List, GCRoomPriList.class);
		msgs.put(MessageType.GC_HUMAN_BACCART, GCHumanBaccart.class);
		msgs.put(MessageType.GC_STATE_ROOM_BLACK_WHITE_END, GCStateRoomBlackWhiteEnd.class);
		msgs.put(MessageType.GC_WEEK_CARD_GET, GCWeekCardGet.class);
		msgs.put(MessageType.GC_CLUB_LIST, GCClubList.class);
		msgs.put(MessageType.GC_LUCKY_SPIN_INFO_DATA, GCLuckySpinInfoData.class);
		msgs.put(MessageType.GC_SLOT_TYPE21_BOUNS, GCSlotType21Bouns.class);
		msgs.put(MessageType.GC_SLOT_ROOM_GIFT, GCSlotRoomGift.class);
		msgs.put(MessageType.GC_CLUB_JOIN2, GCClubJoin2.class);
	}

}
