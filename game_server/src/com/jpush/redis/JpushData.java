package com.jpush.redis;

import java.util.ArrayList;
import java.util.List;

public class JpushData {
	public int[] notificationType;
	public int priority;
	public String title;
	public String content;
	public List<Condition> conditions = new ArrayList<>();
	public String params = "";
	
	
	public static class Condition
	{
		public int type;
		public String value;
	}
	

}

