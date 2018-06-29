package com.gameserver.bazoo.service.classical;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.handler.BazooHandlerFactory;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

// 房间--用户--玩耍 信息
public class ClassicalUserInfo {
	private Logger logger = Loggers.BAZOO;
	//色子的个数
	private int diceNumber;
	//色子的 值
	private int diceValue;
	//是否 跟着 押大 或者押 小了    默认为 false 没有
	private boolean ifGuess=false;
	//猜对right     还是       猜错false
	private boolean isRight;
	
	
	
	//当前正在倒计时的 线程
//	private Thread thread;
	//是否停止线程 默认不停止
//	private boolean stop;
	
	
	
	//是不是当前用户在叫号
	private boolean isCurUser;
	
	
	public ClassicalUserInfo(){
		init();
	}
	/**
	 * 每次重摇 都初始化一些信息
	 */
	public void init(){
//		stop=false;
		isCurUser=false;
		diceNumber=0;
		diceValue=0;
		ifGuess=false;
		isRight=false;
	}

	
	
	/**
	 * 时间 每个用户每一次叫号的 时间，到了就自动下一个
	 * @return
	 */
	/*public  void  setTime(int nextTime,int totalMem,Player nextPlayer,Player nextNextPlayer,PlayerInfo playerInfo,RoomNumber roomNumber){
		stop=false;
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[创建新的线程]---[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
			this.thread=new Thread(new Runnable() {
				@Override
				public void run() {
				
					if(!stop){
							try{
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程-睡前]---[线程名称::"+Thread.currentThread().getName()+"--状态stop::"+stop+"]---[等待用户::"+nextPlayer.getPassportId()+"-"+nextPlayer.getHuman().getName()+"]");
								Thread.sleep((nextTime+1)*1000);
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程-睡后]---[线程名称::"+Thread.currentThread().getName()+"--状态stop::"+stop+"]---[等待用户::"+nextPlayer.getPassportId()+"-"+nextPlayer.getHuman().getName()+"]");
							}catch(Exception e){
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[被唤醒]---[状态stop::"+stop+"]---[等待用户::"+nextPlayer.getPassportId()+"---[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
							}
								
							if(stop){
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[被停止]---[状态stop::"+stop+"]---[等待用户::"+nextPlayer.getPassportId()+"[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
								return;
							}
							//超过三秒结束(后台的时间要大于前台的时间)
						
							logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[由服务器替用户叫号]---[--------------------------------------------------------------------]");
							Player lastPlayer = playerInfo.getLastMan();
							CGTalkBig CGTalkBig = new CGTalkBig();
							if(lastPlayer == null){//一局 开始一个人 没有叫 后台 给他设定一个值
								CGTalkBig.setDiceNum(totalMem);
								CGTalkBig.setDiceValue(2);
							}else{
								//色子总共的个数
								int maxNum = totalMem*6;
								int maxValue = 6;
								RoomEveryUserInfo lastRoomEveryUserInfo= lastPlayer.getHuman().getBazooRoomEveryUserInfo();
								int lastDiceNumber = lastRoomEveryUserInfo.getClassicalUserInfo().getDiceNumber();
								int lastDiceValue = lastRoomEveryUserInfo.getClassicalUserInfo().getDiceValue();
								int lastDiceNumber1=lastDiceNumber+1;
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[最大人数]---[lastDiceNumber:"+lastDiceNumber+"][lastDiceValue:"+lastDiceValue+"]");
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[最大人数]---[lastDiceNumber1:"+lastDiceNumber1+"][maxNum:"+maxNum+"]");
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[最大人数]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
								//个数已经到最大了 和点数 都到最大了
								if(lastDiceNumber1 >= maxNum && lastDiceValue == maxValue){
									logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[最后结算]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
									//去抢开
									Globals.getBazooMService().robOpen(nextPlayer);
									return;
								}
								//点数到最大  个数 还没有到最大
								if(lastDiceNumber1 >= maxNum && lastDiceValue < maxValue){
									logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[设置参数]---[lastDiceValue:"+lastDiceValue+"][maxValue:"+maxValue+"]");
									lastDiceNumber1=maxNum;
									lastDiceValue+=1;
								}
								
								CGTalkBig.setDiceNum(lastDiceNumber1);
								CGTalkBig.setDiceValue(lastDiceValue);
							}
							
							Globals.getBazooMService().talkBig(nextPlayer, CGTalkBig.getDiceNum(), CGTalkBig.getDiceValue(),1);
//							BazooHandlerFactory.getHandler().handleTalkBig(nextPlayer, CGTalkBig,1);
							logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[执行结束===============执行结束==================执行结束]");
							return;
						}
					}
		});
			
		thread.start();
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[创建新的线程]---[创建成功]---[用户::"+nextPlayer.getPassportId()+"]---[线程::::"+thread.getName()+"]");
	}*/
	/**
	 * 结束等待线程
	 * @param roomNumber 
	 */
	/*public  void interrupt(Player player, RoomNumber roomNumber){
		if(thread != null){
			this.stop=true;
			thread.interrupt();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[结束线程]---[用户::"+player.getPassportId()+"]---[线程名称::"+thread.getName()+"]");
		}
	}*/
	
	
	
	

	public int getDiceNumber() {
		return diceNumber;
	}


	public void setDiceNumber(int diceNumber) {
		this.diceNumber = diceNumber;
	}


	public int getDiceValue() {
		return diceValue;
	}


	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
	}

	public boolean isIfGuess() {
		return ifGuess;
	}


	public void setIfGuess(boolean ifGuess) {
		this.ifGuess = ifGuess;
	}


	public boolean isRight() {
		return isRight;
	}


	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isCurUser() {
		return isCurUser;
	}
	public void setCurUser(boolean isCurUser) {
		this.isCurUser = isCurUser;
	}
	/*public Thread getThread() {
		return thread;
	}*/

	
	
}
