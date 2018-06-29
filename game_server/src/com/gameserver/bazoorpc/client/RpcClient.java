package com.gameserver.bazoorpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoorpc.handler.EchoClientHandler;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.gameserver.common.Globals;



/**
 * 
 * 这个主要用于远程开启机器人
 * @author JavaServer
 *
 */
public class RpcClient {
	private static final Logger logger = Loggers.gameLogger;
	private final String host;
    private final int port;
 
    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
	
    
    private EchoClientHandler handler = new EchoClientHandler();
    
	public void start() throws InterruptedException{
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group) // 注册线程池
			.channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
			.remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
			.handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					logger.info("connected...");
					ch.pipeline().addLast(handler);
				}
			});
			logger.info("created..");
			
			ChannelFuture cf = b.connect().sync(); // 异步连接服务器
			logger.info("connected..."); // 连接完成
			
			cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
			logger.info("closed.."); // 关闭完成
		} finally {
			group.shutdownGracefully().sync(); // 释放线程池资源
		}
	}
	

	public void setData(TriggerRobotData data) {
		this.handler.setData(data);
	}

	public static void startClient(TriggerRobotData data){
		
		try {
			RpcClient server = new RpcClient(Globals.getServerConfig().getHttpRobotAddress(),9999);
			server.setData(data);
			server.start();
		} catch (Exception e) {
			logger.info("开启Netty服务器失败："+e.getMessage());
		}
	}
}
