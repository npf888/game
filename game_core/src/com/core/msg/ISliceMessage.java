package com.core.msg;

import java.util.List;


/**
 * 
 * @author Thinker
 *
 */
public interface ISliceMessage<T extends BaseMessage>
{

	public abstract List<T> getSliceMessages();

}