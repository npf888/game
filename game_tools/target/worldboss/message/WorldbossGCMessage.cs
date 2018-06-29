using System.Collections;

public class WorldbossGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_GET_BOSS_INFO,GC_GET_BOSS_INFO);
		register(NetMessageType.GC_GET_RANK_INFO,GC_GET_RANK_INFO);
		register(NetMessageType.GC_REFRESH_BOSS_INFO,GC_REFRESH_BOSS_INFO);
		register(NetMessageType.GC_BOSS_START_END_INFO,GC_BOSS_START_END_INFO);
		register(NetMessageType.GC_SELF_ATTACK_BLOOD_INFO,GC_SELF_ATTACK_BLOOD_INFO);
		register(NetMessageType.GC_RETURN_BLOOD,GC_RETURN_BLOOD);
		register(NetMessageType.GC_BEFORE_START,GC_BEFORE_START);
	}
 
  	/**
	 * 返回两个boss信息
	 * @param bossInfoData boss的信息
	 * @param lastWinHumanData 上次boss Winner
	 */
	public void GC_GET_BOSS_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList bossInfoData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BossInfoData bossInfoData_Datas= new BossInfoData();
			bossInfoData_Datas.nameId = data.GetInt();//多语言ID
			bossInfoData_Datas.descrip = data.GetInt();//描述
			bossInfoData_Datas.beKilled = data.GetInt();//是否被杀
			bossInfoData_Datas.bossType = data.GetInt();//boss类型
			bossInfoData_Datas.changingBlood =data.GetLong();
			bossInfoData_Datas.blood = data.GetInt();//基础血量
			bossInfoData_Datas.increaseBlood =data.GetLong();
			bossInfoData_Datas.rewardNum1 = data.GetInt();//奖励类型1
			bossInfoData_Datas.rewardNum2 = data.GetInt();//奖励类型2
			bossInfoData_Datas.startTime =data.GetLong();
			bossInfoData_Datas.continueTime =data.GetLong();
			bossInfoData_Datas.endTime =data.GetLong();
			bossInfoData.Add(bossInfoData_Datas);
		}
		ArrayList lastWinHumanData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			LastWinHumanData lastWinHumanData_Datas= new LastWinHumanData();
			lastWinHumanData_Datas.level =data.GetLong();
			lastWinHumanData_Datas.vipLevel = data.GetInt();//vip等级
			lastWinHumanData_Datas.userId =data.GetLong();
			lastWinHumanData_Datas.country = data.GetString();//国家
			lastWinHumanData_Datas.head = data.GetString();//头像
			lastWinHumanData_Datas.name = data.GetString();//名称
			lastWinHumanData_Datas.attackTotalGold =data.GetLong();
			lastWinHumanData.Add(lastWinHumanData_Datas);
		}
		WorldbossHandler.Instance().GC_GET_BOSS_INFO(bossInfoData,lastWinHumanData);
	}
 
  	/**
	 * 返回 伤害 排行榜 的 信息 请求
	 * @param curIndex 当前用户所在排行榜的位置
	 * @param attackRankData boss的信息
	 */
	public void GC_GET_RANK_INFO(InputMessage data) 
	{
		int i,size;
		int curIndex = data.GetInt();		
		ArrayList attackRankData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			AttackRankData attackRankData_Datas= new AttackRankData();
			attackRankData_Datas.name = data.GetString();//用户的名称
			attackRankData_Datas.bossId =data.GetLong();
			attackRankData_Datas.userId =data.GetLong();
			attackRankData_Datas.attackTotalBlood =data.GetLong();
			attackRankData_Datas.vipAdditionRate = data.GetInt();//vip加成（百分号 前边的数）
			attackRankData_Datas.attackRate = data.GetInt();//当前用户 打掉的血 占据 总血 的 比例   ，（百分号 前边的数）
			attackRankData_Datas.sortIndex = data.GetInt();//排序字段
			attackRankData.Add(attackRankData_Datas);
		}
		WorldbossHandler.Instance().GC_GET_RANK_INFO(curIndex,attackRankData);
	}
 
  	/**
	 * 刷新boss的信息
	 * @param refreshBossData 刷新boss的信息
	 */
	public void GC_REFRESH_BOSS_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList refreshBossData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RefreshBossData refreshBossData_Datas= new RefreshBossData();
			refreshBossData_Datas.curBlood =data.GetLong();
			refreshBossData_Datas.skillTime =data.GetLong();
			refreshBossData.Add(refreshBossData_Datas);
		}
		WorldbossHandler.Instance().GC_REFRESH_BOSS_INFO(refreshBossData);
	}
 
  	/**
	 * boss的开始结束信息
	 * @param bossType 下一个worldBoss的类型
	 * @param beKilled 是否 被击杀 1：被击杀- 0：没有被击杀
	 * @param startEndRuning 0:开启，1:关闭, 2：进行中
	 * @param nextBossLeftTime 距离 下一个boss 开始的剩余时间 (秒)
	 * @param curBossLeftTime 当前的boss 剩余时间 (秒)
	 */
	public void GC_BOSS_START_END_INFO(InputMessage data) 
	{
		int bossType = data.GetInt();		
		int beKilled = data.GetInt();		
		int startEndRuning = data.GetInt();		
		long nextBossLeftTime = data.GetLong();
		long curBossLeftTime = data.GetLong();
		WorldbossHandler.Instance().GC_BOSS_START_END_INFO(bossType,beKilled,startEndRuning,nextBossLeftTime,curBossLeftTime);
	}
 
  	/**
	 * 自己攻击的血量 
	 * @param selfAttackBlood 自己攻击的血量
	 * @param winType 当前是否 bigWin、megaWin等等 :   0:普通     1:bigwin  2:megawin   3:superwin  4:epicwin
	 */
	public void GC_SELF_ATTACK_BLOOD_INFO(InputMessage data) 
	{
		int selfAttackBlood = data.GetInt();		
		int winType = data.GetInt();		
		WorldbossHandler.Instance().GC_SELF_ATTACK_BLOOD_INFO(selfAttackBlood,winType);
	}
 
  	/**
	 * 自己攻击的血量 
	 * @param skillType 技能类型，0:持续的，1：立即生效的
	 * @param skill 是否开启技能，0:开启，1关闭
	 * @param skillTime 技能持续时间
	 */
	public void GC_RETURN_BLOOD(InputMessage data) 
	{
		int skillType = data.GetInt();		
		int skill = data.GetInt();		
		int skillTime = data.GetInt();		
		WorldbossHandler.Instance().GC_RETURN_BLOOD(skillType,skill,skillTime);
	}
 
  	/**
	 * 比赛之前 多少分钟
	 * @param bossType boss的类型
	 * @param leftTime 剩余时间
	 */
	public void GC_BEFORE_START(InputMessage data) 
	{
		int bossType = data.GetInt();		
		int leftTime = data.GetInt();		
		WorldbossHandler.Instance().GC_BEFORE_START(bossType,leftTime);
	}
}