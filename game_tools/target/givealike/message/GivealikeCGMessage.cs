using System.Collections;
public class GivealikeCGMessage{
	
  
 		/**
		 * 保存用户点评
		 * @param slotType 老虎机类型
		 * @param paintAssess 美术点评
		 * @param playAssess 玩法点评
		 * @param totalAssess 综合点评
		 */
	public static void CG_GIVEALIKE_SAVE(int slotType,int paintAssess,int playAssess,int totalAssess) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_GIVEALIKE_SAVE);
			msgBody.PutInt(slotType);
			msgBody.PutInt(paintAssess);
			msgBody.PutInt(playAssess);
			msgBody.PutInt(totalAssess);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 判断老虎机 是否 是 评论过
		 * @param slotType 老虎机类型
		 */
	public static void CG_ISNOT_GIVEALIKE(int slotType) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ISNOT_GIVEALIKE);
			msgBody.PutInt(slotType);
			PlatformService.Send(msgBody);
		}
}