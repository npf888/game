package com.gameserver.slot;

import com.core.schedule.LLISchedule;

/**
 * 每隔一定的时间刷新一次 打开面板的用户的数据
 * @author JavaServer
 *
 */
public class TournamentRefresh implements LLISchedule {

	private TournamentService tournamentService;
	public TournamentRefresh(TournamentService tournamentService){
		this.tournamentService =tournamentService;
	}
	@Override
	public void execute() {
		tournamentService.refreshTournament();
	}

}
