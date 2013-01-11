package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class WeatherXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "weather";
	
	public WeatherXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(WeatherConditionsXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(WeatherXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(WeatherConditionsXmlState.ELEMENT, false)) {
			mCallback.setWeatherConditionEnumerating(true);
			return WeatherConditionsXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(WeatherXmlState.ELEMENT, true)) {
			return ParametersXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
