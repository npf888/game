package com.tools.finder;

import java.util.List;

/**
 * 作弊者查询器接口
 * 
 * @author Thinker
 * 
 */
public interface IFuckerFinder<F extends IFucker> 
{
	/**
	 * 查找所有的作弊者
	 * 
	 * @return 不会返回Null;
	 */
	public List<F> getAllFucker();

	/**
	 * 判断某个用户是否是作弊者
	 * 
	 * @param charName
	 *            用户名
	 * @param UUID
	 *            用户ID
	 * @return 不会返回空,如果用户不是作弊者则返回NullFucker对象;如果是返回特定类型的作弊者
	 */
	public F areYouFucker(String charName, long UUID);

}
