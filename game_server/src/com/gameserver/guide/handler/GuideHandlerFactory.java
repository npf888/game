package com.gameserver.guide.handler;


public class GuideHandlerFactory {
	private static GuideHandler guideHandler = new GuideHandler();
	public static GuideHandler getHandler() {
		
		return guideHandler;
	}
}
