package com.gameserver.ranknew.pojo;

/**
 * 
 * @author 郭君伟
 *
 */
public class RankCommData {

	/** 角色ID **/
	private long charId;
	
    private int value;
    
    private long win;
    
    
    public RankCommData(){
    	
    }
    
    
    
    
    public RankCommData(long charId,int value,long win){
    	this.charId = charId;
    	this.value = value;
    	this.win =win;
    }
    
	public long getCharId() {
		return charId;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}

	public int getValue() {
		return value;
	}

	public long getWin() {
		return win;
	}




	public void setWin(long win) {
		this.win = win;
	}




	public void setValue(int value) {
		this.value = value;
	}
	
	

	




	@Override
	public String toString() {
		
		return "角色ID ="+charId+" 值= "+value+"||"+win;
	}

	

}
