using System.Collections;
public class BazoonewguyCGMessage{
	
  
 		/**
		 * 无双吹牛新手打点
		 * @param modeType 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4：单挑模式
		 * @param process 新手进程：到第1步传1，到第二步传2，3...依次类推
		 */
	public static void CG_BAZOO_NEW_GUY_PROCESS(int modeType,int process) {
			OutputMessage msgBody= new OutputMessage(NetMessageType.CG_BAZOO_NEW_GUY_PROCESS);
			msgBody.PutInt(modeType);
			msgBody.PutInt(process);
			PlatformService.Send(msgBody);
		}
}