package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class DataXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "data";
	
	public DataXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(TimeLayoutXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(ParametersXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(DataXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(TimeLayoutXmlState.ELEMENT, false)) {
			return TimeLayoutXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(ParametersXmlState.ELEMENT, false)) {
			return ParametersXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(DataXmlState.ELEMENT, true)) {
			return DwmlXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
