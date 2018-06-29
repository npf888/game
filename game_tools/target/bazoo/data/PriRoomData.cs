using System.Collections;

public class PriRoomData
{
	/** 房间号 */
	public string roomNumber;
	/** 创建者 */
	public string creater;
	/** 创建者的ID */
	public long createrPassportId;
	/** 性别 0：女，1：男 */
	public int flag;
	/** vip等级 */
	public int vip;
	/** 房间的模式  1：经典模式，2：牛牛模式，3：梭哈模式 */
	public int modeType;
	/** 底注 */
	public int bet;
	/** 头像 */
	public string img;
	/** 房间当前人数/房间总人数 */
	public string numTotalNum;
	/** 是否需要密码：0需要， 1不需要 */
	public int isNeedPassword;
}