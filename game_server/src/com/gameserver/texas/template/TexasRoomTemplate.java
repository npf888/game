package com.gameserver.texas.template;

import com.common.exception.TemplateConfigException;
import com.common.model.Card;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class TexasRoomTemplate extends TexasRoomTemplateVO{
	
	private Card.CardValueEnum smallCardValue;

	@Override
	public void check() throws TemplateConfigException
	{
		// TODO Auto-generated method stub
		if(this.getRoomNum()!=6 && this.getRoomNum()!=9)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getRoomNum(),"房间人数不是6人也不是9人");
		}
		
		if(this.getMinCarry()<=this.getSmallBlind()*2)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getMinCarry(),"最小携带量必须大于大盲注");
		}
		
		smallCardValue = Card.CardValueEnum.valueOf(this.getSmallCard());
		if(smallCardValue==null){
			throw new TemplateConfigException(this.getSheetName(),this.getSmallCard(),"最小牌不存在");
		}
		
		if(this.getSuitNum() >= this.getRoomNum()){
			throw new TemplateConfigException(this.getSheetName(),this.getSuitNum(),"最合适人不能大于人数");
		}
		
		if(this.getSuitNum() < 2){
			throw new TemplateConfigException(this.getSheetName(),this.getSuitNum(),"最合适人不能小于2");
		}
		
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}
	
	public Card.CardValueEnum getSmallCardValue(){
		return this.smallCardValue;
	}
}
