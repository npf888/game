package com.gameserver.weixin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;




public class HttpClientUtil {
	
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	/** 
	   * post方式提交表单（模拟用户登录请求） 
	   */
	  public static String postUrl(String url,HashMap<String,String> params) { 
	    // 创建默认的httpClient实例.  
	    CloseableHttpClient httpclient = HttpClients.createDefault(); 
	    // 创建httppost  
	    HttpPost httppost = new HttpPost(url); 
	    // 创建参数队列  
	    List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
	    Set<Entry<String, String>> entrySet= params.entrySet();
	    for(Entry<String, String> entry:entrySet){
	    	String key = entry.getKey();
	    	String value = entry.getValue();
	    	formparams.add(new BasicNameValuePair(key, value));
	    }
	    HttpEntity httpEntity; 
	    try { 
	    	httpEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); 
	      httppost.setEntity(httpEntity);
	      logger.error("executing request " + httppost.getURI()); 
	      CloseableHttpResponse response = httpclient.execute(httppost); 
	      try { 
	        HttpEntity entity = response.getEntity(); 
	        if (entity != null) { 
	        	 return EntityUtils.toString(entity, "UTF-8"); 
	        } 
	      } finally { 
	        response.close(); 
	      } 
	    } catch (ClientProtocolException e) { 
	    	logger.error("", e);
	    } catch (UnsupportedEncodingException e1) { 
	    	logger.error("", e1);
	    } catch (IOException e) { 
	    	logger.error("", e);
	    } finally { 
	      // 关闭连接,释放资源  
	      try { 
	        httpclient.close(); 
	      } catch (IOException e) { 
	    	  logger.error("", e);
	      }
	    } 
	    
	    return null;
	} 
	  /** 
	   * post  json 提交 方式提交表单（模拟用户登录请求） 
	   */
	  public static String postJSONUrl(String url,String jsonParamStr) { 
		  // 创建默认的httpClient实例.  
		  CloseableHttpClient httpclient = HttpClients.createDefault(); 
		  // 创建httppost  
		  HttpPost httppost = new HttpPost(url); 
		  // 创建参数队列  
		  try { 
			  
			  // 建立一个NameValuePair数组，用于存储欲传送的参数  
			  httppost.addHeader("Content-type","application/json; charset=utf-8");  
              httppost.setHeader("Accept", "application/json");  
              httppost.setEntity(new StringEntity(jsonParamStr, Charset.forName("UTF-8"))); 
			  logger.error("executing request " + httppost.getURI()); 
			  CloseableHttpResponse response = httpclient.execute(httppost); 
			  try { 
				  HttpEntity entity = response.getEntity(); 
				  if (entity != null) { 
					  return EntityUtils.toString(entity, "UTF-8"); 
				  } 
			  } finally { 
				  response.close(); 
			  } 
		  } catch (ClientProtocolException e) { 
			  logger.error("", e); 
		  } catch (UnsupportedEncodingException e1) { 
			  logger.error("", e1);
		  } catch (IOException e) { 
			  logger.error("", e);
		  } finally { 
			  // 关闭连接,释放资源  
			  try { 
				  httpclient.close(); 
			  } catch (IOException e) { 
				  logger.error("", e);
			  } 
		  } 
		  
		  return null;
	  } 
	  
	  
	  /** 
	     * 发送 get请求 
	     */  
	  public static String get(String url) {  
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        try {  
	            // 创建httpget.    
	            HttpGet httpget = new HttpGet(url);  
	             logger.error("executing request " + httpget.getURI());  
	            // 执行get请求.    
	            CloseableHttpResponse response = httpclient.execute(httpget);  
	            try {  
	                // 获取响应实体    
	                HttpEntity entity = response.getEntity();  
	                // 打印响应状态    
	                 logger.error(response.getStatusLine());  
	                if (entity != null) {  
	                    // 打印响应内容    
	                     return EntityUtils.toString(entity);  
	                }  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            logger.error("", e);
	        } catch (ParseException e) {  
	        	logger.error("", e);
	        } catch (IOException e) {  
	        	logger.error("", e);
	        } finally {  
	            // 关闭连接,释放资源    
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	            	logger.error("", e);
	            }  
	        }  
	        
	        return null;
	    }  
	  
}