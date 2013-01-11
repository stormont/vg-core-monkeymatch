package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class TemperatureXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "temperature";
	
	public TemperatureXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(ValueXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(TemperatureXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(ValueXmlState.ELEMENT, false)) {
			return ValueXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(TemperatureXmlState.ELEMENT, true)) {
			mCallback.setTemperatureEnumerating(false);
			return ParametersXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
