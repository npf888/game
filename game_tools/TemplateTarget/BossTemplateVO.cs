/**
 * BossTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BossTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 每天boss 开始的时间 */
	public string starttime;
		/** 持续时间 分钟 */
	public int continuetime;
		/** 击杀增长血量，百分比（除以100） */
	public int increase;
		/** 未击杀减少量，百分比（除以100） */
	public int reduce;
		/** 技能触发时间（秒） */
	public int skillstart;
		/** 伤害加成百分数 */
	public int vip0;
		/** 伤害加成百分数 */
	public int vip1;
		/** 伤害加成百分数 */
	public int vip2;
		/** 伤害加成百分数 */
	public int vip3;
		/** 伤害加成百分数 */
	public int vip4;
		/** 伤害加成，百分数 */
	public int vip5;
		/** big中大奖伤害加成 */
	public int bigwin;
		/** mega中大奖伤害加成 */
	public int megawin;
		/** super中大奖伤害加成 */
	public int superwin;
		/** epic中大奖伤害加成 */
	public int epicwin;
		/** 开关 0.开启 1.关闭 */
	public int onoff;
		/** 最后一击奖励物品ID */
	public int rewardlastID;
		/** 最后一击奖励物品数量 */
	public int rewardNum;
	
}