package com.gameserver.achievement.handler;

public class AchievementHandlerFactory {

	private static AchievementMessageHandler handler = new AchievementMessageHandler();
	public static AchievementMessageHandler getHandler() {
		return handler;
	}
}
