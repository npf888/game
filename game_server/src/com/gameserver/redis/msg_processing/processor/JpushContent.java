package com.gameserver.redis.msg_processing.processor;

import java.util.ArrayList;
import java.util.List;

public class JpushContent {
	public int id;
	public boolean isTooAll;
	public List<Long> passportIds = new ArrayList<>();
	public String title;
	public String content;
	public String params;
}
