package com.gameserver.currency;



import com.common.LogReasons;
import com.common.LogReasons.ILogReason;
import com.common.constants.Loggers;
import com.core.util.LogUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.rank.RankLogic;
import com.gameserver.rank.enums.RankTypeEnum;


/**
 * 金钱处理器
 * @author Thinker
 * 
 */
public final class CurrencyProcessor 
{
	/** 单例对象 */
	private static volatile CurrencyProcessor _instance = null;

	private CurrencyProcessor() 
	{
		
	}
	
	public static CurrencyProcessor getInstance()
	{
		if (_instance == null)
		{
			synchronized (CurrencyProcessor.class) 
			{
				if (_instance == null)
				{
					_instance = new CurrencyProcessor();
				}
			}
		}
		return _instance;
	}

	/**
	 * 扣钱，如果扣钱的数量为0则抛出 {@link IllegalArgumentException}
	 * 
	 * @param human 玩家角色
	 * @param amount 扣得数量, 大于 0 才有效
	 * @param mainCurrency 主货币类型, 不为 null 才有效
	 * @param altCurrency 替补货币类型, 可以为 null
	 * @param needNotify 是否在该函数内通知玩家货币改变, 为 true时信息格式为 "您花费了 xx (货币单位 ) 用于xx", false 时不在本函数内提示
	 * @param logReason 扣钱的原因
	 * @param detailReason 详细原因, 通常为null, 扩展使用
	 * @param productId 向平台汇报贵重物品消耗时的 productId, 非物品的消耗时使用-1
	 * @param productCount 重要物品数量
	 * @return 扣钱成功返回 true, 否则返回 false, 失败可能是钱已经超出了最大限额, 参数不合法等
	 * 
	 */
	public boolean costMoney(
		Human human, 
		final long amount,
		final Currency mainCurrency, 
		boolean needNotify,
		ILogReason logReason,
		String detailReason, 
		int productId,
		int productCount
		) 
	{

		if (amount <= 0 || mainCurrency == null) 
		{
			// 无效参数
			throw new IllegalArgumentException(
				String.format(
					"扣钱参数有误:amount=%d,mainCurrency=%s,altCurrency=%s,reason=%s,detailReason=%s,productId=%d,productCount=%d",
					amount, 
					mainCurrency, 
					logReason,
					detailReason, 
					productId,
					productCount
				)
			);
		}

		long mainCost = 0;
	
		// 取玩家身上的钱数
		long mainCurrAmount = this.getMoney(human,mainCurrency);
	

		if (mainCurrAmount >= amount)
		{
			// 只扣主货币就够了
			mainCost = amount;
		
		} else 
		{
			return  false;
		}

		// 扣钱成功
		long mainLeft = mainCurrAmount;

		if (mainCost > 0)
		{
			// 扣除主货币
			mainLeft = substractAndFixMoney(
				human, // 玩家角色对象
				mainCost, // 主货币数量
				mainCurrency // 主货币类型
			);
		}

		// 实时更新钱
		human.setModified();
		// 更新客户端任务属性
		if(needNotify)
			human.snapChangedProperty(true);
		//更新排行版
		updateRank(human,mainCurrency);
		
		//增加消费等记录
		switch(mainCurrency){
		case GOLD:
		{
			Globals.getLogService().sendGoldLog(human, (LogReasons.GoldLogReason)logReason, detailReason, mainCost, mainLeft);
			
		}
			break;
		case DIAMOND:
		{
			Globals.getLogService().sendDiamondLog(human, (LogReasons.DiamondLogReason)logReason, detailReason, mainCost, mainLeft);
		}
			break;
		case CHARM:
		{
			Globals.getLogService().sendCharmLog(human, (LogReasons.CharmLogReason)logReason, detailReason, mainCost, mainLeft);
			
		}
			break;
		default:
			break;
	
		}
		
		return true;
	}

	/**
	 * 添加金钱
	 * 
	 * @param h
	 * @param addValue
	 * @param currencyType
	 * @return 
	 * 
	 */
	private long addAndFixMoney(Human h, final long addValue, final Currency currencyType, boolean needNotify) 
	{

		if (h == null || 
			addValue <= 0 || 
			currencyType == null)
		{
			return 0;
		}

		// 获取金钱
		long oldMoney = this.getMoney(h, currencyType);
		// 定义新值
		long newValue = 0;
        //AAA: 代码爆仓
		if (Long.MAX_VALUE - oldMoney < addValue)
		{
			// 判断是否超过上限 ?
			newValue = Long.MAX_VALUE;
		} else
		{
			// 增加金钱
			newValue = oldMoney + addValue;
		}

		// 设置金钱数量
		int propIndex = currencyType.getRoleBaseIntPropIndex();
		h.getBaseIntProperties().setPropertyValue(propIndex,newValue);
		
		if (needNotify)
		{
			//TODO 消息通知玩家获得了多少钱
//			if(currencyType==Currency.GOLD)
//				h.sendSystemMessage(LangConstants.GET_GOLD,addValue);
//			else if(currencyType==Currency.CHARM)
//				h.sendSystemMessage(LangConstants.GET_CHARM,addValue);
//			else
//				h.sendSystemMessage(LangConstants.GET_DIAMOND,addValue);
		}
		return newValue;
	}

	/**
	 * 减少金钱
	 * 
	 * @param h
	 * @param subValue
	 * @param currencyType
	 * @return 
	 * 
	 */
	private long substractAndFixMoney(Human h, final long subValue, final Currency currencyType)
	{

		if (h == null || 
			subValue <= 0 || 
			currencyType == null)
		{
			return 0;
		}

		// 获取现有金钱数量
		long oldMoney = this.getMoney(h, currencyType);
		long maxCanSub = oldMoney;

		long newValue = oldMoney;

		if (subValue > maxCanSub)
		{
			// 如果钱不够扣, 记录异常信息
			Loggers.gameLogger.error(LogUtils.buildLogInfoStr(
				h.getUUID(), String.format("money not enough oldMoney = %d,subValue = %d",oldMoney,subValue)));

			newValue = 0;
		} else
		{
			// 计算新的金钱数量
			newValue = oldMoney - subValue;
		}

		// 更新金钱数量
		int propIndex = currencyType.getRoleBaseIntPropIndex();
		h.getBaseIntProperties().setPropertyValue(propIndex,newValue);
	
		return newValue;
	}

	/**
	 * 给钱
	 * 
	 * @param human 玩家角色
	 * @param amount 改变数量, 大于 0 才有效
	 * @param currencyType 货币类型
	 * @param logReason 给钱原因
	 * @param detailReason 详细原因, 通常为 null, 扩展使用
	 * @return 给钱成功返回 true, 否则返回 false, 失败可能是钱已经超出了最大限额, 参数不合法等
	 * 
	 */
	public boolean giveMoney(
		Human human, 
		final long amount,
		final Currency currencyType, 
		boolean needNotify,
		ILogReason logReason,
		String detailReason,
		int productId,
		int productCount
		) 
	{

		if (amount < 0 || currencyType == null) 
		{
			// 无效参数
			throw new IllegalArgumentException(
				String.format(
					"给钱参数有误: amount = %d, currencyType = %s, logReason = %s detailReason = %s",
					amount, 
					currencyType, 
					logReason, 
					detailReason
				)
			);
		}

		// 根据货币产生原因, 
		// 再判断是否超过该类货币上限, 进行操作限制
		long newAmount = this.addAndFixMoney(human,amount,currencyType,needNotify);

		// 实时更新钱
		human.setModified();
		// 更新客户端任务属性
		if(needNotify)
			human.snapChangedProperty(true);
		
		Globals.getSlotRoomService().change3(human.getPlayer());
		//更新排行版
		//updateRank(human,currencyType);
		//增加消费等记录
		switch(currencyType){
		case GOLD:
		{
			Globals.getLogService().sendGoldLog(human, (LogReasons.GoldLogReason)logReason, detailReason, amount, newAmount);
			
		}
			break;
		case DIAMOND:
		{
			Globals.getLogService().sendDiamondLog(human, (LogReasons.DiamondLogReason)logReason, detailReason, amount, newAmount);
		}
			break;
		case CHARM:
		{
			Globals.getLogService().sendCharmLog(human, (LogReasons.CharmLogReason)logReason, detailReason, amount, newAmount);
			
		}
			break;
		default:
			break;
	
		}
		return true;
	}

	/**
	 * 检查玩家是否足够指定货币, 如果替补货币为 null 则只检查主货币, 主货币不可以为 null!
	 * 
	 * @param h 玩家角色
	 * @param amount 数量, 大于等于 0才有效, 等于 0 时永远返回 true
	 * @param mainCurrency 主货币类型
	 * @param altCurrency 替补货币类型, 为 null 时只检测主货币
	 * @return 如果主货币够 amount 返回 true; 
	 * 主货币不够看替补货币够不够除现有主货币外剩下的, 够也返回true; 
	 * 加起来都不够返回 false;
	 * 参数无效也会返回 false; 
	 * 
	 */
	public boolean hasEnoughMoney(Human h,final int amount,final Currency mainCurrency)
	{

		if (amount < 0 || mainCurrency == null || mainCurrency == Currency.NULL) {
			return false;
		}

		// 获取主货币数量
		long mainCurrAmount = this.getMoney(h, mainCurrency);

		if (mainCurrAmount >= amount) 
		{
			// 主货币就足够了
			return true;
		} else
		{
			// 主货币不足, 没有替补货币, 就不够了
			return false;
		}
	}

	/**
	 * 查询玩家身上有多少钱
	 * 
	 * @param human
	 *            玩家角色
	 * @param currency
	 *            货币类型
	 * @return 钱的数量
	 */
	public long getMoney(Human human, final Currency currency) {
		int propIndex = currency.getRoleBaseIntPropIndex();
		return human.getBaseIntProperties().getPropertyValue(propIndex);
	}
	
	/**
	 * 更新排行版
	 * @param human
	 * @param currency
	 * @param value
	 */
	private void updateRank(Human human,final Currency currency)
	{
		switch(currency)
		{
		/*case DIAMOND: 
			RankLogic.getInstance().updateRank(RankTypeEnum.DIAMOND, human.getPassportId(), human.getDiamond()+human.getCoupon());
			break;
		case COUPON:
			RankLogic.getInstance().updateRank(RankTypeEnum.DIAMOND, human.getPassportId(), human.getDiamond()+human.getCoupon());
			break;*/
		case GOLD:
			RankLogic.getInstance().updateRank(RankTypeEnum.GOLD, human.getPassportId(), human.getGold());
			break;
		case CHARM:
			RankLogic.getInstance().updateRank(RankTypeEnum.CHARM, human.getPassportId(), human.getCharm());
			break;
		default:
			break;
		}
	
	}
}