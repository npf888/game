package com.core.codec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.core.codec.WSToolKit.WSSessionState;
import com.core.msg.IMessage;


/**
 * websocket 编码
 * @author JavaServer
 *
 */
public class WebSocketEncoder extends ProtocolEncoderAdapter {
	
	protected static final SerializerFeature[] features = {
		SerializerFeature.DisableCircularReferenceDetect,//打开循环引用检测，JSONField(serialize = false)不循环
		SerializerFeature.WriteMapNullValue, //输出空置字段
		SerializerFeature.WriteNullListAsEmpty,//list字段如果为null，输出为[]，而不是null
		SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
		SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null，输出为false，而不是null
		SerializerFeature.WriteNullStringAsEmpty,//字符类型字段如果为null，输出为""，而不是null
		};
	
	
	private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;  
	  
	
    public void setByteOrder(ByteOrder byteOrder) {  
        this.byteOrder = byteOrder;  
    }  
  
    private CharsetEncoder charsetEncoder = Charset.forName("utf-8")  
            .newEncoder();  
  
    public void setCharsetEncoder(CharsetEncoder charsetEncoder) {  
        this.charsetEncoder = charsetEncoder;  
    }  
  
    private int defaultPageSize = 65536;  
  
    public void setDefaultPageSize(int defaultPageSize) {  
        this.defaultPageSize = defaultPageSize;  
    }  
      
    // 返回的数据默认不必进行掩码运算。  
    private boolean isMasking = false;  
    public void setIsMasking(boolean masking) {  
        this.isMasking = masking;  
    }  
  
    @Override  
    public void encode(IoSession session, Object message, ProtocolEncoderOutput encoderOutput) throws CharacterCodingException, UnsupportedEncodingException {  
        ByteBuffer buff = ByteBuffer.allocate(1024).setAutoExpand(true);  
  
        WSSessionState status = WSToolKit.getSessionState(session);  
          
        switch (status) {  
            case Handshake:  
                try {  
                    buff.putString((String) message, charsetEncoder)  
                        .flip();  
  
                    encoderOutput.write(buff);  
                } catch (CharacterCodingException e) {  
                    session.close();  
                }  
  
                session.setAttribute(WSSessionState.ATTRIBUTE_KEY, WSSessionState.Connected);  
                break;  
      
            case Connected:  
                if (!session.isConnected() || message == null)  
                    return;  
                IMessage im = (IMessage)message;
                short type = im.getType();
//                String typeStr = "{\"type\":\""+type+"\"}";
//                //转成json的方式
//                String jsonStr = JSONObject.toJSONString(message);
                byte[] bb = encode(getJson(type,message)); 
                
                // 将数据统一转换成byte数组进行处理。  
                //原先的方式
//                byte[] bb = encode(message.toString()); 
                
                ByteBuffer buffer = ByteBuffer.allocate(bb.length); 
                buffer.put(bb);  
                buffer.flip();  
                if (session.isConnected())  
                {  
                    session.write(buffer);  
                }  
        }  
    }  
    
    
    
 // / 对传入数据进行无掩码转换  
    public byte[] encode(String msg) throws UnsupportedEncodingException {  
        // 掩码开始位置  
        int masking_key_startIndex = 2;  
  
        byte[] msgByte = msg.getBytes("UTF-8");  
  
        // 计算掩码开始位置  
        if (msgByte.length <= 125) {  
            masking_key_startIndex = 2;  
        } else if (msgByte.length > 65536) {  
            masking_key_startIndex = 10;  
        } else if (msgByte.length > 125) {  
            masking_key_startIndex = 4;  
        }  
  
        // 创建返回数据  
        byte[] result = new byte[msgByte.length + masking_key_startIndex];  
  
        // 开始计算ws-frame  
        // frame-fin + frame-rsv1 + frame-rsv2 + frame-rsv3 + frame-opcode  
        result[0] = (byte) 0x81; // 129  
  
        // frame-masked+frame-payload-length  
        // 从第9个字节开始是 1111101=125,掩码是第3-第6个数据  
        // 从第9个字节开始是 1111110>=126,掩码是第5-第8个数据  
        if (msgByte.length <= 125) {  
            result[1] = (byte) (msgByte.length);  
        } else if (msgByte.length > 65536) {  
            result[1] = 0x7F; // 127  
        } else if (msgByte.length > 125) {  
            result[1] = 0x7E; // 126  
            result[2] = (byte) (msgByte.length >> 8);  
            result[3] = (byte) (msgByte.length % 256);  
        }  
  
        // 将数据编码放到最后  
        for (int i = 0; i < msgByte.length; i++) {  
            result[i + masking_key_startIndex] = msgByte[i];  
        }  
          
        return result;  
    }  
    
    private String getJson(short type,Object message){
    	String jsonStr = JSONObject.toJSONString(message);
    	JSONObject messageJSON = JSONObject.parseObject(jsonStr,Feature.IgnoreNotMatch);
    	messageJSON.remove("initBufferLength");
    	messageJSON.remove("length");
    	messageJSON.remove("type");
    	messageJSON.remove("typeName");
    	JSONObject json = new JSONObject();
    	json.put("type", type);
    	json.put("data", messageJSON);
    	return json.toJSONString();
    }
	
    
   /* public static void main(String[] args){
    	String jsonString = "{name:'Antony',age:'12',sex:'male',telephone:'88888'}";
    	
//    	Staff message = JSONObject.parseObject(jsonString, Staff.class);
    	Staff message = new Staff();
    	message.setAge(12);
    	message.setName("");
    	message.setSex(null);
    	message.setTelephone(null);
    	String messageStr = JSONObject.toJSONString(message,features);
    	JSONObject messageJSON = JSON.parseObject(messageStr,Feature.IgnoreNotMatch);
    	System.out.println(messageJSON);
    }*/
}


		 
