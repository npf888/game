package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name="t_human_newbie")
public class NewbieEntity implements BaseEntity<Long>{

	private static final long serialVersionUID = 3008914102501509819L;

	/** 主键 */
	private Long id;
	/** 所属角色ID */
	private long charId;
	/** 发送角色ID*/
	private int stepId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Column
	public int getStepId() {
		return stepId;
	}

	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	
}
