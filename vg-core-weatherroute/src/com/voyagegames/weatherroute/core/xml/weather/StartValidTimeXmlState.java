package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class StartValidTimeXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "start-valid-time";
	
	public StartValidTimeXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(StartValidTimeXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(StartValidTimeXmlState.ELEMENT, true)) {
			return TimeLayoutXmlState.ELEMENT;
		}

		return super.performTransition();
	}

	@Override
	public void accumulateCharacters(final char[] ch, final int start, final int length) {
		mBuffer.append(ch, start, length);
	}
	
	@Override
	public void pushData() {
		mCallback.addStartValidTime(mBuffer.toString());
	}

}
