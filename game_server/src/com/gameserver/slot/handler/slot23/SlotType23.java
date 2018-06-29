package com.gameserver.slot.handler.slot23;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.slot.HumanSlot;
import com.gameserver.slot.JackpotDragonService;
import com.gameserver.slot.Slot;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType23BounsInfo;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.JackpotDragonTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;

/**
 * 东方龙 老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType23 extends SlotBase{
	@SuppressWarnings("unchecked")
	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		//老虎机转动 的奖励
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType23 tempScatterInfo = getScatterInfo(human);
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		//龙头的逻辑
		
		Human human = player.getHuman();
		HumanSlotManager huamnSlotManager = human.getHumanSlotManager();
		HumanSlot humanSlot = humanSlotManager.getHumanSlotBySlotId(huamnSlotManager.getCurrentSlotId());
		HumanTemporaryData humanTemporaryData= huamnSlotManager.getHumanTemporaryData();
//		SlotEntity slotEntity = Globals.getDaoService().getSlotDao().get(tempSlotsListTemplate.getId());
		Slot slot = Globals.getSlotService().getSlotByTemplateId(tempSlotsListTemplate.getId());
		humanTemporaryData.setObtainReward(0);
		//获取奖池的基数
		//先从临时数据里边 奖池的数据，如果是空的 ，再去数据库里取数据
		String	jackpot = slot.getSlotType23Jackpot();
		//顺序取出所有的奖池
		JackpotDragonService jackpotDragonService = Globals.getJackpotDragonService();
		List<String> jackpotSingleList = jackpotDragonService.getJackpotList(jackpot,bet);
		List<JackpotDragonTemplate> jackpotDragons = jackpotDragonService.getJackpotDragonTemplateList();
		//如果玩家第一次玩，数据库的数据应该是空的，去获取模板的数据
		if(jackpotSingleList == null || jackpotSingleList.size() == 0){
			Collections.sort(jackpotDragons, new Comparator<Object>(){
				@Override
				public int compare(Object o1, Object o2) {
					JackpotDragonTemplate jk1 = (JackpotDragonTemplate)o1;
					JackpotDragonTemplate jk2 = (JackpotDragonTemplate)o2;
					if(jk1.getJackpotNum() > jk2.getJackpotNum()){
						return 1;
					}
					return -1;
				}
				
			});
			for(JackpotDragonTemplate jackpotDragonTemplate:jackpotDragons){
				jackpotSingleList.add((bet*jackpotDragonTemplate.getInitial())/100+"");
			}
		}
	//计算获奖金额
		//变化后的奖池金额
		String changeSlotType23Jackpot = "";
		if(tempScatterInfo.getBnus() == 3){
			changeSlotType23Jackpot = countReward(jackpotSingleList,jackpotDragons,0,bet,free,humanTemporaryData);
		}else if(tempScatterInfo.getBnus() == 4){
			changeSlotType23Jackpot = countReward(jackpotSingleList,jackpotDragons,1,bet,free,humanTemporaryData);
		}else if(tempScatterInfo.getBnus() == 5){
			changeSlotType23Jackpot = countReward(jackpotSingleList,jackpotDragons,2,bet,free,humanTemporaryData);
		}else if(tempScatterInfo.getBnus() == 6){
			changeSlotType23Jackpot = countReward(jackpotSingleList,jackpotDragons,3,bet,free,humanTemporaryData);
		}else if(tempScatterInfo.getBnus() >= 7){
			changeSlotType23Jackpot = countReward(jackpotSingleList,jackpotDragons,4,bet,free,humanTemporaryData);
		}else{
			changeSlotType23Jackpot = addIncreaseReward(jackpotSingleList,jackpotDragons,bet,humanTemporaryData);
		}
		//改变奖池的 基数 变化后的值放到临时数据里 ，并且 存到数据库里
		if(StringUtils.isNotBlank(changeSlotType23Jackpot)){
			String allJackportRewards = jackpotDragonService.getStringAllJackpots(jackpot,changeSlotType23Jackpot,bet);
			slot.setSlotType23Jackpot(allJackportRewards);
			slot.setModified();
		}
		
		GCSlotType23BounsInfo bounsInfo = new GCSlotType23BounsInfo();
		bounsInfo.setBounsNum(tempScatterInfo.getBnus());
		logger.info("缓存的奖金-----------------------------------："+humanTemporaryData.getObtainReward());
		bounsInfo.setObtainReward(humanTemporaryData.getObtainReward());
		String reReward = jackpotDragonService.sortJackPort(changeSlotType23Jackpot);
		bounsInfo.setJackpotInfo(reReward);
		player.sendMessage(bounsInfo);
		logger.info("金币的数量-------------------------------------"+tempReward);
		return tempSpecialConnectInfoData;
	}
	// index 获取第几个奖池，第几个数
	private String countReward(List<String> jackpotSingleList,List<JackpotDragonTemplate> jackpotDragons,int index,int bet,int free,HumanTemporaryData humanTemporaryData){
		
		long firstReward = Long.valueOf(jackpotSingleList.get(index));//第一个奖池初始值
		long times = jackpotDragons.get(index).getTimes();//第一个奖池的倍数
		long obtainReward = 0;
		if(free == 1){// 1 是免费，0 是付费
			obtainReward = (firstReward*times)/100;
		}else{
			//如果付费 ，初始金额 还要加上 一个增加数
			int firstIncReward = jackpotDragons.get(index).getIncrease();
			int incNum = (bet*firstIncReward)/10000;
			obtainReward = (firstReward+incNum)*times/100;
		}
		humanTemporaryData.setObtainReward(obtainReward);
		logger.info("金币 金币 金币-------------------------：： obtainReward="+obtainReward+"---firstReward="+firstReward+"---times="+times);
		slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(obtainReward),humanSlotManager.getCurrentBet());
		//初始奖池  //判断 奖池 是否 只剩下20%，如果是的话 复原
		long finalReward = getFinalReward(firstReward,obtainReward,index,bet,jackpotDragons);
		return fiveReward(jackpotSingleList,finalReward+"",index);
	}
	private long getFinalReward(long firstReward, long obtainReward,int index,int bet,
			List<JackpotDragonTemplate> jackpotDragons) {
		long finalReward = firstReward-obtainReward;
		long inital = (Long.valueOf(jackpotDragons.get(index).getInitial()).longValue()*bet)/100;
		long compareValue =inital -finalReward*5;
		if(compareValue >=0){
			finalReward=inital;
		}
		return  finalReward;
	}
	//param: jackpot 初始奖池，  finalReward 某个变化后台（减少后）的奖池。     这个方法是替换掉 变化的奖池
	private String fiveReward(List<String> jackpotSingleList,String finalReward,int index){
		String jackpots = "";
		for(int i = 0;i<jackpotSingleList.size();i++){
			String jackpotSingle = jackpotSingleList.get(i);
			if(i == index){
				jackpots += finalReward+",";
			}else{
				jackpots += jackpotSingle+",";
			}
		}
		logger.info("金币 金币 金币-------------------------"+jackpots.substring(0,jackpots.length()-1));
		return jackpots.substring(0,jackpots.length()-1);
	}
	
	//玩家每次花钱 转动 奖池都会增加
	private String addIncreaseReward(List<String> jackpotSingleList,List<JackpotDragonTemplate> jackpotDragons,int bet,HumanTemporaryData humanTemporaryData){
		String newRewards = "";
		for(int i=0;i<jackpotDragons.size();i++){
			JackpotDragonTemplate jk = jackpotDragons.get(i);
			int inc = jk.getIncrease();
			long incNum = (bet*inc)/10000;
			//处理小数部分
			int leftNum = (bet*inc)%10000;
			//把上把不够的剩下的 加上
//			int ln = leftNum/1000+humanTemporaryData.getLn();
			int ln =  getLn(i+1,leftNum,humanTemporaryData);
			if(ln >=5){
				incNum+=1;
				//清空相应的 值
				setNullLn(i+1,humanTemporaryData);
			}else{
				//设置 相应的值
				setValueLn(i+1,ln,humanTemporaryData);
			}
			String reward = jackpotSingleList.get(i);
			long newReward = Long.valueOf(reward).longValue()+incNum;
			newRewards+=String.valueOf(newReward)+",";
		}
		return newRewards.substring(0,newRewards.length()-1);
		
	}
	private int getLn(int i,int leftNum,HumanTemporaryData humanTemporaryData){
		int ln = 0;
		if(i == 1){
			ln= leftNum/1000+humanTemporaryData.getLn1();
		}else if(i == 2){
			ln= leftNum/1000+humanTemporaryData.getLn2();
		}else if(i == 3){
			ln= leftNum/1000+humanTemporaryData.getLn3();
		}else if(i == 4){
			ln= leftNum/1000+humanTemporaryData.getLn4();
		}else if(i == 5){
			ln= leftNum/1000+humanTemporaryData.getLn5();
		}
		return ln;
	}
	private void setNullLn(int i,HumanTemporaryData humanTemporaryData){
		if(i == 1){
			humanTemporaryData.setLn1(0);
		}else if(i == 2){
			humanTemporaryData.setLn2(0);
		}else if(i == 3){
			humanTemporaryData.setLn3(0);
		}else if(i == 4){
			humanTemporaryData.setLn4(0);
		}else if(i == 5){
			humanTemporaryData.setLn5(0);
		}
	}
	private void setValueLn(int i,int value,HumanTemporaryData humanTemporaryData){
		if(i == 1){
			humanTemporaryData.setLn1(value);
		}else if(i == 2){
			humanTemporaryData.setLn2(value);
		}else if(i == 3){
			humanTemporaryData.setLn3(value);
		}else if(i == 4){
			humanTemporaryData.setLn4(value);
		}else if(i == 5){
			humanTemporaryData.setLn5(value);
		}
	}
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType23 getScatterInfo(Human human){
		
		ScatterInfoType23 tempScatterInfo =new ScatterInfoType23();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		// 龙头的 个数
		int jackpotNum = 0;
		//列数
		int col = tempSlotsListTemplate.getColumns();
		//行数
		int row = tempSlotsListTemplate.getRows();

		for(int i=0;i< col;i++){
			
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = humanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for(int j=0;j<row;j++){
				
				int tempTurn =tempIthReelPos +j;
				
				//翻页了（循环起来一个圆）
				tempTurn = tempTurn%tempScrollList.size();
				
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE23){
					tempScatterInfo.getBonusList().add(i*row+j);//龙头的位置  的位置
					++jackpotNum;
					
				}
			}
		}
		tempScatterInfo.setBnus(jackpotNum);
		List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		if(list != null && list.size() >0){
			//先计算最大的配置的scatterMax
			int scatterMax = 0;
			ScatterTemplate maxScatterTemplate = null;
			for(ScatterTemplate tempScatterTemplate :list){
				int scatterNum = tempScatterTemplate.getScatterNum();
				if(scatterNum>scatterMax){
					scatterMax=scatterNum;
					maxScatterTemplate=tempScatterTemplate;
				}
			}
			//如果转动老虎机获得的scatter大于等于最大值，就去最大的 tempScatterTemplate
			if(tempFoundNum>=scatterMax){
				tempScatterInfo.setScatterTemplate(maxScatterTemplate);
				return tempScatterInfo;
			}
			for(ScatterTemplate tempScatterTemplate :list){
				int scatterNum = tempScatterTemplate.getScatterNum();
				if(tempFoundNum==scatterNum){
					tempScatterInfo.setScatterTemplate(tempScatterTemplate);
					return tempScatterInfo;
				}
			}
		}
		
		return tempScatterInfo;
	}

	
	
}
