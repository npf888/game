package com.common.model;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public class Card implements  Comparable<Card>{
	
	/**
	 * 卡类型
	 * @author wayne
	 *
	 */
	public enum CardTypeEnum  implements IndexedEnum{
		SPADE(0),
		HEART(1),
		CLUB(2),
		DIAMOND(3),
		;
		
		private final int index;
		private static final List<CardTypeEnum> values = IndexedEnumUtil.toIndexes(CardTypeEnum.values());
		
		private CardTypeEnum(int index){
			this.index = index;
		}
		
		@Override
		public int getIndex() {
			// TODO Auto-generated method stub
			return index;
		}
		
		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static CardTypeEnum valueOf(final int index)
		{
			return EnumUtil.valueOf(values, index);
		}
		
	}
	
	public enum CardValueEnum  implements IndexedEnum{
		ACE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(11),
		QUEEN(12),
		KING(13),
		BLACK_JOKER(14),
		RED_JOKER(15),
		;
		
		private final int index;
		private static final List<CardValueEnum> values = IndexedEnumUtil.toIndexes(CardValueEnum.values());
		
		private CardValueEnum(int index){
			this.index = index;
		}
		
		@Override
		public int getIndex() {
			// TODO Auto-generated method stub
			return index;
		}
		
		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static CardValueEnum valueOf(final int index)
		{
			return EnumUtil.valueOf(values, index);
		}	
	}
	
	private CardTypeEnum cardType;
	private CardValueEnum cardValue;
	
	public Card(CardTypeEnum cardType,CardValueEnum cardValue){
		this.cardType = cardType;
		this.cardValue = cardValue;
	}
	
	public Card(int aValue)
	{
		int typeMask = 3;
		int valueMask = ~typeMask;
		this.cardType = CardTypeEnum.valueOf(aValue & typeMask);
		
		this.cardValue = CardValueEnum.valueOf((aValue & valueMask) >>2);
	}
	
	public CardValueEnum getCardValue()
	{
		return cardValue;
	}
	
	public CardTypeEnum getCardType()
	{
		return cardType;
	}
	public int  getValue()
	{
		return this.cardType.getIndex() | this.cardValue.getIndex()<<2;
	}
	
	@Override
	public String  toString()
	{
		StringBuilder sb = new StringBuilder();
		switch(cardType)
		{
		case SPADE:
			sb.append("黑桃 ");
			break;
		case HEART:
			sb.append("红心 ");
			break;
		case CLUB:
			sb.append("梅花 ");
			break;
		case DIAMOND:
			sb.append("方块");
			break;
		}
		switch(cardValue)
		{
		case ACE:
			sb.append("A");
			break;
		case JACK:
			sb.append("J");
			break;
		case QUEEN:
			sb.append("Q");
			break;
		case KING:
			sb.append("K");
			break;
		default:
			sb.append(String.valueOf(cardValue.getIndex()));
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Card aCard) {
		// TODO Auto-generated method stub
		if(this.getValue()>aCard.getValue())
			return 1;
		if(this.getValue()<aCard.getValue())
			return -1;
		return 0;
	}
	
	/**
	 * 比较卡大小
	 * @param aCard
	 * @return
	 */
	public int actualCompareTo(Card aCard)
	{
		if(this.getCardValue() == CardValueEnum.ACE && aCard.getCardValue()!=CardValueEnum.ACE )
			return 1;
		else if(this.getCardValue() != CardValueEnum.ACE && aCard.getCardValue()==CardValueEnum.ACE )
			return -1;
		int result =this.getCardValue().compareTo(aCard.getCardValue());
		if(result>0)
			result = 1;
		else if(result<0)
			result = -1;
		return result;
	}
	
	public static boolean isAdjoin(Card aCard,Card bCard)
	{
		if((aCard.getCardValue()== CardValueEnum.ACE && bCard.getCardValue() == CardValueEnum.KING)||(aCard.getCardValue()== CardValueEnum.KING && bCard.getCardValue() == CardValueEnum.ACE))
			return true;
		if(Math.abs(aCard.getCardValue().getIndex() - bCard.getCardValue().getIndex()) ==1)
			return true;
		return false;
	}
	
	public static int[] cardArray(List<Card> aCardList){
		int[] tempArr = new int[aCardList.size()];
		for(int i=0;i<aCardList.size();i++){
			tempArr[i] = aCardList.get(i).getValue();
		}
		return tempArr;
	}
}
