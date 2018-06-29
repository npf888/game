package com.tools.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.util.TimeUtils;

/**
 * 基础的作弊者查找器
 * 
 * @author Thinker
 */
public abstract class BaseFuckerFinder<F extends IFucker> implements IFuckerFinder<F> 
{
	/** 日期字符的长度 */
	private static final int DATE_LEN = 10;
	protected static Logger logger = LoggerFactory.getLogger("FuckerFinder");
	/** 到日志数据库连接 */
	protected Connection logConnection;
	/** 到游戏数据库连接 */
	protected Connection gameConnection;
	/** 查询角色ID信息SQL */
	protected String queryCharIdSql = "select id,name from t_character_info";
	/** 作弊者名单生成器 */
	protected static FuckerListGenerator fuckerListGenerator = new FuckerListGenerator();
	protected long TIME1;
	protected long TIME2;
	protected String logTableName;

	public BaseFuckerFinder(String logTableName) 
	{
		// 构建数据库连接
		try 
		{
			this.logTableName = logTableName;
			TIME1 = TimeUtils.getYMDTime(logTableName.substring(
					logTableName.length() - DATE_LEN, logTableName.length()).replace('_', '-'));
			TIME2 = TIME1 + FuckerConstants.DAY_OF_MS;
			logger.info("MaxTime is: " + TIME2 + ", minTime is: " + TIME1);
			logConnection = JDBCUtil.getConnection(FuckerConstants.LOG_URL,
					FuckerConstants.LOG_USER, FuckerConstants.LOG_PASSWORD);
			logger.info("Success connect to log db");
			gameConnection = JDBCUtil.getConnection(FuckerConstants.GAME_URL,
					FuckerConstants.GAME_USER, FuckerConstants.GAME_PASSWORD);
			logger.info("Success connect to game db");
		} catch (Exception e) 
		{
			throw new RuntimeException("Get connection error", e);
		}

	}


	/**
	 * 获取所有的用户信息
	 * 
	 * @return 返回用户信息集
	 */
	protected Set<Entry<Long, String>> getAllCharNameAndId()
	{
		Map<Long, String> infos = new HashMap<Long, String>();
		ResultSet rs = null;
		PreparedStatement ps;
		try 
		{
			ps = gameConnection.prepareStatement(queryCharIdSql);
			rs = JDBCUtil.query(ps, queryCharIdSql);
			while (rs.next()) 
			{
				infos.put(rs.getLong("id"), rs.getString("name"));
			}
		} catch (SQLException e)
		{
			logger.error("GetCharIds failed", e);
		}
		logger.info("Total character count: " + infos.size());
		return infos.entrySet();
	}

	/**
	 * 获取所有的作弊人员
	 * 
	 * @return
	 */
	@Override
	public List<F> getAllFucker()
	{
		List<F> fuckers = new ArrayList<F>();
		try
		{
			Set<Map.Entry<Long, String>> infos = getAllCharNameAndId();
			logger.info("Find fucker start...");
			for (Entry<Long, String> info : infos)
			{
				F fucker = this.areYouFucker(info.getValue(), info.getKey());
				if (fucker != null)
				{
					fuckers.add(fucker);
				}
			}
			// 进行排序
			doFuckerSort(fuckers);
			logger.info("Find fucker stop, total fucker count: "
					+ fuckers.size());
		} finally 
		{
			// 关闭资源
			try
			{
				if (gameConnection != null) 
				{
					gameConnection.close();
					gameConnection = null;
				}
				if (logConnection != null)
				{
					logConnection.close();
					logConnection = null;
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}

		}
		return fuckers;
	}

	protected void doFuckerSort(List<F> fuckers)
	{
		Collections.sort(fuckers, new Comparator<F>() 
		{

			@Override
			public int compare(F fucker1, F fucker2)
			{
				if (fucker1.getFuckCount() > fucker2.getFuckCount()) 
				{
					return 1;
				} else if (fucker1.getFuckCount() < fucker2.getFuckCount()) 
				{
					return -1;
				}
				return 0;
			}

		});
	}
	
	/**
	 * 根据用户名获取指定的做笔记录
	 * 
	 * @param charName	用户名
	 * @return	不会返回空; 返回做笔记录列表;
	 */
	protected abstract <R extends IFuckerRecord>List<R> getRecords(String charName);

}
