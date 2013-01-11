package com.voyagegames.weatherroute;

import java.util.List;

import com.voyagegames.weatherroute.core.IController;
import com.voyagegames.weatherroute.core.xml.weather.WeatherSnapshot;

public class WeatherGeocodeHandler implements Runnable, IResultHandler {
	
	private enum CallbackIdentifier {
		WEATHER,
		GEOCODE
	}
	
	public final IController controller;
	public final int identifier;
	public final Waypoint waypoint;
	
	private final WeatherRequestCallback mWeatherCallback;
	private final GeocodeRequestCallback mGeocodeCallback;
	
	private boolean mWeatherCompleted;
	private boolean mGeocodeCompleted;
	
	public WeatherGeocodeHandler(final IController controller, final int identifier, final Waypoint waypoint) {
		this.controller = controller;
		this.identifier = identifier;
		this.waypoint = waypoint;
		
		mWeatherCallback = new WeatherRequestCallback(this, CallbackIdentifier.WEATHER.ordinal(), waypoint);
		mGeocodeCallback = new GeocodeRequestCallback(this, CallbackIdentifier.GEOCODE.ordinal(), waypoint);
	}
	
	public List<WeatherSnapshot> snapshots() {
		final WeatherRouteEnumerator wre = (WeatherRouteEnumerator)mWeatherCallback.enumerator().generic();
		return wre.snapshots();
	}
	
	@Override
	public void run() {
		if (mGeocodeCompleted) {
			return;
		}
		
		if (mWeatherCompleted) {
			mGeocodeCallback.run();
			return;
		}
		
		mWeatherCallback.run();
	}

	@Override
	public void notifySuccess(final int identifier) {
		if (!mWeatherCompleted) {
			mWeatherCompleted = true;
			run();
		} else {
			mGeocodeCompleted = true;
			controller.notifyCompletion(this.identifier);
		}
	}

	@Override
	public void notifyFailure(final int identifier) {
		if (identifier == CallbackIdentifier.WEATHER.ordinal()) {
			controller.notifyFailure(this.identifier);
		}
		
		// Ignore GEOCODE failures
	}

}
