package com.gameserver.redis.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * redis Key
 * 
 * @author 郭君伟
 *
 */
public enum RedisKey implements IndexedEnum {
	/** 服务器ID 列表 **/
	SERVERID(1, "serverid"),
	/** 老虎机数据缓存 **/
	SLOT(2, "slotdata"),;

	private int index;
	private String key;

	private RedisKey(int index, String key) {
		this.index = index;
		this.key = key;
	}

	private static final List<RedisKey> indexes = IndexedEnumUtil.toIndexes(RedisKey.values());

	@Override
	public int getIndex() {
		return index;
	}

	public String getKey() {
		return key;
	}

	public static RedisKey indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

	public static final String CLUB_NAME_HASH = "club:name_H";
	public static final String CLUB_INFO_HASH = "club:info_H";
	public static final String CLUB_MEMBER_INFO_HASH__ = "club:member_info_H-";
//	public static final String CLUB_VOTE_HASH__ = "club_vote_H-";
	public static final String CLUB_RANK_HUO_YUE_SORT_SET = "club:rank_hy_SS";
	public static final String CLUB_RANK_GONG_XIAN_SORT_SET = "club:rank_gx_SS";
	
	public static final String CLUB_BOARD_SORT_SET__ = "club:board_SS-";
	public static final String CLUB_BOARD_CONTENT_KEY__ = "club:board:c_K-";
	public static final String CLUB_GIFTED_SET__ = "club:gift_S-";
	
	public static final String CLUB_APPLY_IN_HASH__ = "club:apply_H-";    // key:+clubId     member:passportId
	public static final String CLUB_APPLIER_INFO_HASH = "club:applier_H"; //member:passportId
	
	public static final String CLUB_INVITEE_INFO_SORT_SET__ = "club:invitee_SS-";
	public static final String CLUB_INVITEE_INVITER_HASH__ = "club:invitee_er_H-";
	
	public static final String CLUB_SEASON_KEY = "club:season_K";
//	public static final String CLUB_MEMBER_HUO_YUE_RANK_SORT_SET__ = "club:member_huo_yue_rank_SS-";
}
