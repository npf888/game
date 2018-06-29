package com.gameserver.bazoo.service.cow;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.enums.RoomState;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCCowEndUnifySwing;
import com.gameserver.bazoo.msg.GCStateRoomRoundResult;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.service.room.RoomService;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;

/**
 * 主要计算 牛牛 中 的色子是什么类型
 * 
 * 类型  
 * 	12、豹子 -- 五科色子相同   -- 10 倍倍率
 * 	11、五小牛 -- 五科色子 之和为10 -- 5倍 倍率
 * 	10、牛牛 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 10-- 4倍倍率
 * 	9、牛九 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 9-- 3倍倍率
 * 	8、牛八 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 8-- 2倍倍率
 * 	7、牛七 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 7-- 2倍倍率
 * 	6、牛六 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 6-- 2倍倍率
 * 	5、牛五 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 5-- 2倍倍率
 * 	4、牛四 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 4-- 2倍倍率
 * 	3、牛三 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 3-- 2倍倍率
 * 	2、牛二 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 2-- 2倍倍率
 * 	1、牛一 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 1-- 1倍倍率
 * 	0、无牛 -- 无三颗色子之和为10-- 1倍倍率
 * @author JavaServer
 *
 */
public class CowLogic {
	private static Logger logger = Loggers.BAZOO;
	
	
	//辅助类
	private AssistCowLogic assistCowLogic;
	public CowLogic(){
		assistCowLogic=new AssistCowLogic();
	}
	/**
	 * 比对一下看看 摇到的是啥
	 * @param roomEveryUserInfo
	 * @param dices
	 */
	public void compareName(RoomEveryUserInfo roomEveryUserInfo,List<Integer> dicesList){
		roomEveryUserInfo.getCowUserInfo().getRedDiceValues().clear();
		//三个 和为10的色子 会被标红
		List<Integer> threeDiceList = new ArrayList<Integer>();
		threeDiceList.clear();
		//这个只装 另外两个色子的值相加之后的个位数
		List<Integer> twoDiceList = new ArrayList<Integer>();
		//看看 摇到的是啥
		JudgeCowVO JudgeCowVO = this.judgeCow(dicesList,threeDiceList,twoDiceList);
		roomEveryUserInfo.getCowUserInfo().setCowName(JudgeCowVO.getCowName());
		roomEveryUserInfo.getCowUserInfo().setCowNum(Integer.valueOf(JudgeCowVO.getCowNum()));
		roomEveryUserInfo.getCowUserInfo().setRedDiceValues(threeDiceList);
	}
	/**
	 * 判断 是哪个 牛
	 * @param twoDiceList 
	 * @param threeDiceList2 
	 * @return
	 */
	public JudgeCowVO judgeCow(List<Integer> diceValues,List<Integer> threeDiceList,List<Integer> twoDiceList){
		//首先必须排序，不排序 算出来的 就是错误的
		Collections.sort(diceValues);
        //判断 是不是豹子
		if(panther(diceValues)){
			threeDiceList.addAll(diceValues);
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("豹子");
			JudgeCowVO.setCowNum("12");
			return JudgeCowVO;
		}
		//判断 是不是五小牛
		if(fiveLittleCow(diceValues)){
			threeDiceList.addAll(diceValues);
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("五小牛");
			JudgeCowVO.setCowNum("11");
			return JudgeCowVO;
		}
		//判断 是牛几
		int cowWhat = cowCowTen(diceValues,threeDiceList,twoDiceList);
		if(cowWhat == 10){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛牛");
			JudgeCowVO.setCowNum("10");
			return JudgeCowVO;
		}else if(cowWhat== 9){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛九");
			JudgeCowVO.setCowNum("9");
			return JudgeCowVO;
		}else if(cowWhat== 8){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛八");
			JudgeCowVO.setCowNum("8");
			return JudgeCowVO;
		}else if(cowWhat== 7){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛七");
			JudgeCowVO.setCowNum("7");
			return JudgeCowVO;
		}else if(cowWhat== 6){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛六");
			JudgeCowVO.setCowNum("6");
			return JudgeCowVO;
		}else if(cowWhat== 5){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛五");
			JudgeCowVO.setCowNum("5");
			return JudgeCowVO;
			
		}else if(cowWhat== 4){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛四");
			JudgeCowVO.setCowNum("4");
			return JudgeCowVO;
		}else if(cowWhat== 3){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛三");
			JudgeCowVO.setCowNum("3");
			return JudgeCowVO;
			
		}else if(cowWhat== 2){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛二");
			JudgeCowVO.setCowNum("2");
			return JudgeCowVO;
			
		}else if(cowWhat== 1){
			JudgeCowVO JudgeCowVO = new JudgeCowVO();
			JudgeCowVO.setCowName("牛一");
			JudgeCowVO.setCowNum("1");
			return JudgeCowVO;
		}
		
		JudgeCowVO JudgeCowVO = new JudgeCowVO();
		JudgeCowVO.setCowName("无牛");
		JudgeCowVO.setCowNum("0");
		return JudgeCowVO;
		
		
	}
	
	
	
	/**
	 * 豹子
	 * @param diceValues
	 */
	private boolean panther(List<Integer> diceValues){
		int value=0;
		int judge=0;
		for(Integer one:diceValues){
			if(one.intValue() != value){
				value=one.intValue();
				judge++;
			}
		}
		/**
		 * 说明是 豹子
		 */
		if(judge==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 五小牛
	 * @param diceValues
	 */
	private boolean fiveLittleCow(List<Integer> diceValues){
		int value=0;
		for(Integer one:diceValues){
				value+=one.intValue();
		}
		/**
		 * 说明是 五小牛
		 */
		if(value==10){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 牛牛
	 * @param diceValues
	 * @param twoDiceList 
	 */
	private int cowCowTen(List<Integer> diceValues,List<Integer> threeDiceList, List<Integer> twoDiceList){
		
		int[] diceValueArr = new int[diceValues.size()];
		for(int i=0;i<diceValues.size();i++){
			diceValueArr[i]=diceValues.get(i);
		}
        String str="";
        //求3个数的组合个数
        count(0,str,diceValueArr,3,diceValues,threeDiceList,twoDiceList);
		
        if(twoDiceList.size()>0){
        	return twoDiceList.get(0);
        }
		return 0;
	}
	
	
 	/**
 	 * 取数组中所有的组合
 	 * @param i
 	 * @param str
 	 * @param num
 	 */
    private static void count1(int i, String str, int[] num) {
        if(i==num.length){
        	System.out.println(str);
            return;
        }
        count1(i+1,str,num);
        count1(i+1,str+num[i]+",",num);
    }
    
    
    public static void main(String[] args){
    	
    }
    /**
 	 * 取数组中 n个数的组合
 	 * @param i
 	 * @param str
 	 * @param num
     * @param isRightList 
     * @param threeDiceList 
     * @param twoDiceList 
 	 */
    private static void count(int i, String str, int[] num,int n,List<Integer> diceValues, List<Integer> threeDiceList, List<Integer> twoDiceList) {
        if(n==0){
        	if(threeDiceList.size() > 0){
        		return;
        	}
            String[] strArr = str.split(",");
            //五个色子 三个的值
            int value1 = 0;
            //五个色子 两个的值
            int value2 = 0;
           	List<Integer> curThreeValueList = new ArrayList<Integer>();
            for(int j=0;j<strArr.length;j++){
            	String val = strArr[j];
            	if(StringUtils.isBlank(val)){
            		continue;
            	}
            	int one = Integer.valueOf(val);
            	curThreeValueList.add(one);
            	value1+=one;
//            	logger.info("[无双吹牛]---[牛牛模式]---[三个值之一::"+one+"---叠加 之和"+value1+"]");
            }
//            logger.info("[无双吹牛]---[牛牛模式]----------------------------------------111");2,2,5,5,6
            if(value1 == 10){
            	List<Integer> allDiceValues = new ArrayList<Integer>();
            	allDiceValues.addAll(diceValues);
            	
            	for(int j=0;j<curThreeValueList.size();j++){//移除三个  还剩两个
            		int diceV = curThreeValueList.get(j);
            		List<Integer> indexList = new ArrayList<Integer>();
            		for(int index=0;index<allDiceValues.size();index++){
            			int v = allDiceValues.get(index);
            			if(diceV == v){
            				indexList.add(index);
            				break;
            			}
            		}
            		allDiceValues.remove(indexList.get(0).intValue());
            	}
            	
            	//计算 除去上面三个 另外两个的值
            	for(int j=0;j<allDiceValues.size();j++){
        			int one =allDiceValues.get(j);
        			value2+=one;
//        			logger.info("[无双吹牛]---[牛牛模式]---[叠加 之和::"+value2+"]---[两个值之一::"+one+"]");
            	}
            	
            	//只能取个位
            	if(value2 > 10){
            		value2=value2-10;
            	}
            	logger.info("[无双吹牛]---[牛牛模式]---[]---[两个值之一::"+value2+"]");
            	if(value2 > 0){//value2的值等于几 说明 就是牛几
            		threeDiceList.addAll(curThreeValueList);
            		twoDiceList.add(value2);
            	}
            }
//            logger.info("[无双吹牛]---[牛牛模式]----------------------------------------222");
            return;
        }
        if(i==num.length){
            return;
        }
        count(i+1,str+num[i]+",",num,n-1,diceValues,threeDiceList,twoDiceList);
        count(i+1,str,num,n,diceValues,threeDiceList,twoDiceList);
    }



    /**
     * 通过房间号 结算
     * @param bazooPubService 
     * @param classicalRoom 
     * @param roomNum
     */
	public void endCount(Room room,BazooPubService bazooPubService) {
		RoomNumber roomNumber = room.getRoomNumber();
		PlayerInfo playerInfo = room.getPlayerInfo();
		//获得 partIn的用户
		List<Player> roomPlayerList = room.getPlayersPartIn();
		//先把庄家 找出来
		Player banker = playerInfo.getEveryRoundFirstCallMan();
		RoomEveryUserInfo RoomEveryUserInfoBanker = banker.getHuman().getBazooRoomEveryUserInfo();
		//然后 在一个一个和庄家 对比
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			if(p.getPassportId() == banker.getPassportId()){
				continue;
			}
			//庄家的点
			String bankCowName = RoomEveryUserInfoBanker.getCowUserInfo().getCowName();
			int bankCowNum = RoomEveryUserInfoBanker.getCowUserInfo().getCowNum();
			//其他人
			RoomEveryUserInfo RoomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
			String cowName = RoomEveryUserInfo.getCowUserInfo().getCowName();
			int cowNum = RoomEveryUserInfo.getCowUserInfo().getCowNum();
			//赢了 如果庄加 的cowNum 大于等于 其他人 的 就算赢
			if(bankCowNum >= cowNum){
				RoomEveryUserInfoBanker.getCowUserInfo().getLostPlayers().add(p);
				RoomEveryUserInfoBanker.getCowUserInfo().setRight(true);
				RoomEveryUserInfo.getCowUserInfo().setRight(false);
				RoomEveryUserInfo.getCowUserInfo().getWinPlayers().add(banker);
			}else{
				RoomEveryUserInfoBanker.getCowUserInfo().getWinPlayers().add(p);
				//输了
				RoomEveryUserInfoBanker.getCowUserInfo().setRight(false);
				RoomEveryUserInfo.getCowUserInfo().setRight(true);
				RoomEveryUserInfo.getCowUserInfo().getLostPlayers().add(banker);
			}
		}
		
		
		
		
		
		/**
		 * 计算钱
		 */
		CowUserInfo cowUserInfo = RoomEveryUserInfoBanker.getCowUserInfo();
		//庄家 赢得人
		List<Player> lostPlayers = cowUserInfo.getLostPlayers();
		//庄家 输得人
		List<Player> winPlayers = cowUserInfo.getWinPlayers();
		int bet = roomNumber.getBet();
		/**
		 * 先算赢了多少钱
		 */
		long totalWinGold = 0l; 
		for(Player p:lostPlayers){
			int multiple = this.getMultiple(cowUserInfo.getCowNum());
			//通杀
			if(winPlayers.size()==0 && roomPlayerList.size()>=4){
				multiple=multiple*2;
			}
			long gold = bet*multiple;
			totalWinGold+=gold;
			p.getHuman().getBazooRoomEveryUserInfo().setMultiple(multiple);
			p.getHuman().getBazooRoomEveryUserInfo().setMoney(-gold);
			
			//给用户减钱
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_COW_COST.getReasonText(), roomNumber.toString(),gold);
			p.getHuman().costMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_COW_COST, detailReason, -1, 1);
			
			//排行榜
			Globals.getHumanBankService().addGold(p, -gold);
		}
		
		
		
		
		
		
		
		/**
		 * 再算 输了多少钱
		 */
		long totalLostGold = 0l; 
		for(Player p:winPlayers){
			CowUserInfo cow = p.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo();
			int multiple = this.getMultiple(cow.getCowNum());
			long gold = bet*multiple;
			totalLostGold+=gold;
			
			p.getHuman().getBazooRoomEveryUserInfo().setMultiple(multiple);
			p.getHuman().getBazooRoomEveryUserInfo().setMoney(gold);
			
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_COW_WIN.getReasonText(), roomNumber.toString(),gold);
			p.getHuman().giveMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_COW_WIN, detailReason, -1, 1);
			
			//扣掉 赢家的 流水
			bazooPubService.costWinnerMoney(p,gold);
			//排行榜
			Globals.getHumanBankService().addGold(p, gold);
		}
		
		
		
		
		
		
		
		// 庄家的 钱 看这里  正数就是赢    负数 就是输 还有就是   给庄家设置上参数
		long totalMoney = totalWinGold-totalLostGold;
		long multiple1 = totalMoney/bet;
		RoomEveryUserInfoBanker.setMultiple(Long.valueOf(multiple1).intValue());
		RoomEveryUserInfoBanker.setMoney(totalMoney);
		
		if(totalMoney >0){
			
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_COW_WIN.getReasonText(), roomNumber.toString(),totalMoney);
			banker.getHuman().giveMoney(totalMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_COW_WIN, detailReason, -1, 1);
			
			//扣掉 庄家的 流水
			bazooPubService.costWinnerMoney(banker,totalMoney);
			
			
			//排行榜
			Globals.getHumanBankService().addGold(banker, totalMoney);
		}else{
			
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_COW_COST.getReasonText(), roomNumber.toString(),Math.abs(totalMoney));
			banker.getHuman().costMoney(Math.abs(totalMoney), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_COW_COST, detailReason, -1, 1);
			
			//排行榜
			Globals.getHumanBankService().addGold(banker, totalMoney);
		}
		
		
		
		
		
		//发送消息
		GCCowEndUnifySwing GCCowEndUnifySwing=getEndMessage(roomPlayerList,banker);; 
		for(int i=0;i<room.getPlayers().size();i++){
			Player p = room.getPlayers().get(i);
			p.sendMessage(GCCowEndUnifySwing);
		}
		
		//设置状态
		EndCountInfo[] EndCountInfoArr = GCCowEndUnifySwing.getEndCountInfo();
		GCStateRoomRoundResult GCStateRoomRoundResult = new GCStateRoomRoundResult();
		GCStateRoomRoundResult.setEndCountInfo(EndCountInfoArr);
		room.getRoomStateInfo().setRoomState(RoomState.CowState.stateRoomRoundResult,GCStateRoomRoundResult);//结算的状态
		
		//计算连胜
		bazooPubService.countWinTimes(room.getPlayersPartIn(),RoomNumber.MODE_TYPE_COW);
		
		for(Player player:roomPlayerList){
			//任务 或者成就
			Globals.getHumanBazooTaskService().finishTask(player);
		}
		//个人的 信息变化记录
		for(Player p:room.getPlayersPartIn()){
			Globals.getHumanBazooPersonalService().addGold(p, p.getHuman().getBazooRoomEveryUserInfo().getMoney(),null);
		}
	}

	private GCCowEndUnifySwing getEndMessage(List<Player> roomPlayerList,Player banker){
		GCCowEndUnifySwing GCCowEndUnifySwing = new GCCowEndUnifySwing();
		EndCountInfo[] endCountInfoArr = new EndCountInfo[roomPlayerList.size()];
		for(int i=0;i<roomPlayerList.size();i++){
			Player p = roomPlayerList.get(i);
			RoomEveryUserInfo roomEveryUserInfo = p.getHuman().getBazooRoomEveryUserInfo();
			EndCountInfo EndCountInfo = new EndCountInfo();
			List<Integer> dices = roomEveryUserInfo.getDiceValues();
			
			int[] diceArr =DiceUtils.getArrFromDiceList(dices);
			EndCountInfo.setDiceValues(diceArr);
			EndCountInfo.setGold(roomEveryUserInfo.getMoney());
			EndCountInfo.setMultiple(roomEveryUserInfo.getMultiple());
			EndCountInfo.setName(roomEveryUserInfo.getCowUserInfo().getCowName());
			EndCountInfo.setPassportId(p.getPassportId());
			EndCountInfo.setPersonTotalGold(p.getHuman().getGold());
			List<Player> ppWinList = roomEveryUserInfo.getCowUserInfo().getWinPlayers();
			if(ppWinList.size()>0){
				int[] winPassportId = new int[ppWinList.size()];
				int[] winMultiple = new int[ppWinList.size()];
				for(int j=0;j<ppWinList.size();j++){
					Player pp = ppWinList.get(j);
					winPassportId[j]=Long.valueOf(pp.getPassportId()).intValue();
					if(p.getPassportId() == banker.getPassportId()){//庄家
						winMultiple[j]=Long.valueOf(pp.getHuman().getBazooRoomEveryUserInfo().getMultiple()).intValue();
					}else{//普通用户
						//这个倍数 要取 自己 输的那个 倍数
						winMultiple[j]=Long.valueOf(p.getHuman().getBazooRoomEveryUserInfo().getMultiple()).intValue();
					}
					
				}
				EndCountInfo.setWinPassportId(winPassportId);
				EndCountInfo.setWinMultiple(winMultiple);
			}else{
				EndCountInfo.setWinPassportId(new int[0]);
				EndCountInfo.setWinMultiple(new int[0]);
			}
			endCountInfoArr[i]=EndCountInfo;
		}
		GCCowEndUnifySwing.setEndCountInfo(endCountInfoArr);
		
		return GCCowEndUnifySwing;
	}

	/**
	 * 获取赔率
	 * 	1、豹子 -- 五科色子相同   -- 10 倍倍率
	 * 	2、五小牛 -- 五科色子 之和为10 -- 5倍 倍率
	 * 	3、牛牛 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 10-- 4倍倍率
	 * 	4、牛九 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 9-- 3倍倍率
	 * 
	 * 	5、牛八 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 8-- 2倍倍率
	 * 	6、牛七 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 7-- 2倍倍率
	 * 	7、牛六 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 6-- 2倍倍率
	 * 	8、牛五 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 5-- 2倍倍率
	 * 	9、牛四 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 4-- 2倍倍率
	 * 	10、牛三 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 3-- 2倍倍率
	 * 	11、牛二 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 2-- 2倍倍率
	 * 
	 * 	12、牛一 -- 任意三颗色子之和 为10 其余两颗色子 之和 也为 1-- 1倍倍率
	 * 	13、无牛 -- 无三颗色子之和为10-- 1倍倍率
	 */
	private int getMultiple(int cowNumber){
		if(cowNumber == 1){
			return 1;
		}else if(cowNumber == 2){
			return 1;
		}else if(cowNumber == 3){
			return 1;
		}else if(cowNumber == 4){
			return 1;
			
		}else if(cowNumber == 5){
			return 1;
		}else if(cowNumber == 6){
			return 1;
		}else if(cowNumber == 7){
			return 1;
		}else if(cowNumber == 8){
			return 2;
		}else if(cowNumber == 9){
			return 3;
		}else if(cowNumber == 10){
			return 4;
		}else if(cowNumber == 11){
			return 5;
			
		}else if(cowNumber == 12){
			return 10;
		}else{
			return 1;
			
		}
		
		
	}
}
