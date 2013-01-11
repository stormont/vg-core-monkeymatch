package com.voyagegames.weatherroute;

import android.util.Log;

import com.voyagegames.weatherroute.core.IHttpRequestCallback;
import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.json.JsonParser;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestReverseJson;

public class GeocodeRequestCallback implements IHttpRequestCallback<String>, ILogger {
	
	private static final String TAG = GeocodeRequestCallback.class.getName();
	
	public final Waypoint waypoint;
	
	private final IResultHandler mHandler;
	private final String mUrl;
	private final int mIdentifier;
	
	public GeocodeRequestCallback(final IResultHandler handler, final int identifier, final Waypoint waypoint) {
		this.waypoint = waypoint;
		
		mHandler = handler;
		mIdentifier = identifier;
		mUrl = "http://open.mapquestapi.com/geocoding/v1/reverse?&outFormat=json&zoom=8" +
				"&location=" + waypoint.location.latitude + "," + waypoint.location.longitude;
	}

	@Override
	public void run() {
		new HttpRequestAsyncTask(this).execute(new String[] { mUrl });
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
	public void onPostExecute(final String result) {
		if (result == null) {
			Log.e(TAG, "Result was null using " + mUrl);
			mHandler.notifyFailure(mIdentifier);
			return;
		}

		try {
			final MapQuestReverseJson mqrj = new MapQuestReverseJson(new JsonParser(this).parse(result));
			
			if (mqrj.results.size() < 1) {
				Log.e(TAG, "JSON did not include any results");
				Log.e(TAG, "JSON:");
				Log.e(TAG, result);
				mHandler.notifyFailure(mIdentifier);
				return;
			}
			
			if (mqrj.results.get(0).locations.size() < 1) {
				Log.e(TAG, "JSON result did not include any locations");
				Log.e(TAG, "JSON:");
				Log.e(TAG, result);
				mHandler.notifyFailure(mIdentifier);
				return;
			}
			
			waypoint.setLocationInfo(mqrj.results.get(0).locations.get(0));
			mHandler.notifySuccess(mIdentifier);
		} catch (final Exception e) {
			Log.e(TAG, "Failed to parse JSON using " + mUrl);
			Log.e(TAG, e.getMessage(), e);
			Log.e(TAG, "JSON:");
			Log.e(TAG, result);
			mHandler.notifyFailure(mIdentifier);
		}
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
