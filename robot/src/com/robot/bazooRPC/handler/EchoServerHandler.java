package com.robot.bazooRPC.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.robot.common.Globals;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	 private static final Logger logger = Loggers.gameLogger;
	 
     @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	 ctx.write(msg);
    	 
    	 ByteBuf result = (ByteBuf) msg;
    	 byte[] result1 = new byte[result.readableBytes()]; 
    	 result.readBytes(result1);  
         String resultStr = new String(result1);  
         TriggerRobotData data =JSON.parseObject(resultStr,TriggerRobotData.class);
         Globals.getBazooRobotService().openRobot(data);
         logger.info("server TriggerRobotData:" + data.getNumber()+"---"+data.getRoomNumber());
         
      }
  
      @Override
     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    	  logger.info("server channelReadComplete..");
          // 第一种方法：写一个空的buf，并刷新写出区域。完成后关闭sock channel连接。
          ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
          //ctx.flush(); // 第二种方法：在client端关闭channel连接，这样的话，会触发两次channelReadComplete方法。
          //ctx.flush().close().sync(); // 第三种：改成这种写法也可以，但是这中写法，没有第一种方法的好。
      }
  
      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	  logger.info("server occur exception:" + cause.getMessage());
          cause.printStackTrace();
          ctx.close(); // 关闭发生异常的连接
      }
}
