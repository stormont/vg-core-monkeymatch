package com.voyagegames.weatherroute;

import com.voyagegames.weatherroute.core.LatLong;

public class WaypointItem {
	
	public final String waypoint;
	public final LatLong location;
	public final int temperature;
	public final String weatherConditions;
	public final String iconLink;
	
	public WaypointItem(final String waypoint, final LatLong location, final int temperature, final String weatherConditions, final String iconLink) {
		this.waypoint = waypoint;
		this.location = location;
		this.temperature = temperature;
		this.weatherConditions = weatherConditions;
		this.iconLink = iconLink;
	}

}
