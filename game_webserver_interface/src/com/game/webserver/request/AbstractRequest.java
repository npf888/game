package com.game.webserver.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.util.HttpUtil;
import com.game.webserver.Config.IConfig;
import com.game.webserver.callback.ICallBack;

import com.game.webserver.response.IResponse;

public class AbstractRequest implements IRequest{
	protected String domain;
	protected String reportDomain;
	protected String gmDomain;
    protected String page;
    protected String url;
    protected IConfig config;
    protected long beginTime;
    protected long endTime;
    public AbstractRequest()
    {
    	
    }
    public AbstractRequest(IConfig config)
    {
      this.config = config;
      this.domain = config.getRequestDomain();
      this.reportDomain = config.getReportDomain();
      this.gmDomain = config.getGmDomain();
    }
    
	@Override
	public IResponse send() {
		try
	    {
	      return doRequest(false,true);
	    } catch (Exception e) {
	      this.config.getRecord().recordError("#LOCAL.PLATFORM.REQUEST.ERROR:" + this.url, e);
	    }return getFailResponse();
	}
	
	public IResponse post(String contentData){
		IResponse response = null;
		 long time = 0L;
		 try {
		    String uuid = UUID.randomUUID().toString();

		    recordUrl(uuid, this.url);

		    this.beginTime = System.currentTimeMillis();

		    String result = HttpUtil.postUrl(this.url,contentData);
		   

		    this.endTime = System.currentTimeMillis();

		    time = this.endTime - this.beginTime;

		    this.config.getRecord().recordInfo(uuid + "\t" + result + "\tTime:" + time + "ms");

		    String[] params = getParamters(result.trim());

		    if (params != null)
		        response = getResponse(params);
		    else {
		    	Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error  result is["+result+"]");
		        response = getFailResponse();
		    }
		      
		      
		    if (response.isSuccess()) {
		        response.onSuccess(params);
		    }

		    if (response != null)
		        response.setUseTime(time);
		    }
		    catch (Exception e)
		    {
		      Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error==>"+e.getMessage());
		      this.config.getRecord().recordError("#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url, e);
		      response = getFailResponse();

		      if (response != null) {
		        response.setUseTime(time);
		      }
		    }

	   return response;
	}
	
	@Override
	public IResponse sendUnencode() {
		try
	    {
	      return doRequest(false,false);
	    } catch (Exception e) {
	      this.config.getRecord().recordError("#LOCAL.PLATFORM.REQUEST.ERROR:" + this.url, e);
	    }return getFailResponse();
	}

	@Override
	public Future<IResponse> asyncSend(final ICallBack callback)
	  {
	    ExecutorService service = this.config.getReportService();
	    return service.submit(new Callable()
	    {
	      public IResponse call()
	        throws Exception
	      {
	    	
	        IResponse response = AbstractRequest.this.doRequest(false,true);
	        
	     
	        if (callback != null) {
	          if (response.isSuccess())
	            callback.onSuccess(response);
	          else {
	            callback.onFail(response);
	          }
	        }
	        return response;
	      }
	    });
	  }
	
	@Override
	public void asyncPostSend(final String contentData,final ICallBack callback)
	{
		
		ExecutorService service = this.config.getReportService();
		service.submit(new Callable()
		{
			public IResponse call() throws Exception
		    {
		    	IResponse response = AbstractRequest.this.doPostRequest(contentData);
		        if (callback != null) {
		          if (response.isSuccess())
		            callback.onSuccess(response);
		          else {
		            callback.onFail(response);
		          }
		        }
		        return response;
		      }
		   });
	}
	
	protected IResponse doPostRequest(String contentData)
	{
		 IResponse response = null;
		 long time = 0L;
		 try {
		    String uuid = UUID.randomUUID().toString();

		    recordUrl(uuid, this.url);

		    this.beginTime = System.currentTimeMillis();

		    String result = HttpUtil.postUrl(this.url,contentData);
		   

		    this.endTime = System.currentTimeMillis();

		    time = this.endTime - this.beginTime;

		    this.config.getRecord().recordInfo(uuid + "\t" + result + "\tTime:" + time + "ms");

		    String[] params = getParamters(result.trim());

		    if (params != null)
		        response = getResponse(params);
		    else {
		    	Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error  result is["+result+"]");
		        response = getFailResponse();
		    }
		      
		      
		    if (response.isSuccess()) {
		        response.onSuccess(params);
		    }

		    if (response != null)
		        response.setUseTime(time);
		    }
		    catch (Exception e)
		    {
		      Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error==>"+e.getMessage());
		      this.config.getRecord().recordError("#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url, e);
		      response = getFailResponse();

		      if (response != null) {
		        response.setUseTime(time);
		      }
		    }

	   return response;
	}
	
	protected IResponse doRequest(boolean POST,boolean isUnencode)
	  {
	    IResponse response = null;
	    long time = 0L;
	    try {
	      String uuid = UUID.randomUUID().toString();

	      recordUrl(uuid, this.url);

	      this.beginTime = System.currentTimeMillis();
	      String result ="";
	      if(isUnencode){
	    	  result = HttpUtil.getUrl(this.url, this.config.getTimeout(), POST);
	      }else{
	    	  result = HttpUtil.getUrlUNencode(this.url, this.config.getTimeout(), POST);
	      }

	      this.endTime = System.currentTimeMillis();

	      time = this.endTime - this.beginTime;

	      this.config.getRecord().recordInfo(uuid + "\t" + result + "\tTime:" + time + "ms");

	      String[] params = getParamters(result.trim());

	      if (params != null)
	        response = getResponse(params);
	      else {
	    	Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error  result is["+result+"]");
	        response = getFailResponse();
	      }
	      
	      
	      if (response.isSuccess()) {
	        response.onSuccess(params);
	      }

	      if (response != null)
	        response.setUseTime(time);
	    }
	    catch (Exception e)
	    {
	      Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url+"error==>"+e.getMessage());
	      this.config.getRecord().recordError("#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.url, e);
	      response = getFailResponse();

	      if (response != null) {
	        response.setUseTime(time);
	      }
	    }

	    return response;
	  }
	
	protected void recordUrl(String uuid, String url) {
	    this.config.getRecord().recordInfo(uuid + "\t" + url);
	}

	@Override
	public IResponse getResponse(String[] paramArrayOfString) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected IResponse getFailResponse()
	{
	   return getResponse(new String[] { "-1" });
	}

	@Override
	public String getSign(Object[] paramArrayOfObject) {
		String sign = null;
        StringBuilder builder = new StringBuilder();
        for (Object obj : paramArrayOfObject) {
          builder.append(obj);
        }
        builder.append(this.config.getGameKey());
        try
        {
          sign = DigestUtils.md5Hex(builder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
          this.config.getRecord().recordError("#LOCAL.PLATFORM.SIGN.ERROR:", e);
        }

        return sign;
	}

	@Override
	public void generateUrl(Object[] paramArrayOfObject,String aDomain) {
		if(paramArrayOfObject != null)
		{
		int length = paramArrayOfObject.length;

        for (int index = 0; index < length; index++) {
//        	paramArrayOfObject[index] = encodeUrl(paramArrayOfObject[index].toString());
        	paramArrayOfObject[index] = paramArrayOfObject[index].toString();
        }
        
        this.url = String.format(aDomain + this.page, paramArrayOfObject);
		}
		else
			this.url = aDomain+this.page;
 //       Loggers.reportLogger.debug("=======>serverUrl"+this.url);
	}
	
	

	@Override
	public void setParams(Object[] paramArrayOfObject) {
		// TODO Auto-generated method stub
		
	}
	
    protected String[] getParamters(String result)
    {
	    if (result == null||"".equals(result)) {
	      return null;
	    }
	
	    String[] args = result.split("&");
	
	    if ((args == null)) {
	      return null;
	    }
	
	    return args;
    }

	protected String encodeUrl(String str)
	{
	    try
	    {
	      return URLEncoder.encode(str, "UTF-8");
	    } catch (Exception e) {
	      this.config.getRecord().recordError("#IMOP.LOCALHANDLER.ERROR:" + str, e);
	    }return str;
	}
	
	 protected long getTimestamp()
	 {
	    return System.currentTimeMillis() / 1000L;
	 }
	
	 protected String getDateTime() {
	    return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	 }


}
