package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class TimeLayoutXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "time-layout";
	
	public TimeLayoutXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(TimeLayoutXmlState.ELEMENT, true) ||
				mIncomingElement.matchTransition(StartValidTimeXmlState.ELEMENT, false));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(TimeLayoutXmlState.ELEMENT, true)) {
			mCallback.signalTimeLayoutsCompleted();
			return DataXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(StartValidTimeXmlState.ELEMENT, false)) {
			return StartValidTimeXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
