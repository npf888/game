package com.gameserver.bazoo.service.showHand;

import org.slf4j.Logger;

import com.common.constants.Loggers;

/**
 * 梭哈的个人 信息
 * @author JavaServer
 *
 */
public class ShowHandUserInfo {
	
	private Logger logger = Loggers.BAZOO;
	
	
	//每一次 的 输赢
	private long money;
	//每一次  输赢多少倍
	private int multiple;

	//用户当前 摇的是 什么牌     没摇一次都会变话
	private Card card;
	
	//当前正在倒计时的 线程
//	private Thread thread;
	//是否停止线程 默认不停止
//	private boolean stop;
	
	public ShowHandUserInfo(){
		card=new Card();
	}
	
	/**
	 * 时间 每个用户每一次叫号的 时间，到了就自动下一个
	 * @return
	 */
	/*public  void  setTime(int nextTime,Player lastPlayer,RoomNumber roomNumber){
		stop=false;
		logger.info("[无双吹牛"+roomNumber.toString()+"]---[创建新的线程]---[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
			this.thread=new Thread(new Runnable() {
				@Override
				public void run() {
				
					if(!stop){
							try{
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程-睡前]---[线程名称::"+Thread.currentThread().getName()+"--状态stop::"+stop+"]---[等待用户::"+lastPlayer.getPassportId()+"-"+lastPlayer.getHuman().getName()+"]");
								Thread.sleep((nextTime+1)*1000);
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程-睡后]---[线程名称::"+Thread.currentThread().getName()+"--状态stop::"+stop+"]---[等待用户::"+lastPlayer.getPassportId()+"-"+lastPlayer.getHuman().getName()+"]");
							}catch(Exception e){
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[被唤醒]---[状态stop::"+stop+"]---[等待用户::"+lastPlayer.getPassportId()+"---[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
							}
								
							if(stop){
								logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[被停止]---[状态stop::"+stop+"]---[等待用户::"+lastPlayer.getPassportId()+"[倒计时线程111-111::"+Thread.currentThread().getName()+"]");
								return;
							}
						
							logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---[由服务器替用户叫号]---[--------------------------------------------------------------------]");
							Globals.getBazooShowHandService().swingSingle(lastPlayer,new ArrayList<Integer>(),false);
							logger.info("[无双吹牛"+roomNumber.toString()+"]---[等待线程]---["+Thread.currentThread().getName()+"===============当前线程结束==================]");
							return;
						}
					}
		});
			
		thread.start();
		try{
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[创建新的线程]---[创建成功]---[用户::"+lastPlayer.getPassportId()+"]---[线程::::"+thread.getName()+"]");
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	/**
	 * 结束等待线程
	 * @param roomNumber 
	 */
/*	public  void interrupt(Player player, RoomNumber roomNumber){
		if(thread != null){
			this.stop=true;
			thread.interrupt();
			logger.info("[无双吹牛"+roomNumber.toString()+"]---[结束线程]---[用户::"+player.getPassportId()+"]---[线程名称::"+thread.getName()+"]");
		}
	}*/
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
/*	public Thread getThread() {
		return thread;
	}*/
	
	
	
	

	
}
