package com.matchstick.study.netty.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author MatchstickShi
 */
public class UnixTime
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private final long value;
	
	public UnixTime()
	{
		this(System.currentTimeMillis() / 1000L + 2208988800L);
	}

	/**
	 * @param value
	 */
	public UnixTime(long value)
	{
		this.value = value;
	}
	
	public long value()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return sdf.format(new Date((value() - 2208988800L) * 1000L));
	}
	//test conflict3
}