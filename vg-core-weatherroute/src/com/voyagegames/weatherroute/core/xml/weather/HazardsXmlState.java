package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class HazardsXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "hazards";
	
	public HazardsXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(HazardsXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(HazardsXmlState.ELEMENT, true)) {
			return ParametersXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
