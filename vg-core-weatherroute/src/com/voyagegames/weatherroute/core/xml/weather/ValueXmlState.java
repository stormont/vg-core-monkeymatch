package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class ValueXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "value";
	
	public ValueXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(VisibilityXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(ValueXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(VisibilityXmlState.ELEMENT, false)) {
			return VisibilityXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(ValueXmlState.ELEMENT, true)) {
			if (mCallback.temperatureEnumerating()) {
				return TemperatureXmlState.ELEMENT;
			} else if (mCallback.weatherConditionEnumerating()) {
				return WeatherConditionsXmlState.ELEMENT;
			}
			
			throw new IllegalArgumentException("No known value type has been set as enumerating");
		}

		return super.performTransition();
	}

	@Override
	public void accumulateCharacters(final char[] ch, final int start, final int length) {
		mBuffer.append(ch, start, length);
	}
	
	@Override
	public void pushData() {
		mCallback.addValue(mBuffer.toString(), mAttributes);
	}
	
	@Override
	protected boolean desiresAttributes() {
		return mCallback.weatherConditionEnumerating();
	}

}
