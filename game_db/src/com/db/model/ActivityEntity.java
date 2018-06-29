package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 活动
 * @author wayne
 *
 */
@Entity
@Table(name = "t_activity")
public class ActivityEntity  implements BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3380175577169459614L;

	private long id;
	private int activityType;
	private int pageLink;
	private String title;
	private String title_cn;
	private String title_tw;
	private String activityDesc;
	private String activityDesc_cn;
	private String activityDesc_tw;
	private String pic;
	private String hall_pic_tw;
	private String hall_pic_cn;
	private String hall_pic;
	private String pic_cn;
	private String pic_tw;
	private String rulePack;
	private int daily;
	private long startTime;
	private long endTime;
	private long updateTime;
	private long createTime;
	
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Column
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the pageLink
	 */
	@Column
	public int getPageLink() {
		return pageLink;
	}

	/**
	 * @param pageLink the pageLink to set
	 */
	public void setPageLink(int pageLink) {
		this.pageLink = pageLink;
	}

	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column
	public String getTitle_cn() {
		return title_cn;
	}

	public void setTitle_cn(String title_cn) {
		this.title_cn = title_cn;
	}

	@Column
	public String getTitle_tw() {
		return title_tw;
	}

	public void setTitle_tw(String title_tw) {
		this.title_tw = title_tw;
	}

	@Column
	public String getActivityDesc_tw() {
		return activityDesc_tw;
	}

	public void setActivityDesc_tw(String activityDesc_tw) {
		this.activityDesc_tw = activityDesc_tw;
	}

	@Column
	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String desc) {
		this.activityDesc = desc;
	}

	@Column
	public String getActivityDesc_cn() {
		return activityDesc_cn;
	}

	public void setActivityDesc_cn(String activityDesc_cn) {
		this.activityDesc_cn = activityDesc_cn;
	}

	@Column
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column
	public String getPic_cn() {
		return pic_cn;
	}

	public void setPic_cn(String pic_cn) {
		this.pic_cn = pic_cn;
	}

	@Column
	public String getPic_tw() {
		return pic_tw;
	}

	public void setPic_tw(String pic_tw) {
		this.pic_tw = pic_tw;
	}

	@Column
	public String getRulePack() {
		return rulePack;
	}

	public void setRulePack(String rulePack) {
		this.rulePack = rulePack;
	}

	
	@Column
	public int getDaily() {
		return daily;
	}

	public void setDaily(int daily) {
		this.daily = daily;
	}

	@Column
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Column
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}



	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	@Column
	public String getHall_pic_tw() {
		return hall_pic_tw;
	}

	public void setHall_pic_tw(String hall_pic_tw) {
		this.hall_pic_tw = hall_pic_tw;
	}
	@Column
	public String getHall_pic_cn() {
		return hall_pic_cn;
	}

	public void setHall_pic_cn(String hall_pic_cn) {
		this.hall_pic_cn = hall_pic_cn;
	}
	@Column
	public String getHall_pic() {
		return hall_pic;
	}

	public void setHall_pic(String hall_pic) {
		this.hall_pic = hall_pic;
	}

}
