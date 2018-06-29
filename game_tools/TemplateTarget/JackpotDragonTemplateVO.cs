/**
 * JackpotDragonTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class JackpotDragonTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 多语言描述 */
	public string langDesc;
		/** 老虎机ID */
	public int slotIdNum;
		/** 中jackpot数量 */
	public int jackpotNum;
		/** 奖池初始值（单先下注额倍数，选取的数除以100） */
	public int initial;
		/** 每次转动奖池增加数（单线下注额的倍数，取值除以10000） */
	public int increase;
		/** 单次中奖金额（当前该奖金池的倍数，取值除以100） */
	public int times;
	
}