package com.voyagegames.weatherroute;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WaypointAdapter extends ArrayAdapter<WaypointItem> {
	
	private static final int FREEZING_TEMP = 32;
	private static final int HOT_TEMP = 100;
	
	private final Activity mParent;
	private final List<WaypointItem> mWaypoints;

	public WaypointAdapter(final Context context, final int textViewResourceId, final List<WaypointItem> objects) {
		super(context, textViewResourceId, objects);
		mParent = (Activity)context;
		mWaypoints = objects;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		
		if (row == null) {
			final LayoutInflater inflater = mParent.getLayoutInflater();
			row = inflater.inflate(R.layout.waypoint_row, parent, false);
		}
		
		final WaypointItem item = mWaypoints.get(position);
		
		final TextView location = (TextView)row.findViewById(R.id.location);
		location.setText(item.waypoint);
		
		final String temp = String.valueOf(item.temperature) + "° F";
		final TextView temperature = (TextView)row.findViewById(R.id.temperature);
		temperature.setText(temp);
		
		if (item.temperature <= FREEZING_TEMP) {
			temperature.setTextColor(Color.BLUE);
		} else if (item.temperature >= HOT_TEMP) {
			temperature.setTextColor(Color.RED);
		}
		
		final TextView weather = (TextView)row.findViewById(R.id.weatherConditions);
		weather.setText(item.weatherConditions);
		
		final ImageView icon = (ImageView)row.findViewById(R.id.icon);
		new BitmapRequestAsyncTask(icon).execute(item.iconLink);
		
		return row;
	}

	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		final WaypointItem item = mWaypoints.get(position);
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		
		intent.setData(Uri.parse("geo:" + item.location.latitude + "," + item.location.longitude));
		mParent.startActivity(intent);
	}
	
}
