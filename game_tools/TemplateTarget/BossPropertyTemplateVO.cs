/**
 * BossPropertyTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BossPropertyTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** icon */
	public string icon;
		/** 多语言描述 */
	public string langDesc;
		/** 1.（airtime）秒内，所有wild连线伤害降低百分之（） 2.恢复自身最大血量的百分之（） 3.（airtime）秒内，收到伤害降低百分之（） 4.（airtime）秒内，有（）概率免疫伤害。 */
	public int type;
		/** 技能开始后持续时间（秒） */
	public int airtime;
		/** wild中奖连线伤害降低百分数。（除以100） */
	public int wildreduce;
		/** 恢复最大血量百分数（除以100） */
	public int recover1;
		/** 伤害减免百分数 */
	public int damagereduce;
		/** 免伤概率，百分数（除以100） */
	public int avoid;
		/** 基础血量 */
	public int blood;
		/** 击杀成功后：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	public int rewardNum1;
		/** 未击杀成功：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	public int rewardNum2;
	
}