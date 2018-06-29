package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.common.InitializeRequired;
import com.core.util.TimeUtils;
import com.db.model.FriendEntity;
import com.db.model.FriendRequestEntity;
import com.db.model.HumanFriendGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.relation.Friend;
import com.gameserver.relation.FriendGift;
import com.gameserver.relation.FriendRequest;

public class HumanRelationManager implements RoleDataHolder, InitializeRequired{
	private static final int MAX_KEEP_DAY = 7;
	/** 主人 */
	private Human owner;
	/** 好友数据库的实体对象  */
	private List<Friend> friendList = new ArrayList<Friend>();
	/**好友请求*/
	private List<FriendRequest> friendRequestList = new ArrayList<FriendRequest>();
	/**好友礼物*/
	private List<FriendGift> friendGiftList = new ArrayList<FriendGift>();

	private Map<Long,Long> friendYaoqiTime = new HashMap<Long,Long>(); 
	
	public HumanRelationManager(Human owner){
		this.owner=owner;
	}
	
	public Human getOwner()
	{
		return owner;
	}
	
	/**
	 * 加载好友信息
	 * 进入游戏时调用
	 */
	public void load()
	{

		loadFriends();
		loadFriendRequests();
		loadFriendGifts();
	}
	
	/**
	 * 加载好友
	 */
	private void loadFriends(){
		List<FriendEntity> friendEntityList= Globals.getDaoService().getHumanFriendDao().getFriendListByCharId(owner.getPassportId());
		
		
		if(friendEntityList!=null && friendEntityList.size()!=0)
		{
			for(FriendEntity friendEntity:friendEntityList)
			{
				Friend friend=new Friend();
				friend.setOwner(owner);
				friend.fromEntity(friendEntity);
				friendList.add(friend);
				

				
				
			}
			
			//删除已经成为好友的ID 剩下的是 对方还没有通过的请求
			List<String> shuldBeRemovedIds = new ArrayList<String>(); 
			List<String> allRequestIds = null; 
			//所有的好友 如果requestFriendIds 里还有
			String ids = owner.getRequestFriendIds();
			if(StringUtils.isNotBlank(ids)){
				String[] idArr = ids.split(",");
				allRequestIds= Arrays.asList(idArr);
				for(int i=0;i<idArr.length;i++){
					for(Friend friend:friendList){
						long id = Long.valueOf(idArr[i]).longValue();
						long friendId = friend.getCharId();
						if(id == friendId){
							shuldBeRemovedIds.add(String.valueOf(id));
						}
					}
				}
			}
			if(allRequestIds != null){
				allRequestIds.remove(shuldBeRemovedIds);
				String newRequestIds = "";
				for(String id :allRequestIds){
					newRequestIds+=id+",";
				}
				owner.setRequestFriendIds(newRequestIds);
				owner.setModified();
			}
		}
		
	}
	
	/**
	 * 加载好友请求
	 */
	private void loadFriendRequests(){
		List<FriendRequestEntity> friendRequestEntityList= Globals.getDaoService().getHumanFriendRequestDao().getFriendRequestListByCharId(owner.getPassportId());
		
		if(friendRequestEntityList!=null && friendRequestEntityList.size()!=0)
		{
			for(FriendRequestEntity friendRequestEntity:friendRequestEntityList)
			{
				FriendRequest friendRequest=new FriendRequest(owner);
				
				friendRequest.fromEntity(friendRequestEntity);
				friendRequestList.add(friendRequest);
			}
		}
		
	}
	
	/**
	 * 加载好友礼物
	 */
	private void loadFriendGifts(){
		
		List<HumanFriendGiftEntity> friendGiftEntityList= Globals.getDaoService().getHumanFriendGiftDao().getFriendGiftListByCharId(owner.getPassportId());
		
		
		if(friendGiftEntityList!=null && friendGiftEntityList.size()!=0)
		{
			for(HumanFriendGiftEntity humanFriendGiftEntity:friendGiftEntityList)
			{
				FriendGift friendGift = new FriendGift(owner);
				
				friendGift.fromEntity(humanFriendGiftEntity);
				this.getFriendGiftList().add(friendGift);
			}
		}
		
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		//判断请求数量是否超过
		Iterator<FriendRequest> iterator = friendRequestList.iterator();
		int i=0;
		long now = Globals.getTimeService().now();
		while(iterator.hasNext())
		{
			FriendRequest friendRequest = iterator.next();
			long duration = now - friendRequest.getCreateTime();
			if(i>=Globals.getConfigTempl().getFriendRequestNum() || duration>Globals.getConfigTempl().getFriendRequestTime()*TimeUtils.DAY)
			{
				friendRequest.onDelete();
				iterator.remove();
				continue;
			}
			i++;
		}
		
		checkFriendGifts();
	}
	
	//检查好友礼物
	private void checkFriendGifts(){
		//判断好友礼物
		Iterator<FriendGift> iterator = friendGiftList.iterator();
	
		long now = Globals.getTimeService().now();
		while(iterator.hasNext())
		{
			FriendGift friendGift = iterator.next();
			int day = TimeUtils.daysBetween(friendGift.getCreateTime(), now);
			if( day>MAX_KEEP_DAY)
			{
				friendGift.onDelete();
				iterator.remove();
				continue;
			}
			
		}
	}
	
	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
	public List<Friend> getFriendList()
	{
		return friendList;
	}
	
	public List<FriendRequest> getFriendRequestList(){
		return friendRequestList;
	}
	
	public List<FriendGift> getFriendGiftList(){
		return friendGiftList;
	}
	
	
	
	public Map<Long, Long> getFriendYaoqiTime() {
		return friendYaoqiTime;
	}

	//移除好友
	public void removeFriend(long passportId)
	{
		Iterator<Friend> iter =  friendList.iterator();
		while(iter.hasNext())
		{
			if(iter.next().getFriendId() == passportId)
			{
				iter.remove();
				break;
			}
		}
	}
	
	//移除好友请求
	public void removeFriendRequest(FriendRequest friendRequest)
	{
		Iterator<FriendRequest> iter =  friendRequestList.iterator();
		while(iter.hasNext())
		{
			if(iter.next() == friendRequest)
			{
				iter.remove();
				friendRequest.onDelete();
				break;
			}
		}
	}
	
	//检查是否已经添加过好友
	public boolean checkIfAddFriend(long friendId)
	{
		Friend friend = friendById(friendId);
		return friend!=null;
	}
	
	
	
	/**
	 * 查找好友
	 * @param friendId
	 * @return
	 */
	public Friend friendById(long friendId)
	{
		Iterator<Friend> iter =  friendList.iterator();
		while(iter.hasNext())
		{
			Friend friend = iter.next();
			if(friend.getFriendId() == friendId)
			{
				return friend;
			}
		}
		return null;
	}
	

	
	//检查是否有好友请求
	public FriendRequest friendRequestById(long requestId)
	{
		Iterator<FriendRequest> iter =  friendRequestList.iterator();
		while(iter.hasNext())
		{
			FriendRequest friendRequest = iter.next();
			if(friendRequest.getSendId() == requestId)
			{
				return friendRequest;
			}
		}
		return null;
	}
	
	/**
	 * 获取礼物
	 * @param giftId
	 * @return
	 */
	public FriendGift friendGiftById(long giftId){
		Iterator<FriendGift> iter =  friendGiftList.iterator();
		while(iter.hasNext())
		{
			FriendGift friendGift = iter.next();
			if(friendGift.getDbId() == giftId)
			{
				return friendGift;
			}
		}
		return null;
	}
	
	//添加好友请求
	public void addFriendRequest(FriendRequest friendRequest)
	{
		this.getFriendRequestList().add(0, friendRequest);
	}
	
	
	/**
	 * 好友请求
	 * @return
	 */
	public FriendRequest friendRequestByFriendId(long friendId)
	{
		for(FriendRequest tempFriendRequest : this.getFriendRequestList()){
			if(tempFriendRequest.getSendId() == friendId){
				return tempFriendRequest;
			}
		}
		return null;
	}
	
	//检查好友上限
	public boolean checkIfIsFull()
	{
		return Globals.getConfigTempl().getFriendsNum()<=friendList.size();
	}




}
