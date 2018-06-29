package com.gameserver.slot;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.SlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.redis.enums.RedisSlotKey;
import com.gameserver.slot.redismsg.ChangSpinTimes;
import com.gameserver.slot.redismsg.ChangStock;
import com.gameserver.slot.redismsg.SynchSlot;

public class Slot implements PersistanceObject<Long, SlotEntity>{

	/** 生命期 */
	private final LifeCycle lifeCycle;
	/**id  UUID*/
	private long id;
	/**老虎机      实际上是老虎机的ID*/
	private int slotTempleId;
	/** 彩金 */
	private long jackpot;
	
	/** 东方龙奖池 */
	private String slotType23Jackpot;
	
	/**累计彩金 **/
	private long cumuJackpot;
	
	/** 这个老虎机总的押注量  */
	private long stock;
	
	/**
	 * 新的彩金 字段 各五个
	 */
	private long jackpot1;
	private long jackpot2;
	private long jackpot3;
	private long jackpot4;
	private long jackpot5;
	private long cumuJackpot1;
	private long cumuJackpot2;
	private long cumuJackpot3;
	private long cumuJackpot4;
	private long cumuJackpot5;
	
	
	
	/** 这个老虎机总的旋转次数 **/
	private long spinTimes;
	
	private long updateTime;
	private long createTime;
	
	/** 老虎机游戏类型  这个字段不入库  **/
	private int type;
	
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	

	public long getJackpot1() {
		return jackpot1;
	}

	public void setJackpot1(long jackpot1) {
		this.jackpot1 = jackpot1;
	}

	public long getJackpot2() {
		return jackpot2;
	}

	public void setJackpot2(long jackpot2) {
		this.jackpot2 = jackpot2;
	}

	public long getJackpot3() {
		return jackpot3;
	}

	public void setJackpot3(long jackpot3) {
		this.jackpot3 = jackpot3;
	}

	public long getJackpot4() {
		return jackpot4;
	}

	public void setJackpot4(long jackpot4) {
		this.jackpot4 = jackpot4;
	}

	public long getJackpot5() {
		return jackpot5;
	}

	public void setJackpot5(long jackpot5) {
		this.jackpot5 = jackpot5;
	}

	public long getCumuJackpot1() {
		return cumuJackpot1;
	}

	public void setCumuJackpot1(long cumuJackpot1) {
		this.cumuJackpot1 = cumuJackpot1;
	}

	public long getCumuJackpot2() {
		return cumuJackpot2;
	}

	public void setCumuJackpot2(long cumuJackpot2) {
		this.cumuJackpot2 = cumuJackpot2;
	}

	public long getCumuJackpot3() {
		return cumuJackpot3;
	}

	public void setCumuJackpot3(long cumuJackpot3) {
		this.cumuJackpot3 = cumuJackpot3;
	}

	public long getCumuJackpot4() {
		return cumuJackpot4;
	}

	public void setCumuJackpot4(long cumuJackpot4) {
		this.cumuJackpot4 = cumuJackpot4;
	}

	public long getCumuJackpot5() {
		return cumuJackpot5;
	}

	public void setCumuJackpot5(long cumuJackpot5) {
		this.cumuJackpot5 = cumuJackpot5;
	}

	public Slot(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	@Override
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id =id;
	}

	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "slot#"+this.id;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}

	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	

	public String getSlotType23Jackpot() {
		return slotType23Jackpot;
	}

	public void setSlotType23Jackpot(String slotType23Jackpot) {
		this.slotType23Jackpot = slotType23Jackpot;
	}

	/**
	 * @return the slotType
	 */
	public int getTempleId() {
		return slotTempleId;
	}

	/**
	 * @param slotType the slotType to set
	 */
	public void setTempleId(int slotTempleId) {
		this.slotTempleId = slotTempleId;
	}

	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the jackpot
	 */
	public long getJackpot() {
		return jackpot;
	}

	/**
	 * @param jackpot the jackpot to set
	 */
	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	/**
	 * @return the stock
	 */
	public long getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(long stock) {
		this.stock = stock;
	}

	/**
	 * @return the spinTimes
	 */
	public long getSpinTimes() {
		return spinTimes;
	}

	/**
	 * @param spinTimes the spinTimes to set
	 */
	public void setSpinTimes(long spinTimes) {
		this.spinTimes = spinTimes;
	}

	
	public long getCumuJackpot() {
		return cumuJackpot;
	}

	public void setCumuJackpot(long cumuJackpot) {
		this.cumuJackpot = cumuJackpot;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Override
	public SlotEntity toEntity() {
		// TODO Auto-generated method stub
		SlotEntity slotEntity = new SlotEntity();
		slotEntity.setId(this.getDbId());
		slotEntity.setJackpot(this.getJackpot());
		slotEntity.setSlotType(this.getTempleId());
		slotEntity.setStock(this.getStock());
		slotEntity.setSpinTimes(this.getSpinTimes());
		slotEntity.setUpdateTime(this.getUpdateTime());
		slotEntity.setCreateTime(this.getCreateTime());
		slotEntity.setCumuJackpot(cumuJackpot);
		slotEntity.setSlotType23Jackpot(this.getSlotType23Jackpot());
		slotEntity.setJackpot1(this.getJackpot1());
		slotEntity.setJackpot2(this.getJackpot2());
		slotEntity.setJackpot3(this.getJackpot3());
		slotEntity.setJackpot4(this.getJackpot4());
		slotEntity.setJackpot5(this.getJackpot5());
		slotEntity.setCumuJackpot1(this.getCumuJackpot1());
		slotEntity.setCumuJackpot2(this.getCumuJackpot2());
		slotEntity.setCumuJackpot3(this.getCumuJackpot3());
		slotEntity.setCumuJackpot4(this.getCumuJackpot4());
		slotEntity.setCumuJackpot5(this.getCumuJackpot5());
		return slotEntity;
	}
	
	

	@Override
	public void fromEntity(SlotEntity entity) {
		// TODO Auto-generated method stub
		this.setDbId(entity.getId());
		this.setJackpot(entity.getJackpot());
		this.setTempleId(entity.getSlotType());
		this.setSlotType23Jackpot(entity.getSlotType23Jackpot());
		this.setStock(entity.getStock());
		this.setSpinTimes(entity.getSpinTimes());
		this.setCumuJackpot(entity.getCumuJackpot());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setJackpot1(entity.getJackpot1());
		this.setJackpot2(entity.getJackpot2());
		this.setJackpot3(entity.getJackpot3());
		this.setJackpot4(entity.getJackpot4());
		this.setJackpot5(entity.getJackpot5());
		this.setCumuJackpot1(entity.getCumuJackpot1());
		this.setCumuJackpot2(entity.getCumuJackpot2());
		this.setCumuJackpot3(entity.getCumuJackpot3());
		this.setCumuJackpot4(entity.getCumuJackpot4());
		this.setCumuJackpot5(entity.getCumuJackpot5());
		this.setInDb(true);
		this.active();
	}
	
	
	
	
	/**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}


	@Override
	public void setModified() {
		//服务器ID 
		if(Globals.getServerComm().isAuthority()){
			onUpdate();
			sendRedis();
		}
	}
	
	public void redisJackpot(){
		    JedisPool pool = Globals.getRedisService().getJedisPool();
			Jedis jedis = pool.getResource();
			try{
				String key = RedisKey.SLOT.getKey()+slotTempleId;
				Map<String,String> map = new HashMap<String,String>();
				map.put(RedisSlotKey.jackpot.getKey(), String.valueOf(jackpot));
				map.put(RedisSlotKey.cumuJackpot.getKey(), String.valueOf(cumuJackpot));
				jedis.hmset(key, map);
			/*	
				jedis.hset(key, RedisSlotKey.jackpot.getKey(),String.valueOf(jackpot));   
				jedis.hset(key, RedisSlotKey.cumuJackpot.getKey(),String.valueOf(cumuJackpot));*/  
			}finally{
				jedis.close();
//				pool.returnResourceObject(jedis);
			}
	}
	public void redisStock(){
		JedisPool pool = Globals.getRedisService().getJedisPool();
		Jedis jedis = pool.getResource();
		try{
			String key = RedisKey.SLOT.getKey()+slotTempleId;
			Map<String,String> map = new HashMap<String,String>();
			map.put(RedisSlotKey.stock.getKey(), String.valueOf(stock));
			jedis.hmset(key, map);
			//jedis.hset(key, RedisSlotKey.stock.getKey(),String.valueOf(stock));   
		}finally{
			jedis.close();
//			pool.returnResourceObject(jedis);
		}
	}

	public void redisSpinTimes(){
		JedisPool pool = Globals.getRedisService().getJedisPool();
		Jedis jedis = pool.getResource();
		try{
			String key = RedisKey.SLOT.getKey()+slotTempleId;
			Map<String,String> map = new HashMap<String,String>();
			map.put(RedisSlotKey.spinTimes.getKey(), String.valueOf(spinTimes));
			jedis.hmset(key, map);
			//jedis.hset(key, RedisSlotKey.spinTimes.getKey(),String.valueOf(spinTimes));   
		}finally{
			jedis.close();
//			pool.returnResourceObject(jedis);
		}
		
		
	}
	
	private void sendRedis(){
		SynchSlot ss = new SynchSlot();
		ss.setSlotTemplaId(slotTempleId);
		Globals.getRedisService().broadcastRedisMsg(ss);
	}
	
	/**
	 * 同步redis数据
	 */
	public void getRedis(){
		if(!Globals.getServerComm().isAuthority()){
			 JedisPool pool = Globals.getRedisService().getJedisPool();
				Jedis jedis = pool.getResource();
				try{
					String key = RedisKey.SLOT.getKey()+slotTempleId;
					String jackpotr = jedis.hget(key, RedisSlotKey.jackpot.getKey());   
					String cumuJackpotr = jedis.hget(key, RedisSlotKey.cumuJackpot.getKey());   
					String stockr = jedis.hget(key, RedisSlotKey.stock.getKey());   
					String spinTimesr = jedis.hget(key, RedisSlotKey.spinTimes.getKey()); 
					setJackpot(Long.valueOf(jackpotr));
					setCumuJackpot(Long.valueOf(cumuJackpotr));
					setStock(Long.valueOf(stockr));
					setSpinTimes(Long.valueOf(spinTimesr));
				}finally{
					jedis.close();
//					pool.returnResourceObject(jedis);
				}
				
		}
	}
	

	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{

		// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive())
		{
			//全球管理器的更新，不属于个人操作
			this.updateTime = Globals.getTimeService().now();
			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}
	
	
	/** 判断是否是最小服务器ID，不是就发广播给最小服务器ID **/
	public void spin(){
		if(Globals.getServerComm().isAuthority()){
			this.spinTimes+=1;
			this.setModified();
			redisSpinTimes();
		}else{
			ChangSpinTimes cs = new ChangSpinTimes();
			cs.setSlotTemplaId(slotTempleId);
			String serverId = Globals.getServerComm().getMinServerId();
			Globals.getRedisService().sendRedisMsgToServer(serverId, cs);
		}
	}
	
	/**
	 * 
	 * @param bet
	 */
	public void bet(long bet){
		
		if(Globals.getServerComm().isAuthority()){
			this.stock +=bet;
			this.setModified();
			redisStock();
		}else{
			sendRedis(true,bet);
		}
	}
	
	private void sendRedis(boolean fly,long num){
		ChangStock cs = new ChangStock();
		cs.setChangStock(num);
		cs.setFly(fly);
		cs.setSlotTemplaId(slotTempleId);
		String serverId = Globals.getServerComm().getMinServerId();
		Globals.getRedisService().sendRedisMsgToServer(serverId, cs);
	}
	
	/**
	 * 
	 * @param refund
	 */
	public void refund(long refund){
		if(Globals.getServerComm().isAuthority()){
			this.stock -=refund;
			this.setModified();
			redisStock();
		}else{
			sendRedis(true,refund);
		}
	}
	

}
