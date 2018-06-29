package com.tools.finder;

/**
 * 作弊者查找器工厂
 * 
 * @author Thinker
 * 
 */
public class FuckerFinderFactory 
{
	private static FuckerFinderFactory instance = new FuckerFinderFactory();

	public static FuckerFinderFactory getInstance()
	{
		return instance;
	}

	/**
	 * 创建一个军功作弊者查询器
	 * 
	 * @param logTableName
	 * @return
	 */
	public ExploitFuckerFinder createExploitFuckerFinder(String logTableName) 
	{
		return new ExploitFuckerFinder(logTableName);
	}
	
	/**
	 * 创建一个金钱作弊者查询器
	 * 
	 * @param logTableName
	 * @return
	 */
	public MoneyFuckerFinder createMoneyFuckerFinder(String logTableName)
	{
		return new MoneyFuckerFinder(logTableName);
	}
}
