package com.voyagegames.weatherroute.core.json.openstreetmap;

import com.voyagegames.weatherroute.core.json.JsonObject;
import com.voyagegames.weatherroute.core.json.JsonValueType;
import com.voyagegames.weatherroute.core.json.RequiredObject;
import com.voyagegames.weatherroute.core.json.StructuredJson;

public class MapQuestSearchJson extends StructuredJson {
	
	private static final RequiredObject[] REQUIRED_OBJECTS = {
			new RequiredObject("info", JsonValueType.OBJECT),
			new RequiredObject("route", JsonValueType.OBJECT)
		};
	
	public final MapQuestInfoJson info;
	public final MapQuestRouteJson route;
	
	public MapQuestSearchJson(final JsonObject obj) {
		super(obj, REQUIRED_OBJECTS);
		
		info = new MapQuestInfoJson(obj.values.get("info").objectValue());
		route = new MapQuestRouteJson(obj.values.get("route").objectValue());
	}

}
