/**
 * WeeklyMonthlyPackTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class WeeklyMonthlyPackTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 多语言描述 */
	public string langDesc;
		/** 礼包类型，0表示周礼包，1表示月礼包 */
	public int packType;
		/** VIP等级 */
	public int vipLevel;
		/** 礼包持续时间，单位：秒 */
	public int packDuration;
		/** 礼包刷新时间 */
	public int packCycle;
		/** Recharge表对应的pid */
	public int pid;
		/** 购买弹窗显示： 额外获得的百分比（如：配置200，显示200% MORE） */
	public int extraPercentage;
	
}