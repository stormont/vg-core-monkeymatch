package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class DwmlXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "dwml";
	
	public DwmlXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(HeadXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(DataXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(DwmlXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(HeadXmlState.ELEMENT, false)) {
			return HeadXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(DataXmlState.ELEMENT, false)) {
			return DataXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(DwmlXmlState.ELEMENT, true)) {
			return EndState.ELEMENT;
		}
		
		return super.performTransition();
	}

}
