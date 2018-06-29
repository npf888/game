package com.gameserver.slot;

import java.util.List;

import com.core.schedule.LLISchedule;
import com.core.schedule.LLScheduleEnum;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.TournamentTemplate;

/**
 * 开启新一轮竞赛
 * @author 郭君伟
 *
 */
public class TournamentStart implements LLISchedule {
	
	private TournamentTemplate template;
	
	public TournamentStart(TournamentTemplate template){
		this.template = template;
	}

	@Override
	public void execute() {
		
		List<Integer> list = template.getDurationList();
		
		int durationTime = list.get(RandomUtil.nextInt(0, list.size()));
		
		long timeBlock = durationTime*1000;
		
		TournamentService service = Globals.getTournamentService();
		long now = Globals.getTimeService().now();
		//比赛开始
		service.openSng(template,timeBlock+now);
		
		//比赛结束
		Globals.getScheduleService().scheduleOnce(new TournamentStartSng(template), LLScheduleEnum.SLOT_SNG_RANK_START, timeBlock);
	}

}
