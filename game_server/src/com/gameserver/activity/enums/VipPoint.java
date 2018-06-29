package com.gameserver.activity.enums;

public enum VipPoint {

	 	POINT(6);
	   
	    private int value;
	 
	    private VipPoint(int value) {
	        this.value = value;
	    }
	    
	    public int value() {  
	        return this.value;  
	    }
	 
}