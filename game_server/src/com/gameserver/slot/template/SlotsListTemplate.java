package com.gameserver.slot.template;



import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;



@ExcelRowBinding
public class SlotsListTemplate  extends SlotsListTemplateVO{
	
	private List<Integer> betList = new ArrayList<Integer>();
	private List<Integer> reelNumList = new ArrayList<Integer>();
	
	public List<Integer> getBetList(){
		return this.betList;
	}
	
	public List<Integer> getReelNumList(){
		return this.reelNumList;
	}
	
	@Override
	public void check() throws TemplateConfigException
	{
		
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		this.betList.add(this.getBet1());
		this.betList.add(this.getBet2());
		this.betList.add(this.getBet3());
		this.betList.add(this.getBet4());
		this.betList.add(this.getBet5());
		
		this.reelNumList.add(this.reel1Num);
		this.reelNumList.add(this.reel2Num);
		this.reelNumList.add(this.reel3Num);
		this.reelNumList.add(this.reel4Num);
		this.reelNumList.add(this.reel5Num);
	
		
		int extraSize = this.reelNumList.size()-this.getColumns();
		while(extraSize>0){
			this.reelNumList.remove(this.reelNumList.size()-1);
			--extraSize;
		}
	}
}
