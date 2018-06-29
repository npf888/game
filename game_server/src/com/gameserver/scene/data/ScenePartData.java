package com.gameserver.scene.data;

import java.util.ArrayList;
import java.util.List;


/**
 * 场景段数据
 * @author Thinker
 *
 */
public class ScenePartData
{
	/** 子场景NPC数据列表 */
	List<SceneNpcData> sceneNpcList = new ArrayList<SceneNpcData>();

	/** 子场景BOSS数据列表 */
	List<SceneNpcData> sceneBossList = new ArrayList<SceneNpcData>();

	
	public ScenePartData(){}


	public List<SceneNpcData> getSceneNpcList() {
		return sceneNpcList;
	}

	public List<SceneNpcData> getSceneBossList() {
		return sceneBossList;
	}	
}
