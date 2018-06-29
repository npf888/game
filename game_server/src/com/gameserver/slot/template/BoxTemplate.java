package com.gameserver.slot.template;



import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;


@ExcelRowBinding
public class BoxTemplate  extends BoxTemplateVO{
	
	private static final float RATE = 100.0f;
	private float rate;
	
	@Override
	public void check() throws TemplateConfigException
	{
		
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		this.rate = this.pay/RATE;
		
	}

	/**
	 * @return the rate
	 */
	public float getRate() {
		return rate;
	}


}
