package com.voyagegames.weatherroute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.xml.weather.IWeatherEnumerator;
import com.voyagegames.weatherroute.core.xml.weather.WeatherCondition;
import com.voyagegames.weatherroute.core.xml.weather.WeatherParser;
import com.voyagegames.weatherroute.core.xml.weather.WeatherSnapshot;

public class WeatherRouteEnumerator extends WeatherParser implements IWeatherEnumerator {
	
	private static final String TAG = WeatherRouteEnumerator.class.getName();
	
	private final ILogger mLogger;
	private final List<String> mStartValidTimes;
	private final List<String> mIconLinks;
	private final List<Integer> mTemperatures;
	private final List<List<WeatherCondition>> mWeatherDescriptors;
	
	private boolean mTimeLayoutCompleted;
	private boolean mStateMachineCompleted;
	
	private boolean mTemperatureEnumerating;
	private boolean mWeatherConditionEnumerating;
	
	private String mUrl;
	
	public WeatherRouteEnumerator(final ILogger logger, final String url) {
		mLogger = logger;
		mUrl = url;
		mTimeLayoutCompleted = false;
		mStateMachineCompleted = false;
		mTemperatureEnumerating = false;
		mWeatherConditionEnumerating = false;
		
		mStartValidTimes = new ArrayList<String>();
		mIconLinks = new ArrayList<String>();
		mTemperatures = new ArrayList<Integer>();
		mWeatherDescriptors = new ArrayList<List<WeatherCondition>>();
	}
	
	public boolean completed() {
		return mStateMachineCompleted;
	}
	
	public List<String> startValidTimes() {
		return mStartValidTimes;
	}
	
	public List<String> iconLinks() {
		return mIconLinks;
	}
	
	public List<Integer> temperatures() {
		return mTemperatures;
	}
	
	public List<List<WeatherCondition>> weatherDescriptors() {
		return mWeatherDescriptors;
	}
	
	public List<WeatherSnapshot> snapshots() {
		if (
				mStartValidTimes.size() != mIconLinks.size() ||
				mStartValidTimes.size() != mTemperatures.size() ||
				mStartValidTimes.size() != mWeatherDescriptors.size()) {
			mLogger.log(TAG, "Data sets do not match in size from " + mUrl);
		}
		
		final List<WeatherSnapshot> result = new ArrayList<WeatherSnapshot>();
		
		for (int i = 0; i < mStartValidTimes.size(); ++i) {
			result.add(new WeatherSnapshot(
					mStartValidTimes.get(i),
					mIconLinks.get(i),
					mTemperatures.get(i),
					mWeatherDescriptors.get(i)
				));
		}
		
		return result;
	}

	@Override
	public void initialize(final ILogger logger, final IWeatherEnumerator enumerator) {
		super.initialize(logger, enumerator);
	}

	@Override
	public void addStartValidTime(final String time) {
		if (!mTimeLayoutCompleted) {
			mStartValidTimes.add(time);
		}
	}

	@Override
	public void addValue(final String value, final Map<String, String> attributes) {
		if (mTemperatureEnumerating) {
			mTemperatures.add(Integer.parseInt(value));
		} else if (mWeatherConditionEnumerating) {
			if (attributes != null && attributes.size() > 0) {
				try {
					final WeatherCondition wc = new WeatherCondition(attributes);
					
					switch (wc.type) {
					case SINGLE:
						final List<WeatherCondition> l = new ArrayList<WeatherCondition>();
						l.add(wc);
						mWeatherDescriptors.add(l);
						break;
					case ADDITIVE:
						mWeatherDescriptors.get(mWeatherDescriptors.size() - 1).add(wc);
						break;
					default:
						throw new IllegalArgumentException("Unhandled weather condition type: " + wc.type.toString());
					}
				} catch (final Exception e) {
					mLogger.log(TAG, e.getMessage(), e);
				}
			} else {
				final List<WeatherCondition> l = new ArrayList<WeatherCondition>();
				l.add(new WeatherCondition(null));
				mWeatherDescriptors.add(l);
			}
		}
	}

	@Override
	public void addIconLink(final String link) {
		mIconLinks.add(link);
	}

	@Override
	public void signalTimeLayoutsCompleted() {
		mTimeLayoutCompleted = true;
	}

	@Override
	public void signalComplete() {
		mStateMachineCompleted = true;
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
