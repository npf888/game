using System.Collections;
public class RankCGMessage{
	
  
 		/**
		 * 排行
		 * @param page 页面
		 * @param rankType 排行版类型
		 */
	public static void CG_COMMON_RANK(int page,int rankType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_COMMON_RANK);
			msgBody.PutInt(page);
			msgBody.PutInt(rankType);
			PlatformService.Send(msgBody);
		}
}