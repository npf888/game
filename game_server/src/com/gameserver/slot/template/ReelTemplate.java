package com.gameserver.slot.template;



import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;


@ExcelRowBinding
public class ReelTemplate  extends ReelTemplateVO{
	
	/** 包含元素(图标)ID **/
	private List<Integer> slotList = new ArrayList<Integer>();
	
	/**Reel所包含的元素(图标) **/
	private List<SlotsTemplate> slotsTemplateList = new ArrayList<SlotsTemplate>();
	
	/** 所属老虎机 **/
	private SlotsListTemplate slotsListTemplate;
	
	public List<Integer> getSlotList(){
		return this.slotList;
	}
	
	public List<SlotsTemplate> getSlotsTemplateList(){
		return this.slotsTemplateList;
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
	
	/**
	 * 获取元素
	 * @param slotTurn
	 * @return
	 */
	private SlotsTemplate getSlotsTemplateBySlotTurn(int slotTurn){
		for(SlotsTemplate tempSlotsTemplate : Globals.getTemplateService().getAll(SlotsTemplate.class).values()){
			if(tempSlotsTemplate.getSlotsNum() == this.getSlotsNum() && tempSlotsTemplate.getTurn() == slotTurn){
				return tempSlotsTemplate;
			}
		}
		return null;
		
	}
	
	@Override
	public void patchUp() throws Exception {
		//对应老虎机数据
		this.slotsListTemplate= Globals.getTemplateService().get(this.getSlotsNum(), SlotsListTemplate.class);
		if(this.slotsListTemplate==null){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"没找到此类型老虎机");
		}
		
		this.slotList.add(this.reel1);
		this.slotList.add(this.reel2);
		this.slotList.add(this.reel3);
		this.slotList.add(this.reel4);
		this.slotList.add(this.reel5);
		
		int extraSize = this.slotList.size()-this.slotsListTemplate.getColumns();
		while(extraSize>0){
			this.slotList.remove(this.slotList.size()-1);
			--extraSize;
		}
		
		for(int i:this.slotList){
			SlotsTemplate tempSlotsTemplate = getSlotsTemplateBySlotTurn(i);
			if(tempSlotsTemplate==null){
				throw new TemplateConfigException(this.getSheetName(),this.getId(),"老虎机没有这个slot turn");
			}
			this.slotsTemplateList.add(tempSlotsTemplate);
		}
	
		
	}
}
