package com.gameserver.function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.FunctionEntity;
import com.gameserver.activity.msg.GCFunctionLeftTime;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.function.enums.FunctionTypeEnum;
import com.gameserver.human.Human;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.CategoryEnum;
import com.gameserver.recharge.enums.SmallCategoryEnum;
import com.gameserver.recharge.enums.SmallCategoryEnumBackup;
import com.gameserver.recharge.template.RechargeTemplate;

public class FunctionService implements InitializeRequired{
	
	List<Function> functionList = new ArrayList<Function>();
	@Override
	public void init() {
		/**
		 * 查询出所有没有过期的 功能
		 */
		List<FunctionEntity> functionEntityList = Globals.getDaoService().getFunctionDao().getAllFunctionEntity();
		for(FunctionEntity entity:functionEntityList){
			Function function = new Function();
			function.fromEntity(entity);
			functionList.add(function);
		}
		
		
	}
	
	
	
	/**
	 * 买一赠 一 功能
	 * 
	 */
	public void buyOneSendOne(Human human,RechargeTemplate rechargeTemplate,int type,int gold){
		
		for(Function function:functionList){
			//如果是买一赠一的活动
			if(function.getFunctionType() == FunctionTypeEnum.SendOneToOne.getIndex()){
				//首先 需要没有 过期
				long endTime = function.getEndTime().getTime();
				long now = TimeUtils.getYMDTimeFromStr(TimeUtils.formatYMDTime(new Date().getTime())).getTime();
				if(endTime<now){
					return;
				}
				String conditions = function.getConditions();
				
//				String[] conditionArr = conditions.split(";");
//				int category = Integer.valueOf(conditionArr[0]).intValue();
//				int smallCategory = Integer.valueOf(conditionArr[1]).intValue();
				
				int templateCategory = rechargeTemplate.getCategory();
				int templateSmallCategory = rechargeTemplate.getSmallCategory();
				boolean ok = false;
				String[] conArr = conditions.split(",");
				for(int i=0;i<conArr.length;i++){
					String con = conArr[i];
					if(Integer.valueOf(con).intValue() == templateCategory){
						ok=true;
					}

				}
				if(!ok){
					return;
				}
				  //如果这种类型 在后台配置了 
				  String title = String.valueOf(LangConstants.sendOneToOne);
				  String strContent = String.valueOf(LangConstants.sendOneToOneContent);
				  List<Long> listId = new ArrayList<Long>();
				  listId.add(human.getPassportId());
				  //发邮件
				  List<RandRewardData> list = new ArrayList<RandRewardData>();
				  RandRewardData data = new RandRewardData();
				  data.setRewardCount(gold);//买一赠一 所以，买了多少就给多少
				  data.setRewardId(type);
				  list.add(data);
				  MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, list); 
				  
				  //双倍经验卡 的处理
				  if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM2.getIndex() &&
						  rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM12.getIndex()){
					  ItemTemplate itemTemplate = Globals.getItemService().getItemTemplWithId(type);
						int min = itemTemplate.getTime();
					  //设置并且推送消息
					  Globals.getCollectServer().setDoubleExpEndTime(human, min);
				  }
				  /**
				   * 周卡 的初始奖励
				   */
				  if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM2.getIndex() &&
						  rechargeTemplate.getSmallCategory() == SmallCategoryEnum.SMALLCATEGORYENUM2.getIndex() ){
					  
					  List<RandRewardData>  randRewardDataList = human.getHumanWeekCardManager().getReward();
					  CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.WEEK_CARD_INIT,LogReasons.DiamondLogReason.WEEK_CARD_INIT,LogReasons.CharmLogReason.WEEK_CARD_INIT,LogReasons.ItemLogReason.WEEK_CARD_INIT, true);
							
				  }
				  /**
				   *  月卡   的初始奖励
				   */
				  if(rechargeTemplate.getCategory() == CategoryEnum.CATEGORYENUM2.getIndex() &&
						  rechargeTemplate.getSmallCategory() == SmallCategoryEnumBackup.SMALLCATEGORYENUM3.getIndex() ){
					  
					  List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
					  RandRewardData randRewardData = new RandRewardData();
					  randRewardData.setRewardId(Currency.GOLD.getIndex());
					  randRewardData.setRewardCount(Globals.getConfigTempl().getMonthCardNum());
					  randRewardDataList.add(randRewardData);
					  CommonLogic.getInstance().giveRandReward(human, randRewardDataList,LogReasons.GoldLogReason.WEEK_CARD_INIT,LogReasons.DiamondLogReason.WEEK_CARD_INIT,LogReasons.CharmLogReason.WEEK_CARD_INIT,LogReasons.ItemLogReason.WEEK_CARD_INIT, true);
					  
				  }
			}
		}
		
	}



	public void updateFunction(Function function) {
		//更新
		boolean exist = false;
		List<Function> existFunction = new ArrayList<Function>();
		if(function.getId() != null){
			for(Function func:functionList){
				if(func.getId().longValue() == function.getId().longValue()){
					existFunction.add(func);
					exist=true;
					break;
				}
			}
		}
		if(exist){
			//删除
			for(Function ff:existFunction){
				ff.setConditions(function.getConditions());
				ff.setDescrip(function.getDescrip());
				ff.setEndTime(function.getEndTime());
				ff.setStartTime(function.getStartTime());
				ff.setPic(function.getPic());
				ff.setTitle(function.getTitle());
				ff.setFunctionType(function.getFunctionType());
				ff.setModified();
			}
			
		}
		//增加
		if(!exist){
			function.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(), UUIDType.FUNCTIONUUID));
			function.setCreateTime(new Date());
			function.setModified();
			functionList.add(function);
		}
		
	}



	public void deleteActivity(long id) {
		List<Function> existFunc = new ArrayList<Function>();
		for(Function func:functionList){
			if(func.getId() ==id){
				existFunc.add(func);			}
		}
		for(Function Function:existFunc){
			Function.onDelete();
		}
		functionList.removeAll(existFunc);
		
	}



	public void getFunction(Player player) {
		if(functionList.size() == 0){
			GCFunctionLeftTime GCFunctionLeftTime = new GCFunctionLeftTime();
			GCFunctionLeftTime.setFunctionType(new int[]{0});
			GCFunctionLeftTime.setImg("");
			GCFunctionLeftTime.setLeftTime(0);
			player.sendMessage(GCFunctionLeftTime);
			return;
		}
		for(Function func:functionList){
			//如果是 买一送一
			if(func.getFunctionType() == 1){
				
				GCFunctionLeftTime GCFunctionLeftTime = new GCFunctionLeftTime();
				String conditions = func.getConditions();
				if(StringUtils.isNotBlank(conditions)){
					String[] con = conditions.split(",");
					int[] funcTionType = new int[con.length];
					for(int i=0;i<con.length;i++){
						funcTionType[i]=Integer.valueOf(con[i]);
					}
					GCFunctionLeftTime.setFunctionType(funcTionType);
				}
				GCFunctionLeftTime.setImg(func.getPic());
				long now = Globals.getTimeService().now();
				Calendar cal = Calendar.getInstance();
				cal.setTime(func.getEndTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
				long leftTime = cal.getTime().getTime()-now;
				if(leftTime >0){
					GCFunctionLeftTime.setLeftTime(leftTime);
					player.sendMessage(GCFunctionLeftTime);
				}
				
			}
		}
		
	}

}
