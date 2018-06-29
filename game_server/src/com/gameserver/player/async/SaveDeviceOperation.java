package com.gameserver.player.async;

import java.util.List;

import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.db.dao.DeviceDao;
import com.db.model.DeviceEntity;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 客户端设备信息保存
 * 
 * @author Thinker
 * 
 */
public class SaveDeviceOperation implements IIoOperation 
{
	private Player player;
	/**
	 * 类参数构造器
	 * 
	 * @param passportID
	 * @param passportName
	 * @param deviceType
	 * @param deviceDetail
	 * @param deviceVersion
	 * @param deviceID
	 * @param channelType
	 * @param clientVersion 
	 * 
	 */
	public SaveDeviceOperation(Player player) {
		this.player = player;
	}

	@Override
	public int doIo()
	{
		do
		{
			try
			{
				DeviceDao deviceDao = Globals.getDaoService().getDeviceDao();
				
				List<DeviceEntity> deviceEntitys = deviceDao.queryDeviceByUserid(player.getPassportId());
			
				DeviceEntity deviceEntity = null;
				if (deviceEntitys == null || 
					deviceEntitys.size() == 0) 
				{
					deviceEntity = new DeviceEntity();
					deviceEntity.setServerid(Globals.getServerConfig().getServerId());
					deviceEntity.setUserid(player.getPassportId());
					deviceEntity.setUsername(player.getPassportName());
					deviceEntity.setDeviceMode(player.getDeviceModel());
					deviceEntity.setOsVersion(player.getOsVersion());
					deviceEntity.setDeviceId(player.getDeviceId());
					deviceEntity.setChannelType(player.getChannelType().getIndex());
					deviceEntity.setClientVersion(player.getClientVersion());
					deviceEntity.setDeviceType(player.getDeviceType());
					deviceEntity.setResourceVersion(player.getResourceVersion());
					deviceEntity.setCreateTime(Globals.getTimeService().now());
					deviceEntity.setUpdatetime(Globals.getTimeService().now());
					deviceDao.save(deviceEntity);
					
				} else 
				{
					deviceEntity = deviceEntitys.get(0);
					deviceEntity.setServerid(Globals.getServerConfig().getServerId());
					deviceEntity.setUserid(player.getPassportId());
					deviceEntity.setUsername(player.getPassportName());
					deviceEntity.setDeviceMode(player.getDeviceModel());
					deviceEntity.setOsVersion(player.getOsVersion());
					deviceEntity.setDeviceId(player.getDeviceId());
					deviceEntity.setChannelType(player.getChannelType().getIndex());
					deviceEntity.setClientVersion(player.getClientVersion());
					deviceEntity.setDeviceType(player.getDeviceType());
					deviceEntity.setResourceVersion(player.getResourceVersion());
					deviceEntity.setUpdatetime(Globals.getTimeService().now());
			
					deviceDao.update(deviceEntity);
				}
			}catch(Exception ex)
			{
				Loggers.playerLogger.error("SaveDeviceOperation.doIo :" + ex);
			}
		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStart()
	{		
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() 
	{
		return STAGE_STOP_DONE;
	}
}
