package com.core.orm;

import java.io.Serializable;


/**
 * @author Thinker
 * 
 */
public interface IdGenerator 
{
	@SuppressWarnings("unchecked")
	public Serializable generateId(BaseEntity entity);
}