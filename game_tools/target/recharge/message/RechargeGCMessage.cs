using System.Collections;

public class RechargeGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_REQUEST_ORDER,GC_REQUEST_ORDER);
		register(NetMessageType.GC_MYCARD_AUTHCODE,GC_MYCARD_AUTHCODE);
		register(NetMessageType.GC_MOL_ORDER,GC_MOL_ORDER);
		register(NetMessageType.GC_ORDER_AMAZON_DELIVERY,GC_ORDER_AMAZON_DELIVERY);
		register(NetMessageType.GC_VALIDATE_ORDER,GC_VALIDATE_ORDER);
		register(NetMessageType.GC_ORDER_INFO_DATA_LIST,GC_ORDER_INFO_DATA_LIST);
		register(NetMessageType.GC_DOUBLE_TURN,GC_DOUBLE_TURN);
		register(NetMessageType.GC_OBTAIN_COUPON,GC_OBTAIN_COUPON);
		register(NetMessageType.GC_COUPON_EXIST,GC_COUPON_EXIST);
		register(NetMessageType.GC_REQUEST_ORDER_THIRD_PARTY,GC_REQUEST_ORDER_THIRD_PARTY);
	}
 
  	/**
	 * 请求订单
	 * @param order 订单信息
	 */
	public void GC_REQUEST_ORDER(InputMessage data) 
	{
		HumanRechargeOrderInfoData order = new HumanRechargeOrderInfoData();
		order.orderId = data.GetLong();//vip领取时间
		order.productId = data.GetInt();//产品id
		order.orderStatus = data.GetInt();//下单状态
		RechargeHandler.Instance().GC_REQUEST_ORDER(order);
	}
 
  	/**
	 * 获取mycard交易码
	 * @param returnCode 返回结果 1 表示成功 0 表示失败
	 * @param authCode 交易码
	 */
	public void GC_MYCARD_AUTHCODE(InputMessage data) 
	{
		string returnCode = data.GetString();		
		string authCode = data.GetString();		
		RechargeHandler.Instance().GC_MYCARD_AUTHCODE(returnCode,authCode);
	}
 
  	/**
	 * 待验证发货订单
	 * @param molValidationOrder 待验证订单链表 
	 */
	public void GC_MOL_ORDER(InputMessage data) 
	{
		int i,size;
		ArrayList molValidationOrder = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			MolValidationOrder molValidationOrder_Datas= new MolValidationOrder();
			molValidationOrder_Datas.referenceId = data.GetString();//玩家订单
			molValidationOrder_Datas.paymentId = data.GetString();//MOL订单
			molValidationOrder.Add(molValidationOrder_Datas);
		}
		RechargeHandler.Instance().GC_MOL_ORDER(molValidationOrder);
	}
 
  	/**
	 * 验证订单亚马逊订单返回
	 * @param receiptId 成功的收据ID
	 */
	public void GC_ORDER_AMAZON_DELIVERY(InputMessage data) 
	{
		string receiptId = data.GetString();		
		RechargeHandler.Instance().GC_ORDER_AMAZON_DELIVERY(receiptId);
	}
 
  	/**
	 * 验证订单
	 * @param result 结果
	 * @param orderId 订单号
	 * @param pId 产品
	 * @param randRewardList 道具奖励
	 */
	public void GC_VALIDATE_ORDER(InputMessage data) 
	{
		int i,size;
		int result = data.GetInt();		
		long orderId = data.GetLong();
		long pId = data.GetLong();
		ArrayList randRewardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RandRewardData randRewardList_Datas= new RandRewardData();
			randRewardList_Datas.rewardId = data.GetInt();//奖励id
			randRewardList_Datas.rewardCount = data.GetInt();//奖励数量
			randRewardList.Add(randRewardList_Datas);
		}
		RechargeHandler.Instance().GC_VALIDATE_ORDER(result,orderId,pId,randRewardList);
	}
 
  	/**
	 * 未处理订单列表
	 * @param orderList 订单信息
	 */
	public void GC_ORDER_INFO_DATA_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList orderList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanRechargeOrderInfoData orderList_Datas= new HumanRechargeOrderInfoData();
			orderList_Datas.orderId =data.GetLong();
			orderList_Datas.productId = data.GetInt();//产品id
			orderList_Datas.orderStatus = data.GetInt();//下单状态
			orderList.Add(orderList_Datas);
		}
		RechargeHandler.Instance().GC_ORDER_INFO_DATA_LIST(orderList);
	}
 
  	/**
	 * 充值返回
	 * @param multipleId 表ID
	 * @param doubleMoney 需要翻倍的钱
	 */
	public void GC_DOUBLE_TURN(InputMessage data) 
	{
		int multipleId = data.GetInt();		
		long doubleMoney = data.GetLong();
		RechargeHandler.Instance().GC_DOUBLE_TURN(multipleId,doubleMoney);
	}
 
  	/**
	 * 获得优惠券
	 * @param couponId 优惠券 模板的ID
	 */
	public void GC_OBTAIN_COUPON(InputMessage data) 
	{
		int couponId = data.GetInt();		
		RechargeHandler.Instance().GC_OBTAIN_COUPON(couponId);
	}
 
  	/**
	 * 优惠券是否存在
	 * @param couponExist 是否存在 1 是 有优惠券    0 是没有优惠券
	 */
	public void GC_COUPON_EXIST(InputMessage data) 
	{
		int couponExist = data.GetInt();		
		RechargeHandler.Instance().GC_COUPON_EXIST(couponExist);
	}
 
  	/**
	 * 博趣 第三方请求订单的返回页面（前端直接访问）
	 * @param htmlPage base64编码之后的带参数的页面
	 */
	public void GC_REQUEST_ORDER_THIRD_PARTY(InputMessage data) 
	{
		string htmlPage = data.GetString();		
		RechargeHandler.Instance().GC_REQUEST_ORDER_THIRD_PARTY(htmlPage);
	}
}