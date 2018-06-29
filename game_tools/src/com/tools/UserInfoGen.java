package com.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;

/**
 * 批量创建测试账号 SQL 文件, 文件格式大致如下: 
 * 
 * @author Thinker
 * 
 */
public class UserInfoGen
{
	/** SQL 文件 */
	private static final String SQL_FILE = "/D:/t_user_info.sql";
	/** 事先截断数据表 */
	private static final boolean TRUNCATE_TABLE = true;
	/** 起始索引, 也用作用户名 */
	private static final int START_INDEX = 1001;
	/** 结束索引 */
	private static final int END_INDEX = 20480;

// SQL 语句
/////////////////////////////////////////////////////////////////////////////

	/** SQL : TRUNCATE TABLE */
	private static final String SQL_TRUNCATE_TABLE = "truncate table `t_user_info`;";
	/** SQL : INSERT INTO */
	private static final String SQL_INSERT_INTO = "insert into `t_user_info` ( `id`, `name`, `password`, `role`, `failedLogins`, `lockStatus`, `muteTime` ) values ";
	/** SQL : VALUES */
	private static final String SQL_VALUES = "( {0,Number,#}, ''{0,Number,#}'', ''1'', 2, 0, 0, 0 )";


	public static void main(String[] args)
	{
		// 创建应用程序并执行生成操作
		(new UserInfoGen()).gen();
	}

	/**
	 * 生成账号 SQL 文件
	 * 
	 */
	public void gen() 
	{
		try
		{
			// 创建文件写出者
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(SQL_FILE))));

			// 设置编码
			bw.write("set names 'utf8';");
			bw.write("\r\n");
			bw.write("\r\n");

			if (TRUNCATE_TABLE)
			{
				// 事先截断数据表
				bw.write(SQL_TRUNCATE_TABLE);
				bw.write("\r\n");
				bw.write("\r\n");
			}

			// 添加插入语句, 
			// 即生成 insert into ... values 语句
			bw.write(SQL_INSERT_INTO);
			bw.write("\r\n");

			// 添加具体的 values 值
			for (int i = START_INDEX; i <= END_INDEX; i++)
			{
				// 写出插入值
				bw.write(MessageFormat.format(SQL_VALUES, i));
	
				if (i == END_INDEX)
				{
					// 以 ; 结尾
					bw.write("\r\n;");
				} else
				{
					// 以 , 结尾
					bw.write(", \r\n");
				}
			}

			// 写出文件并关闭
			bw.flush();
			bw.close();

			// 输出完成消息
			System.out.println("## Gen OK!");
			System.out.println(SQL_FILE);
		} catch (Exception ex)
		{
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}
}
