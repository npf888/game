package com.robot;

import java.util.ArrayList;
import java.util.List;

import com.common.model.Card;
import com.common.model.Card.CardValueEnum;

public class TexasDeck {
	
	private Card.CardValueEnum minValue;
	private List<Card> cardList = new ArrayList<Card>(52);

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
	

	public Card.CardValueEnum getMinValue() {
		return minValue;
	}
	
	public Card getCardByValue(int cardValue){
		for(Card card : cardList){
			if(card.getValue() == cardValue)
				return card;
		}
		return null;
	}
	
	public List<Card> getCardList(){
		return this.cardList;
	}

}