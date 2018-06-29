package com.gameserver.texas;

import java.util.ArrayList;
import java.util.List;

import com.common.model.Card;
import com.common.model.Card.CardValueEnum;
import com.core.util.MathUtils;


/**
 * 德州扑克
 * @author wayne
 *
 */
public class TexasDeck {
	
	private Card.CardValueEnum minValue;
	private List<Card> cardList = new ArrayList<Card>(52);
	private List<Card> usedCardList = new ArrayList<Card>(52);
	
	public TexasDeck(Card.CardValueEnum minValue){
		this.minValue = minValue;
		
		for(Card.CardValueEnum tempValue : Card.CardValueEnum.values()){
			if(tempValue == CardValueEnum.BLACK_JOKER || tempValue == CardValueEnum.RED_JOKER)
				continue;
			if(tempValue!=CardValueEnum.ACE && tempValue.getIndex()<this.minValue.getIndex())
				continue;

			for(Card.CardTypeEnum tempType : Card.CardTypeEnum.values())
			{
				cardList.add(new Card(tempType,tempValue));
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
	 * 洗牌
	 */
	public void shuffle()
	{
		cardList.addAll(usedCardList);
		usedCardList.clear();
	}



	public Card.CardValueEnum getMinValue() {
		return minValue;
	}



}
