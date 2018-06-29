package com.gameserver.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.slot.msg.GCWinnerWheel;
import com.gameserver.slot.template.WinnerWheelConfigTemplate;
import com.gameserver.slot.template.WinnerWheelTemplate;

/**
 * 中了winner之后  给翻倍转盘的 service
 * @author JavaServer
 *
 */
public class WinnerWheelService implements InitializeRequired {
	private static Logger logger = Loggers.rechargeLogger;
	private List<WinnerWheelTemplate> WinnerWheelTemplateList = new ArrayList<WinnerWheelTemplate>();
	private List<WinnerWheelConfigTemplate> WinnerWheelConfigTemplateList = new ArrayList<WinnerWheelConfigTemplate>();
	@Override
	public void init() {
		Map<Integer,WinnerWheelTemplate> winnerWheelTemplates = Globals.getTemplateService().getAll(WinnerWheelTemplate.class);
		for(WinnerWheelTemplate ww: winnerWheelTemplates.values()){
			//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
			WinnerWheelTemplateList.add(ww);
		}
		Map<Integer,WinnerWheelConfigTemplate> winnerWheelConfigTemplates = Globals.getTemplateService().getAll(WinnerWheelConfigTemplate.class);
		for(WinnerWheelConfigTemplate ww: winnerWheelConfigTemplates.values()){
			//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
			WinnerWheelConfigTemplateList.add(ww);
		}
		
	}

	
	/**
	 * 顺序 自己排序
	 * jackpots 5
	 * epics 4
	 * supers 3
	 * megas 2
	 * bigswitch 1
	 * @param winGold
	 * @param switchWhich  中了哪个winner
	 */
	public void getByWinGold(Human owner,long winGold,int switchWinner,int goldType){
		WinnerWheelTemplate winnerWheelTemplate =getMulByWeight();
		int itemMul = winnerWheelTemplate.getItemMul();
		logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------itemMul：："+itemMul);
		winGold=winGold*itemMul;
		logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------winGold：："+winGold);
		for(WinnerWheelConfigTemplate winnerWheelConfigTemplate:WinnerWheelConfigTemplateList){
			long start = winnerWheelConfigTemplate.getStart();
			long end = winnerWheelConfigTemplate.getEnd();
			
			logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------start：：end::"+start+""+end);
			if(winGold >= start && winGold <end){
				int jackpotswitch = winnerWheelConfigTemplate.getJackpotswitch();
				int epicswitch = winnerWheelConfigTemplate.getEpicswitch();
				int superswitch = winnerWheelConfigTemplate.getSuperswitch();
				int megaswitch = winnerWheelConfigTemplate.getMegaswitch();
				int bigswitch = winnerWheelConfigTemplate.getBigswitch();
				//选择哪个 权值组 （value1,value2,value3,value4,value5）
				int wheelType = winnerWheelConfigTemplate.getWheeltype();
				logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------start：：end::"+start+""+end);
				if(switchWinner == 5){ //彩金 winner
					if(jackpotswitch == 1){//开
						sendWinnerWheel(owner,winnerWheelTemplate,winGold,itemMul,wheelType,goldType);
						return;
					}
				}else if(switchWinner == 4){ //epic winner
					if(epicswitch==1){//开
						sendWinnerWheel(owner,winnerWheelTemplate,winGold,itemMul,wheelType,goldType);
						return;
					}
				}else if(switchWinner == 3){ //super winner
					if(superswitch==1){//开
						sendWinnerWheel(owner,winnerWheelTemplate,winGold,itemMul,wheelType,goldType);
						return;
					}
				}else if(switchWinner == 2){//megas winner
					if(megaswitch==1){//开
						sendWinnerWheel(owner,winnerWheelTemplate,winGold,itemMul,wheelType,goldType);
						return;
					}
				}else if(switchWinner == 1){//big winner
					if(bigswitch==1){//开
						sendWinnerWheel(owner,winnerWheelTemplate,winGold,itemMul,wheelType,goldType);
						return;
					}
				}
				
				continue;
			}
			
			continue;
		}
	}
	
	public void sendWinnerWheel(Human owner,WinnerWheelTemplate winnerWheelTemplate,long totalGold,int mul,int wheelType,int goldType){
		if(winnerWheelTemplate.getItemId() == Currency.GOLD.getIndex()){
			logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始---HumanTemporaryData().setWinnerGold---"+totalGold);
			GCWinnerWheel gCWinnerWheel = new GCWinnerWheel();
			gCWinnerWheel.setMultiple(mul);
			gCWinnerWheel.setTotalGold(totalGold);
			gCWinnerWheel.setWheelType(wheelType);
			gCWinnerWheel.setGoldType(goldType);
			//设置到用户的 缓存里
			owner.getHumanSlotManager().getHumanTemporaryData().setWinner(true);
			owner.getHumanSlotManager().getHumanTemporaryData().setWinnerGold(totalGold);
			//发送给用户 弹出 大奖转盘
 			owner.sendMessage(gCWinnerWheel);
		}
	}
	
	//现在是默认 以第一个 value1 为主 ，其他的value2,value3,value4,value5,value6 都不用了
	public WinnerWheelTemplate getMulByWeight(){
		List<Integer> weightList = new ArrayList<Integer>();
		for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
			weightList.add(wwt.getValue1());
		}
		
		List<WinnerWheelTemplate>  post = ArrayUtils.randomFromArray(WinnerWheelTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	/*public WinnerWheelTemplate getMulByWeight(int value){
		List<Integer> weightList = new ArrayList<Integer>();
		if(value == 1){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue1());
			}
		}else if(value == 2){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue2());
			}
		}else if(value == 3){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue3());
			}
		}else if(value == 4){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue4());
			}
		}else if(value == 5){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue5());
			}
		}else if(value == 6){
			for(WinnerWheelTemplate wwt:WinnerWheelTemplateList){
				weightList.add(wwt.getValue6());
			}
			
		}
		
		List<WinnerWheelTemplate>  post = ArrayUtils.randomFromArray(WinnerWheelTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}*/
}
