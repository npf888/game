/**
 * GoldBonus
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class GoldBonusTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 1 gold 2 free */
	public int type;
		/** 2 2转盘 3 3转盘 */
	public int wheelType;
		/** 中奖类型，1、linebet，2、转盘2*倍数，3转盘2累加，4大转盘,5:免费转动 */
	public int rewardType;
		/** 中奖数量,0:代表大转盘 */
	public int rewardNum;
	
}