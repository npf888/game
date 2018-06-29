package com.logserver;

import org.apache.mina.common.ByteBuffer;

import com.core.msg.IMessage;
import com.core.msg.recognizer.IMessageRecognizer;
import com.core.msg.MessageParseException;
import com.logserver.model.GoldLog;
import com.logserver.model.ChatLog;
import com.logserver.model.VipLog;
import com.logserver.model.DiamondLog;
import com.logserver.model.WeekcardLog;
import com.logserver.model.MonthcardLog;
import com.logserver.model.SignInLog;
import com.logserver.model.RechargeLog;
import com.logserver.model.BasicPlayerLog;
import com.logserver.model.OnlineTimeRewardLog;
import com.logserver.model.OnlineTimeLog;
import com.logserver.model.DailyTaskLog;
import com.logserver.model.ExceptionLog;
import com.logserver.model.CharmLog;
import com.logserver.model.FriendLog;
import com.logserver.model.ItemLog;
import com.logserver.model.RenameLog;
import com.logserver.model.TexasRoomLog;
import com.logserver.model.BaccaratRoomLog;
import com.logserver.model.LuckySpinLog;
import com.logserver.model.SlotLog;
import com.logserver.model.DataOverviewLog;
import com.logserver.model.NewRechargeLog;
import com.logserver.model.PlayerLoginLog;
import com.logserver.model.PlayerOnleLog;
import com.logserver.model.PlayerKeepLog;
import com.logserver.model.SlotRoomLog;
import com.logserver.model.InOutTimeLog;
import com.logserver.model.JackpotLog;
import com.logserver.model.TournamentLog;
import com.logserver.model.WorldBossLog;
import com.logserver.model.StatisticsTimeLog;
import com.logserver.model.DiceClassicalRoomLog;
import com.logserver.model.DiceClassicalGuessLog;
import com.logserver.model.DiceClassicalCallNumLog;
import com.logserver.model.DiceClassicalBazooGoldLog;
import com.logserver.model.DiceCowLog;
import com.logserver.model.DiceShowHandLog;
import com.logserver.model.DiceSignInLog;
import com.logserver.model.DiceBlackWhiteLog;
import com.logserver.model.DiceStatisticsWinLostLog;

/**
 * This is an auto generated source,please don't modify it.
 */
public class LogMessageRecognizer implements IMessageRecognizer {


	@Override
	public int recognizePacketLen(final ByteBuffer buff) {
		// 消息头还未读到,返回null
		if (buff.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return -1;
		}
		return IMessage.Packet.peekPacketLength(buff);
	}


	public IMessage recognize(ByteBuffer buf) throws MessageParseException {
		/* 长度尚不足 */
		if (buf.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return null;
		}

		/* 长度不足实际命令 */
		int len = buf.getShort(); // 预期长度
		if (buf.remaining() < len - 2) {
			return null;
		}

		short type = buf.getShort();
		return createMessage(type);
	}

	public static IMessage createMessage(int type)
			throws MessageParseException {

		switch (type) {
			case MessageType.LOG_GOLD_RECORD: {
				return new GoldLog();
			}
			case MessageType.LOG_CHAT_RECORD: {
				return new ChatLog();
			}
			case MessageType.LOG_VIP_RECORD: {
				return new VipLog();
			}
			case MessageType.LOG_DIAMOND_RECORD: {
				return new DiamondLog();
			}
			case MessageType.LOG_WEEKCARD_RECORD: {
				return new WeekcardLog();
			}
			case MessageType.LOG_MONTHCARD_RECORD: {
				return new MonthcardLog();
			}
			case MessageType.LOG_SIGNIN_RECORD: {
				return new SignInLog();
			}
			case MessageType.LOG_RECHARGE_RECORD: {
				return new RechargeLog();
			}
			case MessageType.LOG_BASICPLAYER_RECORD: {
				return new BasicPlayerLog();
			}
			case MessageType.LOG_ONLINETIMEREWARD_RECORD: {
				return new OnlineTimeRewardLog();
			}
			case MessageType.LOG_ONLINETIME_RECORD: {
				return new OnlineTimeLog();
			}
			case MessageType.LOG_DAILYTASK_RECORD: {
				return new DailyTaskLog();
			}
			case MessageType.LOG_EXCEPTION_RECORD: {
				return new ExceptionLog();
			}
			case MessageType.LOG_CHARM_RECORD: {
				return new CharmLog();
			}
			case MessageType.LOG_FRIEND_RECORD: {
				return new FriendLog();
			}
			case MessageType.LOG_ITEM_RECORD: {
				return new ItemLog();
			}
			case MessageType.LOG_RENAME_RECORD: {
				return new RenameLog();
			}
			case MessageType.LOG_TEXASROOM_RECORD: {
				return new TexasRoomLog();
			}
			case MessageType.LOG_BACCARATROOM_RECORD: {
				return new BaccaratRoomLog();
			}
			case MessageType.LOG_LUCKYSPIN_RECORD: {
				return new LuckySpinLog();
			}
			case MessageType.LOG_SLOT_RECORD: {
				return new SlotLog();
			}
			case MessageType.LOG_DATAOVERVIEW_RECORD: {
				return new DataOverviewLog();
			}
			case MessageType.LOG_NEWRECHARGE_RECORD: {
				return new NewRechargeLog();
			}
			case MessageType.LOG_PLAYERLOGIN_RECORD: {
				return new PlayerLoginLog();
			}
			case MessageType.LOG_PLAYERONLE_RECORD: {
				return new PlayerOnleLog();
			}
			case MessageType.LOG_PLAYERKEEP_RECORD: {
				return new PlayerKeepLog();
			}
			case MessageType.LOG_SLOTROOM_RECORD: {
				return new SlotRoomLog();
			}
			case MessageType.LOG_INOUTTIME_RECORD: {
				return new InOutTimeLog();
			}
			case MessageType.LOG_JACKPOT_RECORD: {
				return new JackpotLog();
			}
			case MessageType.LOG_TOURNAMENT_RECORD: {
				return new TournamentLog();
			}
			case MessageType.LOG_WORLDBOSS_RECORD: {
				return new WorldBossLog();
			}
			case MessageType.LOG_STATISTICSTIME_RECORD: {
				return new StatisticsTimeLog();
			}
			case MessageType.LOG_DICECLASSICALROOM_RECORD: {
				return new DiceClassicalRoomLog();
			}
			case MessageType.LOG_DICECLASSICALGUESS_RECORD: {
				return new DiceClassicalGuessLog();
			}
			case MessageType.LOG_DICECLASSICALCALLNUM_RECORD: {
				return new DiceClassicalCallNumLog();
			}
			case MessageType.LOG_DICECLASSICALBAZOOGOLD_RECORD: {
				return new DiceClassicalBazooGoldLog();
			}
			case MessageType.LOG_DICECOW_RECORD: {
				return new DiceCowLog();
			}
			case MessageType.LOG_DICESHOWHAND_RECORD: {
				return new DiceShowHandLog();
			}
			case MessageType.LOG_DICESIGNIN_RECORD: {
				return new DiceSignInLog();
			}
			case MessageType.LOG_DICEBLACKWHITE_RECORD: {
				return new DiceBlackWhiteLog();
			}
			case MessageType.LOG_DICESTATISTICSWINLOST_RECORD: {
				return new DiceStatisticsWinLostLog();
			}

		default: {
			// TODO::考虑不要死机，可能要特殊处理
			throw new MessageParseException("Unrecognized message :" + type);
		}
		}

	}


	@Override
	public IMessage recognize(String json) throws MessageParseException {
		// TODO Auto-generated method stub
		return null;
	}

}