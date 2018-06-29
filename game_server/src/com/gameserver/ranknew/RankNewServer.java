package com.gameserver.ranknew;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.RankEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.ranknew.data.RankListData;
import com.gameserver.ranknew.enums.RankKeyType;
import com.gameserver.ranknew.template.ScoreListRewardTemplate;
import com.gameserver.ranknew.template.ScoreListTemplate;
import com.gameserver.redis.RedisService;

/**
 * 
 * @author 郭君伟
 *
 */
public class RankNewServer implements InitializeRequired,AfterInitializeRequired{

	 private Logger logger = Loggers.RankNewServerLogger;
	
     private static final int SINGLE = 100;
	
	/**
	 * key:charId, value:排行数据 
	 */
	private Map<Long,Rank> rankMap = new HashMap<Long,Rank>();

	/**奖励 **/
	private List<ScoreListTemplate> rankList = new ArrayList<ScoreListTemplate>();
	/** **/
	 private Map<Integer,ScoreListTemplate> rankListMap = new HashMap<Integer,ScoreListTemplate>();
	
	/**一周结算 **/
	private Map<int[],ScoreListRewardTemplate> scoreList = new HashMap<int[],ScoreListRewardTemplate>();
	
	private JedisPool jedisPool;

	private RedisService redisService;
	

	
	/**
	 * 组装静态配置数据
	 */
	public void init() {
		
		Map<Integer, ScoreListTemplate> mapData = Globals.getTemplateService().getAll(ScoreListTemplate.class);
		
		for(ScoreListTemplate slt : mapData.values()){
			if(slt.getRewardState() == 1){
				rankList.add(slt);
				rankListMap.put(slt.getId(), slt);
			}
		}
		
		
		
		Map<Integer, ScoreListRewardTemplate> mapRewData = Globals.getTemplateService().getAll(ScoreListRewardTemplate.class);
	    for(Entry<Integer, ScoreListRewardTemplate> en : mapRewData.entrySet()){
	    	 ScoreListRewardTemplate st = en.getValue();
	    	 int[] num = new int[2];
	    	 num[0] = st.getList1();
	    	 num[1] = st.getList2();
	    	 scoreList.put(num, st);
	    }
		
	}

	/**
	 * 装置动态数据
	 */
	public void afterInit() {
		
		redisService = Globals.getRedisService();
		jedisPool = redisService.getJedisPool();
		
		List<RankEntity> list = Globals.getDaoService().getRankDao().getAllRankEntity();
		
		if(list != null && !list.isEmpty()){
			for(RankEntity entity : list){
				
				long totalJackpot = entity.getTotalJackpot();
				long charId = entity.getCharId();
				
				if(Globals.getServerComm().isAuthority()){
					addRank(RankKeyType.RankKeyType6.getKey(),totalJackpot,charId);
				}
				
			}
		}
		
	}
	
	/**
	 * 获取自己的排行榜积分
	 * @param roleId
	 * @return
	 */
	public long getRankIntegral(long roleId){
		if(rankMap.containsKey(roleId)){
			return rankMap.get(roleId).getTotalJackpot();
		}else{
			//查询数据库
			loginData(roleId);
			if(rankMap.containsKey(roleId)){
				return rankMap.get(roleId).getTotalJackpot();
			}else{
				return 0;
			}
		}
	}
	
	/**
	 * 角色上线调用
	 */
	public void loginData(long charId){
		RankEntity entity = Globals.getDaoService().getRankDao().getRankEntity(charId);
		if(entity != null){
			Rank rank = new Rank();
			rank.fromEntity(entity);
			rankMap.put(rank.getCharId(), rank);
		}
		
	}
	
	/**
	 * 角色下线调用
	 */
	public void logoutData(long charId){
		//获取当前排名
		rankMap.remove(charId);
	}
	
	
	
	/**
	 * redis 放入数据
	 * @param key
	 * @param score
	 * @param uId
	 */
	public void addRank(String key,long score,long uId){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null){
			logger.error("更新排行版失败， jedis instance failed");
			return ;
		}
		try{
			jedis.zadd(key, score, String.valueOf(uId));
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		
		
	}
	
	
   //系统内调用方法=====================================================================================================
	
	/**
	 * 百家乐 老虎机 德州扑克 结算的时候调用
	 * @param charId
	 * @param win
	 * @param viplv
	 */
	public void win(Human human,long win){
		if(win <= 0){
			return;
		}
		
		long charId = human.getPassportId();
		
		Rank rank = null;
		if(rankMap.containsKey(charId)){
			rank = rankMap.get(charId);
		}else{
			rank = getRank(charId,win);
			rankMap.put(charId, rank);
		}
//		   long totalJackpot = rank.getTotalJackpot()+win;
		   long totalJackpot = win;
		
			rank.setTotalJackpot(rank.getTotalJackpot()+totalJackpot);
			rank.setModified();//入库
			addRank(RankKeyType.RankKeyType6.getKey(),rank.getTotalJackpot(),charId);
			
			int result = getReward(rank.getTotalJackpot());
			
			if(result != -1){
				 if(!rank.getScoreList().contains(String.valueOf(result))){
					 String str = rank.getScoreList();
					 if(str.equals("")){
						 str = String.valueOf(result);
					 }else{
						 str = ","+result;
					 }
					 rank.setScoreList(str);
					 rank.setModified();
					 //发邮件
					 ScoreListTemplate slt =  this.rankListMap.get(result);
					 //段位
					 int grades = slt.getGrades();
					 List<RandRewardData> list = new ArrayList<RandRewardData>();
					 RandRewardData data = new RandRewardData();
					 data.setRewardCount(slt.getRewardNum());
					 data.setRewardId(slt.getRewardId());
					 list.add(data);
					 sendReward2(human,grades,list);
				 }
			}
	
	}
	
	public int getReward(long totalJackpot){
		for(int i = 0;i < rankList.size();i++){
			ScoreListTemplate st = rankList.get(i);
			if( st.getScoreLower() <= totalJackpot && totalJackpot <= st.getScoreUpper() ){
				return st.getId();
			}
		}
		return -1;
	}
	
	private Rank getRank(long charId,long win){
		Rank rank = new Rank();
		long now = Globals.getTimeService().now();
		rank.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.RANKNEW));
		rank.setCharId(charId);
		rank.setDaySingleJackpot(win);
		rank.setDayTotalJackpot(win);
		rank.setSingleJackpot(win);
		rank.setTotalJackpot(win);
		rank.setViplevel(0);
		rank.setScoreList("");
		rank.setCreateTime(now);
		rank.setInDb(false);
		rank.active();
		rank.setModified();
		return rank;
	}

	/**
	 * 获取排行榜
	 * @param key
	 * @param start
	 * @param end
	 * @param passportId 玩家自己的ID
	 * @return
	 */
	public RankListData[] getRankListData(String key,long start,long end,long passportId){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null){
			logger.error("请求排行版失败， jedis instance failed");
			return null;
		}
		if(end > SINGLE){
			end = SINGLE;
		}
		if(start > SINGLE){
			start = 0;
		}
		List<RankListData> listTuple = new ArrayList<RankListData>();
		List<Long> playerIds = new ArrayList<Long>();
		try{
			Set<Tuple> strSet=jedis.zrevrangeWithScores(key, start, end);
			for(Tuple tuple : strSet){
				long uid = Long.parseLong(tuple.getElement());
				long score = (long)tuple.getScore();
				if(score <= 0){//因为是倒排序 这里是0后面的肯定是0
					break;
				}
				RankListData data = sendData(uid,score);
				playerIds.add(uid);
				listTuple.add(data);
			}
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		
		
		Map<Long,PlayerCacheInfo> playerCacheInfoMap= Globals.getPlayerCacheService().getPlayerCacheMapByIds(playerIds);
		if(playerCacheInfoMap == null){
			logger.error("请求排行版缓存数据失败， jedis instance failed");
			return null;
		}
		
		for(int num = 0;num < listTuple.size();num++){
			RankListData data = listTuple.get(num);
			PlayerCacheInfo playerCacheInfo = playerCacheInfoMap.get(data.getUserId());
			if(playerCacheInfo!=null){
				data.setVipLevel(playerCacheInfo.getVipLevel());
				data.setImg(playerCacheInfo.getImg());
				data.setName(playerCacheInfo.getName());
				data.setLevel((int)playerCacheInfo.getLevel());
				data.setCountries(playerCacheInfo.getCountries());
				data.setRank(num+1);
			}
		}
		return  listTuple.toArray(new RankListData[listTuple.size()]);
	}
	
	/**
	 * 获取自己的排名
	 * @param passportId
	 * @return
	 */
	public int getHumanRankbyId(long passportId){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null){
			logger.error("请求排行版失败， jedis instance failed");
			return 0;
		}
		try{
			Long rank = jedis.zrevrank(RankKeyType.RankKeyType6.getKey(),String.valueOf(passportId));
			if(rank == null){
				return 0;
			}
			return rank.intValue()+1;
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		
	}
	
	public int getDuanWei(long passportId) {
		
		long score = getHumanScorebyId(passportId);
		Collection<ScoreListTemplate> templates = rankListMap.values();
		for(ScoreListTemplate template : templates) {
			if(score > template.getScoreLower() && score <= template.getScoreUpper()) {
				return template.getId();
			}
		}
		return 0;
		
	}
	
	/**
	 * 获取自己的积分
	 * @param passportId
	 * @return
	 */
	public long getHumanScorebyId(long passportId){
		Jedis jedis = jedisPool.getResource();
		if(jedis == null){
			logger.error("请求排行版失败， jedis instance failed");
			return 0;
		}
		try{
			Double score = jedis.zscore(RankKeyType.RankKeyType6.getKey(), String.valueOf(passportId));
			if(score == null){
				return 0;
			}
			return score.longValue();
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		
	}
	

	private RankListData sendData(long uid,long score){
		RankListData data = new RankListData();
		data.setUserId(uid);
		data.setWin(score);
		return data;
	}

	/**
	 * 一周结算排行榜奖励
	 */
	public void execute() {
		
		if(Globals.getServerComm().isAuthority()){
			Jedis jedis = jedisPool.getResource();
			if(jedis == null){
				logger.error("请求排行版失败， jedis instance failed");
				return;
			}
			
			Set<Tuple> sstrSet3 = new HashSet<Tuple>();
			try{
			sstrSet3 = jedis.zrevrangeWithScores(RankKeyType.RankKeyType6.getKey(), 0, SINGLE);
			//清空排行榜
			for(Rank rank:rankMap.values()){
				rank.setTotalJackpot(0l);
				rank.setModified();
			}
			rankMap.clear();
			jedis.del(RankKeyType.RankKeyType6.getKey());
			}finally{
				jedis.close();
//				jedisPool.returnResourceObject(jedis);
			}
		
			 int index = 1;
			 for(Tuple tuple : sstrSet3){
				 long uid = Long.parseLong(tuple.getElement());//角色ID
				 double score =  tuple.getScore();
				 if(score <= 0){
					 break;
				 }
				 List<RandRewardData> list = getRewardDataList(index);
				 if(list.size() == 0){
					 break;
				 }
				 //发邮件
				 sendReward(uid,index,list);
				 index++;
			 }
			 
			 
		}
	}
	
	/**
	 * 获取排行榜对应的奖励
	 * @param index
	 * @return
	 */
	public List<RandRewardData>  getRewardDataList(int index){
		List<RandRewardData> list = new ArrayList<RandRewardData>();
		  for(Entry<int[],ScoreListRewardTemplate> en : scoreList.entrySet()){
			  ScoreListRewardTemplate slt = en.getValue();
			  int[] list1_2 = en.getKey();
			  if(list1_2[0] <= index && index <= list1_2[1]){
				  //发邮件
				  RandRewardData data = new RandRewardData();
				  data.setRewardCount(slt.getRewardNum());
				  data.setRewardId(slt.getReward());
				  list.add(data);
			  }
		  }
		return list;
	}
	
	
	
	
	/**
	 * 发邮件奖励
	 * AAA：排行榜优化
	 * @param rankCommDataList
	 * @param template 模板数据
	 */
	private void sendReward(long charId ,int rank, List<RandRewardData> randRewardDataList){
		String title = String.valueOf(LangConstants.ranknewTitle);
		String strContent = String.valueOf(LangConstants.ranknewcontent)+","+String.valueOf(rank);
		List<Long> listId = new ArrayList<Long>();
		listId.add(charId);
		//发邮件
		MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, randRewardDataList); 
	}
	/**
	 * 发邮件奖励段位
	 * AAA：排行榜优化
	 * @param rankCommDataList
	 * @param template 模板数据
	 */
	private void sendReward2(Human human ,int grades, List<RandRewardData> randRewardDataList){
		  String title = String.valueOf(LangConstants.ranknewTitle1);
		  String strContent = String.valueOf(LangConstants.ranknewcontent2)+","+String.valueOf(grades);
		  List<Long> listId = new ArrayList<Long>();
		  listId.add(human.getPassportId());
		  //发邮件
		  MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, randRewardDataList); 
	}
	
	
	
	/**
	 * 获取自己的排名
	 * @param passportId
	 * @return
	 */
	public String[] getHumanRank(long passportId ){
		 String[] num = new String[]{"99999","999999"};
		 Jedis jedis = jedisPool.getResource();
			if(jedis == null){
				logger.error("请求排行版失败， jedis instance failed");
				return num;
			}
			try{
				long rank = jedis.zrevrank(RankKeyType.RankKeyType1.getKey(),String.valueOf(passportId));
				long rankTotal = jedis.zrevrank(RankKeyType.RankKeyType2.getKey(),String.valueOf(passportId));
				num[0] = String.valueOf(rank);
				num[1] = String.valueOf(rankTotal);
			}finally{
				jedis.close();
//				jedisPool.returnResourceObject(jedis);
			}
		
		 return num;
	}
	/**
	 * 获取自己的排名
	 * @param passportId
	 * @return
	 */
	public String[] getHumanRankToday(long passportId){
		String[] num = new String[]{"99999","999999"};
		Jedis jedis = jedisPool.getResource();
		if(jedis == null){
			logger.error("请求排行版失败， jedis instance failed");
			return num;
		}
		try{
			long rank = jedis.zrevrank(RankKeyType.RankKeyType3.getKey(),String.valueOf(passportId));
			long rankTotal = jedis.zrevrank(RankKeyType.RankKeyType4.getKey(),String.valueOf(passportId));
			num[0] = String.valueOf(rank);
			num[1] = String.valueOf(rankTotal);
		}finally{
			jedis.close();
//			jedisPool.returnResourceObject(jedis);
		}
		return num;
	}
	
	
	
}
