package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 博趣平台入口 返回页面（前端直接访问）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooBoqu extends GCMessage{
	
	/** base64编码之后的带参数的页面 */
	private String htmlPage;

	public GCBazooBoqu (){
	}
	
	public GCBazooBoqu (
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
		return MessageType.GC_BAZOO_BOQU;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_BOQU";
	}

	public String getHtmlPage(){
		return htmlPage;
	}
		
	public void setHtmlPage(String htmlPage){
		this.htmlPage = htmlPage;
	}
}