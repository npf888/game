package com.gameserver.http.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.db.model.HumanEntity;
import com.db.model.UserInfo;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.http.boqu.BoquBillVO;
import com.gameserver.http.boqu.BoquInfoVO;
import com.gameserver.http.json.BoquPayVO;
import com.gameserver.http.util.RequestJSONUtil;
import com.gameserver.player.Player;
import com.gameserver.recharge.handler.RechargeHandlerFactory;
import com.gameserver.recharge.msg.CGValidateOrder;

/**
 * 博趣平台 支付成功之后，调用这个接口
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class BoquPay extends HttpServlet{
	private Logger logger = Loggers.BAZOO;
	// 测试 数据 order_id =  9945173     user_id=4
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			String jsonStr = RequestJSONUtil.getRequestPostStr(req);
			JSONObject json = JSONObject.parseObject(jsonStr);
			
			if(json.getString("action").equals("order")){//充值
				logger.info("[博趣2博趣]---[order]---[开始调用]");
				BoquPayVO boquJSON = JSONObject.parseObject(jsonStr,BoquPayVO.class);
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(boquJSON.getData().getInfo().split("&")[1]));
				if(player == null){
					logger.info("[博趣2博趣]---[当前用户为空:"+boquJSON.getData().getInfo().split("&")[1]+"]");
					resp.setStatus(200);
					return;
				}
				CGValidateOrder CGValidateOrder = new CGValidateOrder();
				CGValidateOrder.setSignature(boquJSON.getAction());
				CGValidateOrder.setPurchaseData(JSONObject.toJSONString(boquJSON.getData()));
				RechargeHandlerFactory.getHandler().handleValidateOrder(player, CGValidateOrder);
				logger.info("[博趣2博趣]---[order]---[处理完毕]");
				resp.setStatus(200);
				return;
				
				
			}else if(json.getString("action").equals("info")){//获取本游戏用户的信息
				logger.info("[博趣2博趣]---[info]---[开始调用][收到的json:"+jsonStr);
				BoquInfoVO boquJSON = JSONObject.parseObject(jsonStr,BoquInfoVO.class);
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(boquJSON.getData().getGame_user_id()));
				JSONObject jb = new JSONObject();
				if(player == null){
					logger.info("[博趣2博趣]---[当前用户在内存 为空:"+boquJSON.getData().getGame_user_id()+"]");
					
					List<HumanEntity> entitys = Globals.getDaoService().getHumanDao().loadHumans(Long.valueOf(boquJSON.getData().getGame_user_id()));
					if(entitys == null || entitys.size() == 0){
						resp.setStatus(500);
						jb.put("msg", "game_user_id："+boquJSON.getData().getGame_user_id()+" 对应的用户为空");
						resp.getWriter().write(jb.toJSONString());
						return;
					}
					UserInfo userInfo = Globals.getDaoService().getUserInfoDao().get(Long.valueOf(boquJSON.getData().getGame_user_id()));
					String type = boquJSON.getData().getType();
					String[] returnTypeStrings = type.split(",");
					for(String t:returnTypeStrings){
						if(t.equals("game_coin")){//游戏币数量
							jb.put("game_coin", entitys.get(0).getGold());
							
						}else if(t.equals("nickname")){//游戏昵称
							jb.put("nickname", entitys.get(0).getName());
							
						}else if(t.equals("username")){//游戏登录用户名
//							jb.put("username", userInfo.getUsername());
							jb.put("username", entitys.get(0).getName());
							
						}else if(t.equals("avatar")){//游戏头像(url)
							if(entitys.get(0).getImg().startsWith("http")){
								jb.put("avatar",entitys.get(0).getImg());
							}else{
								jb.put("avatar", Globals.getServerConfig().getBoquOutDomain()+"/head/"+entitys.get(0).getImg());
							}
							
						}
					}
				}else{
					String type = boquJSON.getData().getType();
					String[] returnTypeStrings = type.split(",");
					for(String t:returnTypeStrings){
						if(t.equals("game_coin")){//游戏币数量
							jb.put("game_coin", player.getHuman().getGold());
							
						}else if(t.equals("nickname")){//游戏昵称
							jb.put("nickname", player.getHuman().getName());
							
						}else if(t.equals("username")){//游戏登录用户名
//							jb.put("username", player.getHuman().getUserInfo().getUsername());
							jb.put("username", player.getHuman().getName());
							
						}else if(t.equals("avatar")){//游戏头像(url)
							if(player.getHuman().getImg().startsWith("http")){
								jb.put("avatar",player.getHuman().getImg());
							}else{
								jb.put("avatar", Globals.getServerConfig().getBoquOutDomain()+"/head/"+player.getHuman().getImg());
							}
							
						}
					}
				}
				resp.getWriter().write(jb.toJSONString());
				logger.info("[博趣2博趣]---[info]---[处理完毕"+jb.toJSONString()+"]");
				
			}else if(json.getString("action").equals("bill")){//游戏币和平台币的兑换
				logger.info("[博趣2博趣]---[bill]---[开始调用]");
				BoquBillVO boquJSON = JSONObject.parseObject(jsonStr,BoquBillVO.class);
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(boquJSON.getData().getGame_user_id()));
				if(player == null){
					logger.info("[博趣2博趣]---[当前用户为空:"+boquJSON.getData().getGame_user_id()+"]");
					resp.setStatus(200);
					return;
				}
				//验证 TODO 一定验证 平台的用户和本游戏的用户是否统一
				int gameCoin = boquJSON.getData().getGame_coin();
				if(gameCoin > 0){
					player.getHuman().giveMoney(gameCoin, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BOQU_GIVE_GOLD, LogReasons.GoldLogReason.BAZOO_BOQU_GIVE_GOLD.toString(), -1, 1);
					resp.setStatus(200);
					logger.info("[博趣2博趣]---[bill]---[当前用户："+player.getPassportId()+"]---[增加成功]");
				}else{
					logger.info("[博趣2博趣]---[bill]---[用户金币："+player.getHuman().getGold()+"]---[博趣传过来的金币："+Long.valueOf(Math.abs(gameCoin)).longValue()+"]");
					if(player.getHuman().getGold()>Long.valueOf(Math.abs(gameCoin)).longValue()){
						player.getHuman().costMoney(Math.abs(gameCoin), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BOQU_GIVE_GOLD, LogReasons.GoldLogReason.BAZOO_BOQU_GIVE_GOLD.toString(), -1, 1);
						logger.info("[博趣2博趣]---[bill]---[当前用户："+player.getPassportId()+"]---[扣除成功]");
						resp.setStatus(200);
					}else{
						resp.setStatus(500);
						logger.info("[博趣2博趣]---[bill]---[当前用户："+player.getPassportId()+"]---[金币不足]");
						
					}
				}
				logger.info("[博趣2博趣]---[bill]---[处理完毕]");
			}
			
			
		} catch (IOException e) {
			logger.error("接收博趣平台支付完成后 异常：",e);
		}
	}
	
	
}
