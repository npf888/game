package com.gameserver.player;

/**
 * 退出玩家某一状态之后的回调接口
 * @author Thinker
 */
public interface PlayerStateExitCallback
{
	void onExitCurState();
}
