package com.tools.msg;

import java.util.List;

import com.core.util.Assert;

/**
 * 消息类中的一个属性字段
 * @author Thinker
 * 
 */
public class FieldObject 
{
	private String smallName; // onTwoThree形式的名字 name/setter/getter/ctor
	private String bigName; // OneTwoThree形式的名字
	private String castType; // 类型所写
	private String type; // 类型所写
	private String clientType;// 对应的客户端对象类型
	private String comment; // 说明
	private boolean list; // 是否是list类型
	private boolean subMsg; // 是否是子消息
	private String subMsgType; // 子消息类型
	private boolean bytes;
	private String ref; // 参见的class引用
	/** 是否是新的类型，如果是新的类型，生成代码的时候需要同时生成内部类代码 */
	private boolean needCreateType;
	/** 是否为子消息，如果类型不是基本类型则认为是子消息 */
	private boolean isNewType;
	private List<FieldObject> subFields;
	private boolean hasListField;
	
	private boolean isDirect;
	public FieldObject() 
	{
	}

	public boolean getBytes()
	{
		return bytes;
	}

	public void setBytes(boolean bytes)
	{
		this.bytes = bytes;
	}

	public String getSmallName()
	{
		return smallName;
	}

	public void setSmallName(String smallName) 
	{
		// 断言参数不为空
		Assert.notNull(smallName, "smallName");

		this.smallName = smallName.trim();
		// 首字母大写
		this.bigName = smallName
			.substring(0, 1)
			.toUpperCase() + smallName.substring(1);
	}

	public String getBigName() 
	{
		return bigName;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? type : type.trim();
	}
	

	public String getCastType() {
		return castType;
	}

	public void setCastType(String castType) {
		this.castType = castType;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment) 
	{
		this.comment = comment;
	}

	public boolean getList()
	{
		return list;
	}

	public void setList(boolean list) 
	{
		this.list = list;
	}

	public String getRef()
	{
		return ref;
	}

	public void setRef(String ref)
	{
		this.ref = ref;
	}

	public void setNeedCreateType(boolean isNewType)
	{
		this.needCreateType = isNewType;
	}

	public boolean getNeedCreateType() 
	{
		return needCreateType;
	}

	public void setSubFields(List<FieldObject> subFields)
	{
		this.subFields = subFields;
	}

	public List<FieldObject> getSubFields() 
	{
		return subFields;
	}

	public void setIsNewType(boolean isNewType)
	{
		this.isNewType = isNewType;
	}

	public boolean getIsNewType() 
	{
		return isNewType;
	}

	public void setSubMsg(boolean subMsg) 
	{
		this.subMsg = subMsg;
	}

	public boolean isSubMsg() 
	{
		return subMsg;
	}

	public void setSubMsgType(String subMsgType) 
	{
		this.subMsgType = subMsgType;
	}

	public String getSubMsgType() 
	{
		return subMsgType;
	}

	public void setHasListField(boolean hasListField) 
	{
		this.hasListField = hasListField;
	}

	public boolean isHasListField() 
	{
		return hasListField;
	}

	public void setClientType(String clientType)
	{
		this.clientType = clientType;
	}

	public String getClientType() 
	{
		return clientType;
	}

	public boolean isDirect() {
		return isDirect;
	}

	public void setDirect(boolean isDirect) {
		this.isDirect = isDirect;
	}
}
