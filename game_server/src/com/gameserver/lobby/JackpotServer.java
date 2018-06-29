package com.gameserver.lobby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.lobby.data.JackpotList;
import com.gameserver.lobby.enums.GameType;

/**
 * 彩金排行 目前不入库
 * @author 郭君伟
 *
 */
public class JackpotServer implements InitializeRequired{
	
	private static final int LISTSIZE = 20;

	/** 都在一个线程里面 没有线程安全问题  最近20位获得彩金的玩家 **/
	private List<JackpotList> jackList = new ArrayList<JackpotList>();
	
	
	public void init() {
	}
	
	/**
	 * 这个方法调用比较频繁
	 * @param userId
	 * @param img
	 * @param name
	 * @param jackpot
	 */
	public void add(long userId,String img,String name,long jackpot,int gameType){
		   if(jackpot <= 0){
			   return;
		   }
		   if(jackList.size() >= LISTSIZE){
			   jackList.remove(jackList.size() -1);
		   }
		   jackList.add(getJackpotList(userId,img,name,jackpot,gameType));
	}
	
	
	
	public JackpotList getJackpotList(long userId,String img,String name,long jackpot,int gameType){
		JackpotList jackList = new JackpotList();
		jackList.setUserId(userId);
		jackList.setImg(img);
		jackList.setName(name);
		jackList.setJackpot(jackpot);
		jackList.setGameType(gameType);
		return jackList;
	}

    /**
     * 获取最高彩金
     * @return
     */
	public List<JackpotList> getJackList() {
		return jackList;
	}
	
	/**
	 * 
	 * @param slotType 老虎机类型
	 * @return  老虎机大厅类型
	 */
	public int getSlotType(int slotType){
		if( slotType == 1){
			return GameType.SLOT_CLASSIC.getIndex();
		}else if(slotType == 2){
			return GameType.SLOT_BEAUTY.getIndex();
		}else if(slotType == 3){
			return GameType.SLOT_FRUITS.getIndex();
		}else if(slotType == 4){
			return GameType.SLOT_BEACH.getIndex();
		}else if(slotType == 5){
			return GameType.SLOT_VAMPIRE.getIndex();
		}else if(slotType == 6){
			return GameType.SLOT_CLEOPATRA.getIndex();
		}else if(slotType == 7){
			return GameType.SLOT_MERMAID.getIndex();
		}else if(slotType == 8){
			return GameType.SLOT_MACAU.getIndex();
		}else if(slotType == 9){
			return GameType.SLOT_SNAKE.getIndex();
		}else if(slotType == 10){
			return GameType.SLOT_NETRED.getIndex();
		}
		return 0;
	}
	
	
	
	
	
	public static void main(String[] args) {
		JackpotServer js = new JackpotServer();
		
		/*js.jackList.add(js.getJackpotList(1000l,"","",123));
		js.jackList.add(js.getJackpotList(1002l,"","",456));
		js.jackList.add(js.getJackpotList(1003l,"","",789));*/
		 Collections.sort(js.jackList);
		 
		 Loggers.JACKPOT.info(js.jackList.toString());
	}
	

}
