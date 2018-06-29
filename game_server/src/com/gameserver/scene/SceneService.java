package com.gameserver.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.annotation.SyncIoOper;
import com.core.template.TemplateService;
import com.core.util.LogUtils;
import com.gameserver.baccart.BaccartRoom;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanMiscManager;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.PlayerState;
import com.gameserver.scene.handler.SceneHandlerFactory;
import com.gameserver.scene.template.CityTemplate;
import com.gameserver.scene.template.GuideTemplate;
import com.gameserver.scene.template.NewGuideSortTemplate;
import com.gameserver.scene.template.StoryTemplate;
import com.gameserver.texas.TexasRoom;

/**
 * 场景服务
 * @author Thinker
 */
public class SceneService implements InitializeRequired,AfterInitializeRequired
{
	/** 非法的场景Id */
	public static final int INVALID_SCENEID = -1;

	
	private Map<Integer, Scene> sceneMap;
	private Map<Integer, SceneRunner> sceneRunners;
	
	private OnlinePlayerService onlinePlayerService;
	
	/** 模版服务 */
	private TemplateService templateService;
	
	
	
	public SceneService(TemplateService templateService, OnlinePlayerService onlinePlayerService)
	{
			sceneMap = new ConcurrentHashMap<Integer, Scene>();
			sceneRunners = new ConcurrentHashMap<Integer, SceneRunner>();
			this.onlinePlayerService = onlinePlayerService;
			
			this.templateService = templateService;
			
			
		}

	@Override
	public void init()
	{
		// 获取场景模版字典
		Map<Integer,CityTemplate> sceneTmplMap = this.templateService.getAll(CityTemplate.class);
		// 场景事件监听
		List<SceneListener> listeners = Arrays.asList(new SceneListener[] {});
		for(CityTemplate sceneTmpl:sceneTmplMap.values())
		{
			// 初始化场景
			this.initScene(sceneTmpl,listeners);
		}
	}
		
	/**
	 * 根据模版初始化场景
	 * 
	 * @param sceneTmpl
	 * @param listeners
	 * 
	 */
	@SyncIoOper
	private void initScene(CityTemplate sceneTmpl,List<SceneListener> listeners)
	{
		if(sceneTmpl==null) return;
		// 创建场景对象
		Scene scene=new Scene(onlinePlayerService,sceneTmpl);
		scene.init();
		
		// 注册事件监听
		for(SceneListener listener : listeners) scene.registerListener(listener);
		this.addScene(scene);
	}
	/**
	 * 获取指定场景对象
	 * 
	 * @param sceneId
	 *            场景ID
	 * @return 如果不存在该ID的场景,则返回null
	 * @exception ConcurrentModificationException
	 *                如果不是该场景的线程调用该方法
	 */
	public Scene getScene(int sceneId)
	{
		return sceneMap.get(sceneId);
	}
	/**
	 * @warning 如果有地方同时使用了scenes和sceneRunners，需要先获得锁，
	 *          再进行操作，因为存在只更新了scenes，而没更新sceneRunners的糟糕情况，
	 *          需要保证它们更新的原子性。目前绝大多数对scenes和sceneRunners的引用
	 *          都没有考虑该问题,这是因为他们没有同时使用这两个集合。
	 * 
	 * @param scene
	 */
	private void addScene(Scene scene) 
	{
		synchronized (sceneMap)
		{
			sceneMap.put(scene.getId(), scene);
			sceneRunners.put(scene.getId(), new SceneRunner(scene));
		}
	}
	
	/**
	 * @warning 与{@link SceneService#addScene(Scene)}类似，需要确保
	 *          在同时修改scenes和sceneRunners时加锁。
	 * 
	 * @param sceneId
	 */
	public void removeScene(Integer sceneId)
	{
		synchronized (sceneMap)
		{
			sceneMap.remove(sceneId);
			sceneRunners.remove(sceneId);
		}
	}
	
	/**
	 * 玩家进入场景
	 * 
	 * @param player
	 * @return
	 */
	public boolean onPlayerEnterScene(final Player player)
	{
		int sceneId = -1;
		sceneId = player.getTargetSceneId();
		if(sceneId == -1 )
		{
			Loggers.errorLogger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "目标场景id或坐标不正确"));
			return false;
		}
		Scene scene=sceneMap.get(sceneId);
		if(scene== null)
		{
			Loggers.errorLogger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "场景id：" + sceneId + "不存在"));
			return false;
		}
		
		// 标记此玩家已进入场景，在场景线程将玩家加入场景中之前如果收到
		// 玩家下线的消息，则向场景发送移除此玩家的消息
		player.getHuman().setScene(scene);
		boolean result = SceneHandlerFactory.getHandler().handleEnterScene(player.getId(),sceneId);
		if (!result)
		{
			Loggers.errorLogger.error("Enter scene result is false , will kick player:"+ player.getRoleUUID());
			player.disconnect();
			Globals.getOnlinePlayerService().offlinePlayer(player, PlayerExitReason.SERVER_ERROR);
			return false;
		}
		player.setState(PlayerState.gaming);
		return true;
	}

	/**
	 * 玩家离开场景
	 * 
	 * @param player
	 * @param callback 
	 * 
	 */
	public boolean onPlayerLeaveScene(final Player player,PlayerLeaveSceneCallback afterPlayerLeave)
	{
		Scene scene = sceneMap.get(player.getSceneId());
		if(scene == null)
		{
			Loggers.errorLogger.warn(LogUtils.buildLogInfoStr(player.getRoleUUID(), "sceneId:" + player.getSceneId()+ " not exist"));
			return false;
		}
		//更新在线时长
		HumanMiscManager miscManager = player.getHuman().getHumanMiscManager();
		miscManager.updateOnlineTime();
		
		//离开房间
//		TexasRoom  texasRoom = player.getHuman().getHumanTexasManager().getTexasRoom();
//		if(texasRoom!=null)
//		{
//			Globals.getTexasService().exitRoom(player);
//		}
		
		//离开百家乐
//		BaccartRoom baccartRoom = player.getHuman().getHumanBaccartManager().getBaccartRoom();
//		if(baccartRoom!=null){
//			baccartRoom.playerLeave(player);
//		}
		
		// 将玩家设置为离线状态
		player.setState(PlayerState.leaving);
		SceneHandlerFactory.getHandler().handleLeaveScene(player.getId(), player.getSceneId(), afterPlayerLeave);
		return true;
	}
	/**
	 * 线程安全的
	 * 
	 * @return
	 */
	public List<SceneRunner> getAllSceneRunners()
	{
		List<SceneRunner> runnerList = new ArrayList<SceneRunner>();
		for (SceneRunner runner : sceneRunners.values())
		{
			runnerList.add(runner);
		}
		return runnerList;
	}
	
	/**
	 * 移除场景中所有的玩家，在服务器断线或停机时调用，此处在主线程中直接操作场景
	 */
	public void removeAllPlayers() 
	{
		for (Scene scene : sceneMap.values())
		{
			List<Integer> playerIds = scene.getPlayerManager().getPlayerIds();
			for (Integer playerId : playerIds) 
			{
				SceneHandlerFactory.getHandler().handleLeaveScene(playerId,scene.getId(), null);
			}
		}
	}
	
	public void start()
	{
//		Loggers.gameLogger.info("begin start heartBeatThread");
//		sceneTaskScheduler = new HeartbeatThread();
//		sceneTaskScheduler.start();
//		Loggers.gameLogger.info("end start heartBeatThread");
	}

	public void stop()
	{
//		Loggers.gameLogger.info("begin stop heartBeatThread");
//		sceneTaskScheduler.shutdown();
//		Loggers.gameLogger.info("end stop heartBeatThread");

	}

	/**
	 * 根据ID获取剧情数据
	 * @param storyId
	 * @return
	 */
	public StoryTemplate getStoryTemplById(int storyId)
	{
		return Globals.getTemplateService().get(storyId,StoryTemplate.class);
	}
	
	/**
	 * 根据ID获取新手引导数据
	 * @param storyId
	 * @return
	 */
	public GuideTemplate getGuideTemplById(int guideId)
	{
		return Globals.getTemplateService().get(guideId,GuideTemplate.class);
	}
	
	/**
	 * 获取第一次登陆时的城市 Id
	 * 
	 * @param human 
	 * @return
	 * 
	 */
	public int getFirstCityId()
	{
		CityTemplate firstCity = null;
		Map<Integer,CityTemplate> sceneTmplMap = this.templateService.getAll(CityTemplate.class);
		for(CityTemplate sceneTmpl:sceneTmplMap.values())
		{
			if(firstCity==null) firstCity=sceneTmpl;
			if(sceneTmpl.getId()<firstCity.getId()) firstCity=sceneTmpl;
		}
		return firstCity.getId();
	}
	
	
	/**
	 * 获取全部新用户行为信息
	 * @return
	 */
	public Map<Integer,NewGuideSortTemplate> getAllNewGuideSortTempl()
	{
		return Globals.getTemplateService().getAll(NewGuideSortTemplate.class);
	}
	
	public void onReportNewGuide(int type,String typeId,Human human){
		for(NewGuideSortTemplate newGuideSortTempl:getAllNewGuideSortTempl().values()){
			if(newGuideSortTempl.getType()!=type)continue;
			if(!newGuideSortTempl.getTypeId().equals(typeId))continue;
	
			break;
		}
	}

	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}
}
