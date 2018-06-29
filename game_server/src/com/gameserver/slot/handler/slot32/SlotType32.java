package com.gameserver.slot.handler.slot32;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.core.util.ArrayUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType32Bonus;
import com.gameserver.slot.msg.GCSlotType32BulletOut;
import com.gameserver.slot.msg.GCSlotType32LeftBulletNum;
import com.gameserver.slot.msg.GCSlotType32SocialContact;
import com.gameserver.slot.msg.GCSlotType32SpecialList;
import com.gameserver.slot.msg.GCSlotType32WildInfo;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.BonusSpartaRewardTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 斯巴达 老虎机
 * @author JavaServer
 *
 */
public class SlotType32 extends SlotBase{


	
	private List<Integer> tempSlotElementsCope;
	
	private List<List<Integer>> list2 = new ArrayList<List<Integer>>();
	
	//大战士的位置   就是中间一列
	private List<Integer> bigSoldiers = new ArrayList<Integer>();

	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
		
		    human = player.getHuman();
		    
	        //5个卷轴每个卷轴随机步数
			List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
			
			//移动步数
			humanSlotManager.slot(randomIntList);
			
			//当前位置信息
			List<Integer> tempSlotElements = getSlotElementsBySlotPos(human);
			/**
			 * 1、免费转动会 把中间的一列替换为 大个的士兵(三个元素)
			 * 2、还有 在免费转动期间：如果出现 小战士元素 就把他固定住
			 */
			if(free==1){
				//检查替换后的元素列表
				tempSlotElementsCope = getReplaceSlotElements(human,tempSlotElements);
				
				//小于等于配置
				tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
				
				
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElementsCope);
			//付费转动就是普通转动 (没有替换元素)
			}else if(free==0){
				/**
				 * 每次付费转动 都把 free==2 特殊转动中的 固定的元素清空
				 */
				HumanTemporaryData humanTemporaryData  = human.getHumanSlotManager().getHumanTemporaryData();
				List<Integer> shootWildList = humanTemporaryData.getShootedWildElement();
				if(shootWildList  != null && shootWildList.size()>0){
					shootWildList.clear();
				}
				/**
				 * 清空战士
				 */
				List<Integer> soldierElements = humanTemporaryData.getSoldierElement();
				if(soldierElements != null && soldierElements.size()>0){
					soldierElements.clear();
				}
				//纯粹的 把tempSlotElements 转换成 list2 （List<List<Integer>> ）这种格式,用于 后边流程中的元素对比
				tempSlotElementsCope = changeSlotElements(tempSlotElements);
				//小于等于配置
				tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
				
				
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElementsCope);
			//这个转动属于 攻城后的转动
			}else if(free == 2){
				//当前位置信息
				tempSlotElements = getSlotElementsBySlotPos2(human);
				
				//检查检查那个WILD是固定的，还有 就是转换下格式
				tempSlotElementsCope = getBulletSlotElements(human,tempSlotElements);
				
				//小于等于配置
				tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
				
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElementsCope);
			}
			
			//连线奖励
			SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
			
			SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
			/**
			 * 自由转动要返回替换后的元素列表
			 */
			if(free==1 || free == 2){
				sendMessageSlot(tempSlotElementsCope,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			}else{
				sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			}
			
			//Loggers.slotLogger.info(JSON.toJSONString(tempSpecialConnectInfoData));
	}
	private List<Integer> getBulletSlotElements(Human human,
			List<Integer> tempSlotElements) {

		
		/**
		 * 查询出固定元素 wild 的index
		 */
		//需要固定的WILD 的 index 而不是元素
		List<Integer> tempList = new ArrayList<Integer>();
		//所有index的元素
		List<Integer> indexList = new ArrayList<Integer>();
		//把index 放进去
		for(int i=0;i<tempSlotElements.size();i++){
			indexList.add(i);
		}
		int randomNum = Globals.getSpartaService().getRandomNum(tempSlotsListTemplate.getType());
		for(int i=0;i<randomNum;i++){
			int index = RandomUtil.nextInt(0, indexList.size());
			int elementType = slotService.getTurnType(tempSlotsListTemplate.getType(), tempSlotElements.get(indexList.get(index)));
			if(elementType == SlotElementType.SLOT32WILD33.getIndex()){
				tempList.add(indexList.get(index));
			}
			indexList.remove(index);
		}
		
		HumanTemporaryData humanTemporaryData  = human.getHumanSlotManager().getHumanTemporaryData();
		List<Integer> shootedWildElements = humanTemporaryData.getShootedWildElement();
		Set<Integer> lastShootedWildElements = new HashSet<Integer>();
		if(shootedWildElements != null && shootedWildElements.size() >0){
			for(int i=0;i<shootedWildElements.size();i++){
				lastShootedWildElements.add(shootedWildElements.get(i));
			}
		}
		shootedWildElements.addAll(lastShootedWildElements);
		
		for(Integer temp:tempList){
			if(!shootedWildElements.contains(temp)){
				shootedWildElements.add(temp);
			}
		}
		/**
		 * 转换格式
		 */
		int columns = tempSlotsListTemplate.getColumns();
		int rows = tempSlotsListTemplate.getRows();
		
		for(int i=0;i<columns;i++){
			List<Integer> columnList = new ArrayList<Integer>();
			for(int j=0;j<rows;j++){
				int temp = tempSlotElements.get(i*rows+j);
				if(shootedWildElements.contains(i*rows+j)){
					columnList.add(slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32WILD33.getIndex()));	
				}else{
					columnList.add(temp);	
				}
			}
			list2.add(columnList);
		}
		List<Integer> list1 = new ArrayList<Integer>();
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
		}
		
		return list1;
	}
	/**
	 * 获得出现元素
	 */
	public List<Integer> getSlotElementsBySlotPos(Human human){
		
		List<Integer> results = new ArrayList<Integer>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
	
		Slot slot =  slotService.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		//卷轴
		List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		int bullotNum = 0;
		for(int i=0;i<humanSlotManager.getCurrentSlotPosList().size();i++){
			
			//ith reel
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			
			int tempTurnBegin = humanSlotManager.getCurrentSlotPosList().get(i);
			//ith reel from j to j+row
			for(int j=0;j<tempSlotsListTemplate.getRows();j++){
				for(int x=0;x<30;x++){
					int tempTurn = tempTurnBegin+j+x;
					//翻页了
					tempTurn = tempTurn%tempScrollList.size();  
					if(tempScrollList.get(tempTurn).getTurn() != slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32CAR34.getIndex())){
						results.add(tempScrollList.get(tempTurn).getTurn());
						break;
					}else{
						if(bullotNum == 0){
							results.add(tempScrollList.get(tempTurn).getTurn());
							bullotNum++;
							break;
						}else{
							continue;
						}
					}
				}
				
				
			}
		}
		
		return results;
	}
	/**
	 * 获得出现元素 所有的攻城车 元素都替换掉
	 */
	public List<Integer> getSlotElementsBySlotPos2(Human human){
		
		List<Integer> results = new ArrayList<Integer>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  slotService.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		//卷轴
		List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		for(int i=0;i<humanSlotManager.getCurrentSlotPosList().size();i++){
			
			//ith reel
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			
			int tempTurnBegin = humanSlotManager.getCurrentSlotPosList().get(i);
			//ith reel from j to j+row
			for(int j=0;j<tempSlotsListTemplate.getRows();j++){
				for(int x=0;x<100;x++){
					int tempTurn = tempTurnBegin+j+x;
					//翻页了
					tempTurn = tempTurn%tempScrollList.size();  
					if(tempScrollList.get(tempTurn).getTurn() != slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32CAR34.getIndex())
						&& 	tempScrollList.get(tempTurn).getTurn() != slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32BONUS36.getIndex())
						&& 	tempScrollList.get(tempTurn).getTurn() != slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SCATTER.getIndex())
							){
						results.add(tempScrollList.get(tempTurn).getTurn());
						break;
					}else{
						continue;
					}
				}
			}
		}
		
		return results;
	}
	//付费转动不替换任何元素，纯粹把 List<Integer> 格式转换成 List<List<Integer>>
	private List<Integer> changeSlotElements(List<Integer> tempSlotElements) {
		int columns = tempSlotsListTemplate.getColumns();
		int rows = tempSlotsListTemplate.getRows();
		
		for(int i=0;i<columns;i++){
			List<Integer> columnList = new ArrayList<Integer>();
			for(int j=0;j<rows;j++){
				int temp = tempSlotElements.get(i*rows+j);
				columnList.add(temp);	
			}
			list2.add(columnList);
		}
		HumanTemporaryData humanTemporaryData  = human.getHumanSlotManager().getHumanTemporaryData();
		List<Integer> list1 = new ArrayList<Integer>();
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
		}
		addBullet(list1,humanTemporaryData,human);
		return list1;
	}
	/**
	 * 1、把第三列 替换为 大个的士兵 （占三个元素）
	 * 2、还有 在免费转动期间：如果出现 小战士元素 就把他固定住
	 * @param tempSlotElements
	 * @return
	 */
	public List<Integer> getReplaceSlotElements(Human human,List<Integer> tempSlotElements){
		
		HumanTemporaryData humanTemporaryData  = human.getHumanSlotManager().getHumanTemporaryData();
		List<Integer> soldierElements = humanTemporaryData.getSoldierElement();
		List<Integer> list1 = new ArrayList<Integer>();
		int columns = tempSlotsListTemplate.getColumns();
		int rows = tempSlotsListTemplate.getRows();
		for(int i=0;i<columns;i++){
			List<Integer> columnsList = new ArrayList<Integer>();
			//第三列 只替换第三列
			if(i==2){
				bigSoldiers.clear();
				for(int j=0;j<rows;j++){
					columnsList.add(slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32WILD35.getIndex()));
					bigSoldiers.add(i*rows+j);
				}
			}else{
				for(int j=0;j<rows;j++){
					int index = i*rows+j;
					int element = tempSlotElements.get(index);
					if(soldierElements.contains(index)){
						columnsList.add(slotService.getTurnBySlotType(tempSlotsListTemplate.getType(), SlotElementType.SLOT32SOLDIER35.getIndex()));
					}else{
						if(slotService.getTurnType(tempSlotsListTemplate.getType(), element) == SlotElementType.SLOT32SOLDIER35.getIndex()){
							soldierElements.add(index);
						}
						columnsList.add(element);
					}
				}
			}
			list2.add(columnsList);
		}
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
		}
		addBullet(list1,humanTemporaryData,human);
		return list1;
	}
	//在转动过程中 添加子弹 一个房间里的人都往里添加
	private void addBullet(List<Integer> list1,HumanTemporaryData humanTemporaryData,Human human){
		/**
		 * 计算战车出现的次数
		 * 这个次数是一个房间 所有的人公用的 参数
		 */
		//是否出现 攻城车 默认为没有
		boolean bulletOut = false;
		for(int i=0;i<list1.size();i++){
			int element = list1.get(i);
			if(slotService.getTurnType(tempSlotsListTemplate.getType(), element) == SlotElementType.SLOT32CAR34.getIndex()){
				Globals.getSlotRoomService().setRoomSlot32Bullet(tempSlotsListTemplate.getType(),human.getSlotRoomId(), 1);
				humanTemporaryData.setSelfAttackNum(humanTemporaryData.getSelfAttackNum()+1);
				bulletOut=true;

				//总的攻城次数
				int bulletNum = Globals.getSlotRoomService().getRoomBullet(tempSlotsListTemplate.getType(),human.getSlotRoomId());
				int wallNum = Globals.getSpartaService().getWallNum(tempSlotsListTemplate.getType());
				int leftbulletNum = wallNum-bulletNum;
				if(leftbulletNum >= 0){
					String[] theRoomMemIds = Globals.getSlotRoomService().getRoomMemeber(player);
					if(theRoomMemIds != null && theRoomMemIds.length >0){
						GCSlotType32LeftBulletNum gCSlotType32LeftBulletNum = new GCSlotType32LeftBulletNum();
						gCSlotType32LeftBulletNum.setBulletLeftNum(leftbulletNum);
						gCSlotType32LeftBulletNum.setUserId(Long.valueOf(human.getPassportId()).intValue());
						//先给自己发消息 再给别人发消息
						human.sendMessage(gCSlotType32LeftBulletNum);
						//一个房间的人  每个人 中了 攻城车 ，都给 所有的人发消息（此处给别人发消息）
						for(int j=0;j<theRoomMemIds.length;j++){
							long userId = Long.valueOf(theRoomMemIds[j]).longValue();
							Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(userId);
							if(player != null){
								Human theSameRoomHuman = player.getHuman();
								theSameRoomHuman.sendMessage(gCSlotType32LeftBulletNum);
							}
						}
					}
				}
			}
		}
		//如果出现攻城车 调用发送此接口
		if(bulletOut){
			GCSlotType32BulletOut gCSlotType32BulletOut = new GCSlotType32BulletOut();
			human.getPlayer().sendMessage(gCSlotType32BulletOut);
		}
	}
	/**
	 * 获得列表
	 */
	public List<SlotConnectInfo> getSlotConnectInfoListByHuman(int currBet){
		
		List<SlotConnectInfo> tempSlotConnectInfoDataList= new ArrayList<SlotConnectInfo>();
	
		//获取连线类型
		int lineType = tempSlotsListTemplate.getLineType();
		
		List<PaylinesTemplate> listLineType = slotService.getPaylinesTemplate(lineType);
			
		//获得连线的种类 20条线
		for(int i=0;i<tempSlotsListTemplate.getPayLinesNum();i++){
			
			//连线表顺序取
			PaylinesTemplate tempPaylinesTemplate = listLineType.get(i);
			
			//获得连线的元素列表（摇取得5个元素）
			List<Integer> tempCombinationList = getCombinationListByHumanAndPayline(human,tempPaylinesTemplate);
			
			//获得连线的奖励信息
			SlotConnectInfo tempSlotConnectInfo = getSlotConnectInfo(tempCombinationList,currBet,human);
			
			if(tempSlotConnectInfo!=null){
				
				tempSlotConnectInfo.setPaylinesTemplate(tempPaylinesTemplate);
				
				tempSlotConnectInfoDataList.add(tempSlotConnectInfo);
			}
		}
		return tempSlotConnectInfoDataList;
	}
	
	/**
	 * 获得5个位置摇取元素 卷轴停的位置
	 * @param human
	 * @param paylinesTemplate 连线
	 * @return
	 */
	public List<Integer> getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate){
		List<Integer> tempCombinationList = new ArrayList<Integer>();
		
		for(int i=0;i<paylinesTemplate.getPositionList().size();i++){//5个位置
			int tempIthPayPos = paylinesTemplate.getPositionList().get(i)-1;
			List<Integer> post = list2.get(i);
			//tempCombinationList.add(tempSlotElementsCope.get(tempIthPayPos));
			tempCombinationList.add(post.get(tempIthPayPos));
		}
		return tempCombinationList;
	}

	/**
	 * 获取赔率
	 * @param slot
	 * @param tempCombinationList 得到的5个元素
	 * @param currBet 当前单线押注
	 * @param human
	 * @return
	 */
	public SlotConnectInfo getSlotConnectInfo(List<Integer> tempCombinationList ,int currBet,Human human){
		
		//获取当前老虎机赔率数据
		List<PayTemplate> tempPayTemplateList = slotService.getPayTemplateListBySlotType(slot.getType());
		
		SlotConnectInfo tempSlotConnectInfo = new SlotConnectInfo();
		
		for(PayTemplate tempPayTemplate : tempPayTemplateList){//循环逐个比较
			boolean match = true;
			tempSlotConnectInfo.getPosList().clear();
			
			//int jackport = 0;
			//5个位置逐个比较
			for(int i=0;i<tempPayTemplate.getCombinationList().size();i++){
				//图标ID
				int tempSlotTurn = tempPayTemplate.getCombinationList().get(i);
				if(tempSlotTurn ==0){
					continue;
				}
				//图标
				int turn  = tempCombinationList.get(i);
				
				//判断是不是wild
				SlotElementType elementType =  SlotElementType.indexOf(slotService.getTurnType(tempSlotsListTemplate.getType(),turn));
				if(
						elementType != SlotElementType.WILD1 &&
						elementType != SlotElementType.WILD2 &&
						elementType != SlotElementType.WILD3 &&
						elementType != SlotElementType.WILD4 &&
						elementType != SlotElementType.bigWILD &&
						elementType != SlotElementType.SLOTTYPE15 &&
						elementType != SlotElementType.SLOTTYPE20WILD &&
						elementType != SlotElementType.SLOTWILD26 &&
						elementType != SlotElementType.WILD27 &&
						elementType != SlotElementType.SLOTWILD31 &&
						elementType != SlotElementType.SLOT32WILD33 &&
						elementType != SlotElementType.SLOT32WILD35 &&
						elementType != SlotElementType.WILD && turn != tempSlotTurn){
					match=false;//这条Pay线不匹配
					break;
				}
				
				tempSlotConnectInfo.getPosList().add(i);//位置
			}
			if(match){//这个图标List 完全匹配
				
				tempSlotConnectInfo.setPay(tempPayTemplate.getPay());
				tempSlotConnectInfo.setPayId(tempPayTemplate.getId());
				return tempSlotConnectInfo;
			}
		}
		
		return null;
	}

	/**
	 * 出现自由转动的时候发送 固定元素的位置
	 */
	private void sendFreeElement(HumanTemporaryData humanTemporaryData){
		List<Integer> soldierElements = humanTemporaryData.getSoldierElement();
		GCSlotType32SpecialList gCSlotType32SpecialList = new GCSlotType32SpecialList();
		int[] smallArr = new int[soldierElements.size()];
		for(int i=0;i<soldierElements.size();i++){
			smallArr[i]=soldierElements.get(i);
		}
		int[] bigArr = new int[bigSoldiers.size()];
		for(int i=0;i<bigSoldiers.size();i++){
			bigArr[i]=bigSoldiers.get(i);
		}
		gCSlotType32SpecialList.setSmallSoldier(smallArr);
		gCSlotType32SpecialList.setBigSoldier(bigArr);
		player.sendMessage(gCSlotType32SpecialList);
	}
	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		HumanTemporaryData humanTemporaryData  = human.getHumanSlotManager().getHumanTemporaryData();
		if(free == 2 && humanTemporaryData.getSocialityFreeNum() >= 0){
			humanTemporaryData.setSocialityFreeNum(humanTemporaryData.getSocialityFreeNum()-1);
			
			//社交转动结束后  算账
			if(humanTemporaryData.getSocialityFreeNum() == 0){
				List<Integer> shootedWildElements = humanTemporaryData.getShootedWildElement();
				//斯巴达自由转动 开始
				humanTemporaryData.setSpartaFreeTurn(false);
				int selfAttackNum = humanTemporaryData.getSelfAttackNum();
				int percent =   Globals.getSpartaService().getSocialityNum(tempSlotsListTemplate.getType(),selfAttackNum);
				int shootedWildElement = shootedWildElements.size();
				int reward =   Globals.getSpartaService().getSocialityWildReward(tempSlotsListTemplate.getType(),shootedWildElement);
				slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(bet*reward*percent/10000),humanSlotManager.getCurrentBet());
			}
			GCSlotType32SocialContact gCSlotType32SocialContact = new GCSlotType32SocialContact();
			gCSlotType32SocialContact.setFreeNum(humanTemporaryData.getSocialityFreeNum());
			int selfAttackNum = humanTemporaryData.getSelfAttackNum();
			int percent =   Globals.getSpartaService().getSocialityNum(tempSlotsListTemplate.getType(),selfAttackNum);
			List<Integer> list = Globals.getSpartaService().getRewardList(tempSlotsListTemplate.getType());
			List<Long> rewardList = new ArrayList<Long>();
			for(Integer ll:list){
				rewardList.add(Long.valueOf(ll*percent*bet/10000));
			}
			long[] rewardArr = new long[rewardList.size()];
			for(int i=0;i<rewardArr.length;i++){
				rewardArr[i]=rewardList.get(i);
			}
			gCSlotType32SocialContact.setRewardList(rewardArr);
			player.sendMessage(gCSlotType32SocialContact);
			
			List<Integer> shootedWildElements = humanTemporaryData.getShootedWildElement();
			GCSlotType32WildInfo gCSlotType32WildInfo = new GCSlotType32WildInfo();
			gCSlotType32WildInfo.setPosList(ArrayUtils.intList2Array(shootedWildElements));
			player.sendMessage(gCSlotType32WildInfo);
			return tempSpecialConnectInfoData;
		}
		/**
		 * 每次免费转动 都把固定的战士 发送给前端
		 */
		if(free == 1){
			sendFreeElement(humanTemporaryData);
		}
		ScatterInfoType32 tempScatterInfo = getScatterInfo();
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
			 if(free == 0 && tempScatterInfo.getScatterTemplate().getFreeSpinNum()>0){
				 sendFreeElement(humanTemporaryData);
			 }
			 
		}
		
		//如果bullet大于指定个数 就触发社交游戏
		//总的攻城次数
		int bulletNum = Globals.getSlotRoomService().getRoomBullet(tempSlotsListTemplate.getType(),human.getSlotRoomId());
		int totalWarNum = Globals.getSpartaService().getWallNum(tempSlotsListTemplate.getType());
		if(bulletNum >=  totalWarNum){
			/**
			 * 每次清空战车的个数
			 */
			Globals.getSlotRoomService().setRoomSlot32BulletZero(tempSlotsListTemplate.getType(),human.getSlotRoomId(), 0);
			GCSlotType32SocialContact gCSlotType32SocialContact = new GCSlotType32SocialContact();
			String[] theRoomMemIds = Globals.getSlotRoomService().getRoomMemeber(player);
			//一个房间的人  每个人 中了 攻城车 ，都给 所有的人发消息（此处给别人发消息）
			for(int j=0;j<theRoomMemIds.length;j++){
				long userId = Long.valueOf(theRoomMemIds[j]).longValue();
				Player roomPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(userId);
				if(roomPlayer != null){
					//每个人的缓存都要改
					HumanTemporaryData perHumanTemporaryData  = roomPlayer.getHuman().getHumanSlotManager().getHumanTemporaryData();
					
					int selfAttackNum = perHumanTemporaryData.getSelfAttackNum();
					int percent =   Globals.getSpartaService().getSocialityNum(tempSlotsListTemplate.getType(),selfAttackNum);
					List<Integer> list = Globals.getSpartaService().getRewardList(tempSlotsListTemplate.getType());
					logger.info("--------------list1::"+list);
					List<Long> rewardList = new ArrayList<Long>();
					if(list != null && list.size() >0){
						logger.info("--------------list2::"+list);
						for(Integer ll:list){
							logger.info("--------------list3::"+ll);
							rewardList.add(Long.valueOf(ll*percent*bet/10000));
						}
					}
					long[] rewardArr = new long[rewardList.size()];
					for(int i=0;i<rewardArr.length;i++){
						rewardArr[i]=rewardList.get(i);
					}
					gCSlotType32SocialContact.setRewardList(rewardArr);
					int initSocialNum = Globals.getSpartaService().getSocialNum(tempSlotsListTemplate.getType());
					perHumanTemporaryData.setSocialityFreeNum(initSocialNum);
					gCSlotType32SocialContact.setFreeNum(initSocialNum);
					//斯巴达自由转动 开始
					perHumanTemporaryData.setSpartaFreeTurn(true);
					roomPlayer.sendMessage(gCSlotType32SocialContact);
				}
			}
		}
		
		//bonus 是否大于 配置中的（3）个
		int bonusNum = Globals.getSpartaService().getBounsNum(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getBnus()>=bonusNum){
			
			List<Integer> levelList = new ArrayList<Integer>();
			List<Integer> isSuccessList = new ArrayList<Integer>();
			List<Long> rewardNumList = new ArrayList<Long>();
			List<BonusSpartaRewardTemplate> bonusSpartaRewardTemplateList = Globals.getSpartaService().getBonusSpartaRewardMap().get(tempSlotsListTemplate.getType());
			//总共有多少关
			int level = 0;
			for(int i=0;i<bonusSpartaRewardTemplateList.size();i++){
				BonusSpartaRewardTemplate bonusSpartaRewardTemplate = bonusSpartaRewardTemplateList.get(i);
				int type = bonusSpartaRewardTemplate.getType();
				if(level != type){
					level++;
				}
			}
			long totalGolds = 0l;
			//我方的学就是 3滴 三关 总共3滴血
			int ourSide = 3;
			//每一关进行处理
			for(int i=0;i<level;i++){
				//第 (i+1) 关
				List<BonusSpartaRewardTemplate> bonusSpartaRewardTemplateList1 = Globals.getSpartaService().getBonusSpartaRewardTemplate(tempSlotsListTemplate.getType(),i+1);
				int totalBlood = bonusSpartaRewardTemplateList1.size();
				//敌我 两方 先装满血
				int enemySide = totalBlood;
				//赢得的次数
				int winTime = 1;
				//对攻 的次数
				int  attackOneAnother = 0;
				while(true){
					attackOneAnother++;
					winTime=winTime%totalBlood;
					if(winTime == 0){
						winTime=totalBlood;
					}
					int random = RandomUtil.nextInt(0, 100);
					BonusSpartaRewardTemplate bonusSpartaRewardTemplate = bonusSpartaRewardTemplateList1.get(winTime-1);
					int weight = bonusSpartaRewardTemplate.getValue();
					//如果没有击中 我方掉血
					if(random > weight){
						isSuccessList.add(0);
						rewardNumList.add(0l);
						--ourSide;
						if(ourSide<=0){
							break;
						}
					//如果击中 敌方掉血
					}else{
						totalGolds+=bonusSpartaRewardTemplate.getRewardNum();
						isSuccessList.add(1);
						rewardNumList.add(Long.valueOf(bonusSpartaRewardTemplate.getRewardNum()).longValue());
						--enemySide;
						winTime++;
						if(enemySide<=0){
							break;
						}
					}
				}
				//每一关攻击了几次
				levelList.add(attackOneAnother);
				//如果我方的 血没了，我就输掉了 这一关的比赛，无缘下一关
				if(ourSide <= 0 && enemySide>0){
					break;
				}
			}
			slotLog(human,slot.getTempleId(),false,true,false,totalGolds/100,humanSlotManager.getCurrentBet());
			int[] levelArr = new int[levelList.size()];
			for(int i=0;i<levelList.size();i++){
				levelArr[i]=levelList.get(i);
			}
			int[] isSuccessArr = new int[isSuccessList.size()];
			for(int i=0;i<isSuccessList.size();i++){
				isSuccessArr[i]=isSuccessList.get(i);
			}
			long[] rewardNumArr = new long[rewardNumList.size()];
			for(int i=0;i<rewardNumList.size();i++){
				rewardNumArr[i]=rewardNumList.get(i);
			}
			GCSlotType32Bonus bounsInfo = new GCSlotType32Bonus();
			bounsInfo.setRewardNumList(rewardNumArr);
			bounsInfo.setLevelList(levelArr);
			bounsInfo.setIsSuccessList(isSuccessArr);
			player.sendMessage(bounsInfo);
		}
		return tempSpecialConnectInfoData;
	}
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType32 getScatterInfo(){
		
		ScatterInfoType32 tempScatterInfo =new ScatterInfoType32();
		//bouns 个数
		int  bounsNum = 0;
		//WILD 个数
		int  wildNum = 0;
		int scatters = 0;
        for(int i = 0;i <  tempSlotElementsCope.size();i++){
             int turn = tempSlotElementsCope.get(i);
             int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
        	if(turnType == SlotElementType.SLOT32BONUS36.getIndex()){
        		bounsNum++;
        		tempScatterInfo.getBonusList().add(i);
        	}else if(turnType == SlotElementType.SCATTER.getIndex()){
        		scatters++;
        		tempScatterInfo.getPosList().add(i);
        	}else if(turnType == SlotElementType.SLOT32WILD33.getIndex()){
        		tempScatterInfo.getWildList().add(i);//wild 的位置
        		++wildNum;
			}
        }
        if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bounsNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
        tempScatterInfo.setBnus(bounsNum);
		tempScatterInfo.setWild(wildNum);
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
			if(scatters>=scatterMax){
				tempScatterInfo.setScatterTemplate(maxScatterTemplate);
				return tempScatterInfo;
			}
			for(ScatterTemplate tempScatterTemplate :list){
				int scatterNum = tempScatterTemplate.getScatterNum();
				if(scatters==scatterNum){
					tempScatterInfo.setScatterTemplate(tempScatterTemplate);
					return tempScatterInfo;
				}
			}
		}
		
		return tempScatterInfo;
	}

}
