package com.gameserver.club.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ClubTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ClubTemplateVO extends TemplateObject {

	/** 俱乐部等级 */
	@ExcelCellBinding(offset = 1)
	protected int clubLevel;

	/** 最大人数 */
	@ExcelCellBinding(offset = 2)
	protected int cluNum;

	/** 捐献数量min */
	@ExcelCellBinding(offset = 3)
	protected int donateMin;

	/** 捐献数量max */
	@ExcelCellBinding(offset = 4)
	protected int donateMax;

	/** 捐献数量max */
	@ExcelCellBinding(offset = 5)
	protected int donate;

	/** 捐献间隔 */
	@ExcelCellBinding(offset = 6)
	protected int donateTime;

	/** 好友活跃度 */
	@ExcelCellBinding(offset = 7)
	protected int inviteActive;

	/** 签到活跃度 */
	@ExcelCellBinding(offset = 8)
	protected int signActive;

	/** 签到刷新时间 */
	@ExcelCellBinding(offset = 9)
	protected int signTime;

	/** 转化率 */
	@ExcelCellBinding(offset = 10)
	protected int convert;

	/** 赛季持续时间 */
	@ExcelCellBinding(offset = 11)
	protected int rankTime;

	/** 创建消耗 */
	@ExcelCellBinding(offset = 12)
	protected int clubFound;

	/** 名字长度 */
	@ExcelCellBinding(offset = 13)
	protected int clubName;

	/** 描述长度 */
	@ExcelCellBinding(offset = 14)
	protected int clubDescribe;

	/** 留言板条数 */
	@ExcelCellBinding(offset = 15)
	protected int clubBoard;

	/** 俱乐部申请上线 */
	@ExcelCellBinding(offset = 16)
	protected int clubapply;

	/** 消息存放时间 */
	@ExcelCellBinding(offset = 17)
	protected int newtime;

	/** 副会长人数 */
	@ExcelCellBinding(offset = 18)
	protected int deputy;

	/** 消息存放上限 */
	@ExcelCellBinding(offset = 19)
	protected int newmax;

	/** 俱乐部被申请上限 */
	@ExcelCellBinding(offset = 20)
	protected int clubapplied;

	/** 弹劾失败冷却时间 */
	@ExcelCellBinding(offset = 21)
	protected int accusetime;

	/** 申请冷却时间 */
	@ExcelCellBinding(offset = 22)
	protected int invitetime;

	/** 赛季结算获得奖励最低活跃值 */
	@ExcelCellBinding(offset = 23)
	protected int activelimit;


	public int getClubLevel() {
		return this.clubLevel;
	}



	public void setClubLevel(int clubLevel) {
		this.clubLevel = clubLevel;
	}
	
	public int getCluNum() {
		return this.cluNum;
	}


	public final static int getCluNumMinLimit() {
		return 0;
	}

	public void setCluNum(int cluNum) {
		if (cluNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[最大人数]cluNum的值不得小于0");
		}
		this.cluNum = cluNum;
	}
	
	public int getDonateMin() {
		return this.donateMin;
	}


	public final static int getDonateMinMinLimit() {
		return 0;
	}

	public void setDonateMin(int donateMin) {
		if (donateMin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[捐献数量min]donateMin的值不得小于0");
		}
		this.donateMin = donateMin;
	}
	
	public int getDonateMax() {
		return this.donateMax;
	}


	public final static int getDonateMaxMinLimit() {
		return 0;
	}

	public void setDonateMax(int donateMax) {
		if (donateMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[捐献数量max]donateMax的值不得小于0");
		}
		this.donateMax = donateMax;
	}
	
	public int getDonate() {
		return this.donate;
	}


	public final static int getDonateMinLimit() {
		return 0;
	}

	public void setDonate(int donate) {
		if (donate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[捐献数量max]donate的值不得小于0");
		}
		this.donate = donate;
	}
	
	public int getDonateTime() {
		return this.donateTime;
	}


	public final static int getDonateTimeMinLimit() {
		return 0;
	}

	public void setDonateTime(int donateTime) {
		if (donateTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[捐献间隔]donateTime的值不得小于0");
		}
		this.donateTime = donateTime;
	}
	
	public int getInviteActive() {
		return this.inviteActive;
	}


	public final static int getInviteActiveMinLimit() {
		return 0;
	}

	public void setInviteActive(int inviteActive) {
		if (inviteActive < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[好友活跃度]inviteActive的值不得小于0");
		}
		this.inviteActive = inviteActive;
	}
	
	public int getSignActive() {
		return this.signActive;
	}


	public final static int getSignActiveMinLimit() {
		return 0;
	}

	public void setSignActive(int signActive) {
		if (signActive < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[签到活跃度]signActive的值不得小于0");
		}
		this.signActive = signActive;
	}
	
	public int getSignTime() {
		return this.signTime;
	}


	public final static int getSignTimeMinLimit() {
		return 0;
	}

	public void setSignTime(int signTime) {
		if (signTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[签到刷新时间]signTime的值不得小于0");
		}
		this.signTime = signTime;
	}
	
	public int getConvert() {
		return this.convert;
	}


	public final static int getConvertMinLimit() {
		return 0;
	}

	public void setConvert(int convert) {
		if (convert < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[转化率]convert的值不得小于0");
		}
		this.convert = convert;
	}
	
	public int getRankTime() {
		return this.rankTime;
	}



	public void setRankTime(int rankTime) {
		this.rankTime = rankTime;
	}
	
	public int getClubFound() {
		return this.clubFound;
	}



	public void setClubFound(int clubFound) {
		this.clubFound = clubFound;
	}
	
	public int getClubName() {
		return this.clubName;
	}



	public void setClubName(int clubName) {
		this.clubName = clubName;
	}
	
	public int getClubDescribe() {
		return this.clubDescribe;
	}



	public void setClubDescribe(int clubDescribe) {
		this.clubDescribe = clubDescribe;
	}
	
	public int getClubBoard() {
		return this.clubBoard;
	}



	public void setClubBoard(int clubBoard) {
		this.clubBoard = clubBoard;
	}
	
	public int getClubapply() {
		return this.clubapply;
	}



	public void setClubapply(int clubapply) {
		this.clubapply = clubapply;
	}
	
	public int getNewtime() {
		return this.newtime;
	}



	public void setNewtime(int newtime) {
		this.newtime = newtime;
	}
	
	public int getDeputy() {
		return this.deputy;
	}



	public void setDeputy(int deputy) {
		this.deputy = deputy;
	}
	
	public int getNewmax() {
		return this.newmax;
	}



	public void setNewmax(int newmax) {
		this.newmax = newmax;
	}
	
	public int getClubapplied() {
		return this.clubapplied;
	}



	public void setClubapplied(int clubapplied) {
		this.clubapplied = clubapplied;
	}
	
	public int getAccusetime() {
		return this.accusetime;
	}



	public void setAccusetime(int accusetime) {
		this.accusetime = accusetime;
	}
	
	public int getInvitetime() {
		return this.invitetime;
	}



	public void setInvitetime(int invitetime) {
		this.invitetime = invitetime;
	}
	
	public int getActivelimit() {
		return this.activelimit;
	}



	public void setActivelimit(int activelimit) {
		this.activelimit = activelimit;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ClubTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClubTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ClubTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ClubTemplateVO [  clubLevel=" + clubLevel + ", cluNum=" + cluNum + ", donateMin=" + donateMin + ", donateMax=" + donateMax + ", donate=" + donate + ", donateTime=" + donateTime + ", inviteActive=" + inviteActive + ", signActive=" + signActive + ", signTime=" + signTime + ", convert=" + convert + ", rankTime=" + rankTime + ", clubFound=" + clubFound + ", clubName=" + clubName + ", clubDescribe=" + clubDescribe + ", clubBoard=" + clubBoard + ", clubapply=" + clubapply + ", newtime=" + newtime + ", deputy=" + deputy + ", newmax=" + newmax + ", clubapplied=" + clubapplied + ", accusetime=" + accusetime + ", invitetime=" + invitetime + ", activelimit=" + activelimit + ",]";
	}
}