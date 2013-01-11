package com.voyagegames.weatherroute;

import com.voyagegames.weatherroute.core.LatLong;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestLocationJson;
import com.voyagegames.weatherroute.core.json.openstreetmap.MapQuestManeuverJson;

public class Waypoint {
	
	public final LatLong location;
	public final int time;
	
	public LocationInfo info;
	
	public Waypoint(final MapQuestManeuverJson maneuver) {
		this.location = new LatLong(maneuver.startPoint.value.latitude, maneuver.startPoint.value.longitude);
		this.time = maneuver.time;
		this.info = null;
	}
	
	public void setLocationInfo(final MapQuestLocationJson details) {
		if (details.adminArea5.length() > 0) {
			info = new LocationInfo(details.adminArea5, details.adminArea4, details.adminArea3, details.adminArea1);
		} else {
			info = new LocationInfo(details.adminArea4, details.adminArea3, details.adminArea1);
		}
	}
	
	public String describe() {
		if (info == null) {
			return new String(location.latitude + ", " + location.longitude);
		}
		
		if (info.city == null) {
			return new String(info.county + ", " + info.state);
		}
		
		return new String(info.city + ", " + info.state);
	}

}
