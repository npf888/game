package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.common.model.Card;
import com.common.model.Card.CardTypeEnum;
import com.common.model.Card.CardValueEnum;
import com.core.util.ArrayUtils;
import com.core.util.MathUtils;
import com.gameserver.texas.data.TexasHandCard;
import com.gameserver.texas.data.TexasHandCard.TexasHandCardEnum;

public class TexasHandCardTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		testHighCard(100000);
		testOnePair(100000);
		testTwoPair(100000);
		testThreeKind(100000);
		testFlush(100000);
		testFullHouse(100000);
		
	}
	
	private void testHighCard(int n)
	{
		for(int k=0;k<n;k++)
		{
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool, 7, false);
			
			for(int i=0;i<7;i++)
			{
				Card card = new Card(CardTypeEnum.valueOf(i%4),randomList.get(i));
				cardList.add(card);
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
			
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.HIGH_CARD && texasHandCard.getCardList().size() == 5) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT && texasHandCard.getCardList().size() == 1));
		}
	}
	
	private void testTwoPair(int n)
	{
		for(int k=0;k<n;k++)
		{
				
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			//int [] we = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0};
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool,5, false);
			
		
			
			for(int i=0;i<randomList.size();i++)
			{
				if(i==0 || i==1)
				{
					for(int j=0;j<2;j++)
					{
						Card card = new Card(CardTypeEnum.valueOf(j%4),randomList.get(i));
						cardList.add(card);
					}
				}
				else
				{
					Card card = new Card(CardTypeEnum.valueOf(i%4),randomList.get(i));
					cardList.add(card);
				}
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
//			System.out.println(cardList);
//			System.out.println(texasHandCard.getCardList());
//			
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.TWO_PAIR && texasHandCard.getCardList().size() == 3) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT && texasHandCard.getCardList().size() == 1));
		}
	}
	
	private void testOnePair(int n)
	{
		for(int k=0;k<n;k++)
		{
				
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			//int [] we = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0};
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool,6, false);
			
		
			
			for(int i=0;i<randomList.size();i++)
			{
				if(i==0)
				{
					for(int j=0;j<2;j++)
					{
						Card card = new Card(CardTypeEnum.valueOf(j%4),randomList.get(i));
						cardList.add(card);
					}
				}
				else
				{
					Card card = new Card(CardTypeEnum.valueOf(i%4),randomList.get(i));
					cardList.add(card);
				}
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
//			System.out.println(cardList);
//			System.out.println(texasHandCard.getCardList());
//				
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.ONE_PAIR && texasHandCard.getCardList().size() == 4) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT && texasHandCard.getCardList().size() == 1));
		}
	}
	
	private void testThreeKind(int n)
	{
		for(int k=0;k<n;k++)
		{
				
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			//int [] we = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0};
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool,5, false);
			
		
			
			for(int i=0;i<randomList.size();i++)
			{
				if(i==0)
				{
					for(int j=0;j<3;j++)
					{
						Card card = new Card(CardTypeEnum.valueOf(j%4),randomList.get(i));
						cardList.add(card);
					}
				}
				else
				{
					Card card = new Card(CardTypeEnum.valueOf(i%4),randomList.get(i));
					cardList.add(card);
				}
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
//			System.out.println(cardList);
//			System.out.println(texasHandCard.getCardList());
			
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.THREE_KIND && texasHandCard.getCardList().size() == 3) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT && texasHandCard.getCardList().size() == 1));
		}
	}
	
	private void testFlush(int n)
	{
		for(int k=0;k<n;k++)
		{
				
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			//int [] we = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0};
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool,7, false);
			
		
			
			for(int i=0;i<randomList.size();i++)
			{
				Card card = new Card(CardTypeEnum.valueOf(1),randomList.get(i));
				cardList.add(card);
				
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
//			System.out.println(cardList);
//			System.out.println(texasHandCard.getCardList());
			
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.FLUSH && texasHandCard.getCardList().size() == 1) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT_FLUSH && texasHandCard.getCardList().size() == 1));
		}
	}

	private void testFullHouse(int n)
	{
		for(int k=0;k<n;k++)
		{
				
			List<Card> cardList= new ArrayList<Card>();
			
			List<CardValueEnum> pool = new ArrayList<CardValueEnum>();
			for(CardValueEnum cardValueEnum: CardValueEnum.values())
			{
				if(cardValueEnum!=CardValueEnum.BLACK_JOKER && cardValueEnum!=CardValueEnum.RED_JOKER  )
					pool.add(cardValueEnum);
			}
			//int [] we = new int[]{1,1,1,1,1,0,0,0,0,0,0,0,0};
			List<CardValueEnum> randomList = ArrayUtils.randomFromArray(pool,4, false);
			
		
			
			for(int i=0;i<randomList.size();i++)
			{
				if(i==0)
				{
					for(int j=0;j<3;j++)
					{
						Card card = new Card(CardTypeEnum.valueOf(j%4),randomList.get(i));
						cardList.add(card);
					}
				}
				else if (i==1)
				{

					for(int j=0;j<2;j++)
					{
						Card card = new Card(CardTypeEnum.valueOf(j%4),randomList.get(i));
						cardList.add(card);
					}
				
				}
				else
				{
					Card card = new Card(CardTypeEnum.valueOf(i%4),randomList.get(i));
					cardList.add(card);
				}
			}
			Collections.sort(cardList);
			TexasHandCard texasHandCard = TexasHandCard.convertFrom(cardList);
//			System.out.println(cardList);
//			System.out.println(texasHandCard.getCardList());
			
			Assert.assertTrue((texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.FULL_HOUSE && texasHandCard.getCardList().size() == 2) || (texasHandCard.getTexasHandCardEnum() == TexasHandCardEnum.STRAIGHT_FLUSH ));
		}
	}

}
