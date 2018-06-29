/**
 * TournamentRankListTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class TournamentRankListTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 多语言描述id */
	public int descrip;
		/**  */
	public int tournamentId;
		/** 多语言描述 */
	public string langDesc;
		/** rank1 到 rank2 是个范围 在这个范围内的人拿这个奖励 */
	public int rank1;
		/** rank1 到 rank2 是个范围 */
	public int rank2;
		/** 奖励比例（万分比） */
	public int reward;
	
}