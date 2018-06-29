package com.game.webserver.request;

import java.util.concurrent.Future;

import com.game.webserver.callback.ICallBack;
import com.game.webserver.response.IResponse;

public interface IRequest {
	/**
	   * 同步发送请求
	   * @return
	   */
	  public abstract IResponse send();
	  
	  /**
	   * 同步发送请求,url不编码
	   * @return
	   */
	  public abstract IResponse sendUnencode();
	  /**
	   * 异步发送请求
	   * @param paramICallback
	   * @return
	   */
	  public abstract Future<IResponse> asyncSend(ICallBack paramICallback);
	  
	  public void asyncPostSend(String contentData,ICallBack paramICallback);
	  
	  public abstract IResponse getResponse(String[] paramArrayOfString);

	  public abstract String getSign(Object[] paramArrayOfObject);

	  public abstract void generateUrl(Object[] paramArrayOfObject,String aDomain);

	  public abstract void setParams(Object[] paramArrayOfObject);
}
