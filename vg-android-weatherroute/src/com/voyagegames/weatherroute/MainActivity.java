package com.voyagegames.weatherroute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.voyagegames.weatherroute.core.IController;
import com.voyagegames.weatherroute.core.xml.weather.WeatherSnapshot;

public class MainActivity extends Activity implements IController {
	
	private static final String TAG = MainActivity.class.getName();
	
	private enum ControllerState {
		INIT,
		WAYPOINTS,
		WEATHER,
		END
	}
	
	private final RouteRequestCallback mRouteCallback;
	private final List<WeatherGeocodeHandler> mWeatherHandlers;
	
	private ControllerState mState;
	private int mCompletedHandlers;
	
	public MainActivity() {
		mRouteCallback = new RouteRequestCallback(this);
		mWeatherHandlers = new ArrayList<WeatherGeocodeHandler>();
		mState = ControllerState.INIT;
		setCallback();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// TODO Dynamically request these locations
		mRouteCallback.setStartLocation(new LocationInfo("Reno", null, "NV", "United States of America"));
		mRouteCallback.setEndLocation(new LocationInfo("Bend", null, "OR", "United States of America"));
		
		if (mRouteCallback != null) {
			mRouteCallback.run();
		} else {
			Log.e(TAG, "Route callback is null with state: " + mState.toString());
			finish();
		}
	}

	@Override
	public void notifyCompletion() {
		setCallback();
		
		final List<Waypoint> waypoints = mRouteCallback.waypoints();
		
		if (waypoints.size() < 1) {
			mState = ControllerState.END;
			return;
		}
		
		for (int i = 0; i < waypoints.size(); ++i) {
			final WeatherGeocodeHandler handler = new WeatherGeocodeHandler(this, i, waypoints.get(i));
			
			mWeatherHandlers.add(handler);
			handler.run();
		}
	}

	@Override
	public void notifyCompletion(final int identifier) {
		++mCompletedHandlers;
		
		if (identifier < 0 || identifier >= mWeatherHandlers.size()) {
			Log.e(TAG, "Identifier is out of range: " + identifier);
			return;
		}
		
		notifyHandlerComplete(mWeatherHandlers.get(identifier));
		
		if (mCompletedHandlers >= mRouteCallback.waypoints().size()) {
			notifyWaypointCallbackComplete();
		}
	}

	@Override
	public void notifyFailure() {
		mState = ControllerState.END;
		setCallback();
		
		// TODO
		finish();
	}

	@Override
	public void notifyFailure(final int identifier) {
		// TODO
		Log.e(TAG, "Handler failed: " + identifier);
		notifyFailure();
	}
	
	private void setCallback() {
		switch (mState) {
		case INIT:
			mState = ControllerState.WAYPOINTS;
			break;
		case WAYPOINTS:
			mState = ControllerState.WEATHER;
			break;
		default:
			mState = ControllerState.END;
			break;
		}
	}
	
	private void notifyHandlerComplete(final WeatherGeocodeHandler handler) {
		// For debugging
		/*
		Log.e(TAG, "Handler " + handler.identifier);
		
		if (handler.waypoint.info.city != null) {
			Log.e(TAG, "  Location: " + handler.waypoint.info.city + ", " + handler.waypoint.info.state);
		} else {
			Log.e(TAG, "  Location: " + handler.waypoint.info.county + ", " + handler.waypoint.info.state);
		}
		
		Log.e(TAG, "  # snapshots: " + handler.snapshots().size());

		for (final WeatherSnapshot s : handler.snapshots()) {
			Log.e(TAG, "  Snapshot:");
			Log.e(TAG, "    " + s.startTime);
			Log.e(TAG, "    " + s.iconLink);
			Log.e(TAG, "    " + s.temperature + " degrees");
			Log.e(TAG, "    " + s.conditions.describe());
		}
		*/
	}
	
	private void notifyWaypointCallbackComplete() {
		// For debugging
		/*
		final int numDecimals = 7;
		Log.e(TAG, "# waypoints: " + mRouteCallback.waypoints().size());
		
		for (final Waypoint w : mRouteCallback.waypoints()) {
			final double lat = Utilities.roundTo(w.location.latitude, numDecimals);
			final double lon = Utilities.roundTo(w.location.longitude, numDecimals);
			Log.e(TAG, "Waypoint: " + lat + "," + lon);
		}
		
		Log.e(TAG, "Routing complete");
		*/
		
		final List<Waypoint> rawWaypoints = mRouteCallback.waypoints();
		final List<WaypointItem> waypoints = new ArrayList<WaypointItem>();
		final Map<String, String> locations = new HashMap<String, String>();
		
		for (int i = 0; i < rawWaypoints.size(); ++i) {
			final WeatherGeocodeHandler handler = mWeatherHandlers.get(i);
			final WeatherSnapshot snapshot = handler.snapshots().get(0);
			final String waypointDesc = rawWaypoints.get(i).describe();
			
			if (locations.containsKey(waypointDesc)) {
				// Prevent duplicates; the locations will be so close together to be basically useless
				continue;
			}

			locations.put(waypointDesc, waypointDesc);
			waypoints.add(new WaypointItem(
					waypointDesc,
					rawWaypoints.get(i).location,
					snapshot.temperature,
					snapshot.conditions.describe(),
					snapshot.iconLink));
		}
		
		final WaypointAdapter adapter = new WaypointAdapter(this, R.layout.waypoint_row, waypoints);
		
		final ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setVisibility(View.VISIBLE);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
				adapter.onItemClick(parent, view, position, id);
			}
		});
		
		final ProgressBar progress = (ProgressBar)findViewById(R.id.progress);
		progress.setVisibility(View.GONE);
	}

}
