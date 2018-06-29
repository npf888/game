package com.tools.finder;
/**
 * 用户金钱记录
 * 
 * @author Thinker
 *
 */
public class MoneyRecord implements IFuckerRecord
{
	/** 金钱活动记录 */
	private int activeMoney;
	private String charName;

	public int getActiveMoney() 
	{
		return activeMoney;
	}

	public void setActiveMoney(int activeMoney) 
	{
		this.activeMoney = activeMoney;
	}
	
	/**
	 * 是否是充入的钱
	 * 
	 * @return	true是; false 否;
	 */
	public boolean isInMoney()
	{
		return activeMoney >= 0;
	}

	@Override
	public String getCharName() 
	{
		return charName;
	}

	public void setCharName(String charName) 
	{
		this.charName = charName;
	}
}
