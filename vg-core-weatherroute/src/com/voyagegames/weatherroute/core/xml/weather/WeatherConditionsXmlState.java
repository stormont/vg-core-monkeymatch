package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class WeatherConditionsXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "weather-conditions";
	
	private boolean mNestedValue;
	
	public WeatherConditionsXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
		mNestedValue = false;
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(ValueXmlState.ELEMENT, false) ||
				mIncomingElement.matchTransition(WeatherConditionsXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(ValueXmlState.ELEMENT, false)) {
			mNestedValue = true;
			return ValueXmlState.ELEMENT;
		} else if (mIncomingElement.matchTransition(WeatherConditionsXmlState.ELEMENT, true)) {
			mCallback.setWeatherConditionEnumerating(false);
			return WeatherXmlState.ELEMENT;
		}

		return super.performTransition();
	}
	
	@Override
	public void resetData() {
		super.resetData();
		mNestedValue = false;
	}
	
	@Override
	protected boolean desiresAttributes() {
		return (!mNestedValue);
	}
	
	@Override
	public void pushData() {
		if (!mNestedValue) {
			mCallback.addValue(mBuffer.toString(), mAttributes);
		}
	}

}
