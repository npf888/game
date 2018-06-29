package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 老虎机
 * @author wayne
 *
 */
@Entity
@Table(name = "t_slot_list")
public class SlotListEntity implements BaseEntity<Long>{
	
	private static final long serialVersionUID = -3380175577169459616L;
	private long id;
	private String langDesc;
	private int payLinesNum;
	private int bet1;
	private int bet2;
	private int bet3;
	private int bet4;
	private int bet5;
	private int type;
	private int bet1Lv;
	private int bet2Lv;
	private int bet3Lv;
	private int bet4Lv;
	private int bet5Lv;
	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	@Column
	public String getLangDesc() {
		return langDesc;
	}
	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	@Column
	public int getPayLinesNum() {
		return payLinesNum;
	}
	public void setPayLinesNum(int payLinesNum) {
		this.payLinesNum = payLinesNum;
	}
	@Column
	public int getBet1() {
		return bet1;
	}
	public void setBet1(int bet1) {
		this.bet1 = bet1;
	}
	@Column
	public int getBet2() {
		return bet2;
	}
	public void setBet2(int bet2) {
		this.bet2 = bet2;
	}
	@Column
	public int getBet3() {
		return bet3;
	}
	public void setBet3(int bet3) {
		this.bet3 = bet3;
	}
	@Column
	public int getBet4() {
		return bet4;
	}
	public void setBet4(int bet4) {
		this.bet4 = bet4;
	}
	@Column
	public int getBet5() {
		return bet5;
	}
	public void setBet5(int bet5) {
		this.bet5 = bet5;
	}
	@Column
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Column
	public int getBet1Lv() {
		return bet1Lv;
	}
	public void setBet1Lv(int bet1Lv) {
		this.bet1Lv = bet1Lv;
	}
	@Column
	public int getBet2Lv() {
		return bet2Lv;
	}
	public void setBet2Lv(int bet2Lv) {
		this.bet2Lv = bet2Lv;
	}
	@Column
	public int getBet3Lv() {
		return bet3Lv;
	}
	public void setBet3Lv(int bet3Lv) {
		this.bet3Lv = bet3Lv;
	}
	@Column
	public int getBet4Lv() {
		return bet4Lv;
	}
	public void setBet4Lv(int bet4Lv) {
		this.bet4Lv = bet4Lv;
	}
	@Column
	public int getBet5Lv() {
		return bet5Lv;
	}
	public void setBet5Lv(int bet5Lv) {
		this.bet5Lv = bet5Lv;
	}
	

	
}
