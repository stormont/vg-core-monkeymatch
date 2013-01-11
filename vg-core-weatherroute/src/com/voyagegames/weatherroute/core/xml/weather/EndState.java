package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class EndState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "end";
	
	public EndState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return false;
	}

	@Override
	public String performTransition() {
		return super.performTransition();
	}

}
