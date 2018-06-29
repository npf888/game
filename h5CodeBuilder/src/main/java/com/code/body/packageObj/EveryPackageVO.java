package com.code.body.packageObj;

import java.util.ArrayList;
import java.util.List;

/**
 * 每个包的对象
 * @author JavaServer
 *
 */
public class EveryPackageVO {

	//当前的包名 例如achievement,activity
	private String fileName;
	// fileName 这个包下的msg和data
	private List<Object> datas = new ArrayList<Object>();
	private List<Object> msgs = new ArrayList<Object>();
	private List<Object> msgWithUnderlines = new ArrayList<Object>();
	public List<Object> getDatas() {
		return datas;
	}
	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}
	public List<Object> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<Object> msgs) {
		this.msgs = msgs;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<Object> getMsgWithUnderlines() {
		return msgWithUnderlines;
	}
	public void setMsgWithUnderlines(List<Object> msgWithUnderlines) {
		this.msgWithUnderlines = msgWithUnderlines;
	}
	
	
	
	public void addUnderLineMsg(String msg){
		if(msg.startsWith("CG")){
			String one = msg.replace("CG", "");
			String finalOne = insertUnderLine(one,"CG");
			msgWithUnderlines.add(finalOne);
		}else{//GC
			String one = msg.replace("GC", "");
			String finalOne = insertUnderLine(one,"GC");
			msgWithUnderlines.add(finalOne);
		}
	}
	
	private String insertUnderLine(String one,String start){
		List<Integer> upperIndexList = new ArrayList<Integer>();
		for(int i=0;i<one.length();i++){
			Character c = one.charAt(i);
			if(Character.isUpperCase(c)){
				upperIndexList.add(i);
			}
		}
		StringBuilder sb = new StringBuilder(one);
		for(int i=0;i<upperIndexList.size();i++){
			Integer index = upperIndexList.get(i);
			sb.insert(index+i, "_");
		}
		sb.insert(0, start);
		return sb.toString();
	}
	
}
