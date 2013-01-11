package com.voyagegames.weatherroute.core.json.openstreetmap;

import com.voyagegames.weatherroute.core.LatLong;
import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestLatLongJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("lat", JsonValueType.NUMBER),
			new RequiredObject("lng", JsonValueType.NUMBER),
		};
	
	public final LatLong value;
	
	public MapQuestLatLongJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		value = new LatLong(obj.values.get("lat").numberValue(), obj.values.get("lng").numberValue());
	}

}
