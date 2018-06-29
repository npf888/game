package com.core.uuid;

import java.util.concurrent.atomic.AtomicLong;

public class MyUUID64 {
	private static final int MAX_BITS=63;
	//时间分片
	private final int timeBits =41;
	private final int shareBits = 12;
	private final int seqBits = 10;
	private int max = 1<<10-1;
	private long shareNum; 
			
	private AtomicLong oid;
	
	public MyUUID64(long sid)
	{
		oid = new AtomicLong();
		oid.set(0);
		sid = sid%(2<<shareBits);
		shareNum = sid <<seqBits;
	}
		
	public long getNextUUID(long now)
	{
		final long _curId = this.oid.incrementAndGet();
		if(_curId >max-1 )
		{
			this.oid.set(0);
		}
		return now<<(MAX_BITS-timeBits) | shareNum |_curId;
	}

	public static MyUUID64 buildDefaultUUID(int sid) {
		// TODO Auto-generated method stub	
		return new MyUUID64(sid);
	}

	public long getCurUUID(long now) {
		// TODO Auto-generated method stub
		return getNextUUID(now);
	}
	
	
}
