package com.gameserver.texas.data.vip;

import com.gameserver.texas.TexasRoom;


/**
 * vip房间数据
 * @author wayne
 *
 */
public class TexasVipInfoData {
	private long id;
	private int tId;
	private int secure;
	private int num;
	private String name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public int getSecure() {
		return secure;
	}
	public void setSecure(int secure) {
		this.secure = secure;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTId(int tId) {
		// TODO Auto-generated method stub
		this.tId = tId;
	}
	
	public int getTId() {
		// TODO Auto-generated method stub
		return tId;
	}
	
	public static TexasVipInfoData convertFromTexasRoom(TexasRoom texasRoom){
		TexasVipInfoData texasVipInfoData = new TexasVipInfoData();
		texasVipInfoData.setId(texasRoom.getRid());
		texasVipInfoData.setTId(texasRoom.getId());
		if(texasRoom.getPassword().length()==0)
			texasVipInfoData.setSecure(0);
		else
			texasVipInfoData.setSecure(1);
		texasVipInfoData.setNum(texasRoom.getTablePlayerNum());
		texasVipInfoData.setName(texasRoom.getName());
		return texasVipInfoData;
	}
	
}
