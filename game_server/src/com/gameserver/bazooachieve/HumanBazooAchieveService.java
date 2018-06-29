package com.gameserver.bazooachieve;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.template.LiarsDiceRoomAchieveTemplate;
import com.gameserver.bazooachieve.data.BazooAchieveData;
import com.gameserver.bazooachieve.data.BazooAchieveInfo;
import com.gameserver.common.Globals;
import com.gameserver.human.manager.HumanBazooAchieveManager;
import com.gameserver.player.Player;
/**
 * 成就不用了（全部放在了task里）
 * @author JavaServer
 *
 */
public class HumanBazooAchieveService  implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;
	
	//所有的任务
	private List<LiarsDiceRoomAchieveTemplate> achieveList = new ArrayList<LiarsDiceRoomAchieveTemplate>();
	@Override
	public void init() {
		/*List<LiarsDiceRoomAchieveTemplate> templateList = Globals.getBazooPubService().getDiceAchieveList();
		achieveList.addAll(templateList);*/
	}

	
	
	/**
	 * 用户会完成那些任务
	 * @param player
	 */
	public BazooAchieveInfo[] getAllTasks(Player player){
		HumanBazooAchieve HumanBazooAchieve = player.getHuman().getHumanBazooAchieveManager().getHumanBazooAchieve();
		BazooAchieveInfo[] BazooAchieveInfoArr = new BazooAchieveInfo[achieveList.size()];
		for(int i=0;i<achieveList.size();i++){
			BazooAchieveInfo bazooAchieveInfo = new BazooAchieveInfo();
			LiarsDiceRoomAchieveTemplate template = achieveList.get(i);
			
			bazooAchieveInfo.setBigtype(template.getBigType());
			bazooAchieveInfo.setModeType(template.getModeType());
			bazooAchieveInfo.setWayOfPlay(template.getWayOfPlay());
			bazooAchieveInfo.setCondition(template.getCondition());
			bazooAchieveInfo.setRewardNum(template.getRewardNum());
			bazooAchieveInfo.setIcon(template.getIcons());
			bazooAchieveInfo.setNameId(template.getNameId());
			bazooAchieveInfo.setDescrip(template.getDescrip());
			for(BazooAchieveData BazooAchieveData:HumanBazooAchieve.getAchieves()){
				if(BazooAchieveData.getAchieveId() == template.getId()){
					bazooAchieveInfo.setFinishVlaues(BazooAchieveData.getFinishValues());
					break;
				}
			}
			BazooAchieveInfoArr[i]=bazooAchieveInfo;
		}
		
		return BazooAchieveInfoArr;
	}
	/**
	 * 用户会完成那些任务
	 * @param player
	 */
	public void finishTask(Player player){
		HumanBazooAchieveManager manager = player.getHuman().getHumanBazooAchieveManager();
		RoomEveryUserInfo RoomEveryUserInfo = player.getHuman().getBazooRoomEveryUserInfo();
		HumanBazooAchieve humanBazooAchieve= manager.getHumanBazooAchieve();
		
		int modeType = RoomNumber.toRoomNumber(player.getHuman().getBazooRoom()).getModeType();
		for(int i=0;i<achieveList.size();i++){
			LiarsDiceRoomAchieveTemplate template = achieveList.get(i);
				if(template.getModeType() !=0 && template.getModeType()  != modeType){
					continue;
				}
				//所有模式  局数 或者 是钱数
				if(template.getWayOfPlay() == 3 || template.getWayOfPlay() ==4){
					humanBazooAchieve.checkAndSetValue(template);
					continue;
				}
				
				if(template.getModeType() == RoomNumber.MODE_TYPE_CLASSICAL){
						//玩的方式：胜场数
						if(template.getWayOfPlay() == 1){
							//如果赢了 才算
							boolean isRight = player.getHuman().getBazooRoomEveryUserInfo().getClassicalUserInfo().isRight();
							if(isRight){
								humanBazooAchieve.checkAndSetValue(template);
							}
						}else if(template.getWayOfPlay() == 2){//必须连胜 才可以
							if(RoomEveryUserInfo.getWinTimes() > 0){
								humanBazooAchieve.checkAndSetValue(template);
							}
						}
				}else if(template.getModeType() == RoomNumber.MODE_TYPE_COW){
					//玩的方式：胜场数
					if(template.getWayOfPlay() == 1){
						//如果赢了 才算
						boolean isRight = player.getHuman().getBazooRoomEveryUserInfo().getCowUserInfo().isRight();
						if(isRight){
							humanBazooAchieve.checkAndSetValue(template);
						}
					}else if(template.getWayOfPlay() == 2){//必须连胜 才可以
						if(RoomEveryUserInfo.getWinTimes() > 0){
							humanBazooAchieve.checkAndSetValue(template);
						}
					}
					 
				}else if(template.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
					
					//玩的方式：胜场数
					if(template.getWayOfPlay() == 1){
						//如果赢了 才算
						long money = player.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getMoney();
						if(money>0){
							humanBazooAchieve.checkAndSetValue(template);
						}
					}else if(template.getWayOfPlay() == 2){//必须连胜 才可以
						if(RoomEveryUserInfo.getWinTimes() > 0){
							humanBazooAchieve.checkAndSetValue(template);
						}
					}
					
				}
			
				
		}
	}



	public List<LiarsDiceRoomAchieveTemplate> getAchieveList() {
		return achieveList;
	}

	public void setAchieveList(List<LiarsDiceRoomAchieveTemplate> achieveList) {
		this.achieveList = achieveList;
	}
	
	
	
}
