package com.gameserver.bazoorpc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private static final Logger logger = Loggers.gameLogger;
	
	TriggerRobotData data = null;
     @Override
     public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	 logger.info("client channelActive..");
    	/* TriggerRobotData data = new TriggerRobotData(); 
    	 data.setNumber(3);
    	 data.setRoomNumber("1_1_50_1");*/
    	 String dataString = JSON.toJSONString(data);
         ctx.writeAndFlush(Unpooled.copiedBuffer(dataString, CharsetUtil.UTF_8)); // 必须有flush        
 
         // 必须存在flush
         // ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
         // ctx.flush();
     }
 
     @Override
     protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    	 logger.info("client channelRead..");
         ByteBuf buf = msg.readBytes(msg.readableBytes());
         logger.info("Client received:" + ByteBufUtil.hexDump(buf) + "; The value is:" + buf.toString(Charset.forName("utf-8")));
         
         String dataString = buf.toString(Charset.forName("utf-8"));
         TriggerRobotData rData = JSON.parseObject(dataString, TriggerRobotData.class);
         
         logger.info("服务器返回值：" + rData.getNumber()+"---"+rData.getRoomNumber());
         
         //ctx.channel().close().sync();// client关闭channel连接
     }
 
     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
     }
	 

     public void setData(TriggerRobotData data){
    	 this.data = data; 
     }
}
