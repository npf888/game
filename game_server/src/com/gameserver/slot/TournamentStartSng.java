package com.gameserver.slot;

import com.core.schedule.LLISchedule;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.TournamentTemplate;

/**
 * 比赛结束
 * @author 郭君伟
 *
 */
public class TournamentStartSng implements LLISchedule {
	
    private TournamentTemplate template;
	
	public TournamentStartSng(TournamentTemplate template){
		this.template = template;
	}

	@Override
	public void execute() {
		
        Globals.getTournamentService().closeSng(template);
        
	}

}
