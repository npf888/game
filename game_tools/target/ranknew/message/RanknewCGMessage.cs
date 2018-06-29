using System.Collections;
public class RanknewCGMessage{
	
  
 		/**
		 * 排行
		 * @param start 开始位置（包括）
		 * @param end 结束位置（包括）
		 */
	public static void CG_REQUEST_RANK(int start,int end) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_REQUEST_RANK);
			msgBody.PutInt(start);
			msgBody.PutInt(end);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 请求自己的排名
		 */
	public static void CG_HUMAN_RANK() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_HUMAN_RANK);
			PlatformService.Send(msgBody);
		}
}