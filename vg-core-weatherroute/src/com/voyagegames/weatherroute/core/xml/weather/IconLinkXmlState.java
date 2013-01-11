package com.voyagegames.weatherroute.core.xml.weather;

import com.voyagegames.weatherroute.core.ILogger;


public class IconLinkXmlState extends WeatherBaseXmlState {
	
	public static final String ELEMENT = "icon-link";
	
	public IconLinkXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		super(logger, callback);
	}

	@Override
	public String key() {
		return ELEMENT;
	}

	@Override
	public boolean canTransition() {
		return (mIncomingElement.matchTransition(IconLinkXmlState.ELEMENT, true));
	}

	@Override
	public String performTransition() {
		if (mIncomingElement.matchTransition(IconLinkXmlState.ELEMENT, true)) {
			return ConditionsIconXmlState.ELEMENT;
		}

		return super.performTransition();
	}

	@Override
	public void accumulateCharacters(final char[] ch, final int start, final int length) {
		mBuffer.append(ch, start, length);
	}
	
	@Override
	public void pushData() {
		mCallback.addIconLink(mBuffer.toString());
	}

}
