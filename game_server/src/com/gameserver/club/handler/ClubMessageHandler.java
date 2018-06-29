package com.gameserver.club.handler;

import static com.gameserver.club.ClubZhiwu.CHENGYUAN;
import static com.gameserver.club.ClubZhiwu.ZHUXI;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.core.async.AsyncIoOpeOnly;
import com.db.dao.ClubApplyDao;
import com.db.dao.HumanDao;
import com.db.model.ClubApplyEntity;
import com.db.model.HumanEntity;
import com.gameserver.club.ClubRight;
import com.gameserver.club.ClubService;
import com.gameserver.club.ClubTemplateService;
import com.gameserver.club.ClubType;
import com.gameserver.club.ClubZhiwu;
import com.gameserver.club.msg.CGClubApplyList;
import com.gameserver.club.msg.CGClubApplyOp;
import com.gameserver.club.msg.CGClubChangeName;
import com.gameserver.club.msg.CGClubCreate;
import com.gameserver.club.msg.CGClubDonate;
import com.gameserver.club.msg.CGClubEdit;
import com.gameserver.club.msg.CGClubGetGift;
import com.gameserver.club.msg.CGClubInfoGet;
import com.gameserver.club.msg.CGClubInvate;
import com.gameserver.club.msg.CGClubInvateList;
import com.gameserver.club.msg.CGClubJoin;
import com.gameserver.club.msg.CGClubJoin2;
import com.gameserver.club.msg.CGClubKick;
import com.gameserver.club.msg.CGClubLeave;
import com.gameserver.club.msg.CGClubNotJoin;
import com.gameserver.club.msg.CGClubNoteDelete;
import com.gameserver.club.msg.CGClubNotePanel;
import com.gameserver.club.msg.CGClubNoteSend;
import com.gameserver.club.msg.CGClubNoteSendGift;
import com.gameserver.club.msg.CGClubPanel;
import com.gameserver.club.msg.CGClubPromate;
import com.gameserver.club.msg.CGClubRankingList;
import com.gameserver.club.msg.CGClubSearch;
import com.gameserver.club.msg.CGClubSign;
import com.gameserver.club.msg.CGClubTanhe;
import com.gameserver.club.msg.CGClubTanheState;
import com.gameserver.club.msg.CGClubTanheVote;
import com.gameserver.club.msg.GCClubApplyList;
import com.gameserver.club.msg.GCClubApplyOp;
import com.gameserver.club.msg.GCClubChangeName;
import com.gameserver.club.msg.GCClubDonate;
import com.gameserver.club.msg.GCClubEdit;
import com.gameserver.club.msg.GCClubGetGift;
import com.gameserver.club.msg.GCClubInvate;
import com.gameserver.club.msg.GCClubInvateList;
import com.gameserver.club.msg.GCClubJoin2;
import com.gameserver.club.msg.GCClubJoinResult;
import com.gameserver.club.msg.GCClubKick;
import com.gameserver.club.msg.GCClubLeave;
import com.gameserver.club.msg.GCClubList;
import com.gameserver.club.msg.GCClubMemberList;
import com.gameserver.club.msg.GCClubNoteDelete;
import com.gameserver.club.msg.GCClubNoteList;
import com.gameserver.club.msg.GCClubPromate;
import com.gameserver.club.msg.GCClubRankingList;
import com.gameserver.club.msg.GCClubSearchResult;
import com.gameserver.club.msg.GCClubSign;
import com.gameserver.club.msg.GCClubTanhe;
import com.gameserver.club.msg.GCClubTanheState;
import com.gameserver.club.msg.GCClubTanheVote;
import com.gameserver.club.msg.utils.ClubMessageUtils;
import com.gameserver.club.protocol.ClubApplyUnit;
import com.gameserver.club.protocol.ClubInvateUnit;
import com.gameserver.club.protocol.ClubListUnit;
import com.gameserver.club.protocol.ClubMemberListUnit;
import com.gameserver.club.redis.BoardMsgData;
import com.gameserver.club.redis.ClubApplyData;
import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.club.template.ClubRechargeTemplate;
import com.gameserver.club.template.ClubTemplate;
import com.gameserver.common.Globals;
import com.gameserver.consts.ClubConsts;
import com.gameserver.consts.MessageCode;
import com.gameserver.currency.Currency;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.Human;
import com.gameserver.mail.MailLogic;
import com.gameserver.mail.enums.MailTypeEnum;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.redis.enums.RedisKey;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class ClubMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClubMessageHandler.class);

    private boolean checkHumanLevel(Human human)
    {
    	if (human.getLevel() < ClubTemplateService.UNLOCK.getClubunlock()) {
            logger.error("玩家passportId: " + human.getPassportId()+"等级 不到"+ClubTemplateService.UNLOCK.getClubunlock());
            return false;
        }
    	return true;
    }
    /**
     * 打开面板
     *
     * @param playerd
     * @param cgClubPanel
     */
    public void handleClubPanel(Player player, CGClubPanel cgClubPanel) {
    	ClubService.clubOperationLock.lock();
        try
        {
        	Human human = player.getHuman();
            if(!checkHumanLevel(human))return;

            String clubId = human.getClubId();
            ClubData cd = ClubCache.getClubs(clubId);
            if (cd != null) {
                // 已经加入直接发消息
            	Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
            	ClubMemberData cmd = map.get(human.getPassportId());
                player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
                player.sendMessage(cd.buildGCClubMemberList());
                player.sendMessage(ClubMessageUtils.getGCClubSignData(cd.getLevel(), human.getClubSignTs()));
                player.sendMessage(ClubMessageUtils.getGCClubDonateData(cd.getLevel(), human.getClubDonateTs()));
                return;
            }
            // 返回50条数据
            Jedis jedis = null;
            try {
            	jedis = Globals.getRedisService().getJedisPool().getResource();
                Set<String> ranks = jedis.zrange(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, 0, 49);
                ClubListUnit[] units = new ClubListUnit[ranks.size()];
                int i = 0;
                for(String cid : ranks)
                {
                	ClubData x = ClubCache.getClubs(cid);
                	 units[i++] = ClubListUnit.toProtocol(x);
                }
                GCClubList gCClubList = new GCClubList();
                gCClubList.setList(units);
                player.sendMessage(gCClubList);
    		} finally {
    			if(jedis!=null) {jedis.close();}
    		}
        }
        finally {
        	ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 创建俱乐部
     *
     * @param player
     * @param cgClubCreate
     */
    public void handleClubCreate(Player player, CGClubCreate cgClubCreate) {
    	ClubService.clubOperationLock.lock();
        try
        {
        	Human human = player.getHuman();

    		if (StringUtils.isNotEmpty(human.getClubId())) {
    			logger.error("passportId: " + human.getPassportId() + " will create club with clubId: " + human.getClubId());
    			human.sendSystemMessage(LangConstants.CLUB_ALREADY_IN);
    			return;
    		}

    		if(!checkHumanLevel(human))return;

            boolean hasDirtyWords = Globals.getWordFilterService()
                    .containKeywords(cgClubCreate.getName() + cgClubCreate.getNotice());

            if (hasDirtyWords == true) {
                human.sendSystemMessage(LangConstants.CLUB_NAME_ERROR);
                logger.error("玩家passportId: " + human.getPassportId()+"创建俱乐部名字非法=>name: "+cgClubCreate.getName()+", notice: "+cgClubCreate.getNotice());
                return;
            }
            
            ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(1);
            int nameLimit = ct.getClubName();
            int descLimit = ct.getClubDescribe();
            int cost = Globals.getClubTemplateService().getClubTemplateByLevel(1).getClubFound();
            if (cgClubCreate.getName().length() > nameLimit) {
                human.sendSystemMessage(LangConstants.CLUB_NAME_TOO_LONG);
                logger.error("玩家passportId: " + human.getPassportId()+" 创建俱乐部 名字太长");
                return;
            }

            if (cgClubCreate.getNotice().length() > descLimit) {
                human.sendSystemMessage(LangConstants.CLUB_DESC_ERROR);
                logger.error("玩家passportId: " + human.getPassportId()+" 创建俱乐部 描述太长");
                return;
            }

            ClubType type = ClubType.from(cgClubCreate.get_type());
            if (type == ClubType.TYPE_NONE) {
                human.sendSystemMessage(LangConstants.CLUB_TYPE_ERROR);
                logger.error("玩家passportId: " + human.getPassportId()+" 创建俱乐部 类型不符");
                return;
            }

            if (!human.hasEnoughMoney(cost, Currency.GOLD)) {
            	logger.error("玩家passportId: " + human.getPassportId()+" 创建俱乐部 消耗不符");
                return;
            }

            Jedis j = null;
            try {
            	j = Globals.getRedisService().getJedisPool().getResource();
                String clubName = cgClubCreate.getName();
                if (j.hexists(RedisKey.CLUB_NAME_HASH, clubName)) {
                    logger.error("passportId: " + human.getPassportId() + "创建俱乐部 名字重复 "+clubName);
                    human.sendSystemMessage(LangConstants.CLUB_NAME_ERROR);
                    return;
                }
                human.costMoney(cost, Currency.GOLD, true, LogReasons.GoldLogReason.CLUB_CREATE, LogReasons.GoldLogReason.CLUB_CREATE.getReasonText(), -1, 1);
                ClubData cd = new ClubData();
                cd.setId(UUID.randomUUID().toString());
                cd.setIco(cgClubCreate.getIco());
                cd.setDuanweiLimit(cgClubCreate.getLimit());
        		cd.setClub_type(cgClubCreate.get_type());
        		cd.setCreate_time(new Date());
        		cd.setHuoyue(0);
        		cd.setMoney(0);
        		cd.setLevel(1);
        		cd.setName(clubName);
        		cd.setNotice(cgClubCreate.getNotice());
        		ClubCache.putClub(cd.getId(), cd);
        		j.hset(RedisKey.CLUB_NAME_HASH, clubName, "");
        		j.zadd(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, cd.calculateScoreForHuoYue(), cd.getId());
        		j.zadd(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, cd.calculateScoreForGongXian(), cd.getId());
        		ClubMemberData cmd = new ClubMemberData();
        		cmd.setId(human.getPassportId());
//        		cmd.setClubId(cd.getId());
        		cmd.setSignTime(0);
        		cmd.setDonateTime(0);
        		long now = System.currentTimeMillis();
        		cmd.setJoinTime(now);
        		cmd.setZhiwu(ZHUXI.getLevel());
        		cmd.setGongxian(0);
        		cmd.setHuoyue(0);
        		cmd.setSex(human.getGirl());
        		cmd.setCountries(human.getCountries());
        		cmd.setLevel((int)human.getLevel());
        		cmd.setVipLevel(human.getHumanVipNewManager().getVipLv());
        		cmd.setName(human.getName());
        		cmd.setOfflineTime(now);
        		cmd.setImg(human.getImg());
        		cmd.setIsOnline(1);
        		human.setClubId(cd.getId());
        		human.setModified();
        		ClubCache.putClubMember(cd.getId(), cmd);

        		player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
                player.sendMessage(cd.buildGCClubMemberList());
                player.sendMessage(ClubMessageUtils.getGCClubSignData(cd.getLevel(), human.getClubSignTs()));
                player.sendMessage(ClubMessageUtils.getGCClubDonateData(cd.getLevel(), human.getClubDonateTs()));
    		} finally {
    			if(j!=null) {j.close();}
    		}
        }
        finally {
        	ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 签到
     *
     * @param player
     * @param cgClubSign
     */
    public void handleClubSign(Player player, CGClubSign cgClubSign) {
    	ClubService.clubOperationLock.lock();
    	try {
    		Human human = player.getHuman();
            if(!checkHumanLevel(human))return;
            
            ClubData cd = ClubCache.getClubs(human.getClubId());
            if (cd == null) {
            	logger.error("passportId: " + human.getPassportId() + "will sign without clubId");
                return;
            }
            ClubMemberData cmd = ClubCache.getClubMembersOfClub(cd.getId()).get(human.getPassportId());

//            ClubMemberModel clubMemberModel = human.getHumanClubManager().getClubMemberModel();
            long now = System.currentTimeMillis();
            long intelval = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getSignTime() * 60 * 60 * 1000L;
            int addHuoyue = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getSignActive();
            if (now - human.getClubSignTs() < intelval) {
            	human.sendSystemMessage(LangConstants.CLUB_DONATE_EARLY);
                return;
            }

            cd.setHuoyue(cd.getHuoyue() + addHuoyue);
            ClubCache.putClub(cd.getId(), cd);
            Jedis j = null;
            try {
            	j = Globals.getRedisService().getJedisPool().getResource();
                j.zincrby(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, -addHuoyue*ClubConsts.integer_coefficient, cd.getId());
    		} finally {
    			if(j!=null){j.close();}
    		}
            cmd.setHuoyue(cmd.getHuoyue() + addHuoyue);
            ClubCache.putClubMember(cd.getId(), cmd);
            pushMemberListToClubMembers(cd);
    		
            human.setClubSignTs(now);
            human.setModified();
            GCClubSign gcClubSign = new GCClubSign();
            gcClubSign.setRet(MessageCode.SUCC);
            player.sendMessage(gcClubSign);
            player.sendMessage(ClubMessageUtils.getGCClubSignData(cd.getLevel(), human.getClubSignTs()));
            ClubMessageUtils.pushClubInfoToClubMembers(cd);
		} finally {
			ClubService.clubOperationLock.unlock();
		}
        
    }

    /**
     * 捐献
     *
     * @param player
     * @param cgClubDonate
     */
    public void handleClubDonate(Player player, CGClubDonate cgClubDonate) {
    	ClubService.clubOperationLock.lock();
        try {
        	Human human = player.getHuman();
            if(!checkHumanLevel(human))return;
            
            ClubData cd = ClubCache.getClubs(human.getClubId());
            if (cd == null) {
            	logger.error("passportId: " + human.getPassportId() + "will donate without clubId");
                return;
            }
            ClubMemberData cmd = ClubCache.getClubMembersOfClub(cd.getId()).get(human.getPassportId());
            
            long now = System.currentTimeMillis();
            long intelval = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getDonateTime() * 60 * 60 * 1000L;
            int min = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getDonateMin();
            int max = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getDonateMax();
            int donateAmount = cgClubDonate.getCount();
            if (donateAmount < min || donateAmount > max) {
            	human.sendSystemMessage(LangConstants.CLUB_DONATE_COST_NOT_ALLOWED);
                logger.error("玩家passportId: " + human.getPassportId()+" 捐献花费不符");
                return;
            }

            if (now - human.getClubDonateTs() < intelval) {
            	human.sendSystemMessage(LangConstants.CLUB_DONATE_EARLY);
                logger.error("玩家passportId: " + human.getPassportId()+" 捐献cd");
                return;
            }
            
            if (!human.hasEnoughMoney(donateAmount, Currency.GOLD)) {
            	human.sendSystemMessage(LangConstants.GOLD_NOT_ENOUGH);
            	logger.error("玩家passportId: " + human.getPassportId()+" 捐献不足");
                return;
            }
            human.costMoney(donateAmount, Currency.GOLD, true, LogReasons.GoldLogReason.CLUB_DONATE, LogReasons.GoldLogReason.CLUB_DONATE.getReasonText(), -1, -1);
            
            cd.setMoney(cd.getMoney() + donateAmount);
            cd.setExp(cd.getExp() + donateAmount);
            while(true)
            {
            	ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
               if(ct==null)
               {
            	   break;
               }
               int n = cd.getExp()-ct.getDonate();
               if(n>=0)
               {
            	   cd.setLevel(cd.getLevel()+1);
            	   cd.setExp(n);
               }
               else
               {
            	   break;
               }
            }
            
            ClubCache.putClub(cd.getId(), cd);
            Jedis j = null;
            try {
            	j = Globals.getRedisService().getJedisPool().getResource();
                j.zincrby(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, -donateAmount*ClubConsts.integer_coefficient, cd.getId());
                cmd.setGongxian(cmd.getGongxian() + donateAmount);
    		} finally {
    			if(j!=null) {j.close();}
    		}
            cmd.setDonateTime(now);
            ClubCache.putClubMember(cd.getId(), cmd);
            human.setClubDonateTs(now);
            human.setModified();

            GCClubDonate gcClubDonate = new GCClubDonate();
            gcClubDonate.setRet(MessageCode.SUCC);
            player.sendMessage(gcClubDonate);
            player.sendMessage(ClubMessageUtils.getGCClubDonateData(cd.getLevel(), human.getClubDonateTs()));
            player.sendMessage(cd.buildGCClubMemberList());
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 搜索俱乐部
     *
     * @param player
     * @param cgClubSearch
     */
    public void handleClubSearch(Player player, CGClubSearch cgClubSearch) {
    	ClubService.clubOperationLock.lock();
        try {
        	Human human = player.getHuman();
            if(!checkHumanLevel(human))return;

            boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(cgClubSearch.getName());

            if (hasDirtyWords == true) {
                human.sendSystemMessage(LangConstants.CLUB_SEARCH_NAME_ERROR);
                return;
            }

            ClubData cd = null;
            Jedis j = null;
            try {
            	j = Globals.getRedisService().getJedisPool().getResource();
                List<String> l = j.hvals(RedisKey.CLUB_INFO_HASH);
                for(String s : l)
                {
                	cd = Globals.gson.fromJson(s, ClubData.class);
                	if(cd.getName().equals(cgClubSearch.getName().trim()))
                	{
                		break;
                	}
                	else
                	{
                		cd = null;
                	}
                }
    		} finally {
    			if(j!=null) {j.close();}
    		}
            
            GCClubSearchResult gcClubSearchResult = new GCClubSearchResult();
            if (cd != null) {
                ClubListUnit[] unit = new ClubListUnit[1];
                unit[0] = ClubListUnit.toProtocol(cd);
                gcClubSearchResult.setList(unit);
            }
            else
            {
            	gcClubSearchResult.setList(new ClubListUnit[0]);
            }

            player.sendMessage(gcClubSearchResult);
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 获取留言板列表
     *
     * @param player
     * @param cgClubNotePanel
     */
    public void handleClubNotePanel(Player player, CGClubNotePanel cgClubNotePanel) {
    	Human human = player.getHuman();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
		GCClubNoteList res = ClubMessageUtils.buildGCClubNoteList(clubId);
        player.sendMessage(res);
        
        /**
         * 每日首次进入自己俱乐部的留言板，会弹出提示 付费引导
         */
        player.getHuman().getHumanPayguideManager().sendClub();
    }

    /**
     * 俱乐部发普通消息
     *
     * @param player
     * @param cgClubNoteSend
     */
    public void handleClubNoteSend(Player player, CGClubNoteSend cgClubNoteSend) {
    	Human human = player.getHuman();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
		String msg = cgClubNoteSend.getMsg();
		ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
        boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(msg);
        if (hasDirtyWords == true) {
            player.getHuman().sendSystemMessage(LangConstants.CLUB_BOARD_NOTE_ERROR);
            logger.error("passportId: " + human.getPassportId() + "留言板不合法: "+msg);
            return;
        }
        
        if(msg.length()>ct.getClubBoard())
        {
        	 logger.error("passportId: " + human.getPassportId() + "字数过多msg: "+msg);
        	 player.getHuman().sendSystemMessage(LangConstants.CLUB_BOARD_TOO_LONG);
        }
        BoardMsgData bm = new BoardMsgData();
        String msgId = UUID.randomUUID().toString();
        bm.setPassportId(human.getPassportId());
        bm.setNote(msg);
        bm.setNoteType(ClubConsts.board_note_type_common);
        bm.setImg(human.getImg());
        bm.setCountry(human.getCountries());
        bm.setLevel((int)human.getLevel());
        bm.setVipLevel(human.getHumanVipNewManager().getVipLv());
        ClubMemberData cmd = ClubCache.getClubMembersOfClub(clubId).get(human.getPassportId());
        bm.setZhiwu(cmd.getZhiwu());
        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            long now = System.currentTimeMillis();
            j.zadd(RedisKey.CLUB_BOARD_SORT_SET__+clubId, now, msgId);
            j.zremrangeByRank(RedisKey.CLUB_BOARD_SORT_SET__+clubId, 0, -ct.getNewmax()+1);
            String key = RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId;
            j.set(key, Globals.gson.toJson(bm));
            int days = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getNewtime();
            j.expire(key, days*24*3600);//保留七天
		} finally {
			if(j!=null) {j.close();}
		}
        ClubMessageUtils.pushBoardList(clubId);
    }


    /**
     * 获取排行榜
     *
     * @param player
     * @param cgClubRankingList
     */
	public void handleClubRankingList(Player player, CGClubRankingList cgClubRankingList) {
		Human human = player.getHuman();
		ClubData cd = ClubCache.getClubs(human.getClubId());
		GCClubRankingList gcClubRankingList = new GCClubRankingList();
		gcClubRankingList.setOpType(cgClubRankingList.getOpType());

		List<ClubData> ret = new ArrayList<>(50);
		Jedis jedis = null;
		try {
			jedis = Globals.getRedisService().getJedisPool().getResource();
			Set<String> ranks = null;
			int rank = -1;
			if (cgClubRankingList.getOpType() == ClubConsts.rank_type_huo_yue) {
				ranks = jedis.zrange(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, 0, 49);
				rank =jedis.zrank(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, human.getClubId()).intValue() + 1;
			} else if (cgClubRankingList.getOpType() == ClubConsts.rank_type_gong_xian) {
				ranks = jedis.zrange(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, 0, 49);
				rank = jedis.zrank(RedisKey.CLUB_RANK_GONG_XIAN_SORT_SET, human.getClubId()).intValue() + 1;
			}
			for (String clubId : ranks) {
				ret.add(ClubCache.getClubs(clubId));
			}

			ClubListUnit[] units = new ClubListUnit[ret.size()];
			int i = 0;
			for (ClubData club : ret) {
				units[i++] = ClubListUnit.toProtocol(club);
			}
			gcClubRankingList.setList(units);
			gcClubRankingList.setSelfRank(rank);
			gcClubRankingList.setSelf(ClubListUnit.toProtocol(cd));
			player.sendMessage(gcClubRankingList);
		} finally {
			if(jedis!=null)
			{
				jedis.close();
			}
		}
	}

    /**
     * 主动加入俱乐部
     *
     * @param player
     * @param cgClubJoin
     */
	@Deprecated
    public void handleClubJoin(Player player, CGClubJoin cgClubJoin) {
//        // 已经加入
//        // 人数
//        // 段位
//        // 设置缓存
//        // 保存记录
//        // 发送信息
    	Jedis j = null;
    	try {
    		j = Globals.getRedisService().getJedisPool().getResource();
            Human human = player.getHuman();
            long passportId = human.getPassportId();
            int applyTime = getPlayerApplyTime(player.getPassportId());
            if (applyTime >= 10) {
            	human.sendSystemMessage(LangConstants.CLUB_APPLY_TOO_MANY);
                logger.error("passportId: " + passportId + "今天申请次数超过10次: "+applyTime);
                return;
            }

            if (StringUtils.isNotEmpty(human.getClubId())) {
            	human.sendSystemMessage(LangConstants.CLUB_ALREADY_IN);
                logger.error("已经存在俱乐部不能加入俱乐部");
                return;
            }
            String clubId = cgClubJoin.getClubId();
            ClubData cd = ClubCache.getClubs(clubId);
            if (cd == null) {
                logger.error("passportId: " + passportId + "加入的俱乐部: "+clubId+"不存在或者解散");
                human.sendSystemMessage(LangConstants.CLUB_NOT_EXIST);
                return;
            }
            Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
            
            if (map.size()>=Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getCluNum()) {
                logger.error("passportId: " + passportId + "加入的俱乐部: "+clubId+" 已满员");
                human.sendSystemMessage(LangConstants.CLUB_FULL);
                return;
            }

            ClubType clubType = ClubType.from(cd.getClub_type());

            if (clubType == ClubType.TYPE_NOT_JOIN) {
                logger.error("passportId: " + passportId + "加入的俱乐部: "+clubId+" 当前不可加入成员");
                human.sendSystemMessage(LangConstants.CLUB_NONE_JOIN);
                return;
            }

            int duanwei = Globals.getRankNewServer().getDuanWei(passportId);
            if (duanwei < cd.getDuanweiLimit()) {
                logger.error("passportId: " + passportId + "加入的俱乐部: "+clubId+" 段位不足, 段位： "+duanwei);
                human.sendSystemMessage(LangConstants.CLUB_LIMIT_NOT_MATCH);
                return;
            }

            long now = System.currentTimeMillis();
            if (clubType == ClubType.TYPE_PUBLIC) {
                //可以自由加入
            	buildMember(j, human, now, clubId, -1, 1);
                GCClubJoinResult result = new GCClubJoinResult();
                result.setClubId(cd.getId());
                player.sendMessage(result);
                
                Map<Long, ClubMemberData> membersOfClub = ClubCache.getClubMembersOfClub(human.getClubId());
                player.sendMessage(cd.getGCClubInfo(membersOfClub.get(passportId).getZhiwu()));
                player.sendMessage(cd.buildGCClubMemberList());

            } else if (clubType == ClubType.TYPE_NEED_APPLY) {
                //俱乐部需要申请
            	Double score = j.zscore(RedisKey.CLUB_APPLY_IN_HASH__+clubId, String.valueOf(passportId));
            	if(score!=null)
            	{
            		logger.error("passportId: " + passportId + " 已经申请过这个俱乐部"+clubId);
                    human.sendSystemMessage(LangConstants.CLUB_APPLY_ALREADY);
            	}
            	else
            	{
            		buildApplier(j, clubId, human, now);
            	}
            	
                //成功
                GCClubJoinResult result = new GCClubJoinResult();
                result.setClubId(cgClubJoin.getClubId());
                player.sendMessage(result);
            }
		} finally {
			if(j!=null ) {j.close();}
		}
        

    }
    private void buildApplier(Jedis j, String clubId, Human human, long ts)
    {
    	j.zadd(RedisKey.CLUB_APPLY_IN_HASH__+clubId, ts, String.valueOf(human.getPassportId()));
		ClubApplyData cad = new ClubApplyData();
		long passportId = human.getPassportId();
		cad.passportId = passportId;
//		cad.ts = ts;
		cad.country = human.getCountries();
		cad.level = (int)human.getLevel();
		cad.vipLevel = human.getHumanVipNewManager().getVipLv();
		cad.name = human.getName();
		cad.img = human.getImg();
		cad.isOnline = 1;
		cad.offlineTime = -1;
		j.hset(RedisKey.CLUB_APPLIER_INFO_HASH, String.valueOf(human.getPassportId()), Globals.gson.toJson(cad));
		
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(new AsyncIoOpeOnly() {
			@Override
			public int doIo() {
				ClubApplyEntity ce = new ClubApplyEntity();
				ce.setApply_time(new Date());
				ce.setClub_id(clubId);
				ce.setPlayer_id(passportId);
				ClubApplyDao caDao = Globals.getDaoService().getClubApplyDao();
				caDao.save(ce);
				return STAGE_IO_DONE;
			}
		});
		
    }
    private void buildMember(Jedis j, Human human, long ts, String clubId, long offlineTime, int isOnline)
    {
    	long passportId = human.getPassportId();
    	ClubMemberData cmd = new ClubMemberData();
		cmd.setId(passportId);
//		cmd.setClubId(cd.getId());
		cmd.setSignTime(0);
		cmd.setDonateTime(0);
		cmd.setJoinTime(ts);
		cmd.setZhiwu(CHENGYUAN.getLevel());
		cmd.setGongxian(0);
		cmd.setHuoyue(0);
		cmd.setSex(human.getGirl());
		cmd.setCountries(human.getCountries());
		cmd.setLevel((int)human.getLevel());
		cmd.setVipLevel(human.getHumanVipNewManager().getVipLv());
		cmd.setName(human.getName());
		cmd.setOfflineTime(offlineTime);
		cmd.setImg(human.getImg());
		cmd.setIsOnline(isOnline);
		ClubCache.putClubMember(clubId, cmd);
		human.setClubId(clubId);
		human.setModified();
//		j.del(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+passportId);
		processInvited(j, clubId, passportId);
		j.hdel(RedisKey.CLUB_APPLIER_INFO_HASH, String.valueOf(passportId));
		j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+human.getClubId(), String.valueOf(passportId));
		
		
		BoardMsgData bm = new BoardMsgData();
        String msgId = UUID.randomUUID().toString();
//        bm.setPassportId(human.getPassportId());
        bm.setNoteType(ClubConsts.board_note_type_common);
        bm.setName(human.getName());
        bm.setZhiwu(cmd.getZhiwu());
        j.zadd(RedisKey.CLUB_BOARD_SORT_SET__+clubId, ts, msgId);
        String key = RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId;
        j.set(key, Globals.gson.toJson(bm));
        ClubData cd = ClubCache.getClubs(clubId);
        int days = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getNewtime();
        j.expire(key, days*24*3600);//保留七天
//        GCClubNoteList gCClubNoteList = new GCClubNoteList();
//        gCClubNoteList.setOpType(2);
//        ClubNoteUnit[] clubNoteUnitslubNoteUnit = new ClubNoteUnit[1];
//        clubNoteUnitslubNoteUnit[0] = ClubNoteUnit.toProtocol(bm, msgId, ts, clubId, passportId);
//        gCClubNoteList.setClubNote(clubNoteUnitslubNoteUnit);
//        human.sendMessage(gCClubNoteList);
        ClubMessageUtils.pushBoardList(clubId);
        pushMemberListToClubMembers(ClubCache.getClubs(clubId));
    }

    private void processInvited(Jedis j, String clubId, long passportid)
    {
    	String inviterPassportId = j.hget(RedisKey.CLUB_INVITEE_INVITER_HASH__+passportid, clubId);
    	if(inviterPassportId!=null)
    	{
    		long inviterId = Long.parseLong(inviterPassportId);
    		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
    		ClubMemberData cmd = map.get(inviterId);
    		if(cmd!=null)
    		{
    			ClubData cd = ClubCache.getClubs(clubId);
    			int addHuoyue = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getInviteActive();
    			cmd.setHuoyue(cmd.getHuoyue()+addHuoyue);
    			ClubCache.putClubMember(clubId, cmd);
    			cd.setHuoyue(cd.getHuoyue()+addHuoyue);
    			ClubCache.putClub(clubId, cd);
    			pushMemberListToClubMembers(cd);
    			j.zincrby(RedisKey.CLUB_RANK_HUO_YUE_SORT_SET, -addHuoyue*ClubConsts.integer_coefficient, cd.getId());
    		}
    	}
    	j.del(RedisKey.CLUB_INVITEE_INVITER_HASH__+passportid);
    	j.del(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+passportid);
    }
    private void buildMemberFromHumanEntity(HumanDao hDao, Jedis j, HumanEntity he, long ts, String clubId, int vipLevel, long offlineTime, int isOnline)
    {
    	long passportId = he.getPassportId();
    	ClubMemberData cmd = new ClubMemberData();
		cmd.setId(passportId);
//		cmd.setClubId(cd.getId());
		cmd.setSignTime(0);
		cmd.setDonateTime(0);
		cmd.setJoinTime(ts);
		cmd.setZhiwu(CHENGYUAN.getLevel());
		cmd.setGongxian(0);
		cmd.setHuoyue(0);
		cmd.setSex(he.getGirlFlag());
		cmd.setCountries(he.getCountries());
		cmd.setLevel((int)he.getLevel());
		cmd.setVipLevel(vipLevel);
		cmd.setName(he.getName());
		cmd.setOfflineTime(offlineTime);
		cmd.setImg(he.getImg());
		cmd.setIsOnline(isOnline);
		ClubCache.putClubMember(clubId, cmd);
		he.setClubId(clubId);
		hDao.saveOrUpdate(he);
//		j.del(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+passportId);
		processInvited(j, clubId, passportId);
		j.hdel(RedisKey.CLUB_APPLIER_INFO_HASH, String.valueOf(passportId));
		j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+he.getClubId(), String.valueOf(passportId));
		
		BoardMsgData bm = new BoardMsgData();
        String msgId = UUID.randomUUID().toString();
//        bm.setPassportId(he.getPassportId());
        bm.setNoteType(ClubConsts.board_note_type_common);
        bm.setName(he.getName());
        bm.setZhiwu(cmd.getZhiwu());
        j.zadd(RedisKey.CLUB_BOARD_SORT_SET__+clubId, ts, msgId);
        String key = RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId;
        j.set(key, Globals.gson.toJson(bm));
        ClubData cd = ClubCache.getClubs(clubId);
        int days = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getNewtime();
        j.expire(key, days*24*3600);//保留七天
//        GCClubNoteList gCClubNoteList = new GCClubNoteList();
//        gCClubNoteList.setOpType(2);
//        ClubNoteUnit[] clubNoteUnitslubNoteUnit = new ClubNoteUnit[1];
//        clubNoteUnitslubNoteUnit[0] = ClubNoteUnit.toProtocol(bm, msgId, ts, he.getClubId(), he.getPassportId());
//        gCClubNoteList.setClubNote(clubNoteUnitslubNoteUnit);
        ClubMessageUtils.pushBoardList(clubId);
        pushMemberListToClubMembers(ClubCache.getClubs(clubId));
    }
    /**
     * 获取申请人列表
     *
     * @param player
     * @param cgClubApplyList
     */
    public void handleClubApplyList(Player player, CGClubApplyList cgClubApplyList) {
        Human human = player.getHuman();
        String clubId = human.getClubId();
        Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
        ClubMemberData cmd = map.get(human.getPassportId());
        long now = System.currentTimeMillis();
        
        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();

            ClubZhiwu zhiwu = ClubZhiwu.from(cmd.getZhiwu());

            if (!zhiwu.haveRight(ClubRight.PIZHUN)) {
            	logger.error("passportId: " + human.getPassportId() + "无权查看申请列表clubId:"+clubId);
                return;
            }
            
            player.sendMessage(buildGCClubApplyList(j, clubId, now));
		} finally {
			if(j!=null) {j.close();}
		}
    }
    private GCClubApplyList buildGCClubApplyList(Jedis j, String clubId, long now)
    {
    	long limit = now-24*3600*7*1000L;
    	Set<Tuple> ss = j.zrevrangeWithScores(RedisKey.CLUB_APPLY_IN_HASH__+clubId, 0, -1);
        GCClubApplyList gCClubApplyList = new GCClubApplyList();
        gCClubApplyList.setOpType(0);
        List<ClubApplyUnit > l = new ArrayList<>();
    	for(Tuple t : ss)
    	{
//    		String[] members = new String[ss.size()];
//            ss.toArray(members);
            String mm = j.hget(RedisKey.CLUB_APPLIER_INFO_HASH, t.getElement());
            if(StringUtils.isEmpty(mm))
            {
            	j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+clubId, String.valueOf(t.getElement()));
            	continue;
            }
        	ClubApplyData bm = Globals.gson.fromJson(mm, ClubApplyData.class);
        	long appliedTime = Math.round(t.getScore());
        	if(appliedTime>limit)
        	{
        		l.add(ClubApplyUnit.toProtocol(bm, appliedTime));
        	}
        	else
        	{
        		j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+clubId, String.valueOf(bm.passportId));
        		break;
        	}
    	}
    	ClubApplyUnit[] marix = new ClubApplyUnit[ss.size()];
    	l.toArray(marix);
    	gCClubApplyList.setList(marix);
    	return gCClubApplyList;
    }
    /**
     * 拒绝加入俱乐部
     *
     * @param player
     * @param cgClubNotJoin
     */
    public void handleClubNotJoin(Player player, CGClubNotJoin cgClubNotJoin) {
//        Human human = player.getHuman();
//        ClubInvateModel ret = Globals.getClubInvateService().deleteClubInvateModel(cgClubNotJoin.getClubId(), human.getCharId());
//        // todo 通知其他缓存删除
//        GCClubJoinResult gCClubJoinResult = new GCClubJoinResult();
//        gCClubJoinResult.setClubId(ret.getClubId());
//        human.getPlayer().sendMessage(gCClubJoinResult);

    }

    /**
     * 被动的加入俱乐部
     *
     * @param player
     * @param cgClubApplyOp
     */
    public void handleClubApplyOp(Player player, CGClubApplyOp cgClubApplyOp) {
    	long now = System.currentTimeMillis();
    	Human human = player.getHuman();
        String clubId = human.getClubId();
        long applierPassportid = cgClubApplyOp.getPlayerId();
        ClubData cd = ClubCache.getClubs(clubId);
        Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();

            if (cgClubApplyOp.getOpType() == ClubConsts.OPERATION_TO_APPLIER_AGREE) {
                // 已经加入
                // 人数
                // 段位
                // 设置缓存
                // 保存记录
                // 发送信息
                // 对方是否有俱乐部

                // 权限
            	ClubMemberData cmd = map.get(human.getPassportId());
                ClubZhiwu zhiwu = ClubZhiwu.from(cmd.getZhiwu());

                if (!zhiwu.haveRight(ClubRight.PIZHUN)) {
                    logger.error("passportId: " + human.getPassportId() + " 没有审批权限, clubId: "+clubId);
                    human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
                    return;
                }
                
                if (map.size()>=Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getCluNum()) {
                    logger.error("俱乐部人满clubId: "+clubId);
                    human.sendSystemMessage(LangConstants.CLUB_FULL);
                    return;
                }

                if (map.containsKey(applierPassportid)) {
                    logger.error("目标 passportId: " + applierPassportid + "已加入club: "+clubId);
            		j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+human.getClubId(), String.valueOf(applierPassportid));
                    return;
                }

                String mem = j.hget(RedisKey.CLUB_APPLIER_INFO_HASH, String.valueOf(applierPassportid));
                ClubApplyData cad = Globals.gson.fromJson(mem, ClubApplyData.class);
                Player playerApplying = Globals.getOnlinePlayerService().getPlayerByPassportId(applierPassportid);
    			if (playerApplying != null) {
    				buildMember(j, playerApplying.getHuman(), now, clubId, cad.offlineTime, cad.isOnline);
    			} else {
    				HumanDao hDao = Globals.getDaoService().getHumanDao();
    				List<HumanEntity> humanEntityList = hDao.loadHumans(applierPassportid);
    				if (humanEntityList != null && humanEntityList.get(0) != null) {
    					HumanEntity en = humanEntityList.get(0);
    					buildMemberFromHumanEntity(hDao, j, en, now, clubId, cad.vipLevel, cad.offlineTime, cad.isOnline);
    					playerApplying = Globals.getOnlinePlayerService().getPlayerByPassportId(applierPassportid);
    					if(playerApplying!=null)
    					{
    						playerApplying.getHuman().setClubId(clubId);
    					}
    				}
    			}
                
        		
                GCClubApplyOp gcClubApplyOp = new GCClubApplyOp();
                gcClubApplyOp.setRet(MessageCode.SUCC);
                player.sendMessage(gcClubApplyOp);
                player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
                player.sendMessage(cd.buildGCClubMemberList());
                
                
            } else if (cgClubApplyOp.getOpType() == ClubConsts.OPERATION_TO_APPLIER_REFUSE) {
            	j.zrem(RedisKey.CLUB_APPLY_IN_HASH__+human.getClubId(), String.valueOf(applierPassportid));
                GCClubApplyOp gcClubApplyOp = new GCClubApplyOp();
                gcClubApplyOp.setRet(MessageCode.SUCC);
                player.sendMessage(gcClubApplyOp);
                //您加入某某某俱乐部的请求已被拒绝。
                List<Long> ids = new ArrayList<>();
                ids.add(cgClubApplyOp.getPlayerId());
                String title = String.valueOf(LangConstants.CLUB_REFUSE_MAIL_TITLE);
                String strContent = String.valueOf(LangConstants.CLUB_REFUSE_MAIL_TITLE)+","+human.getName()+","+cd.getName();
//                MailLogic.getInstance().systemSendMail(null, title, strContent, ids, null);
                MailLogic.getInstance().sendMailToRole(human, applierPassportid, title, MailTypeEnum.PLAYER, strContent,null);
                player.sendMessage(buildGCClubApplyList(j, clubId, now));
            }
		} finally {
			if(j!=null) {j.close();}
		}
    }


    /**
     * 获取邀请列表
     *
     * @param player
     * @param cgClubInvateList
     */
    public void handleClubInvateList(Player player, CGClubInvateList cgClubInvateList) {
        Human human = player.getHuman();
        if(!checkHumanLevel(human))return;
        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            Set<Tuple> tuples = j.zrangeWithScores(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+human.getPassportId(), 0, -1);
            GCClubInvateList ret = new GCClubInvateList();
            List<ClubInvateUnit> l = new ArrayList<>();
            long x = System.currentTimeMillis()-7*24*3600*1000L;
            
            for(Tuple t : tuples)
            {
            	long ts = Math.round(t.getScore());
            	if(ts<x)
            	{
            		j.zrem(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+human.getPassportId(), t.getElement());
            		j.hdel(RedisKey.CLUB_INVITEE_INVITER_HASH__+human.getPassportId(), t.getElement());
            		continue;
            	}
            	ClubData cd = ClubCache.getClubs(t.getElement());
            	if(cd!=null)
            	{
            		l.add(ClubInvateUnit.toProtocol(cd, ts));
            	}
            	else
            	{
            		j.zrem(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+human.getPassportId(), t.getElement());
            		j.hdel(RedisKey.CLUB_INVITEE_INVITER_HASH__+human.getPassportId(), t.getElement());
            	}
            }
            ClubInvateUnit[] list = new ClubInvateUnit[l.size()];
            l.toArray(list);
            ret.setList(list);
            human.getPlayer().sendMessage(ret);
		} finally {
			if(j!=null) {j.close();}
		}
    }

    /**
     * 邀请某人加入俱乐部
     *
     * @param player
     * @param cgClubInvate
     */
    public void handleClubInvate(Player player, CGClubInvate cgClubInvate) {
    	Human human = player.getHuman();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
        long inviteeId = cgClubInvate.getPlayerId();

        Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
        if (map.containsKey(inviteeId)) {
            logger.error("passportId: " + inviteeId + " 对方已经加入俱乐部");
            human.sendSystemMessage(LangConstants.CLUB_ALREADY_IN);
            return;
        }

        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            Double x = j.zscore(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+inviteeId, clubId);
            if (x!=null) {
                logger.error("已经邀请过加入俱乐部");
                human.sendSystemMessage(LangConstants.CLUB_INVITE_ALREADY);
                return;
            }
            j.zadd(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+inviteeId, System.currentTimeMillis(), clubId);
            j.hset(RedisKey.CLUB_INVITEE_INVITER_HASH__+inviteeId, clubId, String.valueOf(human.getPassportId()));
//            cmd.set
            //发邮件
            //好友/陌生人+玩家昵称邀请您加入俱乐部某某某
            List<Long> ids = new ArrayList<>();
            ids.add(inviteeId);
            String title = String.valueOf(LangConstants.CLUB_INVITE_MAIL_TITLE);
            String strContent = String.valueOf(LangConstants.CLUB_INVITE_MAIL_CONTENT)+","+human.getName()+","+cd.getName();
//            MailLogic.getInstance().systemSendMail(human, title, strContent, ids, null);
            MailLogic.getInstance().sendMailToRole(human, inviteeId,title,MailTypeEnum.PLAYER, strContent,null);
            GCClubInvate ret = new GCClubInvate();
            ret.setRet(MessageCode.SUCC);
            player.sendMessage(ret);
		} finally {
			if(j!=null) {j.close();}
		}
    }

    /**
     * 从俱乐部列表加入
     *
     * @param player
     * @param cgClubJoin2
     */
    public void handleClubJoin2(Player player, CGClubJoin2 cgClubJoin2) {
        Human human = player.getHuman();
        String clubIdN = cgClubJoin2.getClubId();
        ClubData cd = ClubCache.getClubs(clubIdN);
        if (cd == null) {
        	logger.error("加入的俱乐部 "+clubIdN+" 不存在或者解散");
        	human.sendSystemMessage(LangConstants.CLUB_NOT_EXIST);
        	return;
        }
        long now = System.currentTimeMillis();
        ClubTemplate ct = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel());
        if (StringUtils.isNotEmpty(human.getClubId())) {
            logger.error("passportId: " + human.getPassportId() + " 已经存在俱乐部不能加入其他俱乐部 "+cgClubJoin2.getClubId());
            return;
        }

        Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubIdN);
        if (map.size()>=ct.getCluNum()) {
            logger.error("passportId: " + human.getPassportId() + "加入的俱乐部: "+clubIdN+" 已满员");
            human.sendSystemMessage(LangConstants.CLUB_FULL);
            return;
        }
        ClubType clubType = ClubType.from(cd.getClub_type());
        if (clubType == ClubType.TYPE_NOT_JOIN) {
            logger.error("加入的俱乐部 "+clubIdN+" 当前不可加入成员");
            human.sendSystemMessage(LangConstants.CLUB_NONE_JOIN);
            return;
        }

        int duanwei = Globals.getRankNewServer().getDuanWei(human.getPassportId());
        if (duanwei < cd.getDuanweiLimit()) {
            logger.error("passportId: " + human.getPassportId() + "加入的俱乐部: "+clubIdN+" 段位不足, 段位： "+duanwei);
            human.sendSystemMessage(LangConstants.CLUB_LIMIT_NOT_MATCH);
            return;
        }

        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            if (clubType == ClubType.TYPE_PUBLIC) {
                //可以自由加入
            	buildMember(j, human, now, clubIdN, -1, 1);
            	j.del(RedisKey.CLUB_INVITEE_INFO_SORT_SET__+human.getPassportId());
            	j.del(RedisKey.CLUB_INVITEE_INVITER_HASH__+human.getPassportId());
                GCClubJoin2 result = new GCClubJoin2();
                result.setRet(MessageCode.SUCC);
                player.sendMessage(result);
                Map<Long, ClubMemberData> members = ClubCache.getClubMembersOfClub(human.getClubId());
            	ClubMemberData cmd = members.get(human.getPassportId());
                player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
                player.sendMessage(cd.buildGCClubMemberList());
            } else if (clubType == ClubType.TYPE_NEED_APPLY) {
                int applyTime = getPlayerApplyTime(player.getPassportId());

                if (applyTime >= ct.getClubapply()) {
                	logger.error("passportId："+human.getPassportId()+" 今日申请次数超过限制");
                	human.sendSystemMessage(LangConstants.CLUB_DAILY_APPLYING_TIMES_LIMIT);
                    return;
                }
              //俱乐部需要申请
            	Double score = j.zscore(RedisKey.CLUB_APPLY_IN_HASH__+clubIdN, String.valueOf(human.getPassportId()));
            	if(score!=null)
            	{
            		logger.error("passportId: " + human.getPassportId() + " 已经申请过这个俱乐部"+clubIdN);
                    human.sendSystemMessage(LangConstants.CLUB_APPLY_ALREADY);
                    return;
            	}
            	else
            	{
            		buildApplier(j, clubIdN, human, now);
            	}
                
                //成功
                GCClubJoin2 result = new GCClubJoin2();
                result.setRet(MessageCode.SUCC);
                player.sendMessage(result);
            }
		} finally {
			if(j!=null) {j.close();}
		}
    }

    /**
     * @param player
     * @param cgClubEdit
     */
    public void handleClubEdit(Player player, CGClubEdit cgClubEdit) {
    	ClubService.clubOperationLock.lock();
        try {
        	Human human = player.getHuman();
            String clubId = human.getClubId();
            if (StringUtils.isEmpty(clubId)) {
                logger.error("passportId: " + human.getPassportId() + " 没有加入俱乐部");
                return;
            }

            boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(cgClubEdit.getNotice());

            if (hasDirtyWords == true) {
                player.getHuman().sendSystemMessage(LangConstants.CLUB_NOTICE_ERROR);
                logger.error("passportId: " + human.getPassportId() + " 俱乐部公告非法");
                return;
            }

            ClubData cd = ClubCache.getClubs(clubId);
            Map<Long, ClubMemberData> members = ClubCache.getClubMembersOfClub(clubId);
        	ClubMemberData cmd = members.get(human.getPassportId());
            ClubZhiwu zhiwu = ClubZhiwu.from(cmd.getZhiwu());
            if (!zhiwu.haveRight(ClubRight.EDIT)) {
                logger.error("passportId: " + human.getPassportId() + " 没有权限");
                human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
                return;
            }

            //判断ico
            cd.setDuanweiLimit(cgClubEdit.getLimit());
            cd.setClub_type(cgClubEdit.get_type());
            cd.setIco(cgClubEdit.getIco());
            cd.setNotice(cgClubEdit.getNotice());
            ClubCache.putClub(clubId, cd);

            //广播也许需要
            GCClubEdit gcClubEdit = new GCClubEdit();
            gcClubEdit.setRet(MessageCode.SUCC);
            player.sendMessage(gcClubEdit);
            player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 获取当日玩家申请俱乐部数量
     *
     * @param playerId
     * @return
     */
    private int getPlayerApplyTime(long playerId) {
        Date date = new Date();

        List<ClubApplyEntity> list = Globals.getDaoService().getClubApplyDao().getClubApplyByPlayerId(playerId);
        if (list == null) {
            return 0;
        }
        int ret = 0;
        for (ClubApplyEntity entity : list) {
            if (entity.getApply_time().getDay() == date.getDay()) {
                ret++;
            }
        }

        return ret;

    }

    /**
     * 个人离开俱乐部
     *
     * @param player
     * @param cgClubLeave
     */
    public void handleClubLeave(Player player, CGClubLeave cgClubLeave) {
        //	自己主动退出
        //	如果主席离开俱乐部，排序：职位>贡献>活跃.>等级，最后是加入时间。
        //	如果副主席，主管离开俱乐部，则系统将自动免除其职位后离开；
        //	如果成员离开俱乐部，这个无需额外处理，直接离开即可；
        //	如果公会中最后一个成员离开公会，则公会直接解散
        Human human = player.getHuman();
        ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
		
        if (StringUtils.isEmpty(clubId)) {
            logger.error("passportId: " + human.getPassportId() + " 没有俱乐部不能退出");
            return;
        }
        
//        ClubData cd = TemporaryCachePool.getClubs(clubId);
        Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
        ClubMemberData m = map.get(human.getPassportId());
        ClubZhiwu zhiwu = ClubZhiwu.from(m.getZhiwu());
		if (zhiwu.equals(ZHUXI)) {
			// TemporaryCachePool.removeClubMember(clubId, human.getPassportId());
			ClubMemberData x = null;

			for (ClubMemberData cmd : map.values()) {
				if (x == null) {
					x = cmd;
					continue;
				} else if (cmd.getId() == human.getPassportId()) {
					continue;
				} else {
					if (x.getZhiwu() > cmd.getZhiwu()) {
						x = cmd;
					}
				}
			}
			if(x!=null)
			{
				x.setZhiwu(ZHUXI.getLevel());
				ClubCache.putClubMember(clubId, x);
			}
		}
//                break;
//            case FUZHUXI:
//            case ZHUGUAN:
//            case CHENGYUAN://成员
//            	TemporaryCachePool.removeClubMember(clubId, human.getPassportId());
//                break;
//        }
        human.setClubId("");
        human.setModified();
        ClubCache.removeClubMember(clubId, human.getPassportId());
        if(map.size()==0)
        {
        	ClubCache.removeClub(clubId);
        	Jedis j = null;
        	try {
        		j = Globals.getRedisService().getJedisPool().getResource();
				j.hdel(RedisKey.CLUB_NAME_HASH, clubId);
			} finally {
				if(j!=null) {j.close();}
			}
        }
        GCClubLeave leave = new GCClubLeave();
        leave.setRet(MessageCode.SUCC);
        player.sendMessage(leave);
        pushMemberListToClubMembers(cd);
    }

    /**
     * 剔除成员
     *
     * @param player
     * @param cgClubKick
     */
    public void handleClubKick(Player player, CGClubKick cgClubKick) {
    	Human human = player.getHuman();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
		ClubMemberData cmd = map.get(human.getPassportId());
		long otherId = cgClubKick.getPlayerId();
        if (!map.containsKey(otherId)) {
        	human.sendSystemMessage(LangConstants.CLUB_MEMBER_NOT_EXISTED);
            logger.error("passportId: " + human.getPassportId() + " 对方不存在: "+otherId);
            return;
        }
        
        ClubZhiwu zhiwu = ClubZhiwu.from(cmd.getZhiwu());
        if (!zhiwu.getRights().haveRight(ClubRight.KICK)) {
        	human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
            logger.error("passportId: " + human.getPassportId() + " 没有踢人权限");
            return;
        }
        ClubMemberData other = map.get(otherId);
        ClubZhiwu otherZhiWu = ClubZhiwu.from(other.getZhiwu());
        if (otherZhiWu != ClubZhiwu.CHENGYUAN) {
        	human.sendSystemMessage(LangConstants.CLUB_CANNOT_KICK_FU_HUI_ZHANG);
            logger.error("passportId: " + human.getPassportId() + " 不能踢出非成员: "+otherId);
            return;
        }
        ClubCache.removeClubMember(clubId, otherId);
        
        Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(otherId);
        if(p!=null)
        {
        	Human h = p.getHuman();
        	h.setClubId("");
        	h.setModified();
        }
        else
        {
        	HumanDao hDao = Globals.getDaoService().getHumanDao();
        	List<HumanEntity> humanEntityList = hDao.loadHumans(otherId);
			if (humanEntityList != null && humanEntityList.get(0) != null) {
				HumanEntity he = humanEntityList.get(0);
				he.setClubId(clubId);
				hDao.saveOrUpdate(he);
			}
        }
        
        GCClubKick gcClubKick = new GCClubKick();
        gcClubKick.setRet(MessageCode.SUCC);

        player.sendMessage(gcClubKick);

    }

    /**
     * 升职/降职
     *
     * @param player
     * @param cgClubPromate
     */
    public void handleClubPromate(Player player, CGClubPromate cgClubPromate) {
    	ClubService.clubOperationLock.lock();
    	try {
    		Human human = player.getHuman();
    		ClubData cd = checkClub(human);
    		if(cd==null) return;
    		String clubId = cd.getId();
    		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
    		ClubMemberData self = map.get(human.getPassportId());
            long otherId = cgClubPromate.getPlayerId();

            if (!map.containsKey(otherId)) {
                logger.error("passportId: " + human.getPassportId() + " 对方不存在: "+otherId);
                human.sendSystemMessage(LangConstants.CLUB_MEMBER_NOT_EXISTED);
                return;
            }
            ClubZhiwu zhiwu = ClubZhiwu.from(self.getZhiwu());
            if (!zhiwu.getRights().haveRight(ClubRight.SHENGZHI_JIANGZHI)) {
                logger.error("没有授权权限");
                human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
                return;
            }
            
            ClubMemberData other = map.get(otherId);
            ClubZhiwu otherZhiWu = ClubZhiwu.from(other.getZhiwu());
            if (otherZhiWu.getLevel() <= zhiwu.getLevel()) {
                logger.error("passportId: " + human.getPassportId() + " 升降职的成员等级要小于自己: "+otherId);
                human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
                return;
            }

            if (cgClubPromate.getOpType() == ClubConsts.OPERATION_PROMOTE) {
                //升
                switch (otherZhiWu) {
                    case CHENGYUAN:
                    {
                    	int count = 0;
                    	for(ClubMemberData cmd : map.values())
                    	{
                    		if(cmd.getZhiwu()==ClubZhiwu.FUZHUXI.getLevel())
                    		count++;
                    	}
                    	int maxDeputy = Globals.getClubTemplateService().getClubTemplateByLevel(cd.getLevel()).getDeputy();
                    	if (count >= maxDeputy) {
                    		logger.error("passportId: " + human.getPassportId() + " 副主席太多了");
                    		human.sendSystemMessage(LangConstants.CLUB_FU_ZHU_XI_TOO_MANY);
                    		return;
                    	}
                    	other.setZhiwu(ClubZhiwu.FUZHUXI.getLevel());
                    	ClubCache.putClubMember(clubId, other);
                    	GCClubPromate gcClubPromate = new GCClubPromate();
                    	gcClubPromate.setInfo(ClubMemberListUnit.toProtocol(other, clubId));
                    	player.sendMessage(gcClubPromate);
                    	break;
                    	//推送俱乐部成员
                    }
                    case FUZHUXI:
                    {
                    	other.setZhiwu(ZHUXI.getLevel());
                    	ClubCache.putClubMember(clubId, other);
                    	self.setZhiwu(ClubZhiwu.FUZHUXI.getLevel());
                    	ClubCache.putClubMember(clubId, self);
                    	break;
                    }
                    default:
                        logger.error("can't process for: "+otherZhiWu.getLevel());
                        return;
                }

            } else if (cgClubPromate.getOpType() == ClubConsts.OPERATION_DEMOTE) {
                //降
                switch (otherZhiWu) {
                    case FUZHUXI:
                        //设置成员为成员
                        other.setZhiwu(ClubZhiwu.CHENGYUAN.getLevel());
                        //推送俱乐部成员
                        GCClubPromate gcClubPromate = new GCClubPromate();
                        gcClubPromate.setInfo(ClubMemberListUnit.toProtocol(other, clubId));
                        player.sendMessage(gcClubPromate);
                        break;
                    default:
                    	logger.error("can't process for: "+otherZhiWu.getLevel());
                    	return;
                }
            }
            pushMemberListToClubMembers(cd);
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 查询单个人信息
     *
     * @param player
     * @param cgClubInfoGet
     */
    public void handleClubInfoGet(Player player, CGClubInfoGet cgClubInfoGet) {
    	Human human = player.getHuman();
    	long passPortId = cgClubInfoGet.getPlayerId();
    	Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(passPortId);
    	String clubId = null;
    	if(p!=null)
    	{
    		clubId = p.getHuman().getClubId();
    	}
    	else
    	{
    		HumanDao hDao = Globals.getDaoService().getHumanDao();
			List<HumanEntity> humanEntityList = hDao.loadHumans(passPortId);
			if (humanEntityList != null && humanEntityList.get(0) != null) {
				HumanEntity en = humanEntityList.get(0);
				clubId = en.getClubId();
				}
    	}
    	ClubData cd = ClubCache.getClubs(clubId);
        if(cd==null)
        {
        	 logger.error("passportId: " + passPortId + " 不属于任何俱乐部");
             human.sendSystemMessage(LangConstants.CLUB_NO_CLUB);
             return;
        }
        player.sendMessage(cd.getGCClubInfoGet());
    }

    /**
     * 从商店购买即生效，此处废弃
     * 俱乐部留言板发礼物
     * @param player
     * @param cgClubNoteSendGift
     */
    @Deprecated
    public void handleClubNoteSendGift(Player player, CGClubNoteSendGift cgClubNoteSendGift) {
//    	Human human = player.getHuman();
//		ClubData cd = checkClub(human);
//		if(cd==null) return;
//		String clubId = cd.getId();
//
//        ClubRechargeTemplate clubRechargeTemplate = Globals.getClubTemplateService().getClubRechargeTemplate(cgClubNoteSendGift.getPid());
//        if(clubRechargeTemplate == null){
//            logger.error("配置模板不存在");
//            return;
//        }
//        int pid = cgClubNoteSendGift.getPid();
//        HumanBagManager hbm = human.getHumanBagManager();
//        if(hbm.getCount(pid) < 1){
//        	human.sendSystemMessage(LangConstants.CLUB_NO_ITEMS);
//            logger.error("passportId: " + human.getPassportId() + " 没有红包道具");
//            return;
//        }
//        
//        BoardMsgData bm = new BoardMsgData();
//        String msgId = UUID.randomUUID().toString();
//        bm.setPassportId(human.getPassportId());
//        bm.setNoteType(ClubConsts.board_note_type_gift);
//        bm.setGiftId(pid);
//        Jedis j = null;
//        try {
//        	j = Globals.getRedisService().getJedisPool().getResource();
//            long now = System.currentTimeMillis();
//            j.zadd(RedisKey.CLUB_BOARD_SORT_SET__+clubId, now, msgId);
//            String key = RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId;
//            j.set(key, Globals.gson.toJson(bm));
//            j.expire(key, 7*24*3600);//保留七天
//            
//            ClubRechargeTemplate ct = Globals.getClubTemplateService().getClubRechargeTemplate(bm.getGiftId());
//    		gift(Currency.valueOf(ct.getItem1Id()), human, ct.getItemNum1());
//    		j.sadd(RedisKey.CLUB_GIFTED_SET__+msgId, String.valueOf(human.getPassportId()));
//    		j.expire(RedisKey.CLUB_GIFTED_SET__+msgId, 7*24*3600);
//    		
//            hbm.removeItem(pid, 1);
//            
//            ClubMessageUtils.pushBoardList(clubId);
//		} finally {
//			if(j!=null) {j.close();}
//		}
    }

    /**
     * 俱乐部改名卡
     *
     * @param player
     * @param cgClubChangeName
     */
    public void handleClubChangeName(Player player, CGClubChangeName cgClubChangeName) {
    	ClubService.clubOperationLock.lock();
    	try {
    		Human human = player.getHuman();
    		ClubData cd = checkClub(human);
    		if(cd==null) return;
    		String clubId = cd.getId();
    		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
    		ClubMemberData cmd = map.get(human.getPassportId());
    		
            ClubZhiwu zhiwu = ClubZhiwu.from(cmd.getZhiwu());
            if (!zhiwu.haveRight(ClubRight.EDIT)) {
                logger.error("passportId: " + human.getPassportId() + " 没有权限");
                human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
                return;
            }
            int itemId = Globals.getConfigTempl().getClubChangeNameItemId();
            int tempCount = human.getHumanBagManager().getCount(itemId);
            if(tempCount < 1){
                logger.error("passportId: " + human.getPassportId() + " 没有改名卡");
                human.sendSystemMessage(LangConstants.CLUB_NO_ITEMS);
                return;
            }
            String newName = cgClubChangeName.getName();
            boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(newName);

            if (hasDirtyWords == true) {
                player.getHuman().sendSystemMessage(LangConstants.CLUB_NAME_ERROR);
                logger.error("passportId: " + human.getPassportId() + " 俱乐部名字非法"+clubId+", name: "+newName);
                return;
            }
            cd.setName(newName);
            ClubCache.putClub(clubId, cd);
            human.getHumanBagManager().removeItem(itemId, 1);
            String itemUseDetailReason = MessageFormat.format(LogReasons.ItemLogReason.ITEM_USE.getReasonText(),  itemId);
			Globals.getLogService().sendItemLog(human, LogReasons.ItemLogReason.ITEM_USE, itemUseDetailReason, itemId, 1, tempCount-1);
			
            
            //广播也许需要
            GCClubChangeName gcClubChangeName = new GCClubChangeName();
            gcClubChangeName.setRet(MessageCode.SUCC);
            player.sendMessage(gcClubChangeName);
            player.sendMessage(cd.getGCClubInfo(cmd.getZhiwu()));
            player.sendMessage(human.getHumanBagManager().buildHumanBagInfoData());
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }

    /**
     * 俱乐部留言删除
     * @param player
     * @param cgClubNoteDelete
     */
	public void handleClubNoteDelete(Player player, CGClubNoteDelete cgClubNoteDelete) {
		Human human = player.getHuman();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = human.getClubId();
		Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
        ClubZhiwu clubZhiwu = ClubZhiwu.from(map.get(human.getPassportId()).getZhiwu());

        Jedis j = null;
        try {
        	j = Globals.getRedisService().getJedisPool().getResource();
            String msgId = cgClubNoteDelete.getMsgId();
            if(!clubZhiwu.haveRight(ClubRight.CLEAN_NOTE)) {
            	String content = j.get(RedisKey.CLUB_BOARD_CONTENT_KEY__ + msgId);
            	BoardMsgData bm = Globals.gson.fromJson(content, BoardMsgData.class);
            	if(bm.getPassportId()!=human.getPassportId())
            	{
            		logger.error("passportId: " + human.getPassportId() + " 没有权限");
            		human.sendSystemMessage(LangConstants.CLUB_OPE_NO_RIGHT);
            		return;
            	}
            }
            j.zrem(RedisKey.CLUB_BOARD_SORT_SET__+clubId, msgId);
            j.del(RedisKey.CLUB_BOARD_CONTENT_KEY__ + msgId);

            GCClubNoteDelete gcClubNoteDelete = new GCClubNoteDelete();
            gcClubNoteDelete.setRet(MessageCode.SUCC);
            player.sendMessage(gcClubNoteDelete);
		} finally {
			if(j!=null) {j.close();}
		}
	}

    /**
     * 获取礼物
     * @param player
     * @param cgClubGetGift
     */
	public void handleClubGetGift(Player player, CGClubGetGift cgClubGetGift) {
		Human human = player.getHuman();
		long passportId = human.getPassportId();
		ClubData cd = checkClub(human);
		if(cd==null) return;
		String clubId = cd.getId();
		String msgId = cgClubGetGift.getMsgId();
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			String json = j.get(RedisKey.CLUB_BOARD_CONTENT_KEY__+msgId);
			if(StringUtils.isEmpty(json))
			{
				logger.error("passportId: "+passportId+", gift of msgId: "+msgId+", expired");
				human.sendSystemMessage(LangConstants.CLUB_GIFT_EXPIRED);
				return;
			}
			BoardMsgData bm = Globals.gson.fromJson(json, BoardMsgData.class);
			boolean gifted = j.sismember(RedisKey.CLUB_GIFTED_SET__+msgId, String.valueOf(passportId));
			if(gifted)
			{
				logger.error("passprotId: "+passportId+" has been gifted for msgId: "+msgId);
				human.sendSystemMessage(LangConstants.CLUB_GIFTED);
				return;
			}
			
			ClubRechargeTemplate ct = Globals.getClubTemplateService().getClubRechargeTemplate(bm.getGiftId());
			
			human.giveMoney(ct.getItemNum2(), Currency.valueOf(ct.getItem2Id()), true, LogReasons.GoldLogReason.CLUB_GIFT, LogReasons.GoldLogReason.CLUB_GIFT.getReasonText(), -1, 1);
			human.giveMoney(ct.getItemNum3(), Currency.valueOf(ct.getItem3Id()), true, LogReasons.GoldLogReason.CLUB_GIFT, LogReasons.GoldLogReason.CLUB_GIFT.getReasonText(), -1, 1);
			j.sadd(RedisKey.CLUB_GIFTED_SET__+msgId, String.valueOf(passportId));
			
	        GCClubGetGift gcClubNoteDelete = new GCClubGetGift();
	        gcClubNoteDelete.setRet(MessageCode.SUCC);
	        player.sendMessage(gcClubNoteDelete);
	        ClubMessageUtils.pushBoardList(clubId);
		} finally {
			if(j!=null) {j.close();}
		}
	}
	/**
	 * 弹劾投票
	 * @param player
	 * @param cgClubTanheVote
	 */
	public void handleClubTanheVote(Player player, CGClubTanheVote cgClubTanheVote) {
		ClubService.clubOperationLock.lock();
		try {
			Human human = player.getHuman();
			ClubData cd = checkClub(human);
			if(cd==null) return;
			String clubId = cd.getId();
			if(cd.getTanheSponsor()==0)
			{
				logger.error("passportId: " + human.getPassportId() + " 弹劾未发起，不可投票");
				human.sendSystemMessage(LangConstants.CLUB_TANHE_CANNOT_VOTE);
				return;
			}
			long x = System.currentTimeMillis()-24*3600*1000L;
			if(cd.getTanheStartTs()<x)
			{
				logger.error("passportId: " + human.getPassportId() + ", clubId: "+clubId+" 弹劾投票结束");
				human.sendSystemMessage(LangConstants.CLUB_TANHE_EXPIRE);
				return;
			}
			Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
			ClubMemberData self = map.get(human.getPassportId());
			if(self.getTanheVote()!=ClubConsts.OPERATION_TANHE_NONE)
			{
				logger.error("passportId: " + human.getPassportId() + " 已做过弹劾投票");
				human.sendSystemMessage(LangConstants.CLUB_TANHE_VOTE_ALREADY);
				return;
			}
			self.setTanheVote(cgClubTanheVote.getRet());
			int agree = 0;
			ClubMemberData zhuxi = null;
			for(ClubMemberData cmd : map.values())
			{
				if(cmd.getTanheVote()==ClubConsts.OPERATION_TANHE_AGREE)
				{
					agree++;
				}
				if(cmd.getZhiwu()==ClubZhiwu.ZHUXI.getLevel())
				{
					zhuxi = cmd;
				}
			}
			if(agree>=(map.size())/2+1)
			{
				zhuxi.setTanheState(ClubConsts.STATE_TANHE_SUCC);
				zhuxi.setZhiwu(ClubZhiwu.CHENGYUAN.getLevel());
				ClubCache.putClubMember(clubId, zhuxi);
				TreeSet<ClubMemberData> ts = sortClubMember(map.values());
				ClubMemberData newZhuxi = ts.first();
				newZhuxi.setZhiwu(ClubZhiwu.ZHUXI.getLevel());
				ClubCache.putClubMember(clubId, newZhuxi);
				cd.setTanheSponsor(-1);
				ClubCache.putClub(clubId, cd);
				pushMemberListToClubMembers(cd);
			}
			
			ClubCache.putClubMember(clubId, self);
			GCClubTanheVote msg = new GCClubTanheVote();
			msg.setRet(MessageCode.SUCC);
			player.sendMessage(msg);
			
			pushClubTanheStateToClubMembers(clubId, cd, zhuxi);
		} finally {
			ClubService.clubOperationLock.unlock();
		}
	}
	
	private void pushMemberListToClubMembers(ClubData cd)
	{
		String clubId = cd.getId();
		Map<Long, ClubMemberData> map  = ClubCache.getClubMembersOfClub(clubId);
		GCClubMemberList gcClubMemberList = cd.buildGCClubMemberList();
		OnlinePlayerService os = Globals.getOnlinePlayerService();
		for(ClubMemberData cmd : map.values())
		{
			Player p = os.getPlayerByPassportId(cmd.getId());
			if(p!=null)
			{
				p.sendMessage(gcClubMemberList);
			}
		}
	}
	
	
	private TreeSet<ClubMemberData> sortClubMember(Collection<ClubMemberData> members)
	{
		TreeSet<ClubMemberData> ts = new TreeSet<>(new Comparator<ClubMemberData>() {
			@Override
			public int compare(ClubMemberData o1, ClubMemberData o2) {
				if(o1.getGongxian()>o2.getGongxian())
				{
					return 1;
				}
				else if(o1.getGongxian()<o2.getGongxian())
				{
					return -1;
				}
				else
				{
					if(o1.getHuoyue()>o2.getHuoyue())
					{
						return 1;
					}
					else if(o1.getHuoyue()<o2.getHuoyue())
					{
						return -1;
					}
					else
					{
						if(o1.getLevel()>o2.getLevel())
						{
							return 1;
						}
						else if(o1.getLevel()<o2.getLevel())
						{
							return -1;
						}
						else
						{
							if(o1.getJoinTime()<o2.getJoinTime())
							{
								return 1;
							}
							else if(o1.getJoinTime()>o2.getJoinTime())
							{
								return -1;
							}
							else
							{
								return 0;
							}
						}
					}
						
				}
			}
		});
		ts.addAll(members);
		return ts;
	}
	public static ClubData checkClub(Human human)
	{
		String clubId = human.getClubId();
		if(StringUtils.isEmpty(clubId))
		{
			logger.error("passportId: " + human.getPassportId() + " 没有加入俱乐部");
			human.sendSystemMessage(LangConstants.CLUB_NO_CLUB);
			return null;
		}
		
		ClubData cd = ClubCache.getClubs(clubId);
		if(cd==null)
		{
			logger.error("passportId: " + human.getPassportId() + ", 俱乐部: "+clubId+"不存在或者解散");
            human.sendSystemMessage(LangConstants.CLUB_NOT_EXIST);
            return null;
		}
		return cd;
	}
	/**
	 * 发起弹劾
	 * @param player
	 * @param cgClubTanhe
	 */
	public void handleClubTanhe(Player player, CGClubTanhe cgClubTanhe) {
		ClubService.clubOperationLock.lock();
		try {
			Human human = player.getHuman();
			ClubData cd = checkClub(human);
			if(cd==null) return;
			String clubId = cd.getId();
			if(cd.getTanheSponsor()!=0)
			{
				logger.error("passportId: " + human.getPassportId() + ", 俱乐部: "+clubId+" 已有人发起弹劾");
	            human.sendSystemMessage(LangConstants.CLUB_TANHE_ALREADY);
	            return;
			}
			Map<Long, ClubMemberData> map = ClubCache.getClubMembersOfClub(clubId);
			for(ClubMemberData cmd : map.values())
			{
				if(cmd.getZhiwu()==ZHUXI.getLevel())
				{
					long now = System.currentTimeMillis();
					if((now - cmd.getOfflineTime())<30*24*3600*1000L)
					{
						logger.error("passportId: " + human.getPassportId() + "弹劾："+cmd.getId()+" 不符合规则");
						human.sendSystemMessage(LangConstants.CLUB_TANHE_INVALID);
					}
					else
					{
						ClubMemberData self = map.get(human.getPassportId());
						self.setTanheVote(ClubConsts.OPERATION_TANHE_AGREE);//同意弹劾
						ClubCache.putClubMember(clubId, self);
						cd.setTanheSponsor(human.getPassportId());
						cd.setTanheStartTs(now);
						ClubCache.putClub(clubId, cd);
						
						cmd.setTanheState(ClubConsts.STATE_TANHE_ING);
//						cmd.setTanheTs(now);
						ClubCache.putClubMember(clubId, cmd);
						GCClubTanhe gcClubTanhe = new GCClubTanhe();
						gcClubTanhe.setRet(0);
						player.sendMessage(gcClubTanhe);
						pushClubTanheStateToClubMembers(clubId, cd, cmd);
					}
					break;
				}
			}
		} finally {
			ClubService.clubOperationLock.unlock();
		}
    }
	private void pushClubTanheStateToClubMembers(String clubId, ClubData cd, ClubMemberData zhuxi)
	{
		Map<Long, ClubMemberData> map  = ClubCache.getClubMembersOfClub(clubId);
		GCClubTanheState res = new GCClubTanheState();
//		res.setSelfState(map.get(human.getPassportId()).getTanheVote());
		int agree = 0;
		int disagree = 0;
//		List<Long> passportIds = new ArrayList<>();
		for(ClubMemberData cmd : map.values())
		{
//			passportIds.add(cmd.getId());
			switch(cmd.getTanheVote())
			{
			case ClubConsts.OPERATION_TANHE_AGREE:
			{
				agree++;
				break;
			}
			case ClubConsts.OPERATION_TANHE_DISAGREE:
			{
				disagree++;
				break;
			}
			}
		}
		res.setAgree(agree);
		res.setRefuse(disagree);
		res.setResult(zhuxi.getTanheState());
		for(ClubMemberData cmd : map.values())
		{
			Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(cmd.getId());
			if(p!=null)
			{
				res.setSelfState(cmd.getTanheVote());
				p.sendMessage(res);
			}
		}
	}
	
	@Deprecated
	public void handleClubTanheState(Player player, CGClubTanheState cgClubTanheState) {
//		Human human = player.getHuman();
//		ClubData cd = checkClub(human);
//		if(cd==null) return;
//		String clubId = cd.getId();
	
	}
}
