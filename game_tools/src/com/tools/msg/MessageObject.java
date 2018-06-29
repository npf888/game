package com.tools.msg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 协议对象
 * 
 * @author Thinker
 */
public class MessageObject implements Comparable<MessageObject>
{
	/** 所引用的消息类型类 */
	private Class<?> _importMsgTypeClazz = null;
	private String type;
	private String className;
	private String comment;
	private boolean collect;
	private String description;
	private String module;
	private boolean hasListField;
	private boolean listMsg;
	/** 是否在玩家的消息队列中处理，只对WG类型的消息起作用 */
	private boolean playerQueue;
	/** 是否将消息放在好友线程中处理，只能GW类型的消息起作用 */
	private boolean friendQueue;
	/** 是否将消息放在公会线程中处理，只能GW类型的消息起作用 */
	private boolean guildQueue;

	private List<FieldObject> fields; // field;
	private List<FieldObject> subMsgs;
	private String handleMethodName;

	private boolean hasBytesField;

	/**
	 * 类默认构造器
	 * 
	 */
	public MessageObject() 
	{
		type = "CG_UNKNOW_MESSAGE";
		className = "UnknownMessage";
		fields = new ArrayList<FieldObject>();
		subMsgs = new ArrayList<FieldObject>();
		hasBytesField = false;
	}

	/**
	 * 获取所引用的消息类型类
	 * 
	 * @return 
	 * 
	 */
	public Class<?> getImportMsgTypeClazz() 
	{
		return this._importMsgTypeClazz;
	}

	/**
	 * 获取导入消息类型类语句
	 * 
	 * @return 
	 * 
	 */
	public String getImportMsgTypeClazzSentence()
	{
		if (this._importMsgTypeClazz == null)
		{
			return null;
		} else
		{
			return "import " + this._importMsgTypeClazz.getName() + ";";
		}
	}

	/**
	 * 设置所引用的消息类型类
	 * 
	 * @param value 
	 * 
	 */
	public void setImportMsgTypeClazz(Class<?> value) 
	{
		this._importMsgTypeClazz = value;
	}

	public void addField(FieldObject fld) 
	{
		if (fld.getBytes())
		{
			hasBytesField = true;
		}
		fields.add(fld);
	}

	public void addSubMsg(FieldObject fld) 
	{
		subMsgs.add(fld);
	}

	public List<FieldObject> getFields()
	{
		return Collections.unmodifiableList(fields);
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getClassName() 
	{
		return className;
	}

	public void setClassName(String className) 
	{
		this.className = className;
	}

	public String getComment() 
	{
		return comment;
	}

	public void setComment(String comment) 
	{
		this.comment = comment;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	@Override
	public int compareTo(MessageObject arg0) 
	{
		return type.compareTo(arg0.type);
	}

	public void setHasListField(boolean hasListField)
	{
		this.hasListField = hasListField;
	}

	public boolean getHasListField()
	{
		return hasListField;
	}

	public void setHandleMethodName(String handleMethodName)
	{
		this.handleMethodName = handleMethodName;
	}

	public String getHandleMethodName()
	{
		return handleMethodName;
	}

	public void setListMsg(boolean listMsg)
	{
		this.listMsg = listMsg;
	}

	public boolean getListMsg() 
	{
		return listMsg;
	}

	public void setSubMsgs(List<FieldObject> subMsgs)
	{
		this.subMsgs = subMsgs;
	}

	public List<FieldObject> getSubMsgs() 
	{
		return subMsgs;
	}

	/**
	 * @return
	 */
	public int getFieldSize() 
	{
		return fields.size() + subMsgs.size();
	}

	public void setPlayerQueue(boolean playerQueue)
	{
		this.playerQueue = playerQueue;
	}

	public boolean isPlayerQueue() 
	{
		return playerQueue;
	}

	public boolean isFriendQueue()
	{
		return friendQueue;
	}

	public boolean isGuildQueue()
	{
		return guildQueue;
	}

	public void setFriendQueue(boolean friendQueue) 
	{
		this.friendQueue = friendQueue;
	}

	public void setGuildQueue(boolean guildQueue)
	{
		this.guildQueue = guildQueue;
	}

	public boolean getHasBytesField()
	{
		return hasBytesField;
	}

	public void setHasBytesField(boolean hasBytesField) 
	{
		this.hasBytesField = hasBytesField;
	}

	public boolean isCollect() {
		return collect;
	}

	public void setCollect(boolean collect) {
		this.collect = collect;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
