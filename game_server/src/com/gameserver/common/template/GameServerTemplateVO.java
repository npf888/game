package com.gameserver.common.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 系统加载表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class GameServerTemplateVO extends TemplateObject {

	/** 客户端版本 */
	@ExcelCellBinding(offset = 1)
	protected String requestClientVersion;

	/** 服务器状态 */
	@ExcelCellBinding(offset = 2)
	protected String serverState;

	/** 是否新服 */
	@ExcelCellBinding(offset = 3)
	protected String serverNew;

	/** 是否是推荐服务器 */
	@ExcelCellBinding(offset = 4)
	protected int suggested;

	/** IP地址 */
	@ExcelCellBinding(offset = 5)
	protected String url;

	/** 服务器名称 */
	@ExcelCellBinding(offset = 6)
	protected String serverName;


	public String getRequestClientVersion() {
		return this.requestClientVersion;
	}



	public void setRequestClientVersion(String requestClientVersion) {
		if (StringUtils.isEmpty(requestClientVersion)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[客户端版本]requestClientVersion不可以为空");
		}
		this.requestClientVersion = requestClientVersion;
	}
	
	public String getServerState() {
		return this.serverState;
	}



	public void setServerState(String serverState) {
		if (StringUtils.isEmpty(serverState)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[服务器状态]serverState不可以为空");
		}
		this.serverState = serverState;
	}
	
	public String getServerNew() {
		return this.serverNew;
	}



	public void setServerNew(String serverNew) {
		if (StringUtils.isEmpty(serverNew)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[是否新服]serverNew不可以为空");
		}
		this.serverNew = serverNew;
	}
	
	public int getSuggested() {
		return this.suggested;
	}



	public void setSuggested(int suggested) {
		if (suggested == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[是否是推荐服务器]suggested不可以为0");
		}
		this.suggested = suggested;
	}
	
	public String getUrl() {
		return this.url;
	}



	public void setUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[IP地址]url不可以为空");
		}
		this.url = url;
	}
	
	public String getServerName() {
		return this.serverName;
	}



	public void setServerName(String serverName) {
		if (StringUtils.isEmpty(serverName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[服务器名称]serverName不可以为空");
		}
		this.serverName = serverName;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, GameServerTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends GameServerTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, GameServerTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "GameServerTemplateVO [  requestClientVersion=" + requestClientVersion + ", serverState=" + serverState + ", serverNew=" + serverNew + ", suggested=" + suggested + ", url=" + url + ", serverName=" + serverName + ",]";
	}
}