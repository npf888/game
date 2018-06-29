package com.gameserver.conversioncode.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.core.util.StringUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.conversioncode.ConversioncodeData;
import com.gameserver.conversioncode.msg.CGConversionCode;
import com.gameserver.conversioncode.msg.GCConversionCode;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

public class ConversioncodeHandler {

	public void handleConversionCode(Player player, CGConversionCode cgConversionCode) {
		
		String code = cgConversionCode.getCode();
		
		if(code.length() == 10 && StringUtils.isLetterAndDigit(code)){
			
			Human human = player.getHuman();
			
			Set<String> receivecode = human.getReceivecode();
			
			ConversioncodeData cData = Globals.getConversioncodeService().getData(code);
			
			if(cData == null){
				human.sendSystemMessage(LangConstants.Conversion4);
				return;
			}
			if(!receivecode.contains(code)){
				long time = Globals.getTimeService().now();
				long endTime = cData.getEndTime();
				if(endTime > time && cData.getIsdelete() == 0){
					int codeType = cData.getCodeType();
					//通用型 兑换码
					if(codeType==0){
						long gold = cData.getGold();
						String detailReason = MessageFormat.format( LogReasons.GoldLogReason.CONVERSION.getReasonText(), code,gold);
						human.giveMoney(gold, Currency.GOLD, false, LogReasons.GoldLogReason.CONVERSION, detailReason, -1, 1);
						receivecode.add(code);
						human.setModified();
						List<RandRewardData> randRewardDataList= new ArrayList<RandRewardData>();
						RandRewardData randRewardData = new RandRewardData();
						randRewardData.setRewardCount((int)gold);
						randRewardData.setRewardId(3);
						randRewardDataList.add(randRewardData);
						GCConversionCode message = new GCConversionCode();
						message.setRandRewardList(randRewardDataList.toArray(new RandRewardData[randRewardDataList.size()]));
						player.sendMessage(message);
						/*CommonLogic.getInstance().giveRandReward(player.getHuman(), 
							randRewardDataList,
							LogReasons.GoldLogReason.CONVERSION,
							LogReasons.DiamondLogReason.CONVERSION,
							LogReasons.CharmLogReason.CONVERSION,
							LogReasons.ItemLogReason.CONVERSION,false);*/
						//提示 领取成功
						//human.sendSystemMessage(LangConstants.Conversion1);
					//特殊型兑换码
					}else if(codeType == 1){
						long gold = cData.getGold();
						//只能用一次
						cData.setIsdelete(1);
						cData.setModified();
						String detailReason = MessageFormat.format( LogReasons.GoldLogReason.CONVERSION.getReasonText(), code,gold);
						human.giveMoney(gold, Currency.GOLD, false, LogReasons.GoldLogReason.CONVERSION, detailReason, -1, 1);
						receivecode.add(code);
						human.setModified();
						List<RandRewardData> randRewardDataList= new ArrayList<RandRewardData>();
						RandRewardData randRewardData = new RandRewardData();
						randRewardData.setRewardCount((int)gold);
						randRewardData.setRewardId(3);
						randRewardDataList.add(randRewardData);
						GCConversionCode message = new GCConversionCode();
						message.setRandRewardList(randRewardDataList.toArray(new RandRewardData[randRewardDataList.size()]));
						player.sendMessage(message);
					}
				}else{
					human.sendSystemMessage(LangConstants.Conversion2);
				}
			}else{
				human.sendSystemMessage(LangConstants.Conversion3);
			}
			
			
		}
	}

}
