package com.gameserver.baccart.template;

import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class JackpotTemplate extends JackpotTemplateVO {
	
	private Integer[] chanceArr = new Integer[3];
	private Integer[] ratioArr = new Integer[3];
	private List<Integer> reviveCostList = new ArrayList<Integer>();
	
	@Override
	public void check() throws TemplateConfigException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		this.chanceArr[0]= this.chance1;
		this.chanceArr[1] = this.chance2;
		this.chanceArr[2] = this.chance3;
		this.ratioArr[0] = this.ratio1;
		this.ratioArr[1] = this.ratio2;
		this.ratioArr[2] = this.ratio3;
		
		reviveCostList.add(this.reviveNum1);
		reviveCostList.add(this.reviveNum2);
		reviveCostList.add(this.reviveNum3);
		reviveCostList.add(this.reviveNum4);
		reviveCostList.add(this.reviveNum5);
		reviveCostList.add(this.reviveNum6);
		reviveCostList.add(this.reviveNum7);
		reviveCostList.add(this.reviveNum8);
		reviveCostList.add(this.reviveNum9);
	
	}
	
	public Integer[] getChanceArr(){
		return this.chanceArr;
	}
	
	public Integer[] getRatioArr(){
		return this.ratioArr;
	}
	
	public List<Integer> getReviveCostList(){
		return reviveCostList;
	}
}
