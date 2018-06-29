package com.gameserver.common.unit;

import com.gameserver.scene.Scene;
import com.gameserver.scene.SceneService;

public abstract class SceneUnit implements GameUnit
{
	/** 标识，由所属场景分配 */
	protected int id = GameUnitList.NULL_ID;
	/** 显示名称 */
	protected String name;
	/** 图标 */
	protected int icon;
	/** 形象 */
	protected int image;
	
	/** 所属场景 */
	protected volatile Scene scene = null;
	/**
	 * 处理游戏对象定时触发的或频繁计算的业务逻辑
	 */
	public abstract void heartBeat();
	

	public int getIcon() 
	{
		return icon;
	}

	public void setIcon(int icon) 
	{
		this.icon = icon;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public int getImage() {
		return image;
	}
	
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}
	
	public int getSceneId() {
		return scene == null ? SceneService.INVALID_SCENEID : scene.getId();
	}
}
