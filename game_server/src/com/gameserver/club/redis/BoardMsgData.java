package com.gameserver.club.redis;

public class BoardMsgData
{
//	private String id;
	private long passportId;
	private String note;
	private int noteType;
	private int giftId;
	private String name;
	private String img;
	private String country;
	private int level;
	private int vipLevel;
	private int zhiwu;
//	private long ts;
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getNoteType() {
		return noteType;
	}
	public void setNoteType(int noteType) {
		this.noteType = noteType;
	}
	public int getGiftId() {
		return giftId;
	}
	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}
//	public long getTs() {
//		return ts;
//	}
//	public void setTs(long ts) {
//		this.ts = ts;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public int getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}
}