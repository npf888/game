/**
 * LiarsDiceRoomConfigTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class LiarsDiceRoomConfigTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 玩家等待开局时间（单位秒） */
	public int waitingTime;
		/** 轮到谁开始摇的时间（单位秒） */
	public int whoTurnTime;
		/** 行动时间 */
	public int actTime;
		/** 下局开始时间 */
	public int nextTime;
		/** 发牌时间 */
	public int preflopTime;
		/** 竞猜时间，单位秒 */
	public int guessTime;
		/** 最后结算比牌时间 */
	public int compareTime;
		/** 振动时间，单位毫秒 */
	public int vibrationTime;
		/** 抢开之后，竞猜之前 有动画要做，所以要等待 一段时间 */
	public int guessBeforeTime;
		/** 牛牛模式 统一摇完色子之后 到 重摇色子之前 */
	public int cowSwingToBegin;
		/** 牛牛模式 一局 的时间，也是重摇的时间 */
	public int cowOneRoundTime;
		/** 牛牛模式 一局 用户 重摇结束到 结算之前 会有段时间 去看别人的色子 */
	public int cowLookDiceValueTime;
		/** 牛牛模式 结算时间 */
	public int cowEndCountTime;
		/** 梭哈模式 该轮到谁摇 色子 */
	public int showHandWhoTurn;
		/** 梭哈模式 摇色子的时间 */
	public int showHandShakeTime;
		/** 梭哈模式 结束到开始 */
	public int showHandEndToStart;
		/** 红黑单双模式 开始等待时间 */
	public int blackWhiteBeginWaitTime;
		/** 红黑单双模式 摇剩余色子等待时间 */
	public int blackWhiteLeftWaitTime;
		/** 红黑单双模式 重摇玩 到 该谁叫号 了 的等待时间 */
	public int blackWhiteWhoTurnTime;
		/** 红黑单双模式  等待用户叫号的时间 */
	public int blackWhiteNextTime;
		/** 红黑单双模式 结束到开始 */
	public int blackWhiteEndWaitTime;
	
}