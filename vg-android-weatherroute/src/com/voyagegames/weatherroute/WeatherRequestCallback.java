package com.voyagegames.weatherroute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;

import com.voyagegames.weatherroute.core.IHttpRequestCallback;
import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.xml.weather.IWeatherEnumerator;

public class WeatherRequestCallback implements IHttpRequestCallback<Boolean>, ILogger {
	
	private static final String TAG = WeatherRequestCallback.class.getName();
	
	private final IResultHandler mHandler;
	private final UrlEnumerator mEnumerator;
	private final int mIdentifier;
	
	@SuppressLint("SimpleDateFormat")
	public WeatherRequestCallback(final IResultHandler handler, final int identifier, final Waypoint waypoint) {
		mHandler = handler;
		mIdentifier = identifier;

		final Date date = new Date();
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		final String url = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php?temp=temp&wx=wx&icons=icons&wwa=wwa" +
				"&lat=" + waypoint.location.latitude +
				"&lon=" + waypoint.location.longitude +
				"&begin=" + dateFormat.format(date);
		
		mEnumerator = new UrlEnumerator(url, new WeatherRouteEnumerator(this, url));
	}
	
	public UrlEnumerator enumerator() {
		return mEnumerator;
	}

	@Override
	public void run() {
		final IUrlXmlHandler<IWeatherEnumerator>[] params = new UrlEnumerator[1];
		final WeatherRouteEnumerator wre = (WeatherRouteEnumerator)mEnumerator.generic();
		
		params[0] = mEnumerator;
		wre.initialize(this, wre);
		new XmlRequestAsyncTask<IWeatherEnumerator>(this).execute(params);
	}

	@Override
	public void onCancelled() {
		Log.e(TAG, "HttpRequest was cancelled");
	}

	@Override
	public void onProgressUpdate(final String... values) {
		for (final String s : values) {
			Log.e(TAG, "HttpRequest progress update: " + s);
		}
	}

	@Override
	public void onPostExecute(final Boolean result) {
		if (result == false) {
			Log.e(TAG, "XmlRequestAsyncTask failed");
			mHandler.notifyFailure(mIdentifier);
			return;
		}
	
		final WeatherRouteEnumerator wre = (WeatherRouteEnumerator)mEnumerator.generic();
		
		if (!wre.completed()) {
			Log.e(TAG, "XML request failed using " + mEnumerator.url());
			Log.e(TAG, "Ended at state " + wre.currentState() + " with chain:");
			
			for (final String s : wre.chain()) {
				Log.e(TAG, "  " + s);
			}
			
			mHandler.notifyFailure(mIdentifier);
			return;
		}

		mHandler.notifySuccess(mIdentifier);
	}

	@Override
	public void log(final String tag, final String msg) {
		Log.e(TAG, msg);
	}

	@Override
	public void log(final String tag, final String msg, final Exception e) {
		Log.e(TAG, msg, e);
	}

}
