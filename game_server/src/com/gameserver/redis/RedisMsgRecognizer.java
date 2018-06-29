package com.gameserver.redis;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.common.InitializeRequired;
import com.core.msg.MessageParseException;
import com.gameserver.activity.redisMsg.ActivityRedisMessage;
import com.gameserver.activity.redisMsg.DeleteActivityRedisMessage;
import com.gameserver.baccart.redisMsg.BaccartRoomCloseRedisMessage;
import com.gameserver.baccart.redisMsg.BaccartRoomDataRedisMessage;
import com.gameserver.chat.redisMsg.ChatRedisMessage;
import com.gameserver.common.redismsg.PlayerLogin;
import com.gameserver.compensation.redisMsg.CompensationRedisMessage;
import com.gameserver.compensation.redisMsg.DeleteCompensationRedisMessage;
import com.gameserver.conversioncode.redisMsg.AddCodeMessage;
import com.gameserver.conversioncode.redisMsg.DeleteCodeMessage;
import com.gameserver.human.redismsg.RoomGift;
import com.gameserver.human.redismsg.RoomReq;
import com.gameserver.mail.redismsg.MailAddRedisMessage;
import com.gameserver.notice.redisMsg.NoticeRedisMessage;
import com.gameserver.recharge.redisMsg.DeliveryRedisMessage;
import com.gameserver.recharge.redisMsg.PaymentRedisMessage;
import com.gameserver.relation.redismsg.FriendAddRedisMessage;
import com.gameserver.relation.redismsg.FriendDeleteRedisMessage;
import com.gameserver.relation.redismsg.FriendGiftRedisMessage;
import com.gameserver.relation.redismsg.FriendRequestRedisMessage;
import com.gameserver.relation.redismsg.FriendSlotIdRoomId;
import com.gameserver.relation.redismsg.FriendSlotIdRoomIdRet;
import com.gameserver.slot.redismsg.AddJackpot;
import com.gameserver.slot.redismsg.ChangJackpot;
import com.gameserver.slot.redismsg.ChangJackpotNew;
import com.gameserver.slot.redismsg.ChangRoom;
import com.gameserver.slot.redismsg.ChangSpinTimes;
import com.gameserver.slot.redismsg.ChangStock;
import com.gameserver.slot.redismsg.ChangeInfo2;
import com.gameserver.slot.redismsg.ChangeInfo3;
import com.gameserver.slot.redismsg.ChangeInfo4;
import com.gameserver.slot.redismsg.EnterSlot;
import com.gameserver.slot.redismsg.EnterSlotRoom;
import com.gameserver.slot.redismsg.EnterSlotRoomReturn;
import com.gameserver.slot.redismsg.NotifyFriends;
import com.gameserver.slot.redismsg.OutRoom;
import com.gameserver.slot.redismsg.RoleRoom;
import com.gameserver.slot.redismsg.SynchSlot;

/**
 * redis 
 * @author wayne
 *
 */
public class RedisMsgRecognizer implements InitializeRequired {


	private Map<String,Class<?>> messageMapping = new HashMap<String,Class<?>>();
	
	private static RedisMsgRecognizer instance = new RedisMsgRecognizer();
	
	static{ 
		RedisMsgRecognizer.getInstance().registerMsg(FriendRequestRedisMessage.class);//
		RedisMsgRecognizer.getInstance().registerMsg(FriendDeleteRedisMessage.class);//
		RedisMsgRecognizer.getInstance().registerMsg(FriendAddRedisMessage.class);//
		RedisMsgRecognizer.getInstance().registerMsg(FriendGiftRedisMessage.class);//
		
		RedisMsgRecognizer.getInstance().registerMsg(FriendSlotIdRoomId.class);//
		RedisMsgRecognizer.getInstance().registerMsg(FriendSlotIdRoomIdRet.class);//
		
		
		RedisMsgRecognizer.getInstance().registerMsg(MailAddRedisMessage.class);//
		RedisMsgRecognizer.getInstance().registerMsg(ChatRedisMessage.class);//
		RedisMsgRecognizer.getInstance().registerMsg(NotifyFriends.class);//
		
		
		//================================= 以下都是后台管理系统发的
		RedisMsgRecognizer.getInstance().registerMsg(ActivityRedisMessage.class);
		RedisMsgRecognizer.getInstance().registerMsg(DeleteActivityRedisMessage.class);
		
		RedisMsgRecognizer.getInstance().registerMsg(NoticeRedisMessage.class);
		
		RedisMsgRecognizer.getInstance().registerMsg(CompensationRedisMessage.class);
		RedisMsgRecognizer.getInstance().registerMsg(DeleteCompensationRedisMessage.class);
		
		
		RedisMsgRecognizer.getInstance().registerMsg(BaccartRoomCloseRedisMessage.class);
		RedisMsgRecognizer.getInstance().registerMsg(BaccartRoomDataRedisMessage.class);
		
		//==============================================================
		RedisMsgRecognizer.getInstance().registerMsg(PaymentRedisMessage.class);//登陆服
		RedisMsgRecognizer.getInstance().registerMsg(DeliveryRedisMessage.class);//发货
		RedisMsgRecognizer.getInstance().registerMsg(AddCodeMessage.class);//添加兑换码
		RedisMsgRecognizer.getInstance().registerMsg(DeleteCodeMessage.class);//删除兑换码
		RedisMsgRecognizer.getInstance().registerMsg(PlayerLogin.class);//角色登陆调用
		
		//==============================================
		RedisMsgRecognizer.getInstance().registerMsg(AddJackpot.class);//修改老虎机采集
		RedisMsgRecognizer.getInstance().registerMsg(ChangJackpot.class);//修改老虎机采集
		RedisMsgRecognizer.getInstance().registerMsg(ChangJackpotNew.class);//修改老虎机采集
		RedisMsgRecognizer.getInstance().registerMsg(ChangStock.class);//修改总的押注量
		RedisMsgRecognizer.getInstance().registerMsg(SynchSlot.class);//
		RedisMsgRecognizer.getInstance().registerMsg(ChangSpinTimes.class);//
		
		//================================================
		RedisMsgRecognizer.getInstance().registerMsg(ChangeInfo2.class);
		RedisMsgRecognizer.getInstance().registerMsg(ChangeInfo3.class);
		RedisMsgRecognizer.getInstance().registerMsg(ChangeInfo4.class);
		RedisMsgRecognizer.getInstance().registerMsg(ChangRoom.class);
		RedisMsgRecognizer.getInstance().registerMsg(EnterSlot.class);
		RedisMsgRecognizer.getInstance().registerMsg(OutRoom.class);
		RedisMsgRecognizer.getInstance().registerMsg(RoleRoom.class);
		
		RedisMsgRecognizer.getInstance().registerMsg(EnterSlotRoom.class);
		RedisMsgRecognizer.getInstance().registerMsg(EnterSlotRoomReturn.class);
		
		//============================================================
		
		RedisMsgRecognizer.getInstance().registerMsg(RoomGift.class);
		RedisMsgRecognizer.getInstance().registerMsg(RoomReq.class);
		
	}
	
	public static RedisMsgRecognizer getInstance()
	{
		return instance;
	}
	
	public RedisMsgRecognizer()
	{
		this.init();
	}
	@Deprecated
	public Map<String,Class<?>> getMessageMapping() {
		// TODO Auto-generated method stub
		return messageMapping;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 注册消息
	 * @param cmd
	 * @param msgClass
	 */
	public void registerMsg(Class<?> msgClass)
	{
		messageMapping.put(msgClass.getSimpleName(), msgClass);
	}
	
	/**
	 * 生成消息
	 * @param cmd
	 * @param payload
	 * @return
	 * @throws MessageParseException
	 */
	public IRedisMessage createMessageImpl(String cmd,String payload) throws MessageParseException{
		Class<?> clazz = messageMapping.get(cmd);
		if (clazz == null)
		{
			throw new MessageParseException("Unknow msgType:" + cmd);
		} else 
		{
			IRedisMessage msg = null;
			try{
				msg = (IRedisMessage)JSON.parseObject(payload, clazz);
			}
			catch(JSONException jsonEx)
			{
				throw new MessageParseException("json decode failed:" + cmd +","+jsonEx.getMessage());
			}
			return msg;
		}
	}
}
