using System.Collections;
public class BaccartCGMessage{
	
  
 		/**
		 * 百家乐列表
		 */
	public static void CG_BACCART_LIST() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_LIST);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐下注
		 * @param betDataList 下注列表
		 */
	public static void CG_BACCART_BET(ArrayList betDataList) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_BET);
			int i;
			msgBody.PutShort((short)betDataList.Count);
			for(i=0; i<betDataList.Count; i++){
	msgBody.PutInt((betDataList[i] as BaccartBetData).betType);
	msgBody.PutLong((betDataList[i] as BaccartBetData).betNum);
			}
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家加入
		 * @param roomId 房间id
		 */
	public static void CG_BACCART_JOIN(int roomId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_JOIN);
			msgBody.PutInt(roomId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家站起
		 */
	public static void CG_BACCART_STAND() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_STAND);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家坐下
		 * @param pos 坐下位置
		 */
	public static void CG_BACCART_SEAT(int pos) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_SEAT);
			msgBody.PutInt(pos);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家退出
		 */
	public static void CG_BACCART_EXIT() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_EXIT);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐玩家信息
		 * @param playerId 玩家id
		 */
	public static void CG_HUMAN_BACCART(long playerId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_HUMAN_BACCART);
			msgBody.PutLong(playerId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐自动补充
		 * @param auto 自动补充
		 */
	public static void CG_BACCART_AUTO(int auto) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_AUTO);
			msgBody.PutInt(auto);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐补充筹码
		 * @param complement 补充数
		 */
	public static void CG_BACCART_COMPLEMENT(long complement) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_COMPLEMENT);
			msgBody.PutLong(complement);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐复活
		 * @param revive 复活
		 */
	public static void CG_BACCART_REVIVE(int revive) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCART_REVIVE);
			msgBody.PutInt(revive);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 百家乐快速开始
		 */
	public static void CG_BACCARAT_QUICK_START() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BACCARAT_QUICK_START);
			PlatformService.Send(msgBody);
		}
}