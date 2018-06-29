package com.gameserver.texas.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.model.Card;
import com.common.model.Card.CardTypeEnum;
import com.common.model.Card.CardValueEnum;
import com.core.util.Assert;

/**
 * 德州手牌
 * @author wayne
 *
 */
public class TexasHandCard implements Comparable<TexasHandCard>{
	
	
	public enum TexasHandCardEnum  implements Comparable<TexasHandCardEnum>{
		HIGH_CARD(0),
		ONE_PAIR(1),
		TWO_PAIR(2),
		THREE_KIND(3),
		STRAIGHT(4),
		FLUSH(5),
		FULL_HOUSE(6),
		/**四条 **/
		FOUR_KIND(7),
		/** 同花顺**/
		STRAIGHT_FLUSH(8),
		/**皇家同花顺 **/
		ROYAL_STRAIGHT_FLUSH(9),;
		
		private int index;
		private TexasHandCardEnum(int index){
			this.index=  index;
		}
		public int getIndex()
		{
			return index;
		}
	
	};
	
	
	
	private TexasHandCardEnum texasHandCardEnum;
	private List<Card> cardList = new ArrayList<Card>();
	private List<Card> finalCardList = new ArrayList<Card>();
	

	public TexasHandCardEnum getTexasHandCardEnum() {
		return texasHandCardEnum;
	}

	public void setTexasHandCardEnum(TexasHandCardEnum texasHandCardEnum) {
		this.texasHandCardEnum = texasHandCardEnum;
	}
	
	public List<Card> getCardList(){
		return cardList;
	}

	public List<Card> getFinalCardList() {
		return finalCardList;
	}

	

	@Override
	public int compareTo(TexasHandCard texasHandCard) {
		// TODO Auto-generated method stub
		int result =this.getTexasHandCardEnum().compareTo(texasHandCard.getTexasHandCardEnum());
		if(result == 0)
		{
		
			int selfSize= this.getCardList().size();
			int otherSize= texasHandCard.getCardList().size();
			Assert.isTrue(selfSize== otherSize,"牌型【"+this.getTexasHandCardEnum()+"】["+selfSize+"]["+otherSize+"]比较数量有误");
			
			for(int i=0;i<selfSize;i++)
			{
				result = this.getCardList().get(i).actualCompareTo(texasHandCard.getCardList().get(i));
				if(result != 0)
					return result;
			}
			
		}
		
		if(result>0)
			result =1;
		else if(result<0)
			result = -1;
		return result;
	}
	
	/*public static void convertCards(List<Card> cardList,HashMap<CardTypeEnum,List<Card>> cardMap,List<Card> soloCardList,List<Card> highCardList,List<Card>pairKindList,List<Card>threeKindList,List<Card>fourKindList){
	
		
		Card previousCard = null;
		int numOfEqual = 0;
		

		for(int i=0;i<cardList.size();i++)
		{
			Card card = cardList.get(i);
			List<Card> typeList= cardMap.get(card.getCardType());
			if(typeList == null)
			{
				typeList= new ArrayList<Card>();
				cardMap.put(card.getCardType(), typeList);
			}
			typeList.add(card);
			
			if(previousCard == null)
			{	
				previousCard = card;
				++numOfEqual;
				continue;
			}
			
			//相等
			if(previousCard.actualCompareTo(card)==0)
			{
				++numOfEqual;
				previousCard = card;
				if(i!=cardList.size()-1)
					continue;
				
			}
			else
			{
				if(numOfEqual ==2)
				{
					pairKindList.add(previousCard);
				
				}
				else if(numOfEqual ==3)
				{
					threeKindList.add(previousCard);
					
				}
				else if(numOfEqual == 4)
				{
					fourKindList.add(previousCard);
				}
				else
				{
					highCardList.add(previousCard);
				}
				
				soloCardList.add(previousCard);
				previousCard = card;
				numOfEqual=1;
				if(i!=cardList.size()-1)
					continue;
			}
			
			//不相等
			if(numOfEqual ==2)
			{
				pairKindList.add(card);
			}
			else if(numOfEqual ==3)
			{
				threeKindList.add(card);
			}
			else if(numOfEqual == 4)
			{
		
				fourKindList.add(card);
			}
			else
			{
			
				highCardList.add(card);
			}
			soloCardList.add(card);
		}
		
	}*/
	
	
	public static TexasHandCard convertFrom(List<Card> cardList)
	{
		
		TexasHandCard texasHandCard = new TexasHandCard();

		Card previousCard = null;
		List<Card> fourKindList = new ArrayList<Card>();
		List<Card> threeKindList= new ArrayList<Card>();
		List<Card> pairKindList = new ArrayList<Card>();
		List<Card> highCardList = new ArrayList<Card>();
		List<Card> soloCardList = new ArrayList<Card>();
		
		Map<CardTypeEnum,List<Card>> cardMap = new HashMap<CardTypeEnum,List<Card>>();

		int numOfEqual = 0;
		

		for(int i=0;i<cardList.size();i++)
		{
			Card card = cardList.get(i);
			List<Card> typeList= cardMap.get(card.getCardType());
			if(typeList == null)
			{
				typeList= new ArrayList<Card>();
				cardMap.put(card.getCardType(), typeList);
			}
			typeList.add(card);
			
			if(previousCard == null)
			{	
				previousCard = card;
				++numOfEqual;
				continue;
			}
			
			//相等
			if(previousCard.actualCompareTo(card)==0)
			{
				++numOfEqual;
				previousCard = card;
				if(i!=cardList.size()-1)
					continue;
				
			}
			else
			{
				if(numOfEqual ==2)
				{
					pairKindList.add(previousCard);
				
				}
				else if(numOfEqual ==3)
				{
					threeKindList.add(previousCard);
					
				}
				else if(numOfEqual == 4)
				{
					fourKindList.add(previousCard);
				}
				else
				{
					highCardList.add(previousCard);
				}
				
				soloCardList.add(previousCard);
				previousCard = card;
				numOfEqual=1;
				if(i!=cardList.size()-1)
					continue;
			}
			
			//不相等
			if(numOfEqual ==2)
			{
				pairKindList.add(card);
			}
			else if(numOfEqual ==3)
			{
				threeKindList.add(card);
			}
			else if(numOfEqual == 4)
			{
		
				fourKindList.add(card);
			}
			else
			{
			
				highCardList.add(card);
			}
			soloCardList.add(card);
		}
		
		
		Iterator<Entry<CardTypeEnum, List<Card>>> iter = cardMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<CardTypeEnum, List<Card>> entry = iter.next();
			List<Card> tempTypeCardList = entry.getValue();
			if(tempTypeCardList.size()>=5)
			{
				texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.FLUSH);
				List<Card> containerStraight = containStraight(tempTypeCardList);
			
				if(containerStraight!=null)
				{
					Card firstCard = containerStraight.get(0);
					Card fiveCard = containerStraight.get(4);
					Card tempStraightCard= null;
					if(firstCard.getCardValue()== CardValueEnum.ACE && fiveCard.getCardValue()== CardValueEnum.KING)
					{
						tempStraightCard = firstCard;
						texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.ROYAL_STRAIGHT_FLUSH);
					}
					else
					{
						tempStraightCard = containerStraight.get(containerStraight.size()-1);
						texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.STRAIGHT_FLUSH);
					}
					
					texasHandCard.getCardList().add(tempStraightCard);
					texasHandCard.getFinalCardList().addAll(containerStraight);
					
					return texasHandCard;
				}
				
				add(texasHandCard.getCardList(),tempTypeCardList,1);
				add(texasHandCard.getFinalCardList(),tempTypeCardList,5);
				return texasHandCard;
			}
		}
		
		List<Card> containerStraight = containStraight(soloCardList);
		
		if(containerStraight!=null)
		{
			Card tempStraightCard=containerStraight.get(containerStraight.size()-1);
			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.STRAIGHT);
			texasHandCard.getCardList().add(tempStraightCard);
			texasHandCard.getFinalCardList().addAll(containerStraight);
			return texasHandCard;
		}
		
		//四条
		if(fourKindList.size()==1)
		{
			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.FOUR_KIND);
			Card fourKindCard = fourKindList.get(0);
			texasHandCard.getCardList().add(fourKindList.get(0));
			
			List<Card> tempSoloCardList= excludeCardsListFromCardList(soloCardList,fourKindCard);
			
			add(texasHandCard.getCardList(),tempSoloCardList,1);
			//加入牌型
			for(int i=0;i<cardList.size();i++)
			{
				Card tempCard =cardList.get(i);
				if(tempCard.getCardValue() == fourKindCard.getCardValue())
					texasHandCard.getFinalCardList().add(tempCard);
			}
			add(texasHandCard.getFinalCardList(),tempSoloCardList,1);
			
			return texasHandCard;
		}
		
		//葫芦或三条
		if(threeKindList.size()>=1)
		{
			if(threeKindList.size()==2)
			{
				texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.FULL_HOUSE);
				add(texasHandCard.getCardList(),threeKindList,2);
				Card threeKindCard =texasHandCard.getCardList().get(0);
				Card pairKindCard =texasHandCard.getCardList().get(1);
				//加入牌型
				for(int i=0;i<cardList.size();i++)
				{
					Card tempCard =cardList.get(i);
					
					if(tempCard.getCardValue() == threeKindCard.getCardValue())
						texasHandCard.getFinalCardList().add(tempCard);
				}
				
				for(int i=0;i<cardList.size();i++)
				{
					Card tempCard =cardList.get(i);
					
					if(tempCard.getCardValue() == pairKindCard.getCardValue())
					{
						texasHandCard.getFinalCardList().add(tempCard);
					
					}
				}
				
				return texasHandCard;
			}
			
			Card firstThreeKindCard = threeKindList.get(0);
			texasHandCard.getCardList().add(firstThreeKindCard);
			
			//加入牌型
			for(int i=0;i<cardList.size();i++)
			{
				Card tempCard =cardList.get(i);
				
				if(tempCard.getCardValue() == firstThreeKindCard.getCardValue())
					texasHandCard.getFinalCardList().add(tempCard);
			}
			
			if(pairKindList.size()>=1)
			{
				texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.FULL_HOUSE);
				add(texasHandCard.getCardList(),pairKindList,1);
				Card pairKindCard =texasHandCard.getCardList().get(1);
				//加入牌型
				for(int i=0;i<cardList.size();i++)
				{
					Card tempCard =cardList.get(i);
					
					if(tempCard.getCardValue() == pairKindCard.getCardValue())
						texasHandCard.getFinalCardList().add(tempCard);
				}
				
				return texasHandCard;
			}
		
			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.THREE_KIND);
			
			//获得除三张以外的高牌
			List<Card> tempSoloCardList= excludeCardsListFromCardList(soloCardList,firstThreeKindCard);
			
			add(texasHandCard.getCardList(),tempSoloCardList,2);
			add(texasHandCard.getFinalCardList(),tempSoloCardList,2);
			return texasHandCard;
		}
		
		//两对或三对
		if(pairKindList.size()>=2)
		{
			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.TWO_PAIR);
			
			add(texasHandCard.getCardList(),pairKindList,2);
			
			Card firstPairKindCard =texasHandCard.getCardList().get(0);
			Card secondPairKindCard =texasHandCard.getCardList().get(1);
		//	Card thirdPairKindCard =texasHandCard.getCardList().get(2);
			//加入牌型
			for(int i=0;i<cardList.size();i++)
			{
				Card tempCard =cardList.get(i);
				
				if(tempCard.getCardValue() == firstPairKindCard.getCardValue() || tempCard.getCardValue() == secondPairKindCard.getCardValue())
					texasHandCard.getFinalCardList().add(tempCard);
			}
			
//			if(highCardList.size()==0){
//			//	add(texasHandCard.getCardList(),pairKindList,3);
//				texasHandCard.getFinalCardList().add(thirdPairKindCard);
//				
//			}
//			else
//			{
//				Card thirdCard = highCardList.get(0);
//				int result =  thirdCard.actualCompareTo(thirdPairKindCard);
//				if(result==1){
//				//	add(texasHandCard.getCardList(),pairKindList,3);
//					texasHandCard.getFinalCardList().add(thirdPairKindCard);
//				}
//				else
//				{
//					texasHandCard.getCardList().remove(2);
//					texasHandCard.getCardList().add(thirdCard);
//					add(texasHandCard.getFinalCardList(),highCardList,1);
//				}
//			}
			List<Card> tempSoloCardList= excludeCardsListFromCardList(soloCardList,firstPairKindCard,secondPairKindCard);
			add(texasHandCard.getCardList(),tempSoloCardList,1);
			add(texasHandCard.getFinalCardList(),tempSoloCardList,1);
			return texasHandCard;
		
		}
		
		//两对
//		if(pairKindList.size()==2)
//		{
//			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.TWO_PAIR);
//			add(texasHandCard.getCardList(),pairKindList,2);
//			add(texasHandCard.getCardList(),highCardList,1);
//		
//			Card firstPairKindCard =texasHandCard.getCardList().get(0);
//			Card secondPairKindCard =texasHandCard.getCardList().get(1);
//			//加入牌型
//			for(int i=0;i<cardList.size();i++)
//			{
//				Card tempCard =cardList.get(i);
//				
//				if(tempCard.getCardValue() == firstPairKindCard.getCardValue() || tempCard.getCardValue() == secondPairKindCard.getCardValue())
//					texasHandCard.getFinalCardList().add(tempCard);
//			}
//			
//			add(texasHandCard.getFinalCardList(),highCardList,1);
//			return texasHandCard;
//		
//		}
		
		//一对
		if(pairKindList.size()==1)
		{
			texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.ONE_PAIR);
			add(texasHandCard.getCardList(),pairKindList,1);
			add(texasHandCard.getCardList(),highCardList,3);
			
			Card firstPairKindCard =texasHandCard.getCardList().get(0);
			//加入牌型
			for(int i=0;i<cardList.size();i++)
			{
				Card tempCard =cardList.get(i);
				
				if(tempCard.getCardValue() == firstPairKindCard.getCardValue())
					texasHandCard.getFinalCardList().add(tempCard);
			}
			
			add(texasHandCard.getFinalCardList(),highCardList,3);
			return texasHandCard;
		}
		
		texasHandCard.setTexasHandCardEnum(TexasHandCardEnum.HIGH_CARD);
		add(texasHandCard.getCardList(),highCardList,5);
		add(texasHandCard.getFinalCardList(),highCardList,5);
		return texasHandCard;
	}


	private static void add(List<Card>toCardList,List<Card>fromCardList,int num)
	{
		Assert.isTrue(fromCardList.size()>=num,"卡列表小于数目");
		Card firstCard = fromCardList.get(0);
		
		if(firstCard.getCardValue() == CardValueEnum.ACE)
		{
			if(!toCardList.contains(firstCard))
			{
				toCardList.add(firstCard);
				--num;
			}
		}
		
		int start = fromCardList.size()-1;
	
		while(num>0)
		{
			for(int i=start;i>=0;i--)
			{
				Card card = fromCardList.get(i);
				if(!toCardList.contains(card))
				{
					toCardList.add(card);
					start = i-1;
					break;
				}
			}
	
			--num;
		}
		
	
	}
	
	private static List<Card> containStraight(List<Card> fromCardList)
	{
		
		List<Card> containList = new ArrayList<Card>();
		
		if(fromCardList.size()<5)
			return null;
		
		
		int num = fromCardList.size();
		if(fromCardList.get(0).getCardValue() == CardValueEnum.ACE)
			++num;
		Card previousCard= null;
		
		for(int i=0;i<num;i++)
		{
			Card card;
			if(i>=fromCardList.size())
				card = fromCardList.get(i-fromCardList.size());
			else
				card = fromCardList.get(i);
			
			if(previousCard !=null)
			{
				if(!Card.isAdjoin(previousCard,card))
				{
					if(containList.size()>=5)
					{
						break;
					}
					
					containList.clear();
					previousCard = null;
				}
			}
			
			containList.add(card);
			
			
			if(num-i+containList.size()<5)
				break;
			
			previousCard = card;
		}
		
		while(containList.size()>5)
		{
			containList.remove(0);
		}
		
		if(containList.size()==5)
		{
			
			return containList;
		}
		containList.clear();
		return null;
	}
	
	/**
	 *排除特殊组合牌
	 * @param originalList
	 * @param cards
	 * @return
	 */
	public static List<Card> excludeCardsListFromCardList(List<Card> originalList,Card ...cards){
		Iterator<Card> tempIter=originalList.iterator();
		while(tempIter.hasNext()){
			Card temp = tempIter.next();
			boolean found = false;
			for(Card tempCard:cards){
				if(temp.getCardValue()!=tempCard.getCardValue()){
					continue;
				}
				found = true;
				break;
			}
			if(!found){
				continue;
			}
			tempIter.remove();
		}
		return originalList;
	}
	

	
	private static int[][] handcardSameCardTypeWeightsArrayOfArray = {
		{0,13,14,14,15,14,14,15,16,19,20,22,24},
		{13,0,7,7,8,6,6,6,7,8,8,9,11},
		{14,7,0,8,9,8,7,6,7,8,9,10,11},
		{14,7,8,0,10,10,9,8,7,8,9,10,12},
		{15,8,6,10,0,11,10,9,8,8,9,10,12},
		{14,6,8,10,11,0,11,11,10,10,9,11,12},
		{14,6,7,9,10,11,0,12,12,11,11,11,13},
		{15,6,6,8,9,11,12, 0,13,13,13,13,13},
		{16,7,7,7,8,10,12,13, 0,15,15,15,15},
		{19,8,8,8,8,10,11,13, 15,0,18,18,18},
		{20,8,9,9,9,9,11,13, 15,18,0,19,20},
		{20,9,10,10,10,11,11,13, 15,18,19,0,21},
		{24,11,11,11,12,12,13,13, 15,18,20,21,0},
		
	};
	
	private static int[][] handcardWeightsArrayOfArray = {
		{0,7,7,8,8,7,8,9,10,13,14,16,19},
		{7,0,1,2,2,1,0,0,1,2,2,3,4},
		{7,1,0,3,4,3,1,1,1,2,2,3,5},
		{8,2,3,0,5,4,3,2,1,2,3,4,5},
		{8,2,4,5,0,6,5,4,3,2,3,4,5},
		{7,1,3,4,6,0,6,5,4,4,3,4,6},
		{8,0,1,3,5,6,0,7,6,6,5,5,6},
		{9,0,1,2,4,5,7, 0,8,8,7,7,7},
		{10,1,1,1,3,4,6,8, 0,10,9,9,9},
		{13,2,2,2,2,4,6,8, 10,0,13,12,13},
		{14,2,2,3,3,3,5,7, 9,13,0,14,14},
		{16,3,3,4,4,4,5,7, 9,12,14,0,16},
		{19,7,7,8,8,7,8,9, 10,13,14,16,0},
		
	};
	
	public final static int MIN_HAND_CARD_WEIGHT = 10;
	public final static int SECOND_MIN_HAND_CARD_WEIGHT = 19;
	public final static int TOTAL_CARDS_NUM = 52;
	
	/**
	 * 手牌权重
	 * @param firstCard
	 * @param secondCard
	 * @return
	 */
	public static int weightForHandCard(Card firstCard,Card secondCard){
		int temp1 = firstCard.getCardValue().getIndex()-1;
		int temp2 = secondCard.getCardValue().getIndex()-1;
		
		if(firstCard.getCardType() == secondCard.getCardType()){
			return handcardWeightsArrayOfArray[temp1][temp2];
		}
		return  handcardSameCardTypeWeightsArrayOfArray[temp1][temp2];
	}
	
	
}
