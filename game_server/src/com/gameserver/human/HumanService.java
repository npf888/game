package com.gameserver.human;



import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.converter.AbstractConverter;
import com.core.converter.Converter;
import com.gameserver.common.Globals;
import com.gameserver.human.data.HumanInfoData;
import com.gameserver.player.Player;

/**
 * 
 * 封装了对游戏角色离线和在线的操作
 * 
 * 1.角色所属玩家在线,直接委派{@see OnlinePlayerService}进行Human实例的获得
 * 2.角色所属玩家不在线,查询{@see GameCacheService}中是否加载它的缓存,如果加载直接转换为HumanInfo,
 * 			如果没有加载,使用{@see GameDaoService}查询,并放置到缓存中,返回HumanInfo
 *@author Thinker
 */
public class HumanService  implements InitializeRequired,AfterInitializeRequired
{
	private ConvertHelper convertHelper;
	
	
	public HumanService()
	{
		
		this.convertHelper  = new ConvertHelper();
	}
	
	@Override
	public void init()
	{
	
	}

	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 尝试从在线玩家数据中获取指定id的HumanInfo
	 * @param roleUUID
	 * @return
	 */
	public HumanInfoData getOnlineHumanInfo(long roleUUID)
	{
		Player player=Globals.getOnlinePlayerService().getPlayer(roleUUID);
		if(player==null) return null;
		Human human=player.getHuman();
		if(human==null) return null;
		return getHumanInfo(human);
	}

	



	
	///////////////////////////////////////数据转换辅助类///////////////////////////////////////////////
	public HumanInfoData getHumanInfo(Human human)
	{
		return convertHelper.HUMAN_INFO_WITH_HUMAN.convert(human);
	}
	/**
	 * 数据转换辅助类
	 * @author Thinker
	 *
	 */
	private static final class ConvertHelper
	{
		private final Converter<Human,HumanInfoData> HUMAN_INFO_WITH_HUMAN = new AbstractConverter<Human,HumanInfoData>()
		{		
			@Override
			public HumanInfoData convert(Human human)
			{
				if (human == null) return null;
				HumanInfoData humanInfo = new HumanInfoData();
				humanInfo.setRoleId(human.getPassportId());
				humanInfo.setName(human.getName());		
				humanInfo.setSex(human.getGirl());
				humanInfo.setLevel(human.getLevel());
				humanInfo.setDiamond(human.getAllDiamond());
				humanInfo.setGold(human.getGold());
				humanInfo.setCurExp(human.getCurExp());
				humanInfo.setCharm(human.getCharm());
				humanInfo.setMaxExp(human.getMaxExp());
				humanInfo.setSceneId(human.getSceneId());
				Set<String> gameviewSet = human.getGameview();
				humanInfo.setGvfirst(JSON.toJSONString(gameviewSet));
				humanInfo.setNewguide(human.getNewguide());
				humanInfo.setWatchNum(human.getWatchNum());
//				humanInfo.setVipLevel(human.getHumanVipNewManager().getVipLv());
				humanInfo.setCountries(human.getCountries());
//				humanInfo.setIntegral(Globals.getRankNewServer().getRankIntegral(human.getPassportId()));
				humanInfo.setNewGuyGift(human.getNewGuyGift());
				humanInfo.setTodayView(human.getTodayView());
				humanInfo.setBazooGold(human.getBazooGold());
				humanInfo.setBazooRoom(human.getBazooRoom());
				humanInfo.setBazooNewGuyProcess(human.getBazooNewGuyProcess()==null?"{\"classicCompleted\":0, \"niuniuCompleted\":0, \"showhandCompleted\":0, \"redblackCompleted\":0}":human.getBazooNewGuyProcess());
				//0未购买 只有未购买 新手大礼包 才处理
				/*if(human.getNewGuyGift() == 0){
					String now = TimeUtils.formatYMDTime(new Date().getTime());
					if(human.getTodayView() == null){
						human.setTodayView(now+":0");
						human.setModified();
						humanInfo.setTodayView(0);
					}else{
						String[] timeStatus = human.getTodayView().split(":");
						if(!timeStatus[0].equals(now)){
							human.setTodayView(now+":0");
							human.setModified();
							humanInfo.setTodayView(0);
						}else{
							if(timeStatus[1].equals("0")){
								human.setTodayView(now+":1");
								human.setModified();
							}
							humanInfo.setTodayView(1);
						}
					}
				}*/
				return humanInfo;
			}
		};
		
	}

	

}

