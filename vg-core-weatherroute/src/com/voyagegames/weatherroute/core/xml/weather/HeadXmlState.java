package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class HeadXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "head";
	
	public HeadXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (this.mIncomingElement.matchTransition(HeadXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (this.mIncomingElement.matchTransition(HeadXmlState.ELEMENT, true)) {
			return DwmlXmlState.ELEMENT;
		}
		
		return super.performTransition();
	}

}
