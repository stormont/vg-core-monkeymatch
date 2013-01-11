package com.voyagegames.weatherroute;

import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.xml.weather.IWeatherEnumerator;

public class UrlEnumerator implements IUrlXmlHandler<IWeatherEnumerator> {
	
	private final String mUrl;
	private final IWeatherEnumerator mEnumerator;
	
	public UrlEnumerator(final String url, final IWeatherEnumerator enumerator) {
		mUrl = url;
		mEnumerator = enumerator;
	}

	@Override
	public String url() {
		return mUrl;
	}

	@Override
	public IWeatherEnumerator handler() {
		return mEnumerator;
	}

	@Override
	public void initialize(final ILogger logger, final IWeatherEnumerator enumerator) {
		// no-op
	}

	@Override
	public IWeatherEnumerator generic() {
		return mEnumerator;
	}

}
