package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanRechargeOrderEntity;

/**
 * 充值订单
 * @author wayne
 *
 */
public class HumanRechargeOrderDao extends BaseDao<HumanRechargeOrderEntity>{

	private static final String GET_RECHARGE_ORDER_BY_CHARID = "queryRechargeOrderByCharid";
	
	private static final String ByOrders = "queryRechargeOrderByOrders";
	
	private static final String StartAndEnd = "queryRechargeOrderStartAndEnd";
	
	private static final String queryMyCardTradeNo = "queryRechargeMyCardTradeNo";
	
	private static final String[] GET_RECHARGE_ORDER_BY_CHARID_PARAMS = new String[] { "charId" };
	
	private static final String[] ByOrders_key = new String[] { "id" };
	
	private static final String[] StartAndEnd_key = new String[] { "startTime", "endTime"};
	
	private static final String[] MyCardTradeNo = new String[] { "myCardTradeNo"};
	
	public HumanRechargeOrderDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */
	
	public List<HumanRechargeOrderEntity> getRechargeOrderListByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_RECHARGE_ORDER_BY_CHARID, GET_RECHARGE_ORDER_BY_CHARID_PARAMS,
				new Object[] { charId });
	
	}
	
	/**
	 * 根据订单号查询
	 * @param orders
	 * @return
	 */
	public HumanRechargeOrderEntity getRechargeOrders(long id){
		List<HumanRechargeOrderEntity> list = this._dbServ.findByNamedQueryAndNamedParam(ByOrders, ByOrders_key,
				new Object[] {id});
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据订单号查询
	 * @param myCardTradeNo
	 * @return
	 */
	public List<HumanRechargeOrderEntity> getMyCardTradeNo(String myCardTradeNo){
		List<HumanRechargeOrderEntity> list = this._dbServ.findByNamedQueryAndNamedParam(queryMyCardTradeNo, MyCardTradeNo,
				new Object[] {myCardTradeNo});
		return list;
	}
	
	
	
	
	/**
	 * 根据开始结束时间
	 * @param start
	 * @param end
	 * @return
	 */
	public List<HumanRechargeOrderEntity> getStartAndEnd(long startTime,long endTime){
		return this._dbServ.findByNamedQueryAndNamedParam(StartAndEnd, StartAndEnd_key,
				new Object[] {startTime,endTime});
	}
	
	/**
	 * 按充值次数过滤用户
	 * @param rechargeTimes
	 * @return
	 */
	public List<HumanRechargeOrderEntity> selectPlayerByChargeTimes(int rechargeTimes){
		return this._dbServ.findByNamedQueryAndNamedParam("selectPlayerByRechargeTimes", new String[]{"times"},
				new Object[] {rechargeTimes});
	}

	/**
	 * 按提交订单未付费过滤用户
	 * @param rechargeTimes
	 * @return
	 */
	public List<HumanRechargeOrderEntity> selectPlayerByOrderNotChargeTimes(int rechargeTimes){
		return this._dbServ.findByNamedQueryAndNamedParam("selectPlayerByOrderNotChargeTimes", new String[]{"times"},
				new Object[] {rechargeTimes});
	}
	
	@Override
	protected Class<HumanRechargeOrderEntity> getEntityClazz()
	{
		return HumanRechargeOrderEntity.class;
	}

}
