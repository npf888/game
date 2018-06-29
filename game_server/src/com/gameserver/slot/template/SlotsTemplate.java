package com.gameserver.slot.template;



import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;
import com.gameserver.slot.enums.SlotElementType;


@ExcelRowBinding
public class SlotsTemplate  extends SlotsTemplateVO{
	
	private SlotElementType slotElementType;
	private SlotsListTemplate slotsListTemplate;
	
	public SlotElementType getSlotElementType(){
		return this.slotElementType;
	}
	
	/**
	 * @return the slotsListTemplate
	 */
	public SlotsListTemplate getSlotsListTemplate() {
		return slotsListTemplate;
	}

	

	@Override
	public void check() throws TemplateConfigException
	{

	}
	
	@Override
	public void patchUp() throws Exception 
	{
		this.slotsListTemplate= Globals.getTemplateService().get(this.getSlotsNum(), SlotsListTemplate.class);
		this.slotElementType = SlotElementType.indexOf(this.getType());
		if(this.slotsListTemplate==null){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"没找到此类型老虎机");
		}
		
		if(this.slotElementType==null){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"slot元素类型不存在");
		}
	}
}
