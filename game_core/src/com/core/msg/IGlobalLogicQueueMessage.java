package com.core.msg;


/**
 * 需要扔到全局处理逻辑的消息队列中处理的消息
 * 
 * <pre>
 * 此类消息使用于从异步线程发送给GameServer的，需要在主逻辑线程中
 * 进行处理的消息，没有实现此接口的消息会在GameServer的主线程中处理
 * </pre>
 * 
 * @see GameMessageProcessor
 */
public interface IGlobalLogicQueueMessage
{

}