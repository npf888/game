/**
 * LiarsDiceRoomTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class LiarsDiceRoomTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4:红黑单双模式，5：单机模式 */
	public int modeType;
		/** 多语言描述 */
	public string langDesc;
		/** icon */
	public string icon;
		/** 是否开放：1开放，0不开放 */
	public int openUp;
		/** 底注 */
	public int smallBet;
		/** 最小买入 */
	public int minCarry;
		/** 抽水:获胜者赢取的筹码的比例（万分比） */
	public int extractValue;
		/** 房间人数 */
	public int roomNum;
		/** 重摇 开关 */
	public int onOff;
		/** 重摇花费初始值 */
	public int reroll;
		/** 重摇花费初始值 */
	public int rerollNum;
	
}