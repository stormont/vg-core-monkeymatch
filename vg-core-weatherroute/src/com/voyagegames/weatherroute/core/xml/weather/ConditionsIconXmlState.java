package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class ConditionsIconXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "conditions-icon";
	
	public ConditionsIconXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(IconLinkXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(ConditionsIconXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(IconLinkXmlState.ELEMENT, false)) {
			return IconLinkXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(ConditionsIconXmlState.ELEMENT, true)) {
			return ParametersXmlState.ELEMENT;
		}

		return super.performTransition();
	}

}
