package com.gameserver.bazoo.service.showHand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.player.Player;

/**
 * 梭哈的一些比对信息
 * @author JavaServer
 * 主要计算 牛牛 中 的色子是什么类型
 * 
 * 类型  
 * 	6、豹子 -- 五颗点数相同的色子   --  倍率
 * 	5、炸弹 -- 四颗点数相同的色子 --  倍率
 * 	4、葫芦 -- 三颗点数相同的色子  + 两颗点数相同的色子   --倍率
 * 	3、三条 -- 三颗点数相同的色子 -- 倍率
 * 	2、两对 -- 两颗点数相同的色子  + 两颗点数相同的色子-- 倍率
 * 	1、一对 -- 两颗点数相同的色子 -- 倍率
 * 	0、散点 -- 无法组合成以上牌型的色子-- 倍率
 * @author JavaServer
 *
 */
public class ShowHandLogic {
	
	private Logger logger = Loggers.BAZOO;
	/**
	 * 判断一下当前是什么牌
	 */
	public Card judgeCards(List<Integer> diceValues){
		
		/**
		 * 首先看看是不是豹子
		 */
		List<Integer> allDices = new ArrayList<Integer>();
		for(Integer one:diceValues){
			if(!allDices.contains(one)){
				allDices.add(one);
			}
		}
		int len = allDices.size();
		Card card = new Card();
		if(len==1){//豹子
			card.setCardName("豹子");
			card.setCardNum(6);
			card.setPanther(allDices.get(0));//豹子是几点 的豹子
			return card;
		}else if(len==2){//炸弹  或者  葫芦
			List<Integer> value1 = new ArrayList<Integer>();
			value1.add(allDices.get(0));
			List<Integer> value2 = new ArrayList<Integer>();
			value2.add(allDices.get(1));
			for(Integer one:diceValues){
				if(value1.contains(one)){
					value1.add(one);
				}else if(value2.contains(one)){
					value2.add(one);
				}
			}
			/**
			 * 把多出来的一个移除掉
			 */
			value1.remove(0);
			value2.remove(0);
			
			
			if(value1.size()==4 || value2.size() == 4){//炸弹
				card.setCardName("炸弹");
				card.setCardNum(5);
				if(value1.size() == 4){
					card.setBomb(value1.get(0),value2.get(0));//设置炸弹点数
				}else if(value1.size() == 1){
					card.setBomb(value2.get(0),value1.get(0));//设置炸弹点数
				}
				return card;
			}else{//葫芦
				card.setCardName("葫芦");
				card.setCardNum(4);
				if(value1.size() == 3){
					card.setGourd(value1.get(0),value2.get(0));//设置炸弹点数
				}
				if(value1.size() == 2){
					card.setGourd(value2.get(0),value1.get(0));//设置炸弹点数
				}
				return card;
			}
		}else if(len == 3){// 三条 或者 两对
			List<Integer> value1 = new ArrayList<Integer>();
			value1.add(allDices.get(0));
			List<Integer> value2 = new ArrayList<Integer>();
			value2.add(allDices.get(1));
			List<Integer> value3 = new ArrayList<Integer>();
			value3.add(allDices.get(2));
			
			for(Integer one:diceValues){
				if(value1.contains(one)){
					value1.add(one);
				}else if(value2.contains(one)){
					value2.add(one);
				}else if(value3.contains(one)){
					value3.add(one);
				}
			}
			/**
			 * 把多出来的一个移除掉
			 */
			value1.remove(0);
			value2.remove(0);
			value3.remove(0);
			
			
			if(value1.size() == 3 || value2.size() == 3 || value3.size() == 3){//三条
				card.setCardName("三条");
				card.setCardNum(3);
			}else{
				card.setCardName("两对");
				card.setCardNum(2);
				
			}
			//三条
			if(value1.size() == 3){
				setThree(card, value1, value2, value3);
			}else if(value2.size() == 3){
				setThree(card, value2, value1, value3);
			}else if(value3.size() == 3){
				setThree(card, value3, value1, value2);
				
			/**
			 * value值 没有一个等于三 的 说明是 两对
			 * 
			 */
			}else if(value1.size() == 2 || value2.size() == 2){
				setTwo(card, value1, value2, value3);
				
			}else if(value1.size() == 2 || value3.size() == 2){
				setTwo(card, value1, value3, value2);
				
			}else if(value2.size() == 2 || value3.size() == 2){
				setTwo(card, value2, value3, value1);
			}else{
				logger.info("[无双吹牛]---[梭哈模式]---对比三条和两对时,something fucking wrong");
			}
			return card;
		}else if(len == 4){// 一对 
			List<Integer> value1 = new ArrayList<Integer>();
			value1.add(allDices.get(0));
			List<Integer> value2 = new ArrayList<Integer>();
			value2.add(allDices.get(1));
			List<Integer> value3 = new ArrayList<Integer>();
			value3.add(allDices.get(2));
			List<Integer> value4 = new ArrayList<Integer>();
			value4.add(allDices.get(3));
			
			for(Integer one:diceValues){
				if(value1.contains(one)){
					value1.add(one);
				}else if(value2.contains(one)){
					value2.add(one);
				}else if(value3.contains(one)){
					value3.add(one);
				}else if(value4.contains(one)){
					value4.add(one);
				}
			}
			/**
			 * 把多出来的一个移除掉
			 */
			value1.remove(0);
			value2.remove(0);
			value3.remove(0);
			value4.remove(0);
			
			card.setCardName("一对");
			card.setCardNum(1);
			if(value1.size()==2){
				setOne(card, value1, value2, value3, value4);
				
			}else if(value2.size()==2){
				setOne(card, value2, value1, value3, value4);				
				
			}else if(value3.size()==2){
				setOne(card, value3, value1, value2, value4);			
				
			}else if(value4.size()==2){
				setOne(card, value4, value1, value2, value3);			
				
			}
			
			return card;
		}else if(len == 5){//散点
			card.setCardName("散点");
			card.setCardNum(0);
			List<Integer> all = new ArrayList<Integer>();
			for(int i=0;i<allDices.size();i++){
				all.add(allDices.get(i)==1?7:allDices.get(i));
			}
			//排序
			compare(all);
			//7 在变回 1
			turnSevenToOne(all);
			card.setDisperse(all.get(0), all.get(1), all.get(2), all.get(3), all.get(4));
			return card;
			
		}else{
			logger.info("[无双吹牛]---[梭哈模式]---[点数问题],something fucking wrong");
			return card;
		}
		
	}
	
	private void setThree(Card card,List<Integer> value1,List<Integer> value2,List<Integer> value3){
		int value2v = this.getIfOneTurnSeven(value2.get(0));
		int value3v = this.getIfOneTurnSeven(value3.get(0));
		if(value2v>value3v){
			card.setThree(value1.get(0), value2.get(0), value3.get(0));
		}else{
			card.setThree(value1.get(0), value3.get(0), value2.get(0));
		}
	}
	private void setTwo(Card card,List<Integer> value1,List<Integer> value2,List<Integer> value3){
		int value1v = this.getIfOneTurnSeven(value1.get(0));
		int value2v = this.getIfOneTurnSeven(value2.get(0));
		int value3v = value3.get(0);
		if(value1v>value2v){
			card.setTwo(value1.get(0), value2.get(0), value3v);
		}else{
			card.setTwo(value2.get(0), value1.get(0), value3v);
		}
	}
	
	private void setOne(Card card,List<Integer> value1,List<Integer> value2,List<Integer> value3,List<Integer> value4){
		List<Integer> all = new ArrayList<Integer>();
		all.add(this.getIfOneTurnSeven(value2.get(0)));
		all.add(this.getIfOneTurnSeven(value3.get(0)));
		all.add(this.getIfOneTurnSeven(value4.get(0)));
		this.compare(all);
		//7 在变回 1
		turnSevenToOne(all);
		card.setOne(value1.get(0), all.get(0), all.get(1), all.get(2));
	}
	
	/**
	 * 翻转  从 7 在变回 1
	 */
	private void turnSevenToOne(List<Integer> all){
		for(int i=0;i<all.size();i++){
			if(all.get(i) == 7){
				all.set(i, 1);
			}
		}
	}
	/**
	 * 排序
	 * @param all
	 */
	private void compare(List<Integer> all){
		Collections.sort(all, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1.intValue() > o2.intValue()){
					return 1;
				}else if(o1.intValue() < o2.intValue()){
					return -1;
				}else{
					return 0;
				}
			}
		});
	}
	
	
	
	/**
	 * 取出牌 最小的人
	 * @return
	 */
	public List<Player> getLittlePlayer(List<Player> allPlayerList){
		List<Player> lessPlayers = new ArrayList<Player>();
		Player lessPlayer = null;
		for(Player every:allPlayerList){
			if(lessPlayer == null){
				lessPlayer=every;
			}else{
				Card lessCard = lessPlayer.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
				Card everyCard = every.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
				if(lessCard == null || everyCard == null){
					continue;
				}
				int num = compare2Card(lessCard,everyCard);
				if(num == 1){//大于 （只能是大于）
					lessPlayer=every;
				}
			}
		}
		//把最小的添加进去
		lessPlayers.add(lessPlayer);
		//然后看看 这个屋子的人 有没有和他 一样的
		for(Player every:allPlayerList){
			if(every.getPassportId() == lessPlayer.getPassportId()){
				continue; 
			}
			Card lessCard = lessPlayer.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
			Card everyCard = every.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard();
			if(lessCard == null || everyCard == null){
				continue;
			}
			int num = compare2Card(lessCard,everyCard);
			if(num == 0){//大于 （只能是大于）
				lessPlayer=every;
				lessPlayers.add(every);
			}
			
		}
		
		
		return lessPlayers;
	}
	
	
	/**
	 * 判断大小
	 * 1 前边 > 后边
	 * -1 前边 < 后边
	 * 0 前边 = 后边
	 * @param lessCard
	 * @param everyCard
	 * @return
	 */
	private int compare2Card(Card lessCard,Card everyCard){
		int lessCardNum = lessCard.getCardNum();
		int everyCardNum = everyCard.getCardNum();
		if(lessCardNum > everyCardNum){
			return 1;
		}else if(lessCardNum == everyCardNum){
			if(lessCardNum == 6){//豹子   如果点数 是1 就按7来算
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				if(lessOne == everyOne){
					return 0;
				}else if(lessOne > everyOne ){
					return 1;
				}else if(lessOne < everyOne ){
					return -1;
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[豹子]---[有问题]::"+lessCardNum);
					return 0;
				}
			}else if(lessCardNum == 5 || lessCardNum == 4){//炸弹   //葫芦   如果点数 是1 就按7来算
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				
				
				int lessFive = getIfOneTurnSeven(lessCard.getCardPointFive());
				int everyFive = getIfOneTurnSeven(everyCard.getCardPointFive());
				
				if(lessOne == everyOne && lessFive == everyFive){
					return 0;
				}else if(lessOne == everyOne && lessFive > everyFive ){
					return 1;
				}else if(lessOne == everyOne && lessFive < everyFive ){
					return -1;
				}else if(lessOne > everyOne){
					return 1;
				}else if(lessOne < everyOne){
					return -1;
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[炸弹葫芦]---[有问题]::"+lessCardNum);
					return 0;
				}
			}else if(lessCardNum == 3){//三条    如果点数 是1 就按7来算
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				
				int lessFour = lessCard.getCardPointFour();
				int lessFive = lessCard.getCardPointFive();
				
				
				int everyFour = everyCard.getCardPointFour();
				int everyFive = everyCard.getCardPointFive();
				
				if(lessOne == everyOne && lessFour == everyFour && lessFive==everyFive){
					return 0;
					
				}else if(lessOne == everyOne && lessFour >everyFour){
					return 1;
				}else if(lessOne == everyOne && lessFour <everyFour){
					return -1;
				
				}else if(lessOne == everyOne && lessFour  == everyFour && lessFive > everyFive){
					return 1;
				}else if(lessOne == everyOne && lessFour  == everyFour && lessFive < everyFive){
					return -1;
					
				}else if(lessOne > everyOne){
					return 1;
				}else if(lessOne < everyOne){
					return -1;
					
					
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[三条]---[有问题]::"+lessCardNum);
					return 0;
				}
				
			}else if(lessCardNum == 2){//两对
				
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int lessThree = getIfOneTurnSeven(lessCard.getCardPointThree());
				int lessFive = getIfOneTurnSeven(lessCard.getCardPointFive());
				
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				int everyThree = getIfOneTurnSeven(everyCard.getCardPointThree());
				int everyFive = getIfOneTurnSeven(everyCard.getCardPointFive());
				
				if(lessOne == everyOne && lessThree == everyThree && lessFive==everyFive){
					return 0;
					
					
				}else if(lessOne > everyOne){
					return 1;
				}else if(lessOne < everyOne){
					return -1;
					
					
				}else if(lessOne == everyOne && lessThree > everyThree){
					return 1;
				}else if(lessOne == everyOne && lessThree < everyThree){
					return -1;
					
				}else if(lessOne == everyOne && lessThree == everyThree && lessFive > everyFive){
					return 1;
				}else if(lessOne == everyOne && lessThree == everyThree && lessFive < everyFive){
					return -1;
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[两对]---[有问题]::"+lessCardNum);
					return 0;
				}
				
			}else if(lessCardNum == 1){//一对
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int lessThree = getIfOneTurnSeven(lessCard.getCardPointThree());
				int lessFour = getIfOneTurnSeven(lessCard.getCardPointFour());
				int lessFive = getIfOneTurnSeven(lessCard.getCardPointFive());
				
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				int everyThree = getIfOneTurnSeven(everyCard.getCardPointThree());
				int everyFour = getIfOneTurnSeven(everyCard.getCardPointFour());
				int everyFive = getIfOneTurnSeven(everyCard.getCardPointFive());
				
				
				if(lessOne == everyOne && lessThree == everyThree &&  lessFour==everyFour && lessFive == everyFive){
					return 0;
				}else if(lessOne == everyOne && lessThree == everyThree &&  lessFour==everyFour && lessFive > everyFive){
					return 1;
				}else if(lessOne == everyOne && lessThree == everyThree &&  lessFour==everyFour && lessFive < everyFive){
					return -1;
				}else if(lessOne == everyOne && lessThree == everyThree &&  lessFour > everyFour ){
					return 1;
				}else if(lessOne == everyOne && lessThree == everyThree &&  lessFour <everyFour ){
					return -1;
				}else if(lessOne == everyOne && lessThree > everyThree  ){
					return 1;
				}else if(lessOne == everyOne && lessThree < everyThree  ){
					return -1;
				}else if(lessOne > everyOne ){
					return 1;
				}else if(lessOne < everyOne ){
					return -1;
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[一对]---[有问题]::"+lessCardNum);
					return 0;
				}
			}else if(lessCardNum == 0){//散点
				int lessOne = getIfOneTurnSeven(lessCard.getCardPointOne());
				int lessTwo = getIfOneTurnSeven(lessCard.getCardPointTwo());
				int lessThree = getIfOneTurnSeven(lessCard.getCardPointThree());
				int lessFour = getIfOneTurnSeven(lessCard.getCardPointFour());
				int lessFive = getIfOneTurnSeven(lessCard.getCardPointFive());
				
				int everyOne = getIfOneTurnSeven(everyCard.getCardPointOne());
				int everyTwo = getIfOneTurnSeven(everyCard.getCardPointTwo());
				int everyThree = getIfOneTurnSeven(everyCard.getCardPointThree());
				int everyFour = getIfOneTurnSeven(everyCard.getCardPointFour());
				int everyFive = getIfOneTurnSeven(everyCard.getCardPointFive());
				
				if(lessOne == everyOne && lessTwo == everyTwo &&  lessThree == everyThree &&  lessFour==everyFour && lessFive == everyFive){
					return 0;
				}else if(lessOne == everyOne && lessTwo == everyTwo &&  lessThree == everyThree &&  lessFour==everyFour && lessFive > everyFive){
					return 1;
				}else if(lessOne == everyOne && lessTwo == everyTwo &&  lessThree == everyThree &&  lessFour==everyFour && lessFive < everyFive){
					return -1;
				}else if(lessOne == everyOne && lessTwo == everyTwo &&  lessThree == everyThree &&  lessFour > everyFour ){
					return 1;
				}else if(lessOne == everyOne && lessTwo == everyTwo &&  lessThree == everyThree &&  lessFour <everyFour ){
					return -1;
				}else if(lessOne == everyOne && lessTwo == everyTwo && lessThree > everyThree  ){
					return 1;
				}else if(lessOne == everyOne && lessTwo == everyTwo && lessThree < everyThree  ){
					return -1;
				}else if(lessOne == everyOne && lessTwo > everyTwo   ){
					return 1;
				}else if(lessOne == everyOne && lessTwo < everyTwo   ){
					return -1;
				}else if(lessOne > everyOne ){
					return 1;
				}else if(lessOne < everyOne ){
					return -1;
				}else{
					logger.info("[无双吹牛]---[梭哈模式]---[对比]---[散点]---[有问题]::"+lessCardNum);
					return 0;
				}
			}else{
				logger.info("[无双吹牛]---[梭哈模式]---[对比]---相等时出现问题"+lessCardNum);
				return 0;
			}
		
		}else{
			logger.info("[无双吹牛]---[梭哈模式]---[对比]---lessCardNum::"+lessCardNum);
			return -1;
		}
		
	}
	
	/**
	 * 如果是 1 就换成 7
	 * @param value
	 * @return
	 */
	private int getIfOneTurnSeven(int value){
		if(value == 1){
			return 7;
		}
		return value;
	}
	
	
	
	/**
	 * 测试  比较连个色子
	 * @param args
	 */
	public static void main(String[] args){
		
		ShowHandLogic ShowHandLogic = new ShowHandLogic();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(6);
		list.add(3);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(1);
		list2.add(1);
		list2.add(1);
		list2.add(5);
		list2.add(4);
		
		Card lessCard =ShowHandLogic.judgeCards(list);
		Card bigCard =ShowHandLogic.judgeCards(list2);
		int c = ShowHandLogic.compare2Card(lessCard,bigCard);
		
		System.out.println(c);
	}
	
	
	
	
}
