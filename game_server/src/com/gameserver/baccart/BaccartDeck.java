package com.gameserver.baccart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.common.model.Card;
import com.common.model.Card.CardValueEnum;
import com.core.util.MathUtils;

/**
 * 百家乐
 * @author wayne
 *
 */
public class BaccartDeck {
	
	private final int DECK_NUM = 8;
	private final int DECK_CARDS_NUM = 52;
	private final float MIN_RATE = 0.3f;
	private final float MAX_RATE =0.5f;
	
	private List<Card> cardList = new ArrayList<Card>(DECK_NUM*DECK_CARDS_NUM);
	private List<Card> usedCardList = new ArrayList<Card>(DECK_NUM*DECK_CARDS_NUM);
	
	public BaccartDeck(){
		
		for(int i=0;i<DECK_NUM;i++){
			for(Card.CardValueEnum tempValue : Card.CardValueEnum.values()){
				if(tempValue == CardValueEnum.BLACK_JOKER || tempValue == CardValueEnum.RED_JOKER)
					continue;
		
				for(Card.CardTypeEnum tempType : Card.CardTypeEnum.values())
				{
					cardList.add(new Card(tempType,tempValue));
				}
			}
		}
	}
	

	
	/**
	 * 获取下一张牌
	 * @return
	 */
	public Card getNextCard(){
		int randomIndex= (int)MathUtils.random(0f,(float)cardList.size());
		
		Card card = cardList.remove(randomIndex);
		usedCardList.add(card);
		return card;
	}
	
	/**
	 * 获取下一张指定牌型
	 */
	public Card getNextCard(List<CardValueEnum> aCardValueEnumList){
		Iterator<Card> tempCardIter=cardList.iterator();
		while(tempCardIter.hasNext()){
			Card tempCard = tempCardIter.next();
			if(aCardValueEnumList.contains(tempCard.getCardValue())){
				tempCardIter.remove();
				usedCardList.add(tempCard);
				return tempCard;
			}
		}
		return null;
	}
	
	/**
	 * 洗牌
	 */
	public void shuffle()
	{
		cardList.addAll(usedCardList);
		usedCardList.clear();
		
		int low = (int)(cardList.size()*MIN_RATE);
		int hi = (int)(cardList.size()*MAX_RATE);
		
		//随机数
		int randomNum = MathUtils.random(low, hi);
		for(int i=0;i<randomNum;i++){
			getNextCard();
		}
		
	}
	
	/**
	 * 卡牌列表
	 * @return
	 */
	public List<Card> getCardList(){
		return this.cardList;
	}
}
