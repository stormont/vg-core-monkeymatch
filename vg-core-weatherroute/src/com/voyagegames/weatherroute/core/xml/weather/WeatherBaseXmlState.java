package com.voyagegames.weatherroute.core.xml.weather;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.IState;

public abstract class WeatherBaseXmlState implements IState {
	
	private static final int STRING_CAPACITY = 1024;
	
	protected final ILogger mLogger;
	protected final IWeatherEnumerator mCallback;
	protected final Map<String, String> mAttributes;
	
	protected IncomingElement mIncomingElement;
	protected StringBuffer mBuffer;
	
	public WeatherBaseXmlState(final ILogger logger, final IWeatherEnumerator callback) {
		mLogger = logger;
		mCallback = callback;
		mAttributes = new HashMap<String, String>();
		resetData();
	}
	
	public void setIncomingElement(final IncomingElement element) {
		mIncomingElement = element;
	}
	
	public void setAttributes(final Attributes attributes) {
		if (attributes == null) {
			return;
		}
	
		for (int i = 0; i < attributes.getLength(); ++i) {
			mAttributes.put(attributes.getLocalName(i), attributes.getValue(i));
		}
	}
	
	public void resetData() {
		mAttributes.clear();
		mBuffer = new StringBuffer(STRING_CAPACITY);
	}
	
	public void accumulateCharacters(final char[] ch, final int start, final int length) {
		// no-op; extend with child classes
	}
	
	public void pushData() {
		// no-op; extend with child classes
	}
	
	protected boolean desiresAttributes() {
		return false;
	}

	@Override
	public String performTransition() {
		throw new IllegalArgumentException("Unhandled transition state with " + this.mIncomingElement.name + "/" + this.mIncomingElement.ending);
	}

}
