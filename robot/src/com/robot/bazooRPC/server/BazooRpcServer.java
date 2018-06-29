package com.robot.bazooRPC.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.robot.RobotBazooClassicalRemote;
import com.robot.bazooRPC.handler.EchoServerHandler;


public class BazooRpcServer {
	
	private static final Logger logger = Loggers.gameLogger;
	
	private final int port;
	
	public BazooRpcServer(int port) {
		this.port = port;
	}
	
	public void start() throws InterruptedException{
	
		 EventLoopGroup group = new NioEventLoopGroup();
	     try {
	        ServerBootstrap sb = new ServerBootstrap();
            sb.group(group) // 绑定线程池
              .channel(NioServerSocketChannel.class) // 指定使用的channel
              .localAddress(this.port)// 绑定监听端口
              .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
	
	               @Override
	               protected void initChannel(SocketChannel ch) throws Exception {
	                  logger.info("connected...; Client:" + ch.remoteAddress());
	                  ch.pipeline().addLast(new EchoServerHandler()); // 客户端触发操作
	               }
                 
             });
	         ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
	         logger.info(" started and listen on " + cf.channel().localAddress());
	         logger.info("----------------------[RPC]----------------OK-----------------");
	         cf.channel().closeFuture().sync(); // 关闭服务器通道
	     } finally {
	         group.shutdownGracefully().sync(); // 释放线程池资源
	     }
 	}
	
	public static void main(String[] args){
		try {
			RobotBazooClassicalRemote.startClassicalRobot();
			BazooRpcServer server = new BazooRpcServer(9999);
			server.start();
		} catch (Exception e) {
			logger.error("开启Netty服务器失败：",e);
		}
	}
}
