package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class ParametersXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "parameters";
	
	public ParametersXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(TemperatureXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(HazardsXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(WeatherXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(ConditionsIconXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(ParametersXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(TemperatureXmlState.ELEMENT, false)) {
			mCallback.setTemperatureEnumerating(true);
			return TemperatureXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(HazardsXmlState.ELEMENT, false)) {
			return HazardsXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(WeatherXmlState.ELEMENT, false)) {
			return WeatherXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(ConditionsIconXmlState.ELEMENT, false)) {
			return ConditionsIconXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(ParametersXmlState.ELEMENT, true)) {
			return DataXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
