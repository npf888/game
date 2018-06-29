/**
 * ClassicalPropertyTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class ClassicalPropertyTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 1:自己没有顺子且没有叫1点  2:有顺子没叫1点 或者 有叫1点没有顺子   3：自己有顺子且叫了1点 */
	public int callType;
		/** 玩游戏的人数 */
	public int peopleNum;
		/** 随便叫 哪个点的数量 */
	public int pointNum;
		/** 概率 */
	public int probability;
	
}