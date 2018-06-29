package com.gameserver.activity.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 客户端链接跳转地方
 * @author 郭君伟
 *
 */
public enum PageLinkEnum implements IndexedEnum{
	NO_LINK(0),
	//快捷商店
	EASY_MALL(1),
	//邮件
	MAIL(2),
	//好友
	FRIEND(3),
	//玩家信息
	PLAYER_INFO(4),
	//成就
	ACHIEVEMENT(5),
	//排行
	RANK(6),
	//设置
	SETTING(7),
	//任务 
	TASK(8),
	//活动
	ACTIVITY(11),
	//奖励列表
	REWARD_LIST(12),
	// 商店
	MALL(100),
	//个心信息
	PERSONAL_INFO(101),
	//聊天
	CHAT(102),
	//帮助
	HELP(103),
	//德州房间
	TEXAS_ROOM(104),
//	/德州游戏
	TEXAS_GAME(200)
	;
	private final int index;
	private static final List<PageLinkEnum> values = IndexedEnumUtil.toIndexes(PageLinkEnum.values());
	
	private PageLinkEnum(int index){
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	public static PageLinkEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
