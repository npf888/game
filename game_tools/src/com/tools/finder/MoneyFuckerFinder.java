package com.tools.finder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tools.finder.IFucker.MoneyFucker;

/**
 * 金钱作弊者查找器
 * 
 * @author Thinker
 * 
 */
public class MoneyFuckerFinder extends BaseFuckerFinder<MoneyFucker> 
{
	/** 查询金钱日志信息SQL */
	private String queryMoneyLogSql;

	public MoneyFuckerFinder(String logTableName)
	{
		super(logTableName);
		try
		{
			queryMoneyLogSql = "select main_delta from " + logTableName
					+ " where char_name = ? and createTime < " + TIME2
					+ " and createTime > " + TIME1
					+ " order by createTime desc limit 0, "
					+ FuckerConstants.LIMIT_COUNT;

		} catch (Exception e) 
		{

		}
	}

	@Override
	public List<MoneyFucker> getAllFucker()
	{
		List<MoneyFucker> fuckers = super.getAllFucker();
		List<MoneyFucker> sortFuckers = new ArrayList<MoneyFucker>();
		if (fuckers.size() > 0) 
		{
			for (int i = fuckers.size() - 1; i > fuckers.size() - 101; i--)
			{
				if (fuckers.get(i).getTotalIn() == 0)
				{
					continue;
				}
				sortFuckers.add(fuckers.get(i));
			}
		}

		return sortFuckers;
	}

	@Override
	public MoneyFucker areYouFucker(String charName, long uuid)
	{
		// FIXME: crazyjohn 这里就是重构案例的姊妹版啊,有时间重构
		List<MoneyRecord> moneyRecords = this.getRecords(charName);
		MoneyFucker fucker = new MoneyFucker();
		fucker.setName(charName);
		fucker.setUuid(uuid);
		int inCount = 0;
		int outCount = 0;
		int totalIn = 0;
		int totalOut = 0;
		for (MoneyRecord record : moneyRecords) {
			if (record.isInMoney()) {
				inCount++;
				totalIn += record.getActiveMoney();
			} else {
				outCount++;
				totalOut += -record.getActiveMoney();
			}
		}
		fucker.setInCount(inCount);
		fucker.setOutCount(outCount);
		fucker.setTotalIn(totalIn);
		fucker.setTotalOut(totalOut);
		return fucker;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MoneyRecord> getRecords(String charName) {
		List<MoneyRecord> records = new ArrayList<MoneyRecord>();
		ResultSet rs = null;
		try {
			PreparedStatement ps = logConnection
					.prepareStatement(queryMoneyLogSql);
			ps.setString(1, charName);
			rs = JDBCUtil.query(ps, queryMoneyLogSql);
			while (rs.next()) {
				MoneyRecord record = new MoneyRecord();
				record.setCharName(charName);
				record.setActiveMoney(rs.getInt("main_delta"));
				records.add(record);
			}
		} catch (SQLException e) {
			logger.error("GerRecords failed, charName: " + charName, e);
		}
		return records;
	}

}
