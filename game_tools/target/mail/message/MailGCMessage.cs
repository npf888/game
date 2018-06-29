using System.Collections;

public class MailGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_LOAD_MAIL_LIST,GC_LOAD_MAIL_LIST);
		register(NetMessageType.GC_SEND_MAIL,GC_SEND_MAIL);
		register(NetMessageType.GC_READ_MAIL,GC_READ_MAIL);
		register(NetMessageType.GC_UPDATE_MAIL_LIST,GC_UPDATE_MAIL_LIST);
		register(NetMessageType.GC_DEAL_WITH_REWARD,GC_DEAL_WITH_REWARD);
		register(NetMessageType.GC_DELETE_MAIL,GC_DELETE_MAIL);
		register(NetMessageType.GC_RECEIVE_ALL,GC_RECEIVE_ALL);
	}
 
  	/**
	 * 响应客户端请求系统邮件列表
	 * @param mailKind 邮件类型
	 * @param mailInfoDataList 邮件列表
	 */
	public void GC_LOAD_MAIL_LIST(InputMessage data) 
	{
		int i,size;
		int mailKind = data.GetInt();		
		ArrayList mailInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			MailInfoData mailInfoDataList_Datas= new MailInfoData();
			mailInfoDataList_Datas.mailId =data.GetLong();
			mailInfoDataList_Datas.sendId =data.GetLong();
			mailInfoDataList_Datas.sendName = data.GetString();//发件角色名称
			mailInfoDataList_Datas.mailCdTime =data.GetLong();
			mailInfoDataList_Datas.hasAttachment = data.GetInt();//是否有奖品未领取
			mailInfoDataList_Datas.mailStatus = data.GetInt();//邮件状态
			mailInfoDataList_Datas.mailTitle = data.GetString();//邮件标题
			mailInfoDataList_Datas.mailCreatTime =data.GetLong();
			mailInfoDataList_Datas.isFriendSend = data.GetInt();//是否是好友发送的    0:是，1:否
			mailInfoDataList_Datas.vipLevel = data.GetInt();//vip等级
			mailInfoDataList_Datas.headName = data.GetString();//头像
			mailInfoDataList.Add(mailInfoDataList_Datas);
		}
		MailHandler.Instance().GC_LOAD_MAIL_LIST(mailKind,mailInfoDataList);
	}
 
  	/**
	 * 响应客户端请求发送邮件
	 */
	public void GC_SEND_MAIL(InputMessage data) 
	{
		MailHandler.Instance().GC_SEND_MAIL();
	}
 
  	/**
	 * 响应客户端请求读取邮件
	 * @param mailId 邮件id
	 * @param mailKind 邮件类型
	 * @param content 邮件内容
	 * @param receiveTime 接收时间
	 * @param promptMessage 提示信息
	 * @param isDealWith 是否已经处理过好友申请
	 * @param hasAttachment 是否有奖品未领取
	 * @param randReward 邮件奖励
	 */
	public void GC_READ_MAIL(InputMessage data) 
	{
		int i,size;
		long mailId = data.GetLong();
		int mailKind = data.GetInt();		
		string content = data.GetString();		
		long receiveTime = data.GetLong();
		string promptMessage = data.GetString();		
		int isDealWith = data.GetInt();		
		int hasAttachment = data.GetInt();		
		ArrayList randReward = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			RandRewardData randReward_Datas= new RandRewardData();
			randReward_Datas.rewardId = data.GetInt();//奖励id
			randReward_Datas.rewardCount = data.GetInt();//奖励数量
			randReward.Add(randReward_Datas);
		}
		MailHandler.Instance().GC_READ_MAIL(mailId,mailKind,content,receiveTime,promptMessage,isDealWith,hasAttachment,randReward);
	}
 
  	/**
	 * 服务器更新邮件列表,显示新邮件
	 * @param mailKind 邮件类型
	 * @param mailId 邮件id
	 * @param mailInfoData 邮件详细信息
	 */
	public void GC_UPDATE_MAIL_LIST(InputMessage data) 
	{
		int mailKind = data.GetInt();		
		long mailId = data.GetLong();
		MailInfoData mailInfoData = new MailInfoData();
		mailInfoData.mailId = data.GetLong();//邮件id
		mailInfoData.sendId = data.GetLong();//发件角色id
		mailInfoData.sendName = data.GetString();//发件角色名称
		mailInfoData.mailCdTime = data.GetLong();//邮件还有多长时间过期
		mailInfoData.hasAttachment = data.GetInt();//是否有奖品未领取
		mailInfoData.mailStatus = data.GetInt();//邮件状态
		mailInfoData.mailTitle = data.GetString();//邮件标题
		mailInfoData.mailCreatTime = data.GetLong();//邮件发送时间
		mailInfoData.isFriendSend = data.GetInt();//是否是好友发送的    0:是，1:否
		mailInfoData.vipLevel = data.GetInt();//vip等级
		mailInfoData.headName = data.GetString();//头像
		MailHandler.Instance().GC_UPDATE_MAIL_LIST(mailKind,mailId,mailInfoData);
	}
 
  	/**
	 * 响应客户端请求全部奖品领取奖励
	 * @param mailId 邮件id
	 */
	public void GC_DEAL_WITH_REWARD(InputMessage data) 
	{
		long mailId = data.GetLong();
		MailHandler.Instance().GC_DEAL_WITH_REWARD(mailId);
	}
 
  	/**
	 * 响应客户端请求删除邮件
	 */
	public void GC_DELETE_MAIL(InputMessage data) 
	{
		MailHandler.Instance().GC_DELETE_MAIL();
	}
 
  	/**
	 * 一键领取邮件返回
	 * @param mailIdList 邮件Id列表
	 */
	public void GC_RECEIVE_ALL(InputMessage data) 
	{
		int i,size;
		ArrayList mailIdList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long mailIdList_Datas = data.GetLong();
			mailIdList.Add(mailIdList_Datas);
		}
		MailHandler.Instance().GC_RECEIVE_ALL(mailIdList);
	}
}