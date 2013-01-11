package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class InitState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "init";
	
	public InitState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(DwmlXmlState.ELEMENT, false));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(DwmlXmlState.ELEMENT, false)) {
			return DwmlXmlState.ELEMENT;
		}
		
		return super.performTransition();
	}

}
