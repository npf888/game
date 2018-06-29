package com.gameserver.slot.template;

import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;


@ExcelRowBinding
public class PaylinesTemplate  extends PaylinesTemplateVO{
	
	/** 位置组合  **/
	private List<Integer> positionList = new ArrayList<Integer>();
	
	public List<Integer> getPositionList(){
		return this.positionList;
	}
	
	@Override
	public void check() throws TemplateConfigException
	{
		
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		positionList.add(this.position1);
		positionList.add(this.position2);
		positionList.add(this.position3);
		positionList.add(this.position4);
		positionList.add(this.position5);
		
	}
}
