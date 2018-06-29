package com.gameserver.recharge.async;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.async.ILocalRelativeOperation;
import com.game.webserver.login.AbstractParams;
import com.gameserver.player.Player;

/**
 * 异步http
 * @author wayne
 *
 * @param <T>
 */
public class AsyncHttpOperation<T> implements ILocalRelativeOperation {

	private Logger logger = Loggers.httpLogger;
	private Player player;
	private IAsyncHttpCallBack<T> callBack;
	private AbstractParams<T> params;
	
	public AsyncHttpOperation(Player player,AbstractParams<T> params,IAsyncHttpCallBack<T> callBack){
		this.player = player;
		this.callBack = callBack;
		this.params = params;
	}
	


	public Player getPlayer() {
		return player;
	}



	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		params.send();
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		if(callBack!=null){
			if(params.getErrorCode()!=0){
				callBack.onFinished(this.getPlayer(),params.getErrorCode(),null);
			}
			else
				callBack.onFinished(this.getPlayer(),params.getErrorCode(),params.getRes());
		}
		
		return STAGE_STOP_DONE;
	}
	
	/**
	 * PlayerAuthOperation 回调接口
	 * 
	 * @author Thinker
	 *
	 */
	public static interface IAsyncHttpCallBack<T>
	{
		/**
		 * 异步操作完成
		 * 
		 * @param result
		 */
		void onFinished(Player player,int errorCode,T res);
	}

}
