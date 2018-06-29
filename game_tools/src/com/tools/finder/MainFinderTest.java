package com.tools.finder;

import java.util.List;

import com.tools.finder.IFucker.ExploitFucker;
import com.tools.finder.IFucker.MoneyFucker;

/**
 * 作弊者查找测试类
 * 
 * @author Thinker
 * 
 */
public class MainFinderTest
{

	public static void main(String[] args)
	{
		// ============ 根据军功进行查找 ==========
		testExploitFinder();

		// ============ 根据金钱进行查找 ==========
		testMoneyFinder();
	}

	private static void testMoneyFinder()
	{
		MoneyFuckerFinder moneyFinder = FuckerFinderFactory.getInstance().createMoneyFuckerFinder("money_log_2011_11_11");
		// 根据金钱进行判断
		List<MoneyFucker> moneyFuckers = moneyFinder.getAllFucker();
		System.out.print(moneyFuckers.toString());
	}

	private static void testExploitFinder()
	{
		ExploitFuckerFinder exploitFinder = FuckerFinderFactory.getInstance().createExploitFuckerFinder("exploit_log_2011_11_14");
		// 根据军功进行判断
		List<ExploitFucker> fuckers = exploitFinder.getAllFucker();
		System.out.print(fuckers.toString());
	}
}
