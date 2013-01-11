package com.voyagegames.weatherroute.core.xml.weather.tests;

import com.voyagegames.weatherroute.core.ILogger;

public class Logger implements ILogger {

	@Override
	public void log(final String tag, final String msg) {
		System.out.println(tag + " - " + msg);
	}

	@Override
	public void log(final String tag, final String msg, final Exception e) {
		System.out.println(tag + " - " + msg);
		e.printStackTrace();
	}
	
}
