package com.robot.slot.slotname;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.orm.DBService;
import com.db.model.SlotListEntity;

/**
 * 所有老虎机名称管理
 * @author JavaServer
 *
 */
public class SlotNameManager {
	

	List<SlotName> slots = new ArrayList<SlotName>();
	
	public void initAddSlots(){
		
		SlotName slotName1 = new SlotName();
		slotName1.setSlotName("classic");
		slotName1.setSlotId(1);
		slotName1.setSlotType(1);
		slotName1.setBet(100);
		slots.add(slotName1);
		
		SlotName slotName2 = new SlotName();
		slotName2.setSlotName("四大美人");
		slotName2.setSlotId(5);
		slotName2.setSlotType(2);
		slotName2.setBet(100);
		slots.add(slotName2);
		
		SlotName slotName3 = new SlotName();
		slotName3.setSlotName("水果");
		slotName3.setSlotId(9);
		slotName3.setSlotType(3);
		slotName3.setBet(75);
		slots.add(slotName3);
		
		
		SlotName slotName4 = new SlotName();
		slotName4.setSlotName("沙滩");
		slotName4.setSlotId(13);
		slotName4.setSlotType(4);
		slotName4.setBet(75);
		slots.add(slotName4);
		
		SlotName slotName5 = new SlotName();
		slotName5.setSlotName("吸血鬼");
		slotName5.setSlotId(17);
		slotName5.setSlotType(5);
		slotName5.setBet(160);
		slots.add(slotName5);
		
		/***************************** 6 - 9 **********************************************/
		SlotName slotName6 = new SlotName();
		slotName6.setSlotName("埃及艳后");
		slotName6.setSlotId(21);
		slotName6.setSlotType(6);
		slotName6.setBet(200);
		slots.add(slotName6);
		
		
		SlotName slotName7 = new SlotName();
		slotName7.setSlotName("美人鱼");
		slotName7.setSlotId(25);
		slotName7.setSlotType(7);
		slotName7.setBet(250);
		slots.add(slotName7);
		
		SlotName slotName8 = new SlotName();
		slotName8.setSlotName("澳门女神");
		slotName8.setSlotId(29);
		slotName8.setSlotType(8);
		slotName8.setBet(160);
		slots.add(slotName8);
		
		SlotName slotName9 = new SlotName();
		slotName9.setSlotName("白蛇传");
		slotName9.setSlotId(33);
		slotName9.setSlotType(9);
		slotName9.setBet(200);
		slots.add(slotName9);
		
		/***************************** 10 -15 **********************************************/
		SlotName slotName10 = new SlotName();
		slotName10.setSlotName("马来网红");
		slotName10.setSlotId(37);
		slotName10.setSlotType(10);
		slotName10.setBet(100);
		slots.add(slotName10);
		
		SlotName slotName11 = new SlotName();
		slotName11.setSlotName("日月潭");
		slotName11.setSlotId(41);
		slotName11.setSlotType(11);
		slotName11.setBet(100);
		slots.add(slotName11);
		
		SlotName slotName12 = new SlotName();
		slotName12.setSlotName("维密");
		slotName12.setSlotId(45);
		slotName12.setSlotType(12);
		slotName12.setBet(100);
		slots.add(slotName12);
		
		SlotName slotName13 = new SlotName();
		slotName13.setSlotName("宙斯");
		slotName13.setSlotId(49);
		slotName13.setSlotType(13);
		slotName13.setBet(100);
		slots.add(slotName13);
		
		SlotName slotName14 = new SlotName();
		slotName14.setSlotName("石器时代");
		slotName14.setSlotId(53);
		slotName14.setSlotType(14);
		slotName14.setBet(100);
		slots.add(slotName14);
		
		SlotName slotName15 = new SlotName();
		slotName15.setSlotName("狮身人面");
		slotName15.setSlotId(57);
		slotName15.setSlotType(15);
		slotName15.setBet(100);
		slots.add(slotName15);
		
		/***************************** 16 - 20 **********************************************/
		SlotName slotName16 = new SlotName();
		slotName16.setSlotName("阿兹特克文明");
		slotName16.setSlotId(61);
		slotName16.setSlotType(16);
		slotName16.setBet(100);
		slots.add(slotName16);
		
		SlotName slotName17 = new SlotName();
		slotName17.setSlotName("动物狼");
		slotName17.setSlotId(65);
		slotName17.setSlotType(17);
		slotName17.setBet(100);
		slots.add(slotName17);
		
		SlotName slotName18 = new SlotName();
		slotName18.setSlotName("动物猫");
		slotName18.setSlotId(69);
		slotName18.setSlotType(18);
		slotName18.setBet(100);
		slots.add(slotName18);
		
		SlotName slotName19 = new SlotName();
		slotName19.setSlotName("水晶魔法宝石");
		slotName19.setSlotId(73);
		slotName19.setSlotType(19);
		slotName19.setBet(100);
		slots.add(slotName19);
		
		SlotName slotName20 = new SlotName();
		slotName20.setSlotName("泰国象");
		slotName20.setSlotId(77);
		slotName20.setSlotType(20);
		slotName20.setBet(100);
		slots.add(slotName20);
		
		
		
		
		/***************************** 21 - 25 **********************************************/
		SlotName slotName21 = new SlotName();
		slotName21.setSlotName("老虎");
		slotName21.setSlotId(81);
		slotName21.setSlotType(21);
		slotName21.setBet(100);
		slots.add(slotName21);
		
		SlotName slotName22 = new SlotName();
		slotName22.setSlotName("西部牛仔");
		slotName22.setSlotId(85);
		slotName22.setSlotType(22);
		slotName22.setBet(100);
		slots.add(slotName22);
		
		SlotName slotName23 = new SlotName();
		slotName23.setSlotName("东方龙");
		slotName23.setSlotId(89);
		slotName23.setSlotType(23);
		slotName23.setBet(100);
		slots.add(slotName23);
		
		SlotName slotName24 = new SlotName();
		slotName24.setSlotName("巴西风情");
		slotName24.setSlotId(93);
		slotName24.setSlotType(24);
		slotName24.setBet(100);
		slots.add(slotName24);
		
		SlotName slotName25 = new SlotName();
		slotName25.setSlotName("忍者");
		slotName25.setSlotId(97);
		slotName25.setSlotType(25);
		slotName25.setBet(100);
		slots.add(slotName25);
		
		
		/***************************** 26 - 30 **********************************************/
		SlotName slotName26 = new SlotName();
		slotName26.setSlotName("女巫魔法");
		slotName26.setSlotId(101);
		slotName26.setSlotType(26);
		slotName26.setBet(100);
		slots.add(slotName26);
		
		
		SlotName slotName27 = new SlotName();
		slotName27.setSlotName("犀牛");
		slotName27.setSlotId(105);
		slotName27.setSlotType(27);
		slotName27.setBet(100);
		slots.add(slotName27);
		
		SlotName slotName28 = new SlotName();
		slotName28.setSlotName("海洋世界");
		slotName28.setSlotId(109);
		slotName28.setSlotType(28);
		slotName28.setBet(120);
		slots.add(slotName28);
		
		SlotName slotName29 = new SlotName();
		slotName29.setSlotName("西方龙");
		slotName29.setSlotId(113);
		slotName29.setSlotType(29);
		slotName29.setBet(100);
		slots.add(slotName29);
		
		SlotName slotName30 = new SlotName();
		slotName30.setSlotName("福尔摩斯");
		slotName30.setSlotId(117);
		slotName30.setSlotType(30);
		slotName30.setBet(100);
		slots.add(slotName30);
		
	}
	/**
	 * 上边每个老虎机的信息没有用了
	 * @param start
	 * @param end
	 * @param dbService
	 * @return
	 */
	public List<SlotName> getSlots(int start,int end, DBService dbService){
		List<SlotListEntity> slotLists = dbService.getAll(SlotListEntity.class);
		List<SlotName> backList = new ArrayList<SlotName>();
		for(int i=0;i<slotLists.size();i++){
			int slotType = slotLists.get(i).getType();
			if(slotType>=start && slotType <= end){
				backList.add(slots.get(i));
			}
		}
		return backList;
	}
}
