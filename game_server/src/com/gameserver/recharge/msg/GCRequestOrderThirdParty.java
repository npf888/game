package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 博趣 第三方请求订单的返回页面（前端直接访问）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRequestOrderThirdParty extends GCMessage{
	
	/** base64编码之后的带参数的页面 */
	private String htmlPage;

	public GCRequestOrderThirdParty (){
	}
	
	public GCRequestOrderThirdParty (
			String htmlPage ){
			this.htmlPage = htmlPage;
	}

	@Override
	protected boolean readImpl() {
		htmlPage = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(htmlPage);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REQUEST_ORDER_THIRD_PARTY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REQUEST_ORDER_THIRD_PARTY";
	}

	public String getHtmlPage(){
		return htmlPage;
	}
		
	public void setHtmlPage(String htmlPage){
		this.htmlPage = htmlPage;
	}
}