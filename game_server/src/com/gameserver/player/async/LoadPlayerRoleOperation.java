package com.gameserver.player.async;

import org.slf4j.Logger;

import com.common.constants.DisconnectReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.util.LogUtils;
import com.db.dao.HumanDao;
import com.db.model.HumanEntity;
import com.game.webserver.login.ResetUpdateFbInfoParams;
import com.gameserver.club.ClubService;
import com.gameserver.club.ClubZhiwu;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.consts.ClubConsts;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanInitManager;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.PlayerState;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 加载角色所有数据的异步操作
 * @author Thinker
 * 
 */
public class LoadPlayerRoleOperation implements IIoOperation
{
	private static Logger logger = Loggers.playerLogger;
	private final Player player;
	private long roleUUID;
	/** 是否数据库操作成功 */
	private boolean isOperateSucc = false;

	public LoadPlayerRoleOperation(Player player, long roleUUID)
	{
		this.player = player;
		this.roleUUID = roleUUID;
	}

	@Override
	public int doIo() 
	{
		do
		{
			try 
			{
				logger.info("player " + player.getPassportName() + " loadAccountInfo");
				
				
				HumanDao _roleInfoDao = Globals.getDaoService().getHumanDao();
				HumanEntity entity = _roleInfoDao.get(roleUUID);
				
				logger.info(player.getPassportId()+">>>"+player.isUpdateForFb());
				if(player.isUpdateForFb())
				{
//					entity.setImg(player.getImg());
//					entity.setName(player.getPassportName());
					_roleInfoDao.saveOrUpdate(entity);
					
					ResetUpdateFbInfoParams resetUpdateFbInfoParams = Globals.getSynLoginServerHandler()
							.resetUpdateFbInfo(player.getPassportId());
					if (resetUpdateFbInfoParams.getErrorCode() == 0) {
						logger.info("successfully updateFbInfo for: " + player.getPassportId());
					} else {
						logger.info("failed updateFbInfo for: " + player.getPassportId());
					}
				}
				
				
				long passportId = entity.getPassportId();
				player.setPassportId(passportId);
				entity.setImg(player.getImg());
				// 首先设置player,因为在之后的执行中可能会用到player
				logger.info("player " + player.getPassportName() + " human.fromEntity(entity)");
				Human human = new Human(player);
				human.fromEntity(entity);
				human.setName(player.getPassportName());
				
				String countries = entity.getCountries();
				if(countries == null || countries.trim().equals("")){
					human.setCountries(player.getCountries());
				}else{
					human.setCountries(countries);
				}
				
				player.setHuman(human);
				
//				human.getHumanTexasManager().load();
				human.getHumanBagManager().load();
//				human.getHumanMonthCardManager().load();
//				human.getHumanWeekCardManager().load();
				human.getHumanRelationManager().load();
//				human.getHumanWeekSignInManager().load();
				human.getHumanMailManager().load();
				human.getHumanMiscManager().load();
//				human.getHumanVipManager().load();
//				human.getHumanVipNewManager().load();
				human.getHumanRechargeManager().load();
//				human.getHumanTaskManager().load();
//				human.getHumanActivityManager().load();
				human.getHumanCompensationManager().load();
//				human.getHumanRefundManager().load();
//				human.getHumanBaccartManager().load();
//				human.getHumanLuckySpinManager().load();
//				human.getHumanSlotManager().load();
//				human.getHumanGiftManager().load();
//				human.getHumanAchievementManager().load();
//				human.getHumanCollectManager().load();
//				human.getHumanTreasuryManager().load();
//				human.getHumanAttackBossManager().load();
				isOperateSucc = true;
				_roleInfoDao.update(entity);
				ClubService.clubOperationLock.lock();
				try {
					String clubId = human.getClubId();
					ClubMemberData cmd = ClubCache.retrieveMemberIfExist(clubId, passportId);
					if(cmd!=null && cmd.getZhiwu()==ClubZhiwu.ZHUXI.getLevel() && cmd.getTanheState()==ClubConsts.STATE_TANHE_CAN)
					{
						cmd.setTanheState(ClubConsts.STATE_TANHE_CANNOT);
						ClubCache.putClubMember(clubId, cmd);
					}
				} finally {
					ClubService.clubOperationLock.unlock();
				}
				
			} catch (Exception e) 
			{
				isOperateSucc = false;
				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "#GS.CharacterLoad.doIo"), e);
			}
		} while (false);
		return STAGE_IO_DONE;
	}
	
	
 
	@Override
	public int doStart()
	{			
		return STAGE_START_DONE;
	}

	@Override
	public int doStop()
	{
		final Human human = player.getHuman();
		try
		{
			logger.info("player " + player.getPassportName() + " doStop");			
			if (player.getState() == PlayerState.logouting || !isOperateSucc || human == null)
			{				
				player.sendMessage(new GCNotifyException(
						DisconnectReason.FINISH_LOAD_HUMAN_EXCEPTION.getIndex(), ""));
				player.exitReason = PlayerExitReason.SERVER_ERROR;
				player.disconnect();
			} else 
			{
				logger.info(player.getHuman().getName()+" in select role and will humanlogin");
				HumanInitManager.getInstance().humanLogin(human);
			    SceneHandlerFactory.getHandler().handleEnterScene(player.getId(),player.getTargetSceneId());
			  
				
			}
		} catch (Exception e) 
		{
			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "#GS.CharacterLoad.doIo"), e);
			player.sendMessage(new GCNotifyException(
					DisconnectReason.FINISH_LOAD_HUMAN_EXCEPTION.getIndex(), Globals.getLangService().readSysLang(LangConstants.LOAD_PLAYER_SELECTED_ROLE)));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
		}
		return STAGE_STOP_DONE;
	}
}
