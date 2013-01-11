package com.voyagegames.weatherroute.core.xml.weather.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.voyagegames.weatherroute.core.xml.weather.IWeatherEnumerator;


public class Enumerator implements IWeatherEnumerator {

	private final List<String> mStartTimes;
	private final List<String> mIconLinks;
	private final List<Integer> mTemperatures;
	private final Map<String, List<String>> mAttributes;
	
	private boolean mCompleted;
	private boolean mTimeLayoutsCompleted;
	private boolean mTemperatureEnumerating;
	private boolean mWeatherConditionEnumerating;
	
	private int mNullCount;
	
	public Enumerator() {
		mStartTimes = new ArrayList<String>();
		mIconLinks = new ArrayList<String>();
		mTemperatures = new ArrayList<Integer>();
		mAttributes = new HashMap<String, List<String>>();
		
		mCompleted = false;
		mTimeLayoutsCompleted = false;
		mTemperatureEnumerating = false;
		mWeatherConditionEnumerating = false;
		mNullCount = 0;
	}
	
	public boolean completed() {
		return mCompleted;
	}
	
	public boolean timeLayoutsCompleted() {
		return mTimeLayoutsCompleted;
	}
	
	public int nullCount() {
		return mNullCount;
	}
	
	public List<String> startTimes() {
		return mStartTimes;
	}
	
	public List<String> iconLinks() {
		return mIconLinks;
	}
	
	public List<Integer> temperatures() {
		return mTemperatures;
	}
	
	public Map<String, List<String>> attributes() {
		return mAttributes;
	}

	@Override
	public void addStartValidTime(final String time) {
		mStartTimes.add(time);
	}

	@Override
	public void addValue(final String value, final Map<String, String> attributes) {
		if (mTemperatureEnumerating) {
			mTemperatures.add(Integer.parseInt(value));
		} else if (mWeatherConditionEnumerating) {
			if (attributes.size() > 0) {
				final Set<String> keys = attributes.keySet();
				
				for (final String k : keys) {
					if (mAttributes.containsKey(k)) {
						mAttributes.get(k).add(attributes.get(k));
					} else {
						final List<String> attrs = new ArrayList<String>();
						attrs.add(attributes.get(k));
						mAttributes.put(k, attrs);
					}
				}
			} else {
				++mNullCount;
			}
		}
	}

	@Override
	public void addIconLink(final String link) {
		mIconLinks.add(link);
	}

	@Override
	public void signalTimeLayoutsCompleted() {
		mTimeLayoutsCompleted = true;
	}

	@Override
	public void signalComplete() {
		mCompleted = true;
	}

	@Override
	public boolean temperatureEnumerating() {
		return mTemperatureEnumerating;
	}

	@Override
	public boolean weatherConditionEnumerating() {
		return mWeatherConditionEnumerating;
	}

	@Override
	public void setTemperatureEnumerating(final boolean enumerating) {
		mTemperatureEnumerating = enumerating;
	}

	@Override
	public void setWeatherConditionEnumerating(final boolean enumerating) {
		mWeatherConditionEnumerating = enumerating;
	}
	
}
