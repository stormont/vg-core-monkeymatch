package com.voyagegames.weatherroute.core;

public interface ILogger {
	
	public void log(String tag, String msg);
	public void log(String tag, String msg, Exception e);

}
