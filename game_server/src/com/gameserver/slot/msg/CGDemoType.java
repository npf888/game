package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 老虎机特效
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDemoType extends CGMessage{
	
	/** 1:3个bonus 2 :4个bonus 3 :5个bonus 4:3个scatter 5:4个scatter 6:5个scatter 7:一列大号wild 8:2列大号wild 9:一列小号wild 10:2列小号wild 11:第一行随机出现相同元素 12:第一行出现3个jackpot 13:随机出现4个jackpot 14:随机出现5个jackpot 15: 随机出现6个jackpot 16: 随机出现7个jackpot 200:增加1个亿 */
	private int demoType;
	
	public CGDemoType (){
	}
	
	public CGDemoType (
			int demoType ){
			this.demoType = demoType;
	}
	
	@Override
	protected boolean readImpl() {
		demoType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(demoType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DEMO_TYPE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DEMO_TYPE";
	}

	public int getDemoType(){
		return demoType;
	}
		
	public void setDemoType(int demoType){
		this.demoType = demoType;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleDemoType(this.getSession().getPlayer(), this);
	}
}