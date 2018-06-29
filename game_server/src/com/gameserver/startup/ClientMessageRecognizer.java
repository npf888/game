package com.gameserver.startup;

import java.util.HashMap;
import java.util.Map;

import com.common.InitializeRequired;
import com.common.MessageMappingProvider;
import com.core.msg.IMessage;
import com.core.msg.MessageParseException;
import com.core.msg.recognizer.BaseMessageRecognizer;
import com.gameserver.achievement.msg.AchievementMsgMappingProvider;
import com.gameserver.activity.msg.ActivityMsgMappingProvider;
import com.gameserver.baccart.msg.BaccartMsgMappingProvider;
import com.gameserver.bazoo.msg.BazooMsgMappingProvider;
import com.gameserver.bazoogift.msg.BazoogiftMsgMappingProvider;
import com.gameserver.bazoonewguy.msg.BazoonewguyMsgMappingProvider;
import com.gameserver.bazoorank.msg.BazoorankMsgMappingProvider;
import com.gameserver.bazoosignin.msg.BazoosigninMsgMappingProvider;
import com.gameserver.bazootask.msg.BazootaskMsgMappingProvider;
import com.gameserver.chat.msg.ChatMsgMappingProvider;
import com.gameserver.club.msg.ClubMsgMappingProvider;
import com.gameserver.collect.msg.CollectMsgMappingProvider;
import com.gameserver.common.msg.CGHandshake;
import com.gameserver.common.msg.CommonMsgMappingProvider;
import com.gameserver.common.msg.MessageType;
import com.gameserver.conversioncode.msg.ConversioncodeMsgMappingProvider;
import com.gameserver.gift.msg.GiftMsgMappingProvider;
import com.gameserver.givealike.msg.GivealikeMsgMappingProvider;
import com.gameserver.guide.msg.GuideMsgMappingProvider;
import com.gameserver.human.msg.HumanMsgMappingProvider;
import com.gameserver.item.msg.ItemMsgMappingProvider;
import com.gameserver.lobby.msg.LobbyMsgMappingProvider;
import com.gameserver.luckyspin.msg.LuckyspinMsgMappingProvider;
import com.gameserver.mail.msg.MailMsgMappingProvider;
import com.gameserver.misc.msg.MiscMsgMappingProvider;
import com.gameserver.monthcard.msg.MonthcardMsgMappingProvider;
import com.gameserver.newbie.msg.NewbieMsgMappingProvider;
import com.gameserver.player.msg.PlayerMsgMappingProvider;
import com.gameserver.rank.msg.RankMsgMappingProvider;
import com.gameserver.ranknew.msg.RanknewMsgMappingProvider;
import com.gameserver.recharge.msg.RechargeMsgMappingProvider;
import com.gameserver.relation.msg.RelationMsgMappingProvider;
import com.gameserver.shop.msg.ShopMsgMappingProvider;
import com.gameserver.signin.msg.SigninMsgMappingProvider;
import com.gameserver.slot.msg.SlotMsgMappingProvider;
import com.gameserver.task.msg.TaskMsgMappingProvider;
import com.gameserver.texas.msg.TexasMsgMappingProvider;
import com.gameserver.treasury.msg.TreasuryMsgMappingProvider;
import com.gameserver.vip.msg.VipMsgMappingProvider;
import com.gameserver.weekcard.msg.WeekcardMsgMappingProvider;
import com.gameserver.worldboss.msg.WorldbossMsgMappingProvider;

/**
 * 来自客户端的消息识别器
 * @author Thinker
 */
public class ClientMessageRecognizer extends BaseMessageRecognizer implements InitializeRequired 
{
	private Map<Short, Class<?>> msgs = new HashMap<Short, Class<?>>();

	public ClientMessageRecognizer()
	{
		this.init();
	}

	@Override
	public void init()
	{
		registerMsgMapping(new MessageMappingProvider()
		{
			@Override
			public Map<Short, Class<?>> getMessageMapping()
			{
				Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
				map.put(MessageType.CG_HANDSHAKE, CGHandshake.class);
				return map;
			}			
		});

		MessageMappingProvider[] providerArr =
		{
			new CommonMsgMappingProvider(),
			new PlayerMsgMappingProvider(),
			new TexasMsgMappingProvider(),
			new RelationMsgMappingProvider(),
			new ShopMsgMappingProvider(),
			new SigninMsgMappingProvider(),
			new RankMsgMappingProvider(),
			new MailMsgMappingProvider(),
			new MiscMsgMappingProvider(),
			new MonthcardMsgMappingProvider(),
			new WeekcardMsgMappingProvider(),
			new VipMsgMappingProvider(),
			new ChatMsgMappingProvider(),
			new RechargeMsgMappingProvider(),
			new TaskMsgMappingProvider(),
			new ItemMsgMappingProvider(),
			new ActivityMsgMappingProvider(),
			new HumanMsgMappingProvider(),
			new BaccartMsgMappingProvider(),
			new LuckyspinMsgMappingProvider(),
			new SlotMsgMappingProvider(),
			new LobbyMsgMappingProvider(),
			new RanknewMsgMappingProvider(),
			new GiftMsgMappingProvider(),
			new AchievementMsgMappingProvider(),
			new ConversioncodeMsgMappingProvider(),
			new CollectMsgMappingProvider(),
			new TreasuryMsgMappingProvider(),
			new ClubMsgMappingProvider(),
			new WorldbossMsgMappingProvider(),
			new GivealikeMsgMappingProvider(),
			new NewbieMsgMappingProvider(),
			new GuideMsgMappingProvider(),
			new BazooMsgMappingProvider(),
			new BazoosigninMsgMappingProvider(),
			new BazoorankMsgMappingProvider(),
			new BazootaskMsgMappingProvider(),
			new BazoogiftMsgMappingProvider(),
			new BazoonewguyMsgMappingProvider(),
		};

		for (MessageMappingProvider provider : providerArr)
		{
			// 注册消息
			this.registerMsgMapping(provider);
		}
	}

	/**
	 * 注册消息号和消息类的映射
	 * 
	 * @param mappingProvider
	 */
	private void registerMsgMapping(MessageMappingProvider mappingProvider)
	{
		msgs.putAll(mappingProvider.getMessageMapping());
	}

	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException
	{
		Class<?> clazz = msgs.get(type);
		if (clazz == null)
		{
			throw new MessageParseException("Unknow msgType:" + type);
		} else 
		{
			try 
			{
				IMessage msg = (IMessage) clazz.newInstance();
				return msg;
			} catch (Exception e)
			{
				throw new MessageParseException("Message Newinstance Failed，msgType:" + type, e);
			}
		}
	}
}
