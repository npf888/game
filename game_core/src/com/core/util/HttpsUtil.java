package com.core.util;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {

	private static PoolingHttpClientConnectionManager connMgr;  
    private static RequestConfig requestConfig;  
    private static final int MAX_TIMEOUT = 7000;  
  
    static {  
        // 设置连接池  
        connMgr = new PoolingHttpClientConnectionManager();  
        // 设置连接池大小  
        connMgr.setMaxTotal(100);  
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());  
  
        RequestConfig.Builder configBuilder = RequestConfig.custom();  
        // 设置连接超时  
        configBuilder.setConnectTimeout(MAX_TIMEOUT);  
        // 设置读取超时  
        configBuilder.setSocketTimeout(MAX_TIMEOUT);  
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  
        // 在提交请求之前 测试连接是否可用  
        //configBuilder.setStaleConnectionCheckEnabled(true);  
        requestConfig = configBuilder.build();  
    }
    
    
    
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String doGet(String url, Map<String, Object> params,boolean isHttps) {  
        HttpResponse response=null;  
        String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
        CloseableHttpClient httpclient =null;  
        if(isHttps){  
            httpclient=createSSLClientDefault();  
        }else {  
            httpclient=HttpClients.createDefault();  
        }  
        try {  
            HttpGet httpGet = new HttpGet(apiUrl);  
            response = httpclient.execute(httpGet);  
            int statusCode = response.getStatusLine().getStatusCode();  
            System.out.println("执行状态码 : " + statusCode);  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {   
                result=EntityUtils.toString(entity, "UTF-8");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return result;  
    }  
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * 
     * @param url 
     * @param params 
     * @return 
     */  
    public static List<String> getUrlAmazon(String url, Map<String, Object> params,boolean isHttps) {  
    	List<String> list = new ArrayList<String>();
    	
    	HttpResponse response=null;  
    	/*String apiUrl = url;  
    	StringBuffer param = new StringBuffer();  
    	int i = 0;  
    	for (String key : params.keySet()) {  
    		if (i == 0)  
    			param.append("?");  
    		else  
    			param.append("&");  
    		param.append(key).append("=").append(params.get(key));  
    		i++;  
    	}  
    	apiUrl += param;  */
    	String result = null;  
    	CloseableHttpClient httpclient =null;  
    	if(isHttps){  
    		httpclient=createSSLClientDefault();  
    	}else {  
    		httpclient=HttpClients.createDefault();  
    	}  
    	try {  
    		HttpGet httpGet = new HttpGet(url);  
    		response = httpclient.execute(httpGet);  
    		int statusCode = response.getStatusLine().getStatusCode();  
    		list.add(String.valueOf(statusCode));
    		if(statusCode == 200){
    			HttpEntity entity = response.getEntity();  
        		if (entity != null) {   
        			result=EntityUtils.toString(entity, "UTF-8"); 
        			list.add(result);
        		}  
    		}
    		
    	} catch (IOException e) {  
    		e.printStackTrace();  
    	}finally {  
    		if (response != null) {  
    			try {  
    				EntityUtils.consume(response.getEntity());  
    			} catch (IOException e) {  
    				e.printStackTrace();  
    			}  
    		}  
    	}  
    	return list;  
    }  
   
    public static CloseableHttpClient createSSLClientDefault(){  
        try {  
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
                //信任所有  
                public boolean isTrusted(X509Certificate[] chain,  
                                         String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();  
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);  
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return  HttpClients.createDefault();  
    }  
    
   public static void main(String[] args) {
	String url = "https://accounts.google.com/o/oauth2/token";
	
	Map<String,Object> paraMap = new HashMap<String,Object>();
	paraMap.put("FacserviceID", "nfeiGC");
	String result = doGet(url,paraMap,true);//doPostSSL(url,paraMap);
	
	
	
	System.out.println(result);
	
	
   }
   
 
    
}
