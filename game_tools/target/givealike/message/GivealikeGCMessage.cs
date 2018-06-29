using System.Collections;

public class GivealikeGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_GET_GIVEALIKE_INFO,GC_GET_GIVEALIKE_INFO);
		register(NetMessageType.GC_ISNOT_GIVEALIKE,GC_ISNOT_GIVEALIKE);
	}
 
  	/**
	 * 返回点评信息
	 * @param paintAssess 美术点评
	 * @param playAssess 玩法点评
	 * @param totalAssess 综合点评
	 */
	public void GC_GET_GIVEALIKE_INFO(InputMessage data) 
	{
		int paintAssess = data.GetInt();		
		int playAssess = data.GetInt();		
		int totalAssess = data.GetInt();		
		GivealikeHandler.Instance().GC_GET_GIVEALIKE_INFO(paintAssess,playAssess,totalAssess);
	}
 
  	/**
	 * 是否评论过
	 * @param isNot 0：否，1：是
	 */
	public void GC_ISNOT_GIVEALIKE(InputMessage data) 
	{
		int isNot = data.GetInt();		
		GivealikeHandler.Instance().GC_ISNOT_GIVEALIKE(isNot);
	}
}