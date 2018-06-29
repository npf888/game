package com.gameserver.human;

import java.util.HashMap;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.human.template.VideoRewardTemplate;
/**
 * 观看视频奖励数据
 * @author 郭君伟
 *
 */
public class VideoRewardServer implements InitializeRequired {
	
	/**次数 奖励筹码 **/
	private Map<Integer,Integer> videoRewardData = new HashMap<Integer,Integer>();

	@Override
	public void init() {
		
		Map<Integer, VideoRewardTemplate> map = Globals.getTemplateService().getAll(VideoRewardTemplate.class);
		
		for(VideoRewardTemplate vt : map.values()){
			videoRewardData.put(vt.getId(), vt.getItemNum());
		}
	}
	
	/**
	 * 观看次数获得奖励筹码
	 * @param watchNum
	 * @return
	 */
	public int getReward(int watchNum){
		if(videoRewardData.containsKey(watchNum)){
			return videoRewardData.get(watchNum);
		}
		return 0;
	}

}
