package com.db.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 活动数据
 * @author wayne
 *
 */

@Entity
@Table(name = "t_human_activity_data")
public class HumanActivityDataEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4097731667463350156L;
	private long id;
	
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

}
