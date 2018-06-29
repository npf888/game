package com.gameserver.achievement.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.achievement.enums.ParamType;

@ExcelRowBinding
public class AchievementTemplate extends AchievementTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		if(paramType == ParamType.ParamType2.getIndex()){
			if(condition1 == 0 || condition2 == 0){
				throw new TemplateConfigException(this.getSheetName(),this.id,"参数不对");
			}
		} 
	}

	@Override
	public void patchUp() throws Exception {
		
	}

	
}
