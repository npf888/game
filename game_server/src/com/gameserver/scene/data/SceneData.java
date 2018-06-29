package com.gameserver.scene.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景数据
 * @author Thinker
 *
 */
public class SceneData
{
	/** 子场景数据列表 */
	List<ScenePartData> scenePartList = new ArrayList<ScenePartData>();

	public SceneData(){}

	public List<ScenePartData> getScenePartList()
	{
		return scenePartList;
	}
}
