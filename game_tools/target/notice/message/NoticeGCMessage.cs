using System.Collections;

public class NoticeGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_NOTICE_INFO_DATA,GC_NOTICE_INFO_DATA);
		register(NetMessageType.GC_NOTICE_INFO_DATA_MULTI,GC_NOTICE_INFO_DATA_MULTI);
	}
 
  	/**
	 * 跑马灯
	 * @param content 内容
	 */
	public void GC_NOTICE_INFO_DATA(InputMessage data) 
	{
		string content = data.GetString();		
		NoticeHandler.Instance().GC_NOTICE_INFO_DATA(content);
	}
 
  	/**
	 * 跑马灯
	 * @param langId 多语言key
	 * @param paramsList 参数
	 */
	public void GC_NOTICE_INFO_DATA_MULTI(InputMessage data) 
	{
		int i,size;
		int langId = data.GetInt();		
		ArrayList paramsList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			string paramsList_Datas = data.GetString();
			paramsList.Add(paramsList_Datas);
		}
		NoticeHandler.Instance().GC_NOTICE_INFO_DATA_MULTI(langId,paramsList);
	}
}