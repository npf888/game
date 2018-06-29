/**
 * BounsHallowmasRewardTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BounsHallowmasRewardTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/** 老虎机类型 */
	public int slotsNum;
		/** 关卡 0为初始 */
	public int checkpoint;
		/** 每关出现的鬼魂个数 */
	public int numberGhosts;
		/** 每关出现的小孩个数 */
	public int numberChildren;
		/** 有效击打时间;秒 */
	public int validTime;
		/** 打中鬼的次数 */
	public int hitTheGhost;
		/** 保底奖金： 0;有 1;无 */
	public int guaranteed;
		/** 奖励 */
	public int reward;
	
}