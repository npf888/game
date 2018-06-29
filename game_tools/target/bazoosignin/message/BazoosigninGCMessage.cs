using System.Collections;

public class BazoosigninGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_SIGNIN,GC_BAZOO_SIGNIN);
	}
 
  	/**
	 * 签到返回
	 * @param gold 签到获得的金币
	 * @param nameInt 色子 int类型 的代号  豹子:7, 顺子:6, 炸弹:5, 葫芦:4, 三条:3, 两对:2, 两对:1, 散点:0
	 * @param diceValues 色子的值
	 */
	public void GC_BAZOO_SIGNIN(InputMessage data) 
	{
		int i,size;
		long gold = data.GetLong();
		int nameInt = data.GetInt();		
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		BazoosigninHandler.Instance().GC_BAZOO_SIGNIN(gold,nameInt,diceValues);
	}
}