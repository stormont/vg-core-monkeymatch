package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class VisibilityXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "visibility";
	
	public VisibilityXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(VisibilityXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(VisibilityXmlState.ELEMENT, true)) {
			return ValueXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
