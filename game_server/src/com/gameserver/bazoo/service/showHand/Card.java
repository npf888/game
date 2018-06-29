package com.gameserver.bazoo.service.showHand;
/**
 * 描述 牌
 * @author JavaServer
 * 	1、豹子 -- 五颗点数相同的色子   --  倍率
 * 	2、炸弹 -- 四颗点数相同的色子 --  倍率
 * 	3、葫芦 -- 三颗点数相同的色子  + 两颗点数相同的色子   --倍率
 * 	4、三条 -- 三颗点数相同的色子 -- 倍率
 * 	5、两对 -- 两颗点数相同的色子  + 两颗点数相同的色子-- 倍率
 * 	6、一对 -- 两颗点数相同的色子 -- 倍率
 * 	7、散点 -- 无法组合成以上牌型的色子-- 倍率
 */
public class Card {

	//牌的名称
	private String cardName;
	//牌的序号
	private int cardNum;
	//总共5个色子
	private int cardPointOne;
	private int cardPointTwo;
	private int cardPointThree;
	private int cardPointFour;
	private int cardPointFive;
	
	public Card(){
		cardNum=-1;
	}
	
	/**
	 * 设置豹子
	 * @param point
	 */
	public void setPanther(int point){
		this.setCardPointOne(point);
		this.setCardPointTwo(point);
		this.setCardPointThree(point);
		this.setCardPointFour(point);
		this.setCardPointFive(point);
	}
	/**
	 * 设置炸弹
	 * @param point
	 */
	public void setBomb(int pointMore,int pointLittle){
		this.setCardPointOne(pointMore);
		this.setCardPointTwo(pointMore);
		this.setCardPointThree(pointMore);
		this.setCardPointFour(pointMore);
		this.setCardPointFive(pointLittle);
	}
	/**
	 * 设置葫芦
	 * @param point
	 */
	public void setGourd(int pointMore,int pointLittle){
		this.setCardPointOne(pointMore);
		this.setCardPointTwo(pointMore);
		this.setCardPointThree(pointMore);
		this.setCardPointFour(pointLittle);
		this.setCardPointFive(pointLittle);
	}
	/**
	 * 设置三条
	 * @param point
	 */
	public void setThree(int pointOne,int pointTwo,int pointThree){
		this.setCardPointOne(pointOne);
		this.setCardPointTwo(pointOne);
		this.setCardPointThree(pointOne);
		this.setCardPointFour(pointTwo);
		this.setCardPointFive(pointThree);
	}
	/**
	 * 设置两对
	 * @param point
	 */
	public void setTwo(int pointOne,int pointTwo,int pointThree){
		this.setCardPointOne(pointOne);
		this.setCardPointTwo(pointOne);
		this.setCardPointThree(pointTwo);
		this.setCardPointFour(pointTwo);
		this.setCardPointFive(pointThree);
	}
	/**
	 * 设置一对
	 * @param point
	 */
	public void setOne(int pointOne,int pointTwo,int pointThree,int pointFour){
		this.setCardPointOne(pointOne);
		this.setCardPointTwo(pointOne);
		this.setCardPointThree(pointTwo);
		this.setCardPointFour(pointThree);
		this.setCardPointFive(pointFour);
	}
	/**
	 * 设置 散点
	 * @param point
	 */
	public void setDisperse(int pointOne,int pointTwo,int pointThree,int pointFour,int pointFive){
		this.setCardPointOne(pointOne);
		this.setCardPointTwo(pointTwo);
		this.setCardPointThree(pointThree);
		this.setCardPointFour(pointFour);
		this.setCardPointFive(pointFive);
	}
	
	/**
	 * 获取5个点数 之和
	 * @return
	 */
	public int getFivePointSum(){
		int sum = this.getCardPointOne()+this.getCardPointTwo()+this.getCardPointThree()+this.getCardPointFour()+this.getCardPointFive();
		return sum;
	}
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public int getCardNum() {
		return cardNum;
	}
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	public int getCardPointOne() {
		return cardPointOne;
	}
	public void setCardPointOne(int cardPointOne) {
		this.cardPointOne = cardPointOne;
	}
	public int getCardPointTwo() {
		return cardPointTwo;
	}
	public void setCardPointTwo(int cardPointTwo) {
		this.cardPointTwo = cardPointTwo;
	}
	public int getCardPointThree() {
		return cardPointThree;
	}
	public void setCardPointThree(int cardPointThree) {
		this.cardPointThree = cardPointThree;
	}
	public int getCardPointFour() {
		return cardPointFour;
	}
	public void setCardPointFour(int cardPointFour) {
		this.cardPointFour = cardPointFour;
	}
	public int getCardPointFive() {
		return cardPointFive;
	}
	public void setCardPointFive(int cardPointFive) {
		this.cardPointFive = cardPointFive;
	}
	
	
	
	
	
}
