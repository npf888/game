/**
 * BounsSphinxRewardTemplate
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BounsSphinxRewardTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 老虎机类型 */
	public int slotsNum;
		/** 1.奖池1 , 2.奖池2  , 。。。 , 5.奖池5 */
	public int rewardPool;
		/** 1：单线下注额倍数；   ,  2：没中奖   , 3：所有中奖和 */
	public int type;
		/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	public int times;
		/** 权值 */
	public int weight;
	
}