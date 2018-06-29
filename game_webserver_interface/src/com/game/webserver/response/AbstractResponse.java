package com.game.webserver.response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractResponse implements IResponse{
	protected boolean success = false;

    private int errorCode = -1;

    protected int parasNum = -1;
    protected long useTime;
    
    public AbstractResponse(String[] args, int parasNum)
    {
      if ("0".equalsIgnoreCase(args[0])) {
        if (args.length != parasNum) {
          Logger logger = LoggerFactory.getLogger("local");
          logger.error("#COM.GAME.WEBSERVER.isOK.RESPONSE.isERROR" + args.length);
        }
        this.success = true;
      } else {
        this.errorCode = Integer.valueOf(args[0]).intValue();
      }
    }
    
	@Override
	public boolean isSuccess() {
		return this.success;
	}

	@Override
	public int getErrorCode() {
		return this.errorCode;
	}

	@Override
	public long getUseTime() {
		return this.useTime;
	}

	@Override
	public void setUseTime(long paramLong) {
		this.useTime=paramLong;
	}

	@Override
	public void onSuccess(String[] paramArrayOfString) {
		// TODO Auto-generated method stub
		
	}

}
