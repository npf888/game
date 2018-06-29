using System.Collections;
public class PlayerCGMessage{
	
  
 		/**
		 * 客户端请求用户信息
		 * @param userId 用户id
		 */
	public static void CG_QUERY_PLAYER_INFO(long userId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_QUERY_PLAYER_INFO);
			msgBody.PutLong(userId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 客户端请求用户信息
		 * @param userName 用户名字
		 */
	public static void CG_QUERY_PLAYER_INFO_NAME(string userName) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_QUERY_PLAYER_INFO_NAME);
			msgBody.PutString(userName);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 用户校验登录
		 * @param userId 玩家的账户Id
		 * @param userCode 玩家的随机码 
		 * @param deviceId 设备码 
		 * @param deviceType 设备的类型
		 * @param deviceModel 设备机型
		 * @param osVersion 操作系统版本号
		 * @param channelType 渠道类型 
		 * @param clientVersion 客户端版本
		 * @param resourceVersion 客户端资源版本
		 * @param countries 国家
		 */
	public static void CG_CHECK_PLAYER_LOGIN(long userId,string userCode,string deviceId,string deviceType,string deviceModel,string osVersion,int channelType,string clientVersion,int resourceVersion,string countries) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CHECK_PLAYER_LOGIN);
			msgBody.PutLong(userId);
			msgBody.PutString(userCode);
			msgBody.PutString(deviceId);
			msgBody.PutString(deviceType);
			msgBody.PutString(deviceModel);
			msgBody.PutString(osVersion);
			msgBody.PutInt(channelType);
			msgBody.PutString(clientVersion);
			msgBody.PutInt(resourceVersion);
			msgBody.PutString(countries);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 加载角色
		 */
	public static void CG_PLAYER_ENTER() {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_PLAYER_ENTER);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家可以进入场景
		 * @param sceneId 场景Id
		 */
	public static void CG_ENTER_SCENE(int sceneId) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_ENTER_SCENE);
			msgBody.PutInt(sceneId);
			PlatformService.Send(msgBody);
		}
 
 		/**
		 * 玩家现在使用版本
		 * @param version 
		 */
	public static void CG_CLIENT_VERSION(string version) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_CLIENT_VERSION);
			msgBody.PutString(version);
			PlatformService.Send(msgBody);
		}
}