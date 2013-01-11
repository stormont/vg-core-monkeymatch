package com.voyagegames.weatherroute;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.voyagegames.weatherroute.core.IController;
import com.voyagegames.weatherroute.core.IHttpRequestCallback;
import com.voyagegames.weatherroute.core.ILogger;
import com.voyagegames.weatherroute.core.json.JsonParser;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestLegJson;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestManeuverJson;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestSearchJson;

public class RouteRequestCallback implements IHttpRequestCallback<String>, ILogger {
	
	private static final String TAG = RouteRequestCallback.class.getName();
	
	private final IController mController;
	private final List<Waypoint> mWaypoints;
	
	private String mUrl;
	private LocationInfo mStart;
	private LocationInfo mEnd;
	
	public RouteRequestCallback(final IController controller) {
		mController = controller;
		mWaypoints = new ArrayList<Waypoint>();
	}
	
	public List<Waypoint> waypoints() {
		return mWaypoints;
	}
	
	public void setStartLocation(final LocationInfo location) {
		mStart = location;
	}
	
	public void setEndLocation(final LocationInfo location) {
		mEnd = location;
	}

	@Override
	public void run() {
		mWaypoints.clear();
		
		if (mStart == null || mEnd == null) {
			Log.e(TAG, "Start and/or end location is null");
			return;
		}
		
		mUrl = "http://open.mapquestapi.com/directions/v1/route?outFormat=json&timeType=1&shapeFormat=raw&generalize=50000" +
				"&from=" + mStart.city + "," + mStart.state +
				"&to=" + mEnd.city + "," + mEnd.state;
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
			mController.notifyFailure();
			return;
		}

		try {
			final MapQuestSearchJson mqsj = new MapQuestSearchJson(new JsonParser(this).parse(result));
			
			if (mqsj.route.legs.size() != 1) {
				Log.e(TAG, "Result did not have one leg using " + mUrl);
				mController.notifyFailure();
				return;
			}
			
			final MapQuestLegJson mqlj = mqsj.route.legs.get(0);
			
			for (final MapQuestManeuverJson j : mqlj.maneuvers) {
				mWaypoints.add(new Waypoint(j));
			}
		
			mController.notifyCompletion();
		} catch (final Exception e) {
			Log.e(TAG, "Failed to parse JSON using " + mUrl);
			Log.e(TAG, e.getMessage(), e);
			Log.e(TAG, "JSON:");
			Log.e(TAG, result);
			mController.notifyFailure();
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
