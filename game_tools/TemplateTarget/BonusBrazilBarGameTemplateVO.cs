/**
 * BonusBrazilBarGameTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class BonusBrazilBarGameTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 老虎机类型 */
	public int slotsNum;
		/** 轮次 */
	public int stage;
		/** 奖励类型 */
	public int type;
		/** 是否减生命值 （是否有酒） 0表是否，1表示是 */
	public int alcohol;
		/** 单线下注额倍数,(百分比，实际值除以100） */
	public int reward;
		/** 权值 */
	public int weight;
	
}