package com.gameserver.slot.template;



import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;


@ExcelRowBinding
public class PayTemplate  extends PayTemplateVO{
	
	/** 元素(图标)位置 **/
	private List<Integer> combinationList = new ArrayList<Integer>();
	/** 元素(图标) **/
	private List<SlotsTemplate> slotsTemplateList = new ArrayList<SlotsTemplate>();
	
	private SlotsListTemplate slotsListTemplate;
	
	public List<Integer> getCombinationList(){
		return this.combinationList;
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
	
		checkSlotTurn(this.combination1);
		checkSlotTurn(this.combination2);
		checkSlotTurn(this.combination3);
		checkSlotTurn(this.combination4);
		checkSlotTurn(this.combination5);
		
		int extraSize = combinationList.size()-this.slotsListTemplate.getColumns();
		while(extraSize>0){
			combinationList.remove(combinationList.size()-1);
			--extraSize;
		}
	}
	
	private void checkSlotTurn(int slotTurn){
		if(slotTurn==0)
			return;
		for(SlotsTemplate tempSlotsTemplate : Globals.getTemplateService().getAll(SlotsTemplate.class).values()){
			if(tempSlotsTemplate.getSlotsNum() == this.getSlotsNum() && tempSlotsTemplate.getTurn() == slotTurn){
				return;
			}
		}
		throw new TemplateConfigException(this.getSheetName(),this.getId(),"老虎机没有这个slot turn");
	}
	
	
	@Override
	public void patchUp() throws Exception 
	{
		
		this.slotsListTemplate= Globals.getTemplateService().get(this.getSlotsNum(), SlotsListTemplate.class);
		if(this.slotsListTemplate==null){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"没找到此类型老虎机");
		}
		
		
		this.combinationList.add(this.getCombination1());
		this.combinationList.add(this.getCombination2());
		this.combinationList.add(this.getCombination3());
		this.combinationList.add(this.getCombination4());
		this.combinationList.add(this.getCombination5());
		
		int extraSize = this.combinationList.size()-this.slotsListTemplate.getColumns();
		while(extraSize>0){
			this.combinationList.remove(this.combinationList.size()-1);
			--extraSize;
		}
		
		for(int i:this.combinationList){
			if(i==0)
				break;
			SlotsTemplate tempSlotsTemplate = this.getSlotsTemplateBySlotTurn(i);
			if(tempSlotsTemplate==null){
				throw new TemplateConfigException(this.getSheetName(),this.getId(),"老虎机没有这个slot turn");
			}
			this.slotsTemplateList.add(tempSlotsTemplate);
		}
	}
	
	private SlotsTemplate getSlotsTemplateBySlotTurn(int slotTurn){
		for(SlotsTemplate tempSlotsTemplate : Globals.getTemplateService().getAll(SlotsTemplate.class).values()){
			if(tempSlotsTemplate.getSlotsNum() == this.getSlotsNum() && tempSlotsTemplate.getTurn() == slotTurn){
				return tempSlotsTemplate;
			}
		}
		return null;
		
	}
}
