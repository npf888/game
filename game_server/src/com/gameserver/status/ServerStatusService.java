package com.gameserver.status;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.status.enums.ServerStatusEnum;


/**
 * 服务器状态服务
 *
 * @author Thinker
 */
public class ServerStatusService implements InitializeRequired,DestroyRequired,AfterInitializeRequired {
	
	private Logger logger = Loggers.serverLogger;
	private final static String SERVERS_KEY = "servers";
	private ServerStatus serverStatus;
	private JedisPool jedisPool;
	
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.info("server status service init");
		serverStatus = new ServerStatus();
		serverStatus.setStatus(ServerStatusEnum.RUN.getIndex());
		if(Globals.getServerConfig().isTest()){
			//test();
		}
	}
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		logger.info("server status service after init");
		jedisPool = Globals.getRedisService().getJedisPool();
		Jedis jedis = jedisPool.getResource();
		try{
			serverStatus.setIp(Globals.getServerConfig().getServerHost());
			serverStatus.setServerId(Globals.getServerConfig().getServerId());
			serverStatus.setPort(Integer.parseInt(Globals.getServerConfig().getPorts()));
			serverStatus.setNums(Globals.getOnlinePlayerService().getOnlinePlayerCount());
			serverStatus.setMaxNums(Globals.getServerConfig().getMaxOnlineUsers());
			jedis.hset(SERVERS_KEY, serverStatus.getServerId(), JSON.toJSONString(serverStatus));
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		
		
		
		
		
	}
	/**
	 * 
	 * @param url 应用地址，类似于http://ip:port/msg/
	 * @param account 账号
	 * @param pswd 密码
	 * @param mobile 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @param needstatus 是否需要状态报告，需要true，不需要false
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	public static String batchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String extno) throws Exception {
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
			}
		}
	

	public void syncStatus() {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String tempServerStatuStr = jedis.hget(SERVERS_KEY, serverStatus.getServerId());
		if (!StringUtils.isEmpty(tempServerStatuStr)){
			ServerStatus tempServerStatus = JSON.parseObject(tempServerStatuStr,ServerStatus.class);
			serverStatus.setStatus(tempServerStatus.getStatus());
		}
		
		serverStatus.setIp(Globals.getServerConfig().getServerHost());
		serverStatus.setServerId(Globals.getServerConfig().getServerId());
		serverStatus.setPort(Integer.parseInt(Globals.getServerConfig().getPorts()));
		serverStatus.setNums(Globals.getOnlinePlayerService().getOnlinePlayerCount());
		serverStatus.setMaxNums(Globals.getServerConfig().getMaxOnlineUsers());
	
		jedis.hset(SERVERS_KEY, serverStatus.getServerId(), JSON.toJSONString(serverStatus));
		jedis.close();
//		jedisPool.returnResourceObject(jedis);
		
		       //AAA：发短信通知服务器人数
				GameServerConfig config = Globals.getServerConfig();
				if(config.getSmsMaxpeople() <= serverStatus.getNums()){
					String url = config.getSmsaddress();// 应用地址
					String account = config.getSmsAccount();// 账号
					String pswd = config.getSmsPwd();// 密码
					String mobile = config.getSmsPhoneNumber();// 手机号码，多个号码使用","分割
					String msg = config.getSmsContent().replace("N", String.valueOf(serverStatus.getNums()));;// 短信内容
					boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
					String extno = null;// 扩展码
					try {
						String returnString = batchSend(url, account, pswd, mobile, msg, needstatus, extno);
						logger.info("SMS ::::::::::  "+returnString);
					} catch (Exception e) {
						logger.error("", e);
					}
				}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		serverStatus.setStatus(ServerStatusEnum.STOP.getIndex());
		Jedis jedis = Globals.getRedisService().getJedisPool().getResource();
		try{
			jedis.hdel(SERVERS_KEY, serverStatus.getServerId());
		}finally{
			jedis.close();
//			Globals.getRedisService().getJedisPool().returnResourceObject(jedis);
		}
		
		
	}
	
	/**
	 * 测试
	 */
	public void test()
	{
		serverStatus.setStatus(ServerStatusEnum.TEST.getIndex());
	}
	
	/**
	 * 运行s
	 */
	public void run()
	{
		serverStatus.setStatus(ServerStatusEnum.RUN.getIndex());
	}
	
	/**
	 * 服务器状态
	 * @return
	 */
	public ServerStatus getServerStatus(){
		return serverStatus;
	}
}
