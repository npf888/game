package com.gameserver.club.protocol;

import redis.clients.jedis.Jedis;

import com.gameserver.club.redis.BoardMsgData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.consts.ClubConsts;
import com.gameserver.fw.ClubCache;
import com.gameserver.redis.enums.RedisKey;

public class ClubNoteUnit {

	private String noteId;
	private Long playerId;
	private String playerName;
	private String img;
	private String guoji;
	private long level;
	private int noteType;// 类型 0 常规 1 礼物
	private String content;
	private int giftId;
	private int alreadyGet;
	private int zhiwu;
	private long time;

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getGuoji() {
		return guoji;
	}

	public void setGuoji(String guoji) {
		this.guoji = guoji;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public int getNoteType() {
		return noteType;
	}

	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}

	public int getAlreadyGet() {
		return alreadyGet;
	}

	public void setAlreadyGet(int alreadyGet) {
		this.alreadyGet = alreadyGet;
	}

	public int getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static ClubNoteUnit toProtocol(BoardMsgData bm, String msgId, long ts, String clubId, long passportId) {
		ClubNoteUnit protocol = new ClubNoteUnit();
		protocol.setNoteId(msgId);
		protocol.setPlayerId(bm.getPassportId());
		ClubMemberData cmd = ClubCache.getClubMembersOfClub(clubId).get(bm.getPassportId());
		if(cmd!=null)
		{
			protocol.setPlayerName(cmd.getName());
			if(passportId!=0)
			{
				protocol.setImg(cmd.getImg());
				protocol.setGuoji(cmd.getCountries());
				protocol.setLevel(cmd.getLevel());
				protocol.setZhiwu(cmd.getZhiwu());
			}
		}
		else
		{
			protocol.setPlayerName(bm.getName());
			protocol.setImg(bm.getImg());
			protocol.setGuoji(bm.getCountry());
			protocol.setLevel(bm.getLevel());
			protocol.setZhiwu(bm.getZhiwu());
		}
		protocol.setTime(ts);
		protocol.setNoteType(bm.getNoteType());
		if (bm.getNoteType() == ClubConsts.board_note_type_gift) {
			protocol.setGiftId(bm.getGiftId());
			Jedis j = null;
			try {
				j = Globals.getRedisService().getJedisPool().getResource();
				String key = RedisKey.CLUB_GIFTED_SET__ + msgId;
				protocol.setAlreadyGet(j.sismember(key, String.valueOf(passportId)) ? 1 : 0);
				j.expire(key, 3600 * 24 * 7);
			} finally {
				if (j != null) {
					j.close();
				}
			}
		} else if (bm.getNoteType() == ClubConsts.board_note_type_common) {
			// 常规
			protocol.setContent(bm.getNote());
		}
		return protocol;
	}
}
