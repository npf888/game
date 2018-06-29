package com.gameserver.common.log;

import java.util.Map;

import net.sf.json.JSONObject;

import com.core.util.StringUtils;

/**
 * 日志开关<br>
 * 记录日志，都需要判断，是否可以记录，只有<code>true</code>才产生日志信息.<br>
 * 用于GM后台控制日志归档
 * 
 * @author Thinker
 */
public class LoggableSwitcher
{
	
	private boolean isGoldLoggable = true;
	private boolean isChatLoggable = true;
	private boolean isVipLoggable = true;
	private boolean isDiamondLoggable = true;
	private boolean isWeekcardLoggable = true;
	private boolean isMonthcardLoggable = true;
	private boolean isSignInLoggable = true;
	private boolean isRechargeLoggable =true;
	private boolean isBasicPlayerLoggable = true;
	private boolean isOnlineTimeRewardLoggable = true;
	private boolean isOnlineTimeLoggable = true;
	private boolean isDailyTaskLoggable = true;
	private boolean isExceptionLoggable = true;
	private boolean isCharmLoggable = true;
	private boolean isFriendLoggable = true;
	private boolean isItemLoggable = true;
	private boolean isRenameLoggable = true;
	private boolean isTexasRoomLoggable = true;
	private boolean isBaccaratRoomLoggable = true;
	private boolean isLuckySpinLoggable =true;
	private boolean isSlotLoggable  =true;
	private boolean isDataOverviewLoggable  =true;
	
	private boolean isPlayerOnleLoggable  =true;
	private boolean isNewRechargeLoggable  =true;
	private boolean isPlayerLoginLoggable  =true;
	private boolean isPlayerKeepLoggable  =true;
	private boolean isSlotRoomLoggable  =true;
	private boolean isInOutTimeLoggable  =true;
	private boolean isJackpotLoggable  =true;
	private boolean isTournamentLoggable  =true;
	private boolean isWorldBossLoggable  =true;
	private boolean isStatisticsTimeLoggable  =true;
	private boolean isDiceClassicalRoomLoggable  =true;
	private boolean isDiceClassicalGuessLoggable  =true;
	private boolean isDiceClassicalCallNumLoggable  =true;
	private boolean isDiceClassicalBazooGoldLoggable  =true;
	private boolean isDiceCowLoggable  =true;
	private boolean isDiceShowHandLoggable  =true;
	private boolean isDiceSignInLoggable  =true;
	private boolean isDiceBlackWhiteLoggable  =true;
	private boolean isDiceStatisticsWinLostLoggable  =true;

	
	public boolean isGoldLoggable() {
		return isGoldLoggable;
	}
	
	public boolean isChatLoggable() {
		// TODO Auto-generated method stub
		return isChatLoggable;
	}
	
	public boolean isVipLoggable() {
		// TODO Auto-generated method stub
		return isVipLoggable;
	}

	public boolean isDiamondLoggable() {
		// TODO Auto-generated method stub
		return isDiamondLoggable;
	}
	
	public boolean isWeekcardLoggable() {
		// TODO Auto-generated method stub
		return isWeekcardLoggable;
	}
	
	public boolean isMonthcardLoggable() {
		// TODO Auto-generated method stub
		return isMonthcardLoggable;
	}
	
	public boolean isSignInLoggable() {
		// TODO Auto-generated method stub
		return isSignInLoggable;
	}
	
	public boolean isRechargeLoggable() {
		// TODO Auto-generated method stub
		return isRechargeLoggable;
	}
	
	public boolean isBasicPlayerLoggable() {
		// TODO Auto-generated method stub
		return isBasicPlayerLoggable;
	}
	
	public boolean isOnlineTimeRewardLoggable() {
		// TODO Auto-generated method stub
		return isOnlineTimeRewardLoggable;
	}
	
	public boolean isOnlineTimeLoggable() {
		// TODO Auto-generated method stub
		return isOnlineTimeLoggable;
	}
	
	public boolean isDailyTaskLoggable() {
		// TODO Auto-generated method stub
		return isDailyTaskLoggable;
	}
	
	public boolean isExceptionLoggable() {
		// TODO Auto-generated method stub
		return isExceptionLoggable;
	}
	public boolean isCharmLoggable() {
		// TODO Auto-generated method stub
		return isCharmLoggable;
	}
	
	
	
	public boolean isFriendLoggable() {
		return isFriendLoggable;
	}



	public boolean isItemLoggable() {
		return isItemLoggable;
	}
	
	public boolean isRenameLoggable() {
		return isRenameLoggable;
	}

	public boolean isTexasRoomLoggable() {
		return isTexasRoomLoggable;
	}
	
	public boolean isBaccaratRoomLoggable() {
		return isBaccaratRoomLoggable;
	}
	
	public boolean isLuckySpinLoggable(){
		return isLuckySpinLoggable;
	}
	
	public boolean isSlotLoggable(){
		return isSlotLoggable;
	}
	
	/**
	 * 转换成JSON数据，用于网络传输
	 * 
	 * @return
	 */
	public JSONObject toJSON() {
		JSONObject _json = new JSONObject();
		_json.put("isGoldLoggable", Boolean.toString(isGoldLoggable));
		_json.put("isChatLoggable", Boolean.toString(isChatLoggable));
		_json.put("isDiamondLoggable", Boolean.toString(isDiamondLoggable));
		_json.put("isVipLoggable", Boolean.toString(isVipLoggable));
		_json.put("isWeekcardLoggable", Boolean.toString(isWeekcardLoggable));
		_json.put("isSignInLoggable", Boolean.toString(isSignInLoggable));
		_json.put("isRechargeLoggable", Boolean.toString(isRechargeLoggable));
		_json.put("isBasicPlayerLoggable", Boolean.toString(isBasicPlayerLoggable));
		_json.put("isOnlineTimeRewardLoggable", Boolean.toString(isOnlineTimeRewardLoggable));
		_json.put("isOnlineTimeLoggable", Boolean.toString(isOnlineTimeLoggable));
		_json.put("isDailyTaskLoggable", Boolean.toString(isDailyTaskLoggable));
		_json.put("isExceptionLoggable", Boolean.toString(isExceptionLoggable));
		_json.put("isCharmLoggable", Boolean.toString(isCharmLoggable));
		_json.put("isFriendLoggable", Boolean.toString(isFriendLoggable));
		_json.put("isItemLoggable", Boolean.toString(isItemLoggable));
		_json.put("isRenameLoggable", Boolean.toString(isRenameLoggable));
		_json.put("isTexasRoomLoggable", Boolean.toString(isTexasRoomLoggable));
		_json.put("isBaccaratRoomLoggable", Boolean.toString(isBaccaratRoomLoggable));
		_json.put("isLuckySpinLoggable", Boolean.toString(isLuckySpinLoggable));
		_json.put("isSlotLoggable", Boolean.toString(isSlotLoggable));
		
		return _json;
	}

	/**
	 * 通过网络（GM）传递来的参数解析数据，然后赋值.
	 * 
	 * @param params
	 */
	public void setFromParameterMap(Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return;
		}
		isGoldLoggable = transformParamter(params.get("isGoldLoggable"), isGoldLoggable);
		isChatLoggable = transformParamter(params.get("isChatLoggable"), isChatLoggable);
		isVipLoggable = transformParamter(params.get("isVipLoggable"), isVipLoggable);
		isDiamondLoggable = transformParamter(params.get("isDiamondLoggable"), isDiamondLoggable);
		isWeekcardLoggable = transformParamter(params.get("isWeekcardLoggable"), isWeekcardLoggable);
		isSignInLoggable = transformParamter(params.get("isSignInLoggable"), isSignInLoggable);
		isRechargeLoggable = transformParamter(params.get("isRechargeLoggable"), isRechargeLoggable);
		isBasicPlayerLoggable = transformParamter(params.get("isBasicPlayerLoggable"), isBasicPlayerLoggable);
		isOnlineTimeRewardLoggable = transformParamter(params.get("isOnlineTimeRewardLoggable"), isOnlineTimeRewardLoggable);
		isOnlineTimeLoggable = transformParamter(params.get("isOnlineTimeLoggable"), isOnlineTimeLoggable);
		isDailyTaskLoggable = transformParamter(params.get("isDailyTaskLoggable"), isDailyTaskLoggable);
		isExceptionLoggable = transformParamter(params.get("isExceptionLoggable"), isExceptionLoggable);
		isCharmLoggable = transformParamter(params.get("isCharmLoggable"), isCharmLoggable);
		isFriendLoggable = transformParamter(params.get("isFriendLoggable"), isFriendLoggable);
		isItemLoggable = transformParamter(params.get("isItemLoggable"), isItemLoggable);
		isRenameLoggable = transformParamter(params.get("isRenameLoggable"), isRenameLoggable);
		isTexasRoomLoggable = transformParamter(params.get("isTexasRoomLoggable"), isTexasRoomLoggable);
		isBaccaratRoomLoggable = transformParamter(params.get("isBaccaratRoomLoggable"), isBaccaratRoomLoggable);
		
		isLuckySpinLoggable= transformParamter(params.get("isLuckySpinLoggable"), isLuckySpinLoggable);
	}
	

	/**
	 * 转换参数<br>
	 * 考虑参数边界
	 * <p>
	 * value = true ====== [true]<br>
	 * value = '' || 11 || .... ====== [false]<br>
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	private boolean transformParamter(String value, boolean defaultValue)
	{
		if (StringUtils.isEmpty(value)) return defaultValue;
		return Boolean.parseBoolean(value);
	}

	public boolean isDataOverviewLoggable() {
		
		return isDataOverviewLoggable;
	}

	public boolean isPlayerOnleLoggable() {
		
		return isPlayerOnleLoggable;
	}

	public boolean isNewRechargeLoggable() {
		
		return isNewRechargeLoggable;
	}

	public boolean isPlayerLoginLoggable() {
		
		return isPlayerLoginLoggable;
	}

	public boolean isPlayerKeepLoggable() {
		return isPlayerKeepLoggable;
	}

	public void setPlayerKeepLoggable(boolean isPlayerKeepLoggable) {
		this.isPlayerKeepLoggable = isPlayerKeepLoggable;
	}

	public boolean isSlotRoomLoggable() {
		
		return isSlotRoomLoggable;
	}

	
	public boolean isInOutTimeLoggable() {
		
		return isInOutTimeLoggable;
	}

	public boolean isJackpotLoggable() {
		return isJackpotLoggable;
	}

	public boolean isTournamentLoggable() {
		return isTournamentLoggable;
	}

	public boolean isWorldBossLoggable() {
		return isWorldBossLoggable;
	}

	public boolean isStatisticsTimeLoggable() {
		return isStatisticsTimeLoggable;
	}

	public boolean isDiceClassicalRoomLoggable() {
		return isDiceClassicalRoomLoggable;
	}

	public boolean isDiceClassicalGuessLoggable() {
		return isDiceClassicalGuessLoggable;
	}

	public boolean isDiceClassicalCallNumLoggable() {
		return isDiceClassicalCallNumLoggable;
	}

	public boolean isDiceClassicalBazooGoldLoggable() {
		return isDiceClassicalBazooGoldLoggable;
	}

	public boolean isDiceCowLoggable() {
		return isDiceCowLoggable;
	}

	public boolean isDiceShowHandLoggable() {
		return isDiceShowHandLoggable;
	}

	public boolean isDiceSignInLoggable() {
		return isDiceSignInLoggable;
	}
	
	public boolean isDiceBlackWhiteLoggable() {
		return isDiceBlackWhiteLoggable;
	}

	public boolean isDiceStatisticsWinLostLoggable() {
		return isDiceStatisticsWinLostLoggable;
	}
	
	




}
