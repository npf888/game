package com.robot.bazoo.blackWhite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.data.BetTotalNum;
import com.gameserver.bazoo.data.BlackWhiteBet;
import com.gameserver.bazoo.data.BlackWhiteDiceInfo;
import com.gameserver.bazoo.data.BlackWhiteNum;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.data.ReturnPlayerInfo;
import com.gameserver.bazoo.msg.CGBlackWhiteCallNum;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomOut;
import com.gameserver.bazoo.msg.CGRoomPriJoin;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
import com.gameserver.bazoo.msg.GCBlackWhiteAllSwing;
import com.gameserver.bazoo.msg.GCBlackWhiteCallNum;
import com.gameserver.bazoo.msg.GCBlackWhiteEndCount;
import com.gameserver.bazoo.msg.GCBlackWhiteWhoTurn;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.util.DiceUtils;
import com.robot.Robot;
import com.robot.bazoo.data.BlackWhiteSort;

public class BazooBlackWhiteMessageHandler {

	protected Logger logger = Loggers.BAZOO;
	
	private boolean cgping= true;
	public void execute(Robot robot, IMessage message) {
		
		/**
		 * 告诉机器人 进入哪个房间
		 */
		if(message instanceof GCRobotWhichRoomToGoin){
			executeGCRobotWhichRoomToGoin(robot,(GCRobotWhichRoomToGoin)message);
		}
		
		/**
		 * 返回模式
		 */
		if(message instanceof GCModeChose){
			executeGCModeChose(robot,(GCModeChose)message);
		}
		
		/**
		 * 进入房间 返回
		 */
		if(message instanceof GCRoomInit){
			executeGCRoomInit(robot,(GCRoomInit)message);
			/*try{
				Thread.sleep(8500);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			CGRoomOut CGRoomOut = new CGRoomOut();
			CGRoomOut.setBet(5000);
			robot.sendMessage(CGRoomOut);*/
		}
		/**
		 * 进入房间 返回  匹配上的消息
		 */
		if(message instanceof GCRoomMatched){
			executeGCRoomMatched(robot,(GCRoomMatched)message);
		}
		
		/**
		 * 退出房间
		 */
		if(message instanceof GCRoomOut){
			executeGCRoomOut(robot,(GCRoomOut)message);
		}
		
		//统一摇色子  和 单独 摇色子 返回
		if(message instanceof GCBlackWhiteAllSwing){//匹配完 由服务器 统一摇色子  每次叫号 玩 也会统一摇色子 
			executeGCBlackWhiteAllSwing(robot,(GCBlackWhiteAllSwing)message);
			
		}else if(message instanceof GCBlackWhiteWhoTurn){//收到 最小摇号的 用户
			executeGCBlackWhiteWhoTurn(robot,(GCBlackWhiteWhoTurn)message);
			
		}else if(message instanceof GCBlackWhiteCallNum){//单独摇完色子 的返回值
			executeGCBlackWhiteCallNum(robot,(GCBlackWhiteCallNum)message);
			
			
		}else if(message instanceof GCBlackWhiteEndCount){//最终结算
			executeGCBlackWhiteEndCount((GCBlackWhiteEndCount)message);
			
		}
	
		
		
	}
	
	/**
	 * 告诉机器人 进入哪个房间
	 * @param robot
	 * @param message
	 */
	private void executeGCRobotWhichRoomToGoin(Robot robot,
			GCRobotWhichRoomToGoin message) {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
		
		}
		if(Long.valueOf(robot.getPassportId()).longValue() != message.getPassportId()){
			return;
		}
		String roomNumber = message.getRoomNumber();
		if(roomNumber.split("_")[1].equals(""+RoomNumber.PUB_ROOM)){//公共 房间
			CGRoomPubJoin CGRoomPubJoin = new CGRoomPubJoin();
			CGRoomPubJoin.setRoomNumber(roomNumber);
			robot.sendMessage(CGRoomPubJoin);
		}else{//私人房间
			CGRoomPriJoin CGRoomPriJoin = new CGRoomPriJoin();
			CGRoomPriJoin.setRoomNumber(roomNumber);
			robot.sendMessage(CGRoomPriJoin);
		}
	}
	
	private void executeGCBlackWhiteEndCount(GCBlackWhiteEndCount message) {
		EndCountInfo[] EndCountInfoArr = message.getEndCountInfo();
		
		for(int i=0;i<EndCountInfoArr.length;i++){
			EndCountInfo EndCountInfo= EndCountInfoArr[i];
			int[] diceValues = EndCountInfo.getDiceValues();
			String dv = "";
			for(int j=0;j<diceValues.length;j++){
				dv+=diceValues[j]+",";
			}
			long gold = EndCountInfo.getGold();
			int multiple = EndCountInfo.getMultiple();
			long passportId = EndCountInfo.getPassportId();
			String name = EndCountInfo.getName();
			int[] winMultiple=	EndCountInfo.getWinMultiple();
			int[] winPassportId=	EndCountInfo.getWinPassportId();
			logger.info("[无双吹牛]---[梭哈模式-结算]---[用户ID::"+passportId+"]---[名称：："+name+"]"+"---[multiple::"+multiple+"]---[gold::"+gold+"]"+"[色子值::"+dv+"]");
		}
		
	}


	/**
	 * 用户单独摇完 色子 的返回值
	 * @param robot
	 * @param message
	 */
	private void executeGCBlackWhiteCallNum(Robot robot,
			GCBlackWhiteCallNum message) {
		long whoCallPassportId = message.getWhoCallPassportId();
		int diceType = message.getDiceType();
		int killNum = message.getKillNum();
		int multiple = message.getMultiple();
		BlackWhiteBet[] BlackWhiteBetArr = message.getBlackWhiteBet();
		for(BlackWhiteBet bet:BlackWhiteBetArr){
			int[] multiple1 = bet.getBet();
			long gold = bet.getGold();
			long passportId = bet.getPassportId();
			int[] winpassportIds = bet.getWinPassportIds();
			String multiple1Str = "";
			for(int i=0;i<multiple1.length;i++){
				multiple1Str+=multiple1[i];
			}
			String winpassportIdsstr = "";
			for(int i=0;i<winpassportIds.length;i++){
				winpassportIdsstr+=winpassportIds[i];
			}
			logger.info("[无双吹牛]---[红黑单双模式]---[multiple1::"+multiple1Str+"]--[gold："+gold+"][passportId:"+passportId+"][personTotalGold:"+winpassportIdsstr+"]");
		}
		BlackWhiteDiceInfo[] diceInfoArr = message.getBlackWhiteDiceInfo();
		for(int i=0;i<diceInfoArr.length;i++){
			BlackWhiteDiceInfo DiceInfo =diceInfoArr[i];
			long passportId = DiceInfo.getPassportId();
			int isOut = DiceInfo.getIsOut();
			int[] diceArr = DiceInfo.getDiceValues();
			String diceStr = "";
			for(int j=0;j<diceArr.length;j++){
				diceStr+=diceArr[j]+",";
			}
			logger.info("[无双吹牛]---[红黑单双模式]---[摇色子用户ID  ::"+passportId+"]--[色子值："+diceStr+"][isOut:"+isOut+"]");
			int[] removeDiceArr = DiceInfo.getRemoveDiceValues();
			String removeDiceStr = "";
			for(int j=0;j<removeDiceArr.length;j++){
				removeDiceStr+=removeDiceArr[j]+",";
			}
			logger.info("[无双吹牛]---[红黑单双模式]---[摇色子用户ID  ::"+passportId+"]--[移除色子值 index："+removeDiceStr+"]");
			logger.info("[无双吹牛]---[红黑单双模式]---[----------------------------------------------------------]");
		}
		
		logger.info("[无双吹牛]---[红黑单双模式]---[当前用户ID  ::"+robot.getPassportId()+"]");
		logger.info("[无双吹牛]---[红黑单双模式]---[谁叫的    ID  ::"+whoCallPassportId+"]");
		logger.info("[无双吹牛]---[红黑单双模式]---[叫的diceType::"+diceType+"]---[几杀::"+killNum+"][倍数:"+multiple+"]");
		
	}


	
	

	/**
	 * 返回 最小的 摇色子的人
	 * @param robot
	 * @param message
	 */
	private void executeGCBlackWhiteWhoTurn(Robot robot,
			GCBlackWhiteWhoTurn message) {
		long leftSecond = message.getLeftSecond();
		long whoTurnPassportId = message.getWhoTurnPassportId();
		logger.info("[无双吹牛]---[红黑单双模式]---[当前用户ID::"+robot.getPassportId()+"]---[轮到谁：："+whoTurnPassportId+"]"+"---[剩余时间::"+leftSecond+"]");
		//轮到自己就出手
		if(robot.getPassportId().equals(String.valueOf(whoTurnPassportId))){
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			CGBlackWhiteCallNum CGBlackWhiteCallNum = new CGBlackWhiteCallNum();
			CGBlackWhiteCallNum.setDiceType(replaceUserCall(robot));
//			CGBlackWhiteCallNum.setDiceType(2);
			robot.sendMessage(CGBlackWhiteCallNum);
			logger.info("[无双吹牛]---[红黑单双模式]---[机器人叫号::]");
		}
		
	}
	
	private int replaceUserCall(Robot robot){
		List<Integer> list = new ArrayList<Integer>();
		String str = "";
		List<Integer> diceValue = robot.getBazooTemp().getDiceValues();
		for(int i=0;i<diceValue.size();i++){
			list.add(diceValue.get(i));
			str+=diceValue.get(i)+",";
		}
		logger.info("[无双吹牛]---[红黑单双模式]---[replaceUserCall-当前色子值::"+str+"]");
		BlackWhiteNum BlackWhiteNum = this.setNum(list);
		//排序
		List<BlackWhiteSort> sortList = sort(BlackWhiteNum);
		for(BlackWhiteSort sort:sortList){
			logger.info("[无双吹牛]---[sort]---[name::"+sort.getName()+"][num::"+sort.getNum()+"]");
		}
		//取第一个
		BlackWhiteSort BlackWhiteSort = getSort(sortList);
		if(BlackWhiteSort == null){
			logger.info("[无双吹牛]---[红黑单双模式 0]---[啥玩没有]");
			return 0;
		}
		if(BlackWhiteSort.getName().equals("red")){
			logger.info("[无双吹牛]---[红黑单双模式 1]---[list::"+list.size()+"]"+"[getRed::"+BlackWhiteSort.getNum()+"]");
			return 1;
		}else if(BlackWhiteSort.getName().equals("black")){
			logger.info("[无双吹牛]---[红黑单双模式 2]---[list::"+list.size()+"]"+"[getBlack::"+BlackWhiteSort.getNum()+"]");
			return 2;
		}else if(BlackWhiteSort.getName().equals("single")){
			logger.info("[无双吹牛]---[红黑单双模式 3]---[list::"+list.size()+"]"+"[getSingle::"+BlackWhiteSort.getNum()+"]");
			return 3;
		}else if(BlackWhiteSort.getName().equals("doubles")){
			logger.info("[无双吹牛]---[红黑单双模式 4]---[list::"+list.size()+"]"+"[getDoubles::"+BlackWhiteSort.getNum()+"]");
			return 4;
		}
		
		
		
		logger.info("[无双吹牛]---[红黑单双模式 0000]---[啥玩没有]");
		return 0;
	}
		
	private BlackWhiteSort getSort(
			List<BlackWhiteSort> sortList) {
		if(sortList == null || sortList.size() <=0){
			return null;
		}
		return getBlackWhiteSort(sortList);
		
	}

	
	private BlackWhiteSort getBlackWhiteSort(List<BlackWhiteSort> sortList) {
		BlackWhiteSort sortOne = sortList.get(0);
		BlackWhiteSort sortTwo = sortList.get(1);
		BlackWhiteSort sortThree = sortList.get(2);
		BlackWhiteSort sortFour = sortList.get(3);
		
		//优先级  黑 单或双 红
		if(sortOne.getNum() != sortTwo.getNum()){//前俩 不相等，那就 取出第一个
			return sortOne;
		}else if(sortOne.getNum() == sortTwo.getNum() 
				&& sortTwo.getNum() == sortThree.getNum()
				&& sortThree.getNum() == sortFour.getNum()){//四个 都 相等
			if(sortOne.getName().equals("black")){//黑的优先级最高
				return sortOne;
			}else if(sortTwo.getName().equals("black")){
				return sortTwo;
			}else if(sortThree.getName().equals("black")){
				return sortThree;
			}else if(sortFour.getName().equals("black")){
				return sortFour;
			}
		}else if(sortOne.getNum() == sortTwo.getNum() 
				&& sortTwo.getNum() == sortThree.getNum()){//前三个 都 相等
			//有黑的就把 黑的取出来
			if(sortOne.getName().equals("black")){//黑的优先级最高
				return sortOne;
			}else if(sortTwo.getName().equals("black")){
				return sortTwo;
			}else if(sortThree.getName().equals("black")){
				return sortThree;
			}
			
			//没有黑的 说明  前三个 肯定是  单双  红
			int x = RandomUtil.nextInt(0,2);
			if(x==0){//取 单的
				if(sortOne.getName().equals("single")){
					return sortOne;
				}else if(sortTwo.getName().equals("single")){
					return sortTwo;
				}else if(sortThree.getName().equals("single")){
					return sortThree;
				}
			}else{
				if(sortOne.getName().equals("doubles")){
					return sortOne;
				}else if(sortTwo.getName().equals("doubles")){
					return sortTwo;
				}else if(sortThree.getName().equals("doubles")){
					return sortThree;
				}
			}
		}else if(sortOne.getNum() == sortTwo.getNum() ){//两个 相等
			//有黑的就把 黑的取出来
			if(sortOne.getName().equals("black")){//黑的优先级最高
				return sortOne;
			}else if(sortTwo.getName().equals("black")){
				return sortTwo;
			}
			
			if(sortOne.getName().equals("single") && sortTwo.getName().equals("doubles")){
				//没有黑的 说明  前三个 肯定是  单双  红
				int x = RandomUtil.nextInt(0,2);
				if(x==0){//取 单的
					return sortOne;
				}
				return sortTwo;
				
			}
			if(sortOne.getName().equals("doubles") && sortTwo.getName().equals("single")){
				//没有黑的 说明  前三个 肯定是  单双  红
				int x = RandomUtil.nextInt(0,2);
				if(x==0){//取 单的
					return sortOne;
				}
				return sortTwo;
			}
			
			if(!sortOne.getName().equals("red")){
				return sortOne;
			}
			if(!sortTwo.getName().equals("red")){
				return sortTwo;
			}
		}
		
		return sortList.get(0);
	}

	private List<BlackWhiteSort> sort(BlackWhiteNum BlackWhiteNum) {
		List<BlackWhiteSort> sortList = new ArrayList<BlackWhiteSort>();
		BlackWhiteSort BlackWhiteSortRed = new BlackWhiteSort();
		BlackWhiteSortRed.setName("red");
		BlackWhiteSortRed.setNum(BlackWhiteNum.getRed());
		sortList.add(BlackWhiteSortRed);
		BlackWhiteSort BlackWhiteSortBlack = new BlackWhiteSort();
		BlackWhiteSortBlack.setName("black");
		BlackWhiteSortBlack.setNum(BlackWhiteNum.getBlack());
		sortList.add(BlackWhiteSortBlack);
		BlackWhiteSort BlackWhiteSortSingle = new BlackWhiteSort();
		BlackWhiteSortSingle.setName("single");
		BlackWhiteSortSingle.setNum(BlackWhiteNum.getSingle());
		sortList.add(BlackWhiteSortSingle);
		BlackWhiteSort BlackWhiteSortDoubles = new BlackWhiteSort();
		BlackWhiteSortDoubles.setName("doubles");
		BlackWhiteSortDoubles.setNum(BlackWhiteNum.getDoubles());
		sortList.add(BlackWhiteSortDoubles);
		
        Collections.sort(sortList, new Comparator<BlackWhiteSort>() {

			@Override
			public int compare(BlackWhiteSort o1, BlackWhiteSort o2) {
				if(o1.getNum() > o2.getNum()){
					return 1;
				}else if(o1.getNum() < o2.getNum()){
					return -1;
				}else{
					return 0;
				}
			}
		 });
		return sortList;
	}

	/**
	 *
	 * 1:红色点数：1、4
	 * 2:黑色点数：2、3、5、6
	 * 
	 * 3:单数点数：1、3、5
	 * 4:双数点数：2、4、6
	 *
	 *
	 */
	public BlackWhiteNum setNum(List<Integer> diceValues){
		int red=0;
		int black=0;
		int single=0;
		int doubles=0;
		for(int i=0;i<diceValues.size();i++){
			int value = diceValues.get(i);
			if(value == 1 || value == 4){//红色
				red++;
			}
			if(value == 2 || value == 3 || value == 5 || value == 6){//黑色色
				black++;
			}
			if(value == 1 || value == 3 || value == 5 ){//单
				single++;
			}
			if(value == 2 || value == 4 || value == 6){//双
				doubles++;
			}
		}
		
		BlackWhiteNum BlackWhiteNum = new BlackWhiteNum();
		BlackWhiteNum.setRed(red);
		BlackWhiteNum.setBlack(black);
		BlackWhiteNum.setSingle(single);
		BlackWhiteNum.setDoubles(doubles);
		return BlackWhiteNum;
	}
	
	/**
	 * 统一摇色子 返回
	 * @param robot
	 * @param message
	 */
	private void executeGCBlackWhiteAllSwing(Robot robot,
			GCBlackWhiteAllSwing message) {
		BlackWhiteNum BlackWhiteNum = message.getBlackWhiteNum();
		
		long[] passportIds = message.getPassportId();
		String passportIdstr = "";
		for(int i=0;i<passportIds.length;i++){
			passportIdstr+=passportIds[i]+",";
		}
		int[] diceValues = message.getDiceValues();
		
		robot.getBazooTemp().getDiceValues().clear();
		robot.getBazooTemp().getDiceValues().addAll(DiceUtils.getListFromArr(diceValues));
		String str = "";
		for(int i=0;i<diceValues.length;i++){
			str+=diceValues[i]+",";
		}
		logger.info("[无双吹牛]---[红黑单双 模式]---[当前用户：ID"+robot.getPassportId()+"-"+passportIdstr+"]---[当前色子值::"+str+"]");
		logger.info("[无双吹牛]---[红黑单双 模式]---["+BlackWhiteNum.getRed()+"]");
		logger.info("[无双吹牛]---[红黑单双 模式]---["+BlackWhiteNum.getBlack()+"]");
		logger.info("[无双吹牛]---[红黑单双 模式]---["+BlackWhiteNum.getSingle()+"]");
		logger.info("[无双吹牛]---[红黑单双 模式]---["+BlackWhiteNum.getDoubles()+"]");
		
	}

	

	
	
	
	
	
	
	private void executeGCRoomMatched(Robot robot, GCRoomMatched message) {
		int waitTime = message.getWaitTime();
		logger.info("[无双吹牛]---[匹配上]---[当前用户ID::"+robot.getPassportId()+"]---[等待时间::"+waitTime+"]");
		
	}

	/**
	 * 选择模式后  去进入房间
	 * @param robot
	 * @param GCModeChose
	 */
	private void executeGCModeChose(Robot robot,GCModeChose GCModeChose){
		BetTotalNum[] BetTotalNumArr = GCModeChose.getBetTotalNum();
		for(int i=0;i<BetTotalNumArr.length;i++){
			BetTotalNum BetTotalNum = BetTotalNumArr[i];
			logger.info("[无双吹牛]---[模式场次]---[当前bet::"+BetTotalNum.getBet()+"]---[当前总人数::"+BetTotalNum.getTotalNum()+"]");
		}
		
		//AllClassicalCowShowHandMain   RobotBazooClassicalMain  RobotBazooCowMain  RobotBazooShowHandMain 调用
		if(robot.getBazooTemp().getWayOfType().equals("normal")){
			CGRoomEnter CGRoomEnter =  new CGRoomEnter();
			CGRoomEnter.setBet(robot.getBazooTemp().getBet());
			robot.sendMessage(CGRoomEnter);
		}else{//RobotBazooClassicalRemote 这个调用
			//根据房间号 决定要去哪个 房间
			CGRoomPubJoin CGRoomPubJoin = new CGRoomPubJoin();
			CGRoomPubJoin.setRoomNumber(robot.getBazooTemp().getData().getRoomNumber());
			robot.sendMessage(CGRoomPubJoin);
		}
	}

	/**
	 * 进入房间 返回值
	 * @param GCRoomEnter
	 */
	private void executeGCRoomInit(Robot robot,GCRoomInit GCRoomInit){
		logger.info("[无双吹牛]---[进入房间]---[当前房间号:"+GCRoomInit.getRoomNum()+"]---[用户ID::"+robot.getPassportId()+" 的人进入房间]");
		/*int status = GCRoomInit.getStatus();
		if(status == 1){
			logger.info("[无双吹牛]---[房间状态]---[进行中... ...]");
		}else{
			logger.info("[无双吹牛]---[房间状态]---[未开始... ...]");
		}*/
		
		logger.info("[无双吹牛]---[房间参数1]---[PubOrPri::"+GCRoomInit.getPubOrPri()+"]");
		logger.info("[无双吹牛]---[房间参数2]---[LastPassportId::"+GCRoomInit.getLastPassportId()+"]");
		logger.info("[无双吹牛]---[房间参数3]---[OnePoint::"+GCRoomInit.getOnePoint()+"]");
		logger.info("[无双吹牛]---[房间参数4]---[GuessWait::"+GCRoomInit.getGuessWait()+"]");
		logger.info("[无双吹牛]---[房间参数5]---[WaitTime::"+GCRoomInit.getWaitTime()+"]");
		logger.info("[无双吹牛]---[房间参数6]---[TurnWait::"+GCRoomInit.getTurnWait()+"]");
		
		
		ReturnPlayerInfo[] allPlayerInfo = GCRoomInit.getReturnPlayerInfo();
		for(int i=0;i<allPlayerInfo.length;i++){
			ReturnPlayerInfo oInfo= allPlayerInfo[i];
			logger.info("[无双吹牛]---[ReturnPlayerInfo::ID"+oInfo.getPassportId()+"---UserStatus:"+oInfo.getUserStatus()+"]");
			logger.info("[无双吹牛]---当前其他用户的ID:"+oInfo.getPassportId()+"---当前其他用户的名称:"+oInfo.getName()+"---"+oInfo.getCurGold()+"-"+oInfo.getHeadImg()+"-"+oInfo.getDiceContainer());
		}
	}
	
	/**
	 * 退出房间
	 * @param robot
	 */
	private void executeCGRoomOut(Robot robot) {
		CGRoomOut CGRoomOut =  new CGRoomOut();
		CGRoomOut.setBet(1000);
		robot.sendMessage(CGRoomOut);
		logger.info("[无双吹牛]---当前用户的ID:"+robot.getPassportId()+"---退出房间");
		
	}
	
	
	/**
	 * 房间里别的用户收到退出房间的信息
	 * @param message
	 */
	private void executeGCRoomOut(Robot robot,GCRoomOut message) {
		logger.info("[无双吹牛]---当前用户的ID:"+robot.getPassportId()+"---那个谁：："+message.getBeRemovedPassportIds()+"---退出了");
	}
	
	
}
