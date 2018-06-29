package com.gameserver.redis.msg_processing.processor;

import com.gameserver.redis.msg_processing.ChannelProcessor;

public class ClubDataChangedProcessor implements ChannelProcessor {

	@Override
	public String getChannel() {
		return "cd_chgd";
	}

	@Override
	public void doProcess(String data) {
		// TODO @drp

	}

}
