package com.core.orm;

import java.io.Serializable;

/**
 * 基础的实体接口
 * @author Thinker
 */

public interface BaseEntity<ID extends Serializable> extends Serializable 
{
	public abstract ID getId();
	
	public abstract void setId(ID id);
}